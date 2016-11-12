package jamaica.core.functions;

import java.util.*;
import jamaica.core.exceptions.*;
import org.testng.annotations.*;
import static jamaica.core.functions.collections.*;

public class exceptions {

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
}
