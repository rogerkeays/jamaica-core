package jamaica.core.functions;

import java.io.*;
import java.util.*;
import jamaica.core.exceptions.*;
import jamaica.core.types.*;
import org.testng.annotations.*;
import static jamaica.core.functions.collections.*;
import static jamaica.core.functions.testing.*;
import static org.testng.Assert.*;

public class exceptions {

    // add_tuple
    @Test public void add_tuple__creates_and_adds_a_tuple_to_an_exception_tuples_list_from_the_given_params() {
        ExceptionTuples errors = new ExceptionTuples(); 
        add_tuple(errors, new NumberFormatException(), 10);
        assert_that(errors.list.size() == 1);
        assert_that(errors.list.get(0).one instanceof NumberFormatException);
        assert_that(errors.list.get(0).two == 10);
    }
    public static Tuple<Exception, Integer> add_tuple(ExceptionTuples errors, Exception one, Integer two) {
        Tuple<Exception, Integer> tuple = new Tuple<>(one, two);
        errors.list.add(tuple);
        return tuple;
    }


    // get_root_cause
    @Test public void get_root_cause__returns_the_cause_used_to_instantiate_an_exception() {
        Throwable a = new Throwable();
        assertEquals(get_root_cause(new Throwable(a)), a);
    }
    public static Throwable get_root_cause(Throwable t) {
        Throwable result = t;
        while (result.getCause() != null) {
            result = result.getCause();
        }
        return result;
    }


    // reduce_exception
    @Test public void reduce_exception__groups_exceptions_by_class() {
        ReducedExceptions result = new ReducedExceptions();
        result = reduce_exception(result, new IllegalArgumentException(), 1);
        result = reduce_exception(result, new IllegalArgumentException(), 2);
        result = reduce_exception(result, new IllegalArgumentException(), 3);
        assert result.getLocationMap().size() == 1 : "expected one exception type in the map";
        assert result.getLocationMap().containsKey(IllegalArgumentException.class);
    }
    @Test public void reduce_exception__retains_the_location_of_the_original_exceptions() {
        ReducedExceptions result = new ReducedExceptions();
        result = reduce_exception(result, new IllegalArgumentException(), 1);
        result = reduce_exception(result, new IllegalArgumentException(), 2);
        result = reduce_exception(result, new IllegalArgumentException(), 3);
        assert elements_match(result.getLocationMap().get(IllegalArgumentException.class), as_list(1, 2, 3))
                : "expected the locations for the IllegalArgumentException to equal [1, 2, 3]";
    }
    public static ReducedExceptions reduce_exception(ReducedExceptions map, Exception cause, int location) {
        Class type = cause.getClass();
        Map<Class, List<Integer>> locations = map.getLocationMap();
        if (!locations.containsKey(type)) {
            locations.put(type, new LinkedList());
        }
        locations.get(type).add(location);
        return map;
    }


    // throw_checked
    @Test(expectedExceptions=IOException.class)
    public void throw__checked_throws_a_checked_exception_without_being_declared_in_the_method_body() {
        throw_checked(new IOException());
    }
    public static void throw_checked(Exception e) {
        exceptions.<RuntimeException>throw_checked_hack(e);
    }
    private static <E extends Exception> void throw_checked_hack(Exception e) throws E {
        throw (E) e;
    }
}
