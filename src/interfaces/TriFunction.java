package jamaica.core.interfaces;

/**
 * @param <T> Type of the function parameter
 * @param <U> Type of the function parameter
 * @param <V> Type of the function parameter
 * @param <R> Type of the function return value
 */
public interface TriFunction<T, U, V, R> {
    R apply(T t, U u, V v);
}
