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
	
}
