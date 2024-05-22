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

package panda

import org.jacodb.panda.staticvm.cfg.PandaApplicationGraph
import org.jacodb.panda.staticvm.classpath.PandaProject
import org.junit.jupiter.api.Test

class PandaIrDeserializationTest {

    companion object {
        private const val SAMPLE_FILE_PATH: String = "sample.abc.ir"
    }

    @Test
    fun deserializationTest() {
        val filePath = SAMPLE_FILE_PATH
        val program = loadProgram("/$filePath")
        val project = PandaProject.fromProgramIr(program, withStdLib = true)
        val applicationGraph = PandaApplicationGraph(project)
    }

    @Test
    fun catchTest() {
        val filePath = "try_catch_finally.abc.ir"
        val program = loadProgram("/$filePath")
        val project = PandaProject.fromProgramIr(program, withStdLib = true)
        val applicationGraph = PandaApplicationGraph(project)
    }

    /*
    private val stdlibFilePath = javaClass.getResource("stdlib.ir")?.path!!

    @OptIn(ExperimentalSerializationApi::class, ExperimentalTime::class)
    @Test
    fun pandaStdLibTest() {
        val input = FileInputStream(stdlibFilePath)

        val (program, deserializationDuration) = measureTimedValue {
            json.decodeFromStream<PandaProgramIr>(input)
        }
        val (project, linkageDuration) = measureTimedValue {
            PandaProject.fromProgramInfo(program)
        }

        println("deserialization: $deserializationDuration, linkage: $linkageDuration")
        println("total: ${deserializationDuration + linkageDuration}")
    }
    */

    @Test
    fun pandaClasspathFlowGraphTest() {
        val filePath = SAMPLE_FILE_PATH
        val program = loadProgram("/$filePath")
        val project = PandaProject.fromProgramIr(program, withStdLib = true)
        val method = project.findMethod("A.greet:i32;void;")
        val flowGraph = method.flowGraph()
    }
}