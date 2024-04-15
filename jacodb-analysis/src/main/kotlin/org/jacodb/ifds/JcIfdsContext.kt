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

package org.jacodb.ifds

import org.jacodb.actors.api.ActorRef
import org.jacodb.actors.api.ActorFactory
import org.jacodb.api.JcClasspath
import org.jacodb.api.analysis.JcApplicationGraph
import org.jacodb.api.cfg.JcInst
import org.jacodb.ifds.domain.Analyzer
import org.jacodb.ifds.domain.Chunk
import org.jacodb.ifds.domain.FlowFunction
import org.jacodb.ifds.domain.RunnerId
import org.jacodb.ifds.messages.RunnerMessage
import org.jacodb.impl.features.HierarchyExtensionImpl

class JcIfdsContext<Fact>(
    private val cp: JcClasspath,
    private val graph: JcApplicationGraph,
    private val bannedPackagePrefixes: List<String>,
    private val chunkStrategy: ChunkResolver,
    private val flowFunctionFactory: (RunnerId) -> FlowFunction<JcInst, Fact>,
) : IfdsContext<JcInst> {
    override fun chunkByMessage(message: RunnerMessage): Chunk =
        chunkStrategy.chunkByMessage(message)

    override fun runnerIdByMessage(message: RunnerMessage): RunnerId =
        message.runnerId

    override fun getAnalyzer(chunk: Chunk, runnerId: RunnerId): Analyzer<JcInst, Fact> =
        DefaultAnalyzer(
            graph,
            flowFunctionFactory(runnerId),
            runnerId
        )

    override fun indirectionHandlerFactory(parent: ActorRef<RunnerMessage>, runnerId: RunnerId) =
        ActorFactory {
            IndirectionHandler(HierarchyExtensionImpl(cp), bannedPackagePrefixes, parent, runnerId)
        }
}
