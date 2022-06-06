package org.utbot.java.compilation.database.impl.fs

import java.io.File
import java.io.InputStream

class JmodByteCodeLocation(file: File, syncLoadClassesOnlyFrom: List<String>?) :
    JarFileLocationImpl(file, syncLoadClassesOnlyFrom) {

    override fun createRefreshed() = JmodByteCodeLocation(file, syncLoadClassesOnlyFrom)

    override val jarWithClasses: JarWithClasses?
        get() {
            val jarWithClasses = super.jarWithClasses ?: return null
            return JarWithClasses(jar = jarWithClasses.jar, jarWithClasses.classes.mapKeys { (key, _) ->
                key.removePrefix("classes.")
            })
        }

    override suspend fun resolve(classFullName: String): InputStream? {
        return super.resolve("classes.$classFullName")
    }
}