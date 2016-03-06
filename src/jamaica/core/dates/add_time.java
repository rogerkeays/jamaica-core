package jamaica.core.dates;

import jamaica.core.testing.TestGrouper.JavaLayer;
import static java.lang.System.currentTimeMillis;

import java.util.Date;

public class add_time implements JavaLayer {

    /**
     * Create a new date with the given offset from the current moment.
     */
    public static Date add_time(double amount, long units) {
        return new Date(currentTimeMillis() + (long) (amount * units));
    }
}
