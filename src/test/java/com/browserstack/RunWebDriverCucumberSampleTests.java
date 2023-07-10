package com.browserstack;

import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features/test")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.browserstack.stepdefs")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty, json:reports/tests/cucumber/json/cucumberTestReport.json")
public class RunWebDriverCucumberSampleTests {
}
