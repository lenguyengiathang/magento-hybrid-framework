package pageUIs;

public class ProductListingPageUI {
	public static final String SEARCH_FOUND_MESSAGE = "css=div[class='search found']";
	public static final String NO_RESULT_FOUND_ERROR_MESSAGE = "css=.error";
	public static final String SEARCH_CRITERIA = "css=div[class='search summary'] li";
	public static final String SEARCH_WARNING_MESSAGE = "css=div[class='message notice']>div";
	public static final String MODIFY_YOUR_SEARCH_LINK = "xpath=//a[text()='Modify your search.']";
	public static final String RELATED_SEARCH_TERM = "xpath=//dt[text()='Related search terms']/following-sibling::dd";

	public static final String DYNAMIC_FILTER_TITLE_BY_LABEL = "xpath=//div[@class='filter-options-title' and text()='%s']";
	public static final String DYNAMIC_FILTER_OPTION_BY_LABEL = "xpath=//div[@class='filter-options-content']//a[contains(text(),'%s')]";
	public static final String DYNAMIC_PRODUCT_COUNT_BY_FILTER_OPTION_LABEL = "xpath=//div[@class='filter-options-content']//a[contains(text(),'%s')]/span";

	public static final String DYNAMIC_MODE_ICON = "css=#modes-label~a[title='%s']";
	public static final String NUMBER_OF_ITEMS_ON_PAGE = "xpath=(//p[@id='toolbar-amount']/span[2])[1]";
	public static final String TOTAL_NUMBER_OF_ITEMS = "xpath=(//p[@id='toolbar-amount']/span[3])[1]";

	public static final String SORT_OPTION_DROPDOWN = "id=sorter";
	public static final String SORT_ARROW_ICON = "css=#sorter~a";

	public static final String SHOPPING_CART_LINK_SUCCESS_MESSAGE = "xpath=//a[text()='shopping cart']";
	public static final String COMPARISON_LIST_LINK_SUCCESS_MESSAGE = "xpath=//a[text()='comparison list']";

	public static final String PRODUCT_LINK = "css=.product-item";
	public static final String PRODUCT_NAME = "css=.product-item-name>a";
	public static final String PRODUCT_PRICE = "css=div[class='price-box price-final_price']";

	public static final String DYNAMIC_PRODUCT_LINK_BY_PRODUCT_NAME = "xpath=//a[contains(text(),'%s')]";
	public static final String DYNAMIC_WISH_LIST_ICON_BY_PRODUCT_NAME = "xpath=//a[contains(text(),'%s')]/parent::strong/following-sibling::div[last()]//a[@class='action towishlist']";
	public static final String DYNAMIC_COMPARE_ICON_BY_PRODUCT_NAME = "xpath=//a[contains(text(),'%s')]/parent::strong/following-sibling::div[last()]//a[@class='action tocompare']";

	public static final String DYNAMIC_SIZE_BUTTON_BY_PRODUCT_NAME_AND_LABEL = "xpath=//li[contains(@class,'product-item')]//a[contains(text(),'%s')]/parent::strong/following-sibling::div/div[contains(@class,'size')]/div/div[text()='%s']";
	public static final String DYNAMIC_COLOR_BUTTON_BY_PRODUCT_NAME_AND_LABEL = "xpath=//li[contains(@class,'product-item')]//a[contains(text(),'%s')]/parent::strong/following-sibling::div/div[contains(@class,'color')]/div/div[@option-label='%s']";
	public static final String DYNAMIC_REVIEWS_LINK_BY_PRODUCT_NAME = "xpath=//li[contains(@class,'product-item')]//a[contains(text(),'%s')]/parent::strong/following-sibling::div[contains(@class,'reviews')]//a";
	public static final String DYNAMIC_ADD_TO_CART_BUTTON_BY_PRODUCT_NAME = "xpath=//li[contains(@class,'product-item')]//a[contains(text(),'%s')]/parent::strong/following-sibling::div[@class='product-item-inner']//button";
}
