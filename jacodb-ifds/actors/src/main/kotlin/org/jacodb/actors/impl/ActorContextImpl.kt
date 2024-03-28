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

package org.jacodb.actors.impl

import mu.KLogger
import org.jacodb.actors.api.ActorContext
import org.jacodb.actors.api.ActorRef
import org.jacodb.actors.api.ActorSpawner
import org.jacodb.actors.api.Factory
import org.jacodb.actors.impl.workers.ActorWorker
import kotlin.coroutines.CoroutineContext

internal class ActorContextImpl<M>(
    private val spawner: ActorSpawner,
    private val worker: ActorWorker<M>,
    override val logger: KLogger,
) : ActorContext<M>, ActorSpawner by spawner {

    override val self: ActorRef<M>
        get() = worker.self

    fun launch(
        coroutineContext: CoroutineContext,
        factory: Factory<M>,
    ) {
        val actor = factory.run { create() }
        worker.launchLoop(coroutineContext, actor)
    }

    override suspend fun <TargetMessage> ActorRef<TargetMessage>.send(message: TargetMessage) {
        worker.send(this as ActorRefImpl<TargetMessage>, message)
    }

    override fun stop() {
        TODO("Not yet implemented")
    }

    override fun stopChild(name: String) {
        TODO("Not yet implemented")
    }
}