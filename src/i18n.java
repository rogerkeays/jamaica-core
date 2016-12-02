package jamaica.core;

import org.testng.annotations.*;
import static jamaica.core.testing.*;

public class i18n {

    // localise
    public static String localise(String input) {
        return input;
    }


    // localise_exception
    @Test public void localise_exception__shows_the_exception_class_and_message() {
        assert_equals("NumberFormatException: foo", localise_exception(new NumberFormatException("foo")));
    }
    public static String localise_exception(Throwable t) {
        return t.getClass().getSimpleName() + ": " + t.getMessage();
    }
}
