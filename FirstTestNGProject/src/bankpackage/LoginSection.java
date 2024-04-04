package bankpackage;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;

public class LoginSection {
    public WebDriver driver;
    public String baseUrl;
    public int waitTime;
    public WebDriverWait wait;
    public String excelFilePath = "D:\\Github\\FirstTestNGProject\\FirstTestNGProject\\resources\\firstTestNG.xlsx";
    
    @DataProvider(name = "loginData")
    public Object[][] provideLoginData() throws IOException {
        FileInputStream fis = new FileInputStream(excelFilePath);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0); // Assuming data is in the first sheet
        int rowCount = sheet.getPhysicalNumberOfRows();
        Object[][] loginData = new Object[rowCount - 1][3];
        
        for (int i = 1; i < rowCount; i++) { // Start from index 1 to skip header row
            Row row = sheet.getRow(i);
            loginData[i - 1][0] = row.getCell(0).getStringCellValue(); // Username
            loginData[i - 1][1] = row.getCell(1).getStringCellValue(); // Password
            loginData[i - 1][2] = row.getCell(2).getStringCellValue(); // ExpectedResult
        }
        
        workbook.close();
        fis.close();
        return loginData;
    }
    
    @BeforeMethod
    public void setUp() throws IOException {
    	FirefoxOptions options = new FirefoxOptions();
        options.setProfile(new FirefoxProfile());
        driver = new FirefoxDriver(options);
        
        
        // Load base URL and wait time from properties
        baseUrl = Util.BASE_URL;
        waitTime = Util.WAIT_TIME;
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(waitTime));
        driver.get(baseUrl);
        //wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
    }
    
    @Test(dataProvider = "loginData")
    public void testLoginSection(String username, String password, String expectedResult) {
    	
    	//WebElement userField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("uid")));
    	WebElement userField = driver.findElement(By.name("uid"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.name("btnLogin"));
    	
    	userField.sendKeys(username);
    	passwordField.sendKeys(password);
    	loginButton.click();
    	
    	try{ 
    		Alert alert = driver.switchTo().alert();
        	String actualText = alert.getText();
        	
        	alert.dismiss();
        	
            Assert.assertEquals(actualText, expectedResult);
			
		}    
	    catch (NoAlertPresentException Ex){ 
	    	String xpath = "//td[contains(text(), 'Manger Id : " + username + "')]";
	    	WebElement welcomeMessage = driver.findElement(By.xpath(xpath));
	        Assert.assertEquals(welcomeMessage.getText(), expectedResult);
        } 
    }
    
    @AfterMethod
    public void tearDown() {
    	driver.quit();
    }
}
