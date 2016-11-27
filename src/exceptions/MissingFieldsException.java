package jamaica.core.exceptions;

public class MissingFieldsException extends RuntimeException {
    public final int required;
    public final int actual;

    public MissingFieldsException(int required, int actual) {
        this.required = required;
        this.actual = actual;
    }
}
