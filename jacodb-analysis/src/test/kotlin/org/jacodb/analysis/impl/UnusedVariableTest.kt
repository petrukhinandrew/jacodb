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

package org.jacodb.analysis.impl

import org.jacodb.analysis.AnalysisEngine
import org.jacodb.analysis.JcNaivePoints2EngineFactory
import org.jacodb.analysis.JcSimplifiedGraphFactory
import org.jacodb.analysis.UnusedVariableAnalysisFactory
import org.jacodb.analysis.analyzers.UnusedVariableAnalyzer
import org.jacodb.impl.features.InMemoryHierarchy
import org.jacodb.impl.features.Usages
import org.jacodb.testing.WithDB
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.*
import java.util.stream.Stream

class UnusedVariableTest : BaseAnalysisTest() {
    companion object : WithDB(Usages, InMemoryHierarchy) {
        @JvmStatic
        fun provideClassesForJuliet563(): Stream<Arguments> = provideClassesForJuliet(563, listOf(
            // Unused variables are already optimized out by cfg
            "unused_uninit_variable_", "unused_init_variable_int", "unused_init_variable_long", "unused_init_variable_String_",

            // Unused variable is generated by cfg (!!)
            "unused_value_StringBuilder_17",

            // Expected answers are strange, seems to be problem in tests
            "_12",
        ))

        private val vulnerabilityType = UnusedVariableAnalyzer.value
    }

    @ParameterizedTest
    @MethodSource("provideClassesForJuliet563")
    fun `test on Juliet's CWE 563`(className: String) {
        testSingleJulietClass(engine, vulnerabilityType, className)
    }

    private val engine: AnalysisEngine
        get() {
            val graph = JcSimplifiedGraphFactory().createGraph(cp)
            val points2Engine = JcNaivePoints2EngineFactory.createPoints2Engine(graph)
            return UnusedVariableAnalysisFactory().createAnalysisEngine(graph, points2Engine)
        }
}