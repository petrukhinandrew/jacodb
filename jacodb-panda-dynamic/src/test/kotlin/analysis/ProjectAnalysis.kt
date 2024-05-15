/*
 *  Copyright 2022 UnitTestBot contributors (utbot.org)
 * <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package analysis

import io.mockk.mockk
import org.jacodb.analysis.ifds.SingletonUnit
import org.jacodb.analysis.ifds.UnitResolver
import org.jacodb.analysis.taint.ForwardTaintFlowFunctions
import org.jacodb.analysis.taint.TaintManager
import org.jacodb.analysis.taint.TaintVulnerability
import org.jacodb.analysis.util.PandaTraits
import org.jacodb.analysis.util.getPathEdges
import org.jacodb.panda.dynamic.api.PandaApplicationGraphImpl
import org.jacodb.panda.dynamic.api.PandaInst
import org.jacodb.panda.dynamic.api.PandaMethod
import org.jacodb.panda.dynamic.api.PandaProject
import org.jacodb.taint.configuration.Argument
import org.jacodb.taint.configuration.AssignMark
import org.jacodb.taint.configuration.ConstantTrue
import org.jacodb.taint.configuration.ContainsMark
import org.jacodb.taint.configuration.Result
import org.jacodb.taint.configuration.TaintConfigurationItem
import org.jacodb.taint.configuration.TaintMark
import org.jacodb.taint.configuration.TaintMethodSink
import org.jacodb.taint.configuration.TaintMethodSource
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.EnabledIf
import parser.loadIr
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

private val logger = mu.KotlinLogging.logger {}

class ProjectAnalysis {
    private var tsLinesSuccess = 0L
    private var tsLinesFailed = 0L
    private var analysisTime: Duration = Duration.ZERO
    private var totalEdges = 0
    private var totalSinks: MutableList<TaintVulnerability<PandaMethod, PandaInst>> = mutableListOf()

    companion object: PandaTraits{
        const val PROJECT_PATH = "/samples/project1"
        const val BASE_PATH = "$PROJECT_PATH/entry/src/main/ets/"

        private fun loadProjectForSample(programName: String): PandaProject {
            val parser = loadIr("$BASE_PATH$programName.json")
            val project = parser.getProject()
            return project
        }

        private fun countFileLines(path: String): Long {
            val stream = object {}::class.java.getResourceAsStream(path) ?: error("Resource not found")
            stream.bufferedReader().use { reader ->
                return reader.lines().count()
            }
        }
    }

    private fun projectAvailable(): Boolean {
        val resource = object {}::class.java.getResource(PROJECT_PATH)?.toURI()
        return resource != null && Files.exists(Paths.get(resource))
    }

    @EnabledIf("projectAvailable")
    @Test
    fun processAllFiles() {
        val baseDirUrl = object {}::class.java.getResource(BASE_PATH)
        val baseDir = Paths.get(baseDirUrl?.toURI() ?: error("Resource not found"))
        Files.walk(baseDir)
            .filter { it.toString().endsWith(".json") }
            .map { baseDir.relativize(it).toString().replace("\\", "/").substringBeforeLast('.') }
            .forEach { filename ->
                handleFile(filename)
            }
        makeReport()
    }

    private fun makeReport() {
        logger.info { "Analysis Report On $PROJECT_PATH" }
        logger.info { "====================" }
        logger.info { "Total files processed: ${tsLinesSuccess + tsLinesFailed}" }
        logger.info { "Successfully processed lines: $tsLinesSuccess" }
        logger.info { "Failed lines: $tsLinesFailed" }
        logger.info { "Total analysis time: $analysisTime" }
        logger.info { "Total edges: $totalEdges" }
        logger.info { "Founded sinks: ${totalSinks.size}" }

        if (totalSinks.isNotEmpty()) {
            totalSinks.forEachIndexed { idx, sink ->
                logger.info {
                    """Detailed Sink Information:
                |
                |Sink ID: $idx
                |Statement: ${sink.sink.statement}
                |Fact: ${sink.sink.fact}
                |Condition: ${sink.rule?.condition}
                |
                """.trimMargin()
                }
            }
        } else {
            logger.info { "No sinks found." }
        }
        logger.info { "====================" }
        logger.info { "End of report" }
    }

    private fun handleFile(filename: String) {
        // This files contain critical bugs
        val banFiles: List<String> = listOf(
            "base/sync/utils/SyncUtil"
        )
        val fileLines = countFileLines("$BASE_PATH$filename.ts")
        try {
            if (filename in banFiles) return
            val project = loadProjectForSample(filename)
            val startTime = System.currentTimeMillis()
            when (filename) {
                "base/account/AccountManager" -> runAnalysisOnAccountManager(project, filename)
                else -> runAnalysis(project, filename)
            }
            val endTime = System.currentTimeMillis()
            analysisTime += (endTime - startTime).milliseconds
            tsLinesSuccess += fileLines
        } catch (_: Exception) {
            tsLinesFailed += fileLines
        }
    }

    private fun runAnalysisOnAccountManager(project: PandaProject, filename: String) {
        val graph = PandaApplicationGraphImpl(project)
        val unitResolver = UnitResolver<PandaMethod> { SingletonUnit }
        val getConfigForMethod: ForwardTaintFlowFunctions<PandaMethod, PandaInst>.(PandaMethod) -> List<TaintConfigurationItem>? =
            { method ->
                val rules = buildList {
                    if (method.name == "taintSink") add(
                        TaintMethodSink(
                            method = mockk(),
                            cwe = listOf(),
                            ruleNote = "SINK",
                            condition = ContainsMark(position = Argument(0), mark = TaintMark("TAINT")),
                        )
                    )
                    if (method.name == "requestGet") add(
                        TaintMethodSource(
                            method = mockk(),
                            condition = ConstantTrue,
                            actionsAfter = listOf(AssignMark(position = Result, mark = TaintMark("TAINT")))
                        )
                    )
                }
                rules.ifEmpty { null }
            }
        val manager = TaintManager(
            graph = graph,
            unitResolver = unitResolver,
            getConfigForMethod = getConfigForMethod,
        )

        val methodNames = setOf(
            "getDeviceIdListWithCursor",
            "requestGet",
            "taintRun",
            "taintSink"
        )

        val methods = project.classes.flatMap { it.methods }.filter { it.name in methodNames }
        val sinks = manager.analyze(methods, timeout = 10.seconds)
        totalEdges += manager.runnerForUnit.values.sumOf { it.getPathEdges().size }
        totalSinks.addAll(sinks)
        assertTrue(sinks.isNotEmpty())
    }

    private fun runAnalysis(project: PandaProject, filename: String) {
        val graph = PandaApplicationGraphImpl(project)
        val unitResolver = UnitResolver<PandaMethod> { SingletonUnit }
        val getConfigForMethod: ForwardTaintFlowFunctions<PandaMethod, PandaInst>.(PandaMethod) -> List<TaintConfigurationItem>? =
            { method ->
                val rules = emptyList<TaintMethodSink>()
                rules.ifEmpty { null }
            }
        val manager = TaintManager(
            graph = graph,
            unitResolver = unitResolver,
            getConfigForMethod = getConfigForMethod,
        )

        val methods = project.classes.flatMap { it.methods }
        val sinks = manager.analyze(methods, timeout = 5.seconds)
        totalEdges += manager.runnerForUnit.values.sumOf { it.getPathEdges().size }
        assertTrue(sinks.isEmpty())
    }
}
