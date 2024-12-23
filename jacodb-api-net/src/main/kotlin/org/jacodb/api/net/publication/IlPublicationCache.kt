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

package org.jacodb.api.net.publication

import org.jacodb.api.net.*
import org.jacodb.api.net.IlMethodExtFeature.IlInstListResult
import org.jacodb.api.net.cfg.IlGraphImpl
import org.jacodb.api.net.generated.models.TypeId
import org.jacodb.api.net.ilinstances.IlMethod
import org.jacodb.impl.caches.PluggableCacheProvider


class IlPublicationCache(private val settings: IlPublicationCacheSettings) : IlTypeSearchFeature, IlMethodExtFeature {

    private val cacheProvider: PluggableCacheProvider = PluggableCacheProvider.getProvider(settings.cacheId)

    private val types = newSegment<TypeId, ResolvedIlTypeResult>(settings.types)
    private val instructions = newSegment<IlMethod, IlInstListResult>(settings.instructions)
    private val cfgs = newSegment<IlMethod, IlGraphImpl>(settings.flowGraphs)
    override fun findType(typeId: TypeId): ResolvedIlTypeResult? = types[typeId]


    override fun instList(method: IlMethod): IlInstListResult? {
        return instructions[method]
    }

    override fun flowGraph(method: IlMethod): IlMethodExtFeature.IlFlowGraphResult? {
        return cfgs[method]?.let { graph -> IlMethodExtFeature.IlFlowGraphResult(method, graph) }
    }


    override fun on(event: IlPublicationEvent) {
        when (val result = event.result) {
            is ResolvedIlTypeResult -> types[result.typeId] = result

            is IlInstListResult -> instructions[result.method] = result
            is ResolvedIlTypesResult -> return
            else -> throw IllegalArgumentException("Unknown event $event")
        }
    }

    private fun <K : Any, V : Any> newSegment(settings: CacheSettings) = cacheProvider.newCache<K, V> {
        maximumSize = settings.maxSize
        expirationDuration = settings.expirationDuration
        valueRefType = settings.storeType
    }
}