package com.techware.utilities

import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.util.*

class PropertiesFile() {
    companion object {
        fun writePropertiesFile(fileName: String, properties: Properties) {
            val userDirectory = File(System.getProperty("user.dir"))
            // Create directory if does not exist
            userDirectory.mkdir()
            val propertiesFile = File(userDirectory, fileName)
            val fileWriter = FileWriter(propertiesFile)
            properties.store(fileWriter, "example to properties file")
        }

        fun readPropertiesFile(fileName: String): Properties {
            val properties = Properties()
            val userDirectory = File(System.getProperty("user.dir"))
            val propertiesFile = File(userDirectory, fileName)
            val reader = FileReader(propertiesFile)
            properties.load(reader)
            return properties
        }

        fun createProperties(): Properties {
            val properties = Properties()
            properties.put("db.username", "username")
            properties.put("db.password", "password")
            properties.put("db.driver", "org.postgresql.Driver")
            properties.put("db.url", "jdbc:postgresql://localhost/testdb")
            return properties
        }

        fun displayProperties(properties: Properties) {
            properties.forEach { (k, v) -> println("key = $k, value = $v") }
        }
    }
}