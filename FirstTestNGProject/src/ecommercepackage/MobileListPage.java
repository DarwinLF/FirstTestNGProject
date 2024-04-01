package ecommercepackage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.*;

public class MobileListPage {
	public String baseUrl = "http://live.techpanda.org/";
    public WebDriver driver;
	
    @BeforeMethod
	public void beforeClass() {
		driver = new FirefoxDriver();
		driver.get(baseUrl);
    }
  
    @Test
    public void testSortedMobileListPageByName() {
    	WebElement actualTitleHomePage = driver.findElement(By.cssSelector("h2"));
    	String expectedTitleHomePage = "THIS IS DEMO SITE FOR   ";
    	Assert.assertEquals(actualTitleHomePage.getText(), expectedTitleHomePage);
    	
    	WebElement mobileLink = driver.findElement(By.linkText("MOBILE"));
    	mobileLink.click();
    	
    	WebElement actualTitleMobilePage = driver.findElement(By.cssSelector("h1"));
    	String expectedTitleMobilePage = "MOBILE";
    	Assert.assertEquals(actualTitleMobilePage.getText(), expectedTitleMobilePage);
    	
    	WebElement selectElement = driver.findElement(By.cssSelector("[title='Sort By']"));
    	Select select = new Select(selectElement);
    	select.selectByVisibleText("Name");
    	
    	List<WebElement> productNameElements = driver.findElements(By.cssSelector("h2.product-name"));
    	List<String> productNames = new ArrayList<>();
        for (WebElement productNameElement : productNameElements) {
            productNames.add(productNameElement.getText());
        }
        List<String> sortedProductNames = new ArrayList<>(productNames);
        Collections.sort(sortedProductNames);
        
        Assert.assertEquals(productNames, sortedProductNames);
    }
    
    @Test
    public void testCostProductAreEqual() {
    	WebElement mobileLink = driver.findElement(By.linkText("MOBILE"));
    	mobileLink.click();
    	
    	WebElement spanElement = driver.findElement(By.cssSelector("#product-price-1 > span.price"));
    	String costListPage = spanElement.getText();
    	
    	WebElement productImg = driver.findElement(By.id("product-collection-image-1"));
    	productImg.click();
    	
    	spanElement = driver.findElement(By.cssSelector("span.price"));
    	String costDetailPage = spanElement.getText();
    	
    	Assert.assertEquals(costListPage, costDetailPage);
    }
    
    @Test
    public void testAddMoreProductThanAvailable() {
    	WebElement mobileLink = driver.findElement(By.linkText("MOBILE"));
    	mobileLink.click();
    	
    	WebElement addToCartButton = driver.findElement(By.xpath(".//*[@id='top']/body/div[1]/div/div[2]/div/div[2]/div[1]/div[3]/ul/li[2]/div/div[3]/button"));
    	addToCartButton.click();
    	
    	WebElement QTYInput = driver.findElement(By.cssSelector("[title='Qty']"));
    	QTYInput.clear();
    	QTYInput.sendKeys("1000");
    	
    	WebElement updateButton = driver.findElement(By.xpath(".//*[@id='shopping-cart-table']/tbody/tr/td[4]/button"));
    	updateButton.click();
    	
    	String reqQty = driver.findElement(By.xpath(".//*[@id='shopping-cart-table']/tbody/tr/td[2]/p")).getText();	    
	    String msgQty = ("* The maximum quantity allowed for purchase is 500.");
	    Assert.assertEquals(reqQty, msgQty);
	    
	    WebElement emptyCartButton = driver.findElement(By.id("empty_cart_button"));
	    emptyCartButton.click();
	    
	    WebElement actualTitle = driver.findElement(By.cssSelector("h1"));
	    String expectedTitle = "SHOPPING CART IS EMPTY";
	    Assert.assertEquals(actualTitle.getText(), expectedTitle);
    }
    
    @Test
    public void testCompareTwoProducts() throws InterruptedException {
    	String mainWindowHandle = driver.getWindowHandle();
    	
    	WebElement mobileLink = driver.findElement(By.linkText("MOBILE"));
    	mobileLink.click();
    	
    	driver.findElement(By.xpath(".//*[@id='top']/body/div[1]/div/div[2]/div/div[2]/div[1]/div[3]/ul/li[1]/div/div[3]/ul/li[2]/a")).click();
	    String mainMobile1 = driver.findElement(By.xpath("//h2/a[@title='IPhone']")).getText();
	    
	    driver.findElement(By.xpath(".//*[@id='top']/body/div[1]/div/div[2]/div/div[2]/div[1]/div[3]/ul/li[2]/div/div[3]/ul/li[2]/a")).click();   
	    String mainMobile2 = driver.findElement(By.xpath("//h2/a[@title='Sony Xperia']")).getText();
	    
	    driver.findElement(By.xpath("//button[@title='Compare']")).click();
	    Thread.sleep(1000);
	    
	    for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(mainWindowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
	    
	    String strHead = ("COMPARE PRODUCTS");
	    String compHead = driver.findElement(By.xpath(".//*[@id='top']/body/div[1]/div[1]/h1")).getText();
	    
	    String popupMobile1 = driver.findElement(By.xpath("//h2/a[@title='IPhone']")).getText();
	    String popupMobile2 = driver.findElement(By.xpath("//h2/a[@title='Sony Xperia']")).getText();
	    
	    Assert.assertEquals(strHead, compHead);
	    Assert.assertEquals(mainMobile1, popupMobile1);
	    Assert.assertEquals(mainMobile2, popupMobile2);
	    
	    driver.findElement(By.xpath("//button[@title='Close Window']")).click();
	    
	    driver.switchTo().window(mainWindowHandle);
    }
    
    @Test(enabled = false)
    public void testCreateAccountAndShareWishlist() throws InterruptedException {
    	String firstName = "BERRY";
    	String lastName = "BERRYTWO";
    	
    	driver.findElement(By.linkText("MY ACCOUNT")).click();
    	driver.findElement(By.linkText("CREATE AN ACCOUNT")).click();
    	  
	    driver.findElement(By.id("firstname")).sendKeys(firstName);     
	    driver.findElement(By.id("lastname")).sendKeys(lastName);
	    driver.findElement(By.id("email_address")).sendKeys("Berry123@tpg.com.au");
	    driver.findElement(By.id("password")).sendKeys("mar8mar");
	    driver.findElement(By.id("confirmation")).sendKeys("mar8mar");
	    driver.findElement(By.xpath("//button[@title='Register']")).click();
	    
	    String vWelcome = ("WELCOME, " + firstName + " " + lastName + "!");
	    String tWelcome = driver.findElement(By.xpath(".//*[@id='top']/body/div[1]/div/div[1]/div/p")).getText();
	    Assert.assertEquals(tWelcome, vWelcome);
	    Thread.sleep(3000);
	    
	    driver.findElement(By.xpath(".//*[@id='nav']/ol/li[2]/a")).click();
	    Thread.sleep(3000);
	    driver.findElement(By.xpath("//li/a[@class='link-wishlist']")).click();
	    Thread.sleep(3000);
	    driver.findElement(By.xpath("//button[@title='Share Wishlist']")).click();
	    
	    driver.findElement(By.id("email_address")).sendKeys("support@guru99.com");
	    driver.findElement(By.id("message")).sendKeys("Hey Mary!! this LCD TV looks ok, check it out !!.. cheers .. Berry");
	    driver.findElement(By.xpath("//button[@title='Share Wishlist']")).click();
	    
	    String vWishList = "Your Wishlist has been shared.";
	    String wishList = driver.findElement(By.xpath(".//*[@id='top']/body/div[1]/div/div[2]/div/div[2]/div/div[1]/ul/li/ul/li/span")).getText();
	    Assert.assertEquals(vWishList, wishList);
    }

    @AfterMethod
    public void afterClass() {
    	driver.quit();
    }
}
