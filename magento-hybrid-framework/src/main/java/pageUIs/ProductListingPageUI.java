package pageUIs;

public class ProductListingPageUI {
	public static final String SEARCH_RESULT_MESSAGE = "css=div[class='message notice']>div";
	public static final String RELATED_SEARCH_TERM = "xpath=//dt[text()='Related search terms']/following-sibling::dd";

	public static final String DYNAMIC_MODE_ICON = "css=#modes-label~a[title='%s']";
	public static final String TOTAL_NUMBER_OF_ITEMS = "id=toolbar-amount";
	public static final String SORT_OPTION_DROPDOWN = "id=sorter";
	public static final String SORT_ARROW_ICON = "css=#sorter~a";
	public static final String PRODUCT_ITEM = "css=.product-item";
	public static final String PRODUCT_NAME = "css=.product-item-name>a";
	public static final String PRODUCT_RATINGS = "css=.rating-summary>div";
	public static final String PRODUTC_REVIEWS = "css=.reviews-actions>a";
	public static final String PRODUCT_PRICE = "css=.price";
	public static final String ADD_TO_CART_BUTTON = "css=button[title='Add to Cart']";
	public static final String WISHLIST_ICON = "css=a[title='Add to Wish List']";
	public static final String COMPARE_ICON = "css=a[title='Add to Compare']";
	public static final String PAGINATION_LINK = "xpath=(//div[@class='pages'])[1]//ul/li[not(contains(@class,'next'))]";
	public static final String LIMITER_DROPDOWN = "id=limiter";
}
