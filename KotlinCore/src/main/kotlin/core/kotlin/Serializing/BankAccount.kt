package core.kotlin.Serializing

import java.io.Serializable

open class BankAccount(private val id:String, private var balance:Int) :Serializable{
    companion object {
        @JvmStatic private val serialVersionUID = 5162676932585621002
    }

    var lastTxAmount=1

    fun getId(): String{
        return id
    }
    @Synchronized
    fun getBalance():Int{
        return balance
    }
    @Synchronized
    fun deposit(amount:Int): Unit{
        balance += amount
    }
    @Synchronized
    fun withdrawal(amount:Int): Unit{
        balance -= amount
    }
}