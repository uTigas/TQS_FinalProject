package tqsgroup.chuchu.functional.admin;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;

import static org.junit.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.cucumber.java.en.*;
import tqsgroup.chuchu.functional.CucumberTest;


public class CreateTrainSteps {

    private final WebDriver driver = CucumberTest.getDriver();
    private final Wait<WebDriver> wait = CucumberTest.wait;
    private static final String BASE_URL = "http://localhost" + CucumberTest.ionicPort;

    @And("I switch to the {string} trains page")
    public void iSwitchToThePage(String pageName) {
        WebElement trainsCard = driver.findElement(By.xpath("//ion-card[.//img[@alt='Trains']]"));
        trainsCard.click();
    }

    @Given("I am on the {string} trains page")
    public void iAmOnThePage(String pageName) {
        wait.until(ExpectedConditions.urlToBe(BASE_URL + pageName));
    }
    
    @When("I choose the select option with {string}")
    public void iFillInTheFieldWith(String value) {
        Select select = (Select) driver.findElement(By.cssSelector(".select-ltr"));
        select.selectByVisibleText(value);
    }

    @When("I fill in the {string} input field with {string}")
    public void iFillInTheFieldWith(String fieldName, String value) {
        WebElement field = driver.findElement(By.xpath("//input[@name='" + fieldName + "']"));
        field.click();
        field.click();
        field.clear();
        field.sendKeys(value);
        wait.until(ExpectedConditions.attributeToBe(field, "value", value));
        // Click outside the input field, so values are saved
        WebElement outsideElement = driver.findElement(By.tagName("body"));
        outsideElement.click();

    }

    @And("I click the {string} add train button")
    public void iClickTheButton(String buttonName) {
        

        WebElement button = driver.findElement(By.xpath("//ion-button[contains(text(),'" + buttonName + "')]"));
        button.click();
    }

    @Then("I should see the train success message {string}")
    public void iShouldSeeTheSuccessMessage(String successMessage) {
        // Wait for the success message to be visible
        WebElement successMessageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(),'" + successMessage + "')]")));
        assertTrue("Success message not displayed", successMessageElement.isDisplayed());
        driver.manage().deleteAllCookies();
    }
}
