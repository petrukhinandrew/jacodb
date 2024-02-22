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

import org.jacodb.api.common.CommonMethod
import org.jacodb.api.common.cfg.CommonInst

data class Edge<out Fact, out Method, out Statement>(
    val from: Vertex<Fact, Method, Statement>,
    val to: Vertex<Fact, Method, Statement>,
) where Method : CommonMethod<Method, Statement>,
        Statement : CommonInst<Method, Statement> {

    init {
        require(from.method == to.method)
    }

    val method: Method
        get() = from.method
}