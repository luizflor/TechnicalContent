import com.techware.utilities.ObjectParser.Companion.parse
import java.nio.file.Files
import java.nio.file.Paths

fun main(args: Array<String>) {
    require(args.size==1){"Need to give file name"}
    val fileName = args[0]
    if (!Files.exists(Paths.get(fileName).toAbsolutePath())) {
        throw Exception("$fileName does not exist")
    }
    val listOfObj = parse(fileName, Person::class)
    println("*********************************")
    println("* processing file: $fileName")
    println(listOfObj)
    println("*********************************")
}
