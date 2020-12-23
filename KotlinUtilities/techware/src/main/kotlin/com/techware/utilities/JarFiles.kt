package com.techware.utilities

import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.jar.JarFile

class JarFiles {
    companion object {

        /**
         * Copy files that either in the src/main/resources or from the jar file
         */
        fun copyJarFiles(sourceFolder: String, targetFolder: String) {
            if (File(Thread.currentThread().contextClassLoader.getResource(sourceFolder).file).exists()) {
                val resource = Thread.currentThread().contextClassLoader.getResource(sourceFolder).file
                File(resource).copyRecursively(File(targetFolder), true)
//                val f = File(targetFolder).name
//                println(f)
            } else {
                val jarFile =
                    File(this.javaClass.protectionDomain.codeSource.location.toURI().path)
                val jar = JarFile(jarFile)

                val entries = jar.entries() //gives ALL entries in jar
                while (entries.hasMoreElements()) {
                    val jarEntry = entries.nextElement()
                    val name = jarEntry.name
                    if (name.startsWith(sourceFolder)) { //filter according to the path

                       val f = if(File(targetFolder).name == name) {
                            File(targetFolder)
                        } else {
                            File(File(targetFolder).absoluteFile.parent + File.separator + name)
                       }

//                        println(f.absoluteFile.path + " - " + jarEntry.isDirectory + " - " + File(targetFolder).name + " - "+ name)
//                        val f = File( name)
                        if (jarEntry.isDirectory) {
                            f.mkdirs()
                            continue
                        }
                        val `in` = BufferedInputStream(jar.getInputStream(jarEntry))
                        val out = BufferedOutputStream(FileOutputStream(f))
                        val buffer = ByteArray(2048)
                        while (true) {
                            val nBytes: Int = `in`.read(buffer)
                            if (nBytes <= 0) break
                            out.write(buffer, 0, nBytes)
                        }
                        out.flush()
                        out.close()
                        `in`.close()
                    }
                }
                jar.close()
            }
        }
    }
}