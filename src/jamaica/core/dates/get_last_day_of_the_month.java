package jamaica.core.dates;

import java.util.Calendar;
import org.testng.annotations.Test;
import jamaica.core.testing.TestGrouper.JavaLayer;
import static org.testng.Assert.assertEquals;

public class get_last_day_of_the_month implements JavaLayer {

    /**
     * Format a date for the last day of the given year and month. Result is
     * YYYY-MM-DD format.
     */
    public static String get_last_day_of_the_month(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.YEAR, year);
        return year + "-" + (month < 10 ? "0" : "") + month + "-" +
                calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }


    @Test
    public void test_last_day_of_month_is_correct() {
        assertEquals(get_last_day_of_the_month(2013, 3), "2013-03-31");
        assertEquals(get_last_day_of_the_month(2013, 2), "2013-02-28");
        assertEquals(get_last_day_of_the_month(2012, 2), "2012-02-29");
        assertEquals(get_last_day_of_the_month(2012, 6), "2012-06-30");
    }
}
