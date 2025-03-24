package pageObjects;

import java.util.Map;

import org.openqa.selenium.WebDriver;

import commons.BasePage;
import commons.PageGeneratorManager;
import pageUIs.MyProductReviewsPageUI;
import pageUIs.ReviewDetailsPageUI;

public class ReviewDetailsPageObject extends BasePage {
	private WebDriver driver;

	public ReviewDetailsPageObject(WebDriver driver) {
		this.driver = driver;
	}

	public String getReviewProductName() {
		waitForElementVisible(driver, ReviewDetailsPageUI.PRODUCT_NAME);
		return getElementText(driver, ReviewDetailsPageUI.PRODUCT_NAME);
	}

	public String getReviewRating() {
		waitForElementVisible(driver, ReviewDetailsPageUI.RATING);
		String titleValue = getElementAttribute(driver, MyProductReviewsPageUI.DYNAMIC_RATING_BY_PRODUCT_NAME, "title");
		Map<String, String> ratingMap = Map.of("20%", "1 star", "40%", "2 stars", "60%", "3 stars", "80%", "4 stars",
				"100%", "5 stars");
		return ratingMap.getOrDefault(titleValue, titleValue);
	}

	public String getReviewTitle() {
		waitForElementVisible(driver, ReviewDetailsPageUI.REVIEW_TITLE);
		return getElementText(driver, ReviewDetailsPageUI.REVIEW_TITLE);
	}

	public String getReviewContent() {
		waitForElementVisible(driver, ReviewDetailsPageUI.REVIEW_CONTENT);
		return getElementText(driver, ReviewDetailsPageUI.REVIEW_CONTENT);
	}

	public String getReviewDate() {
		waitForElementVisible(driver, ReviewDetailsPageUI.REVIEW_DATE);
		return getElementText(driver, ReviewDetailsPageUI.REVIEW_DATE);
	}

	public boolean isReviewsLinkDisplayed() {
		return isElementDisplayed(driver, ReviewDetailsPageUI.REVIEWS_LINK);
	}

	public boolean isAddYourReviewLinkDisplayed() {
		return isElementDisplayed(driver, ReviewDetailsPageUI.ADD_YOUR_REVIEW_LINK);
	}

	public ProductDetailsPageObject clickReviewsLink() {
		waitForElementClickable(driver, ReviewDetailsPageUI.REVIEWS_LINK);
		clickElementByJS(driver, ReviewDetailsPageUI.REVIEWS_LINK);
		return PageGeneratorManager.getProductDetailsPageObject(driver);
	}

	public ProductDetailsPageObject clickAddYourReviewLink() {
		waitForElementClickable(driver, ReviewDetailsPageUI.ADD_YOUR_REVIEW_LINK);
		clickElementByJS(driver, ReviewDetailsPageUI.ADD_YOUR_REVIEW_LINK);
		return PageGeneratorManager.getProductDetailsPageObject(driver);
	}

}
