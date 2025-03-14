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

public class superadmin {
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
    public void loginAsSuperAdmin() {
        driver.get("https://preprod-ui.perkle.store/login");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Enter your email']")))
            .sendKeys("obul+superadmin@neokred.tech");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-path='password']")))
            .sendKeys("NKtech@12345");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-variant='subtle']"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']"))).click();
    }

    @Test(priority = 2)
    public void onboardAndActivateAdmin() {
        // Navigate to Admins
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div//span[text()='Admins']"))).click();

        // Create Admin
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button//span[text()='Create Admin']"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div//input[@placeholder='Enter Email Id']")))
            .sendKeys("obul+admin" + random.nextInt(1000) + "@neokred.tech");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button/span/span[text()='Submit']"))).click();

        // Activate Admin
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//table//tr[1]//td[4]//button"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button/span/span[text()='Make Active']"))).click();
    }

    @Test(priority = 3)
    public void approvePrefundRequest() {
        // Navigate to Prefund
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div//span[text()='Prefund']"))).click();

        // Search and Approve Prefund
        WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div/input[@placeholder='Search...']")));
        searchBox.sendKeys("dfghjkfffwertyhujkjhgf5678");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//table//tr[1]//td[9]//button"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button/span/span[text()='Approve']"))).click();
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}