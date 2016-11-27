package jamaica.core.functions;

import jamaica.core.exceptions.*;
import jamaica.core.types.*;
import jamaica.core.interfaces.*;
import java.io.*;
import org.testng.annotations.*;
import static jamaica.core.functions.exceptions.*;
import static jamaica.core.functions.i18n.*;
import static jamaica.core.functions.lang.*;
import static java.text.MessageFormat.*;
import static org.testng.Assert.*;


public class csv {

    // format_parse_errors
    @Test public void format_parse_errors__creates_a_single_string_summarising_the_errors() {
        ExceptionTuples errors = new ExceptionTuples();
        add_tuple(errors, new NumberFormatException("foo"), 5);
        assertEquals("Line 5: NumberFormatException: foo\n", format_parse_errors(errors));
    }
    @Test public void format_parse_errors__accepts_a_function_for_localising_each_line() {
        ExceptionTuples errors = new ExceptionTuples();
        add_tuple(errors, new NumberFormatException("foo"), 5);
        add_tuple(errors, new NumberFormatException("bar"), 6);
        assertEquals("Line 5: FOO\nLine 6: BAR\n", 
                format_parse_errors(errors, (e) -> e.getMessage().toUpperCase()));
    }
    @Test public void format_parse_errors__falls_back_to_default_localisation_if_localisation_function_returns_null() {
        ExceptionTuples errors = new ExceptionTuples();
        add_tuple(errors, new NumberFormatException("foo"), 5);
        assertEquals("Line 5: NumberFormatException: foo\n", format_parse_errors(errors, (e) -> null));
    }
    public static String format_parse_errors(ExceptionTuples errors) {
        return format_parse_errors(errors, null);
    }
    public static String format_parse_errors(ExceptionTuples errors, Function<Throwable, String> localisation_function) {
        final StringBuilder result = new StringBuilder();
        for (Tuple<Exception, Integer> tuple : errors.list) {
            result.append(format(localise("Line {0}: "), tuple.two))
                .append(localisation_function == null ? localise_exception(tuple.one) :
                    coalesce(localisation_function.apply(tuple.one), localise_exception(tuple.one)))
                .append("\n");
        }
        return result.toString();
    }
}
