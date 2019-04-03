package stepdefinitions;

import cucumber.api.java.en.When;
import other.CommonMethods;
import pages.SearchPage;

public class SearchSteps {

	SearchPage searchPage = new SearchPage();
	
	@When("^Search in Goolge$")
	public void search() {
		searchPage.search(CommonMethods.testData.get("SEARCH_TEXT"));
	}
	
}
