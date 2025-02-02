package pageUIs;

public class MyProductReviewsPageUI {
	public static final String DYNAMIC_REVIEW_ROW_BY_PRODUCT_NAME = "xpath=//a[text()='%s']/ancestor::tr";
	public static final String DYNAMIC_REVIEW_DATE_BY_PRODUCT_NAME = "xpath=//a[text()='%s']/ancestor::td/preceding-sibling::td";
	public static final String DYNAMIC_RATING_BY_PRODUCT_NAME = "xpath=//a[text()='%s']/ancestor::td/following-sibling::td//div[@class='rating-result']";
	public static final String DYNAMIC_REVIEW_CONTENT_BY_PRODUCT_NAME = "xpath=//a[text()='%s']/ancestor::td/following-sibling::td[@data-th='Review']";
	public static final String DYNAMIC_SEE_DETAILS_LINK_BY_PRODUCT_NAME = "xpath=// a[text()='%s']/ancestor::td/following-sibling::td//a";

	public static final String PRODUCT_NAME = "css=h2";
	public static final String RATING = "css=.rating-result";
	public static final String REVIEW_TITLE = "css=.review-title";
	public static final String REVIEW_CONTENT = "css=.review-content";
	public static final String REVIEW_DATE = "css=.review-date>time";
}
