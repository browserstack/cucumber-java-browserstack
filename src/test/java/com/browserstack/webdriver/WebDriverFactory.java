package com.browserstack.webdriver;

import com.browserstack.util.Utility;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WebDriverFactory {

    private static volatile WebDriverFactory instance;
    private final JSONObject testConfig = this.parseWebDriverConfig();

    private WebDriverFactory() {
    }

    public static WebDriverFactory getInstance() {
        if (instance == null) {
            synchronized(WebDriverFactory.class) {
                if (instance == null) {
                    instance = new WebDriverFactory();
                }
            }
        }

        return instance;
    }

    private JSONObject parseWebDriverConfig() {
        JSONParser parser = new JSONParser();
        String capabilitiesConfigFile = System.getProperty("caps", "src/test/resources/conf/single.conf.json");
        try {
            JSONObject testConfig = (JSONObject) parser.parse(new FileReader(capabilitiesConfigFile));
            return testConfig;
        } catch (IOException | ParseException var6) {
            throw new Error("Unable to parse capabilities file " + capabilitiesConfigFile, var6);
        }
    }

    public List<JSONObject> getPlatforms() {
        JSONArray environments = (JSONArray) testConfig.get("environments");
        List<JSONObject> platforms = new ArrayList<>();
        for (Object obj : environments) {
            JSONObject singleConfig = Utility.getCombinedCapability((Map<String, String>) obj, testConfig);
            platforms.add(singleConfig);
        }
        return platforms;
    }

    public WebDriver createWebDriverForPlatform(JSONObject platform, String testName) {
        try {
            String URL = String.format("https://%s/wd/hub", testConfig.get("server"));
            DesiredCapabilities caps = new DesiredCapabilities(platform);
            caps.setCapability("name", testName);
            return new RemoteWebDriver(new URL(URL), caps);
        } catch (MalformedURLException var4) {
            throw new Error("Unable to create WebDriver", var4);
        }
    }

}
