package com.testing.springboottest.selenium;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import com.testing.springboottest.selenium.pageobjects.LoginPage;

public class PageObject_LoginTest extends AbstractUnitSeleniumTest {
	
	LoginPage loginPage;

	
	@Before
	public void setUp() throws Exception {
		loginPage = new LoginPage(driver);
	}

	@After
	public void setDown() {
	}
	
	@Test
	public void testLogin()
	{
			
		loginPage.setUserName("Admin");
		loginPage.setPassword("admin123");
		loginPage.clickSubmit();
		
		// Get label
		String  act_label= driver.findElement(By.xpath("//*[@id='app']/div[1]/div[1]/header/div[1]/div[1]/span/h6")).getText();
		
		// Check Title & Label
		assertThat(driver.getTitle()).isEqualTo("OrangeHRM");
		assertThat(act_label).isEqualTo("Dashboard");
	}
	

}
