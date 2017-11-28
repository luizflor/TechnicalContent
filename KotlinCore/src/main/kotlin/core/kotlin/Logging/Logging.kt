package core.kotlin

import java.util.logging.*

fun main(args: Array<String>) {
    //sampleLog()
//    logFileHandler()
    logWithConfigurationFile()
}
fun sampleLog():Unit{
    val logger = LogManager.getLogManager().getLogger(Logger.GLOBAL_LOGGER_NAME)
    logger.level= Level.INFO
    logger.log(Level.SEVERE, "uh oh")
    logger.log(Level.INFO,"My first message")
    logger.log(Level.INFO,"Another message")
    logger.log(Level.FINE,"Hey developer")
    logger.log(Level.FINEST,"You're awesome")
    logger.log(Level.INFO, "{0} is my favorite", "Java")
    logger.log(Level.INFO, "{0} is {1} days from {2}", arrayOf("Wed", 2, "Fri"))
}

fun logFileHandler():Unit{
    val logger = Logger.getLogger("com.luiz")
    // %h - home directory
    // %g - Rotating log
    // 1000 - each about 1000 bytes
    // 4 - Rotating set of 4
    val h = FileHandler("%h/myapp_%g.log",1000,4)
    h.formatter =SimpleFormatter()
    // 1$ Date & Time
    // 2$ Class & method
    // 3$ Logger name
    // 4$ Log level
    // 5$ Message
    // 6$ Exception
    // String.Format(format, date, source, logger, level, message, thrown);
    // %1$tb %1$td, %1$tY %1$tl:%1$tM:%1$tS %1$Tp %2$s %n %4$s: %5$s %6$s%n

    // java -Djava.util.logging.SimpleFormatter.format=%5$s,%2$s,%4$s%n
    // java -Djava.util.logging.SimpleFormatter.file=log.properties
    logger.addHandler(h)
    logger.level= Level.INFO
    logger.log(Level.SEVERE, "uh oh")
    logger.log(Level.INFO,"My first message")
    logger.log(Level.INFO,"Another message")
    logger.log(Level.FINE,"Hey developer")
    logger.log(Level.FINEST,"You're awesome")
    logger.log(Level.INFO, "{0} is my favorite", "Java")
    logger.log(Level.INFO, "{0} is {1} days from {2}", arrayOf("Wed", 2, "Fri"))
}
fun logWithConfigurationFile():Unit {
    val logger = Logger.getLogger("com.luiz")
    logger.log(Level.INFO, "We’re Logging!")
}