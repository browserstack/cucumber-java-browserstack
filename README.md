# Cucumber JVM-Browserstack
[Cucumber JVM](https://cucumber.io/docs/reference/jvm) Integration with BrowserStack.

![BrowserStack Logo](https://d98b8t1nnulk5.cloudfront.net/production/images/layout/logo-header.png?1469004780) 
<img src="https://cucumber.io/images/cucumber-logo.svg" height="110" />

## Setup
* Clone the repo
* Install dependencies `mvn install`
* Set environment variables with your [BrowserStack Username and Access Key](https://www.browserstack.com/accounts/settings). 

## Running your tests
* To run a single test, run `mvn install -Dbrowser=<BROWSER>`
  * Note - use command line properties to set additional webdriver capabilities
* To run parallel tests, run `make run_all_in_parallel`


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
