import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(strict = true,
features = {"/Users/charlielee/Desktop/demos/java/unfinished/cucumberjavabrowserstack/src/test/java/resources/features/Search.feature"},
plugin = {"junit:/Users/charlielee/Desktop/demos/java/unfinished/cucumberjavabrowserstack/target/cucumber_reports/test_results/1.junit"},
monochrome = true,
 tags = {"~@ignore"}, glue = { "com.browserstack.stepdefs" })
public class Search_Parallel01IT {
}
