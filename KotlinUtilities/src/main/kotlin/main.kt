import com.techware.utilities.Generate
import com.techware.utilities.GenerateKotlinImpl
import com.techware.utilities.GenerateNodeJSImpl
import com.techware.utilities.ObjectParser.Companion.parse
import com.techware.utilities.PropertiesFile
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.reflect.KClass

enum class Config {
    kotlinModel,
    descriptor,
    csvData,
    nodeJS
}

fun main(args: Array<String>) {
    require(args.size==1){"Need to give file name for the configuration"}
    val configFileName = args[0]
    if (!Files.exists(Paths.get(configFileName).toAbsolutePath())) {
        throw Exception("$configFileName does not exist")
    }

    generateAll(configFileName)
    parseCsvData(configFileName, Person::class)
    generateDescriptor(configFileName, Person::class)

    generateNodeJS(configFileName, "person")

}

/**
 * This is an example about how to generate a descriptor from a class
 */

fun<T:Any> generateDescriptor(configFileName: String, kClass: KClass<T>) {
    val properties =PropertiesFile.readPropertiesFile(configFileName)
    val targetDescriptor = properties[Config.descriptor.name]!!.toString()
    val generate: Generate = GenerateKotlinImpl()
    generate.generateDescriptor(kClass, targetDescriptor)
}

/**
 * This is an example about how to import csv file into an object
 */
fun<T:Any> parseCsvData(configFileName: String, kClass: KClass<T>) {
    val properties =PropertiesFile.readPropertiesFile(configFileName)
    val cvsData = properties[Config.csvData.name]!!.toString()
    val fileName = File(cvsData).name
    val listOfObj = parse(cvsData, kClass)
    println("*********************************")
    println("* processing file: $fileName")
    println(listOfObj)
    println("*********************************")
}

fun generateNodeJS(configFileName: String, fileName: String) {
    val generate: Generate = GenerateNodeJSImpl()
    val properties =PropertiesFile.readPropertiesFile(configFileName)
    val nodeJS = properties[Config.nodeJS.name]!!.toString()
    val targetDescriptor = properties[Config.descriptor.name]!!.toString()
    val className = fileName.capitalize()
    val descriptorFile = "$targetDescriptor/${className}.csv"
    generate.generateClass(fileName = descriptorFile, className = className, packageName = "model", targetFolder = nodeJS)


}

fun generateAll(configFileName: String) {
    val properties =PropertiesFile.readPropertiesFile(configFileName)
    val kotlinModel = properties[Config.kotlinModel.name]!!.toString()
    val targetDescriptor = properties[Config.descriptor.name]!!.toString()
    val nodeJS = properties[Config.nodeJS.name]!!.toString()
    File(targetDescriptor).walk().filter{it.name.contains(".csv")}.forEach {
        val className = it.nameWithoutExtension
        val descriptorFile = "$targetDescriptor/${it.name}"

        val generateKotlin: Generate = GenerateKotlinImpl()
        generateKotlin.generateClass(fileName = descriptorFile, className = className, packageName = "model", targetFolder = kotlinModel)

        val generateNodeJS: Generate = GenerateNodeJSImpl()
        val descriptorFileSource = "$targetDescriptor/${className.capitalize()}.csv"
        generateNodeJS.generateClass(fileName = descriptorFile, className = className, packageName = "model", targetFolder = nodeJS)

    }
}
