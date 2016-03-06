package jamaica.core.strings;

import jamaica.core.testing.TestGrouper.JavaLayer;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;
import org.testng.annotations.Test;

public class is_empty implements JavaLayer {
 
    /**
     * Return true is a string is empty or null.
     */
    public static boolean is_empty(String s) {
        return s == null || s.length() == 0;
    }


    @Test
    public void test_null_string_is_empty() {
        assertTrue(is_empty(null));
    }

    @Test
    public void test_empty_string_is_empty() {
        assertTrue(is_empty(""));
    }

    @Test
    public void test_no_empty_string_is_not_empty() {
        assertFalse(is_empty("Hello Worlds"));
    }

    @Test
    public void test_single_space_is_not_empty() {
        assertFalse(is_empty(" "));
    }
}
