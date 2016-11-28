package jamaica.core.functions;

import java.util.*;
import org.testng.annotations.*;
import static jamaica.core.functions.collections.*;
import static jamaica.core.functions.lang.*;

public class testing {

    // assert_contains
    @Test public void assert_contains__does_nothing_if_the_haystack_contains_the_needle() {
        assert_contains("hay", "haystack");
    }
    @Test public void assert_contains__throws_an_assertion_error_if_the_haystack_does_not_contain_the_needle() {
        assert_throws(AssertionError.class, ()-> assert_contains("haystack", "hay"));
    }
    public static void assert_contains(String needle, String haystack) {
        if (!haystack.contains(needle)) {
            fail(needle + " not found in " + haystack);
        }
    }

    
    // assert_equals
    @Test public void assert_equals__does_nothing_if_the_parameters_are_matching_integers() {
        assert_equals(1, 1);
    }
    @Test public void assert_equals__throws_an_assertion_error_if_the_parameters_are_mismatching_integers() {
        assert_throws(AssertionError.class, ()-> assert_equals(1, 2));
    }
    @Test public void assert_equals__does_nothing_if_the_parameters_are_matching_strings() {
        assert_equals("Hello", "Hello");
    }
    @Test public void assert_equals__throws_an_assertion_error_if_the_parameters_are_mismatching_strings() {
        assert_throws(AssertionError.class, ()-> assert_equals("Hello", "World"));
    }
    @Test public void assert_equals__provides_a_message_with_the_expected_and_actual_values() {
        try {
            assert_equals("Hello", "World");
            fail("expected an assertion error");
        } catch (AssertionError e) {
            assert_equals("EXPECTED: Hello GOT: World", e.getMessage());
        }
    }
    public static void assert_equals(Object expected, Object actual) {
        if (not(eq(expected, actual))) {
            throw new AssertionError("EXPECTED: " + expected.toString() + " GOT: " + actual.toString());
        }
    }


    // assert_throws
    @Test public void assert_throws__does_nothing_when_the_block_throws_an_exception_of_the_given_type() {
        assert_throws(NumberFormatException.class, ()-> parse_double("foo"));
    }
    @Test public void assert_throws__throws_an_assertion_error_when_the_block_does_not_throw_an_exception() {
        try {
            assert_throws(NumberFormatException.class, ()-> parse_double("1.0"));
            fail("expected an assertion error");
        } catch (AssertionError e) {}
    }
    @Test public void assert_throws__throws_an_assertion_error_when_the_block_throws_an_exception_of_the_wrong_type() {
        try {
            assert_throws(IllegalArgumentException.class, ()-> parse_double("foo"));
            fail("expected an assertion error");
        } catch (AssertionError e) {}
    }
    public static void assert_throws(Class type, Runnable block) {
        try {
            block.run();
        } catch (Throwable e) {
            if (type.isInstance(e)) {
                return;
            } else {
                throw new AssertionError("expected a " + type.getName() + " but got " + e.getClass());
            }
        }
        throw new AssertionError("expected a " + type.getName());
    }


    // assert_true
    @Test public void assert_true__does_nothing_if_the_condition_evaluates_to_true() {
        assert_true(1 == 1);
    }
    @Test public void assert_true__throws_an_assertion_error_if_the_condition_evaluates_to_false() {
        assert_throws(AssertionError.class, ()-> assert_true(1 == 2));
    }
    @Test public void assert_true__includes_the_given_message_in_the_assertion_exception_if_it_is_supplied() {
        try {
            assert_true(1 == 2, "1 is not 2");
            fail("expected an assertion error");
        } catch (AssertionError e) {
            assert_equals("1 is not 2", e.getMessage());
        }
    }
    public static void assert_true(boolean condition) {
        assert_true(condition, null);
    }
    public static void assert_true(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }


    // create_random_string
    @Test public void create_random_string__creates_two_unique_strings_when_called_twice() {
        assert_true(!create_random_string().equals(create_random_string()));
    }
    public static String create_random_string() {
        sleep(1);
        return String.valueOf(System.currentTimeMillis());
    }


    // create_test_filename
    @Test public void create_test_filename__uses_the_target_directory() {
        assert_true(create_test_filename().startsWith("target/"));
    }
    @Test public void create_test_filename__creates_unique_filenames() {
        assert_true(not(eq(create_test_filename(), create_test_filename())));
    }
    public static String create_test_filename() {
        sleep(1);
        return "target/test-" + System.currentTimeMillis();
    }


    // fail
    @Test public void fail__throws_an_assertion_error() {
        assert_throws(AssertionError.class, ()-> fail());
    }
    @Test public void fail__includes_the_given_message_in_the_exception_if_it_supplied() {
        try {
            fail("you failed");
        } catch (AssertionError e) {
            assert_equals("you failed", e.getMessage());
        }
    }
    public static void fail() {
        fail(null);
    }
    public static void fail(String message) {
        throw message == null ? new AssertionError() : new AssertionError(message);
    }
}
