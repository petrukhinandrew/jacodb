package org.utbot.jcdb.api

import org.objectweb.asm.tree.MethodNode

interface JcTypedField {
    val name: String
    val type: JcType

    val jcClass: JcClassOrInterface
    val jcType: JcType
}

interface JcTypedMethod {
    val name: String
    val returnType: JcType
    val parameters: List<JcTypeMethodParameter>
    val parameterization: List<JcType>

    val exceptions: List<JcClassOrInterface>
    val method: JcMethod

    suspend fun body(): MethodNode

    val jcClass: JcClassOrInterface
    val declaringType: JcType
}

interface JcTypeMethodParameter {
    val annotations: List<JcAnnotation>
    val type: JcType
    val name: String?

    val typeMethod: JcTypedMethod

}

interface JcType {
    val classpath: JcClasspath
    val typeName: String

    val nullable: Boolean
}

// boolean, int, float, double, void (?) etc
interface JcPrimitiveType : JcType {
    override val nullable: Boolean
        get() = false
}

interface JcRefType : JcType {
    val jcClass: JcClassOrInterface

    val methods: List<JcTypedMethod>
    val fields: List<JcTypedField>

    fun notNullable() : JcRefType
}

// -----------
// Array<T: Any> -> JcArrayType(JcTypeVariable())
// -----------
// Array<Any> -> JcArrayType(JcClassType())
// -----------
// Array<List<T>> -> JcArrayType(JcParametrizedType(JcClassType(), JcTypeVariable()))
interface JcArrayType : JcRefType {
    val elementType: JcType
}

// -----------
// class A<T> -> JcParametrizedType(JcTypeVariable())
interface JcParametrizedType : JcRefType {
    val parameterTypes: List<JcRefType>
}

// java.lang.String -> JcClassType()
interface JcClassType : JcRefType {
    suspend fun superType(): JcRefType
    suspend fun interfaces(): JcRefType

    suspend fun outerType(): JcRefType?
    suspend fun outerMethod(): JcTypedMethod?

    suspend fun innerTypes(): List<JcRefType>
}

// -----------
// class A<T> -> JcParametrizedType(JcTypeVariable())
// -----------
// class A : B<T> -> type = JcClassType(), type.superType = JcParametrizedType(JcTypeVariable())
// -----------
// class A<T> {
//      val field: T
// }
// A<T> -> type = JcParametrizedType(JcTypeVariable()), type.fields[0].type = JcTypeVariable("T")
// -----------
interface JcTypeVariable : JcRefType {
    val typeSymbol: String
}


sealed interface JcBoundWildcard : JcRefType {
    val boundType: JcRefType
}
interface JcUpperBoundWildcard : JcBoundWildcard
interface JcLowerBoundWildcard : JcBoundWildcard
interface JcUnboundWildcard : JcRefType