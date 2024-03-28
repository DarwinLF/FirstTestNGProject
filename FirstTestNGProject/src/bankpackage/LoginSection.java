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
    	setupFirefox();
        
        // Load base URL and wait time from properties
        baseUrl = Util.getBaseUrl();
        waitTime = Util.getWaitTime();
        
        driver.get(baseUrl);
    }
    
    @Test
    public void testValidLogin() {
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
    	
    	WebElement userField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("uid")));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.name("btnLogin"));
    	
    	userField.sendKeys(Util.getUser());
    	passwordField.sendKeys(Util.getPassword());
    	
    	loginButton.click();
    	
    	WebElement actualWelcomeMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("marquee.heading3")));
        String expectedWelcomeMessage = "Welcome To Manager's Page of Guru99 Bank";
        Assert.assertEquals(actualWelcomeMessage.getText(), expectedWelcomeMessage);
    }
    
    @AfterClass
    public void tearDown() {
    	driver.quit();
    }
    
    private void setupFirefox() {
        // Setup Firefox with specified location and profile
        FirefoxOptions options = new FirefoxOptions();
        options.setProfile(new FirefoxProfile());
        driver = new FirefoxDriver(options);
    }
}
