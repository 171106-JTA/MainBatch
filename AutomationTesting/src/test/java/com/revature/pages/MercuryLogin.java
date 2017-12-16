package com.revature.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MercuryLogin {
	/*
	 * Page Object Model is a design pattern used within test automation. It stems from creating a class to represent 
	 * each and every page of the application. One class per page.
	 * The class will serve as an object repository for that page, that is, every interactable element for that page
	 * should be identified and given an instance within that class.
	 */
	// You will always pass in the current WebDriver
	private WebDriver _driver;
	
	// next you need to build your repository for web elements.
	private By _username = By.xpath("//input[@name='login']");
	private By _password = By.xpath("//input[@name='password']");
	private By _login = By.xpath("//input[@name='login']");
	
	public MercuryLogin(WebDriver driver)
	{
		_driver = driver;
	}
	
	public void inputUsername(String userName)
	{
		_driver.findElement(_username).sendKeys(userName);
		
	}
	
	public void inputPassword(String password) {
		_driver.findElement(_password).sendKeys(password);
	}
	
	public void submitLogin() { 
		_driver.findElement(_login).click();
	}
	public void driverLogIntoMercury(String username, String password) { 
		inputUsername(username);
		inputPassword(password);
		submitLogin();
	}
}
