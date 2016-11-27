package jamaica.core.interfaces;

/**
 * Backport of Java 8 BiConsumer interface.
 * 
 * @param <T> Type of the first function parameter
 * @param <U> Type of the second function parameter
 */
public interface BiConsumer<T, U> {
    void accept(T t, U u);
}
