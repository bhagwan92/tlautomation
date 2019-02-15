package com.bhagwan.maven.tlautomation;

import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;

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
			log.info("Application launch started");
			File classpathRoot = new File(System.getProperty("user.dir"));
			File appDir = new File(classpathRoot, "/Apps/");
			log.info("appDir : "+appDir);
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
		log.info("Application launch method execution completed");
		return status;
	}
	
	/**
	 * Method Name  : TakeScreenShot
	 * @Description : This method will capture the screenshots of the current screen.
	 * @return      : value
	 * @author      : Bhgawan Singh Yadav
	 * @date        : 15/02/2019
	 *
	 */
	public static String TakeScreenShot(String FileName) {
		String status ="Fail";
		try{
			File classpathRoot = new File(System.getProperty("user.dir"));
			File configDir = new File(classpathRoot, "/ScreenShotFiles/");
			File scrFile = ((TakesScreenshot)android_driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(configDir+"/"+FileName+".png"));
			status = "Pass";
		}catch(Exception ex) {
			log.info("Exception occured while reading data from config.properties file"+ex);
		}
		return status;
		
	}
	
	/**
	 * Method Name  : Scroll
	 * @Description : This method will scroll the screen based on .
	 * @return      : Pass/Fail
	 * @author      : Bhgawan Singh Yadav
	 * @date        : 15/02/2019
	 *
	 */
	public static String Scroll(int x1, int y1, int x2, int y2) {
		log.info("Scroll method started");
		String timeStamp = "";
		try{
			TouchAction ta = new TouchAction(android_driver);
	        ta.press(PointOption.point(x1, y1)).moveTo(PointOption.point(x2,y2)).release().perform();
	        Thread.sleep(1000);
	        log.info("Scroll method completed");
		}catch(Exception ex) {
			log.info("Exception occured while reading data from config.properties file"+ex);
		}
		return timeStamp;
		
	}
	
	/**
	 * Method Name  : CurrentDateTime
	 * @Description : This method will return the current date and time stamp.
	 * @return      : timestamp
	 * @author      : Bhgawan Singh Yadav
	 * @date        : 15/02/2019
	 *
	 */
	public static String CurrentDateTime() {
		String timeStamp = "";
		try{
			timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		}catch(Exception ex) {
			log.info("Exception occured while reading data from config.properties file"+ex);
		}
		return timeStamp;
		
	}
	
	
}
