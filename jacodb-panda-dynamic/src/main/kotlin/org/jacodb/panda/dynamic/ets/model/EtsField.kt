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

package org.jacodb.panda.dynamic.ets.model

import org.jacodb.panda.dynamic.ets.base.EtsEntity
import org.jacodb.panda.dynamic.ets.base.EtsType

// for '!' field marker ("definitely assigned field"), see https://www.typescriptlang.org/docs/handbook/2/classes.html#--strictpropertyinitialization

interface EtsField {
    val enclosingClass: EtsClass?
    val signature: EtsFieldSignature

    val name: String
        get() = signature.sub.name

    val type: EtsType
        get() = signature.sub.type
}

class EtsFieldImpl(
    override val signature: EtsFieldSignature,
    val accessFlags: AccessFlags = AccessFlags(),
    val decorators: List<EtsDecorator> = emptyList(),
    val isOptional: Boolean = false,  // '?'
    val isDefinitelyAssigned: Boolean = false, // '!'
    val initializer: EtsEntity? = null,
) : EtsField {

    override var enclosingClass: EtsClass? = null

    override fun toString(): String {
        return signature.toString()
    }
}

data class AccessFlags(
    var isStatic: Boolean = false,
    var isPublic: Boolean = false,
    var isPrivate: Boolean = false,
    var isProtected: Boolean = false,
    var isReadOnly: Boolean = false,
)

data class EtsDecorator(
    val name: String,
    // TODO: args...
)