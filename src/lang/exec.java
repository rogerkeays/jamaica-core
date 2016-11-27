package jamaica.core.lang;

import jamaica.core.exceptions.UncheckedIOException;
import jamaica.core.testing.TestGrouper.JavaLayer;
import java.io.IOException;
import org.testng.annotations.Test;
import static org.testng.Assert.assertNotEquals;
import static org.testng.AssertJUnit.assertEquals;

public class exec implements JavaLayer {

    /**
     * Execute a system (shell) command and return the exit code. Successfully
     * completed commands normally return 0. Other values usually indicate
     * some sort of error.
     */
    public static int exec(String command) {
        try {
            Process p = Runtime.getRuntime().exec(command);
            p.waitFor();
            return p.exitValue();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void test_successfully_execed_command_returns_zero() {
        assertEquals(0, exec("ls"));
    }

    @Test
    public void test_unsuccessfully_execed_command_returns_non_zero_value() {
        assertNotEquals(0, exec("ls /foo/bar/doesnt/exist"));
    }
}
