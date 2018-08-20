Feature: Verify Local test

  Scenario: Navigate to Local check page
    Given I ams on the website 'http://api.plos.org/search?q=title:DNA'
    When Check query content for the right properties 'http://api.plos.org/search?q=title:DNA'
#    Then paas

