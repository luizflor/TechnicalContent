package core.kotlin.Linq

import core.kotlin.Linq.support.products

class Quantifiers {

    fun linq67() {
        val words = arrayOf("believe", "relief", "receipt", "field")

        val iAfterE = words.any { it.contains("ei") }

        println("There is a word that contains in the list that contains 'ei': $iAfterE")
    }

    fun linq69() {
        val productGroups = products
                .groupBy { it.category }
                .filter { it.value.any { it.unitsInStock == 0 } }
                .map { Pair(it.key, it) }

        productGroups.forEach { println(it.second) }
    }

    fun linq70() {
        val numbers = intArrayOf(1, 11, 3, 19, 41, 65, 19)

        val onlyOdd = numbers.all { it % 2 == 1 }

        println("The list contains only odd numbers: $onlyOdd")
    }

    fun linq72() {
        val productGroups = products
                .groupBy { it.category }
                .filter { it.value.all { it.unitsInStock > 0 } }
                .map { Pair(it.key, it) }

        productGroups.forEach { println(it.second) }
    }
}