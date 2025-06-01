package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.BasePage;
import commons.PageGeneratorManager;
import io.qameta.allure.Step;
import pageUIs.AdvancedSearchPageUI;
import pageUIs.BasePageUI;

public class AdvancedSearchPageObject extends BasePage {
	private WebDriver driver;

	public AdvancedSearchPageObject(WebDriver driver) {
		this.driver = driver;
	}

	@Step("Fill in the 'Product Name' field with the value '{productName}'")
	public void sendKeysToProductNameTextbox(String productName) {
		waitForElementVisible(driver, AdvancedSearchPageUI.PRODUCT_NAME_TEXTBOX);
		sendKeysToElement(driver, AdvancedSearchPageUI.PRODUCT_NAME_TEXTBOX, productName);
	}

	@Step("Fill in the 'SKU' field with the value '{sku}'")
	public void sendKeysToSKUTextbox(String sku) {
		waitForElementVisible(driver, AdvancedSearchPageUI.SKU_TEXTBOX);
		sendKeysToElement(driver, AdvancedSearchPageUI.SKU_TEXTBOX, sku);
	}

	@Step("Fill in the 'Description' field with the value '{description}'")
	public void sendKeysToDescriptionTextbox(String description) {
		waitForElementVisible(driver, AdvancedSearchPageUI.DESCRIPTION_TEXTBOX);
		sendKeysToElement(driver, AdvancedSearchPageUI.DESCRIPTION_TEXTBOX, description);
	}

	@Step("Fill in the 'Price' (from) field with the value '{price}'")
	public void sendKeysToPriceFromTextbox(String price) {
		waitForElementVisible(driver, AdvancedSearchPageUI.PRICE_FROM_TEXTBOX);
		sendKeysToElement(driver, AdvancedSearchPageUI.PRICE_FROM_TEXTBOX, price);
	}

	@Step("Fill in the 'Price' (to) field with the value '{price}'")
	public void sendKeysToPriceToTextbox(String price) {
		waitForElementVisible(driver, AdvancedSearchPageUI.PRICE_TO_TEXTBOX);
		sendKeysToElement(driver, AdvancedSearchPageUI.PRICE_TO_TEXTBOX, price);
	}

	@Step("Click the 'Search' button")
	public ProductListingPageObject clickSearchButton() {
		waitForElementClickable(driver, AdvancedSearchPageUI.SEARCH_BUTTON);
		clickElementByJS(driver, AdvancedSearchPageUI.SEARCH_BUTTON);
		return PageGeneratorManager.getProductListingPageObject(driver);
	}

	@Step("Get the error message displayed when the search value is empty")
	public String getEmptySearchValueErrorMessage() {
		waitForElementVisible(driver, BasePageUI.MainContent.MESSAGE);
		return getElementText(driver, BasePageUI.MainContent.MESSAGE);
	}

}
