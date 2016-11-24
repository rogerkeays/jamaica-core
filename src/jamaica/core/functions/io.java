package jamaica.core.functions;

import java.io.*;
import java.util.*;
import org.testng.annotations.*;
import static jamaica.core.functions.exceptions.*;
import static jamaica.core.functions.lang.*;
import static jamaica.core.functions.strings.*;
import static org.testng.Assert.*;

public class io {

    // create_test_filename
    @Test public void create_test_filename__uses_the_target_directory() {
        assertTrue(create_test_filename().startsWith("target/"));
    }
    @Test public void create_test_filename__creates_unique_filenames() {
        assertNotEquals(create_test_filename(), create_test_filename());
    }
    public static String create_test_filename() {
        sleep(1); return "target/test-" + System.currentTimeMillis();
    }


    // get_lines
    @Test(expectedExceptions=IOException.class)
     public void get_lines__throws_an_io_exception_if_the_file_doesnt_exist() {
        get_lines("this file doesn't exist");
    }
    @Test public void get_lines__parses_an_empty_file_to_an_empty_array() {
        assertEquals(get_lines(write_tmp_file("")).length, 0);
    }
    @Test public void get_lines__parses_a_single_line_file_to_a_one_element_array() {
        File tmp = write_tmp_file("Hello");
        String[] lines = get_lines(tmp);
        assertEquals(lines.length, 1);
        assertEquals(lines[0], "Hello");
    }
    @Test public void get_lines__parses_a_multi_line_file_to_a_multi_element_array() {
        File tmp = write_tmp_file("Hello\nWorld");
        String[] lines = get_lines(tmp);
        assertEquals(lines.length, 2);
        assertEquals(lines[0], "Hello");
        assertEquals(lines[1], "World");
    }
    @Test public void get_lines__parses_windows_line_feeds() {
        File tmp = write_tmp_file("Hello\r\nWorld");
        String[] lines = get_lines(tmp);
        assertEquals(lines.length, 2);
        assertEquals(lines[0], "Hello");
        assertEquals(lines[1], "World");
    }
    @Test public void get_lines__ignores_file_empty_lines() {
        File tmp = write_tmp_file("Hello\nWorld\n");
        String[] lines = get_lines(tmp);
        assertEquals(lines.length, 2);
    }
    public static String[] get_lines(String path) {
        return get_lines(new File(path));
    }
    public static String[] get_lines(File file) {
        String content = read_text_file(file);
        return is_empty(content) ? new String[] {} : content.split("[\n\r]+");
    }


    // read_text_file
    @Test public void read_text_file__can_read_a_text_file_from_tmp_dir() {
        assertEquals(read_text_file(write_tmp_file("Hello World")), "Hello World");
    }
    @Test public void read_text_file__parses_an_empty_file_into_an_empty_string() {
        assertEquals(read_text_file(write_tmp_file("")), "");
    }
    @Test public void read_text_file__reads_long_files_in_their_entirety() {
        assertEquals(read_text_file(write_tmp_file(new String(new char[129000]))).length(), 129000);
    }
    public static String read_text_file(String filename) {
        return read_text_file(new File(filename));
    }
    public static String read_text_file(File file) {
        DataInputStream stream = null;
        try {
            stream = new DataInputStream(new FileInputStream(file));
            byte[] bytes = new byte[(int) file.length()];
            stream.readFully(bytes);
            return new String(bytes, "UTF-8");
        } catch (Exception e) {
            throw_checked(e); 
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    throw_checked(e);
                }
            }
        }
        return null;
    }


    // write_text_file
    @Test(expectedExceptions=IOException.class)
    public void write_text_file__throws_an_io_exception_if_it_cannot_write_the_file() {
        write_text_file("/hello.txt", "Hello World");
    }
    @Test public void write_text_file__creates_a_file_on_disk() {
        File file = write_text_file(create_test_filename(), "Hello World");
        assertNotNull(file);
        assertTrue(file.exists());
    }
    @Test public void write_text_file__writes_the_value_passed_as_a_parameter() {
        assertEquals(read_text_file(write_text_file(create_test_filename(), "Hello World")), "Hello World");
    }
    public static File write_text_file(String file, String content) {
        return write_text_file(new File(file), content);
    }
    public static File write_text_file(File file, String content) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(content);
            return file;
        } catch (IOException e) {
            throw_checked(e);
            return null;
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    throw_checked(e);
                }
            }
        }
    }


    // write_tmp_file
    @Test public void write_tmp_file__creates_a_file_on_disk() {
        File tmp = write_tmp_file("Hello World");
        assertNotNull(tmp);
        assertTrue(tmp.exists());
    }
    @Test public void write_tmp_file__writes_the_value_passed_as_a_parameter() {
        assertEquals(read_text_file(write_tmp_file("Hello World")), "Hello World");
    }
    public static File write_tmp_file(String content) {
        try {
            File tmp = File.createTempFile("tmp-", ".txt");
            tmp.deleteOnExit();
            return write_text_file(tmp, content);
        } catch (IOException e) {
            throw_checked(e);
            return null;
        }
    }
}
