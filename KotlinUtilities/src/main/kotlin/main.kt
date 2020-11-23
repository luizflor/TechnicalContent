import com.techware.utilities.Generate
import com.techware.utilities.GenerateKotlinImpl
import com.techware.utilities.ObjectParser.Companion.parse
import com.techware.utilities.PropertiesFile
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

enum class Config {
    kotlinModel,
    descriptor,
    csvData
}

fun main(args: Array<String>) {
    require(args.size==1){"Need to give file name for the configuration"}
    val configFileName = args[0]
    if (!Files.exists(Paths.get(configFileName).toAbsolutePath())) {
        throw Exception("$configFileName does not exist")
    }

    generateAll(configFileName)
    parseCsvData(configFileName)

    // TODO
    // GenerateDescriptor


}

fun parseCsvData(configFileName: String) {
    val properties =PropertiesFile.readPropertiesFile(configFileName)
    val cvsData = properties[Config.csvData.name]!!.toString()
    val fileName = File(cvsData).name
    val listOfObj = parse(cvsData, Person::class)
    println("*********************************")
    println("* processing file: $fileName")
    println(listOfObj)
    println("*********************************")
}

fun generateAll(configFileName: String) {
    val properties =PropertiesFile.readPropertiesFile(configFileName)
    val kotlinModel = properties[Config.kotlinModel.name]!!.toString()
    val targetDescriptor = properties[Config.descriptor.name]!!.toString()
    File(targetDescriptor).walk().filter{it.name.contains(".csv")}.forEach {
        val className = it.nameWithoutExtension
        val descriptorFile = "$targetDescriptor/${it.name}"

        val generate: Generate = GenerateKotlinImpl()
        generate.generateClass(fileName = descriptorFile, className = className, packageName = "model", targetFolder = kotlinModel)
    }
}
