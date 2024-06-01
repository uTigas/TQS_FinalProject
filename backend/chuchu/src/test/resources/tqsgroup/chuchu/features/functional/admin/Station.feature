@station_sample
Feature: Create a Station in the Admin Dashboard

  Background:
    Given I access the url "/admin"
    When I log in as an admin
    Then I should be redirected back to the "/admin/dashboard" page
    And I switch to the "Stations" page

  Scenario: Create a Station
    Given I am on the "/admin/stations" page
    When I fill in the "newStationLines" field with "4"
    And I fill in the "newStationName" field with "Test Station V2"
    And I click the "Add" button
    Then I should see the success message "Station created successfully."

  Scenario: Edit a Station
    Given I am on the "/admin/stations" page
    When I select the "Test Station V2" station from the list
    And I click the "Edit" button for the station "Test Station V2"
    Then a modal should be displayed
    When I fill in the "editStationName" field with "Test Station edited"
    And I click the "Update" button
    Then I should see the success message "Station updated successfully."