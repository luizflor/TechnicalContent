package core.kotlin

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
//import com.company.Main

fun main(args: Array<String>) {
  // var x = Main.GetDefaultProperties("MyDefaultValues.xml")
    try {
        val defaultProps = Properties()
        //Main::class.java!!.getResourceAsStream("MyDefaultValues.xml").use({ inputStream -> defaultProps.loadFromXML(inputStream) })
        //defaultProps.loadFromXML(Files.newInputStream(Paths.get("MyDefaultValues.xml")))
        loadUserProps(defaultProps,"MyDefaultValues.xml")
        val userProps = Properties(defaultProps)
        loadUserProps(userProps, "userValues.xml")

        val welcomeMessage = userProps.getProperty("welcomeMessage")
        val farewellMessage = userProps.getProperty("farewellMessage")

        println(welcomeMessage)
        println(farewellMessage)

        if (userProps.getProperty("isFirstRun").equals("Y", ignoreCase = true)) {
            userProps.setProperty("welcomeMessage", "Welcome back")
            userProps.setProperty("farewellMessage", "Things will be familiar now")
            userProps.setProperty("isFirstRun", "N")
            saveUserProps(userProps)
        }
        var env = System.getenv()
        env.forEach { k, v ->println(k + " | "+ v)  }

        var userName = System.getProperty("user.name")
        println(userName)
        var userHome = System.getProperty("user.home")
        println(userHome)
        println("os.arch: "+System.getProperty("os.arch"))
        println("os.name: "+System.getProperty("os.name"))
        println("os.version: "+System.getProperty("os.version"))
        var javaVendor = System.getProperty("java.vendor")
        println(javaVendor)
        println("java.version: "+ System.getProperty("java.version"))
        println("java.class.path: "+ System.getProperty("java.class.path"))


    } catch (e: IOException) {
        println("Exception <" + e.javaClass.simpleName + "> " + e.message)
    }
}


@Throws(IOException::class)
private fun loadUserProps(userProps: Properties, fileName:String): Properties {
    val userFile = Paths.get(fileName)
    if (Files.exists(userFile)) {
        Files.newInputStream(userFile).use { inputStream -> userProps.loadFromXML(inputStream) }
    }

    return userProps
}

@Throws(IOException::class)
private fun saveUserProps(userProps: Properties) {
    Files.newOutputStream(Paths.get("userValues.xml")).use { outputStream -> userProps.storeToXML(outputStream, null) }
}