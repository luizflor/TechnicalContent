package com.techware.utilities

//import org.junit.jupiter.api.Assertions
import java.nio.file.Files
import java.nio.file.Paths
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.full.primaryConstructor

/**
 * Returns Map value par fieldName=index position
 * {A=0, B=1, C=2}
 */
fun String.toMap(): Map<String, Int> {
    return this.split(",")
        .asSequence()
        .filter { it.isNotBlank() }
        .map{it.trim().toUpperCase()}.toSet()
        .mapIndexed { index, s -> s to index }.toSet()
        .toMap()
}

/**
 * Returns Map value par fieldName=fieldType
 * {ID=kotlin.Int, FIRSTNAME=kotlin.String, LASTNAME=kotlin.String}
 */
fun<T:Any> KClass<T>.toMap(): Map<String, KType> {
    return this.primaryConstructor?.parameters?.map { it.name!!.toUpperCase() to it.type }!!.toMap()
}
/**
 * Returns a list of fields that are missing
 */
fun validateClass(
    c1: Map<String, KType>,
    f1: Map<String, Int>
): MutableList<String> {
    require(c1.size == f1.size) {"class fieds and list of fields needs to be the same size"}
    val errors = mutableListOf<String>()
    c1.forEach {
        if (!f1.containsKey(it.key)) {
            errors.add(it.key)
        }
    }
    return errors
}

val formatter = DateTimeFormatter.ofPattern("M/d/yyyy")
// parse string to instant

fun String.fromDateToInstant(): Instant {
    val localDate = LocalDate.parse(this, formatter)
    return localDate.atStartOfDay(ZoneId.of(ZoneOffset.UTC.id)).toInstant()!!
}

fun String.fromDateTimeToInstant(): Instant {
    val formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME
//        .withLocale(Locale.US)
        .withZone(ZoneOffset.UTC)
    val localDate = LocalDate.parse(this, formatter)
    return localDate.atStartOfDay(ZoneId.of(ZoneOffset.UTC.id)).toInstant()!!
}

// convert instant to date
fun Instant.toDate(): LocalDate {
    return this.atOffset(ZoneOffset.UTC).toLocalDate()
}

fun LocalDate.toStringDate(): String {
    return this.format(formatter)!!
}
// format instant to string date
fun Instant.toStringDate(): String {
    return formatter.format(this.toDate())!!
}
// format instant to string datetime
fun Instant.toStringDateTime(): String {
    val formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME
//        .withLocale(Locale.US)
        .withZone(ZoneOffset.UTC)
    return formatter.format(this)!!
}

class ObjectParser {
    companion object {

        /**
         * Return a list of objects
         */
        fun cvsToObj(c1: Map<String, KType>, cvsData: List<String>): MutableList<Map<Int, MutableList<Any>>> {
            require(cvsData.isNotEmpty()){ "List of objects are required. Parameter cvsData" }
            // get all the fields from the header
            val fields = cvsData.first().toMap()

            val errors = validateClass(c1 = c1, f1 = fields)
            require(errors.isEmpty()) {"Invalid fields $errors"}

            // skip the first row for the header to get all data
            val allData = cvsData.drop(1)

            val allObj = mutableListOf<Map<Int, MutableList<Any>>>()

            // convert each field for an object with its type
            allData.forEachIndexed { index,line ->
                val data = line.split(",").map { it.trim() }
                //  println(data)
                val listOfObj = mutableListOf<Any>()
                fields.forEach { f ->
                    val field = f.key
                    val position = f.value
                    val type = c1[field].toString()
                    val value = data[position]

                    // println("field: $field, position: $position type: $type value: $value")
                    listOfObj.add(convertStringToObject(type, value))
                }
                allObj.add(mapOf(index to listOfObj))
            }
            return allObj
        }


        /**
         * Convert string to object
         */
        fun convertStringToObject(
            type: String,
            value: String
        ): Any {
            return when (type) {
                "kotlin.Int" ->  value.toInt()
                "kotlin.Int" ->  value.toInt()
                "kotlin.Short" ->  value.toShort()
                "kotlin.Byte" ->  value.toByte()
                "kotlin.Boolean" ->  value.toBoolean()
                "kotlin.Float" ->  value.toFloat()
                "kotlin.Double" ->  value.toDouble()
                "java.time.Instant" ->  value.fromDateTimeToInstant()!!
                "java.time.LocalDate" ->  value.fromDateToInstant()!!.toDate()
                "kotlin.String" ->  value
                else -> value
            }
        }

        /**
         * Create an object
         */
        private fun <T:Any> createObject(kClass: KClass<T>, values: MutableList<Any>): T? {
            val ctor = kClass.primaryConstructor
            return ctor?.call(*values.toTypedArray())
        }

        /**
         * Parse an array cvsData with the first line contains the name of fields
         * that matches with the class
         * it return the list of objects parsed
         */
        fun <T:Any> parse(cvsData: MutableList<String> , kClass: KClass<T>): MutableList<T?> {
            val listOfObj = mutableListOf<T?>()
            val strTypeMap = kClass.toMap()

            val allObj = cvsToObj(strTypeMap, cvsData)
            allObj.forEach {
                val entries = it.values.first()
                val o = createObject(kClass, entries)
                listOfObj.add(o)
            }
            return listOfObj
        }

        /**
         * Parse from a file
         */
        fun <T:Any> parse(fileName: String, kClass: KClass<T>): MutableList<T?> {
            val cvsData = getCvsFile(fileName)
            return parse(cvsData,  kClass)
        }

        /**
         * Get a cvs file
         */
        fun getCvsFile(fileName: String): MutableList<String> {
            var cvsData = mutableListOf<String>()
            if (!Files.exists(Paths.get(fileName).toAbsolutePath())) return cvsData
            cvsData = Files.readAllLines(Paths.get(fileName))
            return cvsData
        }
    }
}