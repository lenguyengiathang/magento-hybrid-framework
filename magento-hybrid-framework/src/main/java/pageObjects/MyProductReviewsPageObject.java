package pageObjects;

import java.util.Map;

import org.openqa.selenium.WebDriver;

import commons.BasePage;
import pageUIs.MyProductReviewsPageUI;

public class MyProductReviewsPageObject extends BasePage {
	private WebDriver driver;

	public MyProductReviewsPageObject(WebDriver driver) {
		this.driver = driver;
	}

	public String getReviewDateByProductName(String productName) {
		waitForElementVisible(driver, MyProductReviewsPageUI.DYNAMIC_REVIEW_DATE_BY_PRODUCT_NAME, productName);
		return getElementText(driver, MyProductReviewsPageUI.DYNAMIC_REVIEW_DATE_BY_PRODUCT_NAME, productName);
	}

	public String getReviewRatingByProductName(String productName) {
		waitForElementVisible(driver, MyProductReviewsPageUI.DYNAMIC_RATING_BY_PRODUCT_NAME, productName);
		String titleValue = getElementAttribute(driver, MyProductReviewsPageUI.DYNAMIC_RATING_BY_PRODUCT_NAME, "title",
				productName);
		Map<String, String> ratingMap = Map.of("20%", "1 star", "40%", "2 stars", "60%", "3 stars", "80%", "4 stars",
				"100%", "5 stars");
		return ratingMap.getOrDefault(titleValue, titleValue);
	}

	public String getReviewContentByProductName(String productName) {
		waitForElementVisible(driver, MyProductReviewsPageUI.DYNAMIC_REVIEW_CONTENT_BY_PRODUCT_NAME, productName);
		return getElementText(driver, MyProductReviewsPageUI.DYNAMIC_REVIEW_CONTENT_BY_PRODUCT_NAME, productName);
	}

	public void clickSeeDetailsLinkByProductName(String productName) {
		waitForElementClickable(driver, MyProductReviewsPageUI.DYNAMIC_SEE_DETAILS_LINK_BY_PRODUCT_NAME, productName);
		clickElementByJS(driver, MyProductReviewsPageUI.DYNAMIC_SEE_DETAILS_LINK_BY_PRODUCT_NAME, productName);
	}

	public boolean isReviewByProductNameDisplayed(String productName) {
		return isElementDisplayed(driver, MyProductReviewsPageUI.DYNAMIC_REVIEW_ROW_BY_PRODUCT_NAME, productName);
	}
}
