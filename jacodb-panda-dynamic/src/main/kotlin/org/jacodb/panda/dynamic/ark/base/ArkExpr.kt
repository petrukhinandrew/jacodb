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

package org.jacodb.panda.dynamic.ark.base

import org.jacodb.api.common.cfg.CommonCallExpr
import org.jacodb.panda.dynamic.ark.graph.BasicBlock
import org.jacodb.panda.dynamic.ark.model.MethodSignature

interface ArkExpr : ArkEntity {
    interface Visitor<out R> {
        fun visit(expr: NewExpr): R
        fun visit(expr: NewArrayExpr): R
        fun visit(expr: TypeOfExpr): R
        fun visit(expr: InstanceOfExpr): R
        fun visit(expr: LengthExpr): R
        fun visit(expr: CastExpr): R
        fun visit(expr: PhiExpr): R
        fun visit(expr: ArkUnaryOperation): R
        fun visit(expr: ArkBinaryOperation): R
        fun visit(expr: ArkRelationOperation): R
        fun visit(expr: ArkInstanceCallExpr): R
        fun visit(expr: ArkStaticCallExpr): R

        interface Default<out R> : Visitor<R> {
            override fun visit(expr: NewExpr): R = defaultVisit(expr)
            override fun visit(expr: NewArrayExpr): R = defaultVisit(expr)
            override fun visit(expr: TypeOfExpr): R = defaultVisit(expr)
            override fun visit(expr: InstanceOfExpr): R = defaultVisit(expr)
            override fun visit(expr: LengthExpr): R = defaultVisit(expr)
            override fun visit(expr: CastExpr): R = defaultVisit(expr)
            override fun visit(expr: PhiExpr): R = defaultVisit(expr)
            override fun visit(expr: ArkUnaryOperation): R = defaultVisit(expr)
            override fun visit(expr: ArkBinaryOperation): R = defaultVisit(expr)
            override fun visit(expr: ArkRelationOperation): R = defaultVisit(expr)
            override fun visit(expr: ArkInstanceCallExpr): R = defaultVisit(expr)
            override fun visit(expr: ArkStaticCallExpr): R = defaultVisit(expr)

            fun defaultVisit(expr: ArkExpr): R
        }
    }

    override fun <R> accept(visitor: ArkEntity.Visitor<R>): R {
        return accept(visitor as Visitor<R>)
    }

    fun <R> accept(visitor: Visitor<R>): R
}

data class NewExpr(
    override val type: ArkType, // ClassType
) : ArkExpr {
    override fun toString(): String {
        return "new ${type.typeName}"
    }

    override fun <R> accept(visitor: ArkExpr.Visitor<R>): R {
        return visitor.visit(this)
    }
}

data class NewArrayExpr(
    val elementType: ArkType,
    val size: ArkEntity,
) : ArkExpr {
    // TODO: support multi-dimensional arrays
    override val type: ArkType
        get() = ArrayType(elementType, 1)

    override fun toString(): String {
        return "new ${elementType.typeName}[$size]"
    }

    override fun <R> accept(visitor: ArkExpr.Visitor<R>): R {
        return visitor.visit(this)
    }
}

data class TypeOfExpr(
    val arg: ArkEntity,
) : ArkExpr {
    override val type: ArkType
        get() = StringType

    override fun toString(): String {
        return "typeof $arg"
    }

    override fun <R> accept(visitor: ArkExpr.Visitor<R>): R {
        return visitor.visit(this)
    }
}

data class InstanceOfExpr(
    val arg: ArkEntity,
    val checkType: ArkType,
) : ArkExpr {
    override val type: ArkType
        get() = BooleanType

    override fun toString(): String {
        return "$arg instanceof $checkType"
    }

    override fun <R> accept(visitor: ArkExpr.Visitor<R>): R {
        return visitor.visit(this)
    }
}

data class LengthExpr(
    val arg: ArkEntity,
) : ArkExpr {
    override val type: ArkType
        get() = NumberType

    override fun toString(): String {
        return "$arg.length"
    }

    override fun <R> accept(visitor: ArkExpr.Visitor<R>): R {
        return visitor.visit(this)
    }
}

data class CastExpr(
    val arg: ArkEntity,
    override val type: ArkType,
) : ArkExpr {
    override fun toString(): String {
        return "$arg as $type"
    }

    override fun <R> accept(visitor: ArkExpr.Visitor<R>): R {
        return visitor.visit(this)
    }
}

// data class TernaryExpr(
//     val condition: Value,
//     val trueBranch: Value,
//     val falseBranch: Value,
// ) : Expr {
//     override val type: Type
//         get() = TypeInference.commonType(trueBranch.type, trueBranch.type)
//
//     override fun toString(): String {
//         return "$condition ? $trueBranch : $falseBranch"
//     }
// }

data class PhiExpr(
    val args: List<ArkEntity>,
    val argToBlock: Map<ArkEntity, BasicBlock>,
    override val type: ArkType,
) : ArkExpr {
    override fun toString(): String {
        return "phi(${args.joinToString()})"
    }

    override fun <R> accept(visitor: ArkExpr.Visitor<R>): R {
        return visitor.visit(this)
    }
}

interface ArkUnaryExpr : ArkExpr {
    val arg: ArkEntity

    override val type: ArkType
        get() = arg.type
}

data class ArkUnaryOperation(
    val op: UnaryOp,
    override val arg: ArkEntity,
) : ArkUnaryExpr {
    override fun toString(): String {
        return "$op$arg"
    }

    override fun <R> accept(visitor: ArkExpr.Visitor<R>): R {
        return visitor.visit(this)
    }
}

interface ArkBinaryExpr : ArkExpr {
    val left: ArkEntity
    val right: ArkEntity

    override val type: ArkType
        get() = TypeInference.infer(this)
}

// TODO: AddExpr and many others
// data class AddExpr(
//     override val left: Value,
//     override val right: Value,
// ) : BinaryExpr {
//     override fun toString(): String {
//         return "$left + $right"
//     }
// }

data class ArkBinaryOperation(
    val op: BinaryOp,
    override val left: ArkEntity,
    override val right: ArkEntity,
) : ArkBinaryExpr {
    override fun toString(): String {
        return "$left $op $right"
    }

    override fun <R> accept(visitor: ArkExpr.Visitor<R>): R {
        return visitor.visit(this)
    }
}

// data class ConditionExpr(
//     val relop: String,
//     override val left: Value,
//     override val right: Value,
// ) : BinaryExpr {
//     override fun toString(): String {
//         return "$left $relop $right"
//     }
// }

interface ArkConditionExpr : ArkBinaryExpr {
    override val type: ArkType
        get() = BooleanType
}

data class ArkRelationOperation(
    val relop: String,
    override val left: ArkEntity,
    override val right: ArkEntity,
) : ArkConditionExpr {
    override fun toString(): String {
        return "$left $relop $right"
    }

    override fun <R> accept(visitor: ArkExpr.Visitor<R>): R {
        return visitor.visit(this)
    }
}

interface ArkCallExpr : ArkExpr, CommonCallExpr {
    val method: MethodSignature
    override val args: List<ArkValue>

    override val type: ArkType
        get() = method.returnType
}

data class ArkInstanceCallExpr(
    val instance: ArkLocal,
    override val method: MethodSignature,
    override val args: List<ArkValue>,
) : ArkCallExpr {
    override fun toString(): String {
        return "$instance.${method.name}(${args.joinToString()})"
    }

    override fun <R> accept(visitor: ArkExpr.Visitor<R>): R {
        return visitor.visit(this)
    }
}

data class ArkStaticCallExpr(
    override val method: MethodSignature,
    override val args: List<ArkValue>,
) : ArkCallExpr {
    override fun toString(): String {
        return "${method.enclosingClass.name}.${method.name}(${args.joinToString()})"
    }

    override fun <R> accept(visitor: ArkExpr.Visitor<R>): R {
        return visitor.visit(this)
    }
}