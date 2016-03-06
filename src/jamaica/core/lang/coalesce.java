package jamaica.core.lang;

import jamaica.core.testing.TestGrouper.JavaLayer;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;

public class coalesce implements JavaLayer {

    /** 
     * Java equivalent of the sql COALESCE(value, default) function. Returns
     * the first value if it is not null (or an empty string). Otherwise
     * returns the second value.
     */
    public static <T extends Object> T coalesce(T $1, T $2) {
        return $1 == null || "".equals($1) ? $2 : $1;
    }


    @Test
    public void test_first_object_is_returned_if_not_null() {
        String $1 = "String";
        String $2 = "Alternative";
        assertEquals(coalesce($1, $2), $1);
    }

    @Test
    public void test_second_object_is_returned_if_first_is_null() {
        String $1 = null;
        String $2 = "Alternative";
        assertEquals(coalesce($1, $2), $2);
    }

    @Test
    public void test_if_first_value_is_an_empty_string_the_second_value_is_returned() {
        String $1 = "";
        String $2 = "Alternative";
        assertEquals(coalesce($1, $2), $2);
    }
}
