package pageUIs;

public class ProductDetailsPageUI {
	public static final String ADD_YOUR_REVIEW_LINK = "css=.reviews-actions>.add";
	public static final String BE_THE_FIRST_TO_REVIEW_THIS_PRODUCT_LINK = "css=.reviews-actions>.add";
	public static final String VIEW_REVIEWS_LINK = "css=.reviews-actions>.view";

	public static final String PRODUCT_FINAL_PRICE = "css=.product-info-price span[id*=product-price]";
	public static final String PRODUCT_REGULAR_PRICE = "css=.product-info-price span[id*=old-price]";
	public static final String PRODUCT_SKU = "css=div[itemprop='sku']";
	public static final String PRODUCT_DESCRIPTION = "css=.description>.value";
	public static final String DYNAMIC_PRODUCT_SIZE_BUTTON_BY_LABEL = "xpath=//span[text()='Size']/following-sibling::div/div[text()='%s']";
	public static final String PRODUCT_SIZE_ERROR_MESSAGE = "css=.size div.mage-error";
	public static final String DYNAMIC_PRODUCT_COLOR_BUTTON_BY_LABEL = "xpath=//span[text()='Color']/following-sibling::div/div";
	public static final String PRODUCT_COLOR_ERROR_MESSAGE = "css=.color div.mage-error";
	public static final String PRODUCT_QUANTITY_TEXTBOX = "id=qty";
	public static final String PRODUCT_QUANTITY_ERROR_MESSAGE = "id=qty-error";
	public static final String ADD_TO_CART_BUTTON = "id=product-addtocart-button";
	public static final String ADD_TO_WISH_LIST_LINK = "css=.product-addto-links>a:nth-of-type(1)";
	public static final String UPDATE_WISH_LIST_LINK = "css=.product-addto-links>a:nth-of-type(1)";
	public static final String ADD_TO_COMPARE_LINK = "css=.product-addto-links>a:nth-of-type(2)";
	public static final String MORE_INFORMATION_TAB = "xpath=//a[contains(text(),'More Information')]";
	public static final String PRODUCT_ACTIVITY = "xpath=//td[@data-th='Activity']";
	public static final String REVIEWS_TAB = "id=tab-label-reviews-title";
	public static final String CUSTOMER_REVIEWS_HEADER = "css=#customer-reviews>.block-title";
	public static final String RATING_STAR_OTHER_REVIEWS = "css=ol.review-items .rating-result";
	public static final String RATING_STAR = "css=label[title*='star']";
	public static final String DYNAMIC_RATING_STAR = "id=Rating_%s_label";
	public static final String NICKNAME_TEXBOX = "id=nickname_field";
	public static final String SUMMARY_TEXTBOX = "id=summary_field";
	public static final String REVIEW_TEXTBOX = "id=review_field";
	public static final String RATING_ERROR_MESSAGE = "id=#ratings\\[4\\]-error";
	public static final String NICKNAME_ERROR_MESSAGE = "id=nickname_field-error";
	public static final String SUMMARY_ERROR_MESSAGE = "id=summary_field-error";
	public static final String REVIEW_ERROR_MESSAGE = "id=review_field-error";
	public static final String SUBMIT_REVIEW_BUTTON = "xpath=//span[text()='Submit Review']/parent::button";

}
