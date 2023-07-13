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
import org.yaml.snakeyaml.Yaml;

import java.net.MalformedURLException;
import java.net.URL;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.*;

import org.testng.Assert;

public class StackDemoSteps {
    private WebDriver driver;
    private HomePage homePage;

    public static String userName, accessKey;
    public static Map<String, Object> browserStackYamlMap;
    public static final String USER_DIR = "user.dir";

    public StackDemoSteps() {
        File file = new File(getUserDir() + "/browserstack.yml");
        this.browserStackYamlMap = convertYamlFileToMap(file, new HashMap<>());
    }

    @Before
    public void setUp() throws MalformedURLException {
        MutableCapabilities capabilities = new MutableCapabilities();
        HashMap<String, String> bstackOptions = new HashMap<>();
        userName = System.getenv("BROWSERSTACK_USERNAME") != null ? System.getenv("BROWSERSTACK_USERNAME") : (String) browserStackYamlMap.get("userName");
        accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY") != null ? System.getenv("BROWSERSTACK_ACCESS_KEY") : (String) browserStackYamlMap.get("accessKey");
        bstackOptions.putIfAbsent("source", "cucumber-java:sample-cucumber-junit5:v1.0");
        capabilities.setCapability("bstack:options", bstackOptions);
        driver = new RemoteWebDriver(
                new URL(String.format("https://%s:%s@hub.browserstack.com/wd/hub", userName , accessKey)), capabilities);
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

    @Then("the page title should contain '(.+)'$")
    public void page_title_should_contain(String expectedTitle) {
        Assert.assertTrue(driver.getTitle().contains(expectedTitle));
    }

    @After
    public void teardown(Scenario scenario) throws Exception {
        Thread.sleep(2000);
        driver.quit();
    }

    private String getUserDir() {
        return System.getProperty(USER_DIR);
    }

    private Map<String, Object> convertYamlFileToMap(File yamlFile, Map<String, Object> map) {
        try {
            InputStream inputStream = Files.newInputStream(yamlFile.toPath());
            Yaml yaml = new Yaml();
            Map<String, Object> config = yaml.load(inputStream);
            map.putAll(config);
        } catch (Exception e) {
            throw new RuntimeException(String.format("Malformed browserstack.yml file - %s.", e));
        }
        return map;
    }
}
