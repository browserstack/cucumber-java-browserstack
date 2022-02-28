package com.browserstack;

import com.browserstack.util.Utility;
import io.cucumber.core.cli.Main;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestRunner {
    public static ThreadLocal<JSONObject> threadLocalValue = new ThreadLocal<>();

    public static void main(String[] args) throws IOException, ParseException {
        JSONObject testConfig;
        int threadCount = 1;
        JSONParser parser = new JSONParser();
        if (StringUtils.isNoneEmpty(System.getProperty("parallel-count")) && StringUtils.isNumeric(System.getProperty("parallel-count"))) {
            threadCount = Integer.parseInt(System.getProperty("parallel-count"));
        }
        if (System.getProperty("caps") != null) {
            testConfig = (JSONObject) parser.parse(new FileReader(System.getProperty("caps")));
        } else {
            testConfig = (JSONObject) parser.parse(new FileReader("src/test/resources/conf/single.conf.json"));
        }
        JSONArray environments = (JSONArray) testConfig.get("environments");
        ExecutorService pool = Executors.newFixedThreadPool(threadCount);
        for (Object obj : environments) {
            JSONObject singleConfig = Utility.getCombinedCapability((Map<String, String>) obj, testConfig);
            Runnable task = new Task(singleConfig, threadLocalValue, System.getProperty("features"), testConfig.get("server").toString());
            pool.execute(task);
        }
        pool.shutdown();
    }

}

class Task implements Runnable {
    private JSONObject singleConfig;
    private ThreadLocal<JSONObject> threadLocalValue;
    private String features;
    private String server;

    public static Logger log = LoggerFactory.getLogger(TestRunner.class);

    public Task(JSONObject singleConfig, ThreadLocal<JSONObject> threadLocalValue, String features, String server) {
        this.singleConfig = singleConfig;
        this.threadLocalValue = threadLocalValue;
        this.features = features;
        this.server = server;
    }

    public void run() {
        System.setProperty("server", server);
        threadLocalValue.set(singleConfig);
        try {
            String[] argv = new String[]{"-g", "", features };
            ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
            Main.run(argv, contextClassLoader);
        } catch (Exception e) {
            log.error("Error with parallel test", e);
        } finally {
            threadLocalValue.remove();
        }
    }
}
