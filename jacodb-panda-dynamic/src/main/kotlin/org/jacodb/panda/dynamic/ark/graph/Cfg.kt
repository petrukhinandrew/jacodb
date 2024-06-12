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

package org.jacodb.panda.dynamic.ark.graph

import org.jacodb.api.common.cfg.ControlFlowGraph
import org.jacodb.panda.dynamic.ark.base.Stmt
import org.jacodb.panda.dynamic.ark.base.TerminatingStmt

class Cfg(
    val blocks: Map<Int, BasicBlock>,
) : ControlFlowGraph<Stmt> {
    val startingStmt: Stmt
        get() = blocks[0]!!.stmts.first()

    override val instructions: List<Stmt> by lazy {
        traverse().toList()
    }

    override val entries: List<Stmt>
        get() = listOf(startingStmt)

    override val exits: List<Stmt>
        get() = instructions.filterIsInstance<TerminatingStmt>()

    private fun traverse(): Sequence<Stmt> = sequence {
        val visited: MutableSet<Int> = hashSetOf()
        val queue: ArrayDeque<Int> = ArrayDeque()
        queue.add(0)
        while (queue.isNotEmpty()) {
            val block = blocks[queue.removeFirst()]!!
            yieldAll(block.stmts)
            for (next in block.successors) {
                if (visited.add(next)) {
                    queue.add(next)
                }
            }
        }
    }

    fun successors(block: BasicBlock): List<BasicBlock> {
        return block.successors.map { blocks[it]!! }
    }

    fun predecessors(block: BasicBlock): List<BasicBlock> {
        return block.predecessors.map { blocks[it]!! }
    }

    private val successorMap: Map<Stmt, List<Stmt>> = run {
        val map: MutableMap<Stmt, List<Stmt>> = hashMapOf()
        for (block in blocks.values) {
            for ((i, stmt) in block.stmts.withIndex()) {
                check(stmt !in map)
                if (i == block.stmts.lastIndex) {
                    map[stmt] = block.successors.mapNotNull { blocks[it]!!.head }
                } else {
                    map[stmt] = listOf(block.stmts[i + 1])
                }
            }
        }
        map
    }

    private val predecessorMap: Map<Stmt, List<Stmt>> = run {
        val map: MutableMap<Stmt, List<Stmt>> = hashMapOf()
        for (block in blocks.values) {
            for ((i, stmt) in block.stmts.withIndex()) {
                check(stmt !in map)
                if (i == 0) {
                    map[stmt] = block.predecessors.mapNotNull { blocks[it]!!.last }
                } else {
                    map[stmt] = listOf(block.stmts[i - 1])
                }
            }
        }
        map
    }

    override fun successors(node: Stmt): Set<Stmt> {
        return successorMap[node]!!.toSet()
    }

    override fun predecessors(node: Stmt): Set<Stmt> {
        return predecessorMap[node]!!.toSet()
    }
}