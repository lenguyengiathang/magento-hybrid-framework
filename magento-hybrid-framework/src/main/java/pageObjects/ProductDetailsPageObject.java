package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.BasePage;
import commons.GlobalConstants;
import commons.PageGeneratorManager;
import pageUIs.BasePageUI;
import pageUIs.ProductDetailsPageUI;

public class ProductDetailsPageObject extends BasePage {
	private WebDriver driver;

	public ProductDetailsPageObject(WebDriver driver) {
		this.driver = driver;
	}

	public String getChooseOptionsForItemWarningMessage() {
		waitForElementVisible(driver, BasePageUI.MainContent.MESSAGE);
		return getElementText(driver, BasePageUI.MainContent.MESSAGE);
	}

	public Float getProductFinalPrice() {
		waitForElementVisible(driver, ProductDetailsPageUI.PRODUCT_FINAL_PRICE);
		return Float.parseFloat(getElementText(driver, ProductDetailsPageUI.PRODUCT_FINAL_PRICE).replace("$", ""));
	}

	public Float getProductRegularPrice() {
		waitForElementVisible(driver, ProductDetailsPageUI.PRODUCT_REGULAR_PRICE);
		return Float.parseFloat(getElementText(driver, ProductDetailsPageUI.PRODUCT_REGULAR_PRICE).replace("$", ""));
	}

	public void clickSizeButtonByLabel(String label) {
		waitForElementClickable(driver, ProductDetailsPageUI.DYNAMIC_PRODUCT_SIZE_BUTTON_BY_LABEL, label);
		clickElementByJS(driver, ProductDetailsPageUI.DYNAMIC_PRODUCT_SIZE_BUTTON_BY_LABEL, label);
	}

	public void clickColorButtonByLabel(String label) {
		waitForElementClickable(driver, ProductDetailsPageUI.DYNAMIC_PRODUCT_COLOR_BUTTON_BY_LABEL, label);
		clickElementByJS(driver, ProductDetailsPageUI.DYNAMIC_PRODUCT_COLOR_BUTTON_BY_LABEL, label);
	}

	public void clickAddReviewLink() {
		waitForElementClickable(driver, ProductDetailsPageUI.ADD_REVIEW_LINK);
		clickElementByJS(driver, ProductDetailsPageUI.ADD_REVIEW_LINK);
	}

	public void clickViewReviewsLink() {
		waitForElementClickable(driver, ProductDetailsPageUI.VIEW_REVIEWS_LINK);
		clickElementByJS(driver, ProductDetailsPageUI.VIEW_REVIEWS_LINK);
	}

	public void sendKeysToQuantityTextbox(String quantity) {
		waitForElementVisible(driver, ProductDetailsPageUI.PRODUCT_QUANTITY_TEXTBOX);
		sendKeysToElement(driver, ProductDetailsPageUI.PRODUCT_QUANTITY_TEXTBOX, quantity);
	}

	public void clickAddToCartButton() {
		waitForElementClickable(driver, ProductDetailsPageUI.ADD_TO_CART_BUTTON);
		clickElementByJS(driver, ProductDetailsPageUI.ADD_TO_CART_BUTTON);
		sleepInSecond(GlobalConstants.SHORT_TIMEOUT);
	}

	public void clickAddToWishlistLink() {
		waitForElementClickable(driver, ProductDetailsPageUI.ADD_TO_WISH_LIST_LINK);
		clickElementByJS(driver, ProductDetailsPageUI.ADD_TO_WISH_LIST_LINK);
	}

	public MyWishListPageObject clickUpdateWishListButton() {
		waitForElementClickable(driver, ProductDetailsPageUI.UPDATE_WISH_LIST_LINK);
		clickElementByJS(driver, ProductDetailsPageUI.UPDATE_WISH_LIST_LINK);
		return PageGeneratorManager.getMyWishlistPage(driver);
	}

	public void clickAddToCompareLink() {
		waitForElementClickable(driver, ProductDetailsPageUI.ADD_TO_COMPARE_LINK);
		clickElementByJS(driver, ProductDetailsPageUI.ADD_TO_COMPARE_LINK);
	}

	public String getProductName() {
		waitForElementVisible(driver, BasePageUI.MainContent.PAGE_HEADER);
		return getElementText(driver, BasePageUI.MainContent.PAGE_HEADER);
	}

	public String getProductSKU() {
		waitForElementVisible(driver, ProductDetailsPageUI.PRODUCT_SKU);
		return getElementText(driver, ProductDetailsPageUI.PRODUCT_SKU);
	}

	public String getProductDescription() {
		waitForElementVisible(driver, ProductDetailsPageUI.PRODUCT_DESCRIPTION);
		return getElementText(driver, ProductDetailsPageUI.PRODUCT_DESCRIPTION);
	}

	public void clickRatingStar(String number) {
		waitForElementClickable(driver, ProductDetailsPageUI.DYNAMIC_RATING_STAR, number);
		clickElementByJS(driver, ProductDetailsPageUI.DYNAMIC_RATING_STAR, number);
	}

	public void sendKeysToNicknameTextbox(String nickname) {
		waitForElementVisible(driver, ProductDetailsPageUI.NICKNAME_TEXBOX);
		sendKeysToElement(driver, ProductDetailsPageUI.NICKNAME_TEXBOX, nickname);
	}

	public void sendKeysToSummaryTextbox(String summary) {
		waitForElementVisible(driver, ProductDetailsPageUI.SUMMARY_TEXTBOX);
		sendKeysToElement(driver, ProductDetailsPageUI.SUMMARY_TEXTBOX, summary);
	}

	public void sendKeysToReviewTextbox(String review) {
		waitForElementVisible(driver, ProductDetailsPageUI.REVIEW_TEXTBOX);
		sendKeysToElement(driver, ProductDetailsPageUI.REVIEW_TEXTBOX, review);
	}

}
