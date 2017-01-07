Feature: Automatically correct mistyped search terms

  Scenario: Enter search term and view related images
    Given I am on the website 'http://www.google.com'
    When I submit the search term 'BrowserStack'
    Then the page title should be 'BrowserStack - Google Search'