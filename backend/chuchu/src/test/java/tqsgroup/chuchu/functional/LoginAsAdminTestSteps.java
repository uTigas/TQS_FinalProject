package tqsgroup.chuchu.functional;

import static org.junit.Assert.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import io.github.bonigarcia.wdm.WebDriverManager;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.openqa.selenium.Keys;

@ContextConfiguration
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class LoginAsAdminTestSteps {
  
  @LocalServerPort
  int randomPort;

  private String username;
  private String password;
  private WebDriver driver;
  
  @Given("I am a admin with valid credentials")
  public void admin_has_valid_credentials(){
    this.driver = WebDriverManager.chromedriver().browserInDocker().create();
    this.driver.get(new StringBuilder().append("http://localhost:").append(this.randomPort).append("/auth/login").toString());
    this.username = "admin";
    this.password = "password";
  }

  @When("I try to login with those credentials")
  public void login_with_valid_credentials(){
        this.driver.findElement(By.id("username")).sendKeys(this.username);
        this.driver.findElement(By.id("password")).sendKeys(this.password);
        this.driver.findElement(By.id("password")).sendKeys(Keys.ENTER);
  }

  @Then("Im  logged in as Admin, and redirected to the Admin dashboard")
    public void i_m_logged_in_as_admin_and_redirected_to_the_admin_dashboard() {
        assertEquals(this.driver.findElement(By.className("title-default")).getText(), "Admin Dashboard");
        this.driver.quit();
    }
}
