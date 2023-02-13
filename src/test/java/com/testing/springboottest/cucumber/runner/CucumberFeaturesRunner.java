package com.testing.springboottest.cucumber.runner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = "src/test/resources/features", // Indiquer le dossier ressource où se trouvent les features
		glue = "com.testing.springboottest.cucumber.steps", // Indiquer le package où se trouvent les steps
		plugin = { // Generation des rapports sur différents format
		"pretty",
		"html:target/reports/cucumber.html", 
		"json:target/reports/cucumber.json",
		"junit:target/reports/cucumber.xml",
		"rerun:target/rerun.txt" // pour réexécuter les test qui ont échoués
		},
		dryRun = false, // mode simulation (dryRun = true), sert à voir si tous les scenarios sont implémentés dans les steps
		monochrome = true, // Gestion des caractères spéciaux dans la console
		tags = "@student" 
// tags = "@student and @login"
// tags = "@Student or @login"
// tags = "@student and not @ignore"
)
public class CucumberFeaturesRunner {

}
