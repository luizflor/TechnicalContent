package core.kotlin.Linq.support

import java.util.Date

class Order(var orderId: Int?, var orderDate: Date, var total: Double) {

    override fun toString(): String {
        return "(Order id=$orderId, total=$total)"
    }
}
