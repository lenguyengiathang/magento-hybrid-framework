package pageUIs;

public class CompareProductsPageUI {
	public static final String CROSS_ICON = "css=a[title='Remove Product']";
	public static final String DYNAMIC_ADD_TO_CART_BUTTON_BY_PRODUCT_NAME = "xpath=//a[contains(text(),'%s')]/parent::strong/following-sibling::div//button";
	public static final String DYNAMIC_WISH_LIST_ICON_BY_PRODUCT_NAME = "xpath=//a[contains(text(),'%s')]/parent::strong/following-sibling::div//a[@class='action towishlist']";

}
