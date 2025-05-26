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

package org.jacodb.api.net.generated.models
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


import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertIs

class DtoByteConversionIdentity {
    @Test
    fun checkPrimitiveType() {
        val primitiveType = IlPrimitiveTypeDto(
            asmName = "lol",
            namespaceName = "kek",
            name = "",
            declType = TypeId(asmName = "type", typeName = "id", typeArgs = emptyList()),
            genericArgs = listOf(),
            isGenericParam = false,
            isValueType = true,
            isManaged = false,
            attrs = listOf(),
            fields = listOf(),
            methods = listOf(),
            moduleToken = 0,
            typeToken = 1,
            fullname = "lolkek",
            baseType = null,
            interfaces = emptyList(),
            isGenericDefinition = false,
            isConstructed = true,
            isGenericType = false,
            genericDefinition = null,
            isCovariant = false,
            isContravariant = false,
            hasRefTypeConstraint = false,
            hasNotNullValueTypeConstraint = false,
            hasDefaultCtorConstraint = false,
            size = 0,
            isInterface = false,
            isAbstract = false,
            genericParameterConstraints = listOf()

        )
        val bytes = primitiveType.getBytes()
        val sameType = bytes.getIlTypeDto()
        assertIs<IlPrimitiveTypeDto>(sameType)
        assertEquals(primitiveType.asmName, sameType.asmName)
        assertEquals(primitiveType.namespaceName, sameType.namespaceName)
    }

    @Test
    fun checkEnumType() {
        val initialEnum = IlEnumTypeDto(
            underlyingType = TypeId(asmName = "lol", typeName = "kek", typeArgs = emptyList()),
            names = listOf("A", "B", "C"),
            values = listOf(1, 2, 3).map {
                IlInt32ConstDto(
                    it,
                    type = TypeId(asmName = "int", typeName = "int", typeArgs = emptyList())
                )
            }.toList(),
            asmName = "asm",
            namespaceName = "namespace",
            name = "name",
            declType = null,
            genericArgs = listOf(),
            isGenericParam = false,
            isValueType = true,
            isManaged = false,
            attrs = listOf(),
            fields = listOf(),
            methods = listOf(),
            moduleToken = 0,
            typeToken = 1,
            fullname = "lolkek",
            baseType = null,
            interfaces = emptyList(),
            isGenericDefinition = false,
            isConstructed = true,
            isGenericType = false,
            genericDefinition = null,
            isCovariant = false,
            isContravariant = false,
            hasRefTypeConstraint = false,
            hasNotNullValueTypeConstraint = false,
            hasDefaultCtorConstraint = false,
            size = 0,
            isInterface = false,
            isAbstract = false,
            genericParameterConstraints = listOf()
        )

        val bytes = initialEnum.getBytes()

        val resIlType = bytes.getIlTypeDto()
        assertIs<IlEnumTypeDto>(resIlType)
        assertEquals(initialEnum, resIlType)
    }

    @Test
    fun `verify deserialization of byte array with unexpected type ID throws exception`() {
        val invalidByteArray = byteArrayOf(99) // 99 is an invalid type ID
        val exception = assertThrows<DtoDeserializationException> {
            invalidByteArray.getIlTypeDto()
        }
        assertEquals("Unexpected bytearray", exception.message)
    }

    @Test
    fun `verify primitive type DTO with null base type can be serialized and deserialized correctly`() {
        val primitiveType = IlPrimitiveTypeDto(
            asmName = "testAsm",
            moduleToken = 123,
            typeToken = 456,
            namespaceName = "testNamespace",
            size = 10,
            name = "testName",
            fullname = "testFullname",
            isConstructed = true,
            declType = null,
            baseType = null,
            interfaces = listOf(),
            genericArgs = listOf(),
            isInterface = false,
            isAbstract = false,
            isGenericType = false,
            genericParameterConstraints = listOf(),
            isGenericParam = false,
            isGenericDefinition = false,
            genericDefinition = null,
            isCovariant = false,
            isContravariant = false,
            hasRefTypeConstraint = false,
            hasNotNullValueTypeConstraint = false,
            hasDefaultCtorConstraint = false,
            isValueType = true,
            isManaged = false,
            attrs = listOf(),
            fields = listOf(),
            methods = listOf()
        )

        val bytes = primitiveType.getBytes()
        val deserializedDto = bytes.getIlTypeDto() as IlPrimitiveTypeDto

        assertEquals(primitiveType.baseType, deserializedDto.baseType)
        assertEquals(primitiveType, deserializedDto)
    }

    @Test
    fun `verify primitive type DTO with multiple interfaces can be serialized and deserialized correctly`() {
        val interfaces = listOf(
            TypeId(listOf(), "interfaceAsm1", "interfaceName1"),
            TypeId(listOf(), "interfaceAsm2", "interfaceName2")
        )
        val primitiveType = IlPrimitiveTypeDto(
            asmName = "testAsm",
            moduleToken = 123,
            typeToken = 456,
            namespaceName = "testNamespace",
            size = 10,
            name = "testName",
            fullname = "testFullname",
            isConstructed = true,
            declType = null,
            baseType = null,
            interfaces = interfaces,
            genericArgs = listOf(),
            isInterface = false,
            isAbstract = false,
            isGenericType = false,
            genericParameterConstraints = listOf(),
            isGenericParam = false,
            isGenericDefinition = false,
            genericDefinition = null,
            isCovariant = false,
            isContravariant = false,
            hasRefTypeConstraint = false,
            hasNotNullValueTypeConstraint = false,
            hasDefaultCtorConstraint = false,
            isValueType = true,
            isManaged = false,
            attrs = listOf(),
            fields = listOf(),
            methods = listOf()
        )

        val bytes = primitiveType.getBytes()
        val deserializedDto = bytes.getIlTypeDto() as IlPrimitiveTypeDto

        assertEquals(primitiveType.interfaces, deserializedDto.interfaces)
    }

    @Test
    fun `verify primitive type DTO with generic arguments can be serialized and deserialized correctly`() {
        val genericArgs = listOf(
            TypeId(listOf(), "genericAsm1", "genericName1"),
            TypeId(listOf(), "genericAsm2", "genericName2")
        )
        val primitiveType = IlPrimitiveTypeDto(
            asmName = "testAsm",
            moduleToken = 123,
            typeToken = 456,
            namespaceName = "testNamespace",
            size = 10,
            name = "testName",
            fullname = "testFullname",
            isConstructed = true,
            declType = null,
            baseType = null,
            interfaces = listOf(),
            genericArgs = genericArgs,
            isInterface = false,
            isAbstract = false,
            isGenericType = true,
            genericParameterConstraints = listOf(),
            isGenericParam = false,
            isGenericDefinition = false,
            genericDefinition = null,
            isCovariant = false,
            isContravariant = false,
            hasRefTypeConstraint = false,
            hasNotNullValueTypeConstraint = false,
            hasDefaultCtorConstraint = false,
            isValueType = true,
            isManaged = false,
            attrs = listOf(),
            fields = listOf(),
            methods = listOf()
        )

        val bytes = primitiveType.getBytes()
        val deserializedDto = bytes.getIlTypeDto() as IlPrimitiveTypeDto

        assertEquals(primitiveType.genericArgs, deserializedDto.genericArgs)
    }

    @Test
    fun `verify primitive type DTO with attributes can be serialized and deserialized correctly`() {
        val attributes = listOf(
            IlAttrDto(
                attrType = TypeId(listOf(), "attrAsm1", "attrName1"),
                ctorArgs = listOf(),
                namedArgsNames = listOf(),
                namedArgsValues = listOf(),
                genericArgs = listOf()
            )
        )
        val primitiveType = IlPrimitiveTypeDto(
            asmName = "testAsm",
            moduleToken = 123,
            typeToken = 456,
            namespaceName = "testNamespace",
            size = 10,
            name = "testName",
            fullname = "testFullname",
            isConstructed = true,
            declType = null,
            baseType = null,
            interfaces = listOf(),
            genericArgs = listOf(),
            isInterface = false,
            isAbstract = false,
            isGenericType = false,
            genericParameterConstraints = listOf(),
            isGenericParam = false,
            isGenericDefinition = false,
            genericDefinition = null,
            isCovariant = false,
            isContravariant = false,
            hasRefTypeConstraint = false,
            hasNotNullValueTypeConstraint = false,
            hasDefaultCtorConstraint = false,
            isValueType = true,
            isManaged = false,
            attrs = attributes,
            fields = listOf(),
            methods = listOf()
        )

        val bytes = primitiveType.getBytes()
        val deserializedDto = bytes.getIlTypeDto() as IlPrimitiveTypeDto

        assertEquals(primitiveType.attrs, deserializedDto.attrs)
    }
}