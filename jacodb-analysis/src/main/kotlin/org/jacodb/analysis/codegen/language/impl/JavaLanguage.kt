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

package org.jacodb.analysis.codegen.language.impl

import org.jacodb.analysis.codegen.language.base.TargetLanguage
import org.jacodb.analysis.codegen.impossibleGraphId
import java.io.BufferedOutputStream
import java.io.File
import java.io.OutputStreamWriter
import java.nio.file.Path
import kotlin.io.path.outputStream
import org.jacodb.analysis.codegen.ast.impl.*
import org.jacodb.analysis.codegen.ast.base.*
import org.jacodb.analysis.codegen.currentDispatch
import org.jacodb.analysis.codegen.dispatcherQueueName

class JavaLanguage : TargetLanguage {
    private val realPrimitivesName = mutableMapOf<TargetLanguage.PredefinedPrimitives, String>()
    private val predefinedTypes = mutableMapOf<String, TypePresentation>()
    private val integer: TypePresentation
    private val arrayDeque: TypePresentation

    init {
        realPrimitivesName[TargetLanguage.PredefinedPrimitives.VOID] = TypePresentation.voidType.shortName
        predefinedTypes[TypePresentation.voidType.shortName] = TypePresentation.voidType

        integer = TypeImpl("Integer")
        realPrimitivesName[TargetLanguage.PredefinedPrimitives.INT] = integer.shortName
        predefinedTypes[integer.shortName] = integer

        // type argument cannot be primitive
        arrayDeque = TypeImpl("ArrayDeque<Integer>")
        arrayDeque.createMethod(impossibleGraphId, name = "add", parameters = listOf(integer.instanceType to "e"))
        arrayDeque.createMethod(impossibleGraphId, name = "remove", returnType = integer.instanceType)
        predefinedTypes[arrayDeque.shortName] = arrayDeque
    }

    override fun dispatch(callable: org.jacodb.analysis.codegen.ast.base.CallablePresentation) {
        val integerType = getPredefinedPrimitive(TargetLanguage.PredefinedPrimitives.INT)!!
        val integerUsage = integerType.instanceType
        val current = callable.getOrCreateLocalVariable(currentDispatch, integerUsage, object :
            DirectStringSubstitution {
            override val substitution: String = "-1"
            override val evaluatedType: TypeUsage = integerUsage
        })
        val dispatcherQueue = callable.getLocal(dispatcherQueueName)!!
        val dispatcherReference = dispatcherQueue.reference
        val removeMethod = arrayDeque.getMethods("remove").single()
        val methodInvocation = MethodInvocationExpressionImpl(removeMethod, dispatcherReference)
        val assignment =
            org.jacodb.analysis.codegen.ast.impl.AssignmentExpressionImpl(current.reference, methodInvocation)
        callable.preparationSite.addAfter(assignment)
    }

    override fun resolveProjectPathToSources(projectPath: Path): Path {
        val relativeJavaSourceSetPath = "UtBotTemplateForIfdsSyntheticTests\\src\\main\\java\\org\\utbot\\ifds\\synthetic\\tests"

        return projectPath.resolve(relativeJavaSourceSetPath.replace('\\', File.separatorChar))
    }

    override fun projectZipInResourcesName(): String {
        return "UtBotTemplateForIfdsSyntheticTests.zip"
    }

    override fun getPredefinedType(name: String): TypePresentation? {
        return predefinedTypes[name]
    }

    override fun getPredefinedPrimitive(primitive: TargetLanguage.PredefinedPrimitives): TypePresentation? {
        return predefinedTypes[realPrimitivesName[primitive]]
    }

    private var fileWriter: OutputStreamWriter? = null

    private fun inFile(fileName: String, pathToSourcesDir: Path, block: () -> Unit) {
        val javaFilePath = pathToSourcesDir.resolve("${fileName.capitalize()}.java")
        try {
            javaFilePath.outputStream().use {
                fileWriter = OutputStreamWriter(BufferedOutputStream(it))
                appendLine { write("package org.utbot.ifds.synthetic.tests") }
                // for simplification - all generated code will be in a single package
                // with all required imports added unconditionally to all files.
                appendLine { write("import java.util.*") }
                write("\n")
                block()
                flush()
            }
        } finally {
            fileWriter = null
        }
    }

    private fun flush() {
        fileWriter!!.flush()
    }

    private fun write(content: String) {
        fileWriter!!.write(content)
    }

    private fun writeSeparated(content: String) {
        write(content)
        write(" ")
    }

    // write - just writes
    // append - handles with tabulation and necessary semicolons
    // dump - accept code element and creates text file with its representation

    private fun writeVisibility(visibility: VisibilityModifier) {
        writeSeparated(visibility.toString().lowercase())
    }

    private fun writeTypeSignature(type: TypePresentation) {
        writeVisibility(type.visibility)
        when (type.inheritanceModifier) {
            InheritanceModifier.ABSTRACT -> writeSeparated("abstract class")
            InheritanceModifier.OPEN -> writeSeparated("class")
            InheritanceModifier.FINAL -> writeSeparated("final class")
            InheritanceModifier.INTERFACE -> writeSeparated("interface")
            InheritanceModifier.STATIC -> writeSeparated("static class")
            InheritanceModifier.ENUM -> writeSeparated("enum")
        }
        writeSeparated(type.shortName)
    }

    private var offset = 0

    private fun addTab() {
        ++offset
    }

    private fun removeTab() {
        --offset
    }

    private fun tabulate() {
        for (i in 0 until offset) {
            write("\t")
        }
    }

    private fun throwCannotDump(ce: CodeElement) {
        assert(false) { "Do not know how to dump ${ce.javaClass.simpleName}" }
    }

    private fun inScope(block: () -> Unit) {
        try {
            write("{\n")
            addTab()
            // do not tabulate here.
            // each code element should be aware if it has to be tabulated and manage tabulation on its own
            block()
        } finally {
            write("\n")
            removeTab()
            tabulate()
            write("}\n")
        }
    }

    private fun writeParametersList(callable: org.jacodb.analysis.codegen.ast.base.CallablePresentation) {
        write("(")
        var first = true
        for (parameter in callable.parameters) {
            if (!first) {
                write(", ")
            }
            first = false
            writeTypeUsage(parameter.usage)
            write(parameter.shortName)
        }
        writeSeparated(")")
    }

    private fun appendField(field: FieldPresentation) {
        // todo
    }

    private fun writeTypeUsage(typeUsage: TypeUsage) {
        writeSeparated(typeUsage.stringPresentation)
    }

    private fun writeDefaultValueForTypeUsage(typeUsage: TypeUsage) {
        when (typeUsage) {
            is org.jacodb.analysis.codegen.ast.base.ArrayTypeUsage -> {
                throwCannotDump(typeUsage)
            }
            is InstanceTypeUsage -> {
                val presentation = typeUsage.typePresentation
                val valueToWrite = presentation.defaultValue
                writeCodeValue(valueToWrite)
            }
            else -> {
                throwCannotDump(typeUsage)
            }
        }
    }

    private fun appendCodeExpression(codeExpression: CodeExpression) = appendLine { writeCodeExpression(codeExpression) }

    private fun writeCodeExpression(codeExpression: CodeExpression) {
        when (codeExpression) {
            is ValueExpression -> writeValueExpression(codeExpression)
            is org.jacodb.analysis.codegen.ast.base.AssignmentExpression -> {
                writeCodeValue(codeExpression.assignmentTarget)
                write(" = ")
                writeCodeValue(codeExpression.assignmentValue)
            }
            // todo returnStatement
            else -> {
                throwCannotDump(codeExpression)
            }
            // 1. localVariable presentation
            // 2. return statement - expression
        }
    }

    private fun appendCodeValue(codeValue: CodeValue) = appendLine { writeCodeValue(codeValue) }

    private fun writeCodeValue(codeValue: CodeValue) {
        when (codeValue) {
            is DirectStringSubstitution -> write(codeValue.substitution)
            is ValueReference -> {
                val presentation = codeValue.resolve()
                write(presentation.shortName)
            }
            is ValueExpression -> writeValueExpression(codeValue)
            else -> {
                throwCannotDump(codeValue)
            }
        }
    }

    private fun writeValueExpression(valueExpression: ValueExpression) {
        when (valueExpression) {
            // TODO creation of arrays?
            is ObjectCreationExpression -> {
                writeSeparated("new")
                writeSeparated(valueExpression.invokedConstructor.containingType.shortName)
                writeCallList(valueExpression)
            }
            is MethodInvocationExpression -> {
                writeCodeValue(valueExpression.invokedOn)
                write(".")
                write(valueExpression.invokedMethod.shortName)
                writeCallList(valueExpression)
            }
            is FunctionInvocationExpression -> {
                write(classNameForStaticFunction(valueExpression.invokedCallable.shortName))
                write(".")
                write(valueExpression.invokedCallable.shortName)
                writeCallList(valueExpression)
            }
            else -> {
                throwCannotDump(valueExpression)
            }
        }
    }

    private fun writeCallList(invocationExpression: InvocationExpression) {
        write("(")
        var first = true
        for (parameter in invocationExpression.invokedCallable.parameters) {
            if (!first) {
                write(", ")
            }
            first = false
            val argument: CodeValue? = invocationExpression.parameterToArgument[parameter]
            if (argument == null) {
                writeDefaultValueForTypeUsage(parameter.usage)
            } else {
                writeCodeValue(argument)
            }
        }
        write(")")
    }

    private fun appendLocalVariable(localVariablePresentation: LocalVariablePresentation) = appendLine {
        writeTypeUsage(localVariablePresentation.usage)
        writeSeparated(localVariablePresentation.shortName)

        val initialValue = localVariablePresentation.initialValue

        if (initialValue != null) {
            writeSeparated("=")
            writeCodeValue(initialValue)
        } else {
            writeSeparated("= null")
        }
    }

// 1. assignment + in vulnerability transit do dispatch
//
// 2. refactor part with expressions in site
// 3. correct styling
// commentaries

// statistics dump - vulnerability id, source, sink, path

// optionally - conditional paths
// optionally - kotlinx serialization for hierarchy

    private fun appendLine(block: () -> Unit) {
        try {
            tabulate()
            block()
        }
        finally {
            write(";\n")
        }
    }

    private fun appendSite(site: Site) {
        // we always dump sites in following order:
        // 1. preparation site
        // 2. call sites
        // 3. termination site
        // there are no different function for each site as their logic is connected:
        // there is always termination site in each callable.
        // so after each call site `else` is added.
        when (site) {
            is PreparationSite -> {
                for (before in site.expressionsBefore) {
                    appendCodeExpression(before)
                }
                for (after in site.expressionsAfter) {
                    appendCodeExpression(after)
                }
            }
            is org.jacodb.analysis.codegen.ast.base.CallSite -> {
                tabulate()
                writeSeparated("if (currentDispatch == ${site.graphId})")
                inScope {
                    for (before in site.expressionsBefore) {
                        appendCodeExpression(before)
                    }
                    appendCodeValue(site.invocationExpression)
                    for (after in site.expressionsAfter) {
                        appendCodeExpression(after)
                    }
                }
                tabulate()
                writeSeparated("else")
            }
            is TerminationSite -> {
                inScope {
                    for (before in site.expressionsBefore) {
                        appendCodeExpression(before)
                    }
                    for (dereference in site.dereferences) {
                        appendLine {
                            writeCodeValue(dereference)
                            write(".toString()")
                        }
                    }
                    for (after in site.expressionsAfter) {
                        appendCodeExpression(after)
                    }
                }
            }
        }
    }

    private fun appendLocalsAndSites(callable: org.jacodb.analysis.codegen.ast.base.CallablePresentation) {
        val localVariables = callable.localVariables
        for (variable in localVariables) {
            appendLocalVariable(variable)
        }
        appendSite(callable.preparationSite)
        val sites = callable.callSites
        for (site in sites) {
            appendSite(site)
        }
        appendSite(callable.terminationSite)
    }

    private fun appendConstructor(constructor: ConstructorPresentation) {
        tabulate()
        writeVisibility(constructor.visibility)
        write(constructor.containingType.shortName)
        writeParametersList(constructor)
        inScope {
            val parentCall = constructor.parentConstructorCall
            if (parentCall != null) {
                appendLine {
                    write("super")
                    writeCallList(parentCall)
                }
            }
            appendLocalsAndSites(constructor)
        }
    }

    private fun appendStaticFunction(function: FunctionPresentation) = appendStartFunction(function.shortName, function)

    private fun classNameForStaticFunction(functionName: String): String {
        return "ClassFor${functionName.capitalize()}"
    }

    private fun appendStartFunction(name: String, function: FunctionPresentation) {
        writeSeparated("public class ${classNameForStaticFunction(name)}")
        inScope {
            tabulate()
            writeVisibility(function.visibility)
            writeSeparated("static")
            writeTypeUsage(function.returnType)
            write(function.shortName)
            writeParametersList(function)
            inScope {
                appendLocalsAndSites(function)
            }
        }
    }

    private fun appendMethodSignature(methodPresentation: MethodPresentation) {
        tabulate()
        if (methodPresentation.inheritedFrom != null) {
            writeSeparated("@Override\n")
        }
        tabulate()
        writeVisibility(methodPresentation.visibility)
        when (methodPresentation.inheritanceModifier) {
            InheritanceModifier.ABSTRACT -> writeSeparated("abstract")
            InheritanceModifier.FINAL -> writeSeparated("final")
            InheritanceModifier.STATIC -> writeSeparated("static")
            else -> {
                assert(false) { "should be impossible" }
            }
        }
        writeSeparated(methodPresentation.shortName)
        writeParametersList(methodPresentation)
    }

    private fun appendMethod(methodPresentation: MethodPresentation) {
        appendMethodSignature(methodPresentation)
        inScope {
            appendLocalsAndSites(methodPresentation)
        }
    }

    override fun dumpType(type: TypePresentation, pathToSourcesDir: Path) = inFile(type.shortName, pathToSourcesDir) {
        writeTypeSignature(type)
        inScope {
            for (field in type.implementedFields) {
                appendField(field)
            }
            for (constructor in type.constructors) {
                appendConstructor(constructor)
            }
            for (method in type.implementedMethods) {
                appendMethod(method)
            }

            val staticCounterPart = type.staticCounterPart

            for (staticField in staticCounterPart.implementedFields) {
                appendField(staticField)
            }
            for (staticMethod in staticCounterPart.implementedMethods) {
                appendMethod(staticMethod)
            }
        }
    }

    override fun dumpFunction(func: FunctionPresentation, pathToSourcesDir: Path) = inFile(classNameForStaticFunction(func.shortName), pathToSourcesDir) {
        appendStaticFunction(func)
    }

    override fun dumpStartFunction(name: String, func: FunctionPresentation, pathToSourcesDir: Path) =
        inFile(classNameForStaticFunction(name), pathToSourcesDir) {
            appendStartFunction(name, func)
        }
}