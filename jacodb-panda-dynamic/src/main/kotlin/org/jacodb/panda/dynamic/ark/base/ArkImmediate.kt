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

package org.jacodb.panda.dynamic.ark.base

import org.jacodb.api.common.cfg.CommonValue

interface ArkImmediate : ArkValue, CommonValue {
    interface Visitor<out R> : ArkConstant.Visitor<R> {
        fun visit(value: ArkLocal): R

        interface Default<out R> : Visitor<R>,
            ArkConstant.Visitor.Default<R> {

            override fun visit(value: ArkLocal): R = defaultVisit(value)

            override fun defaultVisit(value: ArkConstant): R = defaultVisit(value as ArkImmediate)

            fun defaultVisit(value: ArkImmediate): R
        }
    }

    override fun <R> accept(visitor: ArkEntity.Visitor<R>): R {
        return this.accept(visitor as Visitor<R>)
    }

    fun <R> accept(visitor: Visitor<R>): R
}