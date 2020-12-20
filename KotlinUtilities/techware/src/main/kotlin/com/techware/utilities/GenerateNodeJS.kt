package com.techware.utilities

import java.io.File
import java.lang.StringBuilder

class GenerateNodeJS {
    companion object {
        const val PACKAGE_JSON = "./src/main/resources/nodejs/package.json"
        const val NODEJS = "./src/main/resources/nodejs"

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
                        "date" -> "faker.date.past().substring(0,10)"
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

        fun generateIndex(classNames: List<String>): String {
            val sb = StringBuilder()
            sb.appendln("module.exports = function () {")
            classNames.map {
                sb.appendln("var ${it} = require(\"../nodejs/${it.capitalize()}/${it.capitalize()}\");")
            }
            sb.appendln("        return {")
            classNames.map {
                sb.appendln("            ${it}: ${it}().${it.capitalize()},")
            }
            sb.appendln(
                "        }\n" +
                        "}"
            )
            return sb.toString()
        }

        fun generateUnixJson(): String {
            val sb = StringBuilder()
            sb.appendln("#!/usr/bin/env bash\n" +
                    "json-server --port 3001 index.js")
            return sb.toString()
        }

        fun generateWindowsJson(): String {
            val sb = StringBuilder()
            sb.appendln("json-server --port 3001 index.js")
            return sb.toString()
        }
        fun generateIndexJson(classNames: List<String>, targetFolder: String) {
            val indexStr = generateIndex(classNames)
            Descriptor.saveToFile(
                text = indexStr,
                className = "index",
                targetFolder = "$targetFolder",
                extentionName = ".js"
            )

            val unixJson = generateUnixJson()
            Descriptor.saveToFile(
                text = unixJson,
                className = "json",
                targetFolder = "$targetFolder",
                extentionName = ".sh"
            )

            val windowsJson = generateUnixJson()
            Descriptor.saveToFile(
                text = windowsJson,
                className = "json",
                targetFolder = "$targetFolder",
                extentionName = ".bat"
            )


        }
    }
}