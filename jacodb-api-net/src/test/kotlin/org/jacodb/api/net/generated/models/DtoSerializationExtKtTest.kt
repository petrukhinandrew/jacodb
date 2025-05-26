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



import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertIs

class DtoSerializationExtKtTest {
    @Test
    fun validateSerializationOfIlPointerTypeDto() {
        val pointerType = IlPointerTypeDto(
            targetType = TypeId(asmName = "targetAsm", typeName = "targetType", typeArgs = emptyList()),
            asmName = "asmName",
            namespaceName = "namespaceName",
            name = "pointerTypeName",
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
        val bytes = pointerType.getBytes()
        val deserializedType = bytes.getIlTypeDto()
        assertIs<IlPointerTypeDto>(deserializedType)
        assertEquals(pointerType.asmName, deserializedType.asmName)
        assertEquals(pointerType.namespaceName, deserializedType.namespaceName)
    }

    @Test
    fun validateSerializationOfIlStructTypeDto() {
        val field1 = IlFieldDto(
            fieldType = TypeId(
                asmName = "System. String",
                typeName = "String",
                typeArgs = emptyList()
            ),
            isStatic = false,
            name = "field1",
            attrs = listOf(),
            isConstructed = true,
            offset = 0
        )

        val field2 = IlFieldDto(
            fieldType = TypeId(
                asmName = "System. Int32",
                typeName = "Int",
                typeArgs = emptyList()
            ),
            isStatic = true,
            name = "field2",
            attrs = listOf(),
            isConstructed = true,
            offset = 0
        )

        val method1 = IlMethodDto(
            returnType = TypeId(
                asmName = "System. Void",
                typeName = "Void",
                typeArgs = emptyList()
            ),
            attrs = listOf(),
            name = "method1",
            parameters = listOf(
                IlParameterDto(
                    name = "param1",
                    type = TypeId(asmName = "System. String", typeName = "String", typeArgs = emptyList()),
                    index = 0,
                    defaultValue = null,
                    attrs = listOf()
                )
            ),
            resolved = true,
            locals = listOf(),
            temps = listOf(),
            errs = listOf(),
            ehScopes = listOf(),
            rawInstList = listOf(
                IlAssignStmtDto(
                    lhv = IlVarAccessDto(
                        kind = IlVarKind.local,
                        type = TypeId(emptyList(), "", ""),
                        index = 1
                    ),
                    rhv = IlArrayConstDto(
                        values = listOf('1', '2', '3').map {
                            IlCharConstDto(
                                it,
                                type = TypeId(emptyList(), "char", "char")
                            )
                        },
                        type = TypeId(emptyList(), "charArr", "charArr")
                    ),
                    fileLineIdx = null
                )
            ),
            isStatic = false,
            isConstructed = true,
            isGeneric = false,
            isGenericDefinition = false,
            signature = "",
            genericArgs = listOf(),
            baseMethod = null,
            isVirtual = false,
            isAbstract = false,
            filePath = null
        )

        val method2 = IlMethodDto(
            returnType = TypeId(
                asmName = "System. Int32",
                typeName = "Int",
                typeArgs = emptyList()
            ),
            attrs = listOf(),
            name = "method2",
            parameters = listOf(
                IlParameterDto(
                    name = "param1",
                    type = TypeId(asmName = "System. Int32", typeName = "Int", typeArgs = emptyList()),
                    index = 0,
                    defaultValue = null,
                    attrs = listOf()
                ),
                IlParameterDto(
                    name = "param2",
                    type = TypeId(asmName = "System. Int32", typeName = "Int", typeArgs = emptyList()),
                    index = 1,
                    defaultValue = null,
                    attrs = listOf()
                )
            ),
            resolved = false,
            locals = listOf(),
            temps = listOf(),
            errs = listOf(),
            ehScopes = listOf(),
            isStatic = false,
            rawInstList = listOf(),
            isConstructed = true,
            isGeneric = false,
            isGenericDefinition = false,
            signature = "TODO()",
            genericArgs = listOf(),
            baseMethod = null,
            isVirtual = false,
            isAbstract = false,
            filePath = null
        )

        val structType = IlStructTypeDto(
            asmName = "asmName",
            namespaceName = "namespaceName",
            name = "structTypeName",
            declType = null,
            genericArgs = listOf(),
            isGenericParam = false,
            isValueType = true,
            isManaged = false,
            attrs = listOf(),
            fields = listOf(field1, field2),
            methods = listOf(method1, method2),
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

        val bytes = structType.getBytes()
        val deserializedType = bytes.getIlTypeDto()

        assertIs<IlStructTypeDto>(deserializedType)
        assertEquals(structType.asmName, deserializedType.asmName)
        assertEquals(structType.namespaceName, deserializedType.namespaceName)

        assertEquals(2, deserializedType.fields.size)
        assertEquals("field1", deserializedType.fields[0].name)
        assertEquals("field2", deserializedType.fields[1].name)

        assertEquals(2, deserializedType.methods.size)
        assertEquals("method1", deserializedType.methods[0].name)
        assertEquals("method2", deserializedType.methods[1].name)
    }

    @Test
    fun validateSerializationOfIlArrayTypeDto() {
        val arrayType = IlArrayTypeDto(
            elementType = TypeId(asmName = "elementAsm", typeName = "elementType", typeArgs = emptyList()),
            asmName = "asmName",
            namespaceName = "namespaceName",
            name = "arrayTypeName",
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
        val bytes = arrayType.getBytes()
        val deserializedType = bytes.getIlTypeDto()
        assertIs<IlArrayTypeDto>(deserializedType)
        assertEquals(arrayType.asmName, deserializedType.asmName)
        assertEquals(arrayType.namespaceName, deserializedType.namespaceName)
    }

    @Test
    fun `verify serialization and deserialization of IlPrimitiveTypeDto`() {
        val primitiveType = IlPrimitiveTypeDto(
            asmName = "primitiveAsm",
            moduleToken = 123,
            typeToken = 456,
            namespaceName = "primitiveNamespace",
            size = 4,
            name = "primitiveName",
            fullname = "primitiveFullname",
            isConstructed = true,
            declType = null,
            baseType = null,
            interfaces = emptyList(),
            genericArgs = emptyList(),
            isInterface = false,
            isAbstract = false,
            isGenericType = false,
            genericParameterConstraints = emptyList(),
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
            attrs = emptyList(),
            fields = emptyList(),
            methods = emptyList()
        )

        val bytes = primitiveType.getBytes()
        val deserializedType = bytes.getIlTypeDto()

        assertIs<IlPrimitiveTypeDto>(deserializedType)
        assertEquals(primitiveType.asmName, deserializedType.asmName)
        assertEquals(primitiveType.namespaceName, deserializedType.namespaceName)
    }

    @Test
    fun `verify serialization and deserialization of IlEnumTypeDto`() {
        val underlyingType = TypeId(emptyList(), "enumAsm", "enumType")
        val enumType = IlEnumTypeDto(
            underlyingType = underlyingType,
            names = listOf("VALUE1", "VALUE2"),
            values = listOf(
                IlCharConstDto('A', underlyingType),
                IlCharConstDto('B', underlyingType)
            ),
            asmName = "enumAsm",
            moduleToken = 789,
            typeToken = 101,
            namespaceName = "enumNamespace",
            size = 8,
            name = "enumName",
            fullname = "enumFullname",
            isConstructed = true,
            declType = null,
            baseType = null,
            interfaces = emptyList(),
            genericArgs = emptyList(),
            isInterface = false,
            isAbstract = false,
            isGenericType = false,
            genericParameterConstraints = emptyList(),
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
            attrs = emptyList(),
            fields = emptyList(),
            methods = emptyList()
        )

        val bytes = enumType.getBytes()
        val deserializedType = bytes.getIlTypeDto()

        assertIs<IlEnumTypeDto>(deserializedType)
        assertEquals(enumType.underlyingType, deserializedType.underlyingType)
        assertEquals(enumType.names, deserializedType.names)
        assertEquals(enumType.values, deserializedType.values)
    }

    @Test
    fun `verify serialization and deserialization of IlClassTypeDto`() {
        val field = IlFieldDto(
            fieldType = TypeId(emptyList(), "fieldAsm", "fieldType"),
            isStatic = false,
            name = "fieldName",
            attrs = emptyList(),
            isConstructed = true,
            offset = 0
        )

        val method = IlMethodDto(
            returnType = TypeId(emptyList(), "methodAsm", "methodType"),
            attrs = emptyList(),
            isStatic = false,
            isGeneric = false,
            isGenericDefinition = false,
            signature = "methodSignature",
            name = "methodName",
            parameters = emptyList(),
            genericArgs = emptyList(),
            resolved = true,
            locals = emptyList(),
            temps = emptyList(),
            errs = emptyList(),
            ehScopes = emptyList(),
            rawInstList = emptyList(),
            isConstructed = true,
            isVirtual = false,
            isAbstract = false,
            baseMethod = null,
            filePath = null
        )

        val classType = IlClassTypeDto(
            asmName = "classAsm",
            moduleToken = 111,
            typeToken = 222,
            namespaceName = "classNamespace",
            size = 16,
            name = "className",
            fullname = "classFullname",
            isConstructed = true,
            declType = null,
            baseType = null,
            interfaces = emptyList(),
            genericArgs = emptyList(),
            isInterface = false,
            isAbstract = false,
            isGenericType = false,
            genericParameterConstraints = emptyList(),
            isGenericParam = false,
            isGenericDefinition = false,
            genericDefinition = null,
            isCovariant = false,
            isContravariant = false,
            hasRefTypeConstraint = false,
            hasNotNullValueTypeConstraint = false,
            hasDefaultCtorConstraint = false,
            isValueType = false,
            isManaged = true,
            attrs = emptyList(),
            fields = listOf(field),
            methods = listOf(method)
        )

        val bytes = classType.getBytes()
        val deserializedType = bytes.getIlTypeDto()

        assertIs<IlClassTypeDto>(deserializedType)
        assertEquals(classType.fields, deserializedType.fields)
        assertEquals(classType.methods, deserializedType.methods)
        assertEquals(classType.attrs, deserializedType.attrs)
    }

    @Test
    fun `verify handling of unexpected byte array during deserialization`() {
        val unexpectedBytes = byteArrayOf(99)
        val exception = assertFailsWith<DtoDeserializationException> {
            unexpectedBytes.getIlTypeDto()
        }
        assertEquals("Unexpected bytearray", exception.message)
    }
}
