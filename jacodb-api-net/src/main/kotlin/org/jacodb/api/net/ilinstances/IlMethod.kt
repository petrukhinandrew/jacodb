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

package org.jacodb.api.net.ilinstances

import org.example.ilinstances.IlInstance
import org.example.ilinstances.IlType
import org.jacodb.api.net.IlMethodExtFeature
import org.jacodb.api.net.IlPublication
import org.jacodb.api.net.ResolvedInstructionsResult
import org.jacodb.api.net.generated.models.*
import kotlin.LazyThreadSafetyMode.PUBLICATION

class IlMethod(val declaringType: IlType, private val dto: IlMethodDto) : IlInstance {
    private val publication: IlPublication
        get() = declaringType.publication
    val returnType: IlType? by lazy { dto.returnType.let { publication.findIlTypeOrNull(dto.returnType.typeName) } }
    val name: String = dto.name

    val parametes: List<IlParameter> by lazy(PUBLICATION) {
        dto.parameters.map { IlParameter(it, this) }.toMutableList()
    }
    val attributes: List<IlAttribute> by lazy(PUBLICATION) { dto.attrs.map { IlAttribute(it, publication) } }
    val rawInstList: List<IlStmtDto>
        get() = dto.rawInstList

    val instList: List<IlStmt> by lazy {
        publication.featuresChain.callUntilResolved<IlMethodExtFeature, ResolvedInstructionsResult> { it.instList(this) }!!.instructions
    }

    val resolved: Boolean = dto.resolved

    // TODO args next to parameters seems defn improper
    val args: List<IlArgument> by lazy(PUBLICATION) { dto.parameters.map { IlArgument(it) }.toList() }
    val locals: List<IlLocalVar> by lazy(PUBLICATION) { dto.locals.map { IlLocalVar(it, publication) } }
    val temps: List<IlTempVar> by lazy(PUBLICATION) { dto.temps.map { IlTempVar(it, publication) } }
    val errs: List<IlErrVar> by lazy(PUBLICATION) { dto.errs.map { IlErrVar(it, publication) } }
//    val scopes: List<IlEhScope> by lazy(PUBLICATION) { dto.ehScopes.map { IlEhScope.deserialize(this, it) } }

    override fun toString(): String {
        return "${returnType ?: ""} $name(${parametes.joinToString(", ")})"
    }
}

class IlParameter(private val dto: IlParameterDto, val enclosingMethod: IlMethod) : IlInstance {
    private val publication: IlPublication
        get() = enclosingMethod.declaringType.publication
    val paramType: IlType by lazy(PUBLICATION) { publication.findIlTypeOrNull(dto.type.typeName)!! }
    val attributes: List<IlAttribute> by lazy(PUBLICATION) { dto.attrs.map { IlAttribute(it, publication) } }
    val index: Int = dto.index
    val name: String = dto.name
    val defaultValue: IlConstant? get() = dto.defaultValue?.deserializeConst(publication)

    override fun toString(): String {
        return paramType.toString()
    }
}

abstract class IlEhScope {
    abstract val tb: IlStmt
    abstract val te: IlStmt
    abstract val hb: IlStmt
    abstract val he: IlStmt

    companion object {
        fun deserialize(src: IlMethod, dto: IlEhScopeDto): IlEhScope {
            TODO()
//            return when (dto) {
//                is IlFilterScopeDto -> IlFilterScope(
//                    src.rawInstList[dto.tb],
//                    src.rawInstList[dto.te - 1],
//                    src.rawInstList[dto.hb],
//                    src.rawInstList[dto.he - 1],
//                    src.rawInstList[dto.fb]
//                )
//
//                is IlCatchScopeDto -> IlCatchScope(
//                    src.rawInstList[dto.tb],
//                    src.rawInstList[dto.te - 1],
//                    src.rawInstList[dto.hb],
//                    src.rawInstList[dto.he - 1]
//                )
//
//                is IlFaultScopeDto -> IlFaultScope(
//                    src.rawInstList[dto.tb],
//                    src.rawInstList[dto.te - 1],
//                    src.rawInstList[dto.hb],
//                    src.rawInstList[dto.he - 1]
//                )
//
//                is IlFinallyScopeDto -> IlFinallyScope(
//                    src.rawInstList[dto.tb],
//                    src.rawInstList[dto.te - 1],
//                    src.rawInstList[dto.hb],
//                    src.rawInstList[dto.he - 1]
//                )
//
//                else -> throw NotImplementedError()
//            }
        }
    }
}

class IlCatchScope(override val tb: IlStmt, override val te: IlStmt, override val hb: IlStmt, override val he: IlStmt) :
    IlEhScope()

class IlFilterScope(
    override val tb: IlStmt,
    override val te: IlStmt,
    override val hb: IlStmt,
    override val he: IlStmt,
    val fb: IlStmt
) : IlEhScope()

class IlFinallyScope(
    override val tb: IlStmt,
    override val te: IlStmt,
    override val hb: IlStmt,
    override val he: IlStmt
) :
    IlEhScope()

class IlFaultScope(override val tb: IlStmt, override val te: IlStmt, override val hb: IlStmt, override val he: IlStmt) :
    IlEhScope()
