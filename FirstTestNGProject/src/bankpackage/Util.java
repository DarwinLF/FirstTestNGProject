package bankpackage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Util {
    private static Properties prop = new Properties();
    
    static {
        try {
            FileInputStream fis = new FileInputStream("resources/config.properties");
            prop.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static String getBaseUrl() {
        return prop.getProperty("BaseURL");
    }
    
    public static int getWaitTime() {
    	return Integer.parseInt(prop.getProperty("WaitTime"));
    }
    
    public static String getUser() {
    	return prop.getProperty("User");
    }
    
    public static String getPassword() {
    	return prop.getProperty("Password");
    }
    
    // Add methods to retrieve other parameters as needed (e.g., username, password)
}