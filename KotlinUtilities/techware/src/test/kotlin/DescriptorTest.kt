import com.techware.generate.*
import com.techware.generate.Descriptor.Companion.generateDescriptor
import com.techware.generate.Descriptor.Companion.types
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class DescriptorTest {
    @BeforeEach
    fun setUp() {

    }

    @AfterEach
    fun tearDown() {
    }

    /**
     *
     id, int
    fn, string
    ln, string
     */
    @Test
    fun testDescriptor() {
        val personStr ="id, int\n" +
                "firstname, string\n" +
                "lastname, string\n"
        val person2Str="id, int\n" +
                "firstname, string\n" +
                "lastname, string\n" +
                "birthdate, date\n" +
                "ismodified, boolean\n" +
                "timestamp, instant\n" +
                "salary, double\n"
        val personDescriptor = generateDescriptor(Person::class)
        println(personDescriptor)
        assertEquals(personStr, personDescriptor)

        val person2Descriptor = generateDescriptor(Person2::class)
        println(person2Descriptor)
        assertEquals(person2Str, person2Descriptor)
    }

    @Test
    fun generatorDescriptorTest() {
        Descriptor.generateDescriptor(Person::class, PATH_DESTINATION_KOTLIN)
        Descriptor.generateDescriptor(Person2::class, PATH_DESTINATION_KOTLIN)
    }

    @Test
    fun testTypes() {
        val typeInt = types["kotlin.Int"]
        assertEquals("int",typeInt)

        val typeString = types["kotlin.String"]
        assertEquals("string",typeString)
        
         val typeLong = types["kotlin.Long"]
        assertEquals("long",typeLong)
        
         val typeShort = types["kotlin.Short"]
        assertEquals("short",typeShort)
        
         val typeByte = types["kotlin.Byte"]
        assertEquals("byte",typeByte)
        
         val typeBoolean = types["kotlin.Boolean"]
        assertEquals("boolean",typeBoolean)
        
         val typeFloat = types["kotlin.Float"]
        assertEquals("float",typeFloat)
        
         val typeDouble = types["kotlin.Double"]
        assertEquals("double",typeDouble)
        
        

        val kotlinInt = types.filter { it.value == "int" }.keys.first()
        assertEquals("kotlin.Int", kotlinInt)
        
        val kotlinString = types.filter { it.value == "string" }.keys.first()
        assertEquals("kotlin.String", kotlinString)

        val kotlinLong = types.filter { it.value == "long" }.keys.first()
        assertEquals("kotlin.Long",kotlinLong)
        
        val kotlinShort = types.filter { it.value == "short" }.keys.first()
        assertEquals("kotlin.Short",kotlinShort)
        
        val kotlinByte = types.filter { it.value == "byte" }.keys.first()
        assertEquals("kotlin.Byte",kotlinByte)
        
        val kotlinBoolean = types.filter { it.value == "boolean" }.keys.first()
        assertEquals("kotlin.Boolean",kotlinBoolean)
        
        val kotlinFloat = types.filter { it.value == "float" }.keys.first()
        assertEquals("kotlin.Float",kotlinFloat)
        
        val kotlinDouble = types.filter { it.value == "double" }.keys.first()
        assertEquals("kotlin.Double" ,kotlinDouble)
    }

    @Test
    fun dateTest() {
        val instant = "2/29/2020".fromDateToInstant()
        assertEquals("2020-02-29T00:00:00Z",instant.toString())

        val date = instant?.toDate()
        assertEquals("2020-02-29", date.toString())
        assertEquals("2/29/2020", date.toStringDate())

        assertEquals(2020,date?.year)
        assertEquals(2,date?.monthValue)
        assertEquals(29,date?.dayOfMonth)
        println(instant)
        println(date)

        val instantDateString = instant?.toStringDate()
        println(instantDateString)
        assertEquals("2/29/2020",instantDateString)
        val instantDateTimeString = instant?.toStringDateTime()
        assertEquals("2020-02-29T00:00:00Z",instantDateTimeString)
        println(instantDateTimeString)

        val instant2 = "2020-02-29T00:00:00Z".fromDateTimeToInstant()
        println(instant2)
        assertEquals("2020-02-29T00:00:00Z", instant2?.toStringDateTime())

    }

    @Test
    fun generateTypeFromClassTest() {
        val typeListPerson = Descriptor.generateTypeFromClass(Person::class)
        println(typeListPerson)

        val typeListPerson2 = Descriptor.generateTypeFromClass(Person2::class)
        println(typeListPerson2)

    }

    @Test
    fun generateClassTest() {

        val descriptorPersonStr ="id, int\n" +
                "firstname, string\n" +
                "lastname, string\n"

        val generatedPersonClass = Descriptor.generateClassFromString(descriptorPersonStr, "Person")
        println(generatedPersonClass)

        val personList = descriptorPersonStr.split('\n')
        val generatedPersonClass2 = Descriptor.generateClassFromList(personList, "Person")
        println(generatedPersonClass2)


        var personStr ="data class Person(\n" +
                "val id: Int, val firstname: String, val lastname: String) {\n" +
                "\toverride fun toString(): String {\n" +
                "\t\treturn \"Person(id=\$id, firstname=\$firstname, lastname=\$lastname)\"\n" +
                "\t}\n" +
                "}\n"


        assertEquals(personStr,generatedPersonClass)

        val descriptorPerson2Str="id, int\n" +
                "firstname, string\n" +
                "lastname, string\n" +
                "birthdate, date\n" +
                "ismodified, boolean\n" +
                "timestamp, instant\n" +
                "salary, double\n"
        val person2List= descriptorPerson2Str.split('\n')

        val generatedPerson2Class = Descriptor.generateClassFromString(descriptorPerson2Str, "Person2")
        println(generatedPerson2Class)

        val person2Str = "import java.util.*\n" +
                "import java.time.Instant\n" +
                "data class Person2(\n" +
                "val id: Int, val firstname: String, val lastname: String, val birthdate: Date, val ismodified: Boolean, val timestamp: Instant, val salary: Double) {\n" +
                "\toverride fun toString(): String {\n" +
                "\t\treturn \"Person2(id=\$id, firstname=\$firstname, lastname=\$lastname, birthdate=\$birthdate, ismodified=\$ismodified, timestamp=\$timestamp, salary=\$salary)\"\n" +
                "\t}\n" +
                "}\n"
        assertEquals(person2Str,generatedPerson2Class)


    }

    @Test
    fun capitalTest() {
        val name = "luiz"
        name.capitalize()
        println(name.capitalize())
    }

    @Test
    fun generateClassFile() {
        Descriptor.generateClass(fileName = FILE_NAME_PERSON1, className = "Person", packageName = "model",targetFolder =  PATH_DESTINATION_MODEL)
    }

    @Test
    fun generateClassFromFileTest() {
        val generatedClass = Descriptor.generateClass(fileName = FILE_NAME_PERSON1, className = "Person", packageName = "model")
        println(generatedClass)

        val expectedPersonClass = "data class Person(\n" +
                "val id: Int, val firstname: String, val lastname: String) {\n" +
                "\toverride fun toString(): String {\n" +
                "\t\treturn \"Person(id=\$id, firstname=\$firstname, lastname=\$lastname)\"\n" +
                "\t}\n" +
                "}\n"
        assertEquals(expectedPersonClass,generatedClass)
    }
    companion object {
//        const val FILE_NAME_PERSON1 = "./src/test/resources/Descriptors/person1.txt"
        const val FILE_NAME_PERSON1 = "/Users/luizsilva/Projects/Research/TechnicalContent/KotlinUtilities/KotlinTests/src/main/kotlin/generator/kotlin/Person.csv"
        const val PATH_DESTINATION_MODEL = "/Users/luizsilva/Projects/Research/TechnicalContent/KotlinUtilities/KotlinTests/src/main/kotlin/model"
        const val PATH_DESTINATION_KOTLIN = "/Users/luizsilva/Projects/Research/TechnicalContent/KotlinUtilities/KotlinTests/src/main/kotlin/generator/kotlin"
    }
}