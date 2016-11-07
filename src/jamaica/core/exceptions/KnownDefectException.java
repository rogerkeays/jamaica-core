package jamaica.core.exceptions;

import org.testng.SkipException;


/**
 * A SkipException which should be thrown when a test is known to fail. Use
 * the exception message to describe why the defect won't be fixed.
 */
public class KnownDefectException extends SkipException {
    public KnownDefectException(String message) {
        super(message);
    }
}
