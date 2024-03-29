package jamaica.core;

import java.io.*;
import java.util.*;
import org.testng.annotations.*;
import static jamaica.core.arrays.*;
import static jamaica.core.lang.*;
import static jamaica.core.strings.*;
import static jamaica.core.testing.*;
import static java.io.File.*;

public class io {

    // get_lines
    @Test public void get_lines__throws_an_io_exception_if_the_file_doesnt_exist() {
        assert_throws(IOException.class, () -> get_lines("this file doesn't exist"));
    }
    @Test public void get_lines__parses_an_empty_file_to_an_empty_array() {
        assert_equals(get_lines(write_tmp_file("")).length, 0);
    }
    @Test public void get_lines__parses_a_single_line_file_to_a_one_element_array() {
        File tmp = write_tmp_file("Hello");
        String[] lines = get_lines(tmp);
        assert_equals(lines.length, 1);
        assert_equals(lines[0], "Hello");
    }
    @Test public void get_lines__parses_a_multi_line_file_to_a_multi_element_array() {
        File tmp = write_tmp_file("Hello\nWorld");
        String[] lines = get_lines(tmp);
        assert_equals(lines.length, 2);
        assert_equals(lines[0], "Hello");
        assert_equals(lines[1], "World");
    }
    @Test public void get_lines__parses_windows_line_feeds() {
        File tmp = write_tmp_file("Hello\r\nWorld");
        String[] lines = get_lines(tmp);
        assert_equals(lines.length, 2);
        assert_equals(lines[0], "Hello");
        assert_equals(lines[1], "World");
    }
    @Test public void get_lines__ignores_file_empty_lines() {
        File tmp = write_tmp_file("Hello\nWorld\n");
        String[] lines = get_lines(tmp);
        assert_equals(lines.length, 2);
    }
    public static String[] get_lines(String path) {
        return get_lines(new File(path));
    }
    public static String[] get_lines(File file) {
        String content = read_text_file(file);
        return is_empty(content) ? new String[] {} : content.split("[\n\r]+");
    }


    // read_binary_file
    @Test public void read_binary_file__can_read_binary_file_from_disk() throws Exception {
        byte[] data = new byte[] { (byte) 0x05, (byte) 0x13, (byte) 0x44, (byte) 0x99 };
        File tmp = createTempFile("test", "bin");
        FileOutputStream output = new FileOutputStream(tmp);
        output.write(data);
        output.close();
        assert_true(arrays_match(data, read_binary_file(tmp)));
    }
    public static byte[] read_binary_file(String filename) {
        return read_binary_file(new File(filename));
    }
    public static byte[] read_binary_file(File file) {
        byte[] buffer = new byte[(int) file.length()];
        InputStream input = null;
        try {
            input = new FileInputStream(file);
            if (input.read(buffer) == -1) {
                throw checked(new IOException("couldn't read the whole file"));
            }
        } catch (IOException e) {
            throw checked(e);
        } finally {
            if (input != null) {
                try { 
                    input.close();
                } catch (IOException e) {
                    throw checked(e);
                }
            }
        }
        return buffer;
    }


    // read_text_file
    @Test public void read_text_file__can_read_a_text_file_from_tmp_dir() {
        assert_equals(read_text_file(write_tmp_file("Hello World")), "Hello World");
    }
    @Test public void read_text_file__parses_an_empty_file_into_an_empty_string() {
        assert_equals(read_text_file(write_tmp_file("")), "");
    }
    @Test public void read_text_file__reads_long_files_in_their_entirety() {
        assert_equals(read_text_file(write_tmp_file(new String(new char[129000]))).length(), 129000);
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
        } catch (FileNotFoundException e) {
            throw checked(e); 
        } catch (IOException e) {
            throw checked(e); 
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    throw checked(e);
                }
            }
        }
    }


    // write_text_file
    @Test public void write_text_file__throws_an_io_exception_if_it_cannot_write_the_file() {
        assert_throws(IOException.class, ()-> write_text_file("/hello.txt", "Hello World"));
    }
    @Test public void write_text_file__creates_a_file_on_disk() {
        File file = write_text_file(create_test_filename(), "Hello World");
        assert_true(file != null);
        assert_true(file.exists());
    }
    @Test public void write_text_file__writes_the_value_passed_as_a_parameter() {
        assert_equals(read_text_file(write_text_file(create_test_filename(), "Hello World")), "Hello World");
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
            throw checked(e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    throw checked(e);
                }
            }
        }
    }


    // write_tmp_file
    @Test public void write_tmp_file__creates_a_file_on_disk() {
        File tmp = write_tmp_file("Hello World");
        assert_true(tmp != null);
        assert_true(tmp.exists());
    }
    @Test public void write_tmp_file__writes_the_value_passed_as_a_parameter() {
        assert_equals(read_text_file(write_tmp_file("Hello World")), "Hello World");
    }
    public static File write_tmp_file(String content) {
        try {
            File tmp = File.createTempFile("tmp-", ".txt");
            tmp.deleteOnExit();
            return write_text_file(tmp, content);
        } catch (IOException e) {
            throw checked(e);
        }
    }


    // read_text_resource
    @Test public void read_text_resource__reads_a_text_resource_from_the_classpath() {
        String random = create_random_string();
        write_text_file("target/classes/TestResource.txt", random).deleteOnExit();
        assert_equals(random, read_text_resource("TestResource.txt"));
    }
    public static String read_text_resource(String resource) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream(resource))
                    .useDelimiter("\\A");
            return scanner.next();
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }
}
