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

public class MercuryLogin {
	public static WebDriver driver;
	/*@ClassRule
	public static TestWatcher closeResourcesOnFail = new TestWatcher(){
		@Override
		protected void failed(Throwable e, org.junit.runner.Description description) {
			driver.quit();
		};
	};*/
	
	@Given("^I am at the login screen for Mercury Tours$")
	public void i_am_at_the_login_screen_for_Mercury_Tours() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		System.setProperty("webdriver.chrome.driver", "Drivers/chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("http://newtours.demoaut.com");
		System.out.println(driver.getTitle());
		// NOTE: a PendingException is here by default just to show that the test has not been implemented yet.
	}

	@When("^I input my username and my password and click sign in")
	public void i_input_my_username_and_my_password_and_click_sign_in(List<Credential> credentials) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    // For automatic transformation, change DataTable to one of
	    // List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
	    // E,K,V must be a scalar (String, Integer, Date, enum etc)
	    MercuryLoginFactory mlf = new MercuryLoginFactory(driver);
	    for (Credential c : credentials)
	    {
	    	mlf.driverLogIntoMercury(c.getUsername(), c.getPassword());
	    	driver.findElement(By.xpath("//a[contains(text(), 'Home')]")).click();
	    }
	}

	@Then("^I shall be redirected to the find flights page$")
	public void i_shall_be_redirected_to_the_find_flights_page() throws Throwable {
	    System.out.println(driver.getTitle());
	    driver.quit();
	}

	@After 
	public void after(Scenario scenario)
	{
		driver.quit();
	}

}
