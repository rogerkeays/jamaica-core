package jamaica.core;

import org.testng.annotations.*;
import static jamaica.core.lang.*;
import static jamaica.core.testing.*;

public class arrays {

    // array_contains
    @Test public void array_contains__returns_true_if_the_given_object_is_in_the_array() {
        assert_true(array_contains(new String[] { "A", "B", "C", "D" }, "A"));
        assert_true(array_contains(new String[] { "A", "B", "C", "D" }, "B"));
        assert_true(array_contains(new String[] { "A", "B", "C", "D" }, "C"));
        assert_true(array_contains(new String[] { "A", "B", "C", "D" }, "D"));
    }
    @Test public void array_contains__returns_false_if_the_given_object_is_not_in_the_array() {
        assert_true(not(array_contains(new String[] { "A", "B", "C", "D" }, "E")));
        assert_true(not(array_contains(new String[] { "A", "B", "C", "D" }, "F")));
        assert_true(not(array_contains(new String[] { "A", "B", "C", "D" }, "G")));
    }
    @Test public void array_contains__returns_true_if_the_given_object_is_null_and_null_exists_in_the_array() {
        assert_true(array_contains(new String[] { "A", "B", "C", null }, null));
    }
    @Test public void array_contains__returns_false_if_the_given_object_is_null_and_there_are_no_nulls_in_the_array() {
        assert_true(not(array_contains(new String[] { "A", "B", "C", "D" }, null)));
    }
    public static <T> boolean array_contains(final T[] array, final T value) {
        if (value == null) {
            for (final T element : array) {
                if (element == null) {
                    return true;
                }
            }
        } else {
            for (final T element : array) {
                if (element == value || value.equals(element)) {
                    return true;
                }
            }
        }
        return false;
    }


    // array_contains
    @Test public void array_contains__returns_true_if_the_given_int_is_in_the_array() {
        assert_true(array_contains(new int[] { 1, 2, 3, 4 }, 1));
        assert_true(array_contains(new int[] { 1, 2, 3, 4 }, 2));
        assert_true(array_contains(new int[] { 1, 2, 3, 4 }, 3));
        assert_true(array_contains(new int[] { 1, 2, 3, 4 }, 4));
    }
    @Test public void array_contains__returns_false_if_the_given_int_is_not_in_the_array() {
        assert_true(not(array_contains(new int[] { 1, 2, 3, 4 }, 5)));
        assert_true(not(array_contains(new int[] { 1, 2, 3, 4 }, 6)));
        assert_true(not(array_contains(new int[] { 1, 2, 3, 4 }, 7)));
    }
    public static boolean array_contains(final int[] array, final int value) {
        for (final int element : array) {
            if (element == value) {
                return true;
            }
        }
        return false;
    }


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

