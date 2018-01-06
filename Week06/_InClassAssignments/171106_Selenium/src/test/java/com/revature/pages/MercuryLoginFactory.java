package com.revature.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MercuryLoginFactory {

	/*
	 * PageFactory is a class that aids in implementing Page Object Model
	 * design patterns. It seeks to inject the proper instances of webelements
	 * upon instantiation of a class. It provides are more efficient way
	 * to implement POM as opposed to implementing it manually.
	 */
	@FindBy(xpath="//input[@name='userName']")
	WebElement username;
	@FindBy(xpath="//input[@name='password']")
	WebElement password;
	@FindBy(xpath="//input[@name='login']")
	WebElement login;
	
	public MercuryLoginFactory(WebDriver driver){
		PageFactory.initElements(driver, this);
	}
	
	public void inputUsername(String username){
		this.username.sendKeys(username);
	}
	
	public void inputPassword(String password){
		this.password.sendKeys(password);
	}
	
	public void submitLogin(){
		this.login.click();
	}
	
	public void driverLogIntoMercury(String username, String password){
		inputUsername(username);
		inputPassword(password);
		submitLogin();
	}
}
