package core.kotlin

interface TaskWorker{
    fun setTarget(target: Any)
    fun doWork()
}
class AccountWorker(var ba: BankAccount): Runnable, TaskWorker {
    override fun doWork() {
        val o = if (HighVolumeAccount::class.isInstance(ba)) ba as HighVolumeAccount else this
        val t = Thread(o)
        t.start()
    }

    override fun setTarget(target: Any) {
        if(BankAccount::class.isInstance(target))
            ba = target as BankAccount
        else
            throw IllegalArgumentException("Not BankAccount")
    }

    override fun run() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

//    fun doWork() {
//        val t = Thread(hva ?: this)
//        t.start()
//    }
}