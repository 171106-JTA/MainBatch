package com.revature.testsuites;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

public class NewTest {
	/*
	 * With TestNG, we get access to more before/after options than our 
	 * JUnit predecessor.
	 * Each @Test method is considered a 'method'. Thus, the before/after method
	 * annotations will trigger in between.
	 * Should you utilize other classes within this test file, the before/after
	 * class method will run for each instantiation of a class.
	 * Should you utilize multiple Test Classes (IE this entire file), which would
	 * be done within a test suite, then in between each test class, the before/after
	 * test method will trigger.
	 * And should you run multiple suites, then the before/after suite methods will
	 * trigger each time.
	 * 
	 * As with JUnit, @Test signifies some kind of test to run.
	 */
	
	//Utilize the priority attribute for a test to determine run order.
  @Test(priority=367586976)
  public void f1() {
	  System.out.println("Test 1 Executes");
  }
 
  //Use enabled property to skip a test
  @Test(priority=1, enabled=false)
  public void f2() {
	  System.out.println("Test 2 Executes");

  }
  
  @Test(priority=2)
  public void f3() {
	  System.out.println("Test 3 Executes");
  }
  @BeforeMethod
  public void beforeMethod() {
	  System.out.println("====BEFORE METHOD====");

  }

  @AfterMethod
  public void afterMethod() {
	  System.out.println("====AFTER METHOD====");
  }

  @BeforeClass
  public void beforeClass() {
	  System.out.println("====BEFORE CLASS====");
  }

  @AfterClass
  public void afterClass() {
	  System.out.println("====AFTER CLASS====");
  }

  @BeforeTest
  public void beforeTest() {
	  System.out.println("====BEFORE TEST====");
  }

  @AfterTest
  public void afterTest() {
	  System.out.println("====AFTER TEST====");
  }

  @BeforeSuite
  public void beforeSuite() {
	  System.out.println("====BEFORE SUITE====");
  }

  @AfterSuite
  public void afterSuite() {
	  System.out.println("====AFTER SUITE====");
  }

}
