package jamaica.core.functions;

import java.util.*;
import java.text.*;
import org.testng.annotations.*;
import static jamaica.core.functions.lang.*;
import static jamaica.core.functions.testing.*;
import static java.lang.System.currentTimeMillis;

public class dates {
    public static final int DAY_START_HOUR = 4;
    public static final long MS_PER_SECOND = 1000;
    public static final long MS_PER_MINUTE = 1000 * 60;
    public static final long MS_PER_HOUR = 1000 * 60 * 60;
    public static final long MS_PER_DAY = 1000 * 60 * 60 * 24;
    public static final long MS_PER_WEEK = 1000 * 60 * 60 * 24 * 7;
    public static final long MS_PER_MONTH = (long) (1000 * 60 * 60 * 24 * 30.4375);
    public static final long MS_PER_YEAR = (long) (1000 * 60 * 60 * 24 * 365.25);


    /**
     * Create a new date with the given offset from the current moment.
     */
    public static Date add_time(double amount, long units) {
        return new Date(currentTimeMillis() + (long) (amount * units));
    }


    /**
     * Combine a date and a time into a single Date object. If the date is
     * null the result is null, if the time is null the result is the unchanged
     * date.
     */
    public static Date combine_dates(Date date, Date time, TimeZone tz) {
        if (date == null) {
            return null;
        }
        if (time != null) {
            Calendar dcal = Calendar.getInstance(tz);
            Calendar tcal = Calendar.getInstance(tz);
            dcal.setTime(date);
            tcal.setTime(time);
            dcal.set(Calendar.HOUR_OF_DAY, tcal.get(Calendar.HOUR_OF_DAY));
            dcal.set(Calendar.MINUTE, tcal.get(Calendar.MINUTE));
            dcal.set(Calendar.SECOND, tcal.get(Calendar.SECOND));
            dcal.set(Calendar.MILLISECOND, tcal.get(Calendar.MILLISECOND));
            return dcal.getTime();
        } else {
            return date;
        }
    }
    public static Date combine_dates(Date date, Date time) {
        return combine_dates(date, time, TimeZone.getDefault());
    }


    /**
     * Calculate the difference between two dates in days. If date2 is later 
     * than date1 the result will be positive.
     */
    public static double count_days_between(Date date1, Date date2){
        return ((double) (date2.getTime() - date1.getTime())) / MS_PER_DAY;
    }


    // format_iso_date
    @Test public void format_iso_date__formats_the_date_using_the_local_timezone() {
        assert_equals("1970-01-01", format_iso_date(
                new Date(-1 * TimeZone.getDefault().getOffset(0)))); 
    }
    @Test public void format_iso_date__formats_a_parsed_date_to_its_original_value() {
        assert_equals("1970-01-01", format_iso_date(parse_iso_date("1970-01-01")));
    }
    public static String format_iso_date(Date date) {
        return FORMATTER.format(date);
    }
    private static final DateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd");


    // get_last_day_of_the_month
    @Test public void get_last_day_of_the_month__returns_an_iso_formatted_date_for_the_last_day_of_the_given_month() {
        assert_equals(get_last_day_of_the_month(2013, 3), "2013-03-31");
        assert_equals(get_last_day_of_the_month(2013, 2), "2013-02-28");
        assert_equals(get_last_day_of_the_month(2012, 2), "2012-02-29");
        assert_equals(get_last_day_of_the_month(2012, 6), "2012-06-30");
    }
    public static String get_last_day_of_the_month(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.YEAR, year);
        return year + "-" + (month < 10 ? "0" : "") + month + "-" +
                calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }


    // parse_iso_date
    @Test public void parse_iso_date__throws_a_parse_exception_for_non_iso_formatted_dates() {
        assert_throws(ParseDateException.class, ()-> parse_iso_date("now")); 
    }
    @Test public void parse_iso_date__throws_a_parse_exception_for_invalid_dates() {
        assert_throws(ParseDateException.class, ()-> parse_iso_date("2016-13-50")); 
    }
    @Test public void parse_iso_date__includes_input_string_in_parse_exceptions() {
        try {
            parse_iso_date("2016-13-50");
            fail("expected an exception");
        } catch (ParseDateException e) {
            assert_equals("2016-13-50", e.value);
        }
    }
    @Test public void parse_iso_date__creates_a_date_with_time_12_00_in_the_local_timezone() {
        Date date = parse_iso_date("1970-01-01");
        assert_equals(12, date.getHours());
        assert_equals(0, date.getMinutes());
    }
    public static Date parse_iso_date(String string) {
        try {
            PARSER.setLenient(false);
            return new Date(PARSER.parse(string).getTime() + 12 * MS_PER_HOUR);
        } catch (ParseException e) {
            throw set_value(string, set_message(e.getMessage(), new ParseDateException()));
        }
    }
    private static final DateFormat PARSER = new SimpleDateFormat("yyyy-MM-dd");
    public static class ParseDateException extends ValueException {}


    /**
     * Round a date down to the previous date boundary as determined by
     * DAY_START_HOUR.
     */
    public static Date round_date_down(Date date) {
        return round_date_down(date, TimeZone.getDefault());
    }
    public static Date round_date_down(Date date, TimeZone tz) {
        if (date == null) {
            return null;
        } else {
            Calendar cal = Calendar.getInstance(tz);
            cal.setTime(date);
            if (cal.get(Calendar.HOUR_OF_DAY) < DAY_START_HOUR) {
                cal.add(Calendar.DAY_OF_MONTH, -1);
            }
            cal.set(Calendar.HOUR_OF_DAY, DAY_START_HOUR);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            return cal.getTime();
        }
    }


    /**
     * Round a date up to the next date boundary as determined by
     * DAY_START_HOUR. The result is one second before the actual
     * boundary, so the logical day does not change.
     */
    public static Date round_date_up(Date date) {
        return round_date_up(date, TimeZone.getDefault());
    }
    public static Date round_date_up(Date date, TimeZone tz) {
        if (date == null) {
            return null;
        } else {
            Calendar cal = Calendar.getInstance(tz);
            cal.setTime(date);
            if (cal.get(Calendar.HOUR_OF_DAY) >= DAY_START_HOUR) {
                cal.add(Calendar.DAY_OF_MONTH, 1);
            }
            cal.set(Calendar.HOUR_OF_DAY, DAY_START_HOUR);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            cal.add(Calendar.MILLISECOND, -1);
            return cal.getTime();
        }
    }
}
