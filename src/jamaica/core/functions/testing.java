package jamaica.core.testing;

import jamaica.core.testing.TestGrouper.JavaLayer;
import org.testng.annotations.Test;
import static org.testng.AssertJUnit.fail;

public class testing implements JavaLayer {

    // assert_contains
    @Test public void assert_contains__does_nothing_if_the_haystack_contains_the_needle() {
        assert_contains("hay", "haystack");
    }
    @Test public void assert_contains__throws_an_assertion_error_if_the_haystack_does_not_contain_the_needle() {
        try {
            assert_contains("haystack", "hay");
            fail("expected and AssertionError");
        } catch (AssertionError e) {}
    }
    public static void assert_contains(String needle, String haystack) {
        if (!haystack.contains(needle)) {
            fail(needle + " not found in " + haystack);
        }
    }
}
