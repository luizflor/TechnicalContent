package com.techware.nodejs


import com.techware.react.GenerateReactJS
import com.techware.generate.Descriptor
import com.techware.generate.Type
import java.io.File
import java.lang.StringBuilder

class GenerateNodeJS {
    companion object {
        const val PACKAGE_JSON = "./src/main/resources/nodejs/package.json"
        const val NODEJS = "./src/main/resources/nodejs"

        /**
         * This entrypoint to generate all nodejs files
         * it requires two parameters
         * targetDescriptor: this is the directory containing files with .txt extension
         *      an example of file is person.txt that contains the following
         *          id, int
         *          firstname, string
         *          lastname, string
         *
         * targetFolder: this is the directory target that will be written with all files
         *
         * After running this method perform the following tasks
         *      npm install
         *          this will pull all the node dependencies
         *      json.sh or json.bat
         *          this will execute the api the url  http://localhost:3001
         */

        fun generateNodeJSFiles(targetDescriptor: String, targetFolder: String) {
            GenerateReactJS.copyFiles(targetFolder = targetFolder)
            val fileList = File(targetDescriptor).walk().filter{it.name.contains(".txt")}
            val classNames = fileList.map { it.nameWithoutExtension }.toList()
            GenerateNodeJSIndex.generateIndexJson(classNames=classNames, targetFolder = targetFolder)

            fileList.forEach {
                val fileName = it.name
                val className = it.nameWithoutExtension
                val descriptorFile = "$targetDescriptor/${fileName}"

                val descriptorList = Descriptor.getDescriptorFromFile(descriptorFile)
                require(descriptorList.isNotEmpty()) { "descriptorList is empty" }

                val fakeApi = generateFakeNodeJSapi(descriptorList = descriptorList, className = className)
                Descriptor.saveToFile(
                    text = fakeApi,
                    className = className,
                    targetFolder = "$targetFolder/$className",
                    extentionName = ".js"
                )

                val fakeCsv = generateFakeNodeJScsv(descriptorList = descriptorList, className = className)
                Descriptor.saveToFile(
                    text = fakeCsv,
                    className = "${className}_csv",
                    targetFolder = "$targetFolder/$className",
                    extentionName = ".js"
                )

                generateBatchFiles(className = className, targetFolder = targetFolder)

                copyPackageJson(targetFolder)
            }
        }

        fun generateFakeNodeJS(fileName: String, className: String, packageName: String, targetFolder: String) {
            val descriptorList = Descriptor.getDescriptorFromFile(fileName)
            require(descriptorList.isNotEmpty()) { "descriptorList is empty" }

            val fakeApi = generateFakeNodeJSapi(descriptorList = descriptorList, className = className)
            Descriptor.saveToFile(
                text = fakeApi,
                className = className,
                targetFolder = "$targetFolder/$className",
                extentionName = ".js"
            )

            val fakeCsv = generateFakeNodeJScsv(descriptorList = descriptorList, className = className)
            Descriptor.saveToFile(
                text = fakeCsv,
                className = "${className}_csv",
                targetFolder = "$targetFolder/$className",
                extentionName = ".js"
            )

            generateBatchFiles(className = className, targetFolder = targetFolder)

            copyPackageJson(targetFolder)

        }

        fun generateFakeNodeJSapi(descriptorList: List<Type>, className: String): String {
            val sb = StringBuilder()

            sb.appendln(
                "module.exports = function(){\n" +
                        "    var faker = require(\"faker\");\n" +
                        "    var _ = require(\"lodash\");\n" +
                        "    return {"
            )
            sb.appendln("\t$className: _.times(100, function(n){")
            sb.appendln("\t\treturn{")
            descriptorList.forEach {
                sb.append("\t\t\t${it.fieldName}: ")
                if (it.fieldName == "id") {
                    sb.appendln("n,")
                } else {
                    val fake = when (it.fieldType) {
                        "int" -> "faker.random.number()"
                        "boolean" -> "faker.random.boolean()"
                        "string" -> "faker.lorem.word()"
                        "instant" -> "faker.date.past().toISOString()"
                        "date" -> "faker.date.past().toISOString().substring(0,10)"
                        "double" -> "faker.random.number()/100"
                        "float" -> "faker.random.number()/100"
                        else -> "faker.lorem.word()"
                    }
                    sb.appendln("$fake,")
                }
            }
            sb.appendln(
                "            }\n" +
                        "        })\n" +
                        "    }\n" +
                        "}"
            )
            return sb.toString()
        }

        fun generateFakeNodeJScsv(descriptorList: List<Type>, className: String): String {
            val sb = StringBuilder()
            sb.appendln(
                "var $className = require(\"./$className\");\n" +
                        "var fs = require('fs');\n" +
                        "const path = require('path');"
            )
            sb.append("var data = `")
            descriptorList.forEach {
                sb.append("${it.fieldName}, ")
            }
            sb[sb.lastIndex - 1] = '\\'
            sb[sb.lastIndex] = 'n'
            sb.appendln("`")

            sb.appendln("$className().$className.forEach(e => {")
            sb.append("\tdata = data + `")
            descriptorList.forEach {
                sb.append("\${e.${it.fieldName}}, ")
            }
            sb[sb.lastIndex - 1] = '\\'
            sb[sb.lastIndex] = 'n'
            sb.appendln("`")
            sb.appendln(
                "});\n" +
                        "var dirData = './data';\n" +
                        "if (!fs.existsSync(dirData)) {\n" +
                        "    fs.mkdirSync(dirData);\n" +
                        "}\n" +
                        "var fileName = __dirname+\"/data/$className.csv\";\n" +
                        "fs.writeFile(fileName,data, function(err) {\n" +
                        "    if(err) return console.log(err);\n" +
                        "});"
            )
            return sb.toString()
        }

        fun generateBatchFiles(className: String, targetFolder: String) {
            val windowsCsv = generateWindowsBatchCsv(className)
            Descriptor.saveToFile(
                text = windowsCsv,
                className = "csv",
                targetFolder = "$targetFolder/$className",
                extentionName = ".bat"
            )

            val unixCsv = generateUnixBatchCsv(className)
            Descriptor.saveToFile(
                text = unixCsv,
                className = "csv",
                targetFolder = "$targetFolder/$className",
                extentionName = ".sh"
            )

            val windowsJson = generateWindowsBatchJson(className)
            Descriptor.saveToFile(
                text = windowsJson,
                className = "json",
                targetFolder = "$targetFolder/$className",
                extentionName = ".bat"
            )

            val unixJson = generateUnixBatchJson(className)
            Descriptor.saveToFile(
                text = unixJson,
                className = "json",
                targetFolder = "$targetFolder/$className",
                extentionName = ".sh"
            )

        }

        fun generateWindowsBatchCsv(className: String): String {
            return "node ${className}_csv.js"
        }

        fun generateUnixBatchCsv(className: String): String {
            return "#!/usr/bin/env bash\n" +
                    "node ${className}_csv.js"
        }

        fun generateWindowsBatchJson(className: String): String {
            return "json-server --port 3001 ${className}.js"
        }

        fun generateUnixBatchJson(className: String): String {
            return "#!/usr/bin/env bash\n" +
                    "json-server --port 3001 ${className}.js"
        }

        fun copyPackageJson(targetFolder: String) {
            File(PACKAGE_JSON).copyTo(File("$targetFolder/package.json"), true)
        }
    }
}