@station_edit
Feature: Edit a Station in the Admin Dashboard

  Scenario: Edit a Station
    Given I am on the "/admin/stations" page
    When I click the "Edit" button 
    Then a modal should be displayed
    And the modal should be referred to "Test my brand new Station"
    When I fill in the "editStationName" field with "Test Station edited"
    And I fill in the "editStationLines" field with "3"
    And I click the "Save Changes" button
    Then I should see the success message "Station edited successfully." inside the modal
    And I close the modal
    And I close the browser instance