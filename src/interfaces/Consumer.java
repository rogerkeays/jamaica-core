package jamaica.core.interfaces;

/**
 * Backport of Java 8 Consumer interface.
 * 
 * @param <T> Type of the first function parameter
 */
public interface Consumer<T> {
    void accept(T t);
}
