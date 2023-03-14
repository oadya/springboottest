package com.testing.springboottest.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Demo4_XPath_Locators_Basic {

	/**
	 * xpath is the adress of a element in web page
	 * 
	 */
	public static void main(String[] args) {
		
		WebDriverManager.edgedriver().setup();
		WebDriver driver = new EdgeDriver();
		
		
		driver.get("https://demo.opencart.com/");
		driver.manage().window().maximize();
		
		// Relative Xpath
		String productname1= driver.findElement(By.xpath("//a[normalize-space()='MacBook']")).getText(); 
		
		// Full Xpath
		String productname2=driver.findElement(By.xpath("/html[1]/body[1]/main[1]/div[2]/div[1]/div[1]/div[2]/div[1]/form[1]/div[1]/div[2]/div[1]/h4[1]/a[1]")).getText();
		
		System.out.println(productname1);

	}

}
