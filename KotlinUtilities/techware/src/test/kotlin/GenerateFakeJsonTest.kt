import com.techware.utilities.Descriptor
import com.techware.utilities.GenerateNodeJS.Companion.copyPackageJson
import com.techware.utilities.GenerateNodeJS.Companion.generateBatchFiles
import com.techware.utilities.GenerateNodeJS.Companion.generateFakeNodeJS
import com.techware.utilities.GenerateNodeJS.Companion.generateFakeNodeJSapi
import com.techware.utilities.GenerateNodeJS.Companion.generateFakeNodeJScsv
import com.techware.utilities.GenerateNodeJS.Companion.generateUnixBatchCsv
import com.techware.utilities.GenerateNodeJS.Companion.generateUnixBatchJson
import com.techware.utilities.GenerateNodeJS.Companion.generateWindowsBatchCsv
import com.techware.utilities.GenerateNodeJS.Companion.generateWindowsBatchJson
import com.techware.utilities.Type
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File
import java.lang.StringBuilder

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
    fun generateFakeNodeJSTest() {
        val className ="person"
        generateFakeNodeJS(fileName =  FILE_NAME_PERSON1,className=className,packageName = "",targetFolder = PATH_DESTINATION_NODEJS)
    }


    companion object {
        const val FILE_NAME_PERSON1 = "./src/test/resources/Descriptors/person1.txt"
        const val PACKAGE_JSON = "./src/test/resources/nodejs/package.json"
//        const val FILE_NAME_PERSON1 = "/Users/luizsilva/Projects/Research/TechnicalContent/KotlinUtilities/KotlinTests/src/main/kotlin/generator/kotlin/Person.csv"
        const val PATH_DESTINATION_MODEL = "/Users/luizsilva/Projects/Research/TechnicalContent/KotlinUtilities/KotlinTests/src/main/kotlin/model"
        const val PATH_DESTINATION_KOTLIN = "/Users/luizsilva/Projects/Research/TechnicalContent/KotlinUtilities/KotlinTests/src/main/kotlin/generator/kotlin"
        const val PATH_DESTINATION_NODEJS = "/Users/luizsilva/Projects/Research/TechnicalContent/KotlinUtilities/KotlinTests/src/main/kotlin/generator/nodejs"
    }
}