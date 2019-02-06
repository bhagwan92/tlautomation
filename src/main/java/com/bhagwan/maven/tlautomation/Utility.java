package com.bhagwan.maven.tlautomation;

import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

/**
 * @Class Name  : Utility
 * @Description : Contains unit level test cases or basic functionality method
 * @author      : Bhgawan Singh Yadav
 * @date        : 06/02/2019
 *
 */

public class Utility {
	private static Logger      log  = Logger.getLogger(Utility.class.getName());
	//static WebDriver android_driver;
	public static AndroidDriver<AndroidElement> android_driver = null;
	/**
	 * Method Name  : ReadConfigData
	 * @Description : This method will read the data from config.properties file.
	 * @Parameter   : key
	 * @return      : value
	 * @author      : Bhgawan Singh Yadav
	 * @date        : 06/02/2019
	 *
	 */
	public static String ReadConfigData(String key) {
		try{
			File classpathRoot = new File(System.getProperty("user.dir"));
			File configDir = new File(classpathRoot, "/src/main/java/com/bhagwan/maven/properties/");
			File app = new File(configDir, "config.properties");
			FileReader reader = new FileReader(app.getAbsolutePath());
			Properties properties = new Properties();
			properties.load(reader);
			return properties.getProperty(key);

		}catch(Exception ex) {
			log.info("Exception occured while reading data from config.properties file"+ex);
		}
		return "";
		
	}
	/**
	 * Method Name  : ReadElement
	 * @Description : This method will read the data from element.properties file.
	 * @Parameter   : key
	 * @return      : value (Android locator path)
	 * @author      : Bhgawan Singh Yadav
	 * @date        : 06/02/2019
	 *
	 */
	public static String ReadElement(String key) {
		try{
			File classpathRoot = new File(System.getProperty("user.dir"));
			File configDir = new File(classpathRoot, "/src/main/java/com/bhagwan/maven/properties/");
			File app = new File(configDir, "element.properties");
			FileReader reader = new FileReader(app.getAbsolutePath());
			Properties properties = new Properties();
			properties.load(reader);
			return properties.getProperty(key);

		}catch(Exception ex) {
			log.info("Exception occured while reading data from element.properties file"+ex);
		}
		return "";
		
	}
	/**
	 * Method Name  : launchApp
	 * @Description : This method will launch the required application.
	 * @Parameter   : ANDROID_VERSION, DeviceName, appPackage, appActivity, uidi from config.properties file 
	 * @return      : PASS/FAIL
	 * @author      : Bhgawan Singh Yadav
	 * @date        : 06/02/2019
	 *
	 */
	public static String launchApp() {
		String status = "FAIL";
		try{	
			File classpathRoot = new File(System.getProperty("user.dir"));
			File appDir = new File(classpathRoot, "/Apps/");
			File app = new File(appDir,ReadConfigData("apkFile") );
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("BROWSER_NAME", "");
			capabilities.setCapability("automationName", "Appium");
			capabilities.setCapability("VERSION", ReadConfigData("ANDROID_VERSION"));
			capabilities.setCapability("deviceName",ReadConfigData("DeviceName"));
			capabilities.setCapability("platformName","Android");
			capabilities.setCapability("app", app.getAbsolutePath());
		    capabilities.setCapability("appPackage", ReadConfigData("appPackage"));
			capabilities.setCapability("appActivity",ReadConfigData("appActivity"));
			capabilities.setCapability("uidi",ReadConfigData("uidi"));
			//android_driver = new RemoteWebDriver(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
			android_driver = new AndroidDriver<AndroidElement>(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
			log.info("Android devices connection established successfully");
			android_driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			//To avoid screen rotation
			android_driver.rotate(ScreenOrientation.PORTRAIT);
			log.info("PORTRAIT mode set to the Android device");
			status = "PASS";
		}catch(Exception ex){
			log.info("Exception occured during url launching : "+ex);
			status = "FAIL";
			
		}
		return status;
	}
	/**
	 * Method Name  : Set_Country_Region
	 * @Description : This method will set the country/region in the eBay application.
	 * @Parameter   : country  name 
	 * @return      : PASS/FAIL
	 * @author      : Bhgawan Singh Yadav
	 * @date        : 06/02/2019
	 *
	 */
	public static String Set_Country_Region(String country) {
		String status = "FAIL";
		try{	
			AndroidElement menu = (AndroidElement)(android_driver.findElement(By.id(ReadElement("homeMenuId"))));
			menu.click();
			//android_driver.swipe(400, 1500, 400, 500,500);
			android_driver.findElement(By.xpath(ReadElement("HomeSettings"))).click();
			android_driver.findElement(By.xpath(ReadElement("HomeSelectRegion"))).click();
			android_driver.findElement(By.xpath(ReadElement("HomeAutoDetectRegion_ON"))).click();
			Thread.sleep(2000);
			android_driver.findElement(By.xpath(ReadElement("HomeDefaultRegion"))).click();
			Thread.sleep(2000);
			android_driver.findElement(By.xpath(ReadElement("HomeSearchRegion"))).sendKeys(country);
			android_driver.findElement(By.xpath(ReadElement("HomeSearchRegion_Result"))).click();
			String expectedCountry = android_driver.findElement(By.xpath(ReadElement("HomeSelectedRegion"))).getText();
			//System.out.println("selected country is : "+expectedCountry);
			Assert.assertEquals(expectedCountry, country);
			log.info(expectedCountry+" Selected as country/Region ");
			android_driver.navigate().back();
			android_driver.navigate().back();
			status = "PASS";
		}catch(Exception ex){
			log.info("Exception occured during url launching : "+ex);
			status = "FAIL";
		}
		return status;
	}
	/**
	 * Method Name  : AddToCart
	 * @Description : This method will add the given product in the cart.
	 * @Parameter   : product name 
	 * @return      : PASS/FAIL
	 * @author      : Bhgawan Singh Yadav
	 * @date        : 06/02/2019
	 *
	 */
	public static String AddToCart(String product) {
		String status = "FAIL";
		try{	
			android_driver.findElement(By.xpath(ReadElement("ProductSearchField"))).click();
			android_driver.findElement(By.xpath(ReadElement("ProductSearchEditField"))).sendKeys(product);
			// 30 seconds explicit wait to show search result
			WebDriverWait wait = new WebDriverWait(android_driver, 30);
			//WebElement element= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath( "//android.widget.TextView[@text='65 inch smart tv']")));
			//element.click();
			Thread.sleep(10000);
			android_driver.findElement(By.xpath(ReadElement("ProductSelect"))).click();
			android_driver.findElement(By.xpath(ReadElement("ProductSelectSearchItem"))).click();
			String actualProduct=android_driver.findElement(By.xpath(ReadElement("ProductSelectedToBuy"))).getText();
		    String	ExpactedProduct =  ReadConfigData("ExpactedProduct");
		    if(actualProduct.equalsIgnoreCase(ExpactedProduct)) {
		    	log.info("Correct product added to the cart ");
		    }
		    android_driver.findElement(By.xpath(ReadElement("ProductAddToCart"))).click();
		    android_driver.findElement(By.xpath(ReadElement("ProductViewInCart"))).click();
		    android_driver.findElement(By.xpath(ReadElement("ProductCheckOut"))).click();		    
			
			status = "PASS";
		}catch(Exception ex){
			log.info("Exception occured during url launching : "+ex);
			status = "FAIL";
		}
		return status;
	}
	/**
	 * Method Name  : ValidLogin
	 * @Description : This method will successfully login to Application.
	 * @Parameter   : username, password 
	 * @return      : PASS/FAIL
	 * @author      : Bhgawan Singh Yadav
	 * @date        : 06/02/2019
	 *
	 */
	public static String ValidLogin(String username, String password) {
		String status = "FAIL";
		try{	
			Thread.sleep(10000);
			android_driver.findElement(By.xpath(ReadElement("SignInLlink"))).click();
			android_driver.findElement(By.xpath(ReadElement("SignInUserField"))).sendKeys(username);
			android_driver.findElement(By.xpath(ReadElement("SignInPasswordFiled"))).sendKeys(password);
			android_driver.findElement(By.xpath(ReadElement("SignInButton"))).click();
			Thread.sleep(10000);
			android_driver.findElement(By.xpath(ReadElement("SignInFingerPrint_MayBeLater"))).click();
			WebDriverWait wait = new WebDriverWait(android_driver, 30);
			//WebElement element= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath( "//android.widget.TextView[@text='65 inch smart tv']")));
			//element.click();
			Thread.sleep(10000);
			//android_driver.findElement(By.xpath("//android.widget.TextView[@text='65 inch smart tv']")).click();
			status = "PASS";
		}catch(Exception ex){
			log.info("Exception occured during url launching : "+ex);
			status = "FAIL";
		}
		return status;
	}

	
}
