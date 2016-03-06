package jamaica.core.dates;

import jamaica.core.testing.TestGrouper.JavaLayer;
import static jamaica.core.dates.TimeUnits.MS_PER_DAY;
import java.util.Date;

public class count_days_between implements JavaLayer {

    /**
     * Calculate the difference between two dates in days. If date2 is later 
     * than date1 the result will be positive.
     */
    public static double count_days_between(Date date1, Date date2){
        return ((double) (date2.getTime() - date1.getTime())) / MS_PER_DAY;
    }
}
