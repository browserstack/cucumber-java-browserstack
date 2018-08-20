package com.browserstack.stepdefs.API;

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
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.MatcherAssert.*;


public class checkUniformUDxml {

    @Given("^I ams on the website '(.+)'$")
    public void I_ams_on_the_website(String url) throws Throwable {
        // Specify the base URL to the RESTful web service
        RestAssured.baseURI = "http://api.plos.org/search?q=title:DNA";

        // Get the RequestSpecification of the request that you want to sent
        // to the server. The server is specified by the BaseURI that we have
        // specified in the above step.
        RequestSpecification httpRequest = given();

        // Make a GET request call directly by using RequestSpecification.get() method.
        // Make sure you specify the resource name.
        Response response1 = httpRequest.get(url);

        Response response2 = httpRequest.get(url);

        // Response.asString method will directly return the content of the body
        // as String.
        System.out.println("Response Body is =>  " + response1.asString() + response2.asString());
        Assert.assertEquals("The queries should be equal and the same", response1.asString(), response2.asString());

    }

    @When("^Check query content for the right properties '(.+)'$")
    public void Check_query_content_for_the_right_properties(String url2) throws Throwable {
        given().when().get(url2).then()
                .body(containsString("publication_date"))
                .body(containsString("2007-03-14T00:00:00Z"));

}

   /* @Then("^paas$")
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

    }*/

}

