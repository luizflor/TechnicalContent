import com.techware.utilities.PropertiesFile
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class PropertiesFileTest {
    @BeforeEach
    fun setUp() {

    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun testPropertiesFiles() {
        val fileName= "config.properties"
        val properties = PropertiesFile.createProperties()
        assertEquals(4,properties.size)

        PropertiesFile.writePropertiesFile(fileName, properties)
        println("\nWrite file *** $fileName ***")
        PropertiesFile.displayProperties(properties)

        val propertiesRead = PropertiesFile.readPropertiesFile(fileName)
        assertEquals(4,propertiesRead.size)
        println("\nRead file *** $fileName ***")
        PropertiesFile.displayProperties(propertiesRead)

    }
}