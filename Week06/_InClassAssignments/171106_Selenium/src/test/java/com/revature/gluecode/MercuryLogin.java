package com.revature.gluecode;

import org.openqa.selenium.WebDriver;

import com.revature.pages.MercuryLoginFactory;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class MercuryLogin {
	public static WebDriver driver; 

	@Given("^I am at the login screen for Mercury Tours$")
	public void i_am_at_the_login_screen_for_Mercury_Tours() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		System.setProperty("webdriver.chrome.driver", "Drivers/chromedriver.exe"); 
		driver.get("http://newtours.demoaut.com"); 
		System.out.println(driver.getTitle());
	    throw new PendingException();
	}

	@When("^I input my username and password and click submit$")
	public void i_input_my_username_and_password_and_click_submit() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		MercuryLoginFactory mlf = new MercuryLoginFactory(driver);
		mlf.driverLogIntoMercury("bobbert", "bobbert"); 
	    throw new PendingException();
	}

	@Then("^I shall be redirected to he find flights page$")
	public void i_shall_be_redirected_to_he_find_flights_page() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		System.out.println(driver.getTitle());
	    throw new PendingException();
	}



}
