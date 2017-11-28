package core.kotlin

import java.io.IOException
import java.net.URI
import java.net.URISyntaxException
import java.nio.charset.Charset
import java.nio.file.*
import java.util.*

fun main(args: Array<String>) {
    val data = arrayOf("Line 1", "Line 2 2", "Line 3 3 3", "Line 4 4 4 4", "Line 5 5 5 5 5")

    try {
        openZip(Paths.get("myData.zip")).use { zipFs ->
            copyToZip(zipFs)
            writeToFileInZip1(zipFs, data)
            writeToFileInZip2(zipFs, data)
        }
    } catch (e: Exception) {
        println(e.javaClass.simpleName + " - " + e.message)
    }

}

@Throws(IOException::class, URISyntaxException::class)
private fun openZip(zipPath: Path): FileSystem {
    val providerProps = HashMap<String, String>()
    providerProps.put("create", "true")

    val zipUri = URI("jar:file", zipPath.toUri().path, null)

    return FileSystems.newFileSystem(zipUri, providerProps)
}

@Throws(IOException::class)
private fun copyToZip(zipFs: FileSystem) {
    val sourceFile = Paths.get("file1.txt")
    //Path sourceFile = FileSystems.getDefault().getPath("file1.txt");
    val destFile = zipFs.getPath("/file1Copied.txt")

    Files.copy(sourceFile, destFile, StandardCopyOption.REPLACE_EXISTING)
}

@Throws(IOException::class)
private fun writeToFileInZip1(zipFs: FileSystem, data: Array<String>) {
    Files.newBufferedWriter(zipFs.getPath("/newFile1.txt")).use { writer ->
        for (d in data) {
            writer.write(d)
            writer.newLine()
        }
    }
}

@Throws(IOException::class)
private fun writeToFileInZip2(zipFs: FileSystem, data: Array<String>) {
    Files.write(zipFs.getPath("/newFile2.txt"), Arrays.asList(*data),
            Charset.defaultCharset(), StandardOpenOption.CREATE)
}