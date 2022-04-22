# Cucumber Java Browserstack 
<img src="src/test/resources/img/browserstack.png" width="60" height="60" ><img src="src/test/resources/img/cucumber.png" width="60" height="60" >

[Cucumber JVM](https://cucumber.io/docs/reference/jvm) Integration with BrowserStack.

## Setup
* Clone the repo
* Install dependencies `mvn install`
* Set environment variables with your [BrowserStack Username and Access Key](https://www.browserstack.com/accounts/settings).
* Update `*.conf.js` files inside the `src/test/resources/conf/` directory to update desired capabilities.

## Running your tests
* To run a single test, run `mvn test -P single`
* To run parallel tests, run `mvn test -P parallel` 
* To run local tests, run `mvn test -P local`



## Notes
* You can view your test results on the [BrowserStack Automate dashboard](https://www.browserstack.com/automate)
* To test on a different set of browsers, check out our [platform configurator](https://www.browserstack.com/automate/java#setting-os-and-browser)
* You can export the environment variables for the Username and Access Key of your BrowserStack account. 

  ```
  export BROWSERSTACK_USERNAME=<browserstack-username> &&
  export BROWSERSTACK_ACCESS_KEY=<browserstack-access-key>
  ```

## Addtional Resources
* [Documentation for writing Automate test scripts in Java](https://www.browserstack.com/automate/java)
* [Customizing your tests on BrowserStack](https://www.browserstack.com/automate/capabilities)
* [Browsers & mobile devices for selenium testing on BrowserStack](https://www.browserstack.com/list-of-browsers-and-platforms?product=automate)
* [Using REST API to access information about your tests via the command-line interface](https://www.browserstack.com/automate/rest-api)
