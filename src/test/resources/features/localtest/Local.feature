Feature: Verify Local test

  Scenario: BStack Local Test - Navigate to Local App page
    Given I am on the website 'http://bs-local.com:45454/'
    Then the page title should contain 'BrowserStack Local'
