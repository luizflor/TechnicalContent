package core.kotlin.Linq

import core.kotlin.Linq.support.products
import net.servicestack.func.Func.join
import net.servicestack.func.Func.joinGroup
//https://code.msdn.microsoft.com/LINQ-Join-Operators-dabef4e9
class JoinOperators {

    fun linq102() {
        val categories = listOf("Beverages", "Condiments", "Vegetables", "Dairy Products", "Seafood")

        val q = join(categories, products) { c, p -> c == p.category }
                .map { Pair(it.A, it.B.productName) }

        q.forEach { println("${it.first}: ${it.second}") }
    }

    fun linq103() {
        val categories = listOf("Beverages", "Condiments", "Vegetables", "Dairy Products", "Seafood")

        val q = joinGroup(categories, products) { c, p -> c == p.category }
                .map { Pair(it.key, it.items.map { it.B }) }

        q.forEach {
            println("${it.first}:")
            it.second.forEach { p -> println("   ${p.productName}") }
        }
    }

    fun linq104() {
        val categories = listOf("Beverages", "Condiments", "Vegetables", "Dairy Products", "Seafood")

        val q = joinGroup(categories, products) { c, p -> c == p.category }
                .flatMap { j -> j.items.map { it.B }.map {  Pair(j.key, it.productName) } }

        q.forEach { println("${it.second}: ${it.first}") }
    }

    fun linq105() {
        val categories = listOf("Beverages", "Condiments", "Vegetables", "Dairy Products", "Seafood")

        val q = categories.flatMap { c ->
            val catProducts = products.filter { c == it.category }
            if (catProducts.isEmpty())
                listOf(Pair(c, "(No products)"))
            else
                catProducts.map { Pair(c, it.productName) }
        }

        q.forEach { println("${it.second}: ${it.first}") }
    }
}