package com.browserstack.webdriver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;

public class LazyInitWebDriverIterator implements Iterator<Object[]> {
    private final String testMethodName;
    private final List<JSONObject> platforms;
    private final List<Object[]> testParams;
    private final boolean createManagedWebDriver;
    private int paramIdx = 0;

    public LazyInitWebDriverIterator(Boolean createManagedWebDriver, Object[][] testParams) {
        this.testMethodName = "";
        this.platforms = WebDriverFactory.getInstance().getPlatforms();
        this.createManagedWebDriver = createManagedWebDriver;
        List<Object[]> testParamsList = new ArrayList();
        if (testParams != null) {
            testParamsList = (List)Arrays.stream(testParams).collect(Collectors.toList());
        }

        this.testParams = this.populateTestParams((List)testParamsList);
    }

    private List<Object[]> populateTestParams(List<Object[]> testParams) {
        int idx = 0;
        ArrayList tempTestParams = new ArrayList();

        do {
            Object[] testParam = (Object[])testParams.get(idx);
            if (testParam == null) {
                testParam = new Object[0];
            }

            Iterator var5 = this.platforms.iterator();

            while(var5.hasNext()) {
                JSONObject platform = (JSONObject)var5.next();
                Object[] paramsWithPlatform = Arrays.copyOf(testParam, testParam.length + 1);
                paramsWithPlatform[paramsWithPlatform.length - 1] = platform;
                tempTestParams.add(paramsWithPlatform);
            }

            ++idx;
        } while(idx < testParams.size());

        return tempTestParams;
    }

    public boolean hasNext() {
        return this.paramIdx < this.testParams.size();
    }

    public Object[] next() {
        if (this.paramIdx >= this.testParams.size()) {
            throw new NoSuchElementException("No More Platforms configured to create WebDriver for.");
        } else {
            Object[] methodTestParams = (Object[])this.testParams.get(this.paramIdx++);
            if (methodTestParams[methodTestParams.length - 1] instanceof JSONObject) {
                JSONObject platform = (JSONObject)methodTestParams[methodTestParams.length - 1];
                if (this.createManagedWebDriver) {
                    ManagedWebDriver managedWebDriver = new ManagedWebDriver(this.testMethodName, platform);
                    methodTestParams[methodTestParams.length - 1] = managedWebDriver;
                } else {
                    WebDriver webDriver = WebDriverFactory.getInstance().createWebDriverForPlatform(platform, this.testMethodName);
                    methodTestParams[methodTestParams.length - 1] = webDriver;
                }
            }

            return methodTestParams;
        }
    }
}
