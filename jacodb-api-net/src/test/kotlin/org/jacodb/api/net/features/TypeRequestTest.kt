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


import com.jetbrains.rd.util.lifetime.isAlive
import org.jacodb.api.net.TestDllServer
import org.jacodb.api.net.generated.models.TypeId
import org.jacodb.api.net.ilinstances.ext.makeByRefType
import org.jacodb.api.net.ilinstances.ext.makePointerType
import org.jacodb.api.net.ilinstances.impl.IlArrayType
import org.jacodb.api.net.ilinstances.impl.IlPointerType
import org.jacodb.api.net.publication.IlPredefinedAsmExt.mscorelib
import org.jacodb.api.net.publication.IlPredefinedTypeExt.int32
import org.jacodb.api.net.publication.IlPredefinedTypeExt.nuint
import org.jacodb.api.net.publication.IlPredefinedTypeExt.string
import org.jacodb.api.net.storage.TypeIdExt.emptyTypeId
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test
import kotlin.test.*

class TypeRequestTest {
    companion object {
        private val env = TestDllServer.freshEnv()
        val server get() = env.first
        val publication get() = env.second

        @JvmStatic
        @AfterAll
        fun dispatch() {
            server.close()
        }
    }

    @Test
    fun genericBaseRequests() {
        val request = TypeId(
            listOf(emptyTypeId()),
            publication.findAsmNameByLocationOrNull(TestDllServer.testDllPath)!!,
            "TACBuilder.Tests.InMemoryIlHierarchy.SingleParamBase`1"
        )
        val requestGenericDefn = publication.findIlTypeOrNull(
            request
        )
        val substs = listOf(publication.int32(), publication.string(), publication.nuint())
        substs.forEach {
            val response = publication.findIlTypeOrNull(
                TypeId(listOf(it.id), request.asmName, request.typeName)
            )
            assertNotNull(response, "value expected")
            assertEquals(it, response.genericArgs[0])
            assertEquals(response.genericDefinition, requestGenericDefn)
            val responsePtr = response.makePointerType()
            assertIs<IlPointerType>(responsePtr)
            assertFalse(responsePtr.isManaged)
            val responsePtrRef = responsePtr.makeByRefType()
            assertIs<IlPointerType>(responsePtrRef)
            assertTrue(responsePtrRef.isManaged)
            val selfArrSubst = publication.findIlTypeOrNull(
                TypeId(
                    listOf(TypeId(listOf(it.id), request.asmName, request.typeName)),
                    request.asmName,
                    request.typeName + "[]"
                )
            )

            assertNotNull(selfArrSubst)
            assertIs<IlArrayType>(selfArrSubst)
            assertEquals(selfArrSubst.elementType.genericDefinition, requestGenericDefn)

            val selfAsSubst = selfArrSubst.elementType
            assertNotNull(selfAsSubst, "value expected")
            assertEquals(it, selfAsSubst.genericArgs[0].genericArgs[0])
            assertEquals(selfAsSubst.genericDefinition, requestGenericDefn)
        }
    }

    @Test
    fun badRequestDoesNotFailBackend() {
        val request = TypeId(
            listOf(emptyTypeId()),
            publication.findAsmNameByLocationOrNull(TestDllServer.testDllPath)!!,
            "TACBuilder.Tests.InMemoryIlHierarchy.SingleParamBase`1"
        )
        val requestGenericDefn = publication.findIlTypeOrNull(
            request
        )
        assertNotNull(requestGenericDefn)
        val badSubst = TypeId(
            listOf(emptyTypeId()),
            publication.findAsmNameByLocationOrNull(TestDllServer.testDllPath)!!,
            "Type.That.Does.Not.Exist.In.Asm"
        )
        val substs = listOf(badSubst, badSubst, badSubst)
        substs.forEach {
            val response = publication.findIlTypeOrNull(
                TypeId(listOf(it), request.asmName, request.typeName)
            )
            assertNull(response)
            assertTrue(server.lifetime.isAlive)
        }
        Thread.sleep(1_000L)
        assertTrue(server.lifetime.isAlive)
    }

    @Test
    fun `verify system's ability to handle empty type requests`() {
        val emptyTypeId = emptyTypeId()
        val result = publication.findIlTypeOrNull(emptyTypeId)
        assertNull(result, "Expected no type to be found for an empty type request")
        assertTrue(server.lifetime.isAlive, "Server should remain operational")
    }

    @Test
    fun `ensure system correctly identifies predefined types`() {
        val int32Type = publication.int32()
        assertNotNull(int32Type, "Expected to find the type for System.Int32")
        assertFalse(int32Type.isManaged, "System.Int32 should not be managed")
        assertFalse(int32Type.isGenericType, "System.Int32 should not be a generic type")
    }

    @Test
    fun `verify system's ability to handle pointer type transformations`() {
        val typeId = TypeId(emptyList(), publication.mscorelib(), "System.Int32")
        val ilType = publication.findIlType(typeId)
        val pointerType = ilType.makePointerType()

        assertNotNull(pointerType, "Expected to find a corresponding pointer type")
        assertFalse(pointerType.isManaged, "Pointer type should not be managed")
        assertTrue(pointerType.id.typeName.endsWith("*"), "Pointer type name should end with an asterisk")
    }

    @Test
    fun `validate system's ability to handle reference type transformations`() {
        val typeId = TypeId(emptyList(), publication.mscorelib(), "System.Int32")
        val ilType = publication.findIlType(typeId)
        val referenceType = ilType.makeByRefType()

        assertNotNull(referenceType, "Expected to find a corresponding reference type")
        assertTrue(referenceType.isManaged, "Reference type should be managed")
        assertTrue(referenceType.id.typeName.endsWith("&"), "Reference type name should end with an ampersand")
    }

}