package org.utbot.jcdb.impl.types

import org.utbot.jcdb.api.Classpath
import org.utbot.jcdb.api.MethodParameterId
import org.utbot.jcdb.api.throwClassNotFound
import org.utbot.jcdb.impl.suspendableLazy

class MethodParameterIdImpl(private val info: ParameterInfo, private val classpath: Classpath) :
    MethodParameterId {

    override suspend fun access() = info.access

    override val name: String?
        get() = info.name

    private val lazyType = suspendableLazy {
        classpath.findClassOrNull(info.type) ?: info.type.throwClassNotFound()
    }
    private val lazyAnnotations = suspendableLazy {
        info.annotations?.map {
            AnnotationIdImpl(info = it, classpath)
        }
    }


    override suspend fun paramClassId() = lazyType()

    override suspend fun annotations() = lazyAnnotations().orEmpty()
}