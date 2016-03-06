package jamaica.core.dates;

import jamaica.core.testing.TestGrouper.JavaLayer;
import java.text.DateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import org.testng.annotations.Test;
import static org.testng.AssertJUnit.assertEquals;

public class parse_iso_date implements JavaLayer {
    private static final DateFormat PARSER = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Parse a date in the format "YYYY-mm-dd" into a Java date. The created
     * date will be 00:00 on the given day in the local timezone.
     */
    public static Date parse_iso_date(String string) {
        if (!string.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}")) {
            throw new IllegalArgumentException("Invalid date format: " + string);
        }
        try {
            return PARSER.parse(string);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + string, e);
        }
    }


    @Test(expectedExceptions=IllegalArgumentException.class)
    public void test_an_invalid_string_throws_an_illegal_argument_exception() {
        parse_iso_date("now");
    }

    @Test
    public void test_the_string_1970_01_01_creates_a_date_with_value_of_the_local_timezone() {
        assertEquals(-1 * TimeZone.getDefault().getOffset(0), 
                parse_iso_date("1970-01-01").getTime());
    }
}
