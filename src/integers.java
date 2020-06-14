package jamaica.core;

import org.testng.annotations.*;
import static jamaica.core.contracts.*;
import static java.lang.Integer.min;

public class integers {

    @Test
    public static void parse_int_contract() {
        eq(null, parse_int(null));
        eq(null, parse_int(new Object()));
        eq(null, parse_int("foo"));
        eq(null, parse_int("foo", 5));
        eq(null, parse_int(1.0));
        eq(null, parse_int(1.5));
        eq(null, parse_int("1.0"));
        eq(null, parse_int("1.5"));
        eq(1, parse_int(new Integer(1)));
        eq(1, parse_int(1));
        eq(1, parse_int("1"));
        eq(5, parse_int(100, 5));
        eq(5, parse_int("100", 5));
    }
    public static Integer parse_int(Object o) {
        return parse_int(o, Integer.MAX_VALUE);
    }
    public static Integer parse_int(Object o, int max) {
        if (o != null) {
            if (o instanceof Integer) {
                return min((Integer) o, max);
            } else {
                try {
                    return min(Integer.valueOf(o.toString()), max);
                } catch (NumberFormatException nfe) {
                    return null;
                }
            } 
        }
        return null;
    }
}
