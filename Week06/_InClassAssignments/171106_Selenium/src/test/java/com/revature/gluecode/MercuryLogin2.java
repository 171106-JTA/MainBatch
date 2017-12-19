package com.revature.gluecode;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.revature.pages.MercuryLoginFactory;

import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class MercuryLogin2 {
	public static WebDriver driver;
	
	@Given("^I am at the landing page of Mercury$")
	public void i_am_at_the_landing_page_of_Mercury() throws Throwable {
		System.setProperty("webdriver.chrome.driver", "Drivers/chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("http://newtours.demoaut.com");
		assertEquals("Welcome: Mercury Tours", driver.getTitle());
	}

	@When("^I login with \"([^\"]*)\" and password \"([^\"]*)\"$")
	public void i_login_with_and_password(String arg1, String arg2) throws Throwable {
		MercuryLoginFactory mlf = new MercuryLoginFactory(driver);
		mlf.driverLogIntoMercury(arg1, arg2);

	}

	@Then("^I should be at the find flights page$")
	public void i_should_be_at_the_find_flights_page() throws Throwable {
		assertEquals("Find a Flight: Mercury Tours:", driver.getTitle());
	}

	@When("^I login with \"([^\"]*)\" and \"([^\"]*)\"$")
	public void i_login_with_and(String arg1, String arg2) throws Throwable {
		MercuryLoginFactory mlf = new MercuryLoginFactory(driver);
		mlf.driverLogIntoMercury(arg1, arg2);
	}

	@Then("^I arrive at the findflights page$")
	public void i_arrive_at_the_findflights_page() throws Throwable {
		assertEquals("Find a Flight: Mercury Tours:", driver.getTitle());
	}
	
	/*
	 * @After is one of the cucumber hooks that can be applied to your glue
	 * code specifically. It allows this action to execute after every test.
	 */
	
	/*
	 * We can use taghooks in order to trigger @Before or
	 * @After methods for specific tags, as opposed to after
	 * every single scenario.
	 */
	@After("@smoke")
	public void testTeardownSmoke(){
		if(driver!=null){
			driver.quit();
		}
	}
	@After("@regression")
	public void testTeardownRegression(){
		if(driver!=null){
			driver.quit();
		}
	}
}
