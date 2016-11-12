package jamaica.core.functions;

import jamaica.core.interfaces.*;
import java.util.*;
import java.lang.reflect.*;
import org.testng.annotations.*;
import static org.testng.Assert.*;
import static jamaica.core.functions.lang.*;
import static jamaica.core.functions.testing.*;

public class collections {

    // as_list
    @Test public void as_list__constructs_an_array_list_containing_each_element() {
        List<String> strings = as_list("a", "b", "c");
        assertEquals("a", strings.get(0));
        assertEquals("b", strings.get(1));
        assertEquals("c", strings.get(2));
    }
    @Test public void as_list__creates_a_genericised_list_for_primitives() {
        assertEquals(3, as_list(1, 2, 3).size());
    }
    @Test public void as_list__accepts_elements_that_are_not_of_the_same_type() {
        as_list(1, 2, "String");
    }
    public static <T> List<T> as_list(T ... elements) {
        ArrayList result = new ArrayList(elements.length);
        for (T element : elements) {
            result.add(element);
        }
        return result;
    }


    // as_set
    @Test public void as_set__creates_a_hashset_from_the_given_elements() {
        Set<Integer> set = as_set(1, 2, 3);
        assert set instanceof HashSet;
        assert set.size() == 3;
    }
    public static <T> Set<T> as_set(T ... elements) {
        HashSet<T> result = new HashSet<T>(elements.length);
        for (T element : elements) {
            result.add(element);
        }
        return result;
    }


    // elements_match
    @Test public void elements_match__returns_true_if_the_elements_of_the_list_are_equal() {
        assert elements_match(as_list(1, 2, 3), as_list(1, 2, 3));
    }
    @Test public void elements_match__returns_false_if_the_lists_do_not_contain_the_same_elements() {
        assert !elements_match(as_list(1, 2, 3), as_list(1, 2, 4));
    }
    @Test public void elements_match__returns_false_if_the_lists_are_not_the_same_size() {
        assert !elements_match(as_list(1, 2, 3), as_list(1, 2));
    }
    public static boolean elements_match(List a, List b) {
        if (a.size() != b.size()) {
            return false;
        } else {
            for (int i = 0; i < a.size(); i++) {
                if (!a.get(i).equals(b.get(i))) {
                    return false;
                }
            }
            return true;
        }
    }


    // map
    @Test public void map__applies_the_function_to_each_collection_element() {
        assert elements_match(as_list(2, 3, 4), map(e -> e + 1, as_list(1, 2, 3)));
    }
    @Test public void map__creates_a_new_list_and_leaves_the_initial_one_untouched() {
        List<String> strings = as_list("a", "b", "c");
        List<String> new_strings = map(e -> e.toUpperCase(), strings);
        assertEquals("A", new_strings.get(0));
        assertEquals("a", strings.get(0));
    }
    @Test public void map__can_create_lists_of_a_new_type() {
        assertEquals(map(e -> parse_double(e), as_list("0.1", "0.2", "0.3")).get(0), 0.1);
    }
    @Test public void map__can_be_used_on_sets() {
        Set<String> strings = map(e -> e.toUpperCase(), as_set("a", "b", "c"));
        assert strings.contains("A");
        assert strings.contains("B");
        assert strings.contains("C");
    }
    public static <T, R> Set<R> map(Function<T,R> function, Set<T> set) {
        return (Set<R>) map(function, (Collection<T>) set); 
    }
    public static <T, R> List<R> map(Function<T,R> function, List<T> list) {
        return (List<R>) map(function, (Collection<T>) list); 
    }
    public static <T, R> Collection<R> map(Function<T,R> function, Collection<T> collection) {
        try {
            Collection<R> result = collection.getClass().newInstance();
            for (T element : collection) {
                result.add(function.apply(element));
            }
            return result;
        } catch (InstantiationException e) {
            throw new IllegalArgumentException("map() couldn't fork this collection", e);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("map() couldn't fork this collection", e);
        }
    }


    // map_array
    @Test public void map_array__applies_the_function_to_each_array_element() {
        String[] strings = map_array(String.class, e -> e.toUpperCase(), new String[] { "a", "b", "c" });
        assert strings[0].equals("A");
        assert strings[1].equals("B");
        assert strings[2].equals("C");
    }
    @Test public void map_array__can_create_arrays_of_a_new_type() {
        assert map_array(Double.class, e -> parse_double(e), new String[] { "0.1", "0.2", "0.3" })[0] == 0.1;
    }
    @Test public void map_array__accepts_an_error_handling_function() {
        List<Exception> errors = new LinkedList<Exception>();
        map_array(Double.class, e -> parse_double(e), new String[] { "a", "b", "0.1" },
                (exception, index) -> errors.add(exception));
        assert errors.size() == 2 : "expected 2 exceptions during map";
    }
    public static <T, R> R[] map_array(Class resultType, Function<T,R> function, T[] array) {
        return map_array(resultType, function, array, null);
    }
    public static <T, R> R[] map_array(Class resultType, Function<T,R> function, T[] array, 
            BiConsumer<Exception, Integer> exception_handler) {
        try {
            R[] result = (R[]) Array.newInstance(resultType, array.length);
            for (int i = 0; i < array.length; i++) {
                if (exception_handler == null) {
                    result[i] = function.apply(array[i]);
                } else {
                    try {
                        result[i] = function.apply(array[i]);
                    } catch (Exception e) {
                        exception_handler.accept(e, i);
                    }
                }
            }
            return result;
        } catch (NegativeArraySizeException e) {
            throw new IllegalArgumentException("map() couldn't fork this array", e);
        }
    }
}
