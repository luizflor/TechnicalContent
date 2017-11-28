package core.kotlin.Reflection

import java.lang.reflect.Field
import java.lang.reflect.Method
import java.lang.reflect.Modifier

fun main(args: Array<String>) {
    showClassName()
}

fun showClassName() {
    val acct = BankAccount("1234",100)
    doWork(acct)
    callGetId(acct)
    callDeposit(acct,50)
    createInstance()
}


fun doWork(obj: Any){
    val c = obj.javaClass
    showName(c)
    val modifiers = c.modifiers
    if(Modifier.isFinal(modifiers)){
        println("method check - final")
    }
    if(Modifier.isPrivate(modifiers)){
        println("method check - private")
    }
    if(Modifier.isProtected(modifiers)){
        println("method check - protected")
    }
    if(Modifier.isPublic(modifiers)){
        println("method check - public")
    }
    fieldInfo(obj)
    methodInfo(obj)
}

fun fieldInfo(obj: Any){
    val theClass = obj.javaClass
    val fields = theClass.fields
    displayFields(fields,"fields")
    val declaredFields = theClass.declaredFields
    displayFields(declaredFields, "declaredFields")
}

fun displayFields(fields:Array<Field>, field:String){
    println("*** $field ***")
    fields.forEach { println("${it.name} : ${it.type}") }
}

fun methodInfo(obj: Any){
    val theClass = obj.javaClass
    val methods = theClass.methods
    displayMethods(methods,"methods")
    val declMethods = theClass.declaredMethods
    displayMethods(declMethods,"declMethods")
}

fun displayMethods(methods:Array<Method>, method: String){
    println("*** $method ***")
    methods.forEach {println("${it.name}") }
}

fun showName(theClass: Class<Any>) {
    println(theClass.simpleName)
}

fun callGetId(obj: Any){
    println("*** callGetId ***")
    try {
        val theClass = obj.javaClass
        val m = theClass.getMethod("getId")
        val result = m.invoke(obj)
        println("Result: $result")
    } catch (e:Exception){
        e.printStackTrace()
    }
}

fun callDeposit(obj: Any, amt:Int){
    println("*** callDeposit ***")
    try {
        val theClass = obj.javaClass
        val balance = theClass.getMethod("getBalance")
        val before = balance.invoke(obj)
        println("before: $before")

        val deposit = theClass.getMethod("deposit",Int::class.java)
        deposit.invoke(obj, amt)

        val after = balance.invoke(obj)
        println("After: $after with a deposit of $amt")
    } catch (e:Exception){
        e.printStackTrace()
    }
}

fun createInstance() {
    try {
        val theClass = Class.forName("core.kotlin.BankAccount")
        val constructor = theClass.getConstructor(String::class.java, Int::class.java)
        val obj = constructor.newInstance("1111", 500)
        val getId = theClass.getMethod("getId")
        val result = getId.invoke(obj)
        println("getId: $result")

        val balance = theClass.getMethod("getBalance")
        val before = balance.invoke(obj)
        println("before: $before")
    } catch (e: Exception) {
        e.printStackTrace()
    }
}