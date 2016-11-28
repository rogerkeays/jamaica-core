package jamaica.core.io;

import java.util.Scanner;
import org.testng.annotations.Test;
import static jamaica.core.functions.classpath.*;
import static java.lang.Thread.currentThread;
import static org.testng.AssertJUnit.assertEquals;

public class read_text_resource {

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
    @Test public void read_text_resource__reads_a_text_resource_from_the_classpath() {
        assertEquals("This file is used for testing.", read_text_resource("io/TestResource.txt"));
    }
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
}
