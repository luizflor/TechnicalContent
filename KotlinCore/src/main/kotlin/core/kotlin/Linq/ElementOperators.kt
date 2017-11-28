package core.kotlin.Linq

import core.kotlin.Linq.support.products

class ElementOperators {

    fun linq58() {
        val product12 = products.filter { it.productId == 12 }.first()

        println(product12)
    }

    fun linq59() {
        val strings = arrayOf("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine")

        val startsWithO = strings.first { it[0] == 'o' }

        println("A string starting with 'o': $startsWithO")
    }

    fun linq61() {
        val numbers = intArrayOf()

        val firstNumOrDefault = numbers.firstOrNull() ?: 0

        println(firstNumOrDefault)
    }

    fun linq62() {
        val product789 = products.firstOrNull { it.productId == 789 }

        println("Product 789 exists: ${product789 != null}")
    }

    fun linq64() {
        val numbers = intArrayOf(5, 4, 1, 3, 9, 8, 6, 7, 2, 0)

        val fourthLowNum = numbers.filter { it > 5 }[1]

        println("Second number > 5: $fourthLowNum")
    }
}