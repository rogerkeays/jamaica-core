package jamaica.core.exceptions;

import jamaica.core.types.*;
import java.util.*;

/*
 * A list of exception types and the locations they occurred at.
 */
public class ExceptionTuples extends RuntimeException {
    final public List<Tuple<Exception, Integer>> list = new ArrayList<>();

    public ExceptionTuples() {}
}
