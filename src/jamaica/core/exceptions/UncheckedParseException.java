package jamaica.core.exceptions;

import java.text.ParseException;

public class UncheckedParseException extends RuntimeException {

    public UncheckedParseException(String message, ParseException cause) {
        super(message, cause);
    }
    public UncheckedParseException(ParseException cause) {
        super(cause);
    }
    public UncheckedParseException(String message) {
        super(message);
    }
}
