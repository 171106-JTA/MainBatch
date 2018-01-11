package com.revature.pages.phptravels;

import java.lang.reflect.Field;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PhpTravelsLandingPage extends Page {

	WebDriver driver;

	@FindBy(partialLinkText = "MY ACCOUNT")
	WebElement accountTab;

	@FindBy(partialLinkText = "Login")
	WebElement loginBtn;

	@FindBy(partialLinkText = "Sign Up")
	WebElement signupBtn;

	@FindBy(xpath = "/html/body/div[3]/div/div/div[2]/ul/ul/li[2]")
	WebElement currencyElement;

	@FindBy(xpath = "/html/body/div[3]/div/div/div[2]/ul/ul/li[2]/a")
	WebElement currency;

	@FindBy(xpath = "/html/body/div[3]/div/div/div[2]/ul/ul/ul/li")
	WebElement languageElement;

	@FindBy(xpath = "/html/body/div[3]/div/div/div[2]/ul/ul/ul/li/a")
	WebElement language;

	Actions action;

	public PhpTravelsLandingPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		action = new Actions(driver);
		PageFactory.initElements(driver, this);
	}

	public void clickRegister() {
		accountTab.click();
		signupBtn.click();
	}

	public void clickLogin() {
		accountTab.click();
		loginBtn.click();
	}

	public void setCurrency(String currency) {
		action.moveToElement(currencyElement).build().perform();
		By byCurrency = By.linkText(currency);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(byCurrency));
		driver.findElement(byCurrency).click();
		if (!this.currency.getText().contains(currency)) {
			WebDriverWait staleWait = new WebDriverWait(driver, 5);
			staleWait.until(ExpectedConditions.textToBePresentInElement(this.currency, currency));
		}
	}

	public String getCurrency() {
		return currency.getText();
	}

	public void setLanguage(int idx, String lang) {
		action.moveToElement(languageElement).build().perform();
		By byLanguage = By.xpath("/html/body/div[3]/div/div/div[2]/ul/ul/ul/li/ul/li["+idx+"]");
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(byLanguage));
		driver.findElement(byLanguage).click();
		if (!language.getText().contains(lang)) {
			WebDriverWait staleWait = new WebDriverWait(driver, 5);
			staleWait.until(ExpectedConditions.textToBePresentInElement(language, lang));
		}
	}

	public String getLanguage() {
		return language.getText();
	}

	@Override
	public WebElement getWebElement(String elementName) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = this.getClass().getDeclaredField(elementName);
		field.setAccessible(true);
		return (WebElement) field.get(this);


	}
}
