package jamaica.core.dates;

import jamaica.core.testing.TestGrouper.JavaLayer;
import static jamaica.core.dates.parse_iso_date.parse_iso_date;
import java.text.DateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.text.SimpleDateFormat;
import org.testng.annotations.Test;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertEquals;

public class format_iso_date implements JavaLayer {
    private static final DateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Convert a date into a string with the format "YYYY-mm-dd". The 
     * formatted date will be output in the local timezone.
     */
    public static String format_iso_date(Date date) {
        return FORMATTER.format(date);
    }


    @Test
    public void test_a_date_with_the_value_of_the_local_timezone_formats_to_1970_01_01() {
        assertEquals("1970-01-01", format_iso_date(
                new Date(-1 * TimeZone.getDefault().getOffset(0)))); 
    }

    @Test
    public void test_a_parsed_date_formats_to_its_original_value() {
        assertEquals("1970-01-01", format_iso_date(parse_iso_date("1970-01-01")));
    }
}
