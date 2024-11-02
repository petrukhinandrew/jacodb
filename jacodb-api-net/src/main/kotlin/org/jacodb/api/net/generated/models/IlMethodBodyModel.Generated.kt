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

@file:Suppress("EXPERIMENTAL_API_USAGE","EXPERIMENTAL_UNSIGNED_LITERALS","PackageDirectoryMismatch","UnusedImport","unused","LocalVariableName","CanBeVal","PropertyName","EnumEntryName","ClassName","ObjectPropertyName","UnnecessaryVariable","SpellCheckingInspection")
package org.jacodb.api.net.generated.models

import com.jetbrains.rd.framework.*
import com.jetbrains.rd.framework.base.*
import com.jetbrains.rd.framework.impl.*

import com.jetbrains.rd.util.lifetime.*
import com.jetbrains.rd.util.reactive.*
import com.jetbrains.rd.util.string.*
import com.jetbrains.rd.util.*
import kotlin.time.Duration
import kotlin.reflect.KClass
import kotlin.jvm.JvmStatic



/**
 * #### Generated from [IlMethodBodyModel.kt:24]
 */
class IlMethodBodyModel private constructor(
) : RdExtBase() {
    //companion
    
    companion object : ISerializersOwner {
        
        override fun registerSerializersCore(serializers: ISerializers)  {
            serializers.register(IlByteConstDto)
            serializers.register(IlIntConstDto)
            serializers.register(IlLongConstDto)
            serializers.register(IlFloatConstDto)
            serializers.register(IlDoubleConstDto)
            serializers.register(IlNullDto)
            serializers.register(IlBoolConstDto)
            serializers.register(IlStringConstDto)
            serializers.register(IlTypeRefDto)
            serializers.register(IlMethodRefDto)
            serializers.register(IlFieldRefDto)
            serializers.register(IlUnaryOpDto)
            serializers.register(IlBinaryOpDto)
            serializers.register(IlInitExprDto)
            serializers.register(IlNewExprDto)
            serializers.register(IlSizeOfExprDto)
            serializers.register(IlFieldAccessDto)
            serializers.register(IlArrayAccessDto)
            serializers.register(IlNewArrayExprDto)
            serializers.register(IlArrayLengthExprDto)
            serializers.register(IlCallDto)
            serializers.register(IlConvExprDto)
            serializers.register(IlBoxExprDto)
            serializers.register(IlUnboxExprDto)
            serializers.register(IlCastClassExprDto)
            serializers.register(IlIsInstExprDto)
            serializers.register(IlManagedRefExprDto)
            serializers.register(IlUnmanagedRefExprDto)
            serializers.register(IlManagedDerefExprDto)
            serializers.register(IlUnmanagedDerefExprDto)
            serializers.register(IlStackAllocExprDto)
            serializers.register(IlAssignStmtDto)
            serializers.register(IlCallStmtDto)
            serializers.register(IlReturnStmtDto)
            serializers.register(IlThrowStmtDto)
            serializers.register(IlRethrowStmtDto)
            serializers.register(IlEndFinallyStmtDto)
            serializers.register(IlEndFaultStmtDto)
            serializers.register(IlEndFilterStmtDto)
            serializers.register(IlGotoStmtDto)
            serializers.register(IlIfStmtDto)
            serializers.register(IlVarKind.marshaller)
            serializers.register(IlArgAccessDto)
            serializers.register(IlVarAccessDto)
            serializers.register(IlExprDto_Unknown)
            serializers.register(IlValueDto_Unknown)
            serializers.register(IlConstDto_Unknown)
            serializers.register(IlNumConstDto_Unknown)
            serializers.register(IlCastExprDto_Unknown)
            serializers.register(IlRefExprDto_Unknown)
            serializers.register(IlDerefExprDto_Unknown)
            serializers.register(IlStmtDto_Unknown)
            serializers.register(IlEhStmtDto_Unknown)
            serializers.register(IlBranchStmtDto_Unknown)
        }
        
        
        @JvmStatic
        @JvmName("internalCreateModel")
        @Deprecated("Use create instead", ReplaceWith("create(lifetime, protocol)"))
        internal fun createModel(lifetime: Lifetime, protocol: IProtocol): IlMethodBodyModel  {
            @Suppress("DEPRECATION")
            return create(lifetime, protocol)
        }
        
        @JvmStatic
        @Deprecated("Use protocol.ilMethodBodyModel or revise the extension scope instead", ReplaceWith("protocol.ilMethodBodyModel"))
        fun create(lifetime: Lifetime, protocol: IProtocol): IlMethodBodyModel  {
            IlRoot.register(protocol.serializers)
            
            return IlMethodBodyModel()
        }
        
        
        const val serializationHash = -7271216282343879510L
        
    }
    override val serializersOwner: ISerializersOwner get() = IlMethodBodyModel
    override val serializationHash: Long get() = IlMethodBodyModel.serializationHash
    
    //fields
    //methods
    //initializer
    //secondary constructor
    //equals trait
    //hash code trait
    //pretty print
    override fun print(printer: PrettyPrinter)  {
        printer.println("IlMethodBodyModel (")
        printer.print(")")
    }
    //deepClone
    override fun deepClone(): IlMethodBodyModel   {
        return IlMethodBodyModel(
        )
    }
    //contexts
}
val IProtocol.ilMethodBodyModel get() = getOrCreateExtension(IlMethodBodyModel::class) { @Suppress("DEPRECATION") IlMethodBodyModel.create(lifetime, this) }



/**
 * #### Generated from [IlMethodBodyModel.kt:153]
 */
class IlArgAccessDto (
    val index: Int,
    type: CacheKey
) : IlValueDto (
    type
) {
    //companion
    
    companion object : IMarshaller<IlArgAccessDto> {
        override val _type: KClass<IlArgAccessDto> = IlArgAccessDto::class
        
        @Suppress("UNCHECKED_CAST")
        override fun read(ctx: SerializationCtx, buffer: AbstractBuffer): IlArgAccessDto  {
            val type = CacheKey.read(ctx, buffer)
            val index = buffer.readInt()
            return IlArgAccessDto(index, type)
        }
        
        override fun write(ctx: SerializationCtx, buffer: AbstractBuffer, value: IlArgAccessDto)  {
            CacheKey.write(ctx, buffer, value.type)
            buffer.writeInt(value.index)
        }
        
        
    }
    //fields
    //methods
    //initializer
    //secondary constructor
    //equals trait
    override fun equals(other: Any?): Boolean  {
        if (this === other) return true
        if (other == null || other::class != this::class) return false
        
        other as IlArgAccessDto
        
        if (index != other.index) return false
        if (type != other.type) return false
        
        return true
    }
    //hash code trait
    override fun hashCode(): Int  {
        var __r = 0
        __r = __r*31 + index.hashCode()
        __r = __r*31 + type.hashCode()
        return __r
    }
    //pretty print
    override fun print(printer: PrettyPrinter)  {
        printer.println("IlArgAccessDto (")
        printer.indent {
            print("index = "); index.print(printer); println()
            print("type = "); type.print(printer); println()
        }
        printer.print(")")
    }
    
    override fun toString() = PrettyPrinter().singleLine().also { print(it) }.toString()
    //deepClone
    //contexts
}


/**
 * #### Generated from [IlMethodBodyModel.kt:68]
 */
class IlArrayAccessDto (
    val array: IlExprDto,
    val index: IlExprDto,
    type: CacheKey
) : IlValueDto (
    type
) {
    //companion
    
    companion object : IMarshaller<IlArrayAccessDto> {
        override val _type: KClass<IlArrayAccessDto> = IlArrayAccessDto::class
        
        @Suppress("UNCHECKED_CAST")
        override fun read(ctx: SerializationCtx, buffer: AbstractBuffer): IlArrayAccessDto  {
            val type = CacheKey.read(ctx, buffer)
            val array = ctx.serializers.readPolymorphic<IlExprDto>(ctx, buffer, IlExprDto)
            val index = ctx.serializers.readPolymorphic<IlExprDto>(ctx, buffer, IlExprDto)
            return IlArrayAccessDto(array, index, type)
        }
        
        override fun write(ctx: SerializationCtx, buffer: AbstractBuffer, value: IlArrayAccessDto)  {
            CacheKey.write(ctx, buffer, value.type)
            ctx.serializers.writePolymorphic(ctx, buffer, value.array)
            ctx.serializers.writePolymorphic(ctx, buffer, value.index)
        }
        
        
    }
    //fields
    //methods
    //initializer
    //secondary constructor
    //equals trait
    override fun equals(other: Any?): Boolean  {
        if (this === other) return true
        if (other == null || other::class != this::class) return false
        
        other as IlArrayAccessDto
        
        if (array != other.array) return false
        if (index != other.index) return false
        if (type != other.type) return false
        
        return true
    }
    //hash code trait
    override fun hashCode(): Int  {
        var __r = 0
        __r = __r*31 + array.hashCode()
        __r = __r*31 + index.hashCode()
        __r = __r*31 + type.hashCode()
        return __r
    }
    //pretty print
    override fun print(printer: PrettyPrinter)  {
        printer.println("IlArrayAccessDto (")
        printer.indent {
            print("array = "); array.print(printer); println()
            print("index = "); index.print(printer); println()
            print("type = "); type.print(printer); println()
        }
        printer.print(")")
    }
    
    override fun toString() = PrettyPrinter().singleLine().also { print(it) }.toString()
    //deepClone
    //contexts
}


/**
 * #### Generated from [IlMethodBodyModel.kt:77]
 */
class IlArrayLengthExprDto (
    val array: IlExprDto,
    type: CacheKey
) : IlExprDto (
    type
) {
    //companion
    
    companion object : IMarshaller<IlArrayLengthExprDto> {
        override val _type: KClass<IlArrayLengthExprDto> = IlArrayLengthExprDto::class
        
        @Suppress("UNCHECKED_CAST")
        override fun read(ctx: SerializationCtx, buffer: AbstractBuffer): IlArrayLengthExprDto  {
            val type = CacheKey.read(ctx, buffer)
            val array = ctx.serializers.readPolymorphic<IlExprDto>(ctx, buffer, IlExprDto)
            return IlArrayLengthExprDto(array, type)
        }
        
        override fun write(ctx: SerializationCtx, buffer: AbstractBuffer, value: IlArrayLengthExprDto)  {
            CacheKey.write(ctx, buffer, value.type)
            ctx.serializers.writePolymorphic(ctx, buffer, value.array)
        }
        
        
    }
    //fields
    //methods
    //initializer
    //secondary constructor
    //equals trait
    override fun equals(other: Any?): Boolean  {
        if (this === other) return true
        if (other == null || other::class != this::class) return false
        
        other as IlArrayLengthExprDto
        
        if (array != other.array) return false
        if (type != other.type) return false
        
        return true
    }
    //hash code trait
    override fun hashCode(): Int  {
        var __r = 0
        __r = __r*31 + array.hashCode()
        __r = __r*31 + type.hashCode()
        return __r
    }
    //pretty print
    override fun print(printer: PrettyPrinter)  {
        printer.println("IlArrayLengthExprDto (")
        printer.indent {
            print("array = "); array.print(printer); println()
            print("type = "); type.print(printer); println()
        }
        printer.print(")")
    }
    
    override fun toString() = PrettyPrinter().singleLine().also { print(it) }.toString()
    //deepClone
    //contexts
}


/**
 * #### Generated from [IlMethodBodyModel.kt:113]
 */
class IlAssignStmtDto (
    val lhs: IlValueDto,
    val rhs: IlExprDto
) : IlStmtDto (
) {
    //companion
    
    companion object : IMarshaller<IlAssignStmtDto> {
        override val _type: KClass<IlAssignStmtDto> = IlAssignStmtDto::class
        
        @Suppress("UNCHECKED_CAST")
        override fun read(ctx: SerializationCtx, buffer: AbstractBuffer): IlAssignStmtDto  {
            val lhs = ctx.serializers.readPolymorphic<IlValueDto>(ctx, buffer, IlValueDto)
            val rhs = ctx.serializers.readPolymorphic<IlExprDto>(ctx, buffer, IlExprDto)
            return IlAssignStmtDto(lhs, rhs)
        }
        
        override fun write(ctx: SerializationCtx, buffer: AbstractBuffer, value: IlAssignStmtDto)  {
            ctx.serializers.writePolymorphic(ctx, buffer, value.lhs)
            ctx.serializers.writePolymorphic(ctx, buffer, value.rhs)
        }
        
        
    }
    //fields
    //methods
    //initializer
    //secondary constructor
    //equals trait
    override fun equals(other: Any?): Boolean  {
        if (this === other) return true
        if (other == null || other::class != this::class) return false
        
        other as IlAssignStmtDto
        
        if (lhs != other.lhs) return false
        if (rhs != other.rhs) return false
        
        return true
    }
    //hash code trait
    override fun hashCode(): Int  {
        var __r = 0
        __r = __r*31 + lhs.hashCode()
        __r = __r*31 + rhs.hashCode()
        return __r
    }
    //pretty print
    override fun print(printer: PrettyPrinter)  {
        printer.println("IlAssignStmtDto (")
        printer.indent {
            print("lhs = "); lhs.print(printer); println()
            print("rhs = "); rhs.print(printer); println()
        }
        printer.print(")")
    }
    
    override fun toString() = PrettyPrinter().singleLine().also { print(it) }.toString()
    //deepClone
    //contexts
}


/**
 * #### Generated from [IlMethodBodyModel.kt:48]
 */
class IlBinaryOpDto (
    val lhs: IlExprDto,
    val rhs: IlExprDto,
    type: CacheKey
) : IlExprDto (
    type
) {
    //companion
    
    companion object : IMarshaller<IlBinaryOpDto> {
        override val _type: KClass<IlBinaryOpDto> = IlBinaryOpDto::class
        
        @Suppress("UNCHECKED_CAST")
        override fun read(ctx: SerializationCtx, buffer: AbstractBuffer): IlBinaryOpDto  {
            val type = CacheKey.read(ctx, buffer)
            val lhs = ctx.serializers.readPolymorphic<IlExprDto>(ctx, buffer, IlExprDto)
            val rhs = ctx.serializers.readPolymorphic<IlExprDto>(ctx, buffer, IlExprDto)
            return IlBinaryOpDto(lhs, rhs, type)
        }
        
        override fun write(ctx: SerializationCtx, buffer: AbstractBuffer, value: IlBinaryOpDto)  {
            CacheKey.write(ctx, buffer, value.type)
            ctx.serializers.writePolymorphic(ctx, buffer, value.lhs)
            ctx.serializers.writePolymorphic(ctx, buffer, value.rhs)
        }
        
        
    }
    //fields
    //methods
    //initializer
    //secondary constructor
    //equals trait
    override fun equals(other: Any?): Boolean  {
        if (this === other) return true
        if (other == null || other::class != this::class) return false
        
        other as IlBinaryOpDto
        
        if (lhs != other.lhs) return false
        if (rhs != other.rhs) return false
        if (type != other.type) return false
        
        return true
    }
    //hash code trait
    override fun hashCode(): Int  {
        var __r = 0
        __r = __r*31 + lhs.hashCode()
        __r = __r*31 + rhs.hashCode()
        __r = __r*31 + type.hashCode()
        return __r
    }
    //pretty print
    override fun print(printer: PrettyPrinter)  {
        printer.println("IlBinaryOpDto (")
        printer.indent {
            print("lhs = "); lhs.print(printer); println()
            print("rhs = "); rhs.print(printer); println()
            print("type = "); type.print(printer); println()
        }
        printer.print(")")
    }
    
    override fun toString() = PrettyPrinter().singleLine().also { print(it) }.toString()
    //deepClone
    //contexts
}


/**
 * #### Generated from [IlMethodBodyModel.kt:39]
 */
class IlBoolConstDto (
    val value: Boolean,
    type: CacheKey
) : IlConstDto (
    type
) {
    //companion
    
    companion object : IMarshaller<IlBoolConstDto> {
        override val _type: KClass<IlBoolConstDto> = IlBoolConstDto::class
        
        @Suppress("UNCHECKED_CAST")
        override fun read(ctx: SerializationCtx, buffer: AbstractBuffer): IlBoolConstDto  {
            val type = CacheKey.read(ctx, buffer)
            val value = buffer.readBool()
            return IlBoolConstDto(value, type)
        }
        
        override fun write(ctx: SerializationCtx, buffer: AbstractBuffer, value: IlBoolConstDto)  {
            CacheKey.write(ctx, buffer, value.type)
            buffer.writeBool(value.value)
        }
        
        
    }
    //fields
    //methods
    //initializer
    //secondary constructor
    //equals trait
    override fun equals(other: Any?): Boolean  {
        if (this === other) return true
        if (other == null || other::class != this::class) return false
        
        other as IlBoolConstDto
        
        if (value != other.value) return false
        if (type != other.type) return false
        
        return true
    }
    //hash code trait
    override fun hashCode(): Int  {
        var __r = 0
        __r = __r*31 + value.hashCode()
        __r = __r*31 + type.hashCode()
        return __r
    }
    //pretty print
    override fun print(printer: PrettyPrinter)  {
        printer.println("IlBoolConstDto (")
        printer.indent {
            print("value = "); value.print(printer); println()
            print("type = "); type.print(printer); println()
        }
        printer.print(")")
    }
    
    override fun toString() = PrettyPrinter().singleLine().also { print(it) }.toString()
    //deepClone
    //contexts
}


/**
 * #### Generated from [IlMethodBodyModel.kt:89]
 */
class IlBoxExprDto (
    targetType: CacheKey,
    operand: IlExprDto,
    type: CacheKey
) : IlCastExprDto (
    targetType,
    operand,
    type
) {
    //companion
    
    companion object : IMarshaller<IlBoxExprDto> {
        override val _type: KClass<IlBoxExprDto> = IlBoxExprDto::class
        
        @Suppress("UNCHECKED_CAST")
        override fun read(ctx: SerializationCtx, buffer: AbstractBuffer): IlBoxExprDto  {
            val targetType = CacheKey.read(ctx, buffer)
            val operand = ctx.serializers.readPolymorphic<IlExprDto>(ctx, buffer, IlExprDto)
            val type = CacheKey.read(ctx, buffer)
            return IlBoxExprDto(targetType, operand, type)
        }
        
        override fun write(ctx: SerializationCtx, buffer: AbstractBuffer, value: IlBoxExprDto)  {
            CacheKey.write(ctx, buffer, value.targetType)
            ctx.serializers.writePolymorphic(ctx, buffer, value.operand)
            CacheKey.write(ctx, buffer, value.type)
        }
        
        
    }
    //fields
    //methods
    //initializer
    //secondary constructor
    //equals trait
    override fun equals(other: Any?): Boolean  {
        if (this === other) return true
        if (other == null || other::class != this::class) return false
        
        other as IlBoxExprDto
        
        if (targetType != other.targetType) return false
        if (operand != other.operand) return false
        if (type != other.type) return false
        
        return true
    }
    //hash code trait
    override fun hashCode(): Int  {
        var __r = 0
        __r = __r*31 + targetType.hashCode()
        __r = __r*31 + operand.hashCode()
        __r = __r*31 + type.hashCode()
        return __r
    }
    //pretty print
    override fun print(printer: PrettyPrinter)  {
        printer.println("IlBoxExprDto (")
        printer.indent {
            print("targetType = "); targetType.print(printer); println()
            print("operand = "); operand.print(printer); println()
            print("type = "); type.print(printer); println()
        }
        printer.print(")")
    }
    
    override fun toString() = PrettyPrinter().singleLine().also { print(it) }.toString()
    //deepClone
    //contexts
}


/**
 * #### Generated from [IlMethodBodyModel.kt:137]
 */
abstract class IlBranchStmtDto (
    val target: Int
) : IlStmtDto (
) {
    //companion
    
    companion object : IAbstractDeclaration<IlBranchStmtDto> {
        override fun readUnknownInstance(ctx: SerializationCtx, buffer: AbstractBuffer, unknownId: RdId, size: Int): IlBranchStmtDto  {
            val objectStartPosition = buffer.position
            val target = buffer.readInt()
            val unknownBytes = ByteArray(objectStartPosition + size - buffer.position)
            buffer.readByteArrayRaw(unknownBytes)
            return IlBranchStmtDto_Unknown(target, unknownId, unknownBytes)
        }
        
        
    }
    //fields
    //methods
    //initializer
    //secondary constructor
    //equals trait
    //hash code trait
    //pretty print
    //deepClone
    //contexts
}


class IlBranchStmtDto_Unknown (
    target: Int,
    override val unknownId: RdId,
    val unknownBytes: ByteArray
) : IlBranchStmtDto (
    target
), IUnknownInstance {
    //companion
    
    companion object : IMarshaller<IlBranchStmtDto_Unknown> {
        override val _type: KClass<IlBranchStmtDto_Unknown> = IlBranchStmtDto_Unknown::class
        
        @Suppress("UNCHECKED_CAST")
        override fun read(ctx: SerializationCtx, buffer: AbstractBuffer): IlBranchStmtDto_Unknown  {
            throw NotImplementedError("Unknown instances should not be read via serializer")
        }
        
        override fun write(ctx: SerializationCtx, buffer: AbstractBuffer, value: IlBranchStmtDto_Unknown)  {
            buffer.writeInt(value.target)
            buffer.writeByteArrayRaw(value.unknownBytes)
        }
        
        
    }
    //fields
    //methods
    //initializer
    //secondary constructor
    //equals trait
    override fun equals(other: Any?): Boolean  {
        if (this === other) return true
        if (other == null || other::class != this::class) return false
        
        other as IlBranchStmtDto_Unknown
        
        if (target != other.target) return false
        
        return true
    }
    //hash code trait
    override fun hashCode(): Int  {
        var __r = 0
        __r = __r*31 + target.hashCode()
        return __r
    }
    //pretty print
    override fun print(printer: PrettyPrinter)  {
        printer.println("IlBranchStmtDto_Unknown (")
        printer.indent {
            print("target = "); target.print(printer); println()
        }
        printer.print(")")
    }
    
    override fun toString() = PrettyPrinter().singleLine().also { print(it) }.toString()
    //deepClone
    //contexts
}


/**
 * #### Generated from [IlMethodBodyModel.kt:32]
 */
class IlByteConstDto (
    val value: Byte,
    type: CacheKey
) : IlNumConstDto (
    type
) {
    //companion
    
    companion object : IMarshaller<IlByteConstDto> {
        override val _type: KClass<IlByteConstDto> = IlByteConstDto::class
        
        @Suppress("UNCHECKED_CAST")
        override fun read(ctx: SerializationCtx, buffer: AbstractBuffer): IlByteConstDto  {
            val type = CacheKey.read(ctx, buffer)
            val value = buffer.readByte()
            return IlByteConstDto(value, type)
        }
        
        override fun write(ctx: SerializationCtx, buffer: AbstractBuffer, value: IlByteConstDto)  {
            CacheKey.write(ctx, buffer, value.type)
            buffer.writeByte(value.value)
        }
        
        
    }
    //fields
    //methods
    //initializer
    //secondary constructor
    //equals trait
    override fun equals(other: Any?): Boolean  {
        if (this === other) return true
        if (other == null || other::class != this::class) return false
        
        other as IlByteConstDto
        
        if (value != other.value) return false
        if (type != other.type) return false
        
        return true
    }
    //hash code trait
    override fun hashCode(): Int  {
        var __r = 0
        __r = __r*31 + value.hashCode()
        __r = __r*31 + type.hashCode()
        return __r
    }
    //pretty print
    override fun print(printer: PrettyPrinter)  {
        printer.println("IlByteConstDto (")
        printer.indent {
            print("value = "); value.print(printer); println()
            print("type = "); type.print(printer); println()
        }
        printer.print(")")
    }
    
    override fun toString() = PrettyPrinter().singleLine().also { print(it) }.toString()
    //deepClone
    //contexts
}


/**
 * #### Generated from [IlMethodBodyModel.kt:78]
 */
class IlCallDto (
    val method: CacheKey,
    val args: List<IlExprDto>,
    type: CacheKey
) : IlExprDto (
    type
) {
    //companion
    
    companion object : IMarshaller<IlCallDto> {
        override val _type: KClass<IlCallDto> = IlCallDto::class
        
        @Suppress("UNCHECKED_CAST")
        override fun read(ctx: SerializationCtx, buffer: AbstractBuffer): IlCallDto  {
            val type = CacheKey.read(ctx, buffer)
            val method = CacheKey.read(ctx, buffer)
            val args = buffer.readList { ctx.serializers.readPolymorphic<IlExprDto>(ctx, buffer, IlExprDto) }
            return IlCallDto(method, args, type)
        }
        
        override fun write(ctx: SerializationCtx, buffer: AbstractBuffer, value: IlCallDto)  {
            CacheKey.write(ctx, buffer, value.type)
            CacheKey.write(ctx, buffer, value.method)
            buffer.writeList(value.args) { v -> ctx.serializers.writePolymorphic(ctx, buffer, v) }
        }
        
        
    }
    //fields
    //methods
    //initializer
    //secondary constructor
    //equals trait
    override fun equals(other: Any?): Boolean  {
        if (this === other) return true
        if (other == null || other::class != this::class) return false
        
        other as IlCallDto
        
        if (method != other.method) return false
        if (args != other.args) return false
        if (type != other.type) return false
        
        return true
    }
    //hash code trait
    override fun hashCode(): Int  {
        var __r = 0
        __r = __r*31 + method.hashCode()
        __r = __r*31 + args.hashCode()
        __r = __r*31 + type.hashCode()
        return __r
    }
    //pretty print
    override fun print(printer: PrettyPrinter)  {
        printer.println("IlCallDto (")
        printer.indent {
            print("method = "); method.print(printer); println()
            print("args = "); args.print(printer); println()
            print("type = "); type.print(printer); println()
        }
        printer.print(")")
    }
    
    override fun toString() = PrettyPrinter().singleLine().also { print(it) }.toString()
    //deepClone
    //contexts
}


/**
 * #### Generated from [IlMethodBodyModel.kt:118]
 */
class IlCallStmtDto (
    val call: IlCallDto
) : IlStmtDto (
) {
    //companion
    
    companion object : IMarshaller<IlCallStmtDto> {
        override val _type: KClass<IlCallStmtDto> = IlCallStmtDto::class
        
        @Suppress("UNCHECKED_CAST")
        override fun read(ctx: SerializationCtx, buffer: AbstractBuffer): IlCallStmtDto  {
            val call = IlCallDto.read(ctx, buffer)
            return IlCallStmtDto(call)
        }
        
        override fun write(ctx: SerializationCtx, buffer: AbstractBuffer, value: IlCallStmtDto)  {
            IlCallDto.write(ctx, buffer, value.call)
        }
        
        
    }
    //fields
    //methods
    //initializer
    //secondary constructor
    //equals trait
    override fun equals(other: Any?): Boolean  {
        if (this === other) return true
        if (other == null || other::class != this::class) return false
        
        other as IlCallStmtDto
        
        if (call != other.call) return false
        
        return true
    }
    //hash code trait
    override fun hashCode(): Int  {
        var __r = 0
        __r = __r*31 + call.hashCode()
        return __r
    }
    //pretty print
    override fun print(printer: PrettyPrinter)  {
        printer.println("IlCallStmtDto (")
        printer.indent {
            print("call = "); call.print(printer); println()
        }
        printer.print(")")
    }
    
    override fun toString() = PrettyPrinter().singleLine().also { print(it) }.toString()
    //deepClone
    //contexts
}


/**
 * #### Generated from [IlMethodBodyModel.kt:91]
 */
class IlCastClassExprDto (
    targetType: CacheKey,
    operand: IlExprDto,
    type: CacheKey
) : IlCastExprDto (
    targetType,
    operand,
    type
) {
    //companion
    
    companion object : IMarshaller<IlCastClassExprDto> {
        override val _type: KClass<IlCastClassExprDto> = IlCastClassExprDto::class
        
        @Suppress("UNCHECKED_CAST")
        override fun read(ctx: SerializationCtx, buffer: AbstractBuffer): IlCastClassExprDto  {
            val targetType = CacheKey.read(ctx, buffer)
            val operand = ctx.serializers.readPolymorphic<IlExprDto>(ctx, buffer, IlExprDto)
            val type = CacheKey.read(ctx, buffer)
            return IlCastClassExprDto(targetType, operand, type)
        }
        
        override fun write(ctx: SerializationCtx, buffer: AbstractBuffer, value: IlCastClassExprDto)  {
            CacheKey.write(ctx, buffer, value.targetType)
            ctx.serializers.writePolymorphic(ctx, buffer, value.operand)
            CacheKey.write(ctx, buffer, value.type)
        }
        
        
    }
    //fields
    //methods
    //initializer
    //secondary constructor
    //equals trait
    override fun equals(other: Any?): Boolean  {
        if (this === other) return true
        if (other == null || other::class != this::class) return false
        
        other as IlCastClassExprDto
        
        if (targetType != other.targetType) return false
        if (operand != other.operand) return false
        if (type != other.type) return false
        
        return true
    }
    //hash code trait
    override fun hashCode(): Int  {
        var __r = 0
        __r = __r*31 + targetType.hashCode()
        __r = __r*31 + operand.hashCode()
        __r = __r*31 + type.hashCode()
        return __r
    }
    //pretty print
    override fun print(printer: PrettyPrinter)  {
        printer.println("IlCastClassExprDto (")
        printer.indent {
            print("targetType = "); targetType.print(printer); println()
            print("operand = "); operand.print(printer); println()
            print("type = "); type.print(printer); println()
        }
        printer.print(")")
    }
    
    override fun toString() = PrettyPrinter().singleLine().also { print(it) }.toString()
    //deepClone
    //contexts
}


/**
 * #### Generated from [IlMethodBodyModel.kt:83]
 */
abstract class IlCastExprDto (
    val targetType: CacheKey,
    val operand: IlExprDto,
    type: CacheKey
) : IlExprDto (
    type
) {
    //companion
    
    companion object : IAbstractDeclaration<IlCastExprDto> {
        override fun readUnknownInstance(ctx: SerializationCtx, buffer: AbstractBuffer, unknownId: RdId, size: Int): IlCastExprDto  {
            val objectStartPosition = buffer.position
            val targetType = CacheKey.read(ctx, buffer)
            val operand = ctx.serializers.readPolymorphic<IlExprDto>(ctx, buffer, IlExprDto)
            val type = CacheKey.read(ctx, buffer)
            val unknownBytes = ByteArray(objectStartPosition + size - buffer.position)
            buffer.readByteArrayRaw(unknownBytes)
            return IlCastExprDto_Unknown(targetType, operand, type, unknownId, unknownBytes)
        }
        
        
    }
    //fields
    //methods
    //initializer
    //secondary constructor
    //equals trait
    //hash code trait
    //pretty print
    //deepClone
    //contexts
}


class IlCastExprDto_Unknown (
    targetType: CacheKey,
    operand: IlExprDto,
    type: CacheKey,
    override val unknownId: RdId,
    val unknownBytes: ByteArray
) : IlCastExprDto (
    targetType,
    operand,
    type
), IUnknownInstance {
    //companion
    
    companion object : IMarshaller<IlCastExprDto_Unknown> {
        override val _type: KClass<IlCastExprDto_Unknown> = IlCastExprDto_Unknown::class
        
        @Suppress("UNCHECKED_CAST")
        override fun read(ctx: SerializationCtx, buffer: AbstractBuffer): IlCastExprDto_Unknown  {
            throw NotImplementedError("Unknown instances should not be read via serializer")
        }
        
        override fun write(ctx: SerializationCtx, buffer: AbstractBuffer, value: IlCastExprDto_Unknown)  {
            CacheKey.write(ctx, buffer, value.targetType)
            ctx.serializers.writePolymorphic(ctx, buffer, value.operand)
            CacheKey.write(ctx, buffer, value.type)
            buffer.writeByteArrayRaw(value.unknownBytes)
        }
        
        
    }
    //fields
    //methods
    //initializer
    //secondary constructor
    //equals trait
    override fun equals(other: Any?): Boolean  {
        if (this === other) return true
        if (other == null || other::class != this::class) return false
        
        other as IlCastExprDto_Unknown
        
        if (targetType != other.targetType) return false
        if (operand != other.operand) return false
        if (type != other.type) return false
        
        return true
    }
    //hash code trait
    override fun hashCode(): Int  {
        var __r = 0
        __r = __r*31 + targetType.hashCode()
        __r = __r*31 + operand.hashCode()
        __r = __r*31 + type.hashCode()
        return __r
    }
    //pretty print
    override fun print(printer: PrettyPrinter)  {
        printer.println("IlCastExprDto_Unknown (")
        printer.indent {
            print("targetType = "); targetType.print(printer); println()
            print("operand = "); operand.print(printer); println()
            print("type = "); type.print(printer); println()
        }
        printer.print(")")
    }
    
    override fun toString() = PrettyPrinter().singleLine().also { print(it) }.toString()
    //deepClone
    //contexts
}


/**
 * #### Generated from [IlMethodBodyModel.kt:29]
 */
abstract class IlConstDto (
    type: CacheKey
) : IlValueDto (
    type
) {
    //companion
    
    companion object : IAbstractDeclaration<IlConstDto> {
        override fun readUnknownInstance(ctx: SerializationCtx, buffer: AbstractBuffer, unknownId: RdId, size: Int): IlConstDto  {
            val objectStartPosition = buffer.position
            val type = CacheKey.read(ctx, buffer)
            val unknownBytes = ByteArray(objectStartPosition + size - buffer.position)
            buffer.readByteArrayRaw(unknownBytes)
            return IlConstDto_Unknown(type, unknownId, unknownBytes)
        }
        
        
    }
    //fields
    //methods
    //initializer
    //secondary constructor
    //equals trait
    //hash code trait
    //pretty print
    //deepClone
    //contexts
}


class IlConstDto_Unknown (
    type: CacheKey,
    override val unknownId: RdId,
    val unknownBytes: ByteArray
) : IlConstDto (
    type
), IUnknownInstance {
    //companion
    
    companion object : IMarshaller<IlConstDto_Unknown> {
        override val _type: KClass<IlConstDto_Unknown> = IlConstDto_Unknown::class
        
        @Suppress("UNCHECKED_CAST")
        override fun read(ctx: SerializationCtx, buffer: AbstractBuffer): IlConstDto_Unknown  {
            throw NotImplementedError("Unknown instances should not be read via serializer")
        }
        
        override fun write(ctx: SerializationCtx, buffer: AbstractBuffer, value: IlConstDto_Unknown)  {
            CacheKey.write(ctx, buffer, value.type)
            buffer.writeByteArrayRaw(value.unknownBytes)
        }
        
        
    }
    //fields
    //methods
    //initializer
    //secondary constructor
    //equals trait
    override fun equals(other: Any?): Boolean  {
        if (this === other) return true
        if (other == null || other::class != this::class) return false
        
        other as IlConstDto_Unknown
        
        if (type != other.type) return false
        
        return true
    }
    //hash code trait
    override fun hashCode(): Int  {
        var __r = 0
        __r = __r*31 + type.hashCode()
        return __r
    }
    //pretty print
    override fun print(printer: PrettyPrinter)  {
        printer.println("IlConstDto_Unknown (")
        printer.indent {
            print("type = "); type.print(printer); println()
        }
        printer.print(")")
    }
    
    override fun toString() = PrettyPrinter().singleLine().also { print(it) }.toString()
    //deepClone
    //contexts
}


/**
 * #### Generated from [IlMethodBodyModel.kt:88]
 */
class IlConvExprDto (
    targetType: CacheKey,
    operand: IlExprDto,
    type: CacheKey
) : IlCastExprDto (
    targetType,
    operand,
    type
) {
    //companion
    
    companion object : IMarshaller<IlConvExprDto> {
        override val _type: KClass<IlConvExprDto> = IlConvExprDto::class
        
        @Suppress("UNCHECKED_CAST")
        override fun read(ctx: SerializationCtx, buffer: AbstractBuffer): IlConvExprDto  {
            val targetType = CacheKey.read(ctx, buffer)
            val operand = ctx.serializers.readPolymorphic<IlExprDto>(ctx, buffer, IlExprDto)
            val type = CacheKey.read(ctx, buffer)
            return IlConvExprDto(targetType, operand, type)
        }
        
        override fun write(ctx: SerializationCtx, buffer: AbstractBuffer, value: IlConvExprDto)  {
            CacheKey.write(ctx, buffer, value.targetType)
            ctx.serializers.writePolymorphic(ctx, buffer, value.operand)
            CacheKey.write(ctx, buffer, value.type)
        }
        
        
    }
    //fields
    //methods
    //initializer
    //secondary constructor
    //equals trait
    override fun equals(other: Any?): Boolean  {
        if (this === other) return true
        if (other == null || other::class != this::class) return false
        
        other as IlConvExprDto
        
        if (targetType != other.targetType) return false
        if (operand != other.operand) return false
        if (type != other.type) return false
        
        return true
    }
    //hash code trait
    override fun hashCode(): Int  {
        var __r = 0
        __r = __r*31 + targetType.hashCode()
        __r = __r*31 + operand.hashCode()
        __r = __r*31 + type.hashCode()
        return __r
    }
    //pretty print
    override fun print(printer: PrettyPrinter)  {
        printer.println("IlConvExprDto (")
        printer.indent {
            print("targetType = "); targetType.print(printer); println()
            print("operand = "); operand.print(printer); println()
            print("type = "); type.print(printer); println()
        }
        printer.print(")")
    }
    
    override fun toString() = PrettyPrinter().singleLine().also { print(it) }.toString()
    //deepClone
    //contexts
}


/**
 * #### Generated from [IlMethodBodyModel.kt:98]
 */
abstract class IlDerefExprDto (
    val value: IlExprDto,
    type: CacheKey
) : IlValueDto (
    type
) {
    //companion
    
    companion object : IAbstractDeclaration<IlDerefExprDto> {
        override fun readUnknownInstance(ctx: SerializationCtx, buffer: AbstractBuffer, unknownId: RdId, size: Int): IlDerefExprDto  {
            val objectStartPosition = buffer.position
            val value = ctx.serializers.readPolymorphic<IlExprDto>(ctx, buffer, IlExprDto)
            val type = CacheKey.read(ctx, buffer)
            val unknownBytes = ByteArray(objectStartPosition + size - buffer.position)
            buffer.readByteArrayRaw(unknownBytes)
            return IlDerefExprDto_Unknown(value, type, unknownId, unknownBytes)
        }
        
        
    }
    //fields
    //methods
    //initializer
    //secondary constructor
    //equals trait
    //hash code trait
    //pretty print
    //deepClone
    //contexts
}


class IlDerefExprDto_Unknown (
    value: IlExprDto,
    type: CacheKey,
    override val unknownId: RdId,
    val unknownBytes: ByteArray
) : IlDerefExprDto (
    value,
    type
), IUnknownInstance {
    //companion
    
    companion object : IMarshaller<IlDerefExprDto_Unknown> {
        override val _type: KClass<IlDerefExprDto_Unknown> = IlDerefExprDto_Unknown::class
        
        @Suppress("UNCHECKED_CAST")
        override fun read(ctx: SerializationCtx, buffer: AbstractBuffer): IlDerefExprDto_Unknown  {
            throw NotImplementedError("Unknown instances should not be read via serializer")
        }
        
        override fun write(ctx: SerializationCtx, buffer: AbstractBuffer, value: IlDerefExprDto_Unknown)  {
            ctx.serializers.writePolymorphic(ctx, buffer, value.value)
            CacheKey.write(ctx, buffer, value.type)
            buffer.writeByteArrayRaw(value.unknownBytes)
        }
        
        
    }
    //fields
    //methods
    //initializer
    //secondary constructor
    //equals trait
    override fun equals(other: Any?): Boolean  {
        if (this === other) return true
        if (other == null || other::class != this::class) return false
        
        other as IlDerefExprDto_Unknown
        
        if (value != other.value) return false
        if (type != other.type) return false
        
        return true
    }
    //hash code trait
    override fun hashCode(): Int  {
        var __r = 0
        __r = __r*31 + value.hashCode()
        __r = __r*31 + type.hashCode()
        return __r
    }
    //pretty print
    override fun print(printer: PrettyPrinter)  {
        printer.println("IlDerefExprDto_Unknown (")
        printer.indent {
            print("value = "); value.print(printer); println()
            print("type = "); type.print(printer); println()
        }
        printer.print(")")
    }
    
    override fun toString() = PrettyPrinter().singleLine().also { print(it) }.toString()
    //deepClone
    //contexts
}


/**
 * #### Generated from [IlMethodBodyModel.kt:36]
 */
class IlDoubleConstDto (
    val value: Double,
    type: CacheKey
) : IlNumConstDto (
    type
) {
    //companion
    
    companion object : IMarshaller<IlDoubleConstDto> {
        override val _type: KClass<IlDoubleConstDto> = IlDoubleConstDto::class
        
        @Suppress("UNCHECKED_CAST")
        override fun read(ctx: SerializationCtx, buffer: AbstractBuffer): IlDoubleConstDto  {
            val type = CacheKey.read(ctx, buffer)
            val value = buffer.readDouble()
            return IlDoubleConstDto(value, type)
        }
        
        override fun write(ctx: SerializationCtx, buffer: AbstractBuffer, value: IlDoubleConstDto)  {
            CacheKey.write(ctx, buffer, value.type)
            buffer.writeDouble(value.value)
        }
        
        
    }
    //fields
    //methods
    //initializer
    //secondary constructor
    //equals trait
    override fun equals(other: Any?): Boolean  {
        if (this === other) return true
        if (other == null || other::class != this::class) return false
        
        other as IlDoubleConstDto
        
        if (value != other.value) return false
        if (type != other.type) return false
        
        return true
    }
    //hash code trait
    override fun hashCode(): Int  {
        var __r = 0
        __r = __r*31 + value.hashCode()
        __r = __r*31 + type.hashCode()
        return __r
    }
    //pretty print
    override fun print(printer: PrettyPrinter)  {
        printer.println("IlDoubleConstDto (")
        printer.indent {
            print("value = "); value.print(printer); println()
            print("type = "); type.print(printer); println()
        }
        printer.print(")")
    }
    
    override fun toString() = PrettyPrinter().singleLine().also { print(it) }.toString()
    //deepClone
    //contexts
}


/**
 * #### Generated from [IlMethodBodyModel.kt:126]
 */
abstract class IlEhStmtDto (
) : IlStmtDto (
) {
    //companion
    
    companion object : IAbstractDeclaration<IlEhStmtDto> {
        override fun readUnknownInstance(ctx: SerializationCtx, buffer: AbstractBuffer, unknownId: RdId, size: Int): IlEhStmtDto  {
            val objectStartPosition = buffer.position
            val unknownBytes = ByteArray(objectStartPosition + size - buffer.position)
            buffer.readByteArrayRaw(unknownBytes)
            return IlEhStmtDto_Unknown(unknownId, unknownBytes)
        }
        
        
    }
    //fields
    //methods
    //initializer
    //secondary constructor
    //equals trait
    //hash code trait
    //pretty print
    //deepClone
    //contexts
}


class IlEhStmtDto_Unknown (
    override val unknownId: RdId,
    val unknownBytes: ByteArray
) : IlEhStmtDto (
), IUnknownInstance {
    //companion
    
    companion object : IMarshaller<IlEhStmtDto_Unknown> {
        override val _type: KClass<IlEhStmtDto_Unknown> = IlEhStmtDto_Unknown::class
        
        @Suppress("UNCHECKED_CAST")
        override fun read(ctx: SerializationCtx, buffer: AbstractBuffer): IlEhStmtDto_Unknown  {
            throw NotImplementedError("Unknown instances should not be read via serializer")
        }
        
        override fun write(ctx: SerializationCtx, buffer: AbstractBuffer, value: IlEhStmtDto_Unknown)  {
            buffer.writeByteArrayRaw(value.unknownBytes)
        }
        
        
    }
    //fields
    //methods
    //initializer
    //secondary constructor
    //equals trait
    override fun equals(other: Any?): Boolean  {
        if (this === other) return true
        if (other == null || other::class != this::class) return false
        
        other as IlEhStmtDto_Unknown
        
        
        return true
    }
    //hash code trait
    override fun hashCode(): Int  {
        var __r = 0
        return __r
    }
    //pretty print
    override fun print(printer: PrettyPrinter)  {
        printer.println("IlEhStmtDto_Unknown (")
        printer.print(")")
    }
    
    override fun toString() = PrettyPrinter().singleLine().also { print(it) }.toString()
    //deepClone
    //contexts
}


/**
 * #### Generated from [IlMethodBodyModel.kt:132]
 */
class IlEndFaultStmtDto (
) : IlEhStmtDto (
) {
    //companion
    
    companion object : IMarshaller<IlEndFaultStmtDto> {
        override val _type: KClass<IlEndFaultStmtDto> = IlEndFaultStmtDto::class
        
        @Suppress("UNCHECKED_CAST")
        override fun read(ctx: SerializationCtx, buffer: AbstractBuffer): IlEndFaultStmtDto  {
            return IlEndFaultStmtDto()
        }
        
        override fun write(ctx: SerializationCtx, buffer: AbstractBuffer, value: IlEndFaultStmtDto)  {
        }
        
        
    }
    //fields
    //methods
    //initializer
    //secondary constructor
    //equals trait
    override fun equals(other: Any?): Boolean  {
        if (this === other) return true
        if (other == null || other::class != this::class) return false
        
        other as IlEndFaultStmtDto
        
        
        return true
    }
    //hash code trait
    override fun hashCode(): Int  {
        var __r = 0
        return __r
    }
    //pretty print
    override fun print(printer: PrettyPrinter)  {
        printer.println("IlEndFaultStmtDto (")
        printer.print(")")
    }
    
    override fun toString() = PrettyPrinter().singleLine().also { print(it) }.toString()
    //deepClone
    //contexts
}


/**
 * #### Generated from [IlMethodBodyModel.kt:133]
 */
class IlEndFilterStmtDto (
    val value: IlExprDto
) : IlEhStmtDto (
) {
    //companion
    
    companion object : IMarshaller<IlEndFilterStmtDto> {
        override val _type: KClass<IlEndFilterStmtDto> = IlEndFilterStmtDto::class
        
        @Suppress("UNCHECKED_CAST")
        override fun read(ctx: SerializationCtx, buffer: AbstractBuffer): IlEndFilterStmtDto  {
            val value = ctx.serializers.readPolymorphic<IlExprDto>(ctx, buffer, IlExprDto)
            return IlEndFilterStmtDto(value)
        }
        
        override fun write(ctx: SerializationCtx, buffer: AbstractBuffer, value: IlEndFilterStmtDto)  {
            ctx.serializers.writePolymorphic(ctx, buffer, value.value)
        }
        
        
    }
    //fields
    //methods
    //initializer
    //secondary constructor
    //equals trait
    override fun equals(other: Any?): Boolean  {
        if (this === other) return true
        if (other == null || other::class != this::class) return false
        
        other as IlEndFilterStmtDto
        
        if (value != other.value) return false
        
        return true
    }
    //hash code trait
    override fun hashCode(): Int  {
        var __r = 0
        __r = __r*31 + value.hashCode()
        return __r
    }
    //pretty print
    override fun print(printer: PrettyPrinter)  {
        printer.println("IlEndFilterStmtDto (")
        printer.indent {
            print("value = "); value.print(printer); println()
        }
        printer.print(")")
    }
    
    override fun toString() = PrettyPrinter().singleLine().also { print(it) }.toString()
    //deepClone
    //contexts
}


/**
 * #### Generated from [IlMethodBodyModel.kt:131]
 */
class IlEndFinallyStmtDto (
) : IlEhStmtDto (
) {
    //companion
    
    companion object : IMarshaller<IlEndFinallyStmtDto> {
        override val _type: KClass<IlEndFinallyStmtDto> = IlEndFinallyStmtDto::class
        
        @Suppress("UNCHECKED_CAST")
        override fun read(ctx: SerializationCtx, buffer: AbstractBuffer): IlEndFinallyStmtDto  {
            return IlEndFinallyStmtDto()
        }
        
        override fun write(ctx: SerializationCtx, buffer: AbstractBuffer, value: IlEndFinallyStmtDto)  {
        }
        
        
    }
    //fields
    //methods
    //initializer
    //secondary constructor
    //equals trait
    override fun equals(other: Any?): Boolean  {
        if (this === other) return true
        if (other == null || other::class != this::class) return false
        
        other as IlEndFinallyStmtDto
        
        
        return true
    }
    //hash code trait
    override fun hashCode(): Int  {
        var __r = 0
        return __r
    }
    //pretty print
    override fun print(printer: PrettyPrinter)  {
        printer.println("IlEndFinallyStmtDto (")
        printer.print(")")
    }
    
    override fun toString() = PrettyPrinter().singleLine().also { print(it) }.toString()
    //deepClone
    //contexts
}


/**
 * #### Generated from [IlMethodBodyModel.kt:25]
 */
abstract class IlExprDto (
    val type: CacheKey
) : IPrintable {
    //companion
    
    companion object : IAbstractDeclaration<IlExprDto> {
        override fun readUnknownInstance(ctx: SerializationCtx, buffer: AbstractBuffer, unknownId: RdId, size: Int): IlExprDto  {
            val objectStartPosition = buffer.position
            val type = CacheKey.read(ctx, buffer)
            val unknownBytes = ByteArray(objectStartPosition + size - buffer.position)
            buffer.readByteArrayRaw(unknownBytes)
            return IlExprDto_Unknown(type, unknownId, unknownBytes)
        }
        
        
    }
    //fields
    //methods
    //initializer
    //secondary constructor
    //equals trait
    //hash code trait
    //pretty print
    //deepClone
    //contexts
}


class IlExprDto_Unknown (
    type: CacheKey,
    override val unknownId: RdId,
    val unknownBytes: ByteArray
) : IlExprDto (
    type
), IUnknownInstance {
    //companion
    
    companion object : IMarshaller<IlExprDto_Unknown> {
        override val _type: KClass<IlExprDto_Unknown> = IlExprDto_Unknown::class
        
        @Suppress("UNCHECKED_CAST")
        override fun read(ctx: SerializationCtx, buffer: AbstractBuffer): IlExprDto_Unknown  {
            throw NotImplementedError("Unknown instances should not be read via serializer")
        }
        
        override fun write(ctx: SerializationCtx, buffer: AbstractBuffer, value: IlExprDto_Unknown)  {
            CacheKey.write(ctx, buffer, value.type)
            buffer.writeByteArrayRaw(value.unknownBytes)
        }
        
        
    }
    //fields
    //methods
    //initializer
    //secondary constructor
    //equals trait
    override fun equals(other: Any?): Boolean  {
        if (this === other) return true
        if (other == null || other::class != this::class) return false
        
        other as IlExprDto_Unknown
        
        if (type != other.type) return false
        
        return true
    }
    //hash code trait
    override fun hashCode(): Int  {
        var __r = 0
        __r = __r*31 + type.hashCode()
        return __r
    }
    //pretty print
    override fun print(printer: PrettyPrinter)  {
        printer.println("IlExprDto_Unknown (")
        printer.indent {
            print("type = "); type.print(printer); println()
        }
        printer.print(")")
    }
    
    override fun toString() = PrettyPrinter().singleLine().also { print(it) }.toString()
    //deepClone
    //contexts
}


/**
 * #### Generated from [IlMethodBodyModel.kt:64]
 */
class IlFieldAccessDto (
    val instance: IlExprDto?,
    val `field`: CacheKey,
    type: CacheKey
) : IlValueDto (
    type
) {
    //companion
    
    companion object : IMarshaller<IlFieldAccessDto> {
        override val _type: KClass<IlFieldAccessDto> = IlFieldAccessDto::class
        
        @Suppress("UNCHECKED_CAST")
        override fun read(ctx: SerializationCtx, buffer: AbstractBuffer): IlFieldAccessDto  {
            val type = CacheKey.read(ctx, buffer)
            val instance = buffer.readNullable { ctx.serializers.readPolymorphic<IlExprDto>(ctx, buffer, IlExprDto) }
            val `field` = CacheKey.read(ctx, buffer)
            return IlFieldAccessDto(instance, `field`, type)
        }
        
        override fun write(ctx: SerializationCtx, buffer: AbstractBuffer, value: IlFieldAccessDto)  {
            CacheKey.write(ctx, buffer, value.type)
            buffer.writeNullable(value.instance) { ctx.serializers.writePolymorphic(ctx, buffer, it) }
            CacheKey.write(ctx, buffer, value.`field`)
        }
        
        
    }
    //fields
    //methods
    //initializer
    //secondary constructor
    //equals trait
    override fun equals(other: Any?): Boolean  {
        if (this === other) return true
        if (other == null || other::class != this::class) return false
        
        other as IlFieldAccessDto
        
        if (instance != other.instance) return false
        if (`field` != other.`field`) return false
        if (type != other.type) return false
        
        return true
    }
    //hash code trait
    override fun hashCode(): Int  {
        var __r = 0
        __r = __r*31 + if (instance != null) instance.hashCode() else 0
        __r = __r*31 + `field`.hashCode()
        __r = __r*31 + type.hashCode()
        return __r
    }
    //pretty print
    override fun print(printer: PrettyPrinter)  {
        printer.println("IlFieldAccessDto (")
        printer.indent {
            print("instance = "); instance.print(printer); println()
            print("field = "); `field`.print(printer); println()
            print("type = "); type.print(printer); println()
        }
        printer.print(")")
    }
    
    override fun toString() = PrettyPrinter().singleLine().also { print(it) }.toString()
    //deepClone
    //contexts
}


/**
 * #### Generated from [IlMethodBodyModel.kt:45]
 */
class IlFieldRefDto (
    val `field`: CacheKey,
    type: CacheKey
) : IlConstDto (
    type
) {
    //companion
    
    companion object : IMarshaller<IlFieldRefDto> {
        override val _type: KClass<IlFieldRefDto> = IlFieldRefDto::class
        
        @Suppress("UNCHECKED_CAST")
        override fun read(ctx: SerializationCtx, buffer: AbstractBuffer): IlFieldRefDto  {
            val type = CacheKey.read(ctx, buffer)
            val `field` = CacheKey.read(ctx, buffer)
            return IlFieldRefDto(`field`, type)
        }
        
        override fun write(ctx: SerializationCtx, buffer: AbstractBuffer, value: IlFieldRefDto)  {
            CacheKey.write(ctx, buffer, value.type)
            CacheKey.write(ctx, buffer, value.`field`)
        }
        
        
    }
    //fields
    //methods
    //initializer
    //secondary constructor
    //equals trait
    override fun equals(other: Any?): Boolean  {
        if (this === other) return true
        if (other == null || other::class != this::class) return false
        
        other as IlFieldRefDto
        
        if (`field` != other.`field`) return false
        if (type != other.type) return false
        
        return true
    }
    //hash code trait
    override fun hashCode(): Int  {
        var __r = 0
        __r = __r*31 + `field`.hashCode()
        __r = __r*31 + type.hashCode()
        return __r
    }
    //pretty print
    override fun print(printer: PrettyPrinter)  {
        printer.println("IlFieldRefDto (")
        printer.indent {
            print("field = "); `field`.print(printer); println()
            print("type = "); type.print(printer); println()
        }
        printer.print(")")
    }
    
    override fun toString() = PrettyPrinter().singleLine().also { print(it) }.toString()
    //deepClone
    //contexts
}


/**
 * #### Generated from [IlMethodBodyModel.kt:35]
 */
class IlFloatConstDto (
    val value: Float,
    type: CacheKey
) : IlNumConstDto (
    type
) {
    //companion
    
    companion object : IMarshaller<IlFloatConstDto> {
        override val _type: KClass<IlFloatConstDto> = IlFloatConstDto::class
        
        @Suppress("UNCHECKED_CAST")
        override fun read(ctx: SerializationCtx, buffer: AbstractBuffer): IlFloatConstDto  {
            val type = CacheKey.read(ctx, buffer)
            val value = buffer.readFloat()
            return IlFloatConstDto(value, type)
        }
        
        override fun write(ctx: SerializationCtx, buffer: AbstractBuffer, value: IlFloatConstDto)  {
            CacheKey.write(ctx, buffer, value.type)
            buffer.writeFloat(value.value)
        }
        
        
    }
    //fields
    //methods
    //initializer
    //secondary constructor
    //equals trait
    override fun equals(other: Any?): Boolean  {
        if (this === other) return true
        if (other == null || other::class != this::class) return false
        
        other as IlFloatConstDto
        
        if (value != other.value) return false
        if (type != other.type) return false
        
        return true
    }
    //hash code trait
    override fun hashCode(): Int  {
        var __r = 0
        __r = __r*31 + value.hashCode()
        __r = __r*31 + type.hashCode()
        return __r
    }
    //pretty print
    override fun print(printer: PrettyPrinter)  {
        printer.println("IlFloatConstDto (")
        printer.indent {
            print("value = "); value.print(printer); println()
            print("type = "); type.print(printer); println()
        }
        printer.print(")")
    }
    
    override fun toString() = PrettyPrinter().singleLine().also { print(it) }.toString()
    //deepClone
    //contexts
}


/**
 * #### Generated from [IlMethodBodyModel.kt:141]
 */
class IlGotoStmtDto (
    target: Int
) : IlBranchStmtDto (
    target
) {
    //companion
    
    companion object : IMarshaller<IlGotoStmtDto> {
        override val _type: KClass<IlGotoStmtDto> = IlGotoStmtDto::class
        
        @Suppress("UNCHECKED_CAST")
        override fun read(ctx: SerializationCtx, buffer: AbstractBuffer): IlGotoStmtDto  {
            val target = buffer.readInt()
            return IlGotoStmtDto(target)
        }
        
        override fun write(ctx: SerializationCtx, buffer: AbstractBuffer, value: IlGotoStmtDto)  {
            buffer.writeInt(value.target)
        }
        
        
    }
    //fields
    //methods
    //initializer
    //secondary constructor
    //equals trait
    override fun equals(other: Any?): Boolean  {
        if (this === other) return true
        if (other == null || other::class != this::class) return false
        
        other as IlGotoStmtDto
        
        if (target != other.target) return false
        
        return true
    }
    //hash code trait
    override fun hashCode(): Int  {
        var __r = 0
        __r = __r*31 + target.hashCode()
        return __r
    }
    //pretty print
    override fun print(printer: PrettyPrinter)  {
        printer.println("IlGotoStmtDto (")
        printer.indent {
            print("target = "); target.print(printer); println()
        }
        printer.print(")")
    }
    
    override fun toString() = PrettyPrinter().singleLine().also { print(it) }.toString()
    //deepClone
    //contexts
}


/**
 * #### Generated from [IlMethodBodyModel.kt:144]
 */
class IlIfStmtDto (
    val cond: IlExprDto,
    target: Int
) : IlBranchStmtDto (
    target
) {
    //companion
    
    companion object : IMarshaller<IlIfStmtDto> {
        override val _type: KClass<IlIfStmtDto> = IlIfStmtDto::class
        
        @Suppress("UNCHECKED_CAST")
        override fun read(ctx: SerializationCtx, buffer: AbstractBuffer): IlIfStmtDto  {
            val target = buffer.readInt()
            val cond = ctx.serializers.readPolymorphic<IlExprDto>(ctx, buffer, IlExprDto)
            return IlIfStmtDto(cond, target)
        }
        
        override fun write(ctx: SerializationCtx, buffer: AbstractBuffer, value: IlIfStmtDto)  {
            buffer.writeInt(value.target)
            ctx.serializers.writePolymorphic(ctx, buffer, value.cond)
        }
        
        
    }
    //fields
    //methods
    //initializer
    //secondary constructor
    //equals trait
    override fun equals(other: Any?): Boolean  {
        if (this === other) return true
        if (other == null || other::class != this::class) return false
        
        other as IlIfStmtDto
        
        if (cond != other.cond) return false
        if (target != other.target) return false
        
        return true
    }
    //hash code trait
    override fun hashCode(): Int  {
        var __r = 0
        __r = __r*31 + cond.hashCode()
        __r = __r*31 + target.hashCode()
        return __r
    }
    //pretty print
    override fun print(printer: PrettyPrinter)  {
        printer.println("IlIfStmtDto (")
        printer.indent {
            print("cond = "); cond.print(printer); println()
            print("target = "); target.print(printer); println()
        }
        printer.print(")")
    }
    
    override fun toString() = PrettyPrinter().singleLine().also { print(it) }.toString()
    //deepClone
    //contexts
}


/**
 * #### Generated from [IlMethodBodyModel.kt:53]
 */
class IlInitExprDto (
    type: CacheKey
) : IlExprDto (
    type
) {
    //companion
    
    companion object : IMarshaller<IlInitExprDto> {
        override val _type: KClass<IlInitExprDto> = IlInitExprDto::class
        
        @Suppress("UNCHECKED_CAST")
        override fun read(ctx: SerializationCtx, buffer: AbstractBuffer): IlInitExprDto  {
            val type = CacheKey.read(ctx, buffer)
            return IlInitExprDto(type)
        }
        
        override fun write(ctx: SerializationCtx, buffer: AbstractBuffer, value: IlInitExprDto)  {
            CacheKey.write(ctx, buffer, value.type)
        }
        
        
    }
    //fields
    //methods
    //initializer
    //secondary constructor
    //equals trait
    override fun equals(other: Any?): Boolean  {
        if (this === other) return true
        if (other == null || other::class != this::class) return false
        
        other as IlInitExprDto
        
        if (type != other.type) return false
        
        return true
    }
    //hash code trait
    override fun hashCode(): Int  {
        var __r = 0
        __r = __r*31 + type.hashCode()
        return __r
    }
    //pretty print
    override fun print(printer: PrettyPrinter)  {
        printer.println("IlInitExprDto (")
        printer.indent {
            print("type = "); type.print(printer); println()
        }
        printer.print(")")
    }
    
    override fun toString() = PrettyPrinter().singleLine().also { print(it) }.toString()
    //deepClone
    //contexts
}


/**
 * #### Generated from [IlMethodBodyModel.kt:33]
 */
class IlIntConstDto (
    val value: Int,
    type: CacheKey
) : IlNumConstDto (
    type
) {
    //companion
    
    companion object : IMarshaller<IlIntConstDto> {
        override val _type: KClass<IlIntConstDto> = IlIntConstDto::class
        
        @Suppress("UNCHECKED_CAST")
        override fun read(ctx: SerializationCtx, buffer: AbstractBuffer): IlIntConstDto  {
            val type = CacheKey.read(ctx, buffer)
            val value = buffer.readInt()
            return IlIntConstDto(value, type)
        }
        
        override fun write(ctx: SerializationCtx, buffer: AbstractBuffer, value: IlIntConstDto)  {
            CacheKey.write(ctx, buffer, value.type)
            buffer.writeInt(value.value)
        }
        
        
    }
    //fields
    //methods
    //initializer
    //secondary constructor
    //equals trait
    override fun equals(other: Any?): Boolean  {
        if (this === other) return true
        if (other == null || other::class != this::class) return false
        
        other as IlIntConstDto
        
        if (value != other.value) return false
        if (type != other.type) return false
        
        return true
    }
    //hash code trait
    override fun hashCode(): Int  {
        var __r = 0
        __r = __r*31 + value.hashCode()
        __r = __r*31 + type.hashCode()
        return __r
    }
    //pretty print
    override fun print(printer: PrettyPrinter)  {
        printer.println("IlIntConstDto (")
        printer.indent {
            print("value = "); value.print(printer); println()
            print("type = "); type.print(printer); println()
        }
        printer.print(")")
    }
    
    override fun toString() = PrettyPrinter().singleLine().also { print(it) }.toString()
    //deepClone
    //contexts
}


/**
 * #### Generated from [IlMethodBodyModel.kt:92]
 */
class IlIsInstExprDto (
    targetType: CacheKey,
    operand: IlExprDto,
    type: CacheKey
) : IlCastExprDto (
    targetType,
    operand,
    type
) {
    //companion
    
    companion object : IMarshaller<IlIsInstExprDto> {
        override val _type: KClass<IlIsInstExprDto> = IlIsInstExprDto::class
        
        @Suppress("UNCHECKED_CAST")
        override fun read(ctx: SerializationCtx, buffer: AbstractBuffer): IlIsInstExprDto  {
            val targetType = CacheKey.read(ctx, buffer)
            val operand = ctx.serializers.readPolymorphic<IlExprDto>(ctx, buffer, IlExprDto)
            val type = CacheKey.read(ctx, buffer)
            return IlIsInstExprDto(targetType, operand, type)
        }
        
        override fun write(ctx: SerializationCtx, buffer: AbstractBuffer, value: IlIsInstExprDto)  {
            CacheKey.write(ctx, buffer, value.targetType)
            ctx.serializers.writePolymorphic(ctx, buffer, value.operand)
            CacheKey.write(ctx, buffer, value.type)
        }
        
        
    }
    //fields
    //methods
    //initializer
    //secondary constructor
    //equals trait
    override fun equals(other: Any?): Boolean  {
        if (this === other) return true
        if (other == null || other::class != this::class) return false
        
        other as IlIsInstExprDto
        
        if (targetType != other.targetType) return false
        if (operand != other.operand) return false
        if (type != other.type) return false
        
        return true
    }
    //hash code trait
    override fun hashCode(): Int  {
        var __r = 0
        __r = __r*31 + targetType.hashCode()
        __r = __r*31 + operand.hashCode()
        __r = __r*31 + type.hashCode()
        return __r
    }
    //pretty print
    override fun print(printer: PrettyPrinter)  {
        printer.println("IlIsInstExprDto (")
        printer.indent {
            print("targetType = "); targetType.print(printer); println()
            print("operand = "); operand.print(printer); println()
            print("type = "); type.print(printer); println()
        }
        printer.print(")")
    }
    
    override fun toString() = PrettyPrinter().singleLine().also { print(it) }.toString()
    //deepClone
    //contexts
}


/**
 * #### Generated from [IlMethodBodyModel.kt:34]
 */
class IlLongConstDto (
    val value: Long,
    type: CacheKey
) : IlNumConstDto (
    type
) {
    //companion
    
    companion object : IMarshaller<IlLongConstDto> {
        override val _type: KClass<IlLongConstDto> = IlLongConstDto::class
        
        @Suppress("UNCHECKED_CAST")
        override fun read(ctx: SerializationCtx, buffer: AbstractBuffer): IlLongConstDto  {
            val type = CacheKey.read(ctx, buffer)
            val value = buffer.readLong()
            return IlLongConstDto(value, type)
        }
        
        override fun write(ctx: SerializationCtx, buffer: AbstractBuffer, value: IlLongConstDto)  {
            CacheKey.write(ctx, buffer, value.type)
            buffer.writeLong(value.value)
        }
        
        
    }
    //fields
    //methods
    //initializer
    //secondary constructor
    //equals trait
    override fun equals(other: Any?): Boolean  {
        if (this === other) return true
        if (other == null || other::class != this::class) return false
        
        other as IlLongConstDto
        
        if (value != other.value) return false
        if (type != other.type) return false
        
        return true
    }
    //hash code trait
    override fun hashCode(): Int  {
        var __r = 0
        __r = __r*31 + value.hashCode()
        __r = __r*31 + type.hashCode()
        return __r
    }
    //pretty print
    override fun print(printer: PrettyPrinter)  {
        printer.println("IlLongConstDto (")
        printer.indent {
            print("value = "); value.print(printer); println()
            print("type = "); type.print(printer); println()
        }
        printer.print(")")
    }
    
    override fun toString() = PrettyPrinter().singleLine().also { print(it) }.toString()
    //deepClone
    //contexts
}


/**
 * #### Generated from [IlMethodBodyModel.kt:104]
 */
class IlManagedDerefExprDto (
    value: IlExprDto,
    type: CacheKey
) : IlDerefExprDto (
    value,
    type
) {
    //companion
    
    companion object : IMarshaller<IlManagedDerefExprDto> {
        override val _type: KClass<IlManagedDerefExprDto> = IlManagedDerefExprDto::class
        
        @Suppress("UNCHECKED_CAST")
        override fun read(ctx: SerializationCtx, buffer: AbstractBuffer): IlManagedDerefExprDto  {
            val value = ctx.serializers.readPolymorphic<IlExprDto>(ctx, buffer, IlExprDto)
            val type = CacheKey.read(ctx, buffer)
            return IlManagedDerefExprDto(value, type)
        }
        
        override fun write(ctx: SerializationCtx, buffer: AbstractBuffer, value: IlManagedDerefExprDto)  {
            ctx.serializers.writePolymorphic(ctx, buffer, value.value)
            CacheKey.write(ctx, buffer, value.type)
        }
        
        
    }
    //fields
    //methods
    //initializer
    //secondary constructor
    //equals trait
    override fun equals(other: Any?): Boolean  {
        if (this === other) return true
        if (other == null || other::class != this::class) return false
        
        other as IlManagedDerefExprDto
        
        if (value != other.value) return false
        if (type != other.type) return false
        
        return true
    }
    //hash code trait
    override fun hashCode(): Int  {
        var __r = 0
        __r = __r*31 + value.hashCode()
        __r = __r*31 + type.hashCode()
        return __r
    }
    //pretty print
    override fun print(printer: PrettyPrinter)  {
        printer.println("IlManagedDerefExprDto (")
        printer.indent {
            print("value = "); value.print(printer); println()
            print("type = "); type.print(printer); println()
        }
        printer.print(")")
    }
    
    override fun toString() = PrettyPrinter().singleLine().also { print(it) }.toString()
    //deepClone
    //contexts
}


/**
 * #### Generated from [IlMethodBodyModel.kt:102]
 */
class IlManagedRefExprDto (
    value: IlExprDto,
    type: CacheKey
) : IlRefExprDto (
    value,
    type
) {
    //companion
    
    companion object : IMarshaller<IlManagedRefExprDto> {
        override val _type: KClass<IlManagedRefExprDto> = IlManagedRefExprDto::class
        
        @Suppress("UNCHECKED_CAST")
        override fun read(ctx: SerializationCtx, buffer: AbstractBuffer): IlManagedRefExprDto  {
            val value = ctx.serializers.readPolymorphic<IlExprDto>(ctx, buffer, IlExprDto)
            val type = CacheKey.read(ctx, buffer)
            return IlManagedRefExprDto(value, type)
        }
        
        override fun write(ctx: SerializationCtx, buffer: AbstractBuffer, value: IlManagedRefExprDto)  {
            ctx.serializers.writePolymorphic(ctx, buffer, value.value)
            CacheKey.write(ctx, buffer, value.type)
        }
        
        
    }
    //fields
    //methods
    //initializer
    //secondary constructor
    //equals trait
    override fun equals(other: Any?): Boolean  {
        if (this === other) return true
        if (other == null || other::class != this::class) return false
        
        other as IlManagedRefExprDto
        
        if (value != other.value) return false
        if (type != other.type) return false
        
        return true
    }
    //hash code trait
    override fun hashCode(): Int  {
        var __r = 0
        __r = __r*31 + value.hashCode()
        __r = __r*31 + type.hashCode()
        return __r
    }
    //pretty print
    override fun print(printer: PrettyPrinter)  {
        printer.println("IlManagedRefExprDto (")
        printer.indent {
            print("value = "); value.print(printer); println()
            print("type = "); type.print(printer); println()
        }
        printer.print(")")
    }
    
    override fun toString() = PrettyPrinter().singleLine().also { print(it) }.toString()
    //deepClone
    //contexts
}


/**
 * #### Generated from [IlMethodBodyModel.kt:44]
 */
class IlMethodRefDto (
    val method: CacheKey,
    type: CacheKey
) : IlConstDto (
    type
) {
    //companion
    
    companion object : IMarshaller<IlMethodRefDto> {
        override val _type: KClass<IlMethodRefDto> = IlMethodRefDto::class
        
        @Suppress("UNCHECKED_CAST")
        override fun read(ctx: SerializationCtx, buffer: AbstractBuffer): IlMethodRefDto  {
            val type = CacheKey.read(ctx, buffer)
            val method = CacheKey.read(ctx, buffer)
            return IlMethodRefDto(method, type)
        }
        
        override fun write(ctx: SerializationCtx, buffer: AbstractBuffer, value: IlMethodRefDto)  {
            CacheKey.write(ctx, buffer, value.type)
            CacheKey.write(ctx, buffer, value.method)
        }
        
        
    }
    //fields
    //methods
    //initializer
    //secondary constructor
    //equals trait
    override fun equals(other: Any?): Boolean  {
        if (this === other) return true
        if (other == null || other::class != this::class) return false
        
        other as IlMethodRefDto
        
        if (method != other.method) return false
        if (type != other.type) return false
        
        return true
    }
    //hash code trait
    override fun hashCode(): Int  {
        var __r = 0
        __r = __r*31 + method.hashCode()
        __r = __r*31 + type.hashCode()
        return __r
    }
    //pretty print
    override fun print(printer: PrettyPrinter)  {
        printer.println("IlMethodRefDto (")
        printer.indent {
            print("method = "); method.print(printer); println()
            print("type = "); type.print(printer); println()
        }
        printer.print(")")
    }
    
    override fun toString() = PrettyPrinter().singleLine().also { print(it) }.toString()
    //deepClone
    //contexts
}


/**
 * #### Generated from [IlMethodBodyModel.kt:73]
 */
class IlNewArrayExprDto (
    val size: IlExprDto,
    type: CacheKey
) : IlExprDto (
    type
) {
    //companion
    
    companion object : IMarshaller<IlNewArrayExprDto> {
        override val _type: KClass<IlNewArrayExprDto> = IlNewArrayExprDto::class
        
        @Suppress("UNCHECKED_CAST")
        override fun read(ctx: SerializationCtx, buffer: AbstractBuffer): IlNewArrayExprDto  {
            val type = CacheKey.read(ctx, buffer)
            val size = ctx.serializers.readPolymorphic<IlExprDto>(ctx, buffer, IlExprDto)
            return IlNewArrayExprDto(size, type)
        }
        
        override fun write(ctx: SerializationCtx, buffer: AbstractBuffer, value: IlNewArrayExprDto)  {
            CacheKey.write(ctx, buffer, value.type)
            ctx.serializers.writePolymorphic(ctx, buffer, value.size)
        }
        
        
    }
    //fields
    //methods
    //initializer
    //secondary constructor
    //equals trait
    override fun equals(other: Any?): Boolean  {
        if (this === other) return true
        if (other == null || other::class != this::class) return false
        
        other as IlNewArrayExprDto
        
        if (size != other.size) return false
        if (type != other.type) return false
        
        return true
    }
    //hash code trait
    override fun hashCode(): Int  {
        var __r = 0
        __r = __r*31 + size.hashCode()
        __r = __r*31 + type.hashCode()
        return __r
    }
    //pretty print
    override fun print(printer: PrettyPrinter)  {
        printer.println("IlNewArrayExprDto (")
        printer.indent {
            print("size = "); size.print(printer); println()
            print("type = "); type.print(printer); println()
        }
        printer.print(")")
    }
    
    override fun toString() = PrettyPrinter().singleLine().also { print(it) }.toString()
    //deepClone
    //contexts
}


/**
 * #### Generated from [IlMethodBodyModel.kt:55]
 */
class IlNewExprDto (
    val args: List<IlExprDto>,
    type: CacheKey
) : IlExprDto (
    type
) {
    //companion
    
    companion object : IMarshaller<IlNewExprDto> {
        override val _type: KClass<IlNewExprDto> = IlNewExprDto::class
        
        @Suppress("UNCHECKED_CAST")
        override fun read(ctx: SerializationCtx, buffer: AbstractBuffer): IlNewExprDto  {
            val type = CacheKey.read(ctx, buffer)
            val args = buffer.readList { ctx.serializers.readPolymorphic<IlExprDto>(ctx, buffer, IlExprDto) }
            return IlNewExprDto(args, type)
        }
        
        override fun write(ctx: SerializationCtx, buffer: AbstractBuffer, value: IlNewExprDto)  {
            CacheKey.write(ctx, buffer, value.type)
            buffer.writeList(value.args) { v -> ctx.serializers.writePolymorphic(ctx, buffer, v) }
        }
        
        
    }
    //fields
    //methods
    //initializer
    //secondary constructor
    //equals trait
    override fun equals(other: Any?): Boolean  {
        if (this === other) return true
        if (other == null || other::class != this::class) return false
        
        other as IlNewExprDto
        
        if (args != other.args) return false
        if (type != other.type) return false
        
        return true
    }
    //hash code trait
    override fun hashCode(): Int  {
        var __r = 0
        __r = __r*31 + args.hashCode()
        __r = __r*31 + type.hashCode()
        return __r
    }
    //pretty print
    override fun print(printer: PrettyPrinter)  {
        printer.println("IlNewExprDto (")
        printer.indent {
            print("args = "); args.print(printer); println()
            print("type = "); type.print(printer); println()
        }
        printer.print(")")
    }
    
    override fun toString() = PrettyPrinter().singleLine().also { print(it) }.toString()
    //deepClone
    //contexts
}


/**
 * #### Generated from [IlMethodBodyModel.kt:38]
 */
class IlNullDto (
    type: CacheKey
) : IlConstDto (
    type
) {
    //companion
    
    companion object : IMarshaller<IlNullDto> {
        override val _type: KClass<IlNullDto> = IlNullDto::class
        
        @Suppress("UNCHECKED_CAST")
        override fun read(ctx: SerializationCtx, buffer: AbstractBuffer): IlNullDto  {
            val type = CacheKey.read(ctx, buffer)
            return IlNullDto(type)
        }
        
        override fun write(ctx: SerializationCtx, buffer: AbstractBuffer, value: IlNullDto)  {
            CacheKey.write(ctx, buffer, value.type)
        }
        
        
    }
    //fields
    //methods
    //initializer
    //secondary constructor
    //equals trait
    override fun equals(other: Any?): Boolean  {
        if (this === other) return true
        if (other == null || other::class != this::class) return false
        
        other as IlNullDto
        
        if (type != other.type) return false
        
        return true
    }
    //hash code trait
    override fun hashCode(): Int  {
        var __r = 0
        __r = __r*31 + type.hashCode()
        return __r
    }
    //pretty print
    override fun print(printer: PrettyPrinter)  {
        printer.println("IlNullDto (")
        printer.indent {
            print("type = "); type.print(printer); println()
        }
        printer.print(")")
    }
    
    override fun toString() = PrettyPrinter().singleLine().also { print(it) }.toString()
    //deepClone
    //contexts
}


/**
 * #### Generated from [IlMethodBodyModel.kt:30]
 */
abstract class IlNumConstDto (
    type: CacheKey
) : IlConstDto (
    type
) {
    //companion
    
    companion object : IAbstractDeclaration<IlNumConstDto> {
        override fun readUnknownInstance(ctx: SerializationCtx, buffer: AbstractBuffer, unknownId: RdId, size: Int): IlNumConstDto  {
            val objectStartPosition = buffer.position
            val type = CacheKey.read(ctx, buffer)
            val unknownBytes = ByteArray(objectStartPosition + size - buffer.position)
            buffer.readByteArrayRaw(unknownBytes)
            return IlNumConstDto_Unknown(type, unknownId, unknownBytes)
        }
        
        
    }
    //fields
    //methods
    //initializer
    //secondary constructor
    //equals trait
    //hash code trait
    //pretty print
    //deepClone
    //contexts
}


class IlNumConstDto_Unknown (
    type: CacheKey,
    override val unknownId: RdId,
    val unknownBytes: ByteArray
) : IlNumConstDto (
    type
), IUnknownInstance {
    //companion
    
    companion object : IMarshaller<IlNumConstDto_Unknown> {
        override val _type: KClass<IlNumConstDto_Unknown> = IlNumConstDto_Unknown::class
        
        @Suppress("UNCHECKED_CAST")
        override fun read(ctx: SerializationCtx, buffer: AbstractBuffer): IlNumConstDto_Unknown  {
            throw NotImplementedError("Unknown instances should not be read via serializer")
        }
        
        override fun write(ctx: SerializationCtx, buffer: AbstractBuffer, value: IlNumConstDto_Unknown)  {
            CacheKey.write(ctx, buffer, value.type)
            buffer.writeByteArrayRaw(value.unknownBytes)
        }
        
        
    }
    //fields
    //methods
    //initializer
    //secondary constructor
    //equals trait
    override fun equals(other: Any?): Boolean  {
        if (this === other) return true
        if (other == null || other::class != this::class) return false
        
        other as IlNumConstDto_Unknown
        
        if (type != other.type) return false
        
        return true
    }
    //hash code trait
    override fun hashCode(): Int  {
        var __r = 0
        __r = __r*31 + type.hashCode()
        return __r
    }
    //pretty print
    override fun print(printer: PrettyPrinter)  {
        printer.println("IlNumConstDto_Unknown (")
        printer.indent {
            print("type = "); type.print(printer); println()
        }
        printer.print(")")
    }
    
    override fun toString() = PrettyPrinter().singleLine().also { print(it) }.toString()
    //deepClone
    //contexts
}


/**
 * #### Generated from [IlMethodBodyModel.kt:95]
 */
abstract class IlRefExprDto (
    val value: IlExprDto,
    type: CacheKey
) : IlValueDto (
    type
) {
    //companion
    
    companion object : IAbstractDeclaration<IlRefExprDto> {
        override fun readUnknownInstance(ctx: SerializationCtx, buffer: AbstractBuffer, unknownId: RdId, size: Int): IlRefExprDto  {
            val objectStartPosition = buffer.position
            val value = ctx.serializers.readPolymorphic<IlExprDto>(ctx, buffer, IlExprDto)
            val type = CacheKey.read(ctx, buffer)
            val unknownBytes = ByteArray(objectStartPosition + size - buffer.position)
            buffer.readByteArrayRaw(unknownBytes)
            return IlRefExprDto_Unknown(value, type, unknownId, unknownBytes)
        }
        
        
    }
    //fields
    //methods
    //initializer
    //secondary constructor
    //equals trait
    //hash code trait
    //pretty print
    //deepClone
    //contexts
}


class IlRefExprDto_Unknown (
    value: IlExprDto,
    type: CacheKey,
    override val unknownId: RdId,
    val unknownBytes: ByteArray
) : IlRefExprDto (
    value,
    type
), IUnknownInstance {
    //companion
    
    companion object : IMarshaller<IlRefExprDto_Unknown> {
        override val _type: KClass<IlRefExprDto_Unknown> = IlRefExprDto_Unknown::class
        
        @Suppress("UNCHECKED_CAST")
        override fun read(ctx: SerializationCtx, buffer: AbstractBuffer): IlRefExprDto_Unknown  {
            throw NotImplementedError("Unknown instances should not be read via serializer")
        }
        
        override fun write(ctx: SerializationCtx, buffer: AbstractBuffer, value: IlRefExprDto_Unknown)  {
            ctx.serializers.writePolymorphic(ctx, buffer, value.value)
            CacheKey.write(ctx, buffer, value.type)
            buffer.writeByteArrayRaw(value.unknownBytes)
        }
        
        
    }
    //fields
    //methods
    //initializer
    //secondary constructor
    //equals trait
    override fun equals(other: Any?): Boolean  {
        if (this === other) return true
        if (other == null || other::class != this::class) return false
        
        other as IlRefExprDto_Unknown
        
        if (value != other.value) return false
        if (type != other.type) return false
        
        return true
    }
    //hash code trait
    override fun hashCode(): Int  {
        var __r = 0
        __r = __r*31 + value.hashCode()
        __r = __r*31 + type.hashCode()
        return __r
    }
    //pretty print
    override fun print(printer: PrettyPrinter)  {
        printer.println("IlRefExprDto_Unknown (")
        printer.indent {
            print("value = "); value.print(printer); println()
            print("type = "); type.print(printer); println()
        }
        printer.print(")")
    }
    
    override fun toString() = PrettyPrinter().singleLine().also { print(it) }.toString()
    //deepClone
    //contexts
}


/**
 * #### Generated from [IlMethodBodyModel.kt:130]
 */
class IlRethrowStmtDto (
) : IlEhStmtDto (
) {
    //companion
    
    companion object : IMarshaller<IlRethrowStmtDto> {
        override val _type: KClass<IlRethrowStmtDto> = IlRethrowStmtDto::class
        
        @Suppress("UNCHECKED_CAST")
        override fun read(ctx: SerializationCtx, buffer: AbstractBuffer): IlRethrowStmtDto  {
            return IlRethrowStmtDto()
        }
        
        override fun write(ctx: SerializationCtx, buffer: AbstractBuffer, value: IlRethrowStmtDto)  {
        }
        
        
    }
    //fields
    //methods
    //initializer
    //secondary constructor
    //equals trait
    override fun equals(other: Any?): Boolean  {
        if (this === other) return true
        if (other == null || other::class != this::class) return false
        
        other as IlRethrowStmtDto
        
        
        return true
    }
    //hash code trait
    override fun hashCode(): Int  {
        var __r = 0
        return __r
    }
    //pretty print
    override fun print(printer: PrettyPrinter)  {
        printer.println("IlRethrowStmtDto (")
        printer.print(")")
    }
    
    override fun toString() = PrettyPrinter().singleLine().also { print(it) }.toString()
    //deepClone
    //contexts
}


/**
 * #### Generated from [IlMethodBodyModel.kt:122]
 */
class IlReturnStmtDto (
    val retVal: IlExprDto?
) : IlStmtDto (
) {
    //companion
    
    companion object : IMarshaller<IlReturnStmtDto> {
        override val _type: KClass<IlReturnStmtDto> = IlReturnStmtDto::class
        
        @Suppress("UNCHECKED_CAST")
        override fun read(ctx: SerializationCtx, buffer: AbstractBuffer): IlReturnStmtDto  {
            val retVal = buffer.readNullable { ctx.serializers.readPolymorphic<IlExprDto>(ctx, buffer, IlExprDto) }
            return IlReturnStmtDto(retVal)
        }
        
        override fun write(ctx: SerializationCtx, buffer: AbstractBuffer, value: IlReturnStmtDto)  {
            buffer.writeNullable(value.retVal) { ctx.serializers.writePolymorphic(ctx, buffer, it) }
        }
        
        
    }
    //fields
    //methods
    //initializer
    //secondary constructor
    //equals trait
    override fun equals(other: Any?): Boolean  {
        if (this === other) return true
        if (other == null || other::class != this::class) return false
        
        other as IlReturnStmtDto
        
        if (retVal != other.retVal) return false
        
        return true
    }
    //hash code trait
    override fun hashCode(): Int  {
        var __r = 0
        __r = __r*31 + if (retVal != null) retVal.hashCode() else 0
        return __r
    }
    //pretty print
    override fun print(printer: PrettyPrinter)  {
        printer.println("IlReturnStmtDto (")
        printer.indent {
            print("retVal = "); retVal.print(printer); println()
        }
        printer.print(")")
    }
    
    override fun toString() = PrettyPrinter().singleLine().also { print(it) }.toString()
    //deepClone
    //contexts
}


/**
 * #### Generated from [IlMethodBodyModel.kt:59]
 */
class IlSizeOfExprDto (
    val targetType: CacheKey,
    type: CacheKey
) : IlExprDto (
    type
) {
    //companion
    
    companion object : IMarshaller<IlSizeOfExprDto> {
        override val _type: KClass<IlSizeOfExprDto> = IlSizeOfExprDto::class
        
        @Suppress("UNCHECKED_CAST")
        override fun read(ctx: SerializationCtx, buffer: AbstractBuffer): IlSizeOfExprDto  {
            val type = CacheKey.read(ctx, buffer)
            val targetType = CacheKey.read(ctx, buffer)
            return IlSizeOfExprDto(targetType, type)
        }
        
        override fun write(ctx: SerializationCtx, buffer: AbstractBuffer, value: IlSizeOfExprDto)  {
            CacheKey.write(ctx, buffer, value.type)
            CacheKey.write(ctx, buffer, value.targetType)
        }
        
        
    }
    //fields
    //methods
    //initializer
    //secondary constructor
    //equals trait
    override fun equals(other: Any?): Boolean  {
        if (this === other) return true
        if (other == null || other::class != this::class) return false
        
        other as IlSizeOfExprDto
        
        if (targetType != other.targetType) return false
        if (type != other.type) return false
        
        return true
    }
    //hash code trait
    override fun hashCode(): Int  {
        var __r = 0
        __r = __r*31 + targetType.hashCode()
        __r = __r*31 + type.hashCode()
        return __r
    }
    //pretty print
    override fun print(printer: PrettyPrinter)  {
        printer.println("IlSizeOfExprDto (")
        printer.indent {
            print("targetType = "); targetType.print(printer); println()
            print("type = "); type.print(printer); println()
        }
        printer.print(")")
    }
    
    override fun toString() = PrettyPrinter().singleLine().also { print(it) }.toString()
    //deepClone
    //contexts
}


/**
 * #### Generated from [IlMethodBodyModel.kt:107]
 */
class IlStackAllocExprDto (
    val size: IlExprDto,
    type: CacheKey
) : IlExprDto (
    type
) {
    //companion
    
    companion object : IMarshaller<IlStackAllocExprDto> {
        override val _type: KClass<IlStackAllocExprDto> = IlStackAllocExprDto::class
        
        @Suppress("UNCHECKED_CAST")
        override fun read(ctx: SerializationCtx, buffer: AbstractBuffer): IlStackAllocExprDto  {
            val type = CacheKey.read(ctx, buffer)
            val size = ctx.serializers.readPolymorphic<IlExprDto>(ctx, buffer, IlExprDto)
            return IlStackAllocExprDto(size, type)
        }
        
        override fun write(ctx: SerializationCtx, buffer: AbstractBuffer, value: IlStackAllocExprDto)  {
            CacheKey.write(ctx, buffer, value.type)
            ctx.serializers.writePolymorphic(ctx, buffer, value.size)
        }
        
        
    }
    //fields
    //methods
    //initializer
    //secondary constructor
    //equals trait
    override fun equals(other: Any?): Boolean  {
        if (this === other) return true
        if (other == null || other::class != this::class) return false
        
        other as IlStackAllocExprDto
        
        if (size != other.size) return false
        if (type != other.type) return false
        
        return true
    }
    //hash code trait
    override fun hashCode(): Int  {
        var __r = 0
        __r = __r*31 + size.hashCode()
        __r = __r*31 + type.hashCode()
        return __r
    }
    //pretty print
    override fun print(printer: PrettyPrinter)  {
        printer.println("IlStackAllocExprDto (")
        printer.indent {
            print("size = "); size.print(printer); println()
            print("type = "); type.print(printer); println()
        }
        printer.print(")")
    }
    
    override fun toString() = PrettyPrinter().singleLine().also { print(it) }.toString()
    //deepClone
    //contexts
}


/**
 * #### Generated from [IlMethodBodyModel.kt:111]
 */
abstract class IlStmtDto (
) : IPrintable {
    //companion
    
    companion object : IAbstractDeclaration<IlStmtDto> {
        override fun readUnknownInstance(ctx: SerializationCtx, buffer: AbstractBuffer, unknownId: RdId, size: Int): IlStmtDto  {
            val objectStartPosition = buffer.position
            val unknownBytes = ByteArray(objectStartPosition + size - buffer.position)
            buffer.readByteArrayRaw(unknownBytes)
            return IlStmtDto_Unknown(unknownId, unknownBytes)
        }
        
        
    }
    //fields
    //methods
    //initializer
    //secondary constructor
    //equals trait
    //hash code trait
    //pretty print
    //deepClone
    //contexts
}


class IlStmtDto_Unknown (
    override val unknownId: RdId,
    val unknownBytes: ByteArray
) : IlStmtDto (
), IUnknownInstance {
    //companion
    
    companion object : IMarshaller<IlStmtDto_Unknown> {
        override val _type: KClass<IlStmtDto_Unknown> = IlStmtDto_Unknown::class
        
        @Suppress("UNCHECKED_CAST")
        override fun read(ctx: SerializationCtx, buffer: AbstractBuffer): IlStmtDto_Unknown  {
            throw NotImplementedError("Unknown instances should not be read via serializer")
        }
        
        override fun write(ctx: SerializationCtx, buffer: AbstractBuffer, value: IlStmtDto_Unknown)  {
            buffer.writeByteArrayRaw(value.unknownBytes)
        }
        
        
    }
    //fields
    //methods
    //initializer
    //secondary constructor
    //equals trait
    override fun equals(other: Any?): Boolean  {
        if (this === other) return true
        if (other == null || other::class != this::class) return false
        
        other as IlStmtDto_Unknown
        
        
        return true
    }
    //hash code trait
    override fun hashCode(): Int  {
        var __r = 0
        return __r
    }
    //pretty print
    override fun print(printer: PrettyPrinter)  {
        printer.println("IlStmtDto_Unknown (")
        printer.print(")")
    }
    
    override fun toString() = PrettyPrinter().singleLine().also { print(it) }.toString()
    //deepClone
    //contexts
}


/**
 * #### Generated from [IlMethodBodyModel.kt:40]
 */
class IlStringConstDto (
    val value: String,
    type: CacheKey
) : IlConstDto (
    type
) {
    //companion
    
    companion object : IMarshaller<IlStringConstDto> {
        override val _type: KClass<IlStringConstDto> = IlStringConstDto::class
        
        @Suppress("UNCHECKED_CAST")
        override fun read(ctx: SerializationCtx, buffer: AbstractBuffer): IlStringConstDto  {
            val type = CacheKey.read(ctx, buffer)
            val value = buffer.readString()
            return IlStringConstDto(value, type)
        }
        
        override fun write(ctx: SerializationCtx, buffer: AbstractBuffer, value: IlStringConstDto)  {
            CacheKey.write(ctx, buffer, value.type)
            buffer.writeString(value.value)
        }
        
        
    }
    //fields
    //methods
    //initializer
    //secondary constructor
    //equals trait
    override fun equals(other: Any?): Boolean  {
        if (this === other) return true
        if (other == null || other::class != this::class) return false
        
        other as IlStringConstDto
        
        if (value != other.value) return false
        if (type != other.type) return false
        
        return true
    }
    //hash code trait
    override fun hashCode(): Int  {
        var __r = 0
        __r = __r*31 + value.hashCode()
        __r = __r*31 + type.hashCode()
        return __r
    }
    //pretty print
    override fun print(printer: PrettyPrinter)  {
        printer.println("IlStringConstDto (")
        printer.indent {
            print("value = "); value.print(printer); println()
            print("type = "); type.print(printer); println()
        }
        printer.print(")")
    }
    
    override fun toString() = PrettyPrinter().singleLine().also { print(it) }.toString()
    //deepClone
    //contexts
}


/**
 * #### Generated from [IlMethodBodyModel.kt:127]
 */
class IlThrowStmtDto (
    val value: IlExprDto
) : IlEhStmtDto (
) {
    //companion
    
    companion object : IMarshaller<IlThrowStmtDto> {
        override val _type: KClass<IlThrowStmtDto> = IlThrowStmtDto::class
        
        @Suppress("UNCHECKED_CAST")
        override fun read(ctx: SerializationCtx, buffer: AbstractBuffer): IlThrowStmtDto  {
            val value = ctx.serializers.readPolymorphic<IlExprDto>(ctx, buffer, IlExprDto)
            return IlThrowStmtDto(value)
        }
        
        override fun write(ctx: SerializationCtx, buffer: AbstractBuffer, value: IlThrowStmtDto)  {
            ctx.serializers.writePolymorphic(ctx, buffer, value.value)
        }
        
        
    }
    //fields
    //methods
    //initializer
    //secondary constructor
    //equals trait
    override fun equals(other: Any?): Boolean  {
        if (this === other) return true
        if (other == null || other::class != this::class) return false
        
        other as IlThrowStmtDto
        
        if (value != other.value) return false
        
        return true
    }
    //hash code trait
    override fun hashCode(): Int  {
        var __r = 0
        __r = __r*31 + value.hashCode()
        return __r
    }
    //pretty print
    override fun print(printer: PrettyPrinter)  {
        printer.println("IlThrowStmtDto (")
        printer.indent {
            print("value = "); value.print(printer); println()
        }
        printer.print(")")
    }
    
    override fun toString() = PrettyPrinter().singleLine().also { print(it) }.toString()
    //deepClone
    //contexts
}


/**
 * #### Generated from [IlMethodBodyModel.kt:43]
 */
class IlTypeRefDto (
    val referencedType: CacheKey,
    type: CacheKey
) : IlConstDto (
    type
) {
    //companion
    
    companion object : IMarshaller<IlTypeRefDto> {
        override val _type: KClass<IlTypeRefDto> = IlTypeRefDto::class
        
        @Suppress("UNCHECKED_CAST")
        override fun read(ctx: SerializationCtx, buffer: AbstractBuffer): IlTypeRefDto  {
            val type = CacheKey.read(ctx, buffer)
            val referencedType = CacheKey.read(ctx, buffer)
            return IlTypeRefDto(referencedType, type)
        }
        
        override fun write(ctx: SerializationCtx, buffer: AbstractBuffer, value: IlTypeRefDto)  {
            CacheKey.write(ctx, buffer, value.type)
            CacheKey.write(ctx, buffer, value.referencedType)
        }
        
        
    }
    //fields
    //methods
    //initializer
    //secondary constructor
    //equals trait
    override fun equals(other: Any?): Boolean  {
        if (this === other) return true
        if (other == null || other::class != this::class) return false
        
        other as IlTypeRefDto
        
        if (referencedType != other.referencedType) return false
        if (type != other.type) return false
        
        return true
    }
    //hash code trait
    override fun hashCode(): Int  {
        var __r = 0
        __r = __r*31 + referencedType.hashCode()
        __r = __r*31 + type.hashCode()
        return __r
    }
    //pretty print
    override fun print(printer: PrettyPrinter)  {
        printer.println("IlTypeRefDto (")
        printer.indent {
            print("referencedType = "); referencedType.print(printer); println()
            print("type = "); type.print(printer); println()
        }
        printer.print(")")
    }
    
    override fun toString() = PrettyPrinter().singleLine().also { print(it) }.toString()
    //deepClone
    //contexts
}


/**
 * #### Generated from [IlMethodBodyModel.kt:47]
 */
class IlUnaryOpDto (
    val operand: IlExprDto,
    type: CacheKey
) : IlExprDto (
    type
) {
    //companion
    
    companion object : IMarshaller<IlUnaryOpDto> {
        override val _type: KClass<IlUnaryOpDto> = IlUnaryOpDto::class
        
        @Suppress("UNCHECKED_CAST")
        override fun read(ctx: SerializationCtx, buffer: AbstractBuffer): IlUnaryOpDto  {
            val type = CacheKey.read(ctx, buffer)
            val operand = ctx.serializers.readPolymorphic<IlExprDto>(ctx, buffer, IlExprDto)
            return IlUnaryOpDto(operand, type)
        }
        
        override fun write(ctx: SerializationCtx, buffer: AbstractBuffer, value: IlUnaryOpDto)  {
            CacheKey.write(ctx, buffer, value.type)
            ctx.serializers.writePolymorphic(ctx, buffer, value.operand)
        }
        
        
    }
    //fields
    //methods
    //initializer
    //secondary constructor
    //equals trait
    override fun equals(other: Any?): Boolean  {
        if (this === other) return true
        if (other == null || other::class != this::class) return false
        
        other as IlUnaryOpDto
        
        if (operand != other.operand) return false
        if (type != other.type) return false
        
        return true
    }
    //hash code trait
    override fun hashCode(): Int  {
        var __r = 0
        __r = __r*31 + operand.hashCode()
        __r = __r*31 + type.hashCode()
        return __r
    }
    //pretty print
    override fun print(printer: PrettyPrinter)  {
        printer.println("IlUnaryOpDto (")
        printer.indent {
            print("operand = "); operand.print(printer); println()
            print("type = "); type.print(printer); println()
        }
        printer.print(")")
    }
    
    override fun toString() = PrettyPrinter().singleLine().also { print(it) }.toString()
    //deepClone
    //contexts
}


/**
 * #### Generated from [IlMethodBodyModel.kt:90]
 */
class IlUnboxExprDto (
    targetType: CacheKey,
    operand: IlExprDto,
    type: CacheKey
) : IlCastExprDto (
    targetType,
    operand,
    type
) {
    //companion
    
    companion object : IMarshaller<IlUnboxExprDto> {
        override val _type: KClass<IlUnboxExprDto> = IlUnboxExprDto::class
        
        @Suppress("UNCHECKED_CAST")
        override fun read(ctx: SerializationCtx, buffer: AbstractBuffer): IlUnboxExprDto  {
            val targetType = CacheKey.read(ctx, buffer)
            val operand = ctx.serializers.readPolymorphic<IlExprDto>(ctx, buffer, IlExprDto)
            val type = CacheKey.read(ctx, buffer)
            return IlUnboxExprDto(targetType, operand, type)
        }
        
        override fun write(ctx: SerializationCtx, buffer: AbstractBuffer, value: IlUnboxExprDto)  {
            CacheKey.write(ctx, buffer, value.targetType)
            ctx.serializers.writePolymorphic(ctx, buffer, value.operand)
            CacheKey.write(ctx, buffer, value.type)
        }
        
        
    }
    //fields
    //methods
    //initializer
    //secondary constructor
    //equals trait
    override fun equals(other: Any?): Boolean  {
        if (this === other) return true
        if (other == null || other::class != this::class) return false
        
        other as IlUnboxExprDto
        
        if (targetType != other.targetType) return false
        if (operand != other.operand) return false
        if (type != other.type) return false
        
        return true
    }
    //hash code trait
    override fun hashCode(): Int  {
        var __r = 0
        __r = __r*31 + targetType.hashCode()
        __r = __r*31 + operand.hashCode()
        __r = __r*31 + type.hashCode()
        return __r
    }
    //pretty print
    override fun print(printer: PrettyPrinter)  {
        printer.println("IlUnboxExprDto (")
        printer.indent {
            print("targetType = "); targetType.print(printer); println()
            print("operand = "); operand.print(printer); println()
            print("type = "); type.print(printer); println()
        }
        printer.print(")")
    }
    
    override fun toString() = PrettyPrinter().singleLine().also { print(it) }.toString()
    //deepClone
    //contexts
}


/**
 * #### Generated from [IlMethodBodyModel.kt:105]
 */
class IlUnmanagedDerefExprDto (
    value: IlExprDto,
    type: CacheKey
) : IlDerefExprDto (
    value,
    type
) {
    //companion
    
    companion object : IMarshaller<IlUnmanagedDerefExprDto> {
        override val _type: KClass<IlUnmanagedDerefExprDto> = IlUnmanagedDerefExprDto::class
        
        @Suppress("UNCHECKED_CAST")
        override fun read(ctx: SerializationCtx, buffer: AbstractBuffer): IlUnmanagedDerefExprDto  {
            val value = ctx.serializers.readPolymorphic<IlExprDto>(ctx, buffer, IlExprDto)
            val type = CacheKey.read(ctx, buffer)
            return IlUnmanagedDerefExprDto(value, type)
        }
        
        override fun write(ctx: SerializationCtx, buffer: AbstractBuffer, value: IlUnmanagedDerefExprDto)  {
            ctx.serializers.writePolymorphic(ctx, buffer, value.value)
            CacheKey.write(ctx, buffer, value.type)
        }
        
        
    }
    //fields
    //methods
    //initializer
    //secondary constructor
    //equals trait
    override fun equals(other: Any?): Boolean  {
        if (this === other) return true
        if (other == null || other::class != this::class) return false
        
        other as IlUnmanagedDerefExprDto
        
        if (value != other.value) return false
        if (type != other.type) return false
        
        return true
    }
    //hash code trait
    override fun hashCode(): Int  {
        var __r = 0
        __r = __r*31 + value.hashCode()
        __r = __r*31 + type.hashCode()
        return __r
    }
    //pretty print
    override fun print(printer: PrettyPrinter)  {
        printer.println("IlUnmanagedDerefExprDto (")
        printer.indent {
            print("value = "); value.print(printer); println()
            print("type = "); type.print(printer); println()
        }
        printer.print(")")
    }
    
    override fun toString() = PrettyPrinter().singleLine().also { print(it) }.toString()
    //deepClone
    //contexts
}


/**
 * #### Generated from [IlMethodBodyModel.kt:103]
 */
class IlUnmanagedRefExprDto (
    value: IlExprDto,
    type: CacheKey
) : IlRefExprDto (
    value,
    type
) {
    //companion
    
    companion object : IMarshaller<IlUnmanagedRefExprDto> {
        override val _type: KClass<IlUnmanagedRefExprDto> = IlUnmanagedRefExprDto::class
        
        @Suppress("UNCHECKED_CAST")
        override fun read(ctx: SerializationCtx, buffer: AbstractBuffer): IlUnmanagedRefExprDto  {
            val value = ctx.serializers.readPolymorphic<IlExprDto>(ctx, buffer, IlExprDto)
            val type = CacheKey.read(ctx, buffer)
            return IlUnmanagedRefExprDto(value, type)
        }
        
        override fun write(ctx: SerializationCtx, buffer: AbstractBuffer, value: IlUnmanagedRefExprDto)  {
            ctx.serializers.writePolymorphic(ctx, buffer, value.value)
            CacheKey.write(ctx, buffer, value.type)
        }
        
        
    }
    //fields
    //methods
    //initializer
    //secondary constructor
    //equals trait
    override fun equals(other: Any?): Boolean  {
        if (this === other) return true
        if (other == null || other::class != this::class) return false
        
        other as IlUnmanagedRefExprDto
        
        if (value != other.value) return false
        if (type != other.type) return false
        
        return true
    }
    //hash code trait
    override fun hashCode(): Int  {
        var __r = 0
        __r = __r*31 + value.hashCode()
        __r = __r*31 + type.hashCode()
        return __r
    }
    //pretty print
    override fun print(printer: PrettyPrinter)  {
        printer.println("IlUnmanagedRefExprDto (")
        printer.indent {
            print("value = "); value.print(printer); println()
            print("type = "); type.print(printer); println()
        }
        printer.print(")")
    }
    
    override fun toString() = PrettyPrinter().singleLine().also { print(it) }.toString()
    //deepClone
    //contexts
}


/**
 * #### Generated from [IlMethodBodyModel.kt:28]
 */
abstract class IlValueDto (
    type: CacheKey
) : IlExprDto (
    type
) {
    //companion
    
    companion object : IAbstractDeclaration<IlValueDto> {
        override fun readUnknownInstance(ctx: SerializationCtx, buffer: AbstractBuffer, unknownId: RdId, size: Int): IlValueDto  {
            val objectStartPosition = buffer.position
            val type = CacheKey.read(ctx, buffer)
            val unknownBytes = ByteArray(objectStartPosition + size - buffer.position)
            buffer.readByteArrayRaw(unknownBytes)
            return IlValueDto_Unknown(type, unknownId, unknownBytes)
        }
        
        
    }
    //fields
    //methods
    //initializer
    //secondary constructor
    //equals trait
    //hash code trait
    //pretty print
    //deepClone
    //contexts
}


class IlValueDto_Unknown (
    type: CacheKey,
    override val unknownId: RdId,
    val unknownBytes: ByteArray
) : IlValueDto (
    type
), IUnknownInstance {
    //companion
    
    companion object : IMarshaller<IlValueDto_Unknown> {
        override val _type: KClass<IlValueDto_Unknown> = IlValueDto_Unknown::class
        
        @Suppress("UNCHECKED_CAST")
        override fun read(ctx: SerializationCtx, buffer: AbstractBuffer): IlValueDto_Unknown  {
            throw NotImplementedError("Unknown instances should not be read via serializer")
        }
        
        override fun write(ctx: SerializationCtx, buffer: AbstractBuffer, value: IlValueDto_Unknown)  {
            CacheKey.write(ctx, buffer, value.type)
            buffer.writeByteArrayRaw(value.unknownBytes)
        }
        
        
    }
    //fields
    //methods
    //initializer
    //secondary constructor
    //equals trait
    override fun equals(other: Any?): Boolean  {
        if (this === other) return true
        if (other == null || other::class != this::class) return false
        
        other as IlValueDto_Unknown
        
        if (type != other.type) return false
        
        return true
    }
    //hash code trait
    override fun hashCode(): Int  {
        var __r = 0
        __r = __r*31 + type.hashCode()
        return __r
    }
    //pretty print
    override fun print(printer: PrettyPrinter)  {
        printer.println("IlValueDto_Unknown (")
        printer.indent {
            print("type = "); type.print(printer); println()
        }
        printer.print(")")
    }
    
    override fun toString() = PrettyPrinter().singleLine().also { print(it) }.toString()
    //deepClone
    //contexts
}


/**
 * #### Generated from [IlMethodBodyModel.kt:156]
 */
class IlVarAccessDto (
    val kind: IlVarKind,
    val index: Int,
    type: CacheKey
) : IlValueDto (
    type
) {
    //companion
    
    companion object : IMarshaller<IlVarAccessDto> {
        override val _type: KClass<IlVarAccessDto> = IlVarAccessDto::class
        
        @Suppress("UNCHECKED_CAST")
        override fun read(ctx: SerializationCtx, buffer: AbstractBuffer): IlVarAccessDto  {
            val type = CacheKey.read(ctx, buffer)
            val kind = buffer.readEnum<IlVarKind>()
            val index = buffer.readInt()
            return IlVarAccessDto(kind, index, type)
        }
        
        override fun write(ctx: SerializationCtx, buffer: AbstractBuffer, value: IlVarAccessDto)  {
            CacheKey.write(ctx, buffer, value.type)
            buffer.writeEnum(value.kind)
            buffer.writeInt(value.index)
        }
        
        
    }
    //fields
    //methods
    //initializer
    //secondary constructor
    //equals trait
    override fun equals(other: Any?): Boolean  {
        if (this === other) return true
        if (other == null || other::class != this::class) return false
        
        other as IlVarAccessDto
        
        if (kind != other.kind) return false
        if (index != other.index) return false
        if (type != other.type) return false
        
        return true
    }
    //hash code trait
    override fun hashCode(): Int  {
        var __r = 0
        __r = __r*31 + kind.hashCode()
        __r = __r*31 + index.hashCode()
        __r = __r*31 + type.hashCode()
        return __r
    }
    //pretty print
    override fun print(printer: PrettyPrinter)  {
        printer.println("IlVarAccessDto (")
        printer.indent {
            print("kind = "); kind.print(printer); println()
            print("index = "); index.print(printer); println()
            print("type = "); type.print(printer); println()
        }
        printer.print(")")
    }
    
    override fun toString() = PrettyPrinter().singleLine().also { print(it) }.toString()
    //deepClone
    //contexts
}


/**
 * #### Generated from [IlMethodBodyModel.kt:148]
 */
enum class IlVarKind {
    local, 
    temp, 
    err;
    
    companion object {
        val marshaller = FrameworkMarshallers.enum<IlVarKind>()
        
    }
}
