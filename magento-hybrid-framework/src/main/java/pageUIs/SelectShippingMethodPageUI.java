package pageUIs;

public class SelectShippingMethodPageUI {
	public static final String DYNAMIC_CHANGE_LINK_BY_PRODUCT_NAME = "xpath=//a[contains(text(),'%s')]/ancestor::div[@class='block-content']/div[@class='box box-shipping-address']//a[@class='action edit']";
	public static final String DYNAMIC_FULL_ADDRESS_BY_PRODUCT_NAME = "xpath=//a[contains(text(),'%s')]/ancestor::div[@class='block-content']//address";
	public static final String DYNAMIC_TABLE_RATE_RADIO_BUTTON_BY_STREET_ADDRESS = "xpath=//div[contains(@class,'shipping-address')]//address[contains(.,'%s')]/ancestor::div[2]/following-sibling::div//label[contains(text(),'Table Rate')]/preceding-sibling::div/input";
	public static final String DYNAMIC_FIXED_RADIO_BUTTON_BY_STREET_ADDRESS = "xpath=//div[contains(@class,'shipping-address')]//address[contains(.,'%s')]/ancestor::div[2]/following-sibling::div//label[contains(text(),'Fixed')]/preceding-sibling::div/input";
	public static final String DYNAMIC_EDIT_ITEMS_LINK_BY_PRODUCT_NAME = "xpath=//a[text()='%s']/ancestor::div[@class='box box-items']//a[@class='action edit']";
	public static final String CONTINUE_TO_BILLING_INFORMATION_BUTTON = "css=button[type='submit']:not([id='send2'])";
	public static final String BACK_TO_SELECT_ADDRESSES_LINK = "css=.back";
}
