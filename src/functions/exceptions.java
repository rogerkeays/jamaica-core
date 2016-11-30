package jamaica.core.functions;

import java.io.*;
import java.util.*;
import jamaica.core.exceptions.*;
import jamaica.core.interfaces.*;
import jamaica.core.types.*;
import org.testng.annotations.*;
import static jamaica.core.functions.collections.*;
import static jamaica.core.functions.io.*;
import static jamaica.core.functions.i18n.*;
import static jamaica.core.functions.lang.*;
import static jamaica.core.functions.testing.*;
import static java.text.MessageFormat.*;

public class exceptions {

    // get_root_cause
    @Test public void get_root_cause__returns_the_cause_used_to_instantiate_an_exception() {
        Throwable a = new Throwable();
        assert_equals(a, get_root_cause(new Throwable(a)));
    }
    public static Throwable get_root_cause(Throwable t) {
        Throwable result = t;
        while (result.getCause() != null) {
            result = result.getCause();
        }
        return result;
    }


    // localise_exception
    @Test public void localise_exception__shows_the_exception_class_and_message() {
        assert_equals("NumberFormatException: foo", localise_exception(new NumberFormatException("foo")));
    }
    public static String localise_exception(Throwable t) {
        return t.getClass().getSimpleName() + ": " + t.getMessage();
    }


    // reduce_exception
    @Test public void reduce_exception__groups_exceptions_by_class() {
        ReducedExceptions result = new ReducedExceptions();
        result = reduce_exception(result, new IllegalArgumentException(), 1);
        result = reduce_exception(result, new IllegalArgumentException(), 2);
        result = reduce_exception(result, new IllegalArgumentException(), 3);
        assert result.getLocationMap().size() == 1 : "expected one exception type in the map";
        assert result.getLocationMap().containsKey(IllegalArgumentException.class);
    }
    @Test public void reduce_exception__retains_the_location_of_the_original_exceptions() {
        ReducedExceptions result = new ReducedExceptions();
        result = reduce_exception(result, new IllegalArgumentException(), 1);
        result = reduce_exception(result, new IllegalArgumentException(), 2);
        result = reduce_exception(result, new IllegalArgumentException(), 3);
        assert elements_match(result.getLocationMap().get(IllegalArgumentException.class), as_list(1, 2, 3))
                : "expected the locations for the IllegalArgumentException to equal [1, 2, 3]";
    }
    public static ReducedExceptions reduce_exception(ReducedExceptions map, Exception cause, int location) {
        Class type = cause.getClass();
        Map<Class, List<Integer>> locations = map.getLocationMap();
        if (!locations.containsKey(type)) {
            locations.put(type, new LinkedList());
        }
        locations.get(type).add(location);
        return map;
    }
}
