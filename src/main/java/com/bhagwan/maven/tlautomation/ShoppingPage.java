package com.bhagwan.maven.tlautomation;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
//import org.openqa.selenium.support.FindAllBy;

import java.util.List;

public class ShoppingPage {
	private static Logger      log  = Logger.getLogger(ShoppingPage.class.getName());
	private static WebDriver driver;
	public ShoppingPage(WebDriver ldriver) {
		this.driver = ldriver;
	}
	
	@FindBy(id = "com.ebay.mobile:id/search_box")	
	 static WebElement ProductSearchBox;
	
	@FindBy(id = "com.ebay.mobile:id/filter")	
	 static WebElement HomeSearchRegion;
	
	@FindBy(id = "com.ebay.mobile:id/search_src_text")	
	 static WebElement ProductSearchBoxEditField;
	
	@FindBy(xpath = "//android.widget.TextView[@text='65 inch smart tv']")	
	 static WebElement ProductSelect;
	
	@FindBys({
	    @FindBy(id = "com.ebay.mobile:id/cell_collection_item"),
	    @FindBy(className = "android.widget.RelativeLayout")
	    })
	 static List<WebElement> allElementsInList;	
	
	@FindBy(id = "com.ebay.mobile:id/button_add_to_cart")	
	 static WebElement AddToCart;
	
	@FindBy(id = "com.ebay.mobile:id/action_view_icon")	
	 static WebElement ViewCart;
	
	@FindBy(id = "com.ebay.mobile:id/remove_from_cart_button")	
	 static WebElement RemoveFromCart;
	
	@FindBy(id = "android:id/button1")	
	 static WebElement RemoveFromCart_ConfirmYes;

	
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
			ProductSearchBox.click();
			ProductSearchBoxEditField.sendKeys(product);
			Thread.sleep(10000);
			ProductSelect.click();
			allElementsInList.get(1).click();
			Thread.sleep(5000);
			AddToCart.click();
			Thread.sleep(5000);
			status = "PASS";
		}catch(Exception ex){
			log.info("Exception occured during AddToCart : "+ex);
			status = "FAIL";
		}
		return status;
	}
	
	/**
	 * Method Name  : GoToCart
	 * @Description : This method will show the added items in the cart.
	 * @return      : PASS/FAIL
	 * @author      : Bhgawan Singh Yadav
	 * @date        : 09/02/2019
	 *
	 */
	public static String GoToCart() {
		String status = "FAIL";
		try{	
			ViewCart.click();
			status = "PASS";
		}catch(Exception ex){
			log.info("Exception occured during GoToCart : "+ex);
			status = "FAIL";
		}
		return status;
	}
	
	/**
	 * Method Name  : RemoveFromCart
	 * @Description : This method will show the added items in the cart.
	 * @return      : PASS/FAIL
	 * @author      : Bhgawan Singh Yadav
	 * @date        : 09/02/2019
	 *
	 */
	public static String RemoveFromCart() {
		String status = "FAIL";
		try{	
			RemoveFromCart.click();
			Thread.sleep(2000);
			RemoveFromCart_ConfirmYes.click();
			driver.navigate().back();
			driver.navigate().back();
			driver.navigate().back();
			status = "PASS";
		}catch(Exception ex){
			log.info("Exception occured during RemoveFromCart : "+ex);
			status = "FAIL";
		}
		return status;
	}



}
