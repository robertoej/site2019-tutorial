Feature: Review creation

  Scenario: creation of valid review
    Given there are no reviews created
    When a review is created
    Then summary is updated
