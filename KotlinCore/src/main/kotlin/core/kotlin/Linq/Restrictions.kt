package core.kotlin.Linq

import core.kotlin.Linq.support.customers
import core.kotlin.Linq.support.dateFmt
import core.kotlin.Linq.support.products

class Restrictions {

    fun linq1() {
        val numbers = intArrayOf(5, 4, 1, 3, 9, 8, 6, 7, 2, 0)

        val lowNums = numbers.filter { it < 5 }

        println("Numbers < 5:")
        lowNums.forEach { println(it) }
    }

    fun linq2() {
        val soldOutProducts = products.filter { it.unitsInStock == 0 }

        println("Sold out products:")
        soldOutProducts.forEach { println("${it.productName} is sold out!") }
    }

    fun linq3() {
        val expensiveInStockProducts = products.filter { it.unitsInStock > 0 && it.unitPrice > 3.00 }

        println("In-stock products that cost more than 3.00:")
        expensiveInStockProducts.forEach { println("${it.productName} is in stock and costs more than 3.00.") }
    }

    fun linq4() {
        val waCustomers = customers.filter { "WA" == it.region }

        println("Customers from Washington and their orders:")
        waCustomers.forEach { c ->
            println("Customer ${c.customerId} ${c.companyName}")
            c.orders.forEach { println("  Order ${it.orderId}: ${dateFmt(it.orderDate)}") }
        }
    }

    fun linq5() {
        val digits = arrayOf("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine")

        val shortDigits = digits.filterIndexed { i, it -> it.length < i }

        println("Short digits:")
        shortDigits.forEach { println("The word $it is shorter than its value.") }
    }
}