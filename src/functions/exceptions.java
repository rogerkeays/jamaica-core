package jamaica.core.functions;

import java.io.*;
import java.util.*;
import jamaica.core.exceptions.*;
import jamaica.core.interfaces.*;
import jamaica.core.types.*;
import org.testng.annotations.*;
import static jamaica.core.functions.collections.*;
import static jamaica.core.functions.i18n.*;
import static jamaica.core.functions.lang.*;
import static jamaica.core.functions.testing.*;
import static java.text.MessageFormat.*;

public class exceptions {

    // add_tuple
    @Test public void add_tuple__creates_and_adds_a_tuple_to_an_exception_tuples_list_from_the_given_params() {
        ExceptionTuples errors = new ExceptionTuples(); 
        add_tuple(errors, new NumberFormatException(), 10);
        assert_true(errors.list.size() == 1);
        assert_true(errors.list.get(0).one instanceof NumberFormatException);
        assert_true(errors.list.get(0).two == 10);
    }
    public static Tuple<Exception, Integer> add_tuple(ExceptionTuples errors, Exception one, Integer two) {
        Tuple<Exception, Integer> tuple = new Tuple<>(one, two);
        errors.list.add(tuple);
        return tuple;
    }


    // format_line_errors
    @Test public void format_line_errors__creates_a_single_string_summarising_the_errors() {
        ExceptionTuples errors = new ExceptionTuples();
        add_tuple(errors, new NumberFormatException("foo"), 5);
        assert_equals("Line 5: NumberFormatException: foo\n", format_line_errors(errors));
    }
    @Test public void format_line_errors__accepts_a_function_for_localising_each_line() {
        ExceptionTuples errors = new ExceptionTuples();
        add_tuple(errors, new NumberFormatException("foo"), 5);
        add_tuple(errors, new NumberFormatException("bar"), 6);
        assert_equals("Line 5: FOO\nLine 6: BAR\n", 
                format_line_errors(errors, e -> e.getMessage().toUpperCase()));
    }
    @Test public void format_line_errors__falls_back_to_default_localisation_if_localisation_function_returns_null() {
        ExceptionTuples errors = new ExceptionTuples();
        add_tuple(errors, new NumberFormatException("foo"), 5);
        assert_equals("Line 5: NumberFormatException: foo\n", format_line_errors(errors, e -> null));
    }
    public static String format_line_errors(ExceptionTuples errors) {
        return format_line_errors(errors, null);
    }
    public static String format_line_errors(ExceptionTuples errors, Function<Throwable, String> localisation_function) {
        final StringBuilder result = new StringBuilder();
        for (Tuple<Exception, Integer> tuple : errors.list) {
            result.append(format(localise("Line {0}: "), tuple.two))
                .append(localisation_function == null ? localise_exception(tuple.one) :
                    coalesce(localisation_function.apply(tuple.one), localise_exception(tuple.one)))
                .append("\n");
        }
        return result.toString();
    }


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
