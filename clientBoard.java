package cprefund;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;

public class clientBoard implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        // Print the test name and error message
        System.err.println("Test Failed: " + result.getName());
        Throwable throwable = result.getThrowable();
        if (throwable != null) {
            System.err.println("Error: " + throwable.getMessage());
            throwable.printStackTrace();
        }

        // Capture screenshot on failure
        captureScreenshot(result.getName());
    }

    private void captureScreenshot(String testName) {
        if (Perkle.driver != null) {
            try {
                // Create screenshots directory if it doesn't exist
                File screenshotsDir = new File(System.getProperty("user.dir") + "/screenshots");
                if (!screenshotsDir.exists()) {
                    screenshotsDir.mkdirs();
                }

                // Take screenshot
                File screenshot = ((TakesScreenshot) Perkle.driver).getScreenshotAs(OutputType.FILE);

                // Define the path where the screenshot will be saved
                String screenshotPath = screenshotsDir + "/" + testName + "_failure.png";

                // Save the screenshot to the specified path
                FileUtils.copyFile(screenshot, new File(screenshotPath));

                System.out.println("Screenshot saved at: " + screenshotPath);
            } catch (IOException e) {
                System.err.println("Failed to capture screenshot: " + e.getMessage());
            }
        } else {
            System.err.println("WebDriver is not initialized. Screenshot cannot be captured.");
        }
    }
}
