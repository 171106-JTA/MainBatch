package com.revature.pages.phptravels;

import java.lang.reflect.Field;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PhpTravelsDashboard extends Page {

	private WebDriver driver;

	public PhpTravelsDashboard(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}

	@Override
	public WebElement getWebElement(String elementName)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = this.getClass().getDeclaredField(elementName);
		field.setAccessible(true);
		return (WebElement) field.get(this);
	}

}
