package jamaica.core.dates;

import static jamaica.core.dates.TimeUnits.DAY_START_HOUR;
import jamaica.core.testing.TestGrouper.JavaLayer;
import java.util.Date;
import java.util.Calendar;
import java.util.TimeZone;

public class round_date_down implements JavaLayer {

    /**
     * Round a date down to the previous date boundary as determined by
     * DAY_START_HOUR.
     */
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
    public static Date round_date_down(Date date) {
        return round_date_down(date, TimeZone.getDefault());
    }
}
