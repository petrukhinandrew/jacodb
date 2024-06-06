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

package org.jacodb.panda.staticvm.utils

import org.jacodb.panda.staticvm.cfg.PandaGraph
import org.jacodb.panda.staticvm.cfg.PandaInst
import org.jacodb.panda.staticvm.cfg.findDominators
import java.util.ArrayDeque
import kotlin.LazyThreadSafetyMode.PUBLICATION

class PandaLoop(
    val graph: PandaGraph,
    val head: PandaInst,
    val instructions: List<PandaInst>,
) {
    val exits: Collection<PandaInst> by lazy(PUBLICATION) {
        val result = hashSetOf<PandaInst>()
        for (s in instructions) {
            graph.successors(s).forEach {
                if (!instructions.contains(it)) {
                    result.add(s)
                }
            }
        }
        result
    }

    val backJump: PandaInst get() = instructions.last()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PandaLoop

        if (head != other.head) return false
        if (instructions != other.instructions) return false

        return true
    }

    override fun hashCode(): Int {
        var result = head.hashCode()
        result = 31 * result + instructions.hashCode()
        return result
    }
}

val PandaGraph.loops: Set<PandaLoop>
    get() {
        val finder = findDominators()
        val loops = HashMap<PandaInst, MutableList<PandaInst>>()
        instructions.forEach { inst ->
            val dominators = finder.dominators(inst)

            val headers = arrayListOf<PandaInst>()
            successors(inst).forEach {
                if (dominators.contains(it)) {
                    headers.add(it)
                }
            }
            headers.forEach { header ->
                val loopBody = loopBodyOf(header, inst)
                loops[header] = loops[header]?.union(loopBody) ?: loopBody
            }
        }
        return loops.map { (key, value) ->
            newLoop(key, value)
        }.toSet()
    }

private fun PandaGraph.newLoop(head: PandaInst, loopStatements: MutableList<PandaInst>): PandaLoop {
    // put header to the top
    loopStatements.remove(head)
    loopStatements.add(0, head)

    // last statement
    val backJump = loopStatements.last()
    // must branch back to the head
    assert(successors(backJump).contains(head))
    return PandaLoop(this, head = head, instructions = loopStatements)
}

private fun PandaGraph.loopBodyOf(header: PandaInst, inst: PandaInst): MutableList<PandaInst> {
    val loopBody = arrayListOf(header)
    val stack = ArrayDeque<PandaInst>().also {
        it.push(inst)
    }
    while (!stack.isEmpty()) {
        val next = stack.pop()
        if (!loopBody.contains(next)) {
            loopBody.add(0, next)
            predecessors(next).forEach { stack.push(it) }
        }
    }
    assert(inst === header && loopBody.size == 1 || loopBody[loopBody.size - 2] === inst)
    assert(loopBody[loopBody.size - 1] === header)
    return loopBody
}

private fun MutableList<PandaInst>.union(another: List<PandaInst>): MutableList<PandaInst> = apply {
    addAll(another.filter { !contains(it) })
}
