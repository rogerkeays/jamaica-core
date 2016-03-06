package jamaica.core.dates;

import static jamaica.core.dates.TimeUnits.DAY_START_HOUR;
import jamaica.core.testing.TestGrouper.JavaLayer;
import java.util.Date;
import java.util.Calendar;
import java.util.TimeZone;

public class round_date_up implements JavaLayer {

    /**
     * Round a date up to the next date boundary as determined by
     * DAY_START_HOUR. The result is one second before the actual
     * boundary, so the logical day does not change.
     */
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
    public static Date round_date_up(Date date) {
        return round_date_up(date, TimeZone.getDefault());
    }
}
