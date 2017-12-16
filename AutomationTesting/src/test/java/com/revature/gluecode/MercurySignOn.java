package com.revature.gluecode;

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

public class MercurySignOn {
	public static WebDriver driver;
	
	@Given("^I am at the sign-on screen for Mercury Tours$")
	public void i_am_at_the_sign_on_screen_for_Mercury_Tours() throws Throwable {
		System.setProperty("webdriver.chrome.driver", "Drivers/chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("http://newtours.demoaut.com/mercurysignon.php");
		System.out.println(driver.getTitle());
	}
	
	@When("^I input my username and my password and click submit$")
	public void i_input_my_username_and_my_password_and_click_submit(List<Credential> credentials) throws Throwable {
		MercuryLoginFactory mlf = new MercuryLoginFactory(driver);
		// for each credential in credentials
		for (Credential c : credentials)
		{
			// log in with credential
			mlf.driverLogIntoMercury(c.getUsername(), c.getPassword());
			// click the SIGN-OFF button
			driver.findElement(By.xpath("//a[contains(text(), 'SIGN-OFF')]")).click();
		}
	}
	
	@Then("^I shall be redirected to the flight finder page$")
	public void i_shall_be_redirected_to_the_flight_finder_page() throws Throwable {
	     System.out.println(driver.getTitle());
	     driver.quit();
	}
	
	@After 
	public void after(Scenario scenario)
	{
		driver.quit();
	}
}
