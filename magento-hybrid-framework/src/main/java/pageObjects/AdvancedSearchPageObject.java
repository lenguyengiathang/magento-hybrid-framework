package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.BasePage;
import commons.PageGeneratorManager;
import pageUIs.AdvancedSearchPageUI;
import pageUIs.BasePageUI;

public class AdvancedSearchPageObject extends BasePage {
	private WebDriver driver;

	public AdvancedSearchPageObject(WebDriver driver) {
		this.driver = driver;
	}

	public void sendKeysToProductNameTextbox(String productName) {
		waitForElementVisible(driver, AdvancedSearchPageUI.PRODUCT_NAME_TEXTBOX);
		sendKeysToElement(driver, AdvancedSearchPageUI.PRODUCT_NAME_TEXTBOX, productName);
	}

	public void sendKeysToSKUTextbox(String sku) {
		waitForElementVisible(driver, AdvancedSearchPageUI.SKU_TEXTBOX);
		sendKeysToElement(driver, AdvancedSearchPageUI.SKU_TEXTBOX, sku);
	}

	public void sendKeysToDescriptionTextbox(String description) {
		waitForElementVisible(driver, AdvancedSearchPageUI.DESCRIPTION_TEXTBOX);
		sendKeysToElement(driver, AdvancedSearchPageUI.DESCRIPTION_TEXTBOX, description);
	}

	public void sendKeysToPriceFromTextbox(String price) {
		waitForElementVisible(driver, AdvancedSearchPageUI.PRICE_FROM_TEXTBOX);
		sendKeysToElement(driver, AdvancedSearchPageUI.PRICE_FROM_TEXTBOX, price);
	}

	public void sendKeysToPriceToTextbox(String price) {
		waitForElementVisible(driver, AdvancedSearchPageUI.PRICE_TO_TEXTBOX);
		sendKeysToElement(driver, AdvancedSearchPageUI.PRICE_TO_TEXTBOX, price);
	}

	public ProductListingPageObject clickSearchButton() {
		waitForElementClickable(driver, AdvancedSearchPageUI.SEARCH_BUTTON);
		clickElementByJS(driver, AdvancedSearchPageUI.SEARCH_BUTTON);
		return PageGeneratorManager.getProductListingPageObject(driver);
	}

	public String getEmptySearchValueErrorMessage() {
		waitForElementVisible(driver, BasePageUI.MainContent.MESSAGE);
		return getElementText(driver, BasePageUI.MainContent.MESSAGE);
	}

}
