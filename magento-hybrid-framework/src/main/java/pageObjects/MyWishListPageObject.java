package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.BasePage;
import commons.GlobalConstants;
import commons.PageGeneratorManager;
import pageUIs.BasePageUI;
import pageUIs.MyWishListPageUI;

public class MyWishListPageObject extends BasePage {
	private WebDriver driver;

	public MyWishListPageObject(WebDriver driver) {
		this.driver = driver;
	}

	public String getEmptyWishListWarningMessage() {
		waitForElementVisible(driver, MyWishListPageUI.EMPTY_WISH_LIST_WARNING_MESSAGE);
		return getElementText(driver, MyWishListPageUI.EMPTY_WISH_LIST_WARNING_MESSAGE);
	}

	public String getAddProductToWishListSuccessMessage() {
		waitForElementVisible(driver, BasePageUI.MainContent.MESSAGE);
		return getElementText(driver, BasePageUI.MainContent.MESSAGE);
	}

	public BasePage clickHereLinkSuccessMessage() {
		waitForElementClickable(driver, MyWishListPageUI.HERE_LINK);
		clickElementByJS(driver, MyWishListPageUI.HERE_LINK);
		sleepInSecond(GlobalConstants.SHORT_TIMEOUT);

		String url = getPageUrl(driver);
		if (url.equals(GlobalConstants.PORTAL_PAGE_URL)) {
			return PageGeneratorManager.getHomepage(driver);
		} else if (url.contains("html")) {
			return PageGeneratorManager.getProductListingPageObject(driver);
		} else if (url.contains("compare")) {
			return PageGeneratorManager.getCompareProductsPage(driver);
		} else if (url.contains("cart")) {
			return PageGeneratorManager.getShoppingCartPageObject(driver);
		} else {
			return null;
		}
	}

	public void hoverOverProductCardByName(String productName) {
		scrollToElement(driver, MyWishListPageUI.DYNAMIC_PRODUCT_CARD_BY_PRODUCT_NAME, productName);
		waitForElementVisible(driver, MyWishListPageUI.DYNAMIC_PRODUCT_CARD_BY_PRODUCT_NAME, productName);
		hoverOverElement(driver, MyWishListPageUI.DYNAMIC_PRODUCT_CARD_BY_PRODUCT_NAME, productName);
	}

	public void sendKeysToCommentTextbox(String productName, String comment) {
		hoverOverProductCardByName(productName);
		waitForElementVisible(driver, MyWishListPageUI.DYNAMIC_COMMENT_TEXTBOX_BY_PRODUCT_NAME, productName);
		sendKeysToElement(driver, MyWishListPageUI.DYNAMIC_COMMENT_TEXTBOX_BY_PRODUCT_NAME, comment, productName);
	}

	public String getCommentValueByProductName(String productName) {
		hoverOverProductCardByName(productName);
		waitForElementVisible(driver, MyWishListPageUI.DYNAMIC_COMMENT_TEXTBOX_BY_PRODUCT_NAME, productName);
		return getElementValueByJS(driver, MyWishListPageUI.DYNAMIC_COMMENT_TEXTBOX_BY_PRODUCT_NAME, productName);
	}

	public void sendKeysToQuantityTextboxByProductName(String productName, String quantity) {
		hoverOverProductCardByName(productName);
		waitForElementVisible(driver, MyWishListPageUI.DYNAMIC_QUANTITY_TEXTBOX_BY_PRODUCT_NAME, productName);
		sendKeysToElement(driver, MyWishListPageUI.DYNAMIC_QUANTITY_TEXTBOX_BY_PRODUCT_NAME, quantity, productName);
	}

	public String getQuantityValueByProductName(String productName) {
		hoverOverProductCardByName(productName);
		waitForElementVisible(driver, MyWishListPageUI.DYNAMIC_QUANTITY_TEXTBOX_BY_PRODUCT_NAME, productName);
		return getElementValueByJS(driver, MyWishListPageUI.DYNAMIC_QUANTITY_TEXTBOX_BY_PRODUCT_NAME, productName);
	}

	public void clickTrashcanIconByProductName(String productName) {
		hoverOverProductCardByName(productName);
		waitForElementClickable(driver, MyWishListPageUI.DYNAMIC_TRASHCAN_ICON_BY_PRODUCT_NAME, productName);
		clickElementByJS(driver, MyWishListPageUI.DYNAMIC_TRASHCAN_ICON_BY_PRODUCT_NAME, productName);
	}

	public void clickUpdateWishListButton() {
		waitForElementClickable(driver, MyWishListPageUI.UPDATE_WISH_LIST_BUTTON);
		clickElementByJS(driver, MyWishListPageUI.UPDATE_WISH_LIST_BUTTON);
	}

	public void clickShareWishListButton() {
		waitForElementClickable(driver, MyWishListPageUI.SHARE_WISH_LIST_BUTTON);
		clickElementByJS(driver, MyWishListPageUI.SHARE_WISH_LIST_BUTTON);
	}

	public void sendKeysToEmailAddressesSeparatedByCommasTextbox(String email) {
		waitForElementVisible(driver, MyWishListPageUI.EMAIL_ADDRESSES_SEPARATED_BY_COMMAS);
		sendKeysToElement(driver, MyWishListPageUI.EMAIL_ADDRESSES_SEPARATED_BY_COMMAS, email);
	}

	public String getShareWishListSuccessMessage() {
		waitForElementVisible(driver, BasePageUI.MainContent.MESSAGE);
		return getElementText(driver, BasePageUI.MainContent.MESSAGE);
	}

	public void clickAddToCartButtonByProductNameWithoutOptions(String productName) {
		waitForElementClickable(driver, MyWishListPageUI.DYNAMIC_ADD_TO_CART_BUTTON_BY_PRODUCT_NAME, productName);
		clickElementByJS(driver, MyWishListPageUI.DYNAMIC_ADD_TO_CART_BUTTON_BY_PRODUCT_NAME, productName);
	}

	public String getProductAddedToShoppingCartSuccessMessage() {
		waitForElementVisible(driver, BasePageUI.MainContent.MESSAGE);
		return getElementText(driver, BasePageUI.MainContent.MESSAGE);
	}

	public String getAllProductsAddedToShoppingCartSuccessMessage() {
		waitForElementVisible(driver, BasePageUI.MainContent.MESSAGE);
		return getElementText(driver, BasePageUI.MainContent.MESSAGE);
	}

	public boolean isProductNotDisplayedInMyWishListPage(String productName) {
		return isElementNotDisplayed(driver, MyWishListPageUI.DYNAMIC_PRODUCT_CARD_BY_PRODUCT_NAME, productName);
	}

	public String getProductRemovedFromWishListSuccessMessage() {
		waitForElementVisible(driver, BasePageUI.MainContent.MESSAGE);
		return getElementText(driver, BasePageUI.MainContent.MESSAGE);
	}

	public ProductDetailsPageObject clickAddToCartButtonByProductNameWithOptions(String productName) {
		waitForElementClickable(driver, MyWishListPageUI.DYNAMIC_ADD_TO_CART_BUTTON_BY_PRODUCT_NAME, productName);
		clickElementByJS(driver, MyWishListPageUI.DYNAMIC_ADD_TO_CART_BUTTON_BY_PRODUCT_NAME, productName);
		return PageGeneratorManager.getProductDetailsPageObject(driver);
	}

	public void clickAddAllToCartButton() {
		waitForElementClickable(driver, MyWishListPageUI.ADD_ALL_TO_CART_BUTTON);
		clickElementByJS(driver, MyWishListPageUI.ADD_ALL_TO_CART_BUTTON);
	}

	public void hoverOverSeeDetailsTextByProductName(String productName) {
		waitForElementVisible(driver, MyWishListPageUI.DYNAMIC_SEE_DETAILS_TEXT_BY_PRODUCT_NAME, productName);
		hoverOverElement(driver, MyWishListPageUI.DYNAMIC_SEE_DETAILS_TEXT_BY_PRODUCT_NAME, productName);
	}

	public String getOptionsDetails(String productName) {
		waitForElementVisible(driver, MyWishListPageUI.OPTIONS_DETAILS_TOOLTIP, productName);
		return getElementText(driver, MyWishListPageUI.OPTIONS_DETAILS_TOOLTIP, productName);
	}

}
