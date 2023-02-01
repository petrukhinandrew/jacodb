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

package org.utbot.jacodb.impl

import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.utbot.jacodb.api.JcClasspath
import org.utbot.jacodb.api.ext.findClass
import org.utbot.jacodb.api.ext.findFieldsUsedIn
import org.utbot.jacodb.api.ext.findMethodsUsedIn
import org.utbot.jacodb.impl.features.Usages

class DirectUsagesTest : BaseTest() {

    companion object : WithDB(Usages)

    @Test
    fun `find methods used in method`() {
        val usages = cp.methodsUsages<org.utbot.jacodb.impl.usages.direct.DirectA>()

        assertEquals(
            listOf(
                "<init>" to listOf("java.lang.Object#<init>"),
                "setCalled" to listOf(
                    "java.io.PrintStream#println",
                ),
                "newSmth" to listOf(
                    "com.google.common.collect.Lists#newArrayList",
                    "java.lang.Integer#valueOf",
                    "java.util.ArrayList#add",
                    "java.io.PrintStream#println",
                )
            ),
            usages
        )
    }

    @Test
    fun `find methods used in method with broken classpath`() {
        val cp = runBlocking {
            db.classpath(allClasspath - guavaLib)
        }
        cp.use {
            val usages = cp.methodsUsages<org.utbot.jacodb.impl.usages.direct.DirectA>()

            assertEquals(
                listOf(
                    "<init>" to listOf("java.lang.Object#<init>"),
                    "setCalled" to listOf(
                        "java.io.PrintStream#println",
                    ),
                    "newSmth" to listOf(
                        "java.lang.Integer#valueOf",
                        "java.util.ArrayList#add",
                        "java.io.PrintStream#println",
                    )
                ),
                usages
            )
        }
    }

    @Test
    fun `find fields used in method`() {
        val usages = cp.fieldsUsages<org.utbot.jacodb.impl.usages.direct.DirectA>()

        assertEquals(
            listOf(
                "<init>" to listOf(
                    "reads" to listOf(),
                    "writes" to listOf()
                ),
                "newSmth" to listOf(
                    "reads" to listOf(
                        "java.lang.System#out",
                        "org.utbot.jacodb.impl.usages.direct.DirectA#result",
                        "org.utbot.jacodb.impl.usages.direct.DirectA#called",
                    ),
                    "writes" to listOf(
                        "org.utbot.jacodb.impl.usages.direct.DirectA#result",
                        "org.utbot.jacodb.impl.usages.direct.DirectA#called",
                    )
                ),
                "setCalled" to listOf(
                    "reads" to listOf(
                        "java.lang.System#out",
                        "org.utbot.jacodb.impl.usages.direct.DirectA#called",
                    ),
                    "writes" to listOf(
                        "org.utbot.jacodb.impl.usages.direct.DirectA#called",
                    )
                )
            ),
            usages
        )
    }

    private inline fun <reified T> JcClasspath.fieldsUsages(): List<Pair<String, List<Pair<String, List<String>>>>> {
        return runBlocking {
            val classId = cp.findClass<T>()

            classId.declaredMethods.map {
                val usages = findFieldsUsedIn(it)
                it.name to listOf(
                    "reads" to usages.reads.map { it.enclosingClass.name + "#" + it.name },
                    "writes" to usages.writes.map { it.enclosingClass.name + "#" + it.name }
                )
            }
                .toMap()
                .filterNot { it.value.isEmpty() }
                .toSortedMap().toList()
        }
    }

    private inline fun <reified T> JcClasspath.methodsUsages(): List<Pair<String, List<String>>> {
        return runBlocking {
            val jcClass = cp.findClass<T>()

            val methods = jcClass.declaredMethods

            methods.map {
                it.name to findMethodsUsedIn(it).map { it.enclosingClass.name + "#" + it.name }.toImmutableList()
            }.filterNot { it.second.isEmpty() }
        }
    }

    @AfterEach
    fun cleanup() {
        cp.close()
    }

}