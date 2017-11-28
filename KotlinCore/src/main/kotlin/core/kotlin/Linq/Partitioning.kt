package core.kotlin.Linq

import core.kotlin.Linq.support.customers

class Partitioning {

    fun linq20() {
        val numbers = intArrayOf(5, 4, 1, 3, 9, 8, 6, 7, 2, 0)

        val first3Numbers = numbers.take(3)

        println("First 3 numbers:")
        first3Numbers.forEach { println(it) }
    }

    fun linq21() {
        val first3WAOrders = customers
                .filter {  it.region == "WA" }
                .flatMap { c ->
                    c.orders.map { o -> Triple(c.customerId, o.orderId, o.orderDate) }
                }
                .take(3)

        println("First 3 orders in WA:")
        first3WAOrders.forEach { println(it) }
    }

    fun linq22() {
        val numbers = intArrayOf(5, 4, 1, 3, 9, 8, 6, 7, 2, 0)

        val allButFirst4Numbers = numbers.drop(4)

        println("All but first 4 numbers:")
        allButFirst4Numbers.forEach { println(it) }
    }

    fun linq23() {
        val waOrders = customers
                .filter { it.region == "WA" }
                .flatMap { c ->
                    c.orders.map { o -> Triple(c.customerId, o.orderId, o.orderDate) }
                }

        val allButFirst2Orders = waOrders.drop(2)

        println("All but first 2 orders in WA:")
        allButFirst2Orders.forEach { println(it) }
    }

    fun linq24() {
        val numbers = intArrayOf(5, 4, 1, 3, 9, 8, 6, 7, 2, 0)

        val firstNumbersLessThan6 = numbers.takeWhile { it < 6 }

        println("First numbers less than 6:")
        firstNumbersLessThan6.forEach { println(it) }
    }

    fun linq25() {
        val numbers = intArrayOf(5, 4, 1, 3, 9, 8, 6, 7, 2, 0)

        var index = 0
        val firstSmallNumbers = numbers.takeWhile { it >= index++ }

        println("First numbers not less than their position:")
        firstSmallNumbers.forEach { println(it) }
    }

    fun linq26() {
        val numbers = intArrayOf(5, 4, 1, 3, 9, 8, 6, 7, 2, 0)

        val allButFirst3Numbers = numbers.dropWhile { it % 3 != 0 }

        println("All elements starting from first element divisible by 3:")
        allButFirst3Numbers.forEach { println(it) }
    }

    fun linq27() {
        val numbers = intArrayOf(5, 4, 1, 3, 9, 8, 6, 7, 2, 0)

        var index = 0
        val laterNumbers = numbers.dropWhile { it >= index++ }

        println("All elements starting from first element less than its position:")
        laterNumbers.forEach { println(it) }
    }
}