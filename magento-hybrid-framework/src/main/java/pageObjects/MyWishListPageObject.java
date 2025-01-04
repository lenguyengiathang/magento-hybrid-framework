package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.BasePage;
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
		waitForElementVisible(driver, BasePageUI.MESSAGE);
		return getElementText(driver, BasePageUI.MESSAGE);
	}

	public ProductListingPageObject clickSuccessMessageHereLink() {
		waitForElementClickable(driver, MyWishListPageUI.HERE_LINK);
		clickElementByJS(driver, MyWishListPageUI.HERE_LINK);
		return PageGeneratorManager.getProductListingPageObject(driver);
	}

	public void hoverOverProductCardByName(String productName) {
		waitForElementVisible(driver, MyWishListPageUI.DYNAMIC_PRODUCT_LINK_BY_PRODUCT_NAME, productName);
		hoverOverElement(driver, MyWishListPageUI.DYNAMIC_PRODUCT_LINK_BY_PRODUCT_NAME, productName);
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
		waitForElementVisible(driver, BasePageUI.MESSAGE);
		return getElementText(driver, BasePageUI.MESSAGE);
	}

	public void clickAddToCartButtonByProductNameWithoutOptions(String productName) {
		waitForElementClickable(driver, MyWishListPageUI.DYNAMIC_ADD_TO_CART_BUTTON_BY_PRODUCT_NAME, productName);
		clickElementByJS(driver, MyWishListPageUI.DYNAMIC_ADD_TO_CART_BUTTON_BY_PRODUCT_NAME, productName);
	}

	public String getProductAddedToShoppingCartSuccessMessage() {
		waitForElementVisible(driver, BasePageUI.MESSAGE);
		return getElementText(driver, BasePageUI.MESSAGE);
	}

	public boolean isProductRemovedFromWishList(String productName) {
		return isElementNotDisplayed(driver, MyWishListPageUI.DYNAMIC_PRODUCT_LINK_BY_PRODUCT_NAME, productName);
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

}
