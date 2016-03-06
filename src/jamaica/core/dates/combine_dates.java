package jamaica.core.dates;

import jamaica.core.testing.TestGrouper.JavaLayer;
import java.util.Date;
import java.util.Calendar;
import java.util.TimeZone;

public class combine_dates implements JavaLayer {

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
}
