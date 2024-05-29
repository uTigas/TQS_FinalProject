package tqsgroup.chuchu;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("tqsgroup/chuchu/functional")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "tqsgroup.chuchu/functional")
public class CucumberTest {
    private static WebDriver driver;

    public static WebDriver getDriver() {
        if (driver == null) {
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("-headless");
            driver = new FirefoxDriver(options);
        }
        return driver;
    }
}
