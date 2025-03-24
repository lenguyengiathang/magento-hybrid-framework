package pageUIs;

public class MyProductReviewsPageUI {
	public static final String DYNAMIC_REVIEW_ROW_BY_PRODUCT_NAME = "xpath=//a[text()='%s']/ancestor::tr";
	public static final String DYNAMIC_REVIEW_DATE_BY_PRODUCT_NAME = "xpath=//a[text()='%s']/ancestor::td/preceding-sibling::td";
	public static final String DYNAMIC_PRODUCT_LINK_BY_PRODUCT_NAME = "xpath=//a[text()='%s']";
	public static final String DYNAMIC_RATING_BY_PRODUCT_NAME = "xpath=//a[text()='%s']/ancestor::td/following-sibling::td//div[@class='rating-result']";
	public static final String DYNAMIC_REVIEW_CONTENT_BY_PRODUCT_NAME = "xpath=//a[text()='%s']/ancestor::td/following-sibling::td[@data-th='Review']";
	public static final String DYNAMIC_SEE_DETAILS_LINK_BY_PRODUCT_NAME = "xpath=// a[text()='%s']/ancestor::td/following-sibling::td//a";
}
