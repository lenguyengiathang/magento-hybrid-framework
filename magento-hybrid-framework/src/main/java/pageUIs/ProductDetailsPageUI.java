package pageUIs;

public class ProductDetailsPageUI {
	public static final String ADD_REVIEW_LINK = "css=.reviews-actions>.add";
	public static final String VIEW_REVIEWS_LINK = "css=.reviews-actions>.view";

	public static final String PRODUCT_SKU = "css=div[itemprop='sku']";
	public static final String PRODUCT_DESCRIPTION = "css=div[class='product attribute description']>div";
	public static final String PRODUCT_SIZE_BUTTON = "xpath=//span[text()='Size']/following-sibling::div/div";
	public static final String PRODUCT_SIZE_ERROR_MESSAGE = "css=.size div.mage-error";
	public static final String PRODUCT_COLOR_BUTTON = "xpath=//span[text()='Color']/following-sibling::div/div";
	public static final String PRODUCT_COLOR_ERROR_MESSAGE = "css=.color div.mage-error";
	public static final String PRODUCT_QUANTITY_TEXTBOX = "id=qty";
	public static final String PRODUCT_QUANTITY_ERROR_MESSAGE = "id=qty-error";
	public static final String ADD_TO_CART_BUTTON = "id=product-addtocart-button";
	public static final String ADD_TO_WISHLIST_LINK = "css=.product-addto-links>a:nth-of-type(1)";
	public static final String ADD_TO_COMPARE_LINK = "css=.product-addto-links>a:nth-of-type(2)";
	public static final String REVIEWS_TAB = "id=tab-label-reviews-title";
	public static final String DYNAMIC_RATING_STAR = "id=Rating-%s";
	public static final String NICKNAME_TEXBOX = "id=nickname_field";
	public static final String SUMMARY_TEXTBOX = "id=summary_field";
	public static final String REVIEW_TEXTBOX = "id=review_field";
	public static final String SUBMIT_REVIEW_BUTTON = "xpath=//span[text()='Submit Review']/parent::button";
}
