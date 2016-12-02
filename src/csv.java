package jamaica.core;

import java.io.*;
import java.util.*;
import org.testng.annotations.*;
import static jamaica.core.collections.*;
import static jamaica.core.io.*;
import static jamaica.core.i18n.*;
import static jamaica.core.lang.*;
import static jamaica.core.testing.*;
import static java.text.MessageFormat.*;

public class csv {

    // data structures
    public static class LineExceptions extends RuntimeException {
        final public List<LineException> list = new LinkedList<>();
    }
    public static class LineException {
        final public int line;
        final public Throwable exception;
        public LineException(int line, Throwable exception) {
            this.line = line;
            this.exception = exception;
        }
    }
    public class MissingFieldsException extends RuntimeException {
        public final int required;
        public final int actual;
        public MissingFieldsException(int required, int actual) {
            this.required = required;
            this.actual = actual;
        }
    }


    // add_line_exception
    @Test public void add_line_exception__creates_and_adds_a_line_exception_to_the_list_using_the_given_params() {
        LineExceptions errors = add_line_exception(10, new NumberFormatException(), new LineExceptions());
        assert_true(errors.list.size() == 1);
        assert_true(errors.list.get(0).line == 10);
        assert_true(errors.list.get(0).exception instanceof NumberFormatException);
    }
    public static LineExceptions add_line_exception(int line, Throwable exception, LineExceptions errors) {
        errors.list.add(new LineException(line, exception));
        return errors;
    }


    // format_line_errors
    @Test public void format_line_errors__creates_a_single_string_summarising_the_errors() {
        assert_equals("Line 5: NumberFormatException: foo\n", format_line_errors(
                add_line_exception(5, new NumberFormatException("foo"), new LineExceptions())));
    }
    @Test public void format_line_errors__accepts_a_function_for_localising_each_line() {
        assert_equals("Line 5: FOO\nLine 6: BAR\n", format_line_errors(
                add_line_exception(6, new NumberFormatException("bar"),
                add_line_exception(5, new NumberFormatException("foo"), new LineExceptions())),
                e -> e.getMessage().toUpperCase()));
    }
    @Test public void format_line_errors__falls_back_to_default_localisation_if_localisation_function_returns_null() {
        assert_equals("Line 5: NumberFormatException: foo\n", format_line_errors(
                add_line_exception(5, new NumberFormatException("foo"), new LineExceptions()),
                e -> null));
    }
    public static String format_line_errors(LineExceptions errors) {
        return format_line_errors(errors, null);
    }
    public static String format_line_errors(LineExceptions errors, Function<Throwable, String> localisation_function) {
        final StringBuilder result = new StringBuilder();
        for (LineException tuple : errors.list) {
            result.append(format(localise("Line {0}: "), tuple.line))
                .append(localisation_function == null ? localise_exception(tuple.exception) :
                    coalesce(localisation_function.apply(tuple.exception), localise_exception(tuple.exception)))
                .append("\n");
        }
        return result.toString();
    }


    // process_lines
    @Test public void process_lines__executes_the_processing_function_on_each_line_in_the_file() {
        List<String> lines = new LinkedList<>();
        process_lines(write_tmp_file("1\n2\n3\n4"), line -> lines.add(line));
        assert_equals(4, lines.size());
    }
    @Test public void process_lines__collects_processing_exceptions_using_an_exception_tuples() {
        try {
            process_lines(write_tmp_file("1\n2\n3\n4"), line -> { throw new NumberFormatException(line); });
            fail("expected exception tuples");
        } catch (LineExceptions errors) {
            assert_equals(4, errors.list.size());
        }
    }
    public static File process_lines(File file, Consumer<String> processing_function) {
        BufferedReader reader = null;
        try {
            int i = 1;
            reader = new BufferedReader(new FileReader(file));
            final LineExceptions errors = new LineExceptions();
            for (String line; (line = reader.readLine()) != null; i++) {
                try {
                    processing_function.accept(line);
                } catch (Exception e) {
                    errors.list.add(new LineException(i, e));
                }
            }
            if (errors.list.isEmpty()) {
                return file;
            } else {
                throw errors;
            }
        } catch (FileNotFoundException e) {
            throw checked(e); 
        } catch (IOException e) {
            throw checked(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw checked(e);
                }
            }
        }
    }
}

