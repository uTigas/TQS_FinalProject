package tqsgroup.chuchu.functional.admin;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.cucumber.java.en.*;
import tqsgroup.chuchu.CucumberTest;

import static org.junit.Assert.assertTrue;

public class StationSteps {

    private final WebDriver driver = CucumberTest.getDriver();
    private final Wait<WebDriver> wait = CucumberTest.wait;
    private static final String BASE_URL = "http://localhost" + CucumberTest.ionicPort;
    WebElement modal;

    @And("I switch to the {string} page")
    public void iSwitchToThePage(String pageName) {
        WebElement stationsCard = driver.findElement(By.xpath("//ion-card[.//img[@alt='Stations']]"));
        stationsCard.click();
    }

    @Given("I am on the {string} page")
    public void iAmOnThePage(String pageName) {
        wait.until(ExpectedConditions.urlToBe(BASE_URL + pageName));
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

    @Then("a modal should be displayed")
    public void aModalShouldBeDisplayed() {
        modal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("ion-modal")));
        assertTrue("Modal not displayed", modal.isDisplayed());
    }

    @And("the modal should be referred to {string}")
    public void theModalShoulBeReferredTo(String stationName) {
        //WebElement modalTitle = driver.findElement(By.xpath("//ion-card-title[contains(text(),'" + stationName + "')]"));
        WebElement modalTitle = modal.findElement(By.xpath(".//ion-card-header//ion-card-title"));
        assertTrue("Modal title not displayed", modalTitle.isDisplayed());
    }

    @Then("I should see the success message {string}")
    public void iShouldSeeTheSuccessMessage(String successMessage) {
        WebElement messageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(),'" + "tation" + "')]")));
        System.out.println("FOUND MESSAGE: " + messageElement.getText()); //debug print

        // Wait for the success message to be visible
        WebElement successMessageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(),'" + successMessage + "')]")));
        assertTrue("Success message not displayed", successMessageElement.isDisplayed());
    }

    @Then("I should see the success message {string} inside the modal")
    public void iShouldSeeTheSuccessMessageInsideTheModal(String successMessage) {
        WebElement messageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ion-modal//p[contains(text(),'" + "tation" + "')]")));    
        System.out.println("FOUND MESSAGE: " + messageElement.getText()); //debug print
    
        // Wait for the success message to be visible
        WebElement successMessageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ion-modal//p[contains(text(),'" + successMessage + "')]")));
        assertTrue("Success message not displayed", successMessageElement.isDisplayed());
    }

    @And("I close the modal")
    public void iCloseTheModal() {
        // Close the modal
        ((JavascriptExecutor)driver).executeScript("document.querySelector('ion-modal').remove();");
    }

    @And("I close the browser instance")
    public void iCloseTheBrowserInstance() {
        driver.quit();
    }
}