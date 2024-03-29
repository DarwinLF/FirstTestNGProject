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
    
    @BeforeMethod
    public void setUp() throws IOException {
    	FirefoxOptions options = new FirefoxOptions();
        options.setProfile(new FirefoxProfile());
        driver = new FirefoxDriver(options);
        
        // Load base URL and wait time from properties
        baseUrl = Util.BASE_URL;
        waitTime = Util.WAIT_TIME;
        driver.get(baseUrl + "/V4/");
        wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
    }
    
    @Test
    public void testValidLogin() throws IOException {
    	
    	String[] loginData = readLoginDataFromExcel(1);
    	
    	WebElement userField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("uid")));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.name("btnLogin"));
    	
    	userField.sendKeys(loginData[0]);
    	passwordField.sendKeys(loginData[1]);
    	loginButton.click();
    	
    	String actualTitle = driver.getTitle();
        Assert.assertEquals(actualTitle, loginData[2]);
    }
    
    @Test
    public void testInvalidUser() throws IOException {
    	
    	String[] loginData = readLoginDataFromExcel(2);
    	
    	WebElement userField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("uid")));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.name("btnLogin"));
    	
    	userField.sendKeys(loginData[0]);
    	passwordField.sendKeys(loginData[1]);
    	loginButton.click();
    	
    	wait.until(ExpectedConditions.alertIsPresent());
    	
    	Alert alert = driver.switchTo().alert();
    	String actualText = alert.getText();
    	
    	alert.dismiss();
    	
        Assert.assertEquals(actualText, loginData[2]);
    }
    
    @Test
    public void testInvalidPassword() throws IOException {
    	
    	String[] loginData = readLoginDataFromExcel(3);
    	
    	WebElement userField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("uid")));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.name("btnLogin"));
    	
    	userField.sendKeys(loginData[0]);
    	passwordField.sendKeys(loginData[1]);
    	loginButton.click();
    	
    	wait.until(ExpectedConditions.alertIsPresent());
    	
    	Alert alert = driver.switchTo().alert();
    	String actualText = alert.getText();
    	
    	alert.dismiss();
    	
        Assert.assertEquals(actualText, loginData[2]);
    }
    
    @Test
    public void testInvalidUserAndPassword() throws IOException {
    	
    	String[] loginData = readLoginDataFromExcel(4);
    	
    	WebElement userField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("uid")));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.name("btnLogin"));
    	
    	userField.sendKeys(loginData[0]);
    	passwordField.sendKeys(loginData[1]);
    	loginButton.click();
    	
    	wait.until(ExpectedConditions.alertIsPresent());
    	
    	Alert alert = driver.switchTo().alert();
    	String actualText = alert.getText();
    	
    	alert.dismiss();
    	
        Assert.assertEquals(actualText, loginData[2]);
    }
    
    @AfterMethod
    public void tearDown() {
    	driver.quit();
    }
    
    private String[] readLoginDataFromExcel(int rowIndex) throws IOException {
        FileInputStream fis = new FileInputStream(excelFilePath);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0); // Assuming data is in the first sheet
        Row row = sheet.getRow(rowIndex);
        String[] loginData = new String[3]; // Assuming username and password
        loginData[0] = row.getCell(0).getStringCellValue(); // Username
        loginData[1] = row.getCell(1).getStringCellValue(); // Password
        loginData[2] = row.getCell(2).getStringCellValue(); // ExpectedResult
        workbook.close();
        fis.close();
        return loginData;
    }
}
