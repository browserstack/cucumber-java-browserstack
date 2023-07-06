package com.browserstack;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@CucumberOptions(
        glue = "com.browserstack.stepdefs",
        features = "src/test/resources/features/test",
        plugin = {
                "pretty",
                "junit:reports/tests/cucumber/junit/cucumber.xml",
                "json:reports/tests/cucumber/json/cucumberTestReport.json"
        }
)

@RunWith(Cucumber.class)
public class RunWebDriverCucumberTests {
}
