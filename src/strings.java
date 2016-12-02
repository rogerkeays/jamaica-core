package jamaica.core;

import org.testng.annotations.*;
import static org.testng.AssertJUnit.*;


public class strings {

    // is_empty
    @Test public void is_empty__is_true_for_null_value() {
        assertTrue(is_empty(null));
    }
    @Test public void is_empty__is_true_for_empty_string() {
        assertTrue(is_empty(""));
    }
    @Test public void is_empty__is_false_for_non_empty_string() {
        assertFalse(is_empty("Hello Worlds"));
    }
    @Test public void is_empty__is_false_for_a_string_containing_only_whitespace() {
        assertFalse(is_empty(" "));
    } 
    public static boolean is_empty(String s) {
        return s == null || s.length() == 0;
    }
}
