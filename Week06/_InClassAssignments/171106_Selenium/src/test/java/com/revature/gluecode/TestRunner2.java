package com.revature.gluecode;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
			features = "Features/MercuryLogin2.feature",
			glue= "com.revature.gluecode",
			tags= {"@smoke,@regression"}
		)
/*
 * When saying which tags to use in cucumber options,
 * You have the following options:
 * Excluding tags: tags={"~@tagname"}
 * Including tests that have ALL the tags applied to them:
 * 		tags={"@tag1","@tag2"}
 * Tests that have either/or tags:
 * 		tags={"@tag1,@tag2"}
 */
public class TestRunner2 {

}
