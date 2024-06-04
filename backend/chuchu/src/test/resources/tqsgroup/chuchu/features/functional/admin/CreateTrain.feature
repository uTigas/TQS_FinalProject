@station_sample
Feature: Create a Station in the Admin Dashboard

  Background:
    Given I access the url "/admin"
    When I log in as an admin
    Then I should be redirected back to the "/admin/dashboard" page
    And I switch to the "Trains" trains page

  Scenario: Create a Station
    Given I am on the "/admin/trains" trains page
    When I fill in the "newTrainNumber" input field with "4"
    And I choose the select option with "REGIONAL"
    And I click the "Add" add train button
    Then I should see the train success message "Train created successfully."