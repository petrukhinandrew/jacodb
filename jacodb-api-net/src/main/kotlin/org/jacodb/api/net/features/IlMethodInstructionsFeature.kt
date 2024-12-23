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

package org.jacodb.api.net.features

import org.jacodb.api.net.IlInstExtFeature
import org.jacodb.api.net.IlMethodExtFeature
import org.jacodb.api.net.IlMethodExtFeature.IlInstListResult
import org.jacodb.api.net.cfg.IlGraphImpl
import org.jacodb.api.net.ilinstances.IlMethod
import org.jacodb.api.net.ilinstances.IlStmt
import org.jacodb.api.net.ilinstances.impl.IlStmtLocationImpl

class IlMethodInstructionsFeature : IlMethodExtFeature {
    private val IlMethod.methodFeatures
        get() = declaringType.publication.features.filterIsInstance<IlInstExtFeature>()

    override fun instList(method: IlMethod): IlInstListResult {
        val insts = method.rawInstList.mapIndexed { index, inst ->
            IlStmt.deserialize(
                IlStmtLocationImpl(method, index),
                method,
                inst
            )
        }
        return IlInstListResult(method, method.methodFeatures.fold(insts) { value, feature ->
            feature.transformInstList(method, value)
        })
    }

    override fun flowGraph(method: IlMethod): IlMethodExtFeature.IlFlowGraphResult {
        return IlMethodExtFeature.IlFlowGraphResult(method, IlGraphImpl(method, method.instList))
    }
}