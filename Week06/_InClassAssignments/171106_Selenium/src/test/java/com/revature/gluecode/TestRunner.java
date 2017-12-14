package com.revature.gluecode;

import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.*; 

@RunWith(Cucumber.class)

/*
 * Should i veer wnat to only run specific features. I can
 * point to a different folder or place the direct path to a specific feature in the cucumber 
 * options. Makew sure the glue assigned to each feature is also
 * pointed at properly. 
 */
public class TestRunner {
	
	@CucumberOptions(
			features="Features",
			glue="com.revature.gluecode"	
	)

}
