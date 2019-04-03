package stepdefinitions;

import cucumber.api.java.en.Then;
import pages.SearchResultsPage;

public class SearchResultsSteps {
	SearchResultsPage searchResultsPage = new SearchResultsPage();
	
	@Then("^Verify results$")
	public void verifyStats() {
		searchResultsPage.verifyResultStats();
	}
}
