package jamaica.core.interfaces;

/**
 * Backport of Java 8 BiFunction interface.
 * 
 * @param <T> Type of the function parameter
 * @param <U> Type of the function parameter
 * @param <R> Type of the function return value
 */
public interface BiFunction<T, U, R> {
    R apply(T t, U u);
}
