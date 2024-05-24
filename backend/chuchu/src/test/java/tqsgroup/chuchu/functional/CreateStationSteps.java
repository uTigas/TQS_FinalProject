package tqsgroup.chuchu.functional;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.cucumber.java.en.*;

import static org.junit.Assert.assertTrue;

import java.time.Duration;

public class CreateStationSteps {
    private WebDriver driver;
    private WebDriverWait wait;

    @Given("I access the url {string}")
    public void iAccessTheUrl(String url) {
        driver = new ChromeDriver();
        driver.get(url);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @When("I log in as an admin")
    public void iLogInAsAnAdmin() {
        WebElement loginButton = driver.findElement(By.id("login"));
        loginButton.click();

        // Wait to be redirected to the login page
        wait.until(ExpectedConditions.urlToBe("http://localhost:8080/auth/login"));

        WebElement usernameField = driver.findElement(By.name("username"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));

        usernameField.sendKeys("admin");
        passwordField.sendKeys("password");
        submitButton.click();
    }

    @Then("I should be redirected back to the {string} page")
    public void iShouldBeRedirectedBackToPage(String pageName) {
        wait.until(ExpectedConditions.urlToBe("http://localhost:8101/" + pageName));
    }

    @And("I switch to the {string} page")
    public void iSwitchToThePage(String pageName) {
        WebElement stationsCard = driver.findElement(By.xpath("//ion-card[.//img[@alt='Stations']]"));
        stationsCard.click();
    }

    @Given("I am on the {string} page")
    public void iAmOnThePage(String pageName) {
        // Wait until the URL is as expected
        wait.until(ExpectedConditions.urlToBe("http://localhost:8101/" + pageName));
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
        WebElement successMessageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(),'" + successMessage + "')]")));
        assertTrue("Success message not displayed", successMessageElement.isDisplayed());
        driver.quit();
    }
}
