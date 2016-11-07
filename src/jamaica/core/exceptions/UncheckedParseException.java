package jamaica.core.exceptions;

public class UncheckedParseException extends RuntimeException {

    public UncheckedParseException(String message, Exception cause) {
        super(message, cause);
    }
    public UncheckedParseException(Exception cause) {
        super(cause);
    }
    public UncheckedParseException(String message) {
        super(message);
    }
}
