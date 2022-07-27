# Cucumber Java Browserstack 
![BrowserStack Logo](https://d98b8t1nnulk5.cloudfront.net/production/images/layout/logo-header.png?1469004780)

[Cucumber JVM](https://cucumber.io/docs/reference/jvm) Integration with BrowserStack.

### Using Maven

## Run sample build

- Clone the repository
- Replace YOUR_USERNAME and YOUR_ACCESS_KEY with your BrowserStack access credentials in [browserstack.yml](https://github.com/browserstack/cucumber-java-browserstack/blob/sdk/browserstack.yml).
- Install dependencies `mvn compile`
- To run a sample test suite run `mvn test -P sample-test`
- To enable BrowserStack Local, set `browserstackLocal: true` in your `browserstack.yml` file and check its status by running `mvn test -P sample-local-test`


## Integrate your test suite using BrowserStack SDK
You can run your existing test suite on BrowserStack's selenium cloud using the BrowserStack SDK. It provides a plug-and-play integration with Cucumber-JVM. Our SDK is compatible with Cucumber-JVM using the TestNG runner.

### Add Testng Runner to your Cucumber project (if not added)
If you are using the Cucumber CLI runner, you can migrate to the TestNG Runner out of the box using the below command:
```
mvn archetype:generate -DarchetypeGroupId=com.browserstack -DarchetypeArtifactId=cucumber-testng-archetype -DarchetypeVersion=1.0 -DgroupId=com.browserstack -DartifactId=cucumber-testng-archetype -Dversion=1.0 -DinteractiveMode=false
```
* To use specific `@CucumberOptions` in generated class `BrowserStackCucumberTestNgRunner`, refer - https://javadoc.io/static/io.cucumber/cucumber-testng/5.0.0-RC2/io/cucumber/testng/CucumberOptions.html

### Install SDK

Add maven dependency of browserstack-java-sdk in your pom.xml file
```sh
<dependency>
    <groupId>com.browserstack</groupId>
    <artifactId>browserstack-java-sdk</artifactId>
    <version>LATEST</version>
    <scope>compile</scope>
</dependency>
```

Modify your build plugin to run tests by adding argLine `-javaagent:${com.browserstack:browserstack-java-sdk:jar}` and `maven-dependency-plugin` for resolving dependencies in the profiles `sample-test` and `sample-local-test`.
```
<plugin>
   <artifactId>maven-dependency-plugin</artifactId>
     <executions>
       <execution>
         <id>getClasspathFilenames</id>
           <goals>
             <goal>properties</goal>
           </goals>
       </execution>
     </executions>
</plugin>
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>3.0.0-M5</version>
    <configuration>
        <suiteXmlFiles>
            <suiteXmlFile>src/test/resources/testng.xml</suiteXmlFile>
        </suiteXmlFiles>
        <argLine>
            -javaagent:${com.browserstack:browserstack-java-sdk:jar}
        </argLine>
    </configuration>
</plugin>
```



Install dependencies using `mvn compile`

### Download browserstack.yml file

[Download sample browserstack.yml](https://github.com/browserstack/cucumber-java-browserstack/blob/sdk/browserstack.yml) file and and place it in your root folder. Ensure set your [BrowserStack Username and Access Key](https://www.browserstack.com/accounts/settings) in the config file.


### Run your test suite
Run your test suite using your `mvn` build command. The tests should start running on BrowserStack on the platform combinations (OS,Browser/Device) set in the `browserstack.yml` config file.

---

### Using Gradle

## Run sample build

- Clone the repository
- Install dependencies `gradle build`
- To run the test suite having cross-platform with parallelization, run `gradle sampleTest`
- To run local tests, run `gradle sampleLocalTest`

Understand how many parallel sessions you need by using our [Parallel Test Calculator](https://www.browserstack.com/automate/parallel-calculator?ref=github)

## Integrate your test suite

* Install dependencies `gradle build`
* Following are the changes required in `gradle.build` -
    * Add `compileOnly 'com.browserstack:browserstack-java-sdk:latest.release'` in dependencies
    * Fetch Artifact Information and add `jvmArgs` property in tasks *SampleTest* and *SampleLocalTest* :
  ```
  def browserstackSDKArtifact = configurations.compileClasspath.resolvedConfiguration.resolvedArtifacts.find { it.name == 'browserstack-java-sdk' }
  
  task sampleTest(type: Test) {
    useTestNG() {
      dependsOn cleanTest
      useDefaultListeners = true
      suites "config/sample-test.testng.xml"
      jvmArgs "-javaagent:${browserstackSDKArtifact.file}"
    }
  }
  ```

## Notes
* You can view your test results on the [BrowserStack Automate dashboard](https://www.browserstack.com/automate)
* Understand how many parallel sessions you need by using our [Parallel Test Calculator](https://www.browserstack.com/automate/parallel-calculator?ref=github)
