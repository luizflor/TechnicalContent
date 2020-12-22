import com.techware.generate.*
import com.techware.generate.ObjectParser.Companion.convertStringToObject
import com.techware.generate.ObjectParser.Companion.cvsToObj
import com.techware.generate.ObjectParser.Companion.getCvsFile
import com.techware.generate.ObjectParser.Companion.parse
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

//data class Person2(val id: Int, val firstName: String, val lastName: String, val birthDate: LocalDate, val isModified: Boolean, val timestamp: Instant, val salary: Double){
//    override fun toString(): String {
//        return "Person2(id=$id, firstName='$firstName', lastName='$lastName', birthDate=$birthDate, isModified=$isModified, timestamp=$timestamp, salary=$salary)"
//    }
//}

internal class ObjectParserTest {

    @BeforeEach
    fun setUp() {

    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun createPerson() {
        val person = Person(1, "f1", "l1")
        assertNotEquals(person, null)
    }

    @Test
    fun createPerson2() {
        val person = Person2(1,"f1","l1","02/29/2020".fromDateToInstant().toDate(),true,"2020-02-29T00:00:00Z".fromDateTimeToInstant(),1.23)
        assertNotEquals(person, null)
    }

    @Test
    fun stringToMap() {
        val h1 = " A , b , C  ".toMap()
        assertEquals(3, h1.size)
        assertEquals(0,h1["A"])
        assertEquals(1,h1["B"])
        assertEquals(2,h1["C"])
        println(h1)

        // empty string
        val h2 = "".toMap()
        assertEquals(0, h2.size)

        // deal duplicates
        val h3 = " A , b , C, A , B  ".toMap()
        println(h3)
        assertEquals(3, h3.size)
        assertEquals(true,h3.all { it.value <= h3.size },"size is ${h3.size} - $h3")
    }

    @Test
    fun classToMap() {
        val m1 = Person::class.toMap()
        println(m1)
    }

    @Test
    fun validateFieldsAgainstClass() {
        //happy path
        val c1 = Person::class.toMap()
        val f1 =" id , firstName , lastName".toMap()

        val errors = validateClass(c1, f1)
        println(errors)
        assertEquals(0, errors.size)

        // one field invalid
        val f2 =" id , firstNameX , lastName".toMap()
        val errors2 = validateClass(c1, f2)
        println(errors2)
        assertEquals(1, errors2.size)
    }

    @Test
    fun validateExtraFieldsAgainstClass() {
        // one extra field
        val c1 = Person::class.toMap()
        val f3 =" id , firstName , lastName, MI".toMap()
        assertThrows(
            java.lang.IllegalArgumentException::class.java
        ) {

            val errors3 = validateClass(c1, f3)
            println(errors3)
            assertEquals(0, errors3.size)
        }
    }
    // csvToObject
    @Test
    fun cvsToObjectTest() {
        val c1 = Person::class.toMap()
        val cvsData = arrayListOf(
            "id, firstName, lastName\n",
            "1, f1, l1\n",
            "2, f2, l2"
        )

        val result = cvsToObj(c1, cvsData)
        println(result)

        // invert the order of the fields
        val cvsData2 = arrayListOf(
            "firstName, id, lastName\n",
            "f1, 1, l1\n",
            "f2, 2, l2"
        )

        val result2 = cvsToObj(c1, cvsData2)
        println(result2)
    }

    @Test
    fun buildObjTest() {
        val c1 = Person::class.toMap()
        val cvsData = arrayListOf(
            "id, firstName, lastName\n",
            "1, f1, l1\n",
            "2, f2, l2"
        )
        val result = cvsToObj(c1, cvsData)
        assertEquals(2,result.size)
    }

    @Test
    fun convertStringToObjectTest() {
        // Test for integer
        val isInt = convertStringToObject("kotlin.Int", "1")
        assertTrue(isInt is Int)
        assertEquals(1,isInt)

        // Test for String
        val isString = convertStringToObject("kotlin.String", "A")
        assertTrue(isString is String)
        assertEquals("A",isString)

        // Test for Short
        val isShort = convertStringToObject("kotlin.Short", "1")
        assertTrue(isShort is Short)
        assertEquals(1.toShort(),isShort)

        // Test for Byte
        val isByte = convertStringToObject("kotlin.Byte", "1")
        assertTrue(isByte is Byte)
        assertEquals(1.toByte(),isByte)

        // Test for Boolean
        val isBoolean = convertStringToObject("kotlin.Boolean", "true")
        assertTrue(isBoolean is Boolean)
        assertEquals(true,isBoolean)

        // Test for Float
        val isFloat = convertStringToObject("kotlin.Float", "1.23")
        assertTrue(isFloat is Float)
        assertEquals(1.23.toFloat(),isFloat)

        // Test for Double
        val isDouble = convertStringToObject("kotlin.Double", "1.23")
        assertTrue(isDouble is Double)
        assertEquals(1.23.toDouble(),isDouble)

        // Test for Instant
        val isInstant = convertStringToObject("java.time.Instant", "2020-02-29T00:00:00Z")
        assertTrue(isInstant is java.time.Instant)
        assertEquals("2020-02-29T00:00:00Z",isInstant.toString())

        // Test for LocalDate
        val isLocalDate = convertStringToObject("java.time.LocalDate", "2/29/2020")
        assertTrue(isLocalDate is java.time.LocalDate)
        assertEquals("2020-02-29",isLocalDate.toString())
        assertEquals("2/29/2020",(isLocalDate as java.time.LocalDate).toStringDate())
    }

    /**
     * Use this is model to initialize a class
     */
    @Test
    fun createObjectTest() {
        val cvsData = arrayListOf(
            "id, firstName, lastName\n",
            "1, f1, l1\n",
            "2, f2, l2"
        )
        val listOfObj = parse(cvsData, Person::class)
        println(listOfObj)
        assertEquals(2, listOfObj.size)
        listOfObj.forEach {
            assertTrue(it is Person)
        }

    }

    @Test
    fun getCvsData() {
        val cvsData = getCvsFile(FILE_NAME)
        assertEquals(3,cvsData.size)
    }

    @Test
    fun createObjectFromCvsFile() {
        val listOfObj = parse(FILE_NAME, Person::class)
        println(listOfObj)
        assertEquals(2, listOfObj.size)
        listOfObj.forEach {
            assertTrue(it is Person)
        }
    }


    @Test
    fun createObjectFromCvsFilePerson2() {
        val listOfObj = parse(FILE_NAME_PERSON2, Person2::class)
        println(listOfObj)
        assertEquals(2, listOfObj.size)
        listOfObj.forEach {
            assertTrue(it is Person2)
        }
    }

    @Test
    fun createObjectFromInvalidCvsFile() {
        assertThrows(java.lang.IllegalArgumentException::class.java){
            parse(INVALID_FILE_NAME, Person::class)
        }
    }

    companion object {
        const val FILE_NAME = "./src/test/resources/file1.csv"
        const val FILE_NAME_PERSON2 = "./src/test/resources/person2.csv"
        const val INVALID_FILE_NAME = "./src/test/resources/invalidFile1.csv"
    }

}