// package tqsgroup.chuchu.functional;

// import java.time.Duration;

// import org.junit.Test;
// import org.junit.jupiter.api.Disabled;
// import org.openqa.selenium.By;
// import org.openqa.selenium.Keys;
// import org.openqa.selenium.WebDriver;
// import org.openqa.selenium.chrome.ChromeOptions;
// import org.openqa.selenium.remote.RemoteWebDriver;
// import org.openqa.selenium.support.ui.ExpectedConditions;
// import org.openqa.selenium.support.ui.WebDriverWait;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
// import org.springframework.boot.test.web.server.LocalServerPort;
// import org.testcontainers.junit.jupiter.Container;
// import org.testcontainers.junit.jupiter.Testcontainers;
// import org.testcontainers.containers.BrowserWebDriverContainer;

// @Testcontainers
// @SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
// public class AuthTest {

//   @LocalServerPort
//       private int port;

//   private String username;
//   private String password;

//   @Container
//   private static BrowserWebDriverContainer<?> webDriverContainer = new BrowserWebDriverContainer<>()
//             .withCapabilities(new ChromeOptions());

//   private WebDriver driver;
//   @Disabled
//   @Test
//     public void myTest() {
//       webDriverContainer.start();
//       this.driver = new RemoteWebDriver(webDriverContainer.getSeleniumAddress(), new ChromeOptions());
//       System.out.println("Port:" + port);
//       this.driver.get(new StringBuilder().append("http://host.docker.internal:" + port + "/auth/login").toString());
//       System.out.println("Url" + this.driver.getCurrentUrl());
//       System.out.println("Title of Page:" + this.driver.getTitle());

//       WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
//       wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username")));
//       this.driver.findElement(By.id("username")).sendKeys(this.username);
//       this.driver.findElement(By.id("password")).sendKeys(this.password);
//       this.driver.findElement(By.id("password")).sendKeys(Keys.ENTER);
//     }

// }
