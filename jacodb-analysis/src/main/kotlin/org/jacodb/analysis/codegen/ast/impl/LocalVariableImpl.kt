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

package org.jacodb.analysis.codegen.ast.impl

import org.jacodb.analysis.codegen.ast.base.*
import org.jacodb.analysis.codegen.ast.base.presentation.callable.CallablePresentation
import org.jacodb.analysis.codegen.ast.base.presentation.callable.local.LocalVariablePresentation
import org.jacodb.analysis.codegen.ast.base.typeUsage.TypeUsage

class LocalVariableImpl(
    override val usage: TypeUsage,
    // currently we prohibit shadowing local variables,
    // it means that local variables and parameters can be identified by its name and parent function
    override val shortName: String,
    override val initialValue: CodeValue?,
    override val parentCallable: CallablePresentation,
    override var comments: ArrayList<String> = ArrayList()
) : FunctionLocalImpl(), LocalVariablePresentation