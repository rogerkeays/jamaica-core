package jamaica.core.lang;

/**
 * Backport of Java 8 Function interface.
 * 
 * @param <T> Type of the function parameter
 * @param <R> Type of the function return value
 */
public interface Function<T, R> {
    R apply(T t);
}
