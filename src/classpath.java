package jamaica.core.functions;

import org.testng.annotations.*;
import static jamaica.core.functions.lang.*;
import static jamaica.core.functions.strings.*;
import static jamaica.core.functions.testing.*;

public class classpath {

    // get_resource_path
    @Test public void get_resource_path__returns_a_path_matching_the_full_class_name() {
        assert_equals("jamaica/core/functions/classpath.class", get_resource_path(classpath.class));
    }
    @Test public void get_resource_path__returns_a_path_matches_the_package_name_and_resource_requested() {
        assert_equals("jamaica/core/functions/rc.txt", get_resource_path(classpath.class, "rc.txt"));
    }
    public static String get_resource_path(Class klass) {
        return get_resource_path(klass, null);
    }
    public static String get_resource_path(Class klass, String resource) {
        String path = klass.getPackage().getName().replace(".", "/");
        if (not(is_empty(resource))) {
            return path + "/" + resource;
        } else {
            return path + "/" + klass.getSimpleName() + ".class";
        }
    }
}
