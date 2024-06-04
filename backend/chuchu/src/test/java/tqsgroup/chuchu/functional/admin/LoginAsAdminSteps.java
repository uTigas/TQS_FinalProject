package tqsgroup.chuchu.functional.admin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import tqsgroup.chuchu.functional.CucumberTest;

public class LoginAsAdminSteps {

    private final WebDriver driver = CucumberTest.getDriver();
    private final Wait<WebDriver> wait = CucumberTest.wait;
    private static final String BASE_URL = "http://localhost";
    
    @Given("I access the url {string}")
    public void iAccessTheUrl(String url) {
        driver.manage().deleteAllCookies();
        driver.get(new StringBuilder().append(BASE_URL).append(url).toString());
    }

    @When("I log in as an admin")
    public void iLogInAsAnAdmin() {
        WebElement loginButton = driver.findElement(By.id("login"));
        loginButton.click();

        // Wait to be redirected to the login page
        wait.until(ExpectedConditions.urlMatches(".*/auth/login$"));
        
        WebElement usernameField = driver.findElement(By.name("username"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));

        usernameField.sendKeys("admin");
        passwordField.sendKeys("password");
        submitButton.click();
    }

    @Then("I should be redirected back to the {string} page")
    public void iShouldBeRedirectedBackToPage(String pageName) {
        wait.until(ExpectedConditions.urlMatches(".*" + pageName + "$"));
    }
}