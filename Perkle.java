package cprefund;

import java.time.Duration;
import java.util.Random;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Perkle {
    public static WebDriver driver;
    public static WebDriverWait wait;
    private static final Random random = new Random();

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test(priority = 1)
    public void loginAsClient() {
        driver.get("https://preprod-ui.perkle.store/login");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Enter your email']")))
            .sendKeys("obul+client@neokred.tech");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-path='password']")))
            .sendKeys("Enter your password");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-variant='subtle']"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']"))).click();
    }

    @Test(priority = 2)
    public void raisePrefundRequest() {
        // Navigate to Prefund
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div//span[text()='Prefund']"))).click();

        // Raise Prefund Request
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button//span[text()='Raise Prefund']"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div//input[@placeholder='Amount']")))
            .sendKeys("10000");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button//span[text()='Submit']"))).click();
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
