# Cucumber Java Browserstack 

| [Cucumber-TestNG](https://github.com/browserstack/cucumber-java-browserstack/tree/master) | [Cucumber-JUnit4](https://github.com/browserstack/cucumber-java-browserstack/tree/cucumber-junit4) | [Cucumber-JUnit5](https://github.com/browserstack/cucumber-java-browserstack/tree/cucumber-junit5) |
| ------------------------------------------------------ | ------------------------------------------------- | ------------------------------------------------- |

![BrowserStack Logo](https://d98b8t1nnulk5.cloudfront.net/production/images/layout/logo-header.png?1469004780)

[Cucumber JVM](https://cucumber.io/docs/reference/jvm) Integration with BrowserStack.

## Using Maven

### Run sample build

- Clone the repository
- Replace YOUR_USERNAME and YOUR_ACCESS_KEY with your BrowserStack access credentials in browserstack.yml.
- Install dependencies `mvn compile`
- To run the test suite having cross-platform with parallelization, run `mvn test -P sample-test`
- To run local tests, run `mvn test -P sample-local-test`
- To view Allure Reports, run `allure serve target/allure-results`

Understand how many parallel sessions you need by using our [Parallel Test Calculator](https://www.browserstack.com/automate/parallel-calculator?ref=github)

### Integrate your test suite

* Install dependencies `mvn compile`
* Create sample browserstack.yml file with the browserstack related capabilities with your [BrowserStack Username and Access Key](https://www.browserstack.com/accounts/settings) and place it in your root folder.
* Add maven dependency of browserstack-java-sdk in your pom.xml file
```sh
<dependency>
    <groupId>com.browserstack</groupId>
    <artifactId>browserstack-java-sdk</artifactId>
    <version>LATEST</version>
    <scope>compile</scope>
</dependency>
```
* Modify your build plugin to run tests by adding argLine `-javaagent:${com.browserstack:browserstack-java-sdk:jar}` and `maven-dependency-plugin` for resolving dependencies in the profiles `sample-test` and `sample-local-test`.
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

### Migrate from Vanilla Cucumber to use Testng Runner 
* If you are using Vanilla Cucumber CLI, you can migrate to use TestNG Runner with BrowserStack using the below command :
```
mvn archetype:generate -DarchetypeGroupId=com.browserstack -DarchetypeArtifactId=cucumber-testng-archetype -DarchetypeVersion=1.0 -DgroupId=com.browserstack -DartifactId=cucumber-testng-archetype -Dversion=1.0 -DinteractiveMode=false
```
* Run your tests using `mvn test`
* To use specific `@CucumberOptions` in generated class `BrowserStackCucumberTestNgRunner`, refer - https://javadoc.io/static/io.cucumber/cucumber-testng/5.0.0-RC2/io/cucumber/testng/CucumberOptions.html

## Using Gradle

### Run sample build

- Clone the repository
- Install dependencies `gradle build`
- To run the test suite having cross-platform with parallelization, run `gradle sampleTest`
- To run local tests, run `gradle sampleLocalTest`

Understand how many parallel sessions you need by using our [Parallel Test Calculator](https://www.browserstack.com/automate/parallel-calculator?ref=github)

### Integrate your test suite

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
