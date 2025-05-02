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
import org.jacodb.api.net.ilinstances.IlType
import org.jacodb.api.net.ilinstances.impl.IlArrayType
import org.jacodb.api.net.ilinstances.impl.IlPointerType

fun IlType.isAssignableTo(type: IlType): Boolean = type.isAssignableFrom(this)

fun IlType.isAssignableFrom(type: IlType): Boolean = when {
    type == this -> {
        true
    }

    type.isSubclassOf(this) -> {
        true
    }

    this.isInterface -> {
        type.implementInterface(this)
    }

    this.isGenericParameter -> {
        this.genericParameterConstraints.all { constraint -> constraint.isAssignableFrom(type) }
    }

    else -> {
        false
    }
}

fun IlType.implementInterface(interfaceType: IlType): Boolean {
    var t: IlType? = this

    while (t != null) {
        val interfaces = t.interfaces

        if (interfaces.any { i -> i == interfaceType || i.implementInterface(interfaceType) })
            return true

        t = t.baseType
    }
    return false
}

fun IlType.isSubclassOf(type: IlType): Boolean {
    var p: IlType? = this

    if (p == type) return false

    while (p != null) {
        p = p.baseType
        if (p == type) return true
    }

    return false
}

fun IlType.makeArrayType(): IlArrayType =
    publication.findIlType(id.withTypeName { typeName -> "$typeName[]" }) as IlArrayType

fun IlType.makePointerType(): IlPointerType =
    publication.findIlType(id.withTypeName { typeName -> "$typeName*" }) as IlPointerType

fun IlType.makeByRefType(): IlPointerType =
    publication.findIlType(id.withTypeName { typeName -> "$typeName&" }) as IlPointerType

fun IlType.makeGenericType(subst: List<IlType>): IlType {
    check(this.isGenericDefinition) {
        "makeGenericType only allowed on type definition"
    }
    check(this.genericArgs.size == subst.size) {
        "inconsistent number of generic arguments"
    }
    check(this.genericArgs.zip(subst).all { (param, arg) -> arg.isAssignableTo(param) }) {
        "cannot use arg in substitution"
    }
    return publication.findIlType(TypeId(subst.map { it.id }, this.asmName, this.fullname))
}
