package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.BasePage;
import commons.PageGeneratorManager;
import pageUIs.PopularSearchTermsPageUI;

public class PopularSearchTermsPageObject extends BasePage {
	private WebDriver driver;

	public PopularSearchTermsPageObject(WebDriver driver) {
		this.driver = driver;
	}

	public ProductListingPageObject clickSearchTermByLabel(String label) {
		waitForElementClickable(driver, PopularSearchTermsPageUI.DYNAMIC_SEARCH_TERM_BY_LABEL, label);
		clickElement(driver, PopularSearchTermsPageUI.DYNAMIC_SEARCH_TERM_BY_LABEL, label);
		return PageGeneratorManager.getProductListingPageObject(driver);
	}
}
