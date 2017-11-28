package core.kotlin

import java.nio.file.Files
import java.nio.file.Paths


// kotlinc CommandLine.kt -include-runtime -d CommandLine.jar
// java -jar CommandLine.jar ../../../../file1.txt

// https://kotlinlang.org/docs/tutorials/command-line.html

fun main(args: Array<String>) {
    if (args.size == 0) {
        showUsage()
        return
    }

    val filename = args[0]
    if (!Files.exists(Paths.get(filename))) {
        println("\n File not found: " + filename)
        return
    }
    showFileLines(filename)
}
private fun showFileLines(filename: String) {
    println()

    try {
        Files.newBufferedReader(Paths.get(filename)).use { reader ->
            var line: String? = reader.readLine()
            while (line != null) {
                println(line)
                line = reader.readLine()
            }
        }
    } catch (ex: Exception) {
        println(ex.javaClass.simpleName + " - " + ex.message)
    }

}

private fun showUsage() {
    println()
    println("Please provide the filename to process on the command line")
}