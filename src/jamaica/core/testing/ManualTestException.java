package jamaica.core.testing;

import org.testng.SkipException;


/**
 * A SkipException which should be thrown when a test cannot be automated.
 * Include the manual testing instructions in the exception message.
 */
public class ManualTestException extends SkipException {
    public ManualTestException(String message) {
        super(message);
    }
}
