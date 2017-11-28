package core.kotlin.multithreading

class TxWorker(var account: BankAccount, val txType:Char, val amount:Int): Runnable{
    override fun run() {
        if(txType == 'w')
            account.withdrawal(amount)
        else if (txType == 'd')
            account.deposit(amount)
    }
}