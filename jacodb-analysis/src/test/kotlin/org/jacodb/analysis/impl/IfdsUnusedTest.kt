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

import org.jacodb.analysis.ifds.SingletonUnitResolver
import org.jacodb.analysis.unused.UnusedVariableManager
import org.jacodb.api.jvm.ext.findClass
import org.jacodb.api.jvm.ext.methods
import org.jacodb.impl.features.InMemoryHierarchy
import org.jacodb.impl.features.Usages
import org.jacodb.testing.WithDB
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.time.Duration.Companion.seconds

class IfdsUnusedTest : BaseAnalysisTest() {

    companion object : WithDB(Usages, InMemoryHierarchy) {
        @JvmStatic
        fun provideClassesForJuliet563(): Stream<Arguments> = provideClassesForJuliet(
            563, listOf(
                // Unused variables are already optimized out by cfg
                "unused_uninit_variable_",
                "unused_init_variable_int",
                "unused_init_variable_long",
                "unused_init_variable_String_",

                // Unused variable is generated by cfg (!!)
                "unused_value_StringBuilder_17",

                // Expected answers are strange, seems to be problem in tests
                "_12",

                // The variable isn't expected to be detected as unused actually
                "_81"
            )
        )
    }

    @ParameterizedTest
    @MethodSource("provideClassesForJuliet563")
    fun `test on Juliet's CWE 563`(className: String) {
        testSingleJulietClass(className) { method ->
            val unitResolver = SingletonUnitResolver
            val manager = UnusedVariableManager(graph, unitResolver)
            manager.analyze(listOf(method), timeout = 30.seconds)
        }
    }

    @Test
    fun `test on specific Juliet instance`() {
        val className =
            "juliet.testcases.CWE563_Unused_Variable.CWE563_Unused_Variable__unused_init_variable_StringBuilder_01"
        val clazz = cp.findClass(className)
        val badMethod = clazz.methods.single { it.name == "bad" }
        val unitResolver = SingletonUnitResolver
        val manager = UnusedVariableManager(graph, unitResolver)
        val sinks = manager.analyze(listOf(badMethod), timeout = 30.seconds)
        Assertions.assertTrue(sinks.isNotEmpty())
    }
}