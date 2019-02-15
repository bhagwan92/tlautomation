package com.bhagwan.maven.tlautomation;

import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.bhagwan.maven.tlautomation.Settings;
import com.bhagwan.maven.tlautomation.SignIn;


/**
 * @Class Name  : App
 * @Description : Contains Ebay automation Test cases
 * @author      : Bhgawan Singh Yadav
 * @date        : 06/02/2019
 *
 */

public class App extends Utility {
	private static Logger      log  = Logger.getLogger(App.class.getName());
	
	@BeforeClass
	public void Setup() throws InterruptedException{
		try {
			log.info(" ***** Android application launch method started ***** ");
			launchApp();
			log.info(" ***** Android application launch successfully ***** ");
		}catch(AssertionError ex) {
			log.info("Exception occured while executing keyword :"+ex);

		}
	}
	
	@Test
    public void Android() throws InterruptedException
    {
		String status = "FAIL";
		try {
			log.info(" ***** Set Country/Region in the application ***** ");
			String country = ReadConfigData("country");
			log.info("country : "+country);
			PageFactory.initElements(android_driver, Settings.class);
			status= Settings.Set_Country_Region(country);
			log.info("***** Set Country/Region in the application ***** ");
			Assert.assertEquals(status, "PASS");
			Thread.sleep(10000);
			log.info(" ***** Login to the eBay Application ***** ");
			PageFactory.initElements(android_driver, SignIn.class);
			status= SignIn.ValidLogin(ReadConfigData("eByaUser"),ReadConfigData("eBayPassword"));
			log.info(" ***** Login to the eBay Application ***** ");
			Assert.assertEquals(status, "PASS");
			log.info(" ***** Successfull login happened ***** ");
			log.info(" ***** Add 65 inch TV to the Cart ***** ");
			PageFactory.initElements(android_driver, ShoppingPage.class);
			String productName = ReadConfigData("productName");
			status = ShoppingPage.AddToCart(productName);
			Assert.assertEquals(status, "PASS");
		}catch(AssertionError ex) {
			log.info("Exception occured while executing keyword :"+ex);
			Assert.fail();
			
			
		}
    }
	@AfterClass
	public void TearDownn() throws InterruptedException{
		String status = "FAIL";
			try {
				PageFactory.initElements(android_driver, ShoppingPage.class);
				log.info(" ***** Android application launch method started ***** ");
				status = ShoppingPage.GoToCart();
				Assert.assertEquals(status, "PASS");
				status = ShoppingPage.RemoveFromCart();
				Assert.assertEquals(status, "PASS");
			}catch(AssertionError ex) {
				log.info("Exception occured while executing keyword :"+ex);
				Assert.fail();
			}
	}

    
}
