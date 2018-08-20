package com.browserstack.stepdefs.gBuilder;

import com.browserstack.local.Local;
//import com.browserstack.pageobjects.SearchPage;
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

public class ImageComparison {
    private WebDriver driver;
    private SearchPage searchPage; // Променлива, идваща от SearchPage class-a, всеки път за нов патърн се прави нова променлива!
    private Local l;

    @Before
    public void setUp(Scenario scenario) throws Exception {
        String USERNAME = "ilianavajarova1";
        String AUTOMATE_KEY = "anQesbpRtAnE2aYhxQxd";
        String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("browser", System.getProperty("browser"));
        caps.setCapability("browser", "IE");
        caps.setCapability("browser_version", "6.0");
        caps.setCapability("os", "Windows");
        caps.setCapability("os_version", "XP");
        caps.setCapability("resolution", "1920x1080");

        if (System.getProperty("local") != null && System.getProperty("local").equals("true")) {
            caps.setCapability("browserstack.local", "false");
            l = new Local();
            Map<String, String> options = new HashMap<String, String>();
            options.put("key", AUTOMATE_KEY);
            l.start(options);
        }

        driver = new RemoteWebDriver(new URL(URL), caps);
        searchPage = new SearchPage(driver);
    }

    @Given("^I amq on the website '(.+)'$")
    public void I_am_on_the_website(String url) throws Throwable {
        driver.get(url);
    }

    @When("^I submitq the search term '(.+)'$")
    public void I_submit_the_search_term(String searchTerm) throws Throwable {
        searchPage.enterSearchTerm(searchTerm);
        searchPage.submitSearch();
    }

    @Then("the pageq title should be '(.+)'$")
    public void I_should_see_pagetitle(String expectedTitle) throws Throwable {
        assertEquals(expectedTitle, driver.getTitle());
    }

    @Then("the pageq should contain '(.+)'$")
    public void page_should_contain(String expectedTitle) throws Throwable {
        assertTrue(driver.getPageSource().contains(expectedTitle));
    }

    @After
    public void teardown() throws Exception {
        driver.quit();
        if (l != null) l.stop();
    }
}
