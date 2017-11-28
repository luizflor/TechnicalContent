package core.kotlin.Linq

import core.kotlin.Linq.support.init
import java.lang.reflect.InvocationTargetException
import javax.management.relation.RoleUnresolved

fun main(args: Array<String>) {
    init()
    println("101 Kotlin LINQ Examples")
    println("========================\n")
//    Run(Restrictions())
//    Run(Projections())
//    Run(Quantifiers())
//    Run(QueryExecution())
//    Run(SetOperators())
//    Run(Partitioning())
//    Run(Ordering())
//    Run(MiscOperators())
//    Run(JoinOperators())
//    Run(Grouping())
//    Run(ElementOperators())
//    Run(Conversion())
    Run(AggregateOperators())
}
internal fun Run(linqExamples: Any) {
    val cls = linqExamples.javaClass
    val methods = cls.methods
    for (method in methods) {
        if (method.declaringClass != cls || method.parameterTypes.size != 0)
            continue

        println("\n# " + method.name.toUpperCase())
        try {
            method.invoke(linqExamples)
        } catch (e: IllegalAccessException) {
            println(e.cause.toString())
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            println(e.cause.toString())
            e.printStackTrace()
        }

    }

}