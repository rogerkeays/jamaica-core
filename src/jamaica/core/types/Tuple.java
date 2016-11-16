package jamaica.core.types;

public class Tuple<A, B> {
    public final A one;
    public final B two;

    public Tuple(A one, B two) {
        this.one = one;
        this.two = two;
    }
}
