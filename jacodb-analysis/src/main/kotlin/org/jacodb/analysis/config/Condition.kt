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

package org.jacodb.analysis.config

import org.jacodb.analysis.engine.Tainted
import org.jacodb.analysis.paths.startsWith
import org.jacodb.analysis.paths.toPath
import org.jacodb.api.JcClassType
import org.jacodb.api.cfg.JcBool
import org.jacodb.api.cfg.JcConstant
import org.jacodb.api.cfg.JcInt
import org.jacodb.api.cfg.JcStringConstant
import org.jacodb.api.cfg.JcValue
import org.jacodb.api.ext.isSubClassOf
import org.jacodb.configuration.And
import org.jacodb.configuration.AnnotationType
import org.jacodb.configuration.CallParameterContainsMark
import org.jacodb.configuration.Condition
import org.jacodb.configuration.ConditionVisitor
import org.jacodb.configuration.ConstantBooleanValue
import org.jacodb.configuration.ConstantEq
import org.jacodb.configuration.ConstantGt
import org.jacodb.configuration.ConstantIntValue
import org.jacodb.configuration.ConstantLt
import org.jacodb.configuration.ConstantMatches
import org.jacodb.configuration.ConstantStringValue
import org.jacodb.configuration.ConstantTrue
import org.jacodb.configuration.IsConstant
import org.jacodb.configuration.IsType
import org.jacodb.configuration.Not
import org.jacodb.configuration.Or
import org.jacodb.configuration.PositionResolver
import org.jacodb.configuration.SourceFunctionMatches
import org.jacodb.configuration.TypeMatches

interface DefaultConditionVisitor : ConditionVisitor<Boolean> {
    val defaultConditionHandler: (Condition) -> Boolean
        get() = { false }

    override fun visit(condition: And): Boolean {
        return condition.args.all { it.accept(this) }
    }

    override fun visit(condition: Or): Boolean {
        return condition.args.any { it.accept(this) }
    }

    override fun visit(condition: Not): Boolean {
        return !condition.arg.accept(this)
    }

    override fun visit(condition: IsConstant): Boolean = defaultConditionHandler(condition)

    override fun visit(condition: IsType): Boolean = defaultConditionHandler(condition)

    override fun visit(condition: AnnotationType): Boolean = defaultConditionHandler(condition)

    override fun visit(condition: ConstantEq): Boolean = defaultConditionHandler(condition)

    override fun visit(condition: ConstantLt): Boolean = defaultConditionHandler(condition)

    override fun visit(condition: ConstantGt): Boolean = defaultConditionHandler(condition)

    override fun visit(condition: ConstantMatches): Boolean = defaultConditionHandler(condition)

    override fun visit(condition: SourceFunctionMatches): Boolean = defaultConditionHandler(condition)

    override fun visit(condition: CallParameterContainsMark): Boolean = defaultConditionHandler(condition)

    override fun visit(condition: ConstantTrue): Boolean {
        return true
    }

    override fun visit(condition: TypeMatches): Boolean = defaultConditionHandler(condition)
}

class ConditionEvaluator(
    internal val positionResolver: PositionResolver<JcValue>,
) : DefaultConditionVisitor {
    override fun visit(condition: IsConstant): Boolean {
        val value = positionResolver.resolve(condition.position)
        return value is JcConstant
    }

    override fun visit(condition: IsType): Boolean {
        error("Unexpected condition: $condition")
    }

    override fun visit(condition: AnnotationType): Boolean {
        TODO("Not implemented yet")
    }

    override fun visit(condition: ConstantEq): Boolean {
        val value = positionResolver.resolve(condition.position)
        return when (val constant = condition.value) {
            is ConstantBooleanValue -> {
                value is JcBool && value.value == constant.value
            }

            is ConstantIntValue -> {
                value is JcInt && value.value == constant.value
            }

            is ConstantStringValue -> {
                value is JcStringConstant && value.value == constant.value
            }

            else -> error("Unexpected constant: $constant")
        }
    }

    override fun visit(condition: ConstantLt): Boolean {
        val value = positionResolver.resolve(condition.position)
        return when (val constant = condition.value) {
            is ConstantIntValue -> {
                value is JcInt && value.value < constant.value
            }

            else -> error("Unexpected constant: $constant")
        }
    }

    override fun visit(condition: ConstantGt): Boolean {
        val value = positionResolver.resolve(condition.position)
        return when (val constant = condition.value) {
            is ConstantIntValue -> {
                value is JcInt && value.value > constant.value
            }

            else -> error("Unexpected constant: $constant")
        }
    }

    override fun visit(condition: ConstantMatches): Boolean {
        val value = positionResolver.resolve(condition.position)
        val re = condition.pattern.toRegex()
        return re.matches(value.toString())
    }

    override fun visit(condition: SourceFunctionMatches): Boolean {
        TODO("Not implemented yet")
    }

    override fun visit(condition: CallParameterContainsMark): Boolean {
        error("This visitor does not support condition $condition. Use FactAwareConditionEvaluator instead")
    }

    override fun visit(condition: TypeMatches): Boolean {
        val value = positionResolver.resolve(condition.position)
        // return value.type == condition.type
        return when (val valueType = value.type) {
            is JcClassType -> {
                when (val conditionType = condition.type) {
                    is JcClassType -> valueType.jcClass isSubClassOf conditionType.jcClass
                    // TODO: handle primitive types also. See 'JcTypes.kt' for reference impl.
                    else -> false
                }
            }

            else -> false
        }
    }
}

class FactAwareConditionEvaluator(
    private val fact: Tainted,
    private val conditionEvaluator: ConditionEvaluator,
) : ConditionVisitor<Boolean> by conditionEvaluator {

    constructor(
        fact: Tainted,
        positionResolver: PositionResolver<JcValue>,
    ) : this(fact, ConditionEvaluator(positionResolver))

    override fun visit(condition: CallParameterContainsMark): Boolean {
        if (fact.mark == condition.mark) {
            val value = conditionEvaluator.positionResolver.resolve(condition.position)
            val variable = value.toPath()
            if (variable.startsWith(fact.variable)) {
                return true
            }
        }
        return false
    }
}