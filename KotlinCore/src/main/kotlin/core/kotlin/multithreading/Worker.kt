package core.kotlin.multithreading
class Worker(var account: BankAccount): Runnable{
    private val lock = java.lang.Object()
    override fun run() {
//        println("ThreadID: ${Thread.currentThread().id}")
        for (i in 1..10){
            synchronized(lock){
            val startBalance = account.getBalance()
//            Thread.sleep(500)
            account.deposit(10)

            val endBalance=account.getBalance()
            }
          // println("ThreadID: ${Thread.currentThread().id} startBalance: $startBalance endBalance: $endBalance")
        }
    }
}