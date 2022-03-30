package com.browserstack.stepdefs;

import com.browserstack.TestRunner;
import com.browserstack.local.Local;
import com.browserstack.pageobjects.HomePage;
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

public class StackDemoSteps {
    private WebDriver driver;
    private HomePage homePage;
    private Local l;

    @Before
    public void setUp(Scenario scenario) throws Exception {
        JSONObject capability = TestRunner.threadLocalValue.get();
        String URL = String.format("https://%s/wd/hub",System.getProperty("server"));

        DesiredCapabilities caps = new DesiredCapabilities(capability);
        caps.setCapability("name", scenario.getName());
        if (caps.getCapability("browserstack.local")!=null && caps.getCapability("browserstack.local").toString().equals("true")) {
            l = new Local();
            Map<String, String> options = new HashMap<>();
            options.put("key", caps.getCapability("browserstack.key").toString());
            l.start(options);
        }

        driver = new RemoteWebDriver(new URL(URL), caps);
        homePage = new HomePage(driver);
    }

    @Given("^I am on the website '(.+)'$")
    public void I_am_on_the_website(String url) throws Throwable {
        driver.get(url);
        Thread.sleep(2000);
    }

    @When("^I select a product and click on 'Add to cart' button")
    public void I_select_a_product_and_add_to_cart() throws Throwable {
        homePage.selectFirstProductName();
        homePage.clickAddToCartButton();
        Thread.sleep(2000);
    }

    @Then("the product should be added to cart")
    public void product_should_be_added_to_cart() {
        homePage.waitForCartToOpen();
        assertEquals(homePage.getSelectedProductName(), homePage.getProductCartText());
    }

    @Then("the page should contain '(.+)'$")
    public void page_should_contain(String expectedTitle) {
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
