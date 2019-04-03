package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import other.CommonMethods;

public class SearchPage extends BasePage {

	//Locators
	By searchInputElement = By.name("q");
	
	//Methods
	public void search(String searchText) {
		CommonMethods.setText(driver, searchInputElement, searchText+Keys.ENTER);
		CommonMethods.constWait(10);
		
		final byte[] screenshot = ((TakesScreenshot) driver)
                .getScreenshotAs(OutputType.BYTES);
		CommonMethods.scenario.embed(screenshot, "image/png");
	}
	
}
