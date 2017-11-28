package core.kotlin.Linq

class Conversion {

    fun linq54() {
        val doubles = doubleArrayOf(1.7, 2.3, 1.9, 4.1, 2.9)

        val sortedDoubles = doubles.sortedDescending()

        val doublesArray = sortedDoubles.toDoubleArray()

        println("Every other double from highest to lowest:")
        var d = 0
        while (d < doublesArray.size) {
            println(doublesArray[d])
            d += 2
        }
    }

    fun linq55() {
        val words = arrayOf("cherry", "apple", "blueberry")

        val sortedWords = words.sorted()
        val wordList = sortedWords.toList()

        println("The sorted word list:")
        wordList.forEach { println(it) }
    }

    fun linq56() {
        val scoreRecords = listOf(
                Pair("Alice", 50),
                Pair("Bob", 40),
                Pair("Cathy", 45)
        )

        val scoreRecordsDict = scoreRecords.toMap()

        println("Bob's score: ${scoreRecordsDict["Bob"]}")
    }

    fun linq57() {
        val numbers = arrayOf(null, 1.0, "two", 3, "four", 5, "six", 7.0)

        val doubles = numbers.filter { it is Double }

        println("Numbers stored as doubles:")
        doubles.forEach { println(it) }
    }
}