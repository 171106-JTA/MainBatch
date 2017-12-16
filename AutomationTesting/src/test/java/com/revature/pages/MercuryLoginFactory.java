package com.revature.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MercuryLoginFactory {
	
	
	/*
	 * PageFactory is a class that aids in implementing Page Object Model design patterns. It seeks to inject the 
	 * proper instances of WebElements upon instantiation of a class. It provides a more efficient way to implement
	 * POM as opposed to implementing it manually.
	 */
	// next you need to build your repository for web elements.
	@FindBy(xpath="//input[@name='userName']")
	WebElement _username; 
	@FindBy(xpath="//input[@name='password']")
	WebElement _password; 
	@FindBy(xpath="//input[@name='login']")
	WebElement _login;
	
	public MercuryLoginFactory(WebDriver driver)
	{
		PageFactory.initElements(driver, this);
	}
	
	public void inputUsername(String userName)
	{
		_username.sendKeys(userName);
		
	}
	
	public void inputPassword(String password) {
		_password.sendKeys(password);
	}
	
	public void submitLogin() { 
		_login.click();
	}
	public void driverLogIntoMercury(String username, String password) { 
		inputUsername(username);
		inputPassword(password);
		submitLogin();
	}
}
