package core.kotlin.Linq

import core.kotlin.Linq.support.customers
import core.kotlin.Linq.support.products
import java.util.*

class MiscOperators {

    fun linq94() {
        val numbersA = intArrayOf(0, 2, 4, 5, 6, 8, 9)
        val numbersB = intArrayOf(1, 3, 5, 7, 8)

        val allNumbers = numbersA + numbersB

        println("All numbers from both arrays:")
        allNumbers.forEach { println(it) }
    }

    fun linq95() {
        val customerNames = customers.map { it.companyName }

        val productNames = products.map { it.productName }

        val allNames = customerNames + productNames

        println("Customer and product names:")
        allNames.forEach { println(it) }
    }

    fun linq96() {
        val wordsA = arrayOf("cherry", "apple", "blueberry")
        val wordsB = arrayOf("cherry", "apple", "blueberry")

        val match = Arrays.equals(wordsA, wordsB)

        println("The sequences match: $match")
    }

    fun linq97() {
        val wordsA = arrayOf("cherry", "apple", "blueberry")
        val wordsB = arrayOf("cherry", "blueberry", "cherry")

        val match = Arrays.equals(wordsA, wordsB)

        println("The sequences match: $match")
    }
}