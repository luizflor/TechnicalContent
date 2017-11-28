package core.kotlin.Reflection

class BankAccount(private val id:String, private var balance:Int){
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