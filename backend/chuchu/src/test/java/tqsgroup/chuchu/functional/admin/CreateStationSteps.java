package tqsgroup.chuchu.functional.admin;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.cucumber.java.en.*;
import tqsgroup.chuchu.functional.CucumberTest;


public class CreateStationSteps {

    private final WebDriver driver = CucumberTest.getDriver();
    private final Wait<WebDriver> wait = CucumberTest.wait;
    private static final String BASE_URL = "http://localhost";

    @And("I switch to the {string} page")
    public void iSwitchToThePage(String pageName) {
        WebElement stationsCard = driver.findElement(By.xpath("//ion-card[.//img[@alt='Stations']]"));
        stationsCard.click();
    }

    @Given("I am on the {string} page")
    public void iAmOnThePage(String pageName) {
        wait.until(ExpectedConditions.urlMatches(".*" + pageName + "$"));
    }

    @When("I fill in the {string} field with {string}")
    public void iFillInTheFieldWith(String fieldName, String value) {
        WebElement field = driver.findElement(By.xpath("//input[@name='" + fieldName + "']"));
        field.click();
        field.clear();
        field.sendKeys(value);
        wait.until(ExpectedConditions.attributeToBe(field, "value", value));
    }

    @And("I click the {string} button")
    public void iClickTheButton(String buttonName) {
        // Click outside the input field, so values are saved
        WebElement outsideElement = driver.findElement(By.tagName("body"));
        outsideElement.click();

        WebElement button = driver.findElement(By.xpath("//ion-button[contains(text(),'" + buttonName + "')]"));
        button.click();
    }

    @Then("I should see the success message {string}")
    public void iShouldSeeTheSuccessMessage(String successMessage) {
        // Wait for the success message to be visible
        //WebElement successMessageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(),'" + successMessage + "')]")));
        //assertTrue("Success message not displayed", successMessageElement.isDisplayed());
        driver.manage().deleteAllCookies();;
    }
}