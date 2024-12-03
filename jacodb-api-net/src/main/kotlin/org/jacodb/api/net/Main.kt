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

import org.jacodb.api.net.database.IlDatabaseImpl
import org.jacodb.api.net.features.IlApproximations
import org.jacodb.api.net.features.IlMethodInstructionsFeature
import org.jacodb.api.net.publication.IlPublicationCache
import org.jacodb.api.net.rdinfra.NetApiServer
import java.lang.Exception


fun main(args: Array<String>) {
    assert(args[0] == "--exe")
    val exePath = args[1]
    assert(args[2] == "--asm")
    val asmPath = args[3]

    val settings = IlSettings()
    val database = IlDatabaseImpl(settings)

    val api = NetApiServer(exePath, asmPath, database)
    api.requestTestAsm()

    val publication = database.publication(
        listOf(
            IlPublicationCache(settings.publicationCacheSettings),
            IlMethodInstructionsFeature(),
            IlApproximations
        )
    )

    api.close()
    val allTypes = publication.allTypes
    println("types fetched")
    allTypes.forEach { typeDto ->
        val type = publication.findIlTypeOrNull(typeDto.fullname)
        if (type == null)
            println("not found ${typeDto.fullname}")
        if (type != null && type.fullname == "System.Collections.Generic.IList`1[T]704643078") {
            val fields = type.fields
            println(fields.joinToString { it.name })

//            methods.forEach {
//                val insts =
//                    publication.featuresChain.callUntilResolved<IlMethodExtFeature, ResolvedInstructionsResult> { feature ->
//                        feature.instList(it)
//                    }
//            }
//            return@forEach
        }
    }
}