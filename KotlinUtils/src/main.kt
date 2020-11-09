import java.lang.IllegalArgumentException
import java.lang.reflect.Constructor
import java.lang.reflect.Field
import java.lang.reflect.Method
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.javaType

data class Person(val id: Int, val firstName: String, val lastName: String){
    override fun toString(): String {
        return "Person(id=$id, firstName='$firstName', lastName='$lastName')"
    }
}


fun main(args: Array<String>) {
//    showFileLines("./resources/file1.csv")
    //reflectionPerson()
    val data = getListOfLines()
    //println(data)
    val listOfErrors = validate(data, Person::class)
    println(listOfErrors)
    val listOfObj = parse(data, Person::class)
    println(listOfObj)

}

/**
 * listOfErrors validate (listOfLines, class)
 * listOfObject parse (listOfLines, class)
 * listOfLines generateCsv(listOfObject, class)
 */

private fun getListOfLines(): ArrayList<String> {
    return arrayListOf<String>("id, firstName, lastName\n" ,
            "1, f1, l1\n",
            "2, f2, l2")
}

private fun showFileLines(filename: String) {
    if (!Files.exists(Paths.get(filename).toAbsolutePath())) return

    try {
        val data = Files.readAllLines(Paths.get(filename))
        data.forEach { println(it) }

    } catch (ex: Exception) {
        println(ex.javaClass.simpleName + " - " + ex.message)
    }

}

private fun reflectionPerson() {
    val fields = Person::class.java.declaredFields
    displayFields(fields, "fields")
    /**
    id : int
    firstName : class java.lang.String
    lastName : class java.lang.String
     */

//    val methods = Person::class.java.methods
//    displayMethods(methods,"methods")
//    val declMethods = Person::class.java.declaredMethods
//    displayMethods(declMethods,"declMethods")
//
//    val constructors = Person::class.java.constructors
//    displayConstructors(constructors,"constructors")
//
//    val p = construct(Person::class)
//    println(p)

    val fieldsStr = "id, firstName, lastName"
    val isValid = validateType(fieldsStr,Person::class)
    println(isValid)

}


fun displayFields(fields: Array<Field>, field: String) {
    println("\n*** $field ***")
    fields.forEach { println("${it.name} : ${it.type}") }
}

fun displayMethods(methods:Array<Method>, method: String){
    println("*** $method ***")
    methods.forEach {println("${it.name}") }
}

fun displayConstructors(constructors:Array<Constructor<*>>, constructor: String) {
    println("*** $constructor ***")
    constructors.forEach {println("${it.name}") }
}
//fun createInstance(className: String) {
//    val theClass = KClass.
//    val str = "1, f1, l1".split(",")
//    val obj = theClass.newInstance(1,"f1", "f2")
//
//}
fun <T : Any> construct(kClass: KClass<T>): T? {
    val ctor = kClass.primaryConstructor
    val str = "1, f1, l1".split(",")
    val values = listOf(1,"f1", "l1")
    return if (ctor != null)
        ctor.call(*values.toTypedArray()) else
        null
}

fun <T:Any> validateType(fields: String, kClass: KClass<T>): Boolean {
    val fieldsArr = fields.split(",")
    val fields = kClass.primaryConstructor?.parameters?.map { it.name to it.type }
    fieldsArr.forEach {field ->
        val found = fields?.find { it.first == field.trim()  } ?: return false
    }
    return true
}

//fun listOfErrors validate (listOfLines, class)
fun <T:Any> validate(listOfLine: ArrayList<String> , kClass: KClass<T> ): List<String> {
    val listOfErrors = mutableListOf<String>()
    val fieldsArr = listOfLine[0].split(",").map { it.trim() }
    val fields = kClass.primaryConstructor?.parameters?.map { it.name to it.type }
    if(fields == null){
        listOfErrors.add("No constructor for the class ${kClass.qualifiedName}")
    }
    fields?.forEach { field ->
        val found = fieldsArr.find { it == field.first } != null
        if(!found) {
            listOfErrors.add("${field.first} missing \r\n")
        }
    }
    return listOfErrors
}
//listOfObject parse (listOfLines, class)
fun <T:Any> parse(listOfLine: ArrayList<String> , kClass: KClass<T> ): MutableList<T> {
    val listOfObj = mutableListOf<T>()
    val fieldsArr = listOfLine[0].split(",").map { it.trim() }
    val fields = kClass.primaryConstructor?.parameters?.map { it.name to it.type }
    for (x in 1 until listOfLine.size - 1) {
        val data = listOfLine[x].split(",")
        if (data.size != fields?.size) {
            throw IllegalArgumentException("data elements size ${data.size} does not match of type size ${fields?.size}")
        }
        val values = mutableListOf<Any>()
        var i = 0
        fieldsArr.forEach { field ->
            val foundType = fields?.find { it.first == field }
            println(foundType?.second?.javaType?.typeName)
            //foundType?.second == Int
            when (foundType?.second?.javaType?.typeName) {
                "int" -> values += data[i].toInt()
                "java.lang.String" -> values += data[i]
            }
            ++i
        }
        val ctor = kClass.primaryConstructor
        val obj = ctor?.call(*values.toTypedArray())
        if(obj != null ){
            listOfObj.add(obj)
        }
    }
    return listOfObj
}


