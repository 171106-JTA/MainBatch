package com.revature.gluecode;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.revature.pages.MercuryLoginFactory;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class MercuryLogin {
	public static WebDriver driver;

	@Given("^I am at the login screen for Mercury Tours$")
	public void i_am_at_the_login_screen_for_Mercury_Tours() throws Throwable {
		System.setProperty("webdriver.chrome.driver", "Drivers/chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("http://newtours.demoaut.com");
		assertEquals("Welcome: Mercury Tours", driver.getTitle());
		// Note, a pending exception is here by default just to show that
		// The test has not been implemented yet.
	}

	@When("^I input my username and my password and click submit$")
	public void i_input_my_username_and_my_password_and_click_submit(List<Credentials> credentials) throws Throwable {
		MercuryLoginFactory mlf = new MercuryLoginFactory(driver);
		for(Credentials c : credentials){
			mlf.driverLogIntoMercury(c.getUsername(), c.getPassword());
			driver.findElement(By.xpath("//a[contains(text(),'Home')]")).click();
		}

	}

	@Then("^I shall be redirected to the find flights page$")
	public void i_shall_be_redirected_to_the_find_flights_page() throws Throwable {
		System.out.println(driver.getTitle());
		assertEquals("Find a Flight: Mercury Tours:", driver.getTitle());
		//driver.quit();
	}
	
	@After
	public void after(Scenario scenario){
		if(driver!=null){
			driver.quit();
		}
		
	}
}
