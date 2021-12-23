package com.browserstack.stepdefs;

import com.browserstack.local.Local;
import com.browserstack.pageobjects.SearchPage;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
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
        String USERNAME = System.getenv("BROWSERSTACK_USERNAME");
        String ACCESS_KEY = System.getenv("BROWSERSTACK_ACCESS_KEY");
        String URL = "https://" + USERNAME + ":" + ACCESS_KEY + "@hub.browserstack.com/wd/hub";

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("browser", System.getProperty("browser"));

        if (System.getProperty("local") != null && System.getProperty("local").equals("true")) {
            caps.setCapability("browserstack.local", "true");
            l = new Local();
            Map<String, String> options = new HashMap<String, String>();
            options.put("key", ACCESS_KEY);
            l.start(options);
        }

        driver = new RemoteWebDriver(new URL(URL), caps);
        searchPage = new SearchPage(driver);
    }

    @Given("^I am on the website '(.+)'$")
    public void I_am_on_the_website(String url) throws Throwable {
        driver.get(url);
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
    public void teardown() throws Exception {
        driver.quit();
        if (l != null) l.stop();
    }
}
