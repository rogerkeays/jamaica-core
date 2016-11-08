package jamaica.core.interfaces;

/**
 * Backport of Java 8 Supplier interface.
 * 
 * @param <T> Type of the function return value
 */
public interface Supplier<T> {
    T get();
}
