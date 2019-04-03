package pages;

import org.openqa.selenium.By;
import other.CommonMethods;

public class SearchResultsPage extends BasePage {
	
	//Locators
	By resultStatsTextElement = By.id("resultStats");
	
	//Methods
	public void verifyResultStats() {
		System.out.println(CommonMethods.getText(driver, resultStatsTextElement));
	}	
}
