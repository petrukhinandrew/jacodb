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

package org.jacodb.api

/**
 * This exception should be thrown when classpath is incomplete
 */
class NoClassInClasspathException(val className: String) : Exception("Class $className not found in classpath")

/**
 * This exception should be thrown when classpath is incomplete
 */
class TypeNotFoundException(val typeName: String) : Exception("Type $typeName not found in classpath")

class MethodNotFoundException(msg: String) : Exception(msg)

fun String.throwClassNotFound(): Nothing {
    throw NoClassInClasspathException(this)
}

inline fun <reified T> throwClassNotFound(): Nothing {
    T::class.java.name.throwClassNotFound()
}