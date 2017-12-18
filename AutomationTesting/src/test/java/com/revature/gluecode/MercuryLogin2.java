package com.revature.gluecode;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.revature.pages.MercuryLoginFactory;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertEquals;

public class MercuryLogin2 {
	
	public static WebDriver driver;

	@Given("^I am at the landing page of Mercury$")
	public void i_am_at_the_landing_page_of_Mercury() throws Throwable {
		System.setProperty("webdriver.chrome.driver", "Drivers/chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("http://newtours.demoaut.com");
		System.out.println(driver.getTitle());
	}

	@When("^I login with \"([^\"]*)\" and password \"([^\"]*)\"$")
	public void i_login_with_and_password(String arg1, String arg2) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    MercuryLoginFactory mlf = new MercuryLoginFactory(driver);
	    mlf.driverLogIntoMercury(arg1, arg2);
	}

	@Then("^I should be at the Find Flights page$")
	public void i_should_be_at_the_Find_Flights_page() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    assertEquals("Find a Flight: Mercury Tours:", driver.getTitle());
	}

	@Given("^I am at the landing of Mercury$")
	public void i_am_at_the_landing_of_Mercury() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    assertEquals("Find a Flight: Mercury Tours:", driver.getTitle());
	}

	@When("^I login with \"([^\"]*)\" and \"([^\"]*)\"$")
	public void i_login_with_and(String arg1, String arg2) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@Then("^I arive at the findflights page$")
	public void i_arive_at_the_findflights_page() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}


}
