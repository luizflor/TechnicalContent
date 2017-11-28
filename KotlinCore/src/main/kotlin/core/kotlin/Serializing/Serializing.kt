package core.kotlin.Serializing

import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.nio.file.Files
import java.nio.file.Paths

fun main(args: Array<String>) {
    val acct = BankAccount("1234", 500)

//    acct.deposit(250)
//    saveAccount(acct,"account.dat")
    var ba = loadAccount("account.dat")
//    var ba=loadAccountCustom("account.dat")
    var baCustom = loadAccount("account.dat")
    println("Balance: ${ba?.getBalance()} last: ${ba?.lastTxAmount}")
}

fun saveAccount(acct: BankAccount, filename: String) {
    try {
        ObjectOutputStream(Files.newOutputStream(Paths.get(filename)))
                .use { it.writeObject(acct) }
    } catch (e: IOException){
        e.printStackTrace()
    }
}

fun loadAccount(filename: String):BankAccountCustom?{
    var ba: BankAccountCustom? =null
    try {
        ObjectInputStream(Files.newInputStream(Paths.get(filename)))
                .use { ba= it.readObject() as BankAccountCustom }
    }
    catch (e: IOException){
        e.printStackTrace()
    }
    catch (e: ClassNotFoundException){
        e.printStackTrace()
    }
    return ba
}

//fun loadAccountCustom(filename: String):BankAccount?{
//    var ba: BankAccount? =null
//    try {
//        ObjectInputStream(Files.newInputStream(Paths.get(filename)))
//                .use {
//                    val fields = it.readFields()
//                    val id=fields.get("id","")
//                    val balance=fields.get("balance",0)
//                    val lastTxAmount = fields.get("lastTxAmount",0)
//                    ba = BankAccount(id.toString(),balance)
//                    ba!!.lastTxAmount = 9
//                }
//    }
//    catch (e: IOException){
//        e.printStackTrace()
//    }
//    catch (e: ClassNotFoundException){
//        e.printStackTrace()
//    }
//    return ba
//}
