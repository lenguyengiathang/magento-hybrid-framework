package pageUIs;

public class MyWishListPageUI {
	public static final String EMPTY_WISH_LIST_WARNING_MESSAGE = "css=#wishlist-view-form>.empty>span";
	public static final String HERE_LINK = "xpath=//a[text()='here']";

	public static final String FIRST_PRODUCT_CARD = "xpath=(//form[@id='wishlist-view-form']//a[@class='product-item-link'])[1]";
	public static final String TRASHCAN_ICON = "css=a[title='Remove Item']";
	public static final String DYNAMIC_PRODUCT_CARD_BY_PRODUCT_NAME = "xpath=//a[contains(text(),'%s')]/parent::strong/ancestor::li";
	public static final String DYNAMIC_COMMENT_TEXTBOX_BY_PRODUCT_NAME = "xpath=//a[contains(text(),'')]/parent::strong/following-sibling::div[3]//textarea";
	public static final String DYNAMIC_QUANTITY_TEXTBOX_BY_PRODUCT_NAME = "xpath=//a[contains(text(),'%s')]/parent::strong/following-sibling::div[3]//input";
	public static final String DYNAMIC_ADD_TO_CART_BUTTON_BY_PRODUCT_NAME = "xpath=//a[contains(text(),'%s')]/parent::strong/following-sibling::div[3]//button";
	public static final String DYNAMIC_PEN_ICON_BY_PRODUCT_NAME = "xpath=//a[contains(text(),'%s')]/parent::strong/following-sibling::div[3]//a[contains(@class,'edit')]";
	public static final String DYNAMIC_TRASHCAN_ICON_BY_PRODUCT_NAME = "xpath=//a[contains(text(),'%s')]/parent::strong/following-sibling::div[3]//a[contains(@class,'delete')]";
	public static final String DYNAMIC_SEE_DETAILS_TEXT_BY_PRODUCT_NAME = "xpath=//a[contains(text(),'%s')]/parent::strong/following-sibling::div/span[contains(@class,'details tooltip')]";
	public static final String OPTIONS_DETAILS_TOOLTIP = "xpath=//a[contains(text(),'%s')]/parent::strong/following-sibling::div/div[@class='tooltip content']";

	public static final String UPDATE_WISH_LIST_BUTTON = "css=button[title='Update Wish List']";
	public static final String SHARE_WISH_LIST_BUTTON = "css=button[title='Share Wish List']";
	public static final String EMAIL_ADDRESSES_SEPARATED_BY_COMMAS = "css=.emails textarea";
	public static final String ADD_ALL_TO_CART_BUTTON = "css=button[title='Add All to Cart']";

}
