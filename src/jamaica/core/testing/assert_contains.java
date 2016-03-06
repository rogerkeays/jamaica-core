package jamaica.core.testing;

import jamaica.core.testing.TestGrouper.JavaLayer;
import org.testng.annotations.Test;
import static org.testng.AssertJUnit.fail;

public class assert_contains implements JavaLayer {

    /**
     * Assert that the string needle is contained within the string haystack.
     * If it is not found, an AssertionError is thrown.
     */
    public static void assert_contains(String needle, String haystack) {
        if (!haystack.contains(needle)) {
            fail(needle + " not found in " + haystack);
        }
    }


    @Test
    public void test_hay_is_contained_in_haystack() {
        assert_contains("hay", "haystack");
    }

    @Test(expectedExceptions=AssertionError.class)
    public void test_haystack_is_not_contained_in_hay() {
        assert_contains("haystack", "hay");
    }
}
