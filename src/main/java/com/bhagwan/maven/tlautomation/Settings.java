package com.bhagwan.maven.tlautomation;

import org.apache.log4j.Logger;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.testng.Assert;
import org.openqa.selenium.support.PageFactory;


public class Settings {
	private static Logger log  = Logger.getLogger(Settings.class.getName());
	
	private static WebDriver driver;
	public Settings(WebDriver ldriver) {
		this.driver = ldriver;
	}
	
	@FindBy(how = How.XPATH, using = "//android.widget.ImageButton[@content-desc='Main navigation, open']")
	 static WebElement homeMenuId;
	
	@FindBy(xpath = "//android.widget.CheckedTextView[@text='Settings']")	
	 static WebElement HomeSettings;
	
	@FindBy(xpath = "//android.widget.TextView[@text='Country/region']")	
	 static WebElement HomeSelectRegion;
	
	@FindBy(xpath = "//android.widget.Switch[@text='ON']")	
	 static WebElement HomeAutoDetectRegion_ON;
	
	@FindBy(xpath = "//android.widget.TextView[@text='India']")	
	 static WebElement HomeDefaultRegion;
	
	@FindBy(id = "com.ebay.mobile:id/filter")	
	 static WebElement HomeSearchRegion;
	
	@FindBy(xpath = "//android.widget.CheckedTextView[@text='Australia']")	
	 static WebElement HomeSearchRegion_Result;
	
	@FindBy(xpath = "//android.widget.TextView[@text='Australia']")	
	 static WebElement HomeSelectedRegion;
	
	
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
			log.info("Set_Country_Region method execution started");
			log.info(homeMenuId);
			homeMenuId.click();
			new TouchActions(driver).scroll(500, 2000).release().perform();
			HomeSettings.click();
			HomeSelectRegion.click();
			HomeAutoDetectRegion_ON.click();
			Thread.sleep(2000);
			HomeDefaultRegion.click();
			Thread.sleep(2000);
			HomeSearchRegion.sendKeys(country);
			HomeSearchRegion_Result.click();
			String expectedCountry = HomeSelectedRegion.getText();
			log.info("selected country is : "+expectedCountry);
			Assert.assertEquals(expectedCountry, country);
			log.info(expectedCountry+" Selected as country/Region ");
			driver.navigate().back();
			driver.navigate().back();
			status = "PASS";
		}catch(Exception ex){
			log.info("Exception occured during country/region setting : "+ex);
			status = "FAIL";
		}
		log.info("Set_Country_Region method execution completed");
		return status;
	}

}
