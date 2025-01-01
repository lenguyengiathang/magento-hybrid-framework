package pageUIs;

public class MyWishListPageUI {
	public static final String EMPTY_WISH_LIST_WARNING_MESSAGE = "css=#wishlist-view-form>.empty>span";

	public static final String DYNAMIC_COMMENT_TEXT_BOX_BY_PRODUCT_NAME = "xpath=//a[contains(text(),'')]/parent::strong/following-sibling::div[3]//textarea";
	public static final String DYNAMIC_QUANTITY_TEXT_BOX_BY_PRODUCT_NAME = "xpath=//a[contains(text(),'%s')]/parent::strong/following-sibling::div[3]//input";
	public static final String DYNAMIC_ADD_TO_CART_BUTTON_BY_PRODUCT_NAME = "xpath=//a[contains(text(),'%s')]/parent::strong/following-sibling::div[3]//button";
	public static final String DYNAMIC_PEN_ICON_BY_PRODUCT_NAME = "xpath=//a[contains(text(),'%s')]/parent::strong/following-sibling::div[3]//a[contains(@class,'edit')]";
	public static final String DYNAMIC_TRASHCAN_ICON_BY_PRODUCT_NAME = "xpath=//a[contains(text(),'%s')]/parent::strong/following-sibling::div[3]//a[contains(@class,'delete')]";

	public static final String UPDATE_WISH_LIST_BUTTON = "css=button[title='Update Wish List']";
	public static final String SHARE_WISH_LIST_BUTTON = "css=button[title='Share Wish List']";
	public static final String ADD_ALL_TO_CART_BUTTON = "css=button[title='Add All to Cart']";
}
