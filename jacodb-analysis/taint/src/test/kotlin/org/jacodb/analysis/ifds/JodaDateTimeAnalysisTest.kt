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

package org.jacodb.analysis.ifds

import kotlinx.coroutines.runBlocking
import org.jacodb.analysis.graph.JcApplicationGraphImpl
import org.jacodb.analysis.ifds.npe.npeIfdsFacade
import org.jacodb.analysis.ifds.taint.taintIfdsFacade
import org.jacodb.analysis.ifds.unused.unusedIfdsFacade
import org.jacodb.api.JcClasspath
import org.jacodb.api.analysis.JcApplicationGraph
import org.jacodb.api.ext.findClass
import org.jacodb.impl.features.usagesExt
import org.jacodb.taint.configuration.TaintConfigurationFeature
import org.jacodb.testing.BaseTest
import org.jacodb.testing.WithGlobalDB
import org.jacodb.testing.allClasspath
import org.joda.time.DateTime
import org.junit.jupiter.api.Test
import kotlin.time.Duration.Companion.seconds

private val logger = mu.KotlinLogging.logger {}

class JodaDateTimeAnalysisTest : BaseTest() {

    companion object : WithGlobalDB()

    override val cp: JcClasspath = runBlocking {
        val configFileName = "config_small.json"
        val configResource = this.javaClass.getResourceAsStream("/$configFileName")
        if (configResource != null) {
            val configJson = configResource.bufferedReader().readText()
            val configurationFeature = TaintConfigurationFeature.fromJson(configJson)
            db.classpath(allClasspath, listOf(configurationFeature) + classpathFeatures)
        } else {
            super.cp
        }
    }

    private val graph: JcApplicationGraph by lazy {
        runBlocking {
            JcApplicationGraphImpl(cp, cp.usagesExt())
        }
    }

    @Test
    fun `test taint analysis`() = runBlocking {
        val ifds = taintIfdsFacade("ifds", cp, graph)
        val clazz = cp.findClass<DateTime>()
        val methods = clazz.declaredMethods
        ifds.runAnalysis(methods, timeout = 20.seconds)
        val sinks = ifds.collectFindings()
        logger.info { "Vulnerabilities found: ${sinks.size}" }
    }

    @Test
    fun `test NPE analysis`() = runBlocking {
        val ifds = npeIfdsFacade("ifds", cp, graph)
        val clazz = cp.findClass<DateTime>()
        val methods = clazz.declaredMethods
        ifds.runAnalysis(methods, timeout = 20.seconds)
        val sinks = ifds.collectFindings()
        logger.info { "Vulnerabilities found: ${sinks.size}" }
    }

    @Test
    fun `test unused variables analysis`() = runBlocking {
        val ifds = unusedIfdsFacade("ifds", cp, graph)
        val clazz = cp.findClass<DateTime>()
        val methods = clazz.declaredMethods
        ifds.runAnalysis(methods, timeout = 20.seconds)
        val sinks = ifds.collectFindings()
        logger.info { "Vulnerabilities found: ${sinks.size}" }
    }
}