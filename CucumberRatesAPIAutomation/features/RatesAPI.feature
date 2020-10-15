Feature: Rates API
  - Latest Foreign Exchange rates
  - Specific date Foreign Exchange rates

  Background: Initializing Rates API
    Given User request Rates API URL

  Scenario: Rates API for Latest Foreign Exchange rates
    When User request for an API '/latest'
    Then User validates status and body of the response
     And Validate Rates are quoted against the Euro by default

  Scenario: Rates API for An incorrect or incomplete url
    When User request for an incomplete api
    Then User validates error message of the response

  Scenario: Rates API for Specific date Foreign Exchange rates
    When User request for historical record
      | Previous Date |
      | 2010-01-12    |
    Then User validates status and body of the response

  Scenario Outline: Rates API for Future date Foreign Exchange rates
    When User request for future rates "<Future Date>"
    Then User validates that the response matches for the current date

    Examples: 
      | Future Date |
      | 2021-01-12  |
      | 2022-01-12  |
