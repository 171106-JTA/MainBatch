package com.revature.util.hybrid;

import java.util.Properties;

import org.openqa.selenium.WebDriver;

import com.revature.pages.phptravels.*;

public class KeywordExecutor {

	private WebDriver driver;

	public KeywordExecutor(WebDriver driver) {
		this.driver = driver;
	}

	public void execute(Properties p, String keyword, String pageName, String elementName, String value)
			throws Exception {

		switch (keyword.toLowerCase()) {
		case "click":
			getPage(pageName).getWebElement(elementName).click();
			break;
		case "settext":
			getPage(pageName).getWebElement(elementName).sendKeys(value);
			break;
		case "geturl":
			driver.get(p.getProperty(value));
			break;
		default:
			throw new Exception("Unsupported keyword: " + keyword);
		}
	}

	public String read(String keyword, String pageName, String elementName, String value) throws Exception {
		switch (keyword.toLowerCase()) {
		case "gettext":
			return getPage(pageName).getWebElement(elementName).getText();
		case "gettitle":
			return driver.getTitle();
		default:
			throw new Exception("Unsupported keyword: " + keyword);
		}
	}

	private Page getPage(String page) throws Exception {

		return (Page) Class.forName(page).newInstance();

	}
}
