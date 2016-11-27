package jamaica.core.lang;

import jamaica.core.testing.TestGrouper.JavaLayer;
import org.testng.annotations.Test;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;

public class array_contains implements JavaLayer {

    /**
     * Efficiently test if an array contains the given value.
     */
    public static <T> boolean array_contains(final T[] array, 
            final T value) {
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
    public static boolean array_contains(final int[] array, final int value) {
        for (final int element : array) {
            if (element == value) {
                return true;
            }
        }
        return false;
    }

    @Test
    public void test_matching_object_value_returns_true() {
        assertTrue(array_contains(new String[] { "A", "B", "C", "D" }, "A"));
        assertTrue(array_contains(new String[] { "A", "B", "C", "D" }, "B"));
        assertTrue(array_contains(new String[] { "A", "B", "C", "D" }, "C"));
        assertTrue(array_contains(new String[] { "A", "B", "C", "D" }, "D"));
    }

    @Test
    public void test_missing_object_value_returns_false() {
        assertFalse(array_contains(new String[] { "A", "B", "C", "D" }, "E"));
        assertFalse(array_contains(new String[] { "A", "B", "C", "D" }, "F"));
        assertFalse(array_contains(new String[] { "A", "B", "C", "D" }, "G"));
    }

    @Test
    public void test_matching_null_value_returns_false() {
        assertTrue(array_contains(new String[] { "A", "B", "C", null }, null));
    }

    @Test
    public void test_missing_null_value_returns_false() {
        assertFalse(array_contains(new String[] { "A", "B", "C", "D" }, null));
    }

    @Test
    public void test_matching_int_value_returns_true() {
        assertTrue(array_contains(new int[] { 1, 2, 3, 4 }, 1));
        assertTrue(array_contains(new int[] { 1, 2, 3, 4 }, 2));
        assertTrue(array_contains(new int[] { 1, 2, 3, 4 }, 3));
        assertTrue(array_contains(new int[] { 1, 2, 3, 4 }, 4));
    }

    @Test
    public void test_missing_int_value_returns_false() {
        assertFalse(array_contains(new int[] { 1, 2, 3, 4 }, 5));
        assertFalse(array_contains(new int[] { 1, 2, 3, 4 }, 6));
        assertFalse(array_contains(new int[] { 1, 2, 3, 4 }, 7));
    }
}
