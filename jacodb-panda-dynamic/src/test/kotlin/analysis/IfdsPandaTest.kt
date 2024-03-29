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
import org.jacodb.analysis.util.PandaTraits
import org.jacodb.panda.dynamic.api.*
import org.jacodb.panda.dynamic.parser.IRParser
import org.jacodb.taint.configuration.*
import kotlin.test.Test
import kotlin.test.assertTrue

private val logger = mu.KotlinLogging.logger {}

class IfdsPandaTest {

    companion object : PandaTraits

    private fun loadProjectForSample(programName: String): PandaProject {
        val sampleFilePath = javaClass.getResource("/samples/${programName}.json")?.path ?: ""
        val parser = IRParser(sampleFilePath)
        val project = parser.getProject()

        return project
    }

    @Test
    fun `test taint analysis on TypeMismatch`() {
        val project = loadProjectForSample("TypeMismatch")
        val graph = PandaApplicationGraphImpl(project)
        val unitResolver = UnitResolver<PandaMethod> { SingletonUnit }
        val getConfigForMethod: ForwardTaintFlowFunctions<PandaMethod, PandaInst>.(PandaMethod) -> List<TaintConfigurationItem>? =
            { method ->
                val rules = buildList {
                    if (method.name == "add") add(
                        TaintMethodSource(
                            method = mockk(),
                            condition = ConstantTrue,
                            actionsAfter = listOf(
                                AssignMark(mark = TaintMark("TAINT"), position = Result),
                            ),
                        )
                    )
                    if (method.name == "log") add(
                        TaintMethodSink(
                            method = mockk(),
                            ruleNote = "CUSTOM SINK", // FIXME
                            cwe = listOf(), // FIXME
                            condition = ContainsMark(position = Argument(0), mark = TaintMark("TAINT"))
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

        val methods = project.classes.flatMap { it.methods }
        logger.info { "Methods: ${methods.size}" }
        for (method in methods) {
            logger.info { "  ${method.name}" }
        }
        val sinks = manager.analyze(methods)
        logger.info { "Sinks: $sinks" }
        assertTrue(sinks.isNotEmpty())
    }

    @Test
    fun `test taint analysis on DataFlowSecurity`() {
        val project = loadProjectForSample("DataFlowSecurity")
        val graph = PandaApplicationGraphImpl(project)
        val unitResolver = UnitResolver<PandaMethod> { SingletonUnit }
        val getConfigForMethod: ForwardTaintFlowFunctions<PandaMethod, PandaInst>.(PandaMethod) -> List<TaintConfigurationItem>? =
            { method ->
                val rules = buildList {
                    if (method.name == "source") add(
                        TaintMethodSource(
                            method = mockk(),
                            condition = ConstantTrue,
                            actionsAfter = listOf(
                                AssignMark(mark = TaintMark("TAINT"), position = Result),
                            ),
                        )
                    )
                    if (method.name == "sink") add(
                        TaintMethodSink(
                            method = mockk(),
                            ruleNote = "SINK", // FIXME
                            cwe = listOf(), // FIXME
                            condition = ContainsMark(position = Argument(0), mark = TaintMark("TAINT"))
                        )
                    )
                    if (method.name == "pass") add(
                        TaintPassThrough(
                            method = mockk(),
                            condition = ConstantTrue,
                            actionsAfter = listOf(
                                CopyAllMarks(from = Argument(0), to = Result)
                            ),
                        )
                    )
                    if (method.name == "validate") add(
                        TaintPassThrough(
                            method = mockk(),
                            condition = ConstantTrue,
                            actionsAfter = listOf(
                                RemoveMark(mark = TaintMark("TAINT"), position = Argument(0))
                            ),
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

        val goodMethod = project.classes.flatMap { it.methods }.single { it.name == "good" }
        logger.info { "good() method: $goodMethod" }
        val goodSinks = manager.analyze(listOf(goodMethod))
        logger.info { "Sinks in good(): $goodSinks" }
        assertTrue(goodSinks.isEmpty())

        val badMethod = project.classes.flatMap { it.methods }.single { it.name == "bad" }
        logger.info { "bad() method: $badMethod" }
        val badSinks = manager.analyze(listOf(badMethod))
        logger.info { "Sinks in bad(): $badSinks" }
        assertTrue(badSinks.isNotEmpty())
    }

    @Test
    fun `manually create PandaProject`() {
        val project = PandaProject(
            classes = listOf(
                PandaClass(
                    name = "Sample",
                    superClassName = "std.core.Object",
                    methods = listOf(
                        PandaMethod(name = "source", type = PandaAnyType).also {
                            it.blocks = listOf(
                                PandaBasicBlock(
                                    id = 0,
                                    successors = setOf(1),
                                    predecessors = emptySet(),
                                    start = PandaInstRef(0),
                                    end = PandaInstRef(1)
                                ),
                                PandaBasicBlock(
                                    id = 1,
                                    successors = setOf(),
                                    predecessors = setOf(0),
                                    start = PandaInstRef(0),
                                    end = PandaInstRef(1)
                                )
                            )
                            it.instructions = listOf(
                                PandaReturnInst(
                                    location = PandaInstLocation(method = it, index = 0, lineNumber = 3),
                                    returnValue = PandaNullConstant
                                )
                            )
                            it.className = "Sample"
                        }
                    )
                )
            )
        )
        logger.info { "project = $project" }
        logger.info { "classes = ${project.classes}" }
        assertTrue(project.classes.isNotEmpty())
        logger.info { "methods = ${project.classes.flatMap { it.methods }}" }
        assertTrue(project.classes.flatMap { it.methods }.isNotEmpty())
    }

}