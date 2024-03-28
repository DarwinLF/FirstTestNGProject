package ecommercepackage;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class MobileListPage {
	public String baseUrl = "http://live.techpanda.org/";
    public WebDriver driver;
	
    @BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		driver.get(baseUrl);
    }
  
    @Test
    public void testSortedMobileListPageByName() {
    	WebElement actualTitleHomePage = driver.findElement(By.cssSelector("h2:contains('THIS IS DEMO SITE')"));
    	String expectedTitleHomePage = "THIS IS DEMO SITE";
    	Assert.assertEquals(actualTitleHomePage.getText(), expectedTitleHomePage);
    }

    @AfterClass
    public void afterClass() {
    	driver.quit();
    }
}
