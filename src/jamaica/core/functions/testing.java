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

}
