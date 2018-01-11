package com.revature.testsuites;

import java.util.Random;
import java.util.function.Function;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.revature.pages.phptravels.*;
import com.revature.util.drivermanager.*;

// Much larger site, we'll use the POM via Selenium's Page Factory
public class PhpTravelsFactoryTest {
	private static DriverManager driverManager;
	private static WebDriver driver;
	private String url = "http://www.phptravels.net/";

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
	public void validateNavbar() {
		final PhpTravelsLandingPage ptlp = new PhpTravelsLandingPage(driver);
		String[] currencies = {"USD", "GBP", "INR"};
		String[] languages = {"ARABIC", "TURKISH", "FRENCH", "ENGLISH", "SPANISH", "RUSSIAN", "ENGLISH"};
		Random rand = new Random();
		final int currIdx = rand.nextInt(currencies.length);
		final int langIdx = rand.nextInt(languages.length);
		
		final String curr = currencies[currIdx];
		final String lang = languages[langIdx];
		ptlp.clickLogin();
		driver.navigate().back();
		ptlp.clickRegister();
		driver.navigate().back();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(new Function<WebDriver, Boolean>() {

			public Boolean apply(WebDriver driver) {
				System.out.println("Set currency: " + curr);
				ptlp.setCurrency(curr);
				return true;
			}
		});

		Assert.assertTrue(ptlp.getCurrency().contains(curr));
		wait.until(new Function<WebDriver, Boolean>() {

			public Boolean apply(WebDriver driver) {
				System.out.println("Set language: " + lang);
				ptlp.setLanguage(langIdx + 1, lang);
				return true;
			}
		});
		Assert.assertTrue(ptlp.getLanguage().contains(lang));
	}
	
	
}
