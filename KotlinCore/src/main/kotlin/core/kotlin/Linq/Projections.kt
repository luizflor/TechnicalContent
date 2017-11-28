package core.kotlin.Linq


import core.kotlin.Linq.support.customers
import core.kotlin.Linq.support.products
import java.util.*

class Projections {

    fun linq06() {
        val numbers = intArrayOf(5, 4, 1, 3, 9, 8, 6, 7, 2, 0)

        val numsPlusOne = numbers.map { it + 1 }

        println("Numbers + 1:")
        numsPlusOne.forEach { println(it) }
    }

    fun linq07() {
        val productNames = products.map { it.productName }

        println("Product Names:")
        productNames.forEach { println(it) }
    }

    fun linq08() {
        val numbers = intArrayOf(5, 4, 1, 3, 9, 8, 6, 7, 2, 0)
        val strings = arrayOf("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine")

        val textNums = numbers.map { strings[it] }

        println("Number strings:")
        textNums.forEach { println(it) }
    }

    fun linq09() {
        val words = arrayOf("aPPLE", "BlUeBeRrY", "cHeRry")

        val upperLowerWords = words.map { w -> Pair(w.toUpperCase(), w.toLowerCase()) }

        upperLowerWords.forEach { println("Uppercase: ${it.first}, Lowercase: ${it.second}") }
    }

    fun linq10() {
        val numbers = intArrayOf(5, 4, 1, 3, 9, 8, 6, 7, 2, 0)
        val strings = arrayOf("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine")

        val digitOddEvens = numbers.map { Pair(strings[it], it % 2 == 0) }

        digitOddEvens.forEach {
            val (d,isEven) = it
            println("The digit $d is ${if (isEven) "even" else "odd"}.")
        }
    }

    fun linq11() {
        val productInfos = products.map { p -> Triple(p.productName, p.category, p.unitPrice) }

        println("Product Info:")
        productInfos.forEach {
            val (name,category,cost) = it
            println("$name is in the category $category and costs $cost per unit.")
        }
    }

    fun linq12() {
        val numbers = intArrayOf(5, 4, 1, 3, 9, 8, 6, 7, 2, 0)

        val numsInPlace = numbers.mapIndexed { index, n -> Pair(n, n == index) }

        println("Number: In-place?")
        numsInPlace.forEach { println("${it.first.toString()}: ${it.second}") }
    }

    fun linq13() {
        val numbers = intArrayOf(5, 4, 1, 3, 9, 8, 6, 7, 2, 0)
        val digits = arrayOf("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine")

        val lowNums = numbers.filter { it < 5 }.map { digits[it] }

        println("Numbers < 5:")
        lowNums.forEach { println(it) }
    }

    fun linq14() {
        val numbersA = intArrayOf(0, 2, 4, 5, 6, 8, 9)
        val numbersB = intArrayOf(1, 3, 5, 7, 8)

        val pairs = numbersA.flatMap { a ->
            numbersB.filter { b -> a < b }
                    .map { Pair(a, it) }
        }

        println("Pairs where a < b:")
        pairs.forEach { println("${it.first.toString()} is less than ${it.second}") }
    }

    fun linq15() {
        val orders = customers.flatMap { c ->
            c.orders.filter { it.total < 500 }
                    .map { o -> Triple(c.customerId, o.orderId, o.total) }
        }

        orders.forEach { println(it) }
    }

    fun linq16() {
        val date = Date(98, 0, 1) //= 1998-01-01
        val orders = customers.flatMap { c ->
            c.orders.filter { it.orderDate >= date }
                    .map { o -> Triple(c.customerId, o.orderId, o.orderDate) }
        }

        orders.forEach { println(it) }
    }

    fun linq17() {
        val orders = customers.flatMap { c ->
            c.orders.filter { it.total >= 2000 }
                    .map { o -> Triple(c.customerId, o.orderId, o.total) }
        }

        orders.forEach { println(it) }
    }

    fun linq18() {
        val cutoffDate = Date(97, 0, 1) //1997-01-01

        val orders = customers
            .filter { it.region == "WA" }.flatMap { c ->
                c.orders.filter { it.orderDate > cutoffDate }
                        .map { o -> Pair(c.customerId, o.orderId) }
            }

        orders.forEach { println(it) }
    }

    fun linq19() {
        var custIndex = 0
        val customerOrders = customers.flatMap { c ->
            c.orders.mapIndexed { index, o -> "Customer #$index has an order with OrderID ${o.orderId}" }
        }

        customerOrders.forEach { println(it) }
    }
}
