package bankpackage;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.time.Duration;

public class LoginSection {
    public WebDriver driver;
    public String baseUrl;
    public int waitTime;
    
    @BeforeClass
    public void setUp() throws IOException {
    	FirefoxOptions options = new FirefoxOptions();
        options.setProfile(new FirefoxProfile());
        driver = new FirefoxDriver(options);
        
        // Load base URL and wait time from properties
        baseUrl = Util.BASE_URL;
        waitTime = Util.WAIT_TIME;
        driver.get(baseUrl + "/V4/");
    }
    
    @Test
    public void testValidLogin() {
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
    	
    	WebElement userField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("uid")));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.name("btnLogin"));
    	
    	userField.sendKeys(Util.USER_NAME);
    	passwordField.sendKeys(Util.PASSWD);
    	loginButton.click();
    	
    	String actualTitle = driver.getTitle();
        Assert.assertEquals(actualTitle, Util.EXPECT_TITLE);
    }
    
    @AfterClass
    public void tearDown() {
    	driver.quit();
    }
}
