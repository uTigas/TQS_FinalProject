// package tqsgroup.chuchu.functional;

// import static org.junit.Assert.*;

// import java.time.Duration;

// import org.openqa.selenium.By;
// import org.openqa.selenium.Keys;
// import org.openqa.selenium.WebDriver;
// import org.openqa.selenium.chrome.ChromeOptions;
// import org.openqa.selenium.remote.RemoteWebDriver;
// import org.openqa.selenium.support.ui.ExpectedConditions;
// import org.openqa.selenium.support.ui.WebDriverWait;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.test.context.ContextConfiguration;

// import io.cucumber.java.AfterAll;
// import io.cucumber.java.BeforeAll;
// import io.cucumber.java.en.Given;
// import io.cucumber.java.en.Then;
// import io.cucumber.java.en.When;
// import org.testcontainers.junit.jupiter.Container;
// import org.testcontainers.junit.jupiter.Testcontainers;
// import org.testcontainers.containers.BrowserWebDriverContainer;

// @Testcontainers
// @ContextConfiguration
// @SpringBootTest
// public class AuthTestSteps {


//   private String username;
//   private String password;

//   @Container
//   private static BrowserWebDriverContainer<?> webDriverContainer = new BrowserWebDriverContainer<>()
//             .withCapabilities(new ChromeOptions());

//   private WebDriver driver;
  
//   @BeforeAll
//   public static void setup(){
//     webDriverContainer.start();
//   }

//   @Given("I am a admin with valid credentials")
//   public void admin_has_valid_credentials() throws Exception{
//     try {
//       this.driver = new RemoteWebDriver(webDriverContainer.getSeleniumAddress(), new ChromeOptions());
//       this.driver.get(new StringBuilder().append("http://host.docker.internal:8080").toString());
//       this.username = "admin";
//       this.password = "password";
//     } catch (Exception e) {
//       webDriverContainer.stop();
//       throw e; 
//     }
//   }
  
//   @When("I try to login with those credentials")
//   public void login_with_valid_credentials() {
//     WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
//     wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username")));
//     this.driver.findElement(By.id("username")).sendKeys(this.username);
//     this.driver.findElement(By.id("password")).sendKeys(this.password);
//     this.driver.findElement(By.id("password")).sendKeys(Keys.ENTER);
//   }

//   @Then("Im  logged in as Admin, and redirected to the Admin dashboard")
//   public void i_m_logged_in_as_admin_and_redirected_to_the_admin_dashboard() {
//     assertEquals(this.driver.findElement(By.className("title-default")).getText(), "Admin Dashboard");
//     this.driver.quit();
//   }
  
//   @AfterAll
//   public static void tearDown(){
//   }
// }
