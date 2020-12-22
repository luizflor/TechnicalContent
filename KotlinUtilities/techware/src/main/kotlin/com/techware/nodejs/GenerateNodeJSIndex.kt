package com.techware.nodejs

import com.techware.generate.Descriptor
import java.lang.StringBuilder

class GenerateNodeJSIndex {
    companion object {
        fun generateIndex(classNames: List<String>): String {
            val sb = StringBuilder()
            sb.appendln("module.exports = function () {")
            classNames.map {
                sb.appendln("var ${it} = require(\"../nodejs/${it.capitalize()}/${it.capitalize()}\");")
            }
            sb.appendln("        return {")
            classNames.map {
                sb.appendln("            ${it}: ${it}().${it},")
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

            val windowsJson = generateWindowsJson()
            Descriptor.saveToFile(
                text = windowsJson,
                className = "json",
                targetFolder = "$targetFolder",
                extentionName = ".bat"
            )
        }
    }
}