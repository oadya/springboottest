package com.testing.springboottest.selenium;

import java.time.Duration;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;


public abstract class AbstractUnitSeleniumTest {

	/**
	 * Le web driver
	 */
	public static WebDriver driver;

	@BeforeClass
	public static void setUpClass() throws Exception {
		
		WebDriverManager.edgedriver().setup();
	    driver = new EdgeDriver();
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
		Thread.sleep(5000);
	}
	
	@AfterClass
	public static void tearDown()
	{
		driver.quit(); // Fermer le navigateur
	    driver = null;
	}


	public static WebDriver getDriver() {
		return driver;
	}

	public static boolean presenceOfElement(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException ex) {
			return false;
		}
	}

}
