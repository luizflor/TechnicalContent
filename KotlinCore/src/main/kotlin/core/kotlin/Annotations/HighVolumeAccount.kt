package core.kotlin

open class HighVolumeAccount(val highId:String, var highBalance:Int)
    :BankAccount(id=highId,balance=highBalance), Runnable {
    override fun run() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun readDailyDeposits() {

    }

    fun readDailyWithdrawals() {

    }
}