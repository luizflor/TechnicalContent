import com.techware.nodejs.GenerateNodeJS
import com.techware.generate.Descriptor
import com.techware.nodejs.GenerateNodeJS.Companion.copyPackageJson
import com.techware.nodejs.GenerateNodeJS.Companion.generateBatchFiles
import com.techware.nodejs.GenerateNodeJS.Companion.generateFakeNodeJS
import com.techware.nodejs.GenerateNodeJS.Companion.generateFakeNodeJSapi
import com.techware.nodejs.GenerateNodeJS.Companion.generateFakeNodeJScsv
import com.techware.nodejs.GenerateNodeJSIndex.Companion.generateIndex
import com.techware.nodejs.GenerateNodeJSIndex.Companion.generateIndexJson
import com.techware.nodejs.GenerateNodeJSIndex.Companion.generateUnixJson
import com.techware.nodejs.GenerateNodeJSIndex.Companion.generateWindowsJson
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class GenerateFakeJsonTest {
    @Test
    fun generateFakeApi() {
        val className ="person"
        val descriptorList = Descriptor.getDescriptorFromFile(FILE_NAME_PERSON1)
        require(descriptorList.isNotEmpty()) {"descriptorList is empty"}

        val fakeApi = generateFakeNodeJSapi(descriptorList, className = className)
        println(fakeApi)
        val expected ="module.exports = function(){\n" +
                "    var faker = require(\"faker\");\n" +
                "    var _ = require(\"lodash\");\n" +
                "    return {\n" +
                "\tperson: _.times(100, function(n){\n" +
                "\t\treturn{\n" +
                "\t\t\tid: n,\n" +
                "\t\t\tfirstname: faker.lorem.word(),\n" +
                "\t\t\tlastname: faker.lorem.word(),\n" +
                "            }\n" +
                "        })\n" +
                "    }\n" +
                "}\n"
        assertEquals(expected,fakeApi)

        Descriptor.saveToFile(text=fakeApi, className = className, targetFolder = "$PATH_DESTINATION_NODEJS/$className", extentionName = ".js")

    }

    @Test
    fun generateFakeCsv() {
        val className ="person"
        val descriptorList = Descriptor.getDescriptorFromFile(FILE_NAME_PERSON1)
        require(descriptorList.isNotEmpty()) {"descriptorList is empty"}

        val fakeCsv = generateFakeNodeJScsv(descriptorList=descriptorList, className =className)
        println(fakeCsv)

        val expected ="var person = require(\"./person\");\n" +
                "var fs = require('fs');\n" +
                "const path = require('path');\n" +
                "var data = `id, firstname, lastname\\n`\n" +
                "person().person.forEach(e => {\n" +
                "\tdata = data + `\${e.id}, \${e.firstname}, \${e.lastname}\\n`\n" +
                "});\n" +
                "var dirData = './data';\n" +
                "if (!fs.existsSync(dirData)) {\n" +
                "    fs.mkdirSync(dirData);\n" +
                "}\n" +
                "var fileName = __dirname+\"/data/person.csv\";\n" +
                "fs.writeFile(fileName,data, function(err) {\n" +
                "    if(err) return console.log(err);\n" +
                "});\n"
        assertEquals(expected, fakeCsv)

        Descriptor.saveToFile(text=fakeCsv, className = "${className}_csv", targetFolder = "$PATH_DESTINATION_NODEJS/$className", extentionName = ".js")
    }

    @Test
    fun generateBatchFilesTest() {
        val className = "person"
        generateBatchFiles(className=className, targetFolder = PATH_DESTINATION_NODEJS)
    }

    @Test
    fun copyPackageJsonTest(){
//        File(PACKAGE_JSON).copyTo(File("$PATH_DESTINATION_NODEJS/package.json"),true)
        copyPackageJson(PATH_DESTINATION_NODEJS)
    }

    @Test
    fun generateIndexText() {
        val classNames = arrayListOf("person", "person2")
        val index = generateIndex(classNames)
//        println(index)

        val expected =
            "module.exports = function () {\n" +
                    "var person = require(\"../nodejs/Person/Person\");\n" +
                    "var person2 = require(\"../nodejs/Person2/Person2\");\n" +
                    "        return {\n" +
                    "            person: person().person,\n" +
                    "            person2: person2().person2,\n" +
                    "        }\n" +
                    "}\n"

        assertEquals(expected, index)
    }

    @Test
    fun generateUnixJsonTest() {
        val json = generateUnixJson()
        println(json)
        val expected =
            "#!/usr/bin/env bash\n" +
                    "json-server --port 3001 index.js\n"
        assertEquals(expected, json)
    }

    @Test
    fun generateWindowsJsonTest() {
        val json = generateWindowsJson()
        println(json)
        val expected =
                    "json-server --port 3001 index.js\n"
        assertEquals(expected, json)
    }

    @Test
    fun generateFakeNodeJSTest() {
        val className ="person"
        generateFakeNodeJS(fileName =  FILE_NAME_PERSON1,className=className,packageName = "",targetFolder = PATH_DESTINATION_NODEJS)
    }

    @Test
    fun generateFakeIndexJsonTest() {
        val classNames = arrayListOf("person", "person2")
        generateIndexJson(classNames=classNames, targetFolder = PATH_DESTINATION_NODEJS)
    }

    @Test
    fun generateNodeJSFilesTest() {
        GenerateNodeJS.generateNodeJSFiles(targetDescriptor = DESCRIPTOR_FILES, targetFolder = PATH_DESTINATION_NODEJS)
    }


    companion object {
        const val FILE_NAME_PERSON1 = "./src/test/resources/Descriptors/person1.txt"
        const val PACKAGE_JSON = "./src/test/resources/nodejs/package.json"
//        const val FILE_NAME_PERSON1 = "/Users/luizsilva/Projects/Research/TechnicalContent/KotlinUtilities/KotlinTests/src/main/kotlin/generator/kotlin/Person.csv"
        const val PATH_DESTINATION_MODEL = "/Users/luizsilva/Projects/Research/TechnicalContent/KotlinUtilities/KotlinTests/src/main/kotlin/model"
        const val PATH_DESTINATION_KOTLIN = "/Users/luizsilva/Projects/Research/TechnicalContent/KotlinUtilities/KotlinTests/src/main/kotlin/generator/kotlin"
        const val PATH_DESTINATION_NODEJS = "/Users/luizsilva/Projects/Research/TechnicalContent/KotlinUtilities/KotlinTests/src/main/kotlin/generator/nodejs"
        const val DESCRIPTOR_FILES = "./src/test/resources/Descriptors"
    }
}