package com.browserstack.webdriver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;

public class LazyInitWebDriverIterator implements Iterator<Object[]> {
    private final String testMethodName;
    private final List<JSONObject> platforms;
    private final List<Object[]> testParams;
    private int paramIndex = 0;

    public LazyInitWebDriverIterator(Object[][] testParams) {
        this.testMethodName = "";
        this.platforms = WebDriverFactory.getInstance().getPlatforms();
        List<Object[]> testParamsList = new ArrayList();
        if (testParams != null) {
            testParamsList = (List)Arrays.stream(testParams).collect(Collectors.toList());
        }

        //Create list of combinations of Scenarios and Platforms
        this.testParams = this.populateTestParams((List)testParamsList);
    }

    private List<Object[]> populateTestParams(List<Object[]> testParams) {
        int index = 0;
        ArrayList tempTestParams = new ArrayList();

        do {
            Object[] testParam = (Object[])testParams.get(index);
            if (testParam == null) {
                testParam = new Object[0];
            }

            Iterator platformsIterator = this.platforms.iterator();

            while(platformsIterator.hasNext()) {
                JSONObject platform = (JSONObject)platformsIterator.next();
                Object[] paramsWithPlatform = Arrays.copyOf(testParam, testParam.length + 1);
                paramsWithPlatform[paramsWithPlatform.length - 1] = platform;
                tempTestParams.add(paramsWithPlatform);
            }

            ++index;
        } while(index < testParams.size());

        return tempTestParams;
    }

    public boolean hasNext() {
        return this.paramIndex < this.testParams.size();
    }

    public Object[] next() {
        if (this.paramIndex >= this.testParams.size()) {
            throw new NoSuchElementException("No More Platforms configured to create WebDriver for.");
        } else {
            Object[] methodTestParams = (Object[])this.testParams.get(this.paramIndex++);
            if (methodTestParams[methodTestParams.length - 1] instanceof JSONObject) {
                JSONObject platform = (JSONObject)methodTestParams[methodTestParams.length - 1];
                ManagedWebDriver managedWebDriver = new ManagedWebDriver(this.testMethodName, platform);
                methodTestParams[methodTestParams.length - 1] = managedWebDriver;
            }
            //return an Object array consisting PickleWrapper, FeatureWrapper and ManagedWebDriver
            return methodTestParams;
        }
    }
}
