package com.bhagwan.maven.tlautomation;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;


/**
 * @Class Name  : App
 * @Description : Contains Ebay automation Test cases
 * @author      : Bhgawan Singh Yadav
 * @date        : 06/02/2019
 *
 */

public class App extends Utility {
	private static Logger      log  = Logger.getLogger(App.class.getName());
	static int FAILURE;
	
	@Test
    public void Android() throws InterruptedException
    {
		
		String status = "FAIL";
		try {
			
			log.info(" ***** Android application launch method started ***** ");
			status= launchApp();
			Assert.assertEquals(status, "PASS");
			log.info(" ***** Android application launch method Passed ***** ");
			log.info(" ***** Set Country/Region in the application ***** ");
			String country = ReadConfigData("country");
			status= Set_Country_Region(country);
			log.info("***** Set Country/Region in the application ***** ");
			Assert.assertEquals(status, "PASS");
			log.info(" ***** Login to the eBay Application ***** ");
			status= ValidLogin(ReadConfigData("eByaUser"),ReadConfigData("eBayPassword"));
			log.info(" ***** Login to the eBay Application ***** ");
			Assert.assertEquals(status, "PASS");
			log.info(" ***** Successfull login happened ***** ");
			log.info(" ***** Add 65 inch TV to the Cart ***** ");
			String productName = ReadConfigData("productName");
			status = AddToCart(productName);
			Assert.assertEquals(status, "PASS");
		}catch(AssertionError ex) {
			log.info("Exception occured while executing keyword :"+ex);
			Assert.fail();
			
			
		}
		
	}

    
}
