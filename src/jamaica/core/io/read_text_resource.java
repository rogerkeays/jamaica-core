package jamaica.core.io;

import jamaica.core.testing.TestGrouper.JavaLayer;
import static java.lang.Thread.currentThread;
import java.util.Scanner;
import org.testng.annotations.Test;
import static jamaica.core.lang.get_resource_path.get_resource_path;
import static org.testng.AssertJUnit.assertEquals;

public class read_text_resource implements JavaLayer {

    /**
     * Reads a UTF-8 text resource from the classpath and returns it as a 
     * string. You have to make sure the resource is actually on the 
     * classpath for this to work. Maven doesn't include text resources
     * in the source tree by default. You have to add the following to the
     * pom to include text resources from src.
     * 
     * <build>
     *   <resources>
     *     <resource><directory>src</directory></resource>
     *   </resources>
     * </build>
     * 
     * @param resource the name of the resource including the package 
     * @return the contents of the resource file
     */
    public static String read_text_resource(String resource) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(currentThread()
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


    @Test
    public void test_can_read_text_resource_from_the_classpath() {
        assertEquals(read_text_resource(get_resource_path(read_text_resource.class, "TestResource.txt")), 
                "This file is used for testing.");
    }
}
