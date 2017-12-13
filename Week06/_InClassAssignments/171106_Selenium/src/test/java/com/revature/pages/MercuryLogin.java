package com.revature.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MercuryLogin {
	/*
	 * Page Object Model is a design pattern used within test automation.
	 * It stems from creating a class to represent each and every page
	 * of the application. One class per page.
	 * The class will serve as an object repository for that page, that is, every
	 * interactable element for that page should be identified and given an
	 * instance within this class.
	 */
	//You will always pass in the current driver
	private WebDriver driver;
	
	//Next you need to build your repository for web elements
	private By username = By.xpath("//input[@name='userName']");
	private By password = By.xpath("//input[@name='password']");
	private By login = By.xpath("//input[@name='login']");
	
	public MercuryLogin(WebDriver driver){
		this.driver = driver;
	}
	
	public void inputUsername(String username){
		driver.findElement(this.username).sendKeys(username);
	}
	
	public void inputPassword(String password){
		driver.findElement(this.password).sendKeys(password);
	}
	
	public void submitLogin(){
		driver.findElement(login).click();
	}
	
	public void driverLogIntoMercury(String username, String password){
		inputUsername(username);
		inputPassword(password);
		submitLogin();
	}
}
