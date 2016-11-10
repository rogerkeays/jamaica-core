package jamaica.core.functions;

import java.util.*;
import org.testng.annotations.*;
import static org.testng.Assert.*;
import static jamaica.core.functions.collections.*;

public class testing {

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


    // assert_elements_match
    @Test(expectedExceptions=AssertionError.class)
    public void assert_elements_match__throws_an_assertion_error_if_the_lists_do_not_contain_the_same_elements() {
        assert_elements_match(as_list(1, 2, 3), as_list(1, 2, 4));
    }
    @Test(expectedExceptions=AssertionError.class)
    public void assert_elements_match__throws_an_assertion_error_if_the_lists_are_not_the_same_size() {
        assert_elements_match(as_list(1, 2, 3), as_list(1, 2));
    }
    @Test public void assert_elements_match__passes_if_the_elements_of_the_list_are_equal() {
        assert_elements_match(as_list(1, 2, 3), as_list(1, 2, 3));
    }
    public static void assert_elements_match(List a, List b) {
        if (a.size() != b.size()) {
            fail("lists are different size (" + a.size() + "/" + b.size());
        } else {
            for (int i = 0; i < a.size(); i++) {
                assertEquals(a.get(i), b.get(i));
            }
        }
    }
}
