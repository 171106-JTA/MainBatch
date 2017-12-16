package com.revature.mercury;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.revature.pages.MercuryLoginFactory;

public class Driver {
	static WebDriver driver;
	static String url = "http://newtours.demoaut.com/";
	static int testCount = 0;

	public static void main(String[] args) throws InterruptedException {
		// sets a system variable to point to chromedriver.exe
		System.setProperty("webdriver.chrome.driver", "Drivers/chromedriver.exe");
		// have our driver become an instance of a ChromeDriver()
		driver = new ChromeDriver();
		// lanuch driver at indicated url
		driver.get(url);
		// send keys in order to type in a field
		checkTitle("Welcome: Mercury Tours");
		MercuryLoginFactory ml = new MercuryLoginFactory(driver);
		ml.driverLogIntoMercury("bobbert", "bobbert");
		
		driver.findElements(By.name("tripType")).get(1).click();
		// XPath is the preferred test automation option , next to css selector.
		driver.findElement(By.xpath("//input[@value='roundtrip']"));
		
		// NoSuchElementException occurs in cases where there is no element
		// *always* quit your drivers.
		// if an exception occurs, the process will not quit properly, and you will have to end the process directly.
		driver.quit();
	}
	
	public static void checkTitle(String expected)
	{
		String actual = driver.getTitle();
		testCount ++ ;
		String testString = String.format("TEST %d: ", testCount);
		/*
		 * The simplest first test for any automated script should be a clarification that you are, indeed, in the 
		 * right location. Otherwise, why even bother with the rest of the tests.
		 * So here was compare the html page title with an expected one provided us.
		 */
		
		if (!actual.equals(expected))
		{
			System.out.println(testString + "FAILED");
			System.out.printf("Expected:\t\"%s\"\nActual:\t\"%s\"", expected, actual);
		}
		else
		{
			System.out.println(testString + "PASSED");
		}
	}
}
