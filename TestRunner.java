import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner { // there's a way to get more detailed output, but this works for now.
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(categoryTest.class, teamTest.class); // add your test classes here:
        System.out.println();
        System.out.println();
        if (result.wasSuccessful()) {
            System.out.println("Passed all tests.");
        } else {
            System.out.println("The following tests failed:");
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        }
    }
}
