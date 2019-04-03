package stepdefinitions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import other.CommonMethods;
import pages.BasePage;

public class BaseSteps {
	
	private static WebDriver driver;
	
	@Before
	public void before(Scenario scenario) {

		String testName = scenario.getName();
		CommonMethods.loadApplicationProperties();
		switch (CommonMethods.appProperties.get("DRIVER").toUpperCase()) {
		case "FIREFOX":
			System.setProperty("webdriver.gecko.driver", CommonMethods.appProperties.get("GECKODRIVER_PATH")); 
			driver = new FirefoxDriver();
			break;
		case "IE":
			System.setProperty("webdriver.ie.driver", CommonMethods.appProperties.get("IEDRIVER_PATH")); 
			driver = new InternetExplorerDriver();
			break;
		default:
			System.setProperty("webdriver.gecko.driver", CommonMethods.appProperties.get("GECKODRIVER_PATH")); 
			driver = new FirefoxDriver();
			break;
		}

		driver.manage().window().maximize();
		driver.get(CommonMethods.appProperties.get("URL")); 
		new BasePage().setDriver(driver);
		CommonMethods.constWait(5);
		CommonMethods.readTestData(testName);
		CommonMethods.scenario = scenario;
		
	}
	
	@After
	public void after() {
		driver.quit();
		driver = null;
		CommonMethods.appProperties = null;
	}
	
}
