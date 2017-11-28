package core.kotlin.Linq

import core.kotlin.Linq.support.Product
import core.kotlin.Linq.support.products

class Ordering {

    fun linq28() {
        val words = arrayOf("cherry", "apple", "blueberry")

        val sortedWords = words.sorted()

        println("The sorted list of words:")
        sortedWords.forEach { println(it) }
    }

    fun linq29() {
        val words = arrayOf("cherry", "apple", "blueberry")

        val sortedWords = words.sortedBy { s -> s.length }

        println("The sorted list of words (by length):")
        sortedWords.forEach { println(it) }
    }

    fun linq30() {
        val sortedProducts = products.sortedBy { it.productName }

        sortedProducts.forEach { println(it) }
    }

    fun linq31() {
        val words = arrayOf("aPPLE", "AbAcUs", "bRaNcH", "BlUeBeRrY", "ClOvEr", "cHeRry")

        val sortedWords = words.sortedWith(String.CASE_INSENSITIVE_ORDER)

        sortedWords.forEach { println(it) }
    }

    fun linq32() {
        val doubles = doubleArrayOf(1.7, 2.3, 1.9, 4.1, 2.9)

        val sortedDoubles = doubles.sortedDescending()

        println("The doubles from highest to lowest:")
        sortedDoubles.forEach { println(it) }
    }

    fun linq33() {
        val sortedProducts = products.sortedByDescending { it.unitsInStock }

        sortedProducts.forEach { println(it) }
    }

    fun linq34() {
        val words = arrayOf("aPPLE", "AbAcUs", "bRaNcH", "BlUeBeRrY", "ClOvEr", "cHeRry")

        val sortedWords = words.sortedWith(String.CASE_INSENSITIVE_ORDER).reversed()

        sortedWords.forEach { println(it) }
    }

    fun linq35() {
        val digits = arrayOf("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine")

        val sortedDigits = digits.sorted().sortedBy { it.length }

        println("Sorted digits:")
        sortedDigits.forEach { println(it) }
    }

    fun linq36() {
        val words = arrayOf("aPPLE", "AbAcUs", "bRaNcH", "BlUeBeRrY", "ClOvEr", "cHeRry")

        val sortedWords = words.sortedWith(String.CASE_INSENSITIVE_ORDER).sortedBy { it.length }

        sortedWords.forEach { println(it) }
    }

//    fun linq37() {
//        val sortedProducts = orderByAll(products,
//                Comparator<Product> { a, b -> a.category.compareTo(b.category) },
//                Comparator<Product> { a, b -> b.unitPrice.compareTo(a.unitPrice) })
//
//        sortedProducts.forEach { println(it) }
//    }
//
//    fun linq38() {
//        val words = arrayOf("aPPLE", "AbAcUs", "bRaNcH", "BlUeBeRrY", "ClOvEr", "cHeRry")
//
//        val sortedWords = orderByAll(words,
//                Comparator<String> { a, b -> Integer.compare(a.length, b.length) },
//                Comparator<String> { a, b -> String.CASE_INSENSITIVE_ORDER.compare(b, a) })
//
//        sortedWords.forEach { println(it) }
//    }

    fun linq39() {
        val digits = arrayOf("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine")

        val reversedIDigits = digits.filter { it[1] == 'i' }.reversed()

        println("A backwards list of the digits with a second character of 'i':")
        reversedIDigits.forEach { println(it) }
    }
}