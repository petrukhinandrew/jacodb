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

package org.jacodb.panda.staticvm

import org.jacodb.api.core.cfg.InstList
import org.jacodb.api.core.cfg.MutableInstList

data class PandaInstList(override val instructions: List<PandaInst>) : InstList<PandaInst> {
    override val size: Int
        get() = instructions.size
    override val indices: IntRange
        get() = instructions.indices
    override val lastIndex: Int
        get() = instructions.lastIndex

    override fun get(index: Int): PandaInst = instructions[index]

    override fun getOrNull(index: Int): PandaInst? = instructions.getOrNull(index)

    override fun toMutableList(): MutableInstList<PandaInst> {
        TODO("Not yet implemented")
    }

    override fun iterator(): Iterator<PandaInst> = instructions.iterator()
}