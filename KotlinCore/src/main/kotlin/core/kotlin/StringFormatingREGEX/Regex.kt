package core.kotlin

import java.util.regex.Pattern

fun main(args: Array<String>) {
    doPatern()
}

fun doPatern(): Unit{
    // https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html
    // https://docs.oracle.com/javase/tutorial/essential/regex/
    // https://regex101.com/
    val value1="apple, apple and orange please"
    val pattern = Pattern.compile("\\w+")
    val matcher = pattern.matcher(value1)
    while (matcher.find()){
        println(matcher.group())
    }
}