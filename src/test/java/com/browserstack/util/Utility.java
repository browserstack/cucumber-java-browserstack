package com.browserstack.util;

import com.browserstack.local.Local;
import com.browserstack.webdriver.ManagedWebDriver;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Utility {

    public static JSONObject getCombinedCapability(Map<String, String> envCapabilities, JSONObject config) {
        JSONObject capabilities = new JSONObject();
        JSONObject commonCapabilities = (JSONObject) config.get("capabilities");
        Iterator it = envCapabilities.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            capabilities.put(pair.getKey().toString(), pair.getValue());
        }
        it = commonCapabilities.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (capabilities.get(pair.getKey().toString()) == null) {
                capabilities.put(pair.getKey().toString(), pair.getValue());
            }
        }
        String username = System.getenv("BROWSERSTACK_USERNAME");
        if(username == null) {
            username = (String) config.get("user");
        }

        String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
        if(accessKey == null) {
            accessKey = (String) config.get("key");
        }
        JSONObject bstackOptions = (JSONObject) capabilities.get("bstack:options");
        if(bstackOptions == null)
            bstackOptions = new JSONObject();
        bstackOptions.put("userName", username);
        bstackOptions.put("accessKey", accessKey);
        bstackOptions.put("source", "cucumber-java:sample-selenium-4:v1.1");
        capabilities.put("bstack:options", bstackOptions);
        return capabilities;
    }

    public static void setSessionStatus(WebDriver webDriver, String status, String reason) {
        JavascriptExecutor jse = (JavascriptExecutor) webDriver;
        jse.executeScript(String.format("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"%s\", \"reason\": \"%s\"}}", status, reason));
    }

    public static boolean isLocal(ManagedWebDriver managedWebDriver) {
        JSONObject platform = managedWebDriver.getPlatform();
        Map<String, Object> bstackOptions = (Map<String, Object>) platform.get("bstack:options");
        return bstackOptions!=null && bstackOptions.get("local")!=null && bstackOptions.get("local").toString().equals("true");
    }

    public static void startLocal(Local local, ManagedWebDriver managedWebDriver) {
        JSONParser parser = new JSONParser();
        Map<String, String> options = new HashMap<>();
        JSONObject platform = managedWebDriver.getPlatform();
        Map<String, Object> bstackOptions = (Map<String, Object>) platform.get("bstack:options");
        options.put("key", bstackOptions.get("accessKey").toString());
        String capabilitiesConfigFile = System.getProperty("caps", "src/test/resources/conf/local.conf.json");
        try {
            JSONObject testConfig = (JSONObject) parser.parse(new FileReader(capabilitiesConfigFile));
            if(testConfig.containsKey("localOptions")) {
                JSONObject localOptions = (JSONObject) testConfig.get("localOptions");
                options.forEach(localOptions::putIfAbsent);
            }
            local.start(options);
        } catch (Exception e) {
            throw new Error("Unable to start BrowserStack Local.");
        }
    }
}
