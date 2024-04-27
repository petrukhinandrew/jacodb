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

package org.jacodb.analysis.npe

import org.jacodb.analysis.ifds.AccessPath
import org.jacodb.analysis.ifds.minus
import org.jacodb.analysis.util.startsWith
import org.jacodb.analysis.util.values
import org.jacodb.api.common.CommonMethod
import org.jacodb.api.common.CommonMethodParameter
import org.jacodb.analysis.util.Traits
import org.jacodb.api.common.CommonProject
import org.jacodb.api.common.cfg.CommonCallExpr
import org.jacodb.api.common.cfg.CommonExpr
import org.jacodb.api.common.cfg.CommonInst
import org.jacodb.api.common.cfg.CommonValue
import org.jacodb.api.jvm.cfg.JcInstanceCallExpr
import org.jacodb.api.jvm.cfg.JcLengthExpr

context(Traits<CommonProject, CommonMethod<*, *>, CommonInst<*, *>, CommonValue, CommonExpr, CommonCallExpr, CommonMethodParameter>)
internal fun AccessPath?.isDereferencedAt(expr: CommonExpr): Boolean {
    if (this == null) {
        return false
    }

    if (expr is JcInstanceCallExpr) {
        val instancePath = expr.instance.toPathOrNull()
        if (instancePath.startsWith(this)) {
            return true
        }
    }

    if (expr is JcLengthExpr) {
        val arrayPath = expr.array.toPathOrNull()
        if (arrayPath.startsWith(this)) {
            return true
        }
    }

    return expr.values
        .mapNotNull { it.toPathOrNull() }
        .any {
            (it - this)?.isNotEmpty() == true
        }
}

context(Traits<CommonProject, CommonMethod<*, *>, CommonInst<*, *>, CommonValue, CommonExpr, CommonCallExpr, CommonMethodParameter>)
internal fun AccessPath?.isDereferencedAt(inst: CommonInst<*, *>): Boolean {
    if (this == null) return false
    return inst.operands.any { isDereferencedAt(it) }
}
