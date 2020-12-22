package com.techware.generate

import java.io.File
import kotlin.reflect.KClass

data class Type(val fieldName: String, val fieldType: String)

class Descriptor {
    companion object {
        val types = mapOf(
            "kotlin.Int" to "int",
            "kotlin.String" to "string",
            "kotlin.Long" to "long",
            "kotlin.Short" to "short",
            "kotlin.Byte" to "byte",
            "kotlin.Boolean" to "boolean",
            "kotlin.Float" to "float",
            "kotlin.Double" to "double",
            "java.time.Instant" to "instant",
            "java.time.LocalDate" to "date"
        )

        /**
         * Generate class based on descriptor
         * for example
         *
            data class Todo(val id: Int, val description: String) {
                override fun toString(): String {
                return "Todo(id=$id, description='$description')"
                }
            }
         */
        fun convertDescriptorToTypeList(descriptorStr: String): List<Type> {
            val typeList = descriptorStr
                .split("\n")
                .map { it.split(",") }
                .filter{it.size> 1}
                .map { Type(it[0], it[1].trim()) }
            return typeList
        }

        fun convertDescriptorToTypeList(descriptor: List<String>): List<Type> {
            val typeList = descriptor
                .filter { !it.startsWith("#") }
                .map { it.split(",") }
                .filter{it.size> 1}
                .map { Type(it[0], it[1].trim()) }
            return typeList
        }

        fun generateClassFromString(typeList: List<Type>, className: String): String {
            val sb = java.lang.StringBuilder()

            val containsDate = typeList.find { it.fieldType.contains("date") }
            val containsInstant = typeList.find{it.fieldType.contains("instant")}
            if(containsDate !=null) {
                sb.appendln("import java.util.*")
            }
            if(containsInstant!=null) {
                sb.appendln("import java.time.Instant")
            }

            sb.appendln("data class $className(")
            typeList.forEach {
                sb.append("val ${it.fieldName}: ${it.fieldType.capitalize()}, ")
            }
            sb[sb.lastIndex-1] = ')'
            sb.appendln("{")
            sb.appendln("\toverride fun toString(): String {")
            sb.append("\t\treturn \"$className(")
            typeList.forEach {
                sb.append("${it.fieldName}=\$${it.fieldName}, ")
            }
            sb[sb.lastIndex-1] = ')'
            sb[sb.lastIndex] = '\"'
//        sb.appendln("\"")
            sb.appendln("\n\t}")
            sb.appendln("}")
            return sb.toString()
        }


        fun generateClassFromString(descriptorStr: String, className: String): String {
            val typeList2 =
                convertDescriptorToTypeList(descriptorStr)

            return generateClassFromString(
                typeList2,
                className
            )
        }

        fun generateClassFromList(descriptor: List<String>, className: String): String {
            val typeList2 =
                convertDescriptorToTypeList(descriptor)

            return generateClassFromString(
                typeList2,
                className
            )
        }

        fun generateClass(fileName:String, className: String, packageName: String): String {
           val cvsData = ObjectParser.getCvsFile(fileName)
            require(cvsData.isNotEmpty()){"invalid file: $fileName"}

            return generateClassFromList(
                descriptor = cvsData,
                className = className
            )
        }

        /**
         * Read descriptor file into List<Type>
         */
        fun getDescriptorFromFile(fileName: String): List<Type> {
            val cvsData = ObjectParser.getCvsFile(fileName)
            require(cvsData.isNotEmpty()){"invalid file: $fileName"}
            return convertDescriptorToTypeList(cvsData)
        }

        fun saveToFile(text:String, className: String, targetFolder: String, extentionName: String) {
            val folder = File(targetFolder)
            folder.mkdirs()
            val fileName = "$targetFolder/$className$extentionName"
            val file = File(fileName)
            file.writeText(text)
        }

        fun generateClass(fileName:String, className: String, packageName: String, targetFolder: String) {
            val generatedClass = generateClass(
                fileName,
                className,
                packageName
            )

            saveToFile(
                text = generatedClass,
                className = className,
                targetFolder = targetFolder,
                extentionName = ".kt"
            )

//            val folder = File(targetFolder)
//            folder.mkdir()
//            val fileName = "$targetFolder/$className.kt"
//            val file = File(fileName)
//            file.writeText(generatedClass)
        }

        /**
         *
        let takes the object it is invoked upon as the parameter and returns the result of the lambda expression.
        Kotlin let is a scoping function wherein the variables declared inside the expression cannot be used outside.
         */
        fun<T:Any> generateTypeFromClass(kClass: KClass<T>): MutableList<Type> {
            val c1 = kClass.toMap()
            val typeList = mutableListOf<Type>()
            c1.forEach { t, u ->
                val type = types[u.toString()]
                type?.let { Type(t.toLowerCase(), it.toLowerCase()) }?.let { typeList.add(it) }
            }
            return typeList
        }

        /**
         * Returns a string in the format
        id, int
        fn, string
        ln, string
         */
        fun<T:Any> generateDescriptor(kClass: KClass<T>): String {
            val c1= generateTypeFromClass(kClass)
            var sb = StringBuilder()
            c1.forEach { sb.appendln("${it.fieldName}, ${it.fieldType}") }
            return sb.toString()
        }

        fun<T:Any> generateDescriptor(kClass: KClass<T>, targetFolder: String) {
            val descriptor = generateDescriptor(kClass)

            val folder = File(targetFolder)
            folder.mkdirs()
            val fileName = "$targetFolder/${kClass.simpleName!!}.csv"
            //val fileName = "$targetFolder/$className.kt"
            val file = File(fileName)
            file.writeText("# fieldName, field Type, size, format, label\n")
            file.appendText(descriptor)
        }
    }
}