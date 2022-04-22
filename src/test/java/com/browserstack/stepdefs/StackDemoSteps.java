package com.browserstack.stepdefs;

import com.browserstack.RunWebDriverCucumberTests;
import com.browserstack.pageobjects.HomePage;
import com.browserstack.util.Utility;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StackDemoSteps {
    private WebDriver driver;
    private HomePage homePage;

    @Before
    public void setUp() {
        driver = RunWebDriverCucumberTests.getManagedWebDriver().getWebDriver();
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
    }
}
