package pageObjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import commons.BasePage;
import pageUIs.ProductListingPageUI;

public class ProductListingPageObject extends BasePage {
	private WebDriver driver;

	public ProductListingPageObject(WebDriver driver) {
		this.driver = driver;
	}

	public String getSearchResultMessage() {
		waitForElementVisible(driver, ProductListingPageUI.SEARCH_RESULT_MESSAGE);
		return getElementText(driver, ProductListingPageUI.SEARCH_RESULT_MESSAGE);
	}

	public boolean areRelatedSearchTermsDisplayedCorrectly(String searchValue) {
		waitForAllElementsVisible(driver, ProductListingPageUI.RELATED_SEARCH_TERM);
		List<WebElement> products = getWebElements(driver, ProductListingPageUI.RELATED_SEARCH_TERM);

		for (WebElement product : products) {
			if (!product.getText().toLowerCase().trim().contains(searchValue)) {
				return false;
			}
		}
		return true;
	}

	public String getTotalNumberOfProductItems() {
		waitForElementVisible(driver, ProductListingPageUI.TOTAL_NUMBER_OF_ITEMS);
		return getElementText(driver, ProductListingPageUI.TOTAL_NUMBER_OF_ITEMS);
	}

	public String getProductName() {
		waitForElementVisible(driver, ProductListingPageUI.PRODUCT_NAME);
		return getElementText(driver, ProductListingPageUI.PRODUCT_NAME);
	}

	public boolean areProductsDisplayedCorrectly(String searchValue) {
		waitForAllElementsVisible(driver, ProductListingPageUI.PRODUCT_NAME);
		List<WebElement> products = getWebElements(driver, ProductListingPageUI.PRODUCT_NAME);

		for (WebElement product : products) {
			if (!product.getText().toLowerCase().trim().contains(searchValue)) {
				return false;
			}
		}
		return true;
	}

	public String getProductRatings() {
		waitForElementVisible(driver, ProductListingPageUI.PRODUCT_RATINGS);
		return getElementText(driver, ProductListingPageUI.PRODUCT_RATINGS);
	}

	public String getNumberOfProductReviews() {
		waitForElementVisible(driver, ProductListingPageUI.PRODUTC_REVIEWS);
		return getElementText(driver, ProductListingPageUI.PRODUTC_REVIEWS);
	}
}
