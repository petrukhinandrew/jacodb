package org.utbot.java.compilation.database.impl.fs

import mu.KLogging
import org.utbot.java.compilation.database.ApiLevel
import org.utbot.java.compilation.database.api.ByteCodeLocation
import org.utbot.java.compilation.database.api.md5
import java.io.File
import java.io.InputStream
import java.util.jar.JarEntry
import java.util.jar.JarFile
import kotlin.streams.toList

class JarFileLocationImpl(
    val file: File,
    override val apiLevel: ApiLevel,
    private val loadClassesOnlyFrom: List<String>?
) : ByteCodeLocation {
    companion object : KLogging()

    override val currentVersion: String
        get() = (file.absolutePath + file.lastModified()).md5()

    override val version = currentVersion
    override fun refreshed() = JarFileLocationImpl(file, apiLevel, loadClassesOnlyFrom)

    override suspend fun loader(): ByteCodeLoader? {
        try {
            val sync = jarClasses() ?: return null
            val classes = sync.second.mapValues { (className, jar) ->
                when (className.matchesOneOf(loadClassesOnlyFrom)) {
                    true -> jar.first.getInputStream(jar.second)
                    else -> null // lazy
                }
            }

            return ByteCodeLoaderImpl(this, LoadingPortion(classes) { sync.first.close() }) {
                val async = jarClasses()
                LoadingPortion(async?.second?.filterKeys { className ->
                    !className.matchesOneOf(loadClassesOnlyFrom)
                }.orEmpty().mapValues { (_, jar) ->
                    jar.first.getInputStream(jar.second)
                }) {
                    async?.first?.close()
                }
            }
        } catch (e: Exception) {
            logger.warn(e) { "error loading classes from build folder: ${file.absolutePath}. returning empty loader" }
            return null
        }
    }

    override suspend fun resolve(classFullName: String): InputStream? {
        val jar = jarFile() ?: return null
        val jarEntryName = classFullName.replace(".", "/") + ".class"
        val jarEntry = jar.getJarEntry(jarEntryName)
        return jar.getInputStream(jarEntry)
    }

    private fun jarClasses(): Pair<JarFile, Map<String, Pair<JarFile, JarEntry>>>? {
        val jarFile = jarFile() ?: return null

        return jarFile to jarFile.stream().filter { it.name.endsWith(".class") }.map {
            val className = it.name.removeSuffix(".class").replace("/", ".")
            className to (jarFile to it)
        }.toList().toMap()
    }

    private fun jarFile(): JarFile? {
        if (!file.exists() || !file.isFile) {
            return null
        }

        try {
            return JarFile(file)
        } catch (e: Exception) {
            logger.warn(e) { "error processing jar located ${file.absolutePath}" }
            return null
        }
    }

    override fun toString(): String = file.absolutePath
}