package jamaica.core.lang;

import jamaica.core.testing.TestGrouper.JavaLayer;
import org.testng.annotations.Test;
import static jamaica.core.strings.is_empty.is_empty;
import static org.testng.AssertJUnit.assertEquals;

public class get_resource_path implements JavaLayer {

    /**
     * Return the path to use for resources in the same package as the
     * given class. If no resource name is provided the path to the class 
     * itself is returned without the trailing /
     */
    public static String get_resource_path(Class klass, String resource) {
        String path = klass.getPackage().getName().replace(".", "/");
        if (!is_empty(resource)) {
            return path + "/" + resource;
        } else {
            return path + "/" + klass.getSimpleName() + ".class";
        }

    }
    public static String get_resource_path(Class klass) {
        return get_resource_path(klass, null);
    }


    @Test
    public void test_class_resource_path_matches_class_name() {
        assertEquals("jamaica/core/lang/get_resource_path.class",
                get_resource_path(get_resource_path.class));
    }

    @Test
    public void test_string_resource_path_matches_package_name() {
        assertEquals("jamaica/core/lang/rc.txt",
                get_resource_path.get_resource_path(
                get_resource_path.class, "rc.txt"));
    }
}
