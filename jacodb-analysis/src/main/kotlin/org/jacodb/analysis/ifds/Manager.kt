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

package org.jacodb.analysis.ifds

import kotlinx.coroutines.CoroutineScope
import org.jacodb.api.common.CommonMethod
import org.jacodb.api.common.cfg.CommonInst
import org.jacodb.api.jvm.JcMethod

interface Manager<out Fact, in Event, out Method, out Statement>
    where Method : CommonMethod<Method, Statement>,
          Statement : CommonInst<Method, Statement> {

    fun handleEvent(event: Event)

    fun handleControlEvent(event: ControlEvent)

    fun subscribeOnSummaryEdges(
        method: @UnsafeVariance Method,
        scope: CoroutineScope,
        handler: (Edge<Fact, Method, Statement>) -> Unit,
    )
}

sealed interface ControlEvent

data class QueueEmptinessChanged(
    val runner: Runner<*, *, *>,
    val isEmpty: Boolean,
) : ControlEvent