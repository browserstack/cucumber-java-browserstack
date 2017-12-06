package com.browserstack.stepdefs;

import com.browserstack.local.Local;
import com.browserstack.pageobjects.SearchPage;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.assertion.BodyMatcher;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;
import static io.restassured.RestAssured.responseSpecification;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by Lucho on 04/12/2017.
 */
public class bla {
    private WebDriver driver;
    private SearchPage searchPage;
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

    @Given("^I ams on the website '(.+)'$")
    public void I_ams_on_the_website(String url) throws Throwable {
        // Specify the base URL to the RESTful web service
        RestAssured.baseURI = "http://dev.embodee.com/services/factoryxml/";

        // Get the RequestSpecification of the request that you want to sent
        // to the server. The server is specified by the BaseURI that we have
        // specified in the above step.
        RequestSpecification httpRequest = given();

        // Make a GET request call directly by using RequestSpecification.get() method.
        // Make sure you specify the resource name.
        Response response1 = httpRequest.get("/UD5a256d96492ec");

        Response response2 = httpRequest.get("/UD5a268ac5b5ee9");

        // Response.asString method will directly return the content of the body
        // as String.
        System.out.println("Response Body is =>  " + response1.asString() + response2.asString());
        Assert.assertEquals("Mani mani q da viim", response1.asString(), response2.asString());

    }

    @When("^sss$")
    public void sss() throws Throwable {
        given().when().get("http://dev.embodee.com/services/factoryxml/UD5a256d96492ec").then()
                .body(containsString("graphic"))
                .body(containsString("id"));

}

    @Then("^paas$")
    public void paas() throws Throwable {
        // Specify the base URL to the RESTful web service
        RestAssured.baseURI = "http://dev.embodee.com/services/factoryxml/";

        // Get the RequestSpecification of the request that you want to sent
        // to the server. The server is specified by the BaseURI that we have
        // specified in the above step.
        RequestSpecification httpRequest = given();

        // Make a GET request call directly by using RequestSpecification.get() method.
        // Make sure you specify the resource name.
        Response response2 = httpRequest.get("/UD5a256d96492ec");

        // Response.asString method will directly return the content of the body
        // as String.
        System.out.println("Response Body is =>  " + response2.asString());

    }

}

