package com.browserstack.stepdefs;

import com.browserstack.pageobjects.HomePage;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.testng.Assert;

public class StackDemoSteps {
    private WebDriver driver;
    private HomePage homePage;

    @Before
    public void setUp() throws MalformedURLException {
        MutableCapabilities capabilities = new MutableCapabilities();
        HashMap<String, String> bstackOptions = new HashMap<>();
        bstackOptions.putIfAbsent("source", "cucumber-java:sample-master:v1.2");
        capabilities.setCapability("bstack:options", bstackOptions);
        driver = new RemoteWebDriver(
                new URL("https://hub.browserstack.com/wd/hub"), capabilities);
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
        Assert.assertEquals(homePage.getSelectedProductName(), homePage.getProductCartText());
    }

    @Given("^I verify the website URL is '(.+)'$")
    public void I_verify_the_website_URL(String url) throws Throwable {
        driver.get(url);
        Thread.sleep(2000);
    }

    @When("^I choose an item and tap on 'Add to cart' action")
    public void I_choose_an_item_and_tap_add_to_cart() throws Throwable {
        homePage.selectFirstProductName();
        homePage.clickAddToCartButton();
        Thread.sleep(2000);
    }

    @Then("the item should be placed in cart")
    public void item_should_be_placed_in_cart() {
        homePage.waitForCartToOpen();
        Assert.assertEquals(homePage.getSelectedProductName(), homePage.getProductCartText());
    }

    @Then("^I confirm the page title should contain '(.+)'$")
    public void I_confirm_page_title_should_contain(String expectedTitle) {
        Assert.assertTrue(driver.getTitle().contains(expectedTitle));
    }

    @When("^I pick a merchandise and press on 'Add to cart' function")
    public void I_pick_merchandise_and_press_add_to_cart() throws Throwable {
        homePage.selectFirstProductName();
        homePage.clickAddToCartButton();
        Thread.sleep(2000);
    }

    @Then("the merchandise should be inserted in cart")
    public void merchandise_should_be_inserted_in_cart() {
        homePage.waitForCartToOpen();
        Assert.assertEquals(homePage.getSelectedProductName(), homePage.getProductCartText());
    }

    @Given("^I validate the website location is '(.+)'$")
    public void I_validate_website_location(String url) throws Throwable {
        driver.get(url);
        Thread.sleep(2000);
    }

    @When("^I grab an article and click on 'Add to cart' feature")
    public void I_grab_article_and_click_add_to_cart() throws Throwable {
        homePage.selectFirstProductName();
        homePage.clickAddToCartButton();
        Thread.sleep(2000);
    }

    @Then("the page title should contain '(.+)'$")
    public void page_title_should_contain(String expectedTitle) {
        Assert.assertTrue(driver.getTitle().contains(expectedTitle));
    }

    @After
    public void teardown(Scenario scenario) throws Exception {
        Thread.sleep(2000);
        driver.quit();
    }
}
