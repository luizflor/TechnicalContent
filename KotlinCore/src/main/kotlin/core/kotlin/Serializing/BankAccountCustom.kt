package core.kotlin.Serializing

import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable

open class BankAccountCustom(private var id:String, private var balance:Int) :Serializable{
    companion object {
        @JvmStatic private val serialVersionUID = 5162676932585621002
    }

    var lastTxAmount=1

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
    @Throws(IOException::class)
    private fun writeObject(out:ObjectOutputStream) {
        out.defaultWriteObject()
    }

    @Throws(IOException::class, ClassNotFoundException::class)
    private fun readObject(o:ObjectInputStream){
        val fields = o.readFields()
        id=fields.get("id","").toString()
        balance=fields.get("balance",0)
        lastTxAmount = fields.get("lastTxAmount",0)

    }
}