package jamaica.core.testing;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestLogger implements ITestListener {

    private String getName(ITestResult result) {
        return result.getTestClass().getName() + " - " + result.getName();
    }

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("START " + getName(result));     
    }

    @Override
    public void onTestSuccess(ITestResult result) {}

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("FAILED " + getName(result));
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("SKIPPED " + getName(result));
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {}

    @Override
    public void onStart(ITestContext context) {}

    @Override
    public void onFinish(ITestContext context) {}
}
