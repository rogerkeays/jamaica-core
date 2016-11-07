package jamaica.core.io;

import jamaica.core.exceptions.UncheckedIOException;
import jamaica.core.testing.TestGrouper.JavaLayer;
import java.io.File;
import static java.io.File.createTempFile;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import static org.testng.AssertJUnit.assertEquals;
import org.testng.annotations.Test;

public class read_binary_file implements JavaLayer {

    /**
     * Do the dirty work to read a binary file into a byte array.
     */
    public static byte[] read_binary_file(File file) {
        byte[] buffer = new byte[(int) file.length()];
        InputStream input = null;
        try {
            input = new FileInputStream(file);
            if (input.read(buffer) == -1) {
                throw new UncheckedIOException(
                        "EOF reached while trying to read the whole file");
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } finally {
            if (input != null) {
                try { 
                    input.close();
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            }
        }
        return buffer;
    }
    public static byte[] read_binary_file(String filename) {
        return read_binary_file(new File(filename));
    }


    @Test
    public void test_can_read_binary_file_from_disk() throws Exception {
        byte[] data = new byte[] { (byte) 0x05, (byte) 0x13, (byte) 0x44, (byte) 0x99 };
        File tmp = createTempFile("test", "bin");
        FileOutputStream output = new FileOutputStream(tmp);
        output.write(data);
        output.close();
        assertEquals(data, read_binary_file(tmp));
    }
}
