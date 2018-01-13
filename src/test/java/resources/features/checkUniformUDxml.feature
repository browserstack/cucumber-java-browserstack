Feature: Verify Local test

  Scenario: Navigate to Local check page
    Given I ams on the website 'http://dev.embodee.com/services/factoryxml/UD5a256d96492ec'
    When Check query content for the right properties 'http://dev.embodee.com/services/factoryxml/UD5a256d96492ec'
#    Then paas

