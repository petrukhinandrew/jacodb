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

package org.example.ilinstances

import org.jacodb.api.net.devmocs.IlClasspathMock
import org.jacodb.api.net.generated.models.IlFieldDto
import org.jacodb.api.net.ilinstances.IlAttribute

class IlField(private val declType: IlType, private val dto: IlFieldDto, classpath: IlClasspathMock) : IlInstance {
    val fieldType: IlType by lazy { classpath.findType(dto.fieldType)!! }
    val attributes: MutableList<IlAttribute> = mutableListOf()
    val isStatic: Boolean = dto.isStatic
    val name: String = dto.name
    override fun toString(): String {
        return if (isStatic) "$declType.$name" else "$.$name"
    }
}