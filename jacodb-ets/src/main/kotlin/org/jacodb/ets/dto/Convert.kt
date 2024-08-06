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

package org.jacodb.ets.dto

import org.jacodb.ets.base.EtsAddExpr
import org.jacodb.ets.base.EtsAndExpr
import org.jacodb.ets.base.EtsAnyType
import org.jacodb.ets.base.EtsArrayAccess
import org.jacodb.ets.base.EtsArrayLiteral
import org.jacodb.ets.base.EtsArrayType
import org.jacodb.ets.base.EtsAssignStmt
import org.jacodb.ets.base.EtsBitAndExpr
import org.jacodb.ets.base.EtsBitNotExpr
import org.jacodb.ets.base.EtsBitOrExpr
import org.jacodb.ets.base.EtsBitXorExpr
import org.jacodb.ets.base.EtsBooleanConstant
import org.jacodb.ets.base.EtsBooleanType
import org.jacodb.ets.base.EtsCallExpr
import org.jacodb.ets.base.EtsCallStmt
import org.jacodb.ets.base.EtsCallableType
import org.jacodb.ets.base.EtsCastExpr
import org.jacodb.ets.base.EtsClassType
import org.jacodb.ets.base.EtsCommaExpr
import org.jacodb.ets.base.EtsConstant
import org.jacodb.ets.base.EtsDeleteExpr
import org.jacodb.ets.base.EtsDivExpr
import org.jacodb.ets.base.EtsEntity
import org.jacodb.ets.base.EtsEqExpr
import org.jacodb.ets.base.EtsExpExpr
import org.jacodb.ets.base.EtsExpr
import org.jacodb.ets.base.EtsFieldRef
import org.jacodb.ets.base.EtsGotoStmt
import org.jacodb.ets.base.EtsGtEqExpr
import org.jacodb.ets.base.EtsGtExpr
import org.jacodb.ets.base.EtsIfStmt
import org.jacodb.ets.base.EtsInExpr
import org.jacodb.ets.base.EtsInstLocation
import org.jacodb.ets.base.EtsInstanceCallExpr
import org.jacodb.ets.base.EtsInstanceFieldRef
import org.jacodb.ets.base.EtsInstanceOfExpr
import org.jacodb.ets.base.EtsLeftShiftExpr
import org.jacodb.ets.base.EtsLengthExpr
import org.jacodb.ets.base.EtsLiteralType
import org.jacodb.ets.base.EtsLocal
import org.jacodb.ets.base.EtsLtEqExpr
import org.jacodb.ets.base.EtsLtExpr
import org.jacodb.ets.base.EtsMulExpr
import org.jacodb.ets.base.EtsNegExpr
import org.jacodb.ets.base.EtsNeverType
import org.jacodb.ets.base.EtsNewArrayExpr
import org.jacodb.ets.base.EtsNewExpr
import org.jacodb.ets.base.EtsNopStmt
import org.jacodb.ets.base.EtsNotEqExpr
import org.jacodb.ets.base.EtsNotExpr
import org.jacodb.ets.base.EtsNullConstant
import org.jacodb.ets.base.EtsNullType
import org.jacodb.ets.base.EtsNullishCoalescingExpr
import org.jacodb.ets.base.EtsNumberConstant
import org.jacodb.ets.base.EtsNumberType
import org.jacodb.ets.base.EtsObjectLiteral
import org.jacodb.ets.base.EtsOrExpr
import org.jacodb.ets.base.EtsParameterRef
import org.jacodb.ets.base.EtsPreDecExpr
import org.jacodb.ets.base.EtsPreIncExpr
import org.jacodb.ets.base.EtsRemExpr
import org.jacodb.ets.base.EtsReturnStmt
import org.jacodb.ets.base.EtsRightShiftExpr
import org.jacodb.ets.base.EtsStaticCallExpr
import org.jacodb.ets.base.EtsStaticFieldRef
import org.jacodb.ets.base.EtsStmt
import org.jacodb.ets.base.EtsStrictEqExpr
import org.jacodb.ets.base.EtsStrictNotEqExpr
import org.jacodb.ets.base.EtsStringConstant
import org.jacodb.ets.base.EtsStringType
import org.jacodb.ets.base.EtsSubExpr
import org.jacodb.ets.base.EtsSwitchStmt
import org.jacodb.ets.base.EtsThis
import org.jacodb.ets.base.EtsThrowStmt
import org.jacodb.ets.base.EtsTupleType
import org.jacodb.ets.base.EtsType
import org.jacodb.ets.base.EtsTypeOfExpr
import org.jacodb.ets.base.EtsUnaryPlusExpr
import org.jacodb.ets.base.EtsUnclearRefType
import org.jacodb.ets.base.EtsUndefinedConstant
import org.jacodb.ets.base.EtsUndefinedType
import org.jacodb.ets.base.EtsUnionType
import org.jacodb.ets.base.EtsUnknownType
import org.jacodb.ets.base.EtsUnsignedRightShiftExpr
import org.jacodb.ets.base.EtsValue
import org.jacodb.ets.base.EtsVoidType
import org.jacodb.ets.base.Ops
import org.jacodb.ets.graph.EtsCfg
import org.jacodb.ets.model.EtsClass
import org.jacodb.ets.model.EtsClassImpl
import org.jacodb.ets.model.EtsClassSignature
import org.jacodb.ets.model.EtsField
import org.jacodb.ets.model.EtsFieldImpl
import org.jacodb.ets.model.EtsFieldSignature
import org.jacodb.ets.model.EtsFieldSubSignature
import org.jacodb.ets.model.EtsFile
import org.jacodb.ets.model.EtsMethod
import org.jacodb.ets.model.EtsMethodImpl
import org.jacodb.ets.model.EtsMethodParameter
import org.jacodb.ets.model.EtsMethodSignature
import org.jacodb.ets.model.EtsMethodSubSignature

class EtsMethodBuilder(
    signature: EtsMethodSignature,
    // Default locals count is args + this
    localsCount: Int = signature.parameters.size + 1,
    modifiers: List<String> = emptyList(),
) {
    val etsMethod = EtsMethodImpl(signature, localsCount, modifiers)

    private val currentStmts: MutableList<EtsStmt> = mutableListOf()

    private var freeTempLocal: Int = 0

    private fun newTempLocal(type: EtsType): EtsLocal {
        return EtsLocal("_tmp${freeTempLocal++}", type)
    }

    private fun loc(): EtsInstLocation {
        return EtsInstLocation(etsMethod, currentStmts.size)
    }

    private var built: Boolean = false

    fun build(cfgDto: CfgDto): EtsMethod {
        require(!built) { "Method has already been built" }
        val cfg = convertToEtsCfg(cfgDto)
        etsMethod.cfg = cfg
        built = true
        return etsMethod
    }

    private fun ensureOneAddress(entity: EtsEntity): EtsValue {
        // TODO: think about whether 'CastExpr' should be considered "one-address". This would require changing the return type of this function to `EtsEntity`.
        // if (entity is EtsCastExpr) return entity

        if (entity is EtsExpr || entity is EtsFieldRef || entity is EtsArrayAccess) {
            val newLocal = newTempLocal(entity.type)
            currentStmts += EtsAssignStmt(
                location = loc(),
                lhv = newLocal,
                rhv = entity,
            )
            return newLocal
        } else {
            check(entity is EtsValue)
            return entity
        }
    }

    fun convertToEtsStmt(stmt: StmtDto): EtsStmt {
        return when (stmt) {
            is UnknownStmtDto -> object : EtsStmt {
                override val location: EtsInstLocation = loc()

                override fun toString(): String = "Unknown(${stmt.stmt})"

                // TODO: equals/hashCode ???

                override fun <R> accept(visitor: EtsStmt.Visitor<R>): R {
                    error("UnknownStmt is not supported")
                }
            }

            is NopStmtDto -> {
                EtsNopStmt(location = loc())
            }

            is AssignStmtDto -> {
                val lhv = convertToEtsEntity(stmt.left) as EtsValue
                val rhv = ensureOneAddress(convertToEtsEntity(stmt.right))
                EtsAssignStmt(
                    location = loc(),
                    lhv = lhv,
                    rhv = rhv,
                )
            }

            is CallStmtDto -> {
                val expr = convertToEtsEntity(stmt.expr) as EtsCallExpr
                EtsCallStmt(
                    location = loc(),
                    expr = expr,
                )
            }

            is ReturnStmtDto -> {
                val returnValue = ensureOneAddress(convertToEtsEntity(stmt.arg))
                EtsReturnStmt(
                    location = loc(),
                    returnValue = returnValue,
                )
            }

            is ReturnVoidStmtDto -> {
                EtsReturnStmt(
                    location = loc(),
                    returnValue = null,
                )
            }

            is ThrowStmtDto -> {
                val arg = convertToEtsEntity(stmt.arg)
                EtsThrowStmt(
                    location = loc(),
                    arg = arg,
                )
            }

            is GotoStmtDto -> {
                EtsGotoStmt(location = loc())
            }

            is IfStmtDto -> {
                val condition = convertToEtsEntity(stmt.condition)
                EtsIfStmt(
                    location = loc(),
                    condition = condition,
                )
            }

            is SwitchStmtDto -> {
                val arg = convertToEtsEntity(stmt.arg)
                val cases = stmt.cases.map { convertToEtsEntity(it) }
                EtsSwitchStmt(
                    location = loc(),
                    arg = arg,
                    cases = cases,
                )
            }

            // else -> error("Unknown Stmt: $stmt")
        }
    }

    fun convertToEtsEntity(value: ValueDto): EtsEntity {
        return when (value) {
            is UnknownValueDto -> object : EtsEntity {
                override val type: EtsType = EtsUnknownType

                // TODO: change to this `toString()` implementation when `value.value` field is restored.
                //       override fun toString(): String = "Unknown(${value.value})"
                //       Note: `value` field was removed from `UnknownValueDto` due to circular references in ArkIR,
                //       which forbid their serialization.
                override fun toString(): String = "Unknown"

                override fun <R> accept(visitor: EtsEntity.Visitor<R>): R {
                    if (visitor is EtsEntity.Visitor.Default<R>) {
                        return visitor.defaultVisit(this)
                    }
                    error("Cannot handle $this")
                }
            }

            is LocalDto -> EtsLocal(
                name = value.name,
                type = convertToEtsType(value.type),
            )

            is ConstantDto -> convertToEtsConstant(value)

            is NewExprDto -> EtsNewExpr(
                type = convertToEtsType(value.classType as ClassTypeDto) // safe cast
            )

            is NewArrayExprDto -> EtsNewArrayExpr(
                elementType = convertToEtsType(value.type),
                size = convertToEtsEntity(value.size),
            )

            is DeleteExprDto -> EtsDeleteExpr(
                arg = convertToEtsEntity(value.arg)
            )

            is TypeOfExprDto -> EtsTypeOfExpr(
                arg = convertToEtsEntity(value.arg)
            )

            is InstanceOfExprDto -> EtsInstanceOfExpr(
                arg = convertToEtsEntity(value.arg),
                checkType = value.checkType,
            )

            is LengthExprDto -> EtsLengthExpr(
                arg = convertToEtsEntity(value.arg)
            )

            is CastExprDto -> EtsCastExpr(
                arg = convertToEtsEntity(value.arg),
                type = convertToEtsType(value.type),
            )

            is PhiExprDto -> error("PhiExpr is not supported")

            is ArrayLiteralDto -> EtsArrayLiteral(
                elements = value.elements.map { convertToEtsEntity(it) },
                type = convertToEtsType(value.type), // TODO: as EtsArrayType,
            )

            is ObjectLiteralDto -> EtsObjectLiteral(
                properties = emptyList(), // TODO
                type = convertToEtsType(value.type),
            )

            is UnaryOperationDto -> {
                val arg = convertToEtsEntity(value.arg)
                // Note: `value.type` is ignored here!
                when (value.op) {
                    Ops.NOT -> EtsNotExpr(arg)
                    Ops.BIT_NOT -> EtsBitNotExpr(arg.type, arg)
                    Ops.MINUS -> EtsNegExpr(arg.type, arg)
                    Ops.PLUS -> EtsUnaryPlusExpr(arg)
                    Ops.INC -> EtsPreIncExpr(arg.type, arg)
                    Ops.DEC -> EtsPreDecExpr(arg.type, arg)
                    else -> error("Unknown unop: '${value.op}'")
                }
            }

            is BinaryOperationDto -> {
                val left = convertToEtsEntity(value.left)
                val right = convertToEtsEntity(value.right)
                val type = convertToEtsType(value.type)
                when (value.op) {
                    Ops.ADD -> EtsAddExpr(type, left, right)
                    Ops.SUB -> EtsSubExpr(type, left, right)
                    Ops.MUL -> EtsMulExpr(type, left, right)
                    Ops.DIV -> EtsDivExpr(type, left, right)
                    Ops.MOD -> EtsRemExpr(type, left, right)
                    Ops.EXP -> EtsExpExpr(type, left, right)
                    Ops.BIT_AND -> EtsBitAndExpr(type, left, right)
                    Ops.BIT_OR -> EtsBitOrExpr(type, left, right)
                    Ops.BIT_XOR -> EtsBitXorExpr(type, left, right)
                    Ops.LSH -> EtsLeftShiftExpr(type, left, right)
                    Ops.RSH -> EtsRightShiftExpr(type, left, right)
                    Ops.URSH -> EtsUnsignedRightShiftExpr(type, left, right)
                    Ops.AND -> EtsAndExpr(type, left, right)
                    Ops.OR -> EtsOrExpr(type, left, right)
                    Ops.NULLISH -> EtsNullishCoalescingExpr(type, left, right)
                    Ops.COMMA -> EtsCommaExpr(left, right) // Note: `type` is ignored here!

                    // TODO: fix (remove) this when `instanceof` is properly supported in ArkAnalyzer.
                    //  Ideally, it would become a separate `ArkInstanceOfExpr`, and we are going to
                    //  introduce a corresponding DTO for it.
                    //  Currently, `x instanceof T` is represented as `BinopExpr(Local("x"), Local("T"))`,
                    //  so we just *unsafely* extract the type name from the "pseudo-local" here:
                    "instanceof" -> EtsInstanceOfExpr(left, (right as EtsLocal).name)

                    else -> error("Unknown binop: ${value.op}")
                }
            }

            is RelationOperationDto -> {
                val left = convertToEtsEntity(value.left)
                val right = convertToEtsEntity(value.right)
                // Note: `value.type` is ignored here!
                when (value.op) {
                    Ops.EQ_EQ -> EtsEqExpr(left, right)
                    Ops.NOT_EQ -> EtsNotEqExpr(left, right)
                    Ops.EQ_EQ_EQ -> EtsStrictEqExpr(left, right)
                    Ops.NOT_EQ_EQ -> EtsStrictNotEqExpr(left, right)
                    Ops.LT -> EtsLtExpr(left, right)
                    Ops.LT_EQ -> EtsLtEqExpr(left, right)
                    Ops.GT -> EtsGtExpr(left, right)
                    Ops.GT_EQ -> EtsGtEqExpr(left, right)
                    Ops.IN -> EtsInExpr(left, right)
                    else -> error("Unknown relop: ${value.op}")
                }
            }

            is InstanceCallExprDto -> EtsInstanceCallExpr(
                instance = convertToEtsEntity(value.instance),
                method = convertToEtsMethodSignature(value.method),
                args = value.args.map {
                    ensureOneAddress(convertToEtsEntity(it))
                },
            )

            is StaticCallExprDto -> EtsStaticCallExpr(
                method = convertToEtsMethodSignature(value.method),
                args = value.args.map {
                    ensureOneAddress(convertToEtsEntity(it))
                },
            )

            is ThisRefDto -> EtsThis(
                type = convertToEtsType(value.type) as EtsClassType // safe cast
            )

            is ParameterRefDto -> EtsParameterRef(
                index = value.index,
                type = convertToEtsType(value.type),
            )

            is ArrayRefDto -> EtsArrayAccess(
                array = convertToEtsEntity(value.array) as EtsValue, // TODO: check whether the cast is safe
                index = convertToEtsEntity(value.index) as EtsValue, // TODO: check whether the cast is safe
                type = convertToEtsType(value.type),
            )

            is FieldRefDto -> convertToEtsFieldRef(value)

            // else -> error("Unknown Value: $value")
        }
    }

    fun convertToEtsFieldRef(fieldRef: FieldRefDto): EtsFieldRef {
        val field = convertToEtsFieldSignature(fieldRef.field)
        return when (fieldRef) {
            is InstanceFieldRefDto -> EtsInstanceFieldRef(
                instance = convertToEtsEntity(fieldRef.instance), // as Local
                field = field,
            )

            is StaticFieldRefDto -> EtsStaticFieldRef(
                field = field
            )
        }
    }

    fun convertToEtsCfg(cfg: CfgDto): EtsCfg {
        require(cfg.blocks.isNotEmpty()) {
            "Method body should contain at least return stmt"
        }

        val visited: MutableSet<Int> = hashSetOf()
        val queue: ArrayDeque<Int> = ArrayDeque()
        queue.add(0)

        val blocks = cfg.blocks.associateBy { it.id }
        val blockStart: MutableMap<Int, Int> = hashMapOf()
        val blockEnd: MutableMap<Int, Int> = hashMapOf()

        while (queue.isNotEmpty()) {
            val block = blocks[queue.removeFirst()]!!
            blockStart[block.id] = currentStmts.size
            if (block.stmts.isNotEmpty()) {
                for (stmt in block.stmts) {
                    currentStmts += convertToEtsStmt(stmt)
                }
            } else {
                currentStmts += EtsNopStmt(loc())
            }
            blockEnd[block.id] = currentStmts.lastIndex
            check(blockStart[block.id]!! <= blockEnd[block.id]!!)

            for (next in block.successors) {
                if (visited.add(next)) {
                    queue.addLast(next)
                }
            }
        }

        val successorMap: MutableMap<EtsStmt, List<EtsStmt>> = hashMapOf()
        for (block in cfg.blocks) {
            val startId = blockStart[block.id]!!
            val endId = blockEnd[block.id]!!
            for (i in startId until endId) {
                successorMap[currentStmts[i]] = listOf(currentStmts[i + 1])
            }
            successorMap[currentStmts[endId]] = block.successors.mapNotNull { blockId ->
                blockStart[blockId]?.let { currentStmts[it] }
            }
        }

        return EtsCfg(
            stmts = currentStmts,
            successorMap = successorMap,
        )
    }
}

fun convertToEtsClass(classDto: ClassDto): EtsClass {
    fun defaultConstructorDto(classSignatureDto: ClassSignatureDto): MethodDto {
        val zeroBlock = BasicBlockDto(
            id = 0,
            successors = emptyList(),
            predecessors = emptyList(),
            stmts = listOf(
                ReturnStmtDto(arg = ThisRefDto(type = ClassTypeDto(classSignatureDto)))
            )
        )
        val cfg = CfgDto(blocks = listOf(zeroBlock))
        val body = BodyDto(locals = emptyList(), cfg = cfg)
        val signature = MethodSignatureDto(
            enclosingClass = classSignatureDto,
            name = "constructor",
            parameters = emptyList(),
            returnType = ClassTypeDto(classSignatureDto),
        )
        return MethodDto(
            signature = signature,
            modifiers = emptyList(),
            typeParameters = emptyList(),
            body = body,
        )
    }

    fun isStaticField(field: FieldDto): Boolean {
        val modifiers = field.modifiers ?: return false
        return modifiers.contains(ModifierDto.StringItem("StaticKeyword"))
    }

    val signature = EtsClassSignature(
        name = classDto.signature.name,
        namespace = null, // TODO
        file = null, // TODO
    )

    val superclassSignature = classDto.superClassName?.takeIf { it != "" }?.let { spName ->
        EtsClassSignature(
            name = spName,
            namespace = null, // TODO
            file = null, // TODO
        )
    }

    val (methodDtos, ctorDtos) = classDto.methods.partition { it.signature.name != "constructor" }
    check(ctorDtos.size <= 1) { "Class should not have multiple constructors" }
    val ctorDto = ctorDtos.singleOrNull() ?: defaultConstructorDto(classDto.signature)

    val fields = classDto.fields.map { convertToEtsField(it) }
    val methods = methodDtos.map { convertToEtsMethod(it) }

    val initializers = classDto.fields.mapNotNull {
        if (it.initializer != null && !isStaticField(it)) {
            AssignStmtDto(
                left = InstanceFieldRefDto(
                    instance = ThisRefDto(ClassTypeDto(classDto.signature)),
                    field = it.signature,
                ),
                right = it.initializer,
            )
        } else null
    }

    val ctorBlocks = ctorDto.body.cfg.blocks
    val ctorStartingBlock = ctorBlocks.single { it.id == 0 }

    check(ctorStartingBlock.predecessors.isEmpty()) {
        "Starting block should not have predecessors, or else the (prepended) initializers will be evaluated multiple times"
    }

    val newStartingBlock = ctorStartingBlock.copy(
        stmts = initializers + ctorStartingBlock.stmts
    )
    val ctorWithInitializersDto = ctorDto.copy(
        body = ctorDto.body.copy(
            cfg = CfgDto(
                blocks = ctorBlocks - ctorStartingBlock + newStartingBlock
            )
        )
    )
    val ctor = convertToEtsMethod(ctorWithInitializersDto)

    return EtsClassImpl(
        signature = signature,
        fields = fields,
        methods = methods,
        ctor = ctor,
        superClass = superclassSignature
    )
}

fun convertToEtsType(type: TypeDto): EtsType {
    return when (type) {
        is AbsolutelyUnknownTypeDto -> object : EtsType {
            override val typeName: String
                get() = type.type ?: "UNKNOWN"

            override fun toString(): String {
                return type.type ?: "UNKNOWN"
            }

            override fun <R> accept(visitor: EtsType.Visitor<R>): R {
                error("Not supported: ${type.type}")
            }
        }

        AnyTypeDto -> EtsAnyType

        is ArrayTypeDto -> EtsArrayType(
            elementType = convertToEtsType(type.elementType),
            dimensions = type.dimensions,
        )

        is CallableTypeDto -> EtsCallableType(
            method = convertToEtsMethodSignature(type.signature)
        )

        is ClassTypeDto -> EtsClassType(
            classSignature = convertToEtsClassSignature(type.signature)
        )

        NeverTypeDto -> EtsNeverType

        BooleanTypeDto -> EtsBooleanType

        is LiteralTypeDto -> EtsLiteralType(
            literalTypeName = type.literal
        )

        NullTypeDto -> EtsNullType

        NumberTypeDto -> EtsNumberType

        StringTypeDto -> EtsStringType

        UndefinedTypeDto -> EtsUndefinedType

        is TupleTypeDto -> EtsTupleType(
            types = type.types.map { convertToEtsType(it) }
        )

        is UnclearReferenceTypeDto -> EtsUnclearRefType(
            typeName = type.name
        )

        is UnionTypeDto -> EtsUnionType(
            types = type.types.map { convertToEtsType(it) }
        )

        UnknownTypeDto -> EtsUnknownType

        VoidTypeDto -> EtsVoidType
    }
}

fun convertToEtsConstant(value: ConstantDto): EtsConstant {
    val type = convertToEtsType(value.type)
    return when (type) {
        EtsStringType -> EtsStringConstant(
            value = value.value
        )

        EtsBooleanType -> EtsBooleanConstant(
            value = value.value.toBoolean()
        )

        EtsNumberType -> EtsNumberConstant(
            value = value.value.toDouble()
        )

        EtsNullType -> EtsNullConstant

        EtsUndefinedType -> EtsUndefinedConstant

        else -> object : EtsConstant {
            override val type: EtsType = EtsUnknownType

            override fun toString(): String = "Unknown(${value.value})"

            override fun <R> accept(visitor: EtsValue.Visitor<R>): R {
                if (visitor is EtsValue.Visitor.Default<R>) {
                    return visitor.defaultVisit(this)
                }
                error("Cannot handle $this")
            }
        }
    }
}

fun convertToEtsClassSignature(clazz: ClassSignatureDto): EtsClassSignature {
    return EtsClassSignature(
        name = clazz.name,
        namespace = null, // TODO
        file = null, // TODO
    )
}

fun convertToEtsFieldSignature(field: FieldSignatureDto): EtsFieldSignature {
    return EtsFieldSignature(
        enclosingClass = convertToEtsClassSignature(field.enclosingClass),
        sub = EtsFieldSubSignature(
            name = field.name,
            type = convertToEtsType(field.type),
        )
    )
}

fun convertToEtsMethodSignature(method: MethodSignatureDto): EtsMethodSignature {
    return EtsMethodSignature(
        enclosingClass = convertToEtsClassSignature(method.enclosingClass),
        sub = EtsMethodSubSignature(
            name = method.name,
            parameters = method.parameters.mapIndexed { index, param ->
                EtsMethodParameter(
                    index = index,
                    name = param.name,
                    type = convertToEtsType(param.type),
                    isOptional = param.isOptional
                )
            },
            returnType = convertToEtsType(method.returnType),
        )
    )
}

fun convertToEtsMethod(method: MethodDto): EtsMethod {
    val signature = convertToEtsMethodSignature(method.signature)
    // Note: locals are not used in the current implementation
    // val locals = method.body.locals.map {
    //     convertToEtsEntity(it) as EtsLocal  // safe cast
    // }
    val localsCount = method.body.locals.size
    val modifiers = method.modifiers
        .filterIsInstance<ModifierDto.StringItem>()
        .map { it.modifier }
    val builder = EtsMethodBuilder(signature, localsCount, modifiers)
    val etsMethod = builder.build(method.body.cfg)
    return etsMethod
}

fun convertToEtsField(field: FieldDto): EtsField {
    return EtsFieldImpl(
        signature = EtsFieldSignature(
            enclosingClass = convertToEtsClassSignature(field.signature.enclosingClass),
            sub = EtsFieldSubSignature(
                name = field.signature.name,
                type = convertToEtsType(field.signature.type),
            )
        ),
        modifiers = field.modifiers
            ?.filterIsInstance<ModifierDto.StringItem>()
            ?.map { it.modifier }
            .orEmpty(),
        isOptional = field.isOptional,
        isDefinitelyAssigned = field.isDefinitelyAssigned,
    )
}

fun convertToEtsFile(file: EtsFileDto): EtsFile {
    val classesFromNamespaces = file.namespaces.flatMap { it.classes }
    val allClasses = file.classes + classesFromNamespaces
    val convertedClasses = allClasses.map { convertToEtsClass(it) }
    return EtsFile(
        name = file.name,
        path = file.absoluteFilePath,
        classes = convertedClasses,
    )
}