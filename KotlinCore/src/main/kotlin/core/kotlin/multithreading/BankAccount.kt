package core.kotlin.multithreading

class BankAccount(private var balance:Int){
    @Synchronized
    fun getBalance():Int{
        return balance
    }
    @Synchronized
    fun deposit(amount:Int){
        balance += amount
    }
    @Synchronized
    fun withdrawal(amount:Int){
        balance -= amount
    }
}