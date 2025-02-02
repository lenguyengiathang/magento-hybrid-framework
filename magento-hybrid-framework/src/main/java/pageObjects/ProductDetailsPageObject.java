package pageObjects;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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

	public void clickAddYourReviewLink() {
		waitForElementClickable(driver, ProductDetailsPageUI.ADD_YOUR_REVIEW_LINK);
		clickElementByJS(driver, ProductDetailsPageUI.ADD_YOUR_REVIEW_LINK);
	}

	public void clickBeTheFirstToReviewThisProductLink() {
		waitForElementClickable(driver, ProductDetailsPageUI.BE_THE_FIRST_TO_REVIEW_THIS_PRODUCT_LINK);
		clickElementByJS(driver, ProductDetailsPageUI.BE_THE_FIRST_TO_REVIEW_THIS_PRODUCT_LINK);
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

	public MyWishListPageObject clickAddToWishlistLink() {
		waitForElementClickable(driver, ProductDetailsPageUI.ADD_TO_WISH_LIST_LINK);
		clickElementByJS(driver, ProductDetailsPageUI.ADD_TO_WISH_LIST_LINK);
		return PageGeneratorManager.getMyWishlistPage(driver);
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

	public void clickMoreInformationTab() {
		waitForElementClickable(driver, ProductDetailsPageUI.MORE_INFORMATION_TAB);
		clickElementByJS(driver, ProductDetailsPageUI.MORE_INFORMATION_TAB);
	}

	public String getProductActivity() {
		waitForElementVisible(driver, ProductDetailsPageUI.PRODUCT_ACTIVITY);
		return getElementText(driver, ProductDetailsPageUI.PRODUCT_ACTIVITY);
	}

	public void clickReviewsTab() {
		waitForElementClickable(driver, ProductDetailsPageUI.REVIEWS_TAB);
		clickElementByJS(driver, ProductDetailsPageUI.REVIEWS_TAB);
	}

	public void clickRatingStar(String number) {
		waitForElementClickable(driver, ProductDetailsPageUI.DYNAMIC_RATING_STAR, number);
		clickElementByJS(driver, ProductDetailsPageUI.DYNAMIC_RATING_STAR, number);
	}

	public int getNumberOfRatedStars() {
		List<WebElement> stars = getWebElements(driver, ProductDetailsPageUI.RATING_STAR);

		int ratedStarsCount = 0;

		for (WebElement star : stars) {
			String color = star.getCssValue("color");
			if (color.equals("rgb(255, 85, 1)")) {
				ratedStarsCount++;
			}
		}
		return ratedStarsCount;
	}

	public double getSumOfRatedStarsFromOtherReviews() {
		List<WebElement> stars = getWebElements(driver, ProductDetailsPageUI.RATING_STAR_OTHER_REVIEWS);
		int totalStars = 0;

		for (WebElement star : stars) {
			String titleValue = star.getAttribute("title");
			Map<String, Integer> ratingMap = Map.of("20%", 1, "40%", 2, "60%", 3, "80%", 4, "100%", 5);
			totalStars += ratingMap.getOrDefault(titleValue, 0);
		}
		return (double) totalStars;
	}

	public boolean isOverallRatingDisplayedCorrectly(double overallStars) {
		return overallStars == getSumOfRatedStarsFromOtherReviews();
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

	public boolean isCustomerReviewsHeaderDisplayed() {
		return isElementDisplayed(driver, ProductDetailsPageUI.CUSTOMER_REVIEWS_HEADER);
	}

	public void clickSubmitReviewButton() {
		waitForElementClickable(driver, ProductDetailsPageUI.SUBMIT_REVIEW_BUTTON);
		clickElementByJS(driver, ProductDetailsPageUI.SUBMIT_REVIEW_BUTTON);
	}

	public String getRatingErrorMessage() {
		waitForElementVisible(driver, ProductDetailsPageUI.RATING_ERROR_MESSAGE);
		return getElementText(driver, ProductDetailsPageUI.RATING_ERROR_MESSAGE);
	}

	public String getNicknameErrorMessage() {
		waitForElementVisible(driver, ProductDetailsPageUI.NICKNAME_ERROR_MESSAGE);
		return getElementText(driver, ProductDetailsPageUI.NICKNAME_ERROR_MESSAGE);
	}

	public String getSummaryErrorMessage() {
		waitForElementVisible(driver, ProductDetailsPageUI.SUMMARY_ERROR_MESSAGE);
		return getElementText(driver, ProductDetailsPageUI.SUMMARY_ERROR_MESSAGE);
	}

	public String getReviewErrorMessage() {
		waitForElementVisible(driver, ProductDetailsPageUI.REVIEW_ERROR_MESSAGE);
		return getElementText(driver, ProductDetailsPageUI.REVIEW_ERROR_MESSAGE);
	}

	public String getReviewSubmittedSuccessMessage() {
		waitForElementVisible(driver, BasePageUI.MainContent.MESSAGE);
		return getElementText(driver, BasePageUI.MainContent.MESSAGE);
	}
}
