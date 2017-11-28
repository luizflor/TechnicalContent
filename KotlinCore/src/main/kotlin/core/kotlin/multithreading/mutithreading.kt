package core.kotlin.multithreading

import java.io.IOException
import java.util.concurrent.*

fun main(args: Array<String>) {
//    doSequencial()
//    doSeparateThreads()
//    doWithThreadPool()
//    doWithThreadPoolCallable()
//    UpdateBalanceAccountSingleThread()
    UpdateBalanceAccountMultiThread()
}
fun doSequencial():Unit {
    val inFiles = arrayOf("./file1.txt","./file2.txt","./file3.txt","./file4.txt","./file5.txt","./file6.txt")
    val outFiles = arrayOf("./out.file1.txt","./out.file2.txt","./out.file3.txt","./out.file4.txt","./out.file5.txt","./out.file6.txt")
    try {
        for (i in inFiles.indices) {
            val adder = Adder(inFiles[i],outFiles[i])
            adder.doAdd()
        }
    }
    catch ( e: Exception){
        e.printStackTrace()
    }
}

fun doSeparateThreads():Unit {
    val inFiles = arrayOf("./file1.txt","./file2.txt","./file3.txt","./file4.txt","./file5.txt","./file6.txt")
    val outFiles = arrayOf("./out.file1.txt","./out.file2.txt","./out.file3.txt","./out.file4.txt","./out.file5.txt","./out.file6.txt")
    var threads = mutableListOf<Thread>()
    try {
        for (i in inFiles.indices) {
            val adder = AdderRunnable(inFiles[i],outFiles[i])
            val thread = Thread(adder)
            threads.add(thread)
            thread.start()
            println("Start threading "+i)
        }
        println("All started")
        for (thread in threads)
            thread.join() //Blocks waiting for thread completion
        println("All finished")
    }
    catch ( e: Exception){
        e.printStackTrace()
    }
}

fun doWithThreadPool():Unit {
    val inFiles = arrayOf("./file1.txt","./file2.txt","./file3.txt","./file4.txt","./file5.txt","./file6.txt")
    val outFiles = arrayOf("./out.file1.txt","./out.file2.txt","./out.file3.txt","./out.file4.txt","./out.file5.txt","./out.file6.txt")
    val es = Executors.newSingleThreadExecutor()
    for (i in inFiles.indices) {
        val adder = AdderRunnable(inFiles[i], outFiles[i])
        es.submit(adder)
    }

    try {
        es.shutdown()
        es.awaitTermination(60,TimeUnit.SECONDS)
    }
    catch ( e: Exception){
        e.printStackTrace()
    }
}

fun doWithThreadPoolCallable():Unit {
    val inFiles = arrayOf("./file1.txt","./file2.txt","./file3.txt","./file4.txt","./file5.txt","./file6.txt")
    val outFiles = arrayOf("./out.file1.txt","./out.file2.txt","./out.file3.txt","./out.file4.txt","./out.file5.txt","./out.file6.txt")
    val es = Executors.newSingleThreadExecutor()
    var results = mutableListOf<Future<Int>>()
    for (i in inFiles.indices) {
        val adder = AdderReturn(inFiles[i])
        results.add(es.submit(adder))
    }

    try {
        for (r in results){
            var value = r.get() //blocks until returns a value
            println("Total: "+ value)
        }
        es.shutdown()
        es.awaitTermination(60,TimeUnit.SECONDS)
    }
    catch (e: ExecutionException){
        val adderEx = e.cause
        println(adderEx)
    }
    catch ( e: Exception){
        e.printStackTrace()
    }
}
fun UpdateBalanceAccountSingleThread() {
    val es = Executors.newFixedThreadPool(5)
    var account = BankAccount(100)
    var worker = Worker(account)
    es.submit(worker)
}

fun UpdateBalanceAccountMultiThread() {
    val es = Executors.newFixedThreadPool(5)
    var account = BankAccount(100)
    for (i in 1..1000) {
        var worker = Worker(account)
//        Thread.sleep(100)
        es.submit(worker)
    }

    es.shutdown()
    es.awaitTermination(60,TimeUnit.SECONDS)
    println("Balance: ${account.getBalance()}")
}