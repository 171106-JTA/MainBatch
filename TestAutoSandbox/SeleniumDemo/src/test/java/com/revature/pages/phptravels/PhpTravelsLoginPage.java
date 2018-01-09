package com.revature.pages.phptravels;

import java.lang.reflect.Field;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PhpTravelsLoginPage extends Page {

	private WebDriver driver;

	@FindBy(name = "username")
	private WebElement usernameBox;

	@FindBy(name = "password")
	private WebElement passwordBox;

	@FindBy(xpath = "//*[@id=\"loginfrm\"]/div[1]/div[5]/button")
	private WebElement submitBtn;

	public PhpTravelsLoginPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void setUsername(String text) {
		usernameBox.clear();
		usernameBox.sendKeys(text);
	}

	public void setPassword(String text) {
		passwordBox.clear();
		passwordBox.sendKeys(text);
	}

	public void clickLogin() {
		submitBtn.click();
	}

	@Override
	public WebElement getWebElement(String elementName)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = this.getClass().getDeclaredField(elementName);
		field.setAccessible(true);
		return (WebElement) field.get(this);
	}

}
