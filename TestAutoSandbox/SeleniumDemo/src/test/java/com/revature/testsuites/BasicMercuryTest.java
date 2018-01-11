package com.revature.testsuites;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import com.revature.util.drivermanager.DriverManager;
import com.revature.util.drivermanager.DriverManagerFactory;
import com.revature.util.drivermanager.DriverType;

import org.testng.*;

// automate all of Mercury Tours
public class BasicMercuryTest {

	private static DriverManager driverManager;
	private static WebDriver driver;
	private String url = "http://newtours.demoaut.com";

	@BeforeClass
	public void beforeTest() {
		driverManager = DriverManagerFactory.getManager(DriverType.CHROME);
	}

	@BeforeMethod
	public void beforeMethod() {
		driver = driverManager.getDriver();
		driver.get(url);
	}
	
	@AfterMethod
	public void afterMethod() {
		driverManager.quitDriver();
	}

	@Test
	public void validateLandingPage() {
		Assert.assertEquals(driver.getTitle(), "Welcome: Mercury Tours");
	}
	
	@Test(dependsOnMethods = "validateLandingPage")
	public void sendKeys() {
		driver.findElement(By.name("userName")).sendKeys("username");
		driver.findElement(By.name("password")).sendKeys("password");
		driver.findElement(By.name("login")).click();
		
		Assert.assertEquals(driver.getTitle(), "Sign-on: Mercury Tours");
	}
}
