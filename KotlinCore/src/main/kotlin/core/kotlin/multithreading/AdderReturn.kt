package core.kotlin.multithreading

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.util.concurrent.Callable

class AdderReturn(private val inFile: String):  Callable<Int> {
    override fun call(): Int {
        return doAdd()
    }

//    override fun run() {
//       try {
//           doAdd()
//       }
//       catch (e:Exception){
//           e.printStackTrace()
//       }
//    }

    @Throws(IOException::class, IllegalArgumentException::class)
    fun doAdd(): Int {
        var total = 0
        if (!Files.exists(Paths.get(inFile)))
            throw IllegalArgumentException("Invalid file name: " + inFile)

        Files.newBufferedReader(Paths.get(inFile)).use { reader ->
            var line = reader.readLine()
            while (line != null) {
                total += Integer.parseInt(line)
                line = reader.readLine()
            }
            return total
        }
    }
}