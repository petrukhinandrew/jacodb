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

package org.jacodb.impl.storage.kv.lmdb

import org.jacodb.api.storage.kv.Cursor
import org.jacodb.api.storage.kv.NamedMap
import org.jacodb.api.storage.kv.Transaction
import org.jacodb.api.storage.kv.withFirstMoveSkipped
import org.lmdbjava.GetOp
import org.lmdbjava.SeekOp
import org.lmdbjava.Txn
import java.nio.ByteBuffer

internal class LmdbTransaction(
    override val storage: LmdbKeyValueStorage,
    internal val lmdbTxn: Txn<ByteBuffer>
) : Transaction {

    override val isReadonly: Boolean get() = lmdbTxn.isReadOnly

    override val isFinished: Boolean get() = lmdbTxn.id < 0

    override fun getNamedMap(name: String, create: Boolean): NamedMap? {
        val (db, duplicates) = storage.getMap(lmdbTxn, name, create) ?: return null
        return LmdbNamedMap(db, duplicates, name)
    }

    override fun getMapNames(): Set<String> = storage.getMapNames()

    override fun get(map: NamedMap, key: ByteArray): ByteArray? {
        map as LmdbNamedMap
        return map.db.get(lmdbTxn, key.asByteBuffer)?.asArray
    }

    override fun put(map: NamedMap, key: ByteArray, value: ByteArray): Boolean {
        map as LmdbNamedMap
        map.db.openCursor(lmdbTxn).use { cursor ->
            val keyBuffer = key.asByteBuffer
            val valueBuffer = value.asByteBuffer
            if (map.duplicates) {
                if (cursor[keyBuffer, valueBuffer, SeekOp.MDB_GET_BOTH]) {
                    return false
                }
                cursor.put(keyBuffer, valueBuffer)
            } else {
                if (cursor.get(keyBuffer, GetOp.MDB_SET_KEY) && cursor.`val`().equals(valueBuffer)) {
                    return false
                }
                cursor.put(keyBuffer, valueBuffer)
            }
            return true
        }
    }

    override fun delete(map: NamedMap, key: ByteArray): Boolean {
        map as LmdbNamedMap
        map.db.openCursor(lmdbTxn).use { cursor ->
            if (cursor[key.asByteBuffer, GetOp.MDB_SET]) {
                cursor.delete()
                return true
            }
            return false
        }
    }

    override fun delete(map: NamedMap, key: ByteArray, value: ByteArray): Boolean {
        map as LmdbNamedMap
        map.db.openCursor(lmdbTxn).use { cursor ->
            if (cursor[key.asByteBuffer, value.asByteBuffer, SeekOp.MDB_GET_BOTH]) {
                cursor.delete()
                return true
            }
            return false
        }
    }

    override fun navigateTo(map: NamedMap, key: ByteArray?): Cursor {
        map as LmdbNamedMap
        val cursor = map.db.openCursor(lmdbTxn)
        val result = LmdbCursor(this, cursor)
        return if (key == null) {
            result
        } else if (cursor.get(key.asByteBuffer, GetOp.MDB_SET_RANGE)) {
            result.withFirstMoveSkipped()
        } else {
            result
        }
    }

    override fun commit(): Boolean {
        lmdbTxn.commit()
        return true
    }

    override fun abort() {
        lmdbTxn.abort()
    }
}