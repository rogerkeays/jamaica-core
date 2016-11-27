package jamaica.core.functions;

import jamaica.core.interfaces.*;
import org.testng.annotations.*;
import static java.lang.System.*;
import static jamaica.core.functions.testing.*;

public class lang {

    // are_equal
    @Test public void are_equal__returns_true_if_both_parameters_are_null() {
        assert_true(are_equal(null, null));
    }
    @Test public void are_equal__returns_true_if_both_parameters_are_not_null_and_equal() {
        assert_true(are_equal("Hello", "Hello"));
    }
    @Test public void are_equal__returns_false_if_only_the_first_parameter_is_null() {
        assert_true(not(are_equal(null, "Hello")));
    }
    @Test public void are_equal__returns_false_if_only_the_second_parameter_is_null() {
        assert_true(not(are_equal("Hello", null)));
    }
    public static boolean are_equal(Object o1, Object o2) {
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
    public static double parse_double(String value) {
        return Double.valueOf(value);
    }


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
            throw new RuntimeException(e);
        }
    }
}
