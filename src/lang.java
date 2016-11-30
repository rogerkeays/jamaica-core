package jamaica.core.functions;

import java.io.*;
import org.testng.annotations.*;
import static java.lang.System.*;
import static jamaica.core.functions.testing.*;

public class lang {

    // custom exceptions
    public static class ValueException extends RuntimeException {
        public Object value;
        public String message;
    }
    public static <T extends ValueException> T set_value(Object value, T exception) {
        exception.value = value;
        return exception;
    }
    public static <T extends ValueException> T set_message(String message, T exception) {
        exception.message = message;
        return exception;
    }


    // functional interfaces
    public static interface Supplier<T> { T get(); }
    public static interface Consumer<T> { void accept(T t); }
    public static interface Predicate<T> { boolean apply(T t); }
    public static interface Function<T, R> { R apply(T t); }
    public static interface BiConsumer<T, U> { void accept(T t, U u); }
    public static interface BiFunction<T, U, R> { R apply(T t, U u); }
    public static interface TriFunction<T, U, V, R> { R apply(T t, U u, V v); }


    // b
    @Test public void b__casts_an_int_to_a_byte() {
        byte b = b(1);
        assert_equals(b, (byte) 1);
    }
    public static byte b(int i) {
        return (byte) i;
    }


    // checked
    @Test(expectedExceptions=IOException.class)
    public void checked__lets_us_throw_a_checked_exception_without_being_declared_in_the_method_body() {
        throw checked(new IOException());
    }
    public static RuntimeException checked(Exception e) {
        lang.<RuntimeException>throw_checked(e);
        return null;
    }
    private static <E extends Exception> void throw_checked(Exception e) throws E {
        throw (E) e;
    }


    // coalesce
    @Test public void coalesce__returns_the_first_object_if_is_not_null() {
        assert_equals("String", coalesce("String", "Alternative"));
    }
    @Test public void coalesce__returns_the_second_object_if_the_first_is_null() {
        assert_equals("Alternative", coalesce(null, "Alternative"));
    }
    @Test public void coalesce__returns_the_second_object_if_the_first_is_an_empty_string() {
        assert_equals("Alternative", coalesce("", "Alternative"));
    }
    public static <T extends Object> T coalesce(T $1, T $2) {
        return $1 == null || "".equals($1) ? $2 : $1;
    }


    // eq
    @Test public void eq__returns_true_if_both_parameters_are_null() {
        assert_true(eq(null, null));
    }
    @Test public void eq__returns_true_if_both_parameters_are_not_null_and_equal() {
        assert_true(eq("Hello", "Hello"));
    }
    @Test public void eq__returns_false_if_only_the_first_parameter_is_null() {
        assert_true(not(eq(null, "Hello")));
    }
    @Test public void eq__returns_false_if_only_the_second_parameter_is_null() {
        assert_true(not(eq("Hello", null)));
    }
    public static boolean eq(Object o1, Object o2) {
        if (o1 == null && o2 == null) {
            return true;
        } else if (o1 == null && o2 != null) {
            return false;
        } else if (o2 == null && o1 != null) {
            return false;
        } else {
            return o1.equals(o2);
        }
    }


    // exec
    @Test public void test_successfully_execed_command_returns_zero() {
        assert_equals(0, exec("ls"));
    }
    @Test public void test_unsuccessfully_execed_command_returns_non_zero_value() {
        assert_true(not(eq(0, exec("ls /foo/bar/doesnt/exist"))));
    }
    public static int exec(String command) {
        try {
            Process p = Runtime.getRuntime().exec(command);
            p.waitFor();
            return p.exitValue();
        } catch (IOException e) {
            throw checked(e);
        } catch (InterruptedException e) {
            throw checked(e);
        }
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


    // if_null
    @Test public void if_null__returns_the_first_parameter_if_it_is_not_null() {
        String notNull = "Hello";
        assert_equals("Hello", if_null(notNull, () -> "World"));
    }
    @Test public void if_null__executes_the_code_block_if_the_first_parameter_is_null() {
        String Null = null;
        assert_equals("World", if_null(Null, () -> "World"));
    }
    public static <T> T if_null(T object, Supplier<T> block) {
        return object != null ? object : block.get();
    }


    // not
    @Test public void not__returns_true_if_the_parameter_is_not_true() {
        assert_true(not(false));
    }
    @Test public void not__returns_false_if_the_parameter_is_true() {
        assert_true(not(not(true)));
    }
    public static boolean not(boolean condition) {
        return !condition;
    }
    

    // parse_double
    @Test public void parse_double__parses_a_string_value_into_the_equivalent_double() {
        assert_equals(parse_double("5.0"), 5.0);
    }
    @Test public void parse_double__throws_a_parse_exception_for_invalid_values() {
        assert_throws(ParseDoubleException.class, ()-> parse_double("foo")); 
    }
    @Test public void parse_double__includes_input_string_in_parse_exceptions() {
        try {
            parse_double("foo");
            fail("expected an exception");
        } catch (ParseDoubleException e) {
            assert_equals("foo", e.value);
        }
    }
    public static double parse_double(String value) {
        try {
            return Double.valueOf(value);
        } catch (NumberFormatException e) {
            throw set_value(value, set_message(e.getMessage(), new ParseDoubleException()));
        }
    }
    public static class ParseDoubleException extends ValueException {}


    // sleep
    @Test public void sleep__causes_the_thread_to_pause() {
        long start = currentTimeMillis();
        sleep(1);
        assert_true(currentTimeMillis() > start);
    }
    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw checked(e);
        }
    }
}

