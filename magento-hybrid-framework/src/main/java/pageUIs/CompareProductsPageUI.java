package pageUIs;

public class CompareProductsPageUI {
	public static final String DYNAMIC_PRODUCT_ATTRIBUTE_VALUE_BY_PRODUCT_NAME_AND_LABEL = "xpath=//a[contains(text(),'%s')]/ancestor::tbody/following-sibling::tbody//span[contains(text(),'%s')]/parent::th/following-sibling::td/div";
	public static final String CROSS_ICON = "css=a[title='Remove Product']";
	public static final String DYNAMIC_CROSS_ICON_BY_INDEX = "xpath=(//a[@title='Remove Product'])[%s]";
	public static final String PRODUCT_LINK = "xpath=//strong[@class='product-item-name']/a";
	public static final String DYNAMIC_PRODUCT_LINK_BY_PRODUCT_NAME = "xpath=//a[contains(.,'%s')]";
	public static final String DYNAMIC_ADD_TO_CART_BUTTON_BY_PRODUCT_NAME = "xpath=//a[contains(text(),'%s')]/parent::strong/following-sibling::div//button";
	public static final String DYNAMIC_WISH_LIST_ICON_BY_PRODUCT_NAME = "xpath=//a[contains(text(),'%s')]/parent::strong/following-sibling::div//a[@class='action towishlist']";
}
