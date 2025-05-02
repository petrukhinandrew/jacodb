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

package org.jacodb.api.net.ilinstances.ext

import org.jacodb.api.net.generated.models.TypeId


fun TypeId.withTypeName(transformation: (String) -> String): TypeId = TypeId(typeArgs, asmName, transformation(typeName))

@Suppress("unchecked_cast")
fun TypeId.with(
    typeNameTransformation: ((String) -> String)? = null,
    typeArgsTransformation: ((List<TypeId>) -> List<TypeId>)? = null
): TypeId {
    val newTypeArgs = typeArgsTransformation?.invoke(typeArgs as List<TypeId>) ?: typeArgs
    val newTypeName = typeNameTransformation?.invoke(typeName) ?: typeName
    return TypeId(newTypeArgs, asmName, newTypeName)
}