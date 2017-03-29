Feature: Verify Local test

  Scenario: Navigate to Local check page
    Given I am on the website 'http://bs-local.com:45691/check'
    Then the page should contain 'Up and running'