package jamaica.core.exceptions;

public class ParseDoubleException extends RuntimeException {
    public final String value;

    public ParseDoubleException(String value) {
        super(value);
        this.value = value;
    }
    public ParseDoubleException(String value, String message) {
        super(message);
        this.value = value;
    }
}
