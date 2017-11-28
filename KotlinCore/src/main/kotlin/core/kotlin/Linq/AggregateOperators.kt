package core.kotlin.Linq

import core.kotlin.Linq.support.customers
import core.kotlin.Linq.support.products

class AggregateOperators {

    fun linq73() {
        val factorsOf300 = intArrayOf(2, 2, 3, 5, 5)

        val uniqueFactors = factorsOf300.distinct().size

        println("There are $uniqueFactors unique factors of 300.")
    }

    fun linq74() {
        val numbers = intArrayOf(5, 4, 1, 3, 9, 8, 6, 7, 2, 0)

        val oddNumbers = numbers.count { it % 2 == 1 }

        println("There are $oddNumbers odd numbers in the list.")
    }

    fun linq76() {
        val orderCounts = customers.map { Pair(it.customerId, it.orders.size) }

        orderCounts.forEach { println(it) }
    }

    fun linq77() {
        val categoryCounts = products.groupBy { it.category }
                .map { Pair(it.key, it.value.size) }

        categoryCounts.forEach { println(it) }
    }

    fun linq78() {
        val numbers = intArrayOf(5, 4, 1, 3, 9, 8, 6, 7, 2, 0)

        val numSum = numbers.sum().toDouble()

        println("The sum of the numbers is $numSum")
    }

    fun linq79() {
        val words = arrayOf("cherry", "apple", "blueberry")

        val totalChars = words.sumBy { it.length }

        println("There are a total of $totalChars characters in these words.")
    }

    fun linq80() {
        val categories = products.groupBy { it.category }
                .map { Pair(it.key, it.value.sumBy { it.unitsInStock }) }

        categories.forEach { println(it) }
    }

    fun linq81() {
        val numbers = intArrayOf(5, 4, 1, 3, 9, 8, 6, 7, 2, 0)

        val minNum = numbers.min()

        println("The minimum number is $minNum")
    }

    fun linq82() {
        val words = arrayOf("cherry", "apple", "blueberry")

        val shortestWord = words.minBy { it.length }?.length

        println("The shortest word is $shortestWord characters long.")
    }

    fun linq83() {
        val categories = products.groupBy { it.category }
                .map { Pair(it.key, it.value.minBy { it.unitPrice }?.unitPrice) }

        categories.forEach { println(it) }
    }

    fun linq84() {
        val categories = products.groupBy { it.category }
                .map {
                    val minPrice = it.value.minBy { it.unitPrice }!!.unitPrice
                    Pair(it.key, it.value.filter { p -> p.unitPrice == minPrice })
                }

        categories.forEach {
            val (category,cheapestProducts) = it
            println(category + ": ")
            cheapestProducts.forEach { println(it) }
        }
    }

    fun linq85() {
        val numbers = intArrayOf(5, 4, 1, 3, 9, 8, 6, 7, 2, 0)

        val maxNum = numbers.max()

        println("The maximum number is $maxNum")
    }

    fun linq86() {
        val words = arrayOf("cherry", "apple", "blueberry")

        val longestLength = words.maxBy { it.length }?.length

        println("The longest word is $longestLength characters long.")
    }

    fun linq87() {
        val categories = products.groupBy { it.category }
                .map { Pair(it.key, it.value.maxBy { it.unitPrice }?.unitPrice) }

        categories.forEach { println(it) }
    }

    fun linq88() {
        val categories = products.groupBy { it.category }
                .map {
                    val maxPrice = it.value.maxBy { p -> p.unitPrice }?.unitPrice
                    Pair(it.key, it.value.filter { p -> p.unitPrice == maxPrice })
                }

        categories.forEach {
            val (category, mostExpensiveProducts) = it
            println("$category: ")
            mostExpensiveProducts.forEach { println(it) }
        }
    }

    fun linq89() {
        val numbers = intArrayOf(5, 4, 1, 3, 9, 8, 6, 7, 2, 0)

        val averageNum = numbers.average()

        println("The average number is $averageNum")
    }

    fun linq90() {
        val words = arrayOf("cherry", "apple", "blueberry")

        val averageLength = words.map { it.length }.average()

        println("The average word length is $averageLength characters.")
    }

    fun linq91() {
        val categories = products.groupBy { it.category }
                .map { Pair(it.key, it.value.map{it.unitPrice }.average() ) }

        categories.forEach { println("Category: ${it.first}, AveragePrice: ${it.second}") }
    }

    fun linq92() {
        val doubles = doubleArrayOf(1.7, 2.3, 1.9, 4.1, 2.9)

        val product = doubles.reduce { runningProduct, nextFactor -> runningProduct * nextFactor }

        println("Total product of all numbers: $product")
    }

    fun linq93() {
        val startBalance = 100

        val attemptedWithdrawals = intArrayOf(20, 10, 40, 50, 10, 70, 30)

        var i = 0
        val endBalance = attemptedWithdrawals.reduce { acc, nextWithdrawal ->
            val balance = if (i++ == 0 && nextWithdrawal <= acc) startBalance - acc else acc
            if (nextWithdrawal <= balance) balance - nextWithdrawal else balance
        }

        println("Ending balance: $endBalance")
    }
}