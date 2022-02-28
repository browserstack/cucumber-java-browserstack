package com.browserstack.stepdefs;

import com.browserstack.TestRunner;
import com.browserstack.local.Local;
import com.browserstack.pageobjects.SearchPage;
import com.browserstack.util.Utility;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SearchSteps {
    private WebDriver driver;
    private SearchPage searchPage;
    private Local l;

    @Before
    public void setUp(Scenario scenario) throws Exception {
        JSONObject capability = TestRunner.threadLocalValue.get();
        String URL = String.format("https://%s/wd/hub",System.getProperty("server"));

        DesiredCapabilities caps = new DesiredCapabilities(capability);
        caps.setCapability("name", scenario.getName());
        if (caps.getCapability("browserstack.local")!=null && caps.getCapability("browserstack.local").toString().equals("true")) {
            l = new Local();
            Map<String, String> options = new HashMap<String, String>();
            options.put("key", caps.getCapability("browserstack.key").toString());
            l.start(options);
        }

        driver = new RemoteWebDriver(new URL(URL), caps);
        searchPage = new SearchPage(driver);
    }

    @Given("^I am on the website '(.+)'$")
    public void I_am_on_the_website(String url) throws Throwable {
        driver.get(url);
        Thread.sleep(2000);
    }

    @When("^I submit the search term '(.+)'$")
    public void I_submit_the_search_term(String searchTerm) throws Throwable {
        searchPage.enterSearchTerm(searchTerm);
        searchPage.submitSearch();
        Thread.sleep(2000);
    }

    @Then("the page title should be '(.+)'$")
    public void I_should_see_pagetitle(String expectedTitle) throws Throwable {
        assertEquals(expectedTitle, driver.getTitle());
    }

    @Then("the page should contain '(.+)'$")
    public void page_should_contain(String expectedTitle) throws Throwable {
        assertTrue(driver.getPageSource().contains(expectedTitle));
    }

    @After
    public void teardown(Scenario scenario) throws Exception {
        if (scenario.isFailed()) {
            Utility.setSessionStatus(driver, "failed", String.format("%s failed.", scenario.getName()));
        } else {
            Utility.setSessionStatus(driver, "passed", String.format("%s passed.", scenario.getName()));
        }
        Thread.sleep(2000);
        driver.quit();
        if (l != null) l.stop();
    }
}