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

package org.jacodb.api.net

import org.jacodb.impl.ValueStoreType
import org.jacodb.impl.caches.guava.GUAVA_CACHE_PROVIDER_ID
import org.jacodb.impl.storage.ers.kv.KV_ERS_SPI
import org.jacodb.impl.storage.ers.ram.RAM_ERS_SPI
import org.jacodb.impl.storage.ers.sql.SQL_ERS_SPI
import java.time.Duration

class IlSettings() {
    val ersSpiId = RAM_ERS_SPI
    val publicationCacheSettings = IlPublicationCacheSettings()
}

class CacheSettings(
    val maxSize: Int = 10_000,
    val expirationDuration: Duration = Duration.ZERO,
    val storeType: ValueStoreType = ValueStoreType.SOFT
)


class IlPublicationCacheSettings(
    val cacheId: String = GUAVA_CACHE_PROVIDER_ID,
    val types: CacheSettings = CacheSettings(),
    val instructions: CacheSettings = CacheSettings(),
)
