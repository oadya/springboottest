package com.testing.springboottest.cucumber.runner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = "src/test/resources/features",
		glue = "com.testing.springboottest.cucumber.steps",
		plugin = {
		"pretty",
		"html:target/reports/cucumber.html", 
		"json:target/reports/cucumber.json",
		"junit:target/reports/cucumber.xml",
		"rerun:target/rerun.txt" 
		},
		dryRun = false,
		monochrome = true,
		tags = "@student"
// tags = "@student and @login"
// tags = "@Student or @login"
// tags = "@student and not @ignore"
)
public class CucumberFeaturesRunner {

}
