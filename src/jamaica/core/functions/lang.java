package jamaica.core.functions;

import org.testng.annotations.*;
import static java.lang.System.currentTimeMillis;
import static org.testng.Assert.*;

public class lang {

    // sleep
    @Test public void sleep__causes_the_thread_to_pause() {
        long start = currentTimeMillis();
        sleep(1);
        assertTrue(currentTimeMillis() > start);
    }
    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
