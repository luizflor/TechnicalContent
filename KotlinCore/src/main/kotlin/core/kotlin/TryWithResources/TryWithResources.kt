package core.kotlin
import java.io.IOException
import java.io.Reader
import java.io.Writer
import java.nio.file.Files
import java.nio.file.Paths

fun main(args: Array<String>) {
    //doTryCatchFinally()
//    doTryWithResources()
    doTryWithResourcesMulti()
}

fun doTryCatchFinally() {
    val buff = CharArray(8)
    var length: Int
    var reader: Reader? = null
    try {
        reader = Helper.openReader("file1.txt")
        length = reader!!.read(buff)
        while (length  >= 0) {
            println("\nlength: " + length)
            for (i in 0 until length)
                print(buff[i])
            length = reader!!.read(buff)
        }
    } catch (e: IOException) {
        println(e.javaClass.simpleName + " - " + e.message)
    } finally {
        try {
            if (reader != null)
                reader.close()
        } catch (e2: IOException) {
            println(e2.javaClass.simpleName + " - " + e2.message)
        }

    }
}

fun doTryWithResources() {
    val buff = CharArray(8)
    var length: Int
    try {
        Helper.openReader("file1.txt").use { reader ->
            length = reader.read(buff)
            while (length >= 0) {
                println("\nlength: " + length)
                for (i in 0 until length)
                    print(buff[i])
                length = reader.read(buff)
            }
        }
    } catch (e: IOException) {
        println(e.javaClass.simpleName + " - " + e.message)
    }

}

fun doTryWithResourcesMulti() {
    val buff = CharArray(8)
    var length: Int
    try {
        Helper.openReader("file1.txt").use { reader ->
            Helper.openWriter("file2.txt").use { writer ->
                length = reader.read(buff)
                while (length >= 0) {
                    println("\nlength: " + length)
                    writer.write(buff, 0, length)
                    length = reader.read(buff)
                }
            }
        }
    } catch (e: IOException) {
        println(e.javaClass.simpleName + " - " + e.message)
    }

}

object Helper {
    @Throws(IOException::class)
    fun openReader(fileName: String): Reader {
        return Files.newBufferedReader(Paths.get(fileName))
    }

    @Throws(IOException::class)
    fun openWriter(fileName: String): Writer {
        return Files.newBufferedWriter(Paths.get(fileName))
    }

}