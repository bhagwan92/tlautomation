
package com.bhagwan.maven.tlautomation;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SignIn {
	private static Logger log  = Logger.getLogger(SignIn.class.getName());
	
	WebDriver driver;
	public SignIn(WebDriver ldriver) {
		this.driver = ldriver;
	}
	
	@FindBy(id = "com.ebay.mobile:id/button_sign_in")
	                                                       
	 static WebElement SignInLlink;
	
	@FindBy(xpath = "//android.widget.EditText[@resource-id='com.ebay.mobile:id/edit_text_username']")	
	 static WebElement SignInUserField;
	
	@FindBy(xpath = "//android.widget.EditText[@resource-id='com.ebay.mobile:id/edit_text_password']")	
	 static WebElement SignInPasswordFiled;
	
	@FindBy(xpath = "//android.widget.Button[@resource-id='android:id/button2']")	
	 static WebElement SignInFingerPrint_MayBeLater;
	
	
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
			log.info("ValidLogin method execution started");
			Thread.sleep(10000);
			SignInLlink.click();
			SignInUserField.sendKeys(username);
			SignInPasswordFiled.sendKeys(password);
			SignInLlink.click();
			Thread.sleep(5000);
			SignInFingerPrint_MayBeLater.click();
			status = "PASS";
		}catch(Exception ex){
			log.info("Exception occured during url launching : "+ex);
			status = "FAIL";
		}
		log.info("ValidLogin method execution completed");
		return status;
	}


}
