package tqsgroup.chuchu.functional;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

import java.time.Duration;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("tqsgroup/chuchu")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "tqsgroup.chuchu")
public class CucumberTest {
    private static WebDriver driver;
    public static Wait<WebDriver> wait;
    public static String springPort = ":8080";
    public static String ionicPort = ":8100";
    public static WebDriver getDriver() {
        if (driver == null) {
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("-headless");
            driver = new FirefoxDriver(options);
            wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        }
        return driver;
    }
}