package jamaica.core.functions;

import java.util.*;
import org.testng.annotations.*;
import static org.testng.Assert.*;
import static jamaica.core.functions.collections.*;
import static jamaica.core.functions.lang.*;

public class testing {
    
    // assert_that
    @Test public void assert_that__does_nothing_if_the_condition_evaluates_to_true() {
        assert_that(1 == 1);
    }
    @Test public void assert_that__throws_an_assertion_error_if_the_condition_evaluates_to_false() {
        try {
            assert_that(1 == 2);
            fail("expected an assertion error");
        } catch (AssertionError e) {}
    }
    @Test public void assert_that__includes_the_given_message_in_the_assertion_exception_if_it_is_supplied() {
        try {
            assert_that(1 == 2, "1 is not 2");
        } catch (AssertionError e) {
            assert_that(e.getMessage().equals("1 is not 2"));
        }
    }
    public static void assert_that(boolean condition) {
        assert_that(condition, null);
    }
    public static void assert_that(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }


    // assert_throws
    @Test public void assert_throws__does_nothing_when_the_block_throws_an_exception_of_the_given_type() {
        assert_throws(NumberFormatException.class, () -> parse_double("foo"));
    }
    @Test public void assert_throws__throws_an_assertion_error_when_the_block_does_not_throw_an_exception() {
        try {
            assert_throws(NumberFormatException.class, () -> parse_double("1.0"));
            fail("expected an assertion error");
        } catch (AssertionError e) {}
    }
    @Test public void assert_throws__throws_an_assertion_error_when_the_block_throws_an_exception_of_the_wrong_type() {
        try {
            assert_throws(IllegalArgumentException.class, () -> parse_double("foo"));
            fail("expected an assertion error");
        } catch (AssertionError e) {}
    }
    public static void assert_throws(Class type, Runnable block) {
        try {
            block.run();
        } catch (Exception e) {
            if (type.isInstance(e)) {
                return;
            } else {
                throw new AssertionError("expected a " + type.getName() + " but got " + e.getClass());
            }
        }
        throw new AssertionError("expected a " + type.getName());
    }


    // assert_contains
    @Test public void assert_contains__does_nothing_if_the_haystack_contains_the_needle() {
        assert_contains("hay", "haystack");
    }
    @Test(expectedExceptions=AssertionError.class)
    public void assert_contains__throws_an_assertion_error_if_the_haystack_does_not_contain_the_needle() {
        assert_contains("haystack", "hay");
    }
    public static void assert_contains(String needle, String haystack) {
        if (!haystack.contains(needle)) {
            fail(needle + " not found in " + haystack);
        }
    }


    // create_random_string
    @Test public void create_random_string__creates_two_unique_strings_when_called_twice() {
        assert_that(!create_random_string().equals(create_random_string()));
    }
    public static String create_random_string() {
        sleep(1);
        return String.valueOf(System.currentTimeMillis());
    }


    // fail
    @Test public void fail__throws_an_assertion_error() {
        try {
            fail();
            throw new RuntimeException("fail() failed!"); 
        } catch (AssertionError e) {}
    }
    @Test public void fail__includes_the_given_message_in_the_exception_if_it_supplied() {
        try {
            fail("you failed");
        } catch (AssertionError e) {
            assert_that(e.getMessage().equals("you failed"));
        }
    }
    public static void fail() {
        fail(null);
    }
    public static void fail(String message) {
        throw message == null ? new AssertionError() : new AssertionError(message);
    }
}
