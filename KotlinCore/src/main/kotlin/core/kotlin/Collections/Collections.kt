package core.kotlin

import java.util.*

fun main(args: Array<String>) {

    //SimpleCollection()
//    RemoveMember()
//    RemoveIf()
//    RetrievingArray()
//    UsingMap()
    UsingTreeMap()
}
fun SimpleCollection():Unit {
    var list = mutableListOf<String>()
    list.add("Foo")
    list.add("Bar")
    println("Elements: "+list.size )
    for(l in list){
        println(l)
    }
    println("list[0]: " +list[0])
}

fun RemoveMember():Unit{
    var list = mutableListOf<MyClass>()
    val v1= MyClass("v1","abc")
    val v2= MyClass("v2","abc")
    val v3= MyClass("v3","abc")
    list.add(v1)
    list.add(v2)
    list.add(v3)
    println("before")
    list.forEach{m -> println(m.label)}
    list.remove(v3)
    println("after")
    for(l in list)
        println(l.label)
}

fun RemoveIf():Unit{
    var list = mutableListOf<MyClass>()
    val v1= MyClass("v1","abc")
    val v2= MyClass("v2","abc")
    val v3= MyClass("v3","abc")
    list.add(v1)
    list.add(v2)
    list.add(v3)
    println("before")
    list.forEach{m -> println(m.label)}
    list.removeIf{m->m.value.equals("abc")}
    println("after")
    for(l in list)
        println(l.label)
}

fun RetrievingArray():Unit{
    var list = mutableListOf<MyClass>()
    list.add(MyClass("v1","abc"))
    list.add(MyClass("v2","xyz"))
    list.add(MyClass("v3","abc"))

    var a1 = list.toTypedArray()
    for(s in a1)
        println(s.label)
    var a2 = arrayOf(MyClass("v4","abc"),
            MyClass("v5","zyx"),MyClass("v6","abc"))
    a2.forEach { x->println(x.label) }

}

fun UsingMap():Unit{
    var map = HashMap<String,String>()
    map.put("2222","ghi")
    map.put("3333","abc")
    map.put("1111","def")
    
    map.forEach { k, v ->  println(k+" | "+ v)}
    map.replaceAll { t, u -> u.toUpperCase() }
    map.forEach { k, v ->  println(k+" | "+ v)}
    
}

fun UsingTreeMap():Unit{
    var map = TreeMap<String,String>()
    map.put("2222","ghi")
    map.put("3333","abc")
    map.put("1111","def")
    map.put("6666","xyz")
    map.put("4444","mno")
    map.put("5555","pqr")

    map.forEach { k, v ->  println(k+" | "+ v)}

    println("headMap")
    var hMap = map.headMap("3333")
    hMap.forEach { k, v ->  println(k+" | "+ v)}

    println("tailMap")
    var tMap = map.tailMap("3333")
    tMap.forEach { k, v ->  println(k+" | "+ v)}

}


class MyClass(val label:String, val value: String){

    override fun equals(o: Any?): Boolean {
        var other =  o as MyClass
        return value.equals(other.value)
    }
}