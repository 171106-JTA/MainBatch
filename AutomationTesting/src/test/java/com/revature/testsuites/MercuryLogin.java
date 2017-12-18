package com.revature.testsuites;

import java.io.File;
import java.io.FileInputStream;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.revature.pages.MercuryLoginFactory;


public class MercuryLogin {
	private static WebDriver driver;
	private String url = "http://newtours.demoaut.com";
  @Test(priority=0, groups={"smoke"})
  public void validateLandingPage() {
	Assert.assertEquals(driver.getTitle(), "Welcome: Mercury Tours");  
  }
  
  @Test(dependsOnMethods="validateLandingPage", priority=2, groups={"regression", "somethingElse"}, dataProvider="provideAccountDetailsDynamic")
  public void loginToMercury(String username, String password)
  {
	  MercuryLoginFactory mlf = new MercuryLoginFactory(driver);
	  mlf.driverLogIntoMercury(username, password);
	  Assert.assertEquals(driver.getTitle(), "Find a Flight: Mercury Tours:");
	  
	  driver.findElement(By.xpath("//a[contains(text(), 'Home')]")).click();
  }
  
  @DataProvider
  public Object[][] provideAccountDetails() { 
	  return new Object[][] { 
		  { "bobbert", "bobbert" },
		  { "jakeFromStateFarm", "khakis" }
	  };
  }
  
  @DataProvider
  public Object[][] provideAccountDetailsDynamic() throws Exception { 
	  File file = new File("src/test/resources/mercuryData.xlsx");
	  FileInputStream fis = new FileInputStream(file);
	  
	  Workbook workbook = new XSSFWorkbook(fis);
	  Sheet sheet = workbook.getSheet("sheet1");
	  
	  int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
	  Object[][] data = new Object[rowCount][2];
	  
	  /*
	   * Data driven framework example.
	   * • This is a design patern for test automation where you develop the tests in a manner where they will run
	   * 	based on provided data. In this case, a tester could have 3 rows data, warranting the test to run 3 
	   * 	separate times with the given values. 
	   * 	This allows for configurable automation tests at the hands of a non-developer.
	   */
	  for (int i = 1; i <= rowCount; i++)
	  {
		  Row row = sheet.getRow(i);
		  data[i-1] = new Object[] { 
			  row.getCell(0).getStringCellValue(),
			  row.getCell(1).getStringCellValue()
		  };
	  }
	  
	  return data;
  }
  
  @BeforeTest(groups= {"smoke"})
  public void beforeTest() {
	  System.setProperty("webdriver.chrome.driver", "Drivers/chromedriver.exe");
	  driver = new ChromeDriver();
	  driver.get(url);
	  driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
	  
  }

  @AfterTest(groups= {"smoke", "regression"})
  public void afterTest() {
	  driver.quit();
  }

}
