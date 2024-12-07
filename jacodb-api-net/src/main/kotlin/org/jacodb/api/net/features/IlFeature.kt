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

import org.jacodb.api.net.IlDatabase
import org.jacodb.api.net.IlPublication

interface IlFeature<Request, Response> {
    suspend fun query(publication: IlPublication, request: Request): Sequence<Response>
    fun onSignal(signal: IlSignal)
}

sealed class IlSignal(db: IlDatabase) {
    class BeforeIndexing(val db: IlDatabase) : IlSignal(db)
}

suspend fun <Request, Response> IlPublication.query(
    feature: IlFeature<Request, Response>,
    req: Request
): Sequence<Response> {
    return feature.query(this, req)
}
