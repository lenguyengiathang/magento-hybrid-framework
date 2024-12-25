package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.BasePage;
import pageUIs.BasePageUI;
import pageUIs.ProductDetailsPageUI;

public class ProductDetailsPageObject extends BasePage {
	private WebDriver driver;

	public ProductDetailsPageObject(WebDriver driver) {
		this.driver = driver;
	}

	public void clickAddReviewLink() {
		waitForElementClickable(driver, ProductDetailsPageUI.ADD_REVIEW_LINK);
		clickElement(driver, ProductDetailsPageUI.ADD_REVIEW_LINK);
	}

	public void clickViewReviewsLink() {
		waitForElementClickable(driver, ProductDetailsPageUI.VIEW_REVIEWS_LINK);
		clickElement(driver, ProductDetailsPageUI.VIEW_REVIEWS_LINK);
	}

	public void sendKeysToQuantityTextbox(String quantity) {
		waitForElementVisible(driver, ProductDetailsPageUI.PRODUCT_QUANTITY_TEXTBOX);
		sendKeysToElement(driver, ProductDetailsPageUI.PRODUCT_QUANTITY_TEXTBOX, quantity);
	}

	public void clickAddToCartButton() {
		waitForElementClickable(driver, ProductDetailsPageUI.ADD_TO_CART_BUTTON);
		clickElement(driver, ProductDetailsPageUI.ADD_TO_CART_BUTTON);
	}

	public void clickAddToWishlistLink() {
		waitForElementClickable(driver, ProductDetailsPageUI.ADD_TO_WISHLIST_LINK);
		clickElement(driver, ProductDetailsPageUI.ADD_TO_WISHLIST_LINK);
	}

	public void clickAddToCompareLink() {
		waitForElementClickable(driver, ProductDetailsPageUI.ADD_TO_COMPARE_LINK);
		clickElement(driver, ProductDetailsPageUI.ADD_TO_COMPARE_LINK);
	}

	public String getProductName() {
		waitForElementVisible(driver, BasePageUI.PAGE_HEADER);
		return getElementText(driver, BasePageUI.PAGE_HEADER);
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
		clickElement(driver, ProductDetailsPageUI.DYNAMIC_RATING_STAR, number);
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
