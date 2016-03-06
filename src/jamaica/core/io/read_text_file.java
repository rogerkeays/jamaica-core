package jamaica.core.io;

import jamaica.core.testing.TestGrouper.JavaLayer;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;
import org.testng.annotations.Test;
import static java.io.File.createTempFile;
import static org.testng.AssertJUnit.assertEquals;

public class read_text_file implements JavaLayer {

    /**
     * Reads an entire UTF-8 encoded text file into a string.
     */
    public static String read_text_file(File file) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(file).useDelimiter("\\Z");
            return scanner.next();
        } catch (IOException ioe) {
            throw new UncheckedIOException(ioe);
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }
    public static String read_text_file(String filename) {
        return read_text_file(new File(filename));
    }


    @Test
    public void test_can_read_file_from_tmp_dir() throws IOException {
        File file = createTempFile("tmp", "txt");
        file.deleteOnExit();
        Writer writer = new FileWriter(file);
        writer.write("Hello World");
        writer.close();
        assertEquals(read_text_file(file.getAbsolutePath()), "Hello World");
    }
}
