package pageObjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import commons.BasePage;
import commons.GlobalConstants;
import commons.PageGeneratorManager;
import pageUIs.BasePageUI;
import pageUIs.ProductListingPageUI;

public class ProductListingPageObject extends BasePage {
	private WebDriver driver;

	public ProductListingPageObject(WebDriver driver) {
		this.driver = driver;
	}

	public void clickShoppingFilterDropdownByLabel(String filterTitle) {
		waitForElementClickable(driver, ProductListingPageUI.DYNAMIC_FILTER_TITLE_BY_LABEL, filterTitle);
		clickElementByJS(driver, ProductListingPageUI.DYNAMIC_FILTER_TITLE_BY_LABEL, filterTitle);
	}

	public String getShoppingFilterOptionProductCount() {
		waitForElementVisible(driver, ProductListingPageUI.DYNAMIC_PRODUCT_COUNT_BY_FILTER_OPTION_LABEL);
		return getElementText(driver, ProductListingPageUI.DYNAMIC_PRODUCT_COUNT_BY_FILTER_OPTION_LABEL);
	}

	public void clickShoppingFilterOptionByLabel(String filterOption) {
		waitForElementClickable(driver, ProductListingPageUI.DYNAMIC_FILTER_OPTION_BY_LABEL, filterOption);
		clickElement(driver, ProductListingPageUI.DYNAMIC_FILTER_OPTION_BY_LABEL, filterOption);
	}

	public String getSearchFoundMessage() {
		waitForElementVisible(driver, ProductListingPageUI.SEARCH_FOUND_MESSAGE);
		return getElementText(driver, ProductListingPageUI.SEARCH_FOUND_MESSAGE);
	}

	public String getSearchCriteria() {
		waitForElementVisible(driver, ProductListingPageUI.SEARCH_CRITERIA);
		return getElementText(driver, ProductListingPageUI.SEARCH_CRITERIA);
	}

	public String getSearchWarningMessage() {
		waitForElementVisible(driver, ProductListingPageUI.SEARCH_WARNING_MESSAGE);
		return getElementText(driver, ProductListingPageUI.SEARCH_WARNING_MESSAGE);
	}

	public String getProductName() {
		waitForElementVisible(driver, ProductListingPageUI.PRODUCT_NAME);
		return getElementText(driver, ProductListingPageUI.PRODUCT_NAME);
	}

	public AdvancedSearchPageObject clickModifyYourSearchLink() {
		waitForElementClickable(driver, ProductListingPageUI.MODIFY_YOUR_SEARCH_LINK);
		clickElement(driver, ProductListingPageUI.MODIFY_YOUR_SEARCH_LINK);
		return PageGeneratorManager.getAdvancedSearchPage(driver);
	}

	public boolean areRelatedSearchTermsDisplayedCorrectly(String searchValue) {
		waitForAllElementsVisible(driver, ProductListingPageUI.RELATED_SEARCH_TERM);
		List<WebElement> terms = getWebElements(driver, ProductListingPageUI.RELATED_SEARCH_TERM);

		for (WebElement term : terms) {
			if (!term.getText().trim().toLowerCase().contains(searchValue)) {
				return false;
			}
		}
		return true;
	}

	public String getTotalNumberOfProductItems() {
		waitForElementVisible(driver, ProductListingPageUI.TOTAL_NUMBER_OF_ITEMS);
		return getElementText(driver, ProductListingPageUI.TOTAL_NUMBER_OF_ITEMS);
	}

	public boolean areProductsDisplayedCorrectly(String searchValue) {
		waitForAllElementsVisible(driver, ProductListingPageUI.PRODUCT_NAME);
		List<WebElement> products = getWebElements(driver, ProductListingPageUI.PRODUCT_NAME);

		for (WebElement product : products) {
			if (!product.getText().trim().toLowerCase().contains(searchValue)) {
				return false;
			}
		}
		return true;
	}

	public boolean areProductSKUsDisplayedCorrectly(String searchValue) {
		waitForAllElementsVisible(driver, ProductListingPageUI.PRODUCT_LINK);
		int numberOfProducts = getNumberOfElements(driver, ProductListingPageUI.PRODUCT_LINK);

		for (int i = 0; i < numberOfProducts; i++) {
			List<WebElement> products = getWebElements(driver, ProductListingPageUI.PRODUCT_LINK);
			WebElement product = products.get(i);
			product.click();
			sleepInSecond(GlobalConstants.SHORT_TIMEOUT);
			String sku = PageGeneratorManager.getProductDetailsPageObject(driver).getProductSKU();
			if (!sku.contains(searchValue)) {
				return false;
			}
			backToPage(driver);
			waitForAllElementsVisible(driver, ProductListingPageUI.PRODUCT_LINK);
		}
		return true;
	}

	public boolean areProductDescriptionsDisplayedCorrectly(String searchValue) {
		waitForAllElementsVisible(driver, ProductListingPageUI.PRODUCT_LINK);
		int numberOfProducts = getNumberOfElements(driver, ProductListingPageUI.PRODUCT_LINK);

		String[] searchWords = searchValue.toLowerCase().split(" ");

		for (int i = 0; i < numberOfProducts; i++) {
			List<WebElement> products = getWebElements(driver, ProductListingPageUI.PRODUCT_LINK);
			WebElement currentProduct = products.get(i);
			currentProduct.click();
			sleepInSecond(GlobalConstants.SHORT_TIMEOUT);
			String description = PageGeneratorManager.getProductDetailsPageObject(driver).getProductDescription();
			description = description.toLowerCase();

			for (String word : searchWords) {
				if (!description.contains(word)) {
					return false;
				}
			}
			backToPage(driver);
			waitForAllElementsVisible(driver, ProductListingPageUI.PRODUCT_LINK);
		}
		return true;
	}

	public boolean areProductsSortedAscending(String sortOption) {
		if (sortOption.equalsIgnoreCase("Product Name")) {
			return isDataSortedAscending(driver, ProductListingPageUI.PRODUCT_NAME);
		} else if (sortOption.equalsIgnoreCase("Price")) {
			return isDataSortedAscending(driver, ProductListingPageUI.PRODUCT_PRICE);
		} else {
			return false;
		}
	}

	public boolean areProductsSortedDescending(String sortOption) {
		if (sortOption.equalsIgnoreCase("Product Name")) {
			return isDataSortedDescending(driver, ProductListingPageUI.PRODUCT_NAME);
		} else if (sortOption.equalsIgnoreCase("Price")) {
			return isDataSortedDescending(driver, ProductListingPageUI.PRODUCT_PRICE);
		} else {
			return false;
		}
	}

	public String getAddedToShoppingCartSuccessMessage() {
		waitForElementVisible(driver, BasePageUI.MainContent.MESSAGE);
		return getElementText(driver, BasePageUI.MainContent.MESSAGE);
	}

	public ShoppingCartPageObject clickShoppingCartLinkSuccessMessage() {
		waitForElementClickable(driver, ProductListingPageUI.SHOPPING_CART_LINK_SUCCESS_MESSAGE);
		clickElementByJS(driver, ProductListingPageUI.SHOPPING_CART_LINK_SUCCESS_MESSAGE);
		return PageGeneratorManager.getShoppingCartPageObject(driver);
	}

}
