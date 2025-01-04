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
		waitForElementVisible(driver, ProductListingPageUI.DYNAMIC_FILTER_OPTION_PRODUCT_COUNT_BY_LABEL);
		return getElementText(driver, ProductListingPageUI.DYNAMIC_FILTER_OPTION_PRODUCT_COUNT_BY_LABEL);
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

	public String getProductName() {
		waitForElementVisible(driver, ProductListingPageUI.PRODUCT_NAME);
		return getElementText(driver, ProductListingPageUI.PRODUCT_NAME);
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
		waitForAllElementsVisible(driver, ProductListingPageUI.PRODUCT_CARD);
		int numberOfProducts = getNumberOfElements(driver, ProductListingPageUI.PRODUCT_CARD);

		for (int i = 0; i < numberOfProducts; i++) {
			List<WebElement> products = getWebElements(driver, ProductListingPageUI.PRODUCT_CARD);
			WebElement product = products.get(i);
			product.click();
			String sku = PageGeneratorManager.getProductDetailsPageObject(driver).getProductSKU();
			if (!sku.contains(searchValue)) {
				return false;
			}
			backToPage(driver);
			waitForAllElementsVisible(driver, ProductListingPageUI.PRODUCT_CARD);
		}
		return true;
	}

	public boolean areProductDescriptionsDisplayedCorrectly(String searchValue) {
		waitForAllElementsVisible(driver, ProductListingPageUI.PRODUCT_CARD);
		int numberOfProducts = getNumberOfElements(driver, ProductListingPageUI.PRODUCT_CARD);

		String[] searchWords = searchValue.toLowerCase().split(" ");

		for (int i = 0; i < numberOfProducts; i++) {
			List<WebElement> products = getWebElements(driver, ProductListingPageUI.PRODUCT_CARD);
			WebElement currentProduct = products.get(i);
			currentProduct.click();
			String description = PageGeneratorManager.getProductDetailsPageObject(driver).getProductDescription();
			description = description.toLowerCase();

			for (String word : searchWords) {
				if (!description.contains(word)) {
					return false;
				}
			}
			backToPage(driver);
			waitForAllElementsVisible(driver, ProductListingPageUI.PRODUCT_CARD);
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

	public Float getProductPrice() {
		waitForElementVisible(driver, ProductListingPageUI.PRODUCT_PRICE);
		String price = getElementText(driver, ProductListingPageUI.PRODUCT_PRICE);
		return Float.parseFloat(price);
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

	public void clickAddToCartButtonByProductName(String productName) {
		waitForElementVisible(driver, ProductListingPageUI.DYNAMIC_PRODUCT_LINK_BY_PRODUCT_NAME, productName);
		hoverOverElement(driver, ProductListingPageUI.DYNAMIC_PRODUCT_LINK_BY_PRODUCT_NAME, productName);
		waitForElementClickable(driver, ProductListingPageUI.DYNAMIC_ADD_TO_CART_BUTTON_BY_PRODUCT_NAME, productName);
		clickElementByJS(driver, ProductListingPageUI.DYNAMIC_ADD_TO_CART_BUTTON_BY_PRODUCT_NAME, productName);
		sleepInSecond(GlobalConstants.SHORT_TIMEOUT);
	}

	public void clickSizeButtonByProductNameAndLabel(String productName, String sizeLabel) {
		waitForElementClickable(driver, ProductListingPageUI.DYNAMIC_SIZE_BUTTON_BY_PRODUCT_NAME_AND_LABEL, productName,
				sizeLabel);
		clickElementByJS(driver, ProductListingPageUI.DYNAMIC_SIZE_BUTTON_BY_PRODUCT_NAME_AND_LABEL, productName,
				sizeLabel);
	}

	public void clickColorButtonByProductNameAndLabel(String productName, String colorLabel) {
		waitForElementClickable(driver, ProductListingPageUI.DYNAMIC_COLOR_BUTTON_BY_PRODUCT_NAME_AND_LABEL,
				productName, colorLabel);
		clickElementByJS(driver, ProductListingPageUI.DYNAMIC_COLOR_BUTTON_BY_PRODUCT_NAME_AND_LABEL, productName,
				colorLabel);
	}

	public void addProductWithOptionsToCart(String productName, String sizeLabel, String colorLabel) {
		clickSizeButtonByProductNameAndLabel(productName, sizeLabel);
		clickColorButtonByProductNameAndLabel(productName, colorLabel);
		clickAddToCartButtonByProductName(productName);
	}

	public void addProductWithNoOptionsToCart(String productName) {
		clickAddToCartButtonByProductName(productName);
	}

	public String getAddedToCartSuccessMessage() {
		waitForElementVisible(driver, BasePageUI.MESSAGE);
		return getElementText(driver, BasePageUI.MESSAGE);
	}

	public ShoppingCartPageObject clickViewAndEditCartLink() {
		waitForElementClickable(driver, BasePageUI.VIEW_AND_EDIT_CART_LINK);
		clickElementByJS(driver, BasePageUI.VIEW_AND_EDIT_CART_LINK);
		return PageGeneratorManager.getShoppingCartPageObject(driver);
	}

	public ShoppingCartPageObject clickShoppingCartLink() {
		waitForElementClickable(driver, ProductListingPageUI.SHOPPING_CART_LINK);
		clickElementByJS(driver, ProductListingPageUI.SHOPPING_CART_LINK);
		return PageGeneratorManager.getShoppingCartPageObject(driver);
	}

	public ProductDetailsPageObject clickProductLinkByName(String productName) {
		waitForElementClickable(driver, ProductListingPageUI.DYNAMIC_PRODUCT_LINK_BY_PRODUCT_NAME, productName);
		clickElementByJS(driver, ProductListingPageUI.DYNAMIC_PRODUCT_LINK_BY_PRODUCT_NAME, productName);
		return PageGeneratorManager.getProductDetailsPageObject(driver);
	}

	public MyWishListPageObject clickWishListIconByProductName(String productName) {
		waitForElementVisible(driver, ProductListingPageUI.DYNAMIC_PRODUCT_LINK_BY_PRODUCT_NAME, productName);
		hoverOverElement(driver, ProductListingPageUI.DYNAMIC_PRODUCT_LINK_BY_PRODUCT_NAME, productName);
		waitForElementClickable(driver, ProductListingPageUI.DYNAMIC_WISH_LIST_ICON_BY_PRODUCT_NAME, productName);
		clickElementByJS(driver, ProductListingPageUI.DYNAMIC_WISH_LIST_ICON_BY_PRODUCT_NAME, productName);
		return PageGeneratorManager.getMyWishlistPage(driver);
	}

	public void clickCompareIconByProductName(String productName) {
		waitForElementVisible(driver, ProductListingPageUI.DYNAMIC_PRODUCT_LINK_BY_PRODUCT_NAME, productName);
		hoverOverElement(driver, ProductListingPageUI.DYNAMIC_PRODUCT_LINK_BY_PRODUCT_NAME, productName);
		waitForElementClickable(driver, ProductListingPageUI.DYNAMIC_COMPARE_ICON_BY_PRODUCT_NAME, productName);
		clickElementByJS(driver, ProductListingPageUI.DYNAMIC_COMPARE_ICON_BY_PRODUCT_NAME, productName);
	}

}
