import com.techware.generate.*
import com.techware.generate.ObjectParser.Companion.parse
import com.techware.utilities.PropertiesFile
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.reflect.KClass

enum class Config {
    kotlinModel,
    targetDescriptor,
    csvData,
    descriptors,
    targetNodeJS,
    targetReactJS,
    targetSpringAPI,
}

fun main(args: Array<String>) {
    require(args.size==1){"Need to give file name for the configuration"}
    val configFileName = args[0]
    if (!Files.exists(Paths.get(configFileName).toAbsolutePath())) {
        throw Exception("$configFileName does not exist")
    }

    generateKotlin(configFileName)
    parseCsvData(configFileName, Person::class)
    generateDescriptor(configFileName, Person::class)

    //generateNodeJS(configFileName, "person")
    generateReactJS(configFileName)
    generateNodeJS(configFileName)
    generateSpringAPI(configFileName)

}

/**
 * This is an example about how to generate a descriptor from a class
 * and creates a file with this sample content
 * # fieldName, field Type, size, format, label
 *   id, int
 *   firstname, string
 *   lastname, string
 */
fun<T:Any> generateDescriptor(configFileName: String, kClass: KClass<T>) {
    val properties =PropertiesFile.readPropertiesFile(configFileName)
    val targetDescriptor = properties[Config.targetDescriptor.name]!!.toString()
    val generate: Generate = GenerateKotlinImpl()
    generate.generateDescriptor(kClass, targetDescriptor)
    println("descriptor files created at $targetDescriptor")
}

/**
 * This is an example about how to import csv file into an object
 * this is an example of csvData
 *  id, firstName, lastName
 *  1, f1, l1
 *  2, f2, l2
 */
fun<T:Any> parseCsvData(configFileName: String, kClass: KClass<T>) {
    val properties =PropertiesFile.readPropertiesFile(configFileName)
    val csvData = properties[Config.csvData.name]!!.toString()
    val fileName = File(csvData).name
    val listOfObj = parse(csvData, kClass)
    println("*********************************")
    println("* processing file: $fileName")
    println(listOfObj)
    println("*********************************")
}

//fun generateNodeJS(configFileName: String, fileName: String) {
//    val generate: Generate = GenerateNodeJSImpl()
//    val properties =PropertiesFile.readPropertiesFile(configFileName)
//    val nodeJS = properties[Config.targetNodeJS.name]!!.toString()
//    val targetDescriptor = properties[Config.targetDescriptor.name]!!.toString()
//    val className = fileName.capitalize()
//    val descriptorFile = "$targetDescriptor/${className}.csv"
//    generate.generateClass(fileName = descriptorFile, className = className, packageName = "model", targetFolder = nodeJS)
//
//    println("nodejs files created at $nodeJS")
//
//}

fun generateKotlin(configFileName: String) {
    val properties =PropertiesFile.readPropertiesFile(configFileName)
    val kotlinModel = properties[Config.kotlinModel.name]!!.toString()
    val targetDescriptor = properties[Config.targetDescriptor.name]!!.toString()
//    val nodeJS = properties[Config.nodeJS.name]!!.toString()
    File(targetDescriptor).walk().filter{it.name.contains(".csv")}.forEach {
        val className = it.nameWithoutExtension
        val descriptorFile = "$targetDescriptor/${it.name}"

        val generateKotlin: Generate = GenerateKotlinImpl()
        generateKotlin.generateClass(fileName = descriptorFile, className = className, packageName = "model", targetFolder = kotlinModel)

//        val generateNodeJS: Generate = GenerateNodeJSImpl()
//        val descriptorFileSource = "$targetDescriptor/${className.capitalize()}.csv"
//        generateNodeJS.generateClass(fileName = descriptorFile, className = className, packageName = "model", targetFolder = nodeJS)

    }
    println("kotlin files created at $kotlinModel")
}

fun generateReactJS(configFileName: String){
    val properties =PropertiesFile.readPropertiesFile(configFileName)
    val descriptors = properties[Config.descriptors.name]!!.toString()
    val reactJS = properties[Config.targetReactJS.name]!!.toString()
    var generate: GenerateSolution = GenerateReactJS()
    generate.generateFiles(targetDescriptor = descriptors, targetFolder = reactJS)
    println("react files created at $reactJS")
}

fun generateNodeJS(configFileName: String){
    val properties =PropertiesFile.readPropertiesFile(configFileName)
    val descriptors = properties[Config.descriptors.name]!!.toString()
    val nodeJS = properties[Config.targetNodeJS.name]!!.toString()
    var generate: GenerateSolution = GenerateNodeJS()
    generate.generateFiles(targetDescriptor = descriptors, targetFolder = nodeJS)
    println("nodejs files created at $nodeJS")
}

fun generateSpringAPI(configFileName: String){
    val properties =PropertiesFile.readPropertiesFile(configFileName)
    val descriptors = properties[Config.descriptors.name]!!.toString()
    val targetSpringAPI = properties[Config.targetSpringAPI.name]!!.toString()
    var generate: GenerateSolution = GenerateSpringApi()
    generate.generateFiles(targetDescriptor = descriptors, targetFolder = targetSpringAPI)
    println("spring files files created at $targetSpringAPI")
}
