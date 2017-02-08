import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(strict = true,
features = {"/Users/charlielee/Desktop/temp/cucumber-jvm-java-browserstack/src/test/java/resources/features/Local.feature"},
plugin = {"junit:/Users/charlielee/Desktop/temp/cucumber-jvm-java-browserstack/target/cucumber_reports/test_results/1.junit"},
monochrome = true,
 tags = {"~@ignore"}, glue = { "com.browserstack.stepdefs" })
public class Local_Parallel01IT {
}
