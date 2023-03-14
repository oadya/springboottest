package com.testing.springboottest.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Demo1_Locators_To_Identify_Single_Element {

	public static void main(String[] args) throws InterruptedException {
		
		//Set up Driver and Browser	
		// Methode manuelle
		//System.setProperty("webdriver.chrome.driver","C:\\Drivers\\chromedriver_win32\\chromedriver.exe");
		// Méthode automatique
		 WebDriverManager.edgedriver().setup();
		
		//Set up Driver and Browser	
		WebDriver driver = new EdgeDriver();
				
		//open url on the browser
		driver.get("https://money.rediff.com/gainers/bse/daily/groupa");
		driver.manage().window().maximize(); // maximize browser window
		
		//search box
		WebElement web =  driver.findElement(By.id("srchword"));
		web.clear();
		web.sendKeys("Tata motors");
		
		//wait 5s
		Thread.sleep(5000);
				
		//search box
		web.clear();
		driver.findElement(By.name("srchword")).sendKeys("Reliance MediaWorks Ltd");
		
		//wait 5s
		Thread.sleep(5000);
				
		//link text & partial linktext
		driver.findElement(By.linkText("Home")).click(); // Présiser le texte complet, à préférer 
		//driver.findElement(By.partialLinkText("Hom")).click(); // Préciser partiel du texte, à éviter
		
	}

}
