package com.testing.springboottest.selenium.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

	public LoginPage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(name="username")
	WebElement txt_username;
	
	@FindBy(css="input[placeholder='Password']")
	WebElement txt_password;
	
	@FindBy(xpath="//button[normalize-space()='Login']")
	WebElement btn_submit;
		
	@FindBy(xpath="//img[@alt='company-branding']") 
	//@FindBy(how=How.XPATH, using="//img[@alt='company-branding']")
	WebElement img_logo;
	
	@FindBy(tagName="a") // to capture multiple web elements
	List<WebElement> links;
	
	
	//Action methods
	public void setUserName(String username){
		txt_username.sendKeys(username);
	}
	
	public void setPassword(String password){
		txt_password.sendKeys(password);
	}
	
	public void clickSubmit(){
		btn_submit.click();
	}
	
	public boolean checkLogoPresence()
	{
		boolean status=img_logo.isDisplayed();
		return status;
	}

}
