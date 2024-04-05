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

package org.jacodb.ifds.taint

import org.jacodb.analysis.taint.BackwardTaintAnalyzer
import org.jacodb.analysis.taint.EdgeForOtherRunner
import org.jacodb.analysis.taint.NewVulnerability
import org.jacodb.analysis.taint.TaintAnalyzer
import org.jacodb.analysis.taint.TaintDomainFact
import org.jacodb.api.JcClasspath
import org.jacodb.api.analysis.JcApplicationGraph
import org.jacodb.ifds.JcFlowFunctionsAdapter
import org.jacodb.ifds.JcIfdsContext
import org.jacodb.ifds.domain.Reason
import org.jacodb.ifds.domain.RunnerId
import org.jacodb.ifds.messages.NewEdge
import org.jacodb.ifds.messages.NewResult
import org.jacodb.ifds.messages.NewSummaryEdge
import org.jacodb.ifds.toEdge

private fun complementRunner(type: RunnerId): RunnerId =
    when (type) {
        ForwardRunner -> BackwardRunner
        BackwardRunner -> ForwardRunner
        else -> error("unexpected runner: $type")
    }

fun taintIfdsContext(
    cp: JcClasspath,
    graph: JcApplicationGraph,
    bannedPackagePrefixes: List<String>,
): JcIfdsContext<TaintDomainFact> =
    JcIfdsContext(
        cp,
        graph,
        bannedPackagePrefixes
    ) { _, runnerId ->
        val analyzer = when (runnerId) {
            is ForwardRunner -> TaintAnalyzer(graph)
            is BackwardRunner -> BackwardTaintAnalyzer(graph)
            else -> error("Unexpected runnerId: $runnerId")
        }

        JcFlowFunctionsAdapter(
            runnerId,
            analyzer
        ) { event ->
            when (event) {
                is EdgeForOtherRunner -> {
                    val edgeForOtherRunner =
                        NewEdge(
                            complementRunner(runnerId),
                            event.edge.toEdge(),
                            Reason.FromOtherRunner(edge, runnerId)
                        )
                    add(edgeForOtherRunner)
                }

                is org.jacodb.analysis.taint.NewSummaryEdge -> {
                    val summaryEdge = NewSummaryEdge(runnerId, event.edge.toEdge())
                    add(summaryEdge)
                }

                is NewVulnerability -> {
                    val result = NewResult(runnerId, TaintVulnerability(event.vulnerability))
                    add(result)
                }
            }
        }
    }