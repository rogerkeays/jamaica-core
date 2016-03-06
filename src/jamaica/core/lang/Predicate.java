package jamaica.core.lang;

/**
 * Backport of the Java 8 Predicate interface.
 * 
 * @param <T> Type of the predicate parameter.
 */
public interface Predicate<T> {
    boolean apply(T t);
}
