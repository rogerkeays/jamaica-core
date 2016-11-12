package jamaica.core.exceptions;

import java.util.*;

/*
 * A collection of exception types and the locations they occurred at.
 */
public class ReducedExceptions extends RuntimeException {
    public ReducedExceptions() {}

    Map<Class, List<Integer>> locations = new HashMap<Class, List<Integer>>();
    public Map<Class, List<Integer>> getLocationMap() {
        return locations;
    }
}
