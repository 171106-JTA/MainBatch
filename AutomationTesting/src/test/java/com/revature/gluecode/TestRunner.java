package com.revature.gluecode;

import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
/*
 * Should I ever want to only run specific features. I can point to a different folder or place the
 * direct path to a specific feature in the Cucumber options.
 * Make sure the gluecode assigned to each feature is also pointed at properly.
 */
@CucumberOptions(
		features="Features/MercurySignOn.feature",
		glue="com.revature.gluecode"
	)
public class TestRunner {
	
}
