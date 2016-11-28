package jamaica.core.exceptions;

public class ParseDateException extends RuntimeException {
    public final String value;

    public ParseDateException(String value) {
        super(value);
        this.value = value;
    }
    public ParseDateException(String value, String message) {
        super(message);
        this.value = value;
    }
}
