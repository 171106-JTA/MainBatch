package com.revature.pages.phptravels;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class Page {

	private WebDriver driver;
	
	public Page(WebDriver driver) {
		this.driver = driver;
	}
	
	public String getTitle() {
		return driver.getTitle();
	}

	public abstract WebElement getWebElement(String elementName) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException;
	
}
