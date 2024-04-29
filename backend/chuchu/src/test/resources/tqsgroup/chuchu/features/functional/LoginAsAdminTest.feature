Feature:
Scenario: Login as Admin
Given I am a admin with valid credentials
When I try to login with those credentials
Then Im  logged in as Admin, and redirected to the Admin dashboard