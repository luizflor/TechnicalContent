package core.kotlin.multithreading

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

class Adder(private val inFile: String, private val outFile: String) {
    @Throws(IOException::class, IllegalArgumentException::class)
    fun doAdd() {
        var total = 0
        if (!Files.exists(Paths.get(inFile)))
            throw IllegalArgumentException("Invalid file name: " + inFile)

        Files.newBufferedReader(Paths.get(inFile)).use { reader ->
            var line = reader.readLine()
            while (line != null) {
                total += Integer.parseInt(line)
                line = reader.readLine()
            }
            Files.newBufferedWriter(Paths.get(outFile)).use { writer ->
                writer.write("Total: " + total)
            }
        }
    }
}