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

package org.jacodb.api.net.ilinstances.impl

import org.jacodb.api.net.ilinstances.IlField
import org.jacodb.api.net.IlPublication
import org.jacodb.api.net.generated.models.IlFieldDto
import org.jacodb.api.net.ilinstances.IlType

class IlFieldImpl(override val declaringType: IlType, private val dto: IlFieldDto, typeLoader: IlPublication) :
    IlField {
    override val isConstructed: Boolean = dto.isConstructed
    override val fieldType: IlType by lazy { typeLoader.findIlTypeOrNull(dto.fieldType)!! }
    override val attributes: MutableList<IlAttributeImpl> = mutableListOf()
    override val isStatic: Boolean = dto.isStatic
    override val name: String = dto.name
    override fun toString(): String {
        return if (isStatic) "$declaringType.$name" else "$.$name"
    }
}
