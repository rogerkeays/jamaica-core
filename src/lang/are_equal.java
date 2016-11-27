package jamaica.core.lang;

public class are_equal {

    /**
     * Compare two objects for equality, handling nulls. If both are null
     * they are considered equal.
     */
    public static boolean are_equal(Object o1, Object o2) {
        if (o1 == null && o2 == null) {
            return true;
        } else if (o1 == null && o2 != null) {
            return false;
        } else if (o2 == null && o1 != null) {
            return false;
        } else {
            return o1.equals(o2);
        }
    }
}
