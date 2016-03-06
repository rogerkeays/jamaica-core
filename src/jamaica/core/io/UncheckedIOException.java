package jamaica.core.io;

import java.io.IOException;

public class UncheckedIOException extends RuntimeException {

    public UncheckedIOException(IOException cause) {
        super(cause);
    }
    public UncheckedIOException(String message) {
        super(message);
    }
}
