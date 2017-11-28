package core.kotlin.Linq

import core.kotlin.Linq.support.customers
import core.kotlin.Linq.support.products
import net.servicestack.func.Func.groupBy
import java.util.*

class Grouping {

    fun linq40() {
        val numbers = intArrayOf(5, 4, 1, 3, 9, 8, 6, 7, 2, 0)

        val numberGroups = numbers.groupBy { it % 5 }
                .map { Pair(it.key, it) }

        numberGroups.forEach { it ->
            val (remainder, g) = it
            println("Numbers with a remainder of $remainder when divided by 5:")
            g.value.forEach { println(it) }
        }
    }

    fun linq41() {
        val words = arrayOf("blueberry", "chimpanzee", "abacus", "banana", "apple", "cheese")

        val wordGroups = words.groupBy { it[0] }
                .map { Pair(it.key, it) }

        wordGroups.forEach {
            val (firstLetter, g) = it
            println("Words that start with the letter '$firstLetter':")
            g.value.forEach { println(it) }
        }
    }

    fun linq42() {
        val orderGroups = products.groupBy { it.category }
                .map { Pair(it.key, it) }

        orderGroups.forEach {
            val (category,g) = it
            println("${category}:")
            g.value.forEach { println(it) }
        }
    }

    fun linq43() {
        val customerOrderGroups = customers.map { c ->
            Pair(c.companyName,
                    c.orders.groupBy { it.orderDate.year + 1900 }
                            .map { Pair(it.key, it.value.groupBy { it.orderDate.month + 1 }) })
        }

        customerOrderGroups.forEach {
            val (companyName, yearGroups) = it
            println("\n# $companyName")
            yearGroups.forEach {
                val (year,monthGroups) = it
                println("${year.toString()}: ")
                monthGroups.forEach { println("    $it") }
            }
        }
    }

    fun linq44() {
        val anagrams = listOf("from   ", " salt", " earn ", "  last   ", " near ", " form  ")

        val orderGroups = groupBy(anagrams,
                { w -> w.trim { it <= ' ' } },
                { a, b ->
                    val aChars = a.toCharArray()
                    val bChars = b.toCharArray()
                    Arrays.sort(aChars)
                    Arrays.sort(bChars)
                    Arrays.equals(aChars, bChars)
                })

        orderGroups.forEach { g ->
            val sb = StringBuilder()
            g.forEach { w ->
                if (sb.length > 0)
                    sb.append(", ")
                sb.append("'").append(w).append("'")
            }
            println("[ $sb ]")
        }
    }

    fun linq45() {
        val anagrams = listOf("from   ", " salt", " earn ", "  last   ", " near ", " form  ")

        val orderGroups = groupBy(anagrams,
                { w -> w.trim { it <= ' ' } },
                { a, b ->
                    val aChars = a.toCharArray()
                    val bChars = b.toCharArray()
                    Arrays.sort(aChars)
                    Arrays.sort(bChars)
                    Arrays.equals(aChars, bChars)
                },
                { it.toUpperCase() })

        orderGroups.forEach { g ->
            val sb = StringBuilder()
            g.forEach {
                if (sb.length > 0)
                    sb.append(", ")
                sb.append("'").append(it).append("'")
            }
            println("[ $sb ]")
        }
    }
}