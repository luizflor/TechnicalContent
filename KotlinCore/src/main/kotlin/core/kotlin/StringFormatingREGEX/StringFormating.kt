package core.kotlin

import java.util.*

fun main(args: Array<String>) {
    println(doStringJoiner())
    doFormat()
}

fun doStringJoiner(): String {
    // this will return {alpha, theta, gamma}
    val sj = StringJoiner(", ","{","}")
    sj.setEmptyValue("EMPTY")

    sj.add("alpha")
    sj.add("theta")
    sj.add("gamma")
    return sj.toString()
}
fun doFormat() {
    // https://docs.oracle.com/javase/8/docs/api/java/util/Formatter.html
    // % [argument index][flags][width][precision] conversion
    // [precision] minimum characters to display - right justified
    // [width] decimal places to display
    // d - Decimal
    // o - Octal
    // x - Hex
    // f - Decimal
    // e - Scientific Notation
    // s - String
    //

    println("%d: " + String.format("%d", 32))
    println("%o: " + String.format("%o", 32))
    println("%x: " + String.format("%x", 32))
    println("%#o: " + String.format("%#o", 32))
    println("%#x: " + String.format("%#x", 32))
    // flags
    // # - include radix
    // 0 - Zero-padding
    // - - Left justify
    // , -  Include grouping separator
    //   - Leave space for positive numbers
    // + - Always show sign
    // ( - Enclose nagative values in parenthesis
    println("Flags")
    println("W:%d X:%d: " + String.format("W:%d X:%d", 5,235))
    println("Y:%d Z:%d: " + String.format("Y:%d Z:%d", 481,12))
    println()
    println("W:%4d X:%4d: " + String.format("W:%4d X:%4d", 5,235))
    println("Y:%4d Z:%4d: " + String.format("Y:%4d Z:%4d", 481,12))
    println()
    println("W:%04d X:%04d: " + String.format("W:%04d X:%04d", 5,235))
    println("Y:%04d Z:%04d: " + String.format("Y:%04d Z:%04d", 481,12))
    println()
    println("W:%4d X:%-4d: " + String.format("W:%-4d X:%4d", 5,235))
    println("Y:%4d Z:%-4d: " + String.format("Y:%-4d Z:%4d", 481,12))
    println()
    println("%,.2f: " + String.format("%,.2f",1234567.0))
    println()
    println("%d: "+ String.format("%d",123))
    println("%d: "+ String.format("%d",-456))

    println()
    println("%d: "+ String.format("% d",123))
    println("%d: "+ String.format("% d",-456))

    println()
    println("%d: "+ String.format("%+d",123))
    println("%d: "+ String.format("%+d",-456))

    println()
    println("%d: "+ String.format("%(d",123))
    println("%d: "+ String.format("%(d",-456))
    println("%d: "+ String.format("% (d",123))

}