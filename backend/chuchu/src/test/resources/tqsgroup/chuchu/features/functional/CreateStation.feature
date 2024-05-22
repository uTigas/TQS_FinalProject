@station_sample
Feature: Create a Station in the Admin Dashboard

  Background:
    Given I access the url "http://localhost:8101"
    When I log in as an admin
    Then I should be redirected back to the "dashboard" page
    And I switch to the "Stations" page

  Scenario: Create a Station
    Given I am on the "stations" page
    When I fill in the "newStationLines" field with "4"
    And I fill in the "newStationName" field with "Test Station"
    And I click the "Add" button
    Then I should see the success message "Station created successfully."