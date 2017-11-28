package core.kotlin.Linq

import core.kotlin.Linq.support.customers
import core.kotlin.Linq.support.products

class SetOperators {

    fun linq46() {
        val factorsOf300 = intArrayOf(2, 2, 3, 5, 5)

        val uniqueFactors = factorsOf300.distinct()

        println("Prime factors of 300:")
        uniqueFactors.forEach { println(it) }
    }

    fun linq47() {
        val categoryNames = products.map { it.category}.distinct()

        println("Category names:")
        categoryNames.forEach { println(it) }
    }

    fun linq48() {
        val numbersA = listOf(0, 2, 4, 5, 6, 8, 9)
        val numbersB = listOf(1, 3, 5, 7, 8)

        val uniqueNumbers = numbersA.union(numbersB)

        println("Unique numbers from both arrays:")
        uniqueNumbers.forEach { println(it) }
    }

    fun linq49() {
        val productFirstChars = products.map { it.productName[0] }

        val customerFirstChars = customers.map { it.companyName[0] }

        val uniqueFirstChars = productFirstChars.union(customerFirstChars)

        println("Unique first letters from Product names and Customer names:")
        uniqueFirstChars.forEach { println(it) }
    }

    fun linq50() {
        val numbersA = listOf(0, 2, 4, 5, 6, 8, 9)
        val numbersB = listOf(1, 3, 5, 7, 8)

        val commonNumbers = numbersA.intersect(numbersB)

        println("Common numbers shared by both arrays:")
        commonNumbers.forEach { println(it) }
    }

    fun linq51() {
        val productFirstChars = products.map { it.productName[0] }

        val customerFirstChars = customers.map { it.companyName[0] }

        val commonFirstChars = productFirstChars.intersect(customerFirstChars)

        println("Common first letters from Product names and Customer names:")
        commonFirstChars.forEach { println(it) }
    }

    fun linq52() {
        val numbersA = listOf(0, 2, 4, 5, 6, 8, 9)
        val numbersB = listOf(1, 3, 5, 7, 8)

        val aOnlyNumbers = numbersA.difference(numbersB)

        println("Numbers in first array but not second array:")
        aOnlyNumbers.forEach { println(it) }
    }

    fun linq53() {
        val productFirstChars = products.map { it.productName[0] }

        val customerFirstChars = customers.map { it.companyName[0] }

        val productOnlyFirstChars = productFirstChars.difference(customerFirstChars)

        println("First letters from Product names, but not from Customer names:")
        productOnlyFirstChars.forEach { println(it) }
    }
}