package com.testing.springboottest.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Demo3_CSS_Selectors {

	/**
	 * CSS SELECTOR
	 *
	 * tag & id
	 * tag & class
	 * tag & attribute
	 * tag , class & attribute
	 * 
	 * tag is optional
	 * 
	 */
	public static void main(String[] args) {
			
		//Set up Driver and Browser	
		WebDriverManager.edgedriver().setup();
		WebDriver driver = new EdgeDriver();
		
		//open url on the browser
		driver.get("https://demo.nopcommerce.com/");
		driver.manage().window().maximize();


		//tag & id
		driver.findElement(By.cssSelector("input#small-searchterms")).sendKeys("MacBook");
		driver.findElement(By.cssSelector("#small-searchterms")).sendKeys("MacBook");

		//tag & class
		driver.findElement(By.cssSelector("input.search-box-text")).sendKeys("MacBook");
		driver.findElement(By.cssSelector(".search-box-text")).sendKeys("MacBook");
		
		//tag & attribute
		driver.findElement(By.cssSelector("input[name='q']")).sendKeys("MacBook");
		driver.findElement(By.cssSelector("[name='q']")).sendKeys("MacBook");
		
		//tag, class & attribute : to identify element uniquely
		driver.findElement(By.cssSelector("input.search-box-text[name='q']")).sendKeys("MacBook");
		
	
	}

}
