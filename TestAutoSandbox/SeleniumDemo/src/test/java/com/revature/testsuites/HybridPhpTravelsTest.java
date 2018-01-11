package com.revature.testsuites;

import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import com.revature.util.drivermanager.DriverManager;
import com.revature.util.drivermanager.DriverManagerFactory;
import com.revature.util.drivermanager.DriverType;
import com.revature.util.hybrid.*;

public class HybridPhpTravelsTest {

	WebDriver driver;
	DriverManager manager;
	
	@Test(dataProvider = "hybridData")
	public void testLogin(String testName, String keyword, String pageName, 
			String elementName, String value) throws Exception {
		KeywordExecutor executor;
		Properties props;
		if(testName != null && testName.length() > 0) {
			manager = DriverManagerFactory.getManager(DriverType.CHROME);
			executor = new KeywordExecutor(manager.getDriver());
			props = PhpTravelsRepository.getRepo("PhpTravels");
			switch(keyword.toLowerCase()) {
			case "gettitle":
				String title = executor.read(keyword, pageName, elementName, value);
				Assert.assertTrue(title != null);
				Assert.assertEquals(title, value);
				break;
			case "gettext":
				String text = executor.read(keyword, pageName, elementName, value);
				Assert.assertTrue(text != null);
				Assert.assertEquals(text, value);
			default:
				executor.execute(props, keyword, pageName, elementName, value);
			}
		}		
	}
	
	@DataProvider(name = "hybridData")
	public Object[][] getData() throws IOException {
		Object[][] object = null;
		PhpTravelsExcelParser.readExcel(System.getProperty("user.dir") + "", "", "")
		return null;
	}
}
