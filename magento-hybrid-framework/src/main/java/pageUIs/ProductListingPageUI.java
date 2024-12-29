package pageUIs;

public class ProductListingPageUI {
	public static final String SEARCH_FOUND_MESSAGE = "css=div[class='search found']";
	public static final String SEARCH_CRITERIA = "css=div[class='search summary'] li";
	public static final String SEARCH_WARNING_MESSAGE = "css=div[class='message notice']>div";
	public static final String MODIFY_YOUR_SEARCH_LINK = "xpath=//a[text()='Modify your search.']";
	public static final String RELATED_SEARCH_TERM = "xpath=//dt[text()='Related search terms']/following-sibling::dd";

	public static final String DYNAMIC_FILTER_TITLE_BY_LABEL = "xpath=//div[@class='filter-options-title' and text()='%s']";
	public static final String DYNAMIC_FILTER_OPTION_BY_LABEL = "xpath=//div[@class='filter-options-content']//a[contains(text(),'%s')]";
	public static final String DYNAMIC_FILTER_OPTION_PRODUCT_COUNT_BY_LABEL = "xpath=//div[@class='filter-options-content']//a[contains(text(),'%s')]/span";
	public static final String DYNAMIC_MODE_ICON = "css=#modes-label~a[title='%s']";
	public static final String TOTAL_NUMBER_OF_ITEMS = "css=#toolbar-amount>span:nth-of-type(3)";
	public static final String SORT_OPTION_DROPDOWN = "id=sorter";
	public static final String SORT_ARROW_ICON = "css=#sorter~a";

	public static final String SHOPPING_CART_LINK = "xpath=//a[text()='shopping cart']";
	public static final String DYNAMIC_PRODUCT_LINK_BY_PRODUCT_NAME = "xpath=//a[contains(text(),'%s')]";
	public static final String PRODUCT_CARD = "css=.product-item";
	public static final String PRODUCT_NAME = "css=.product-item-name>a";
	public static final String PRODUCT_RATINGS = "css=.rating-summary>div";
	public static final String PRODUTC_REVIEWS = "css=.reviews-actions>a";
	public static final String PRODUCT_PRICE = "css=div[class='price-box price-final_price']";
	public static final String DYNAMIC_SIZE_BUTTON_BY_PRODUCT_NAME_AND_LABEL = "xpath=//li[contains(@class,'product-item')]//a[contains(text(),'%s')]/parent::strong/following-sibling::div/div[contains(@class,'size')]/div/div[text()='%s']";
	public static final String DYNAMIC_COLOR_BUTTON_BY_PRODUCT_NAME_AND_LABEL = "xpath=//li[contains(@class,'product-item')]//a[contains(text(),'%s')]/parent::strong/following-sibling::div/div[contains(@class,'color')]/div/div[@option-label='%s']";
	public static final String DYNAMIC_ADD_TO_CART_BUTTON_BY_PRODUCT_NAME = "xpath=//li[contains(@class,'product-item')]//a[contains(text(),'%s')]/parent::strong/following-sibling::div[@class='product-item-inner']//button";
	public static final String WISHLIST_ICON = "css=a[title='Add to Wish List']";
	public static final String COMPARE_ICON = "css=a[title='Add to Compare']";
	public static final String PAGINATION_LINK = "xpath=(//div[@class='pages'])[1]//ul/li[not(contains(@class,'next'))]";
	public static final String LIMITER_DROPDOWN = "id=limiter";
}
