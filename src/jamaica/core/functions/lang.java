package jamaica.core.functions;

import jamaica.core.interfaces.*;
import org.testng.annotations.*;
import static java.lang.System.*;
import static org.testng.Assert.*;

public class lang {

    // if_null
    @Test public void if_null__returns_the_first_parameter_if_it_is_not_null() {
        String notNull = "Hello";
        assertEquals(if_null(notNull, () -> "World"), "Hello");
    }
    @Test public void if_null__executes_the_code_block_if_the_first_parameter_is_null() {
        String notNull = null;
        assertEquals(if_null(notNull, () -> "World"), "World");
    }
    public static <T> T if_null(T object, Supplier<T> block) {
        return object != null ? object : block.get();
    }


    // parse_double
    @Test public void parse_double__parses_a_string_value_into_the_equivalent_double() {
        assertEquals(parse_double("5.0"), 5.0);
    }
    public static double parse_double(String value) {
        return Double.valueOf(value);
    }


    // sleep
    @Test public void sleep__causes_the_thread_to_pause() {
        long start = currentTimeMillis();
        sleep(1);
        assertTrue(currentTimeMillis() > start);
    }
    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
