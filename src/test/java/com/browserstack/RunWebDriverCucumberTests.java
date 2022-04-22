package com.browserstack;

import com.browserstack.local.Local;
import com.browserstack.util.Utility;
import com.browserstack.webdriver.LazyInitWebDriverIterator;
import com.browserstack.webdriver.ManagedWebDriver;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
import io.cucumber.testng.TestNGCucumberRunner;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;

@CucumberOptions(
        glue = "com.browserstack.stepdefs",
        plugin = {
                "pretty",
                "html:reports/tests/cucumber/html",
                "timeline:reports/tests/cucumber/timeline",
                "junit:reports/tests/cucumber/junit/cucumber.xml",
                "testng:reports/tests/cucumber/testng/cucumber.xml",
                "json:reports/tests/cucumber/json/cucumber.json"
        }
)
public class RunWebDriverCucumberTests {

    private TestNGCucumberRunner testNGCucumberRunner;
    private Local local;
    private static final ThreadLocal<ManagedWebDriver> threadLocalWebDriver = new ThreadLocal<>();

    @BeforeClass(alwaysRun = true)
    public void setUpClass() {
        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
    }

    private synchronized static void setThreadLocalWebDriver(ManagedWebDriver managedWebDriver) {
        threadLocalWebDriver.set(managedWebDriver);
    }

    public synchronized static ManagedWebDriver getManagedWebDriver() {
        return threadLocalWebDriver.get();
    }

    @Test(groups = "cucumber", description = "Runs Cucumber Feature", dataProvider = "scenarios")
    public void feature(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper, ManagedWebDriver managedWebDriver) {
        if(Utility.isLocal(managedWebDriver) && local==null){
            local = new Local();
            Utility.startLocal(local, managedWebDriver);
        }
        managedWebDriver.setTestName(pickleWrapper.getPickle().getName());
        setThreadLocalWebDriver(managedWebDriver);
        testNGCucumberRunner.runScenario(pickleWrapper.getPickle());
    }

    @DataProvider(name = "scenarios", parallel = true)
    public Iterator<Object[]> scenarios() {
        Object[][] scenarios = testNGCucumberRunner.provideScenarios();
        return new LazyInitWebDriverIterator(true, scenarios);
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() {
        if(local != null){
            try {
                local.stop();
            } catch (Exception e) {
                throw new Error("Unable to stop BrowserStack Local.");
            }
        }
        if (testNGCucumberRunner == null) {
            return;
        }
        testNGCucumberRunner.finish();
    }

}
