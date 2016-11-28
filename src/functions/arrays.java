package jamaica.core.functions;

import org.testng.annotations.*;
import static jamaica.core.functions.lang.*;
import static jamaica.core.functions.testing.*;

public class arrays {

    // arrays_match
    @Test public void arrays_match__returns_true_if_the_elements_of_two_byte_arrays_equals() {
        assert_true(arrays_match(new byte[] { b(1), b(2), b(3) }, new byte[] { b(1), b(2), b(3) }));
    }
    @Test public void arrays_match__returns_false_if_an_element_of_two_byte_arrays_is_mismatched() {
        assert_true(not(arrays_match(new byte[] { b(1), b(2), b(3) }, new byte[] { b(1), b(2), b(4) })));
    }
    @Test public void arrays_match__returns_false_if_two_byte_arrays_are_not_the_same_size() {
        assert_true(not(arrays_match(new byte[] { b(1), b(2), b(3) }, new byte[] { b(1), b(2) })));
    }
    public static boolean arrays_match(byte[] a, byte[] b) {
        if (a.length != b.length) {
            return false;
        } else {
            for (int i = 0; i < a.length; i++) {
                if (a[i] != b[i]) {
                    return false;
                }
            }
            return true;
        }
    }


    // arrays_match
    @Test public void arrays_match__returns_true_if_the_elements_of_the_array_equals() {
        assert_true(arrays_match(new Integer[] { 1, 2, 3 }, new Integer[] { 1, 2, 3 }));
    }
    @Test public void arrays_match__returns_false_if_the_arrays_do_not_contain_the_same_elements() {
        assert_true(not(arrays_match(new Integer[] { 1, 2, 3 }, new Integer[] { 1, 2, 4 })));
    }
    @Test public void arrays_match__returns_false_if_the_arrays_are_not_the_same_size() {
        assert_true(not(arrays_match(new Integer[] { 1, 2, 3 }, new Integer[] { 1, 2 })));
    }
    public static <T> boolean arrays_match(T[] a, T[] b) {
        if (a.length != b.length) {
            return false;
        } else {
            for (int i = 0; i < a.length; i++) {
                if (not(a[i].equals(b[i]))) {
                    return false;
                }
            }
            return true;
        }
    }
}

