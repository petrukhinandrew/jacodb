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

package org.jacodb.api.net.features

import kotlinx.coroutines.runBlocking
import org.jacodb.api.net.TestDllServer
import org.jacodb.api.net.generated.models.TypeId
import org.jacodb.api.net.publication.IlPredefinedAsmExt.mscorelib
import org.jacodb.api.net.publication.IlPredefinedTypeExt.int32
import org.jacodb.api.net.publication.IlPredefinedTypeExt.string
import org.jacodb.api.net.storage.TypeIdExt
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class InMemoryHierarchyManualTest {

    companion object {
        val env = TestDllServer.freshEnv()
        val server get() = env.first
        val publication get() = env.second

        @JvmStatic
        @AfterAll
        fun dispatch() {
            server.close()
        }
    }

    @Test
    fun verifyClassesWithReferenceTypeConstraint() {
        val refTypeTemplate = TypeId(
            listOf(TypeIdExt.emptyTypeId()),
            publication.findAsmNameByLocationOrNull(TestDllServer.testDllPath)!!,
            "TACBuilder.Tests.InMemoryIlHierarchy.RefTypeTestBase`1"
        )

        val refTypeRequest = InMemoryIlHierarchyReq(
            TypeId(listOf(publication.string().id), refTypeTemplate.asmName, refTypeTemplate.typeName)
        )

        val response = runBlocking { InMemoryIlHierarchy.query(publication, refTypeRequest).toList() }
        assertEquals(1, response.size)
        assertEquals("RefTypeClass", response.first().name)

        val valueTypeRequest = InMemoryIlHierarchyReq(
            TypeId(listOf(publication.int32().id), refTypeTemplate.asmName, refTypeTemplate.typeName)
        )

        val valueTypeResponse = runBlocking { InMemoryIlHierarchy.query(publication, valueTypeRequest).toList() }
        assertTrue(valueTypeResponse.isEmpty())
    }

    @Test
    fun verifyClassesWithNotNullValueTypeConstraint() {
        val notNullValueTypeTemplate = TypeId(
            listOf(TypeIdExt.emptyTypeId()),
            publication.findAsmNameByLocationOrNull(TestDllServer.testDllPath)!!,
            "TACBuilder.Tests.InMemoryIlHierarchy.NotNullValueTypeTestBase`1"
        )

        val notNullValueTypeRequest = InMemoryIlHierarchyReq(
            TypeId(
                listOf(publication.int32().id),
                notNullValueTypeTemplate.asmName,
                notNullValueTypeTemplate.typeName
            )
        )

        val response = runBlocking { InMemoryIlHierarchy.query(publication, notNullValueTypeRequest).toList() }
        assertEquals(1, response.size)
        assertEquals("NotNullValueTypeClass", response.first().name)

        val nullableTypeRequest = InMemoryIlHierarchyReq(
            TypeId(
                listOf(publication.string().id),
                notNullValueTypeTemplate.asmName,
                notNullValueTypeTemplate.typeName
            )
        )

        val nullableTypeResponse = runBlocking { InMemoryIlHierarchy.query(publication, nullableTypeRequest).toList() }
        assertTrue(nullableTypeResponse.isEmpty())
    }

    @Test
    fun verifyRetrievalOfPredefinedTypes() {
        val intType = publication.int32()
        assertEquals("System.Int32", intType.typeName)
        assertEquals(publication.mscorelib(), intType.asmName)

        val stringType = publication.string()
        assertEquals("System.String", stringType.typeName)
        assertEquals(publication.mscorelib(), stringType.asmName)
    }

    @Test
    fun verifyClassesWithMultipleGenericArguments() {
        val multiParamTemplate = TypeId(
            listOf(TypeIdExt.emptyTypeId(), TypeIdExt.emptyTypeId()),
            publication.findAsmNameByLocationOrNull(TestDllServer.testDllPath)!!,
            "TACBuilder.Tests.InMemoryIlHierarchy.MultiParamBase`2"
        )
        val reqTypeId = TypeId(
            listOf(publication.int32().id, publication.string().id),
            multiParamTemplate.asmName,
            multiParamTemplate.typeName
        )
        val multiParamRequest = InMemoryIlHierarchyReq(
            reqTypeId
        )
        val response = runBlocking { InMemoryIlHierarchy.query(publication, multiParamRequest).toList() }
        Assertions.assertFalse(response.isEmpty())
        val r = response.singleOrNull()
        assertTrue(r != null)
        assertTrue(r.name == "MultiParamImpl")
    }
}