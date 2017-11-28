package core.kotlin

import java.util.concurrent.Executors

val pool = Executors.newFixedThreadPool(5)
fun main(args: Array<String>) {

}

fun startWork(workerTypeName: String, workerTarget:Any){
    val workerType = Class.forName(workerTypeName)
    val worker = workerType.newInstance() as TaskWorker
    worker.setTarget(workerTarget)
    val wh =workerType.getAnnotation(WorkHandler::class.java)
    if(wh == null){
        throw IllegalArgumentException("not threaded")
    }
    if(wh.useThreadPool){
        pool.submit(Runnable { worker.doWork() })
    }
    else
        worker.doWork()
    worker.doWork()
}