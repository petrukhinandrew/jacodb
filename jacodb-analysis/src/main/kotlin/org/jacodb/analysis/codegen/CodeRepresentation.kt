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

package org.jacodb.analysis.codegen

import org.jacodb.analysis.codegen.ast.base.presentation.callable.CallablePresentation
import org.jacodb.analysis.codegen.ast.base.CodeElement
import org.jacodb.analysis.codegen.ast.base.DirectStringSubstitution
import org.jacodb.analysis.codegen.ast.base.presentation.callable.FunctionPresentation
import org.jacodb.analysis.codegen.ast.base.presentation.type.TypePresentation
import org.jacodb.analysis.codegen.ast.impl.FunctionImpl
import org.jacodb.analysis.codegen.ast.impl.MethodInvocationExpressionImpl
import org.jacodb.analysis.codegen.ast.impl.SimpleValueReference
import org.jacodb.analysis.codegen.ast.impl.TypeImpl
import org.jacodb.analysis.codegen.language.base.TargetLanguage
import java.nio.file.Path

class CodeRepresentation(private val language: TargetLanguage) :
    CodeElement {
    private val functions = mutableMapOf<Int, FunctionPresentation>()
    private var startFunctionIdCounter = startFunctionFirstId
    private val startFunctionToGenericId = mutableMapOf<String, Int>()
    private val generatedTypes = mutableMapOf<String, TypePresentation>()
    private val dispatchedCallables = mutableSetOf<Int>()

    fun createDispatch(callable: CallablePresentation) {
        if (dispatchedCallables.add(callable.graphId)) {
            language.dispatch(callable)
        }
    }

    fun getOrCreateFunctionFor(v: Int): FunctionPresentation {
        return functions.getOrPut(v) { FunctionImpl(v) }
    }

    fun getOrCreateStartFunction(name: String): FunctionPresentation {
        val startFunctionId = startFunctionToGenericId.getOrPut(name) { startFunctionIdCounter++ }

        return getOrCreateFunctionFor(startFunctionId)
    }

    fun getOrCreateType(name: String): TypePresentation {
        val generated = generatedTypes[name]
        val predefined = language.getPredefinedType(name)

        assert(generated == null || predefined == null) { "type with $name is generated and predefined simultaneously" }

        return generated ?: predefined ?: generatedTypes.getOrPut(name) { TypeImpl(name.capitalize()) }
    }

    fun getPredefinedType(name: String): TypePresentation? {
        return language.getPredefinedType(name)
    }

    fun getPredefinedPrimitive(primitive: TargetLanguage.PredefinedPrimitives): TypePresentation? {
        return language.getPredefinedPrimitive(primitive)
    }

    private fun generateOneLineComment(
        iterator: Int,
        readableConst: Int,
        numsOfDispatches: List<String>,
        currentComment: StringBuilder
    ) {
        val alreadyAddedToPreviousComment = iterator * readableConst
        val willAddedAfterCurrentIterration = minOf((iterator + 1) * readableConst, numsOfDispatches.size)
        for (i in alreadyAddedToPreviousComment until willAddedAfterCurrentIterration) {
            currentComment.append("${numsOfDispatches[i]} -> ")
        }
    }

    private fun generateCommentForStartFunction(function: FunctionPresentation): List<String> {
        val numsOfDispatches =
            function.preparationSite.expressionsBefore.filterIsInstance<MethodInvocationExpressionImpl>()
                .flatMap { expressionBefore ->
                    expressionBefore.parameterToArgument.map { dispatchValue -> dispatchValue.value }
                        .filterIsInstance<DirectStringSubstitution>()
                        .map { dispatchValue ->
                            dispatchValue.substitution
                        }.toList()
                }.toList()
        val numOfBreakingVarClass = numsOfDispatches[numsOfDispatches.size - 2].toInt()
        val breakingClass = functions[numOfBreakingVarClass]
        val breakingVar = breakingClass?.terminationSite?.dereferences?.filterIsInstance<SimpleValueReference>()?.get(0)
        val comments: ArrayList<String> = ArrayList()
        if (breakingVar != null) {
            comments.add(
                "This is start function for NullPointerException. The source for this issue is in variable " +
                        "${breakingVar.shortName}."
            )
        }
        val readableConst = 16
        val numOfDispatchComments =
            numsOfDispatches.size / readableConst + if (numsOfDispatches.size % readableConst == 0) 0 else 1
        var iterator = 0
        while (iterator < numOfDispatchComments) {
            val currentComment = StringBuilder()
            generateOneLineComment(
                iterator = iterator,
                readableConst = readableConst,
                numsOfDispatches = numsOfDispatches,
                currentComment = currentComment
            )
            comments.add(currentComment.toString())
            iterator++
        }
        val lastComment = comments[numOfDispatchComments]
        comments[numOfDispatchComments] = lastComment.substring(0, lastComment.length - 4)
        return comments
    }

    fun dumpTo(projectPath: Path) {
        val pathToSourcesDir = language.resolveProjectPathToSources(projectPath)
        for ((name, presentation) in generatedTypes) {
            language.dumpType(presentation, pathToSourcesDir)
        }

        for ((id, function) in functions) {
            if (id < startFunctionFirstId)
                language.dumpFunction(function, pathToSourcesDir)
        }

        for ((name, id) in startFunctionToGenericId) {
            val function = functions.getValue(id)
            val comments = generateCommentForStartFunction(function)
            function.addComments(comments)
            language.dumpStartFunction(name, function, pathToSourcesDir)
        }
    }

    companion object {
        const val startFunctionFirstId = Int.MAX_VALUE / 2
    }
}