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

package org.jacodb.api.net.publication
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


import org.jacodb.api.net.TestDllServer
import org.jacodb.api.net.generated.models.TypeId
import org.jacodb.api.net.ilinstances.IlAttribute
import org.jacodb.api.net.ilinstances.IlMethod
import org.jacodb.api.net.ilinstances.IlParameter
import org.jacodb.api.net.ilinstances.IlType
import org.jacodb.api.net.ilinstances.ext.getOverridingMethods
import org.jacodb.api.net.ilinstances.ext.isOverriding
import org.jacodb.api.net.ilinstances.ext.isReturnTypeCovarianceAgnosticOverrideOf
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class OverridingMethodsTest {
    companion object {
        val env = TestDllServer.freshEnv()
        val server get() = env.first
        val publication get() = env.second
        val testAsm = publication.findAsmNameByLocationOrNull(publication.targetAsmLocations[0])!!

        @JvmStatic
        @AfterAll
        fun dispatch() {
            server.close()
        }
    }

    @Test
    fun inherited() {
        val parent = publication.findIlTypeOrNull(
            TypeId(
                listOf(),
                testAsm,
                "TACBuilder.Tests.InMemoryIlHierarchy.Simple+Base"
            )
        )
        assertNotNull(parent)
        val child = publication.findIlTypeOrNull(
            TypeId(
                listOf(),
                testAsm,
                "TACBuilder.Tests.InMemoryIlHierarchy.Simple+DirectChild"
            )
        )
        assertNotNull(child)
        val parentMethod = parent.methods.single { it.name == "VirtualMethod" }
        val overridings = parentMethod.getOverridingMethods(publication)
        val childMethod = child.methods.single { it.name == "VirtualMethod" }
        assertEquals(2, overridings.size)
        assertContains(overridings, childMethod)
    }

    @Test
    fun interfaceImpl() {
        val parent = publication.findIlTypeOrNull(
            TypeId(
                listOf(),
                testAsm,
                "SlavaCases+IVuln"
            )
        )
        assertNotNull(parent)
        val child = publication.findIlTypeOrNull(
            TypeId(
                listOf(),
                testAsm,
                "SlavaCases+Vuln"
            )
        )
        assertNotNull(child)
        val parentMethod = parent.methods.single { it.name == "Get" }
        val overridings = parentMethod.getOverridingMethods(publication)
        val childMethod = child.methods.single { it.name == "Get" }
        assertEquals(1, overridings.size)
        assertContains(overridings, childMethod)
    }

    @Test
    fun callSiteArgumentTypeTest() {
        val callsiteClass = publication.findIlTypeOrNull(
            TypeId(
                listOf(),
                testAsm,
                "TACBuilder.Tests.Misc.CallsiteClassResolve"
            )
        )!!
        val requiredCall = callsiteClass.methods.firstOrNull { it.name == "Resolve" }
        assertNotNull(requiredCall)
    }

    @Test
    fun `verify method with different return type is not considered an override`() {
        val baseMethod = Mockito.mock(IlMethod::class.java)
        Mockito.`when`(baseMethod.name).thenReturn("BaseMethod")
        Mockito.`when`(baseMethod.isVirtual).thenReturn(true)
        Mockito.`when`(baseMethod.attributes).thenReturn(emptyList())

        val derivedMethod = Mockito.mock(IlMethod::class.java)
        Mockito.`when`(derivedMethod.name).thenReturn("DerivedMethod")
        Mockito.`when`(derivedMethod.isVirtual).thenReturn(true)
        Mockito.`when`(derivedMethod.attributes).thenReturn(emptyList())

        val isOverride = derivedMethod.isOverriding(baseMethod)
        assertFalse(isOverride)
    }

    @Test
    fun `verify method with matching parameters is considered an override`() {
        val pt1 = Mockito.mock<IlType>()
        Mockito.`when`(pt1.name).thenReturn("a")
        val pt2 = Mockito.mock<IlType>()
        Mockito.`when`(pt2.name).thenReturn("b")
        val rt = Mockito.mock<IlType>()
        val baseMethod = Mockito.spy(IlMethod::class.java)
        Mockito.`when`(baseMethod.name).thenReturn("DerivedMethod")
        Mockito.`when`(baseMethod.isVirtual).thenReturn(true)
        Mockito.`when`(baseMethod.returnType).thenReturn(rt)
        val fp1 = Mockito.mock<IlParameter>()
        Mockito.`when`(fp1.type).thenReturn(pt1)
        val fp2 = Mockito.mock<IlParameter>()
        Mockito.`when`(fp2.type).thenReturn(pt2)
        Mockito.`when`(baseMethod.parameters).thenReturn(listOf(fp1, fp2))

        val derivedMethod = Mockito.spy(IlMethod::class.java)
        Mockito.`when`(derivedMethod.name).thenReturn("DerivedMethod")
        Mockito.`when`(derivedMethod.isVirtual).thenReturn(true)
        Mockito.`when`(derivedMethod.returnType).thenReturn(rt)
        val attr = Mockito.mock<IlAttribute>()
        val attrType = Mockito.mock<IlType>()
        Mockito.`when`(attrType.name).thenReturn("System.Runtime.CompilerServices.PreserveBaseOverridesAttribute")
        Mockito.`when`(attr.type).thenReturn(attrType)
        Mockito.`when`(baseMethod.attributes).thenReturn(listOf(attr))
        val sp1 = Mockito.mock<IlParameter>()
        Mockito.`when`(sp1.type).thenReturn(pt1)
        val sp2 = Mockito.mock<IlParameter>()
        Mockito.`when`(sp2.type).thenReturn(pt2)
        Mockito.`when`(derivedMethod.parameters).thenReturn(listOf(sp1, sp2))
        val isOverride = derivedMethod.isReturnTypeCovarianceAgnosticOverrideOf(baseMethod)
        assertTrue(isOverride)
    }
}