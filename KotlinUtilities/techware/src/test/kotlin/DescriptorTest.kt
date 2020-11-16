import com.techware.utilities.*
import com.techware.utilities.Descriptor.Companion.types
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
    fun getDescriptor() {
        val c1 = Person::class.toMap()
        println(c1)
        var str = StringBuilder()

        c1.forEach { t, u ->
            val type = types[u.toString()]
            str.appendln("${t.toLowerCase()}, ${type?.toLowerCase()}")
            println("$t - $u")
        }
        println(str)
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
}