package jamaica.core.testing;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;


/**
 * Group tests based on their superclass which represents the layer of
 * the application that they test.
 */
public class TestGrouper implements IAnnotationTransformer {
 
    /* application layers, used to group tests */
    public static interface JavaLayer {}
    public static interface DataLayer {}
    public static interface NetworkLayer {}
    public static interface APILayer {}
    public static interface UILayer {}
    public static interface DeploymentLayer {}
       
    /* string names of the testing groups */
    public static final String JAVA_LAYER = "Java";
    public static final String DATA_LAYER = "Database";
    public static final String NETWORK_LAYER = "Network";
    public static final String API_LAYER = "API";
    public static final String UI_LAYER = "UI";
    public static final String DEPLOYMENT_LAYER = "Deployment";
    public static final String WONT_FIX = "WONT_FIX";

    @Override
    public void transform(ITestAnnotation test, Class testClass, 
            Constructor c, Method method) {

        // get the class this test is on
        Class layer;
        if (method != null) {
            layer = method.getDeclaringClass();
        } else if (c != null) {
            layer = c.getDeclaringClass();
        } else if (testClass != null) {
            layer = testClass;
        } else {
            throw new IllegalArgumentException(
                    "Couldn't find declaring class for test");
        }

        // determine layer group based on the test class
        String group;
        int priority;
        if (DeploymentLayer.class.isAssignableFrom(layer)) {
            group = DEPLOYMENT_LAYER;
            priority = 6;
        } else if (UILayer.class.isAssignableFrom(layer)) {
            group = UI_LAYER;
            priority = 5;
        } else if (APILayer.class.isAssignableFrom(layer)) {
            group = API_LAYER;
            priority = 4;
        } else if (NetworkLayer.class.isAssignableFrom(layer)) {
            group = NETWORK_LAYER;
            priority = 3;
        } else if (DataLayer.class.isAssignableFrom(layer)) {
            group = DATA_LAYER;
            priority = 2;
        } else {
            group = JAVA_LAYER;
            priority = 1;
        }

        // add the test group and set priority
        if (group != null) {
            int length = test.getGroups().length;
            String [] groups = new String[length + 1]; 
            System.arraycopy(test.getGroups(), 0, groups, 0, length);
            groups[length] = group;
            test.setGroups(groups);
        }
        if (test.getPriority() == 0) {
            test.setPriority(priority);
        }
    }
}
