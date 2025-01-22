package pageUIs;

public class ShipToMultipleAddressesPageUI {
	public static final String DYNAMIC_QUANTITY_TEXTBOX_BY_PRODUCT_NAME = "xpath=//a[contains(text(),'%s')]/ancestor::td/following-sibling::td//input";
	public static final String DYNAMIC_SEND_TO_DROPDOWN_BY_PRODUCT_NAME = "xpath=//a[contains(text(),'%s')]/ancestor::td/following-sibling::td//select";
	public static final String DYNAMIC_REMOVE_ITEM_LINK_BY_PRODUCT_NAME = "xpath=//a[contains(text(),'%s')]/ancestor::td/following-sibling::td//a";

	public static final String GO_TO_SHIPPING_INFORMATION_BUTTON = "xpath=//span[text()='Go to Shipping Information']/parent::button";
	public static final String BACK_TO_SHOPPING_CART_LINK = "xpath=//span[text()='Back to Shopping Cart']/parent::a";
	public static final String UPDATE_QUANTITY_AND_ADDRESSES_BUTTON = "xpath=//span[text()='Update Qty & Addresses']/parent::button";
	public static final String ENTER_A_NEW_ADDRESS_BUTTON = "xpath=//span[text()='Enter a New Address']/parent::button";

}
