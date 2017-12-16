package com.revature.mercury;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.revature.pages.MercuryLoginFactory;

public class Driver {

	static WebDriver driver;
	static String url = "http://newtours.demoaut.com/";
	static int testCount = 0;
	
	public static void main(String[] args) throws InterruptedException {
		//Sets a system variable to point to our chromedriver.exe file
		System.setProperty("webdriver.chrome.driver", "Drivers/chromedriver.exe");
		//Have our driver become an instance of a ChromeDriver()
		driver = new ChromeDriver();
		//Launch driver at indicated url.
		driver.get(url);
		checkTitle("Welcome: Mercury Tours");
/*		//send keys in order to type in a field
		driver.findElement(By.name("userName")).sendKeys("bobbert");
		driver.findElement(By.name("password")).sendKeys("bobbert");
		//Click in order to simulate a mouse click.
		driver.findElement(By.name("login")).click();*/
		
		MercuryLoginFactory ml = new MercuryLoginFactory(driver);
		ml.driverLogIntoMercury("bobbert", "bobbert");
		
		checkTitle("Find a Flight: Mercury Tours:");
		
		List<WebElement> elements = driver.findElements(By.name("tripType"));
		for(WebElement e: elements){
			e.click();
		}
		
		//XPath is the preferred test automation option, next to css
		//selector.
		driver.findElement(By.xpath("//input[@value='roundtrip']")).click();
		

		//NoSuchElementException in cases where there is no element
		
		
		//ALWAYS quit your drivers.
		//If an exception occurs, the process will not quit
		//properly, and you will have to end the process 
		//directly...
		//driver.quit();
	}
	
	public static void checkTitle(String expected){
		String actual = driver.getTitle();
		testCount++;
		String testString = "TEST " + testCount + ": ";
		/*
		 * The simplest first test for any automated script should be a clarification
		 * that you are, indeed, in the right location. Otherwise why even bother
		 * with the rest of the tests.
		 * So here was compare the html page title with an expected one provided us.
		 */
		if(!actual.equals(expected)){
			System.out.println(testString + "FAILED");
			System.out.println("Expected:\t\"" + expected + "\"");
			System.out.println("Actual:\t\"" + actual + "\"");
		}else{
			System.out.println(testString + "PASSED");
		}
	}

}
