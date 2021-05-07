import org.apache.logging.log4j.ThreadContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.UnknownHostException;
import java.util.concurrent.ThreadLocalRandom;

/**
 * LOG4J2 documentation https://logging.apache.org/log4j/2.x/manual/layouts.html#JSONLayout
 */
public class App {

    private static final Logger logger = LogManager.getLogger(App.class);

    public static void main(String[] args) throws UnknownHostException {

        Integer t1 = ThreadLocalRandom.current().nextInt(10000, 999999);
        Integer t2 = ThreadLocalRandom.current().nextInt(10000, 999999);
        Integer t3 = ThreadLocalRandom.current().nextInt(10000, 999999);
        Integer t4 = ThreadLocalRandom.current().nextInt(10000, 999999);
        ThreadContext.put("trackingId", t1.toString());
        logger.debug("Hello from Log4j 2");

        ThreadContext.put("trackingId", t2.toString());
        logger.debug("This is a Debug Message!");

        ThreadContext.put("trackingId", t3.toString());
        logger.info("This is an Info Message!");
        try {
            System.out.println(100/0);
        }
        catch(Exception e) {
            // to print stacktrace
            ThreadContext.put("trackingId", t4.toString());
            logger.error("Error Occurred", e);
        }
    }
}