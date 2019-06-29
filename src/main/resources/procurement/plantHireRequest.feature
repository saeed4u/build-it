Feature: plant hire request functionalities
  Scenario: create plant hire request
    Given site engineer login to the system with valid credentials
    When site engineer creates plant hire request
    Then site engineer receives after successful creation of request

  Scenario: modify plant hire request
    Given site engineer login to the system with valid credentials
    When site engineer updates plant hire request
    Then plant hire request sends to the work engineer for approval

  Scenario: cancel plant hire request
    Given site engineer login to the system with valid credentials
    When site engineer cancels plant hire request
    Then notification sends to the supplier about cancellation





