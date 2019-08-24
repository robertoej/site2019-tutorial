Feature: Review creation

  Scenario: creation of valid review
    Given there are no reviews created
    When a review is created
    Then summary is updated

  Scenario: creation of invalid review
    Given there are no reviews created
    When an invalid review is created
    Then no summary is updated
    And warn user
