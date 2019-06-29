Feature: purchase order functionalities
  Scenario: view all purchase orders
    Given buildIT employee login to the system with valid credentials
    When buildIT employee checks all submitted purchase orders
    Then buildIT employee receives list of submitted purchase orders with status

