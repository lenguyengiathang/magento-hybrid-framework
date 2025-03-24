package pageUIs;

public class ChangeBillingAddressPageUI {
	public static final String DYNAMIC_BILLING_ADDRESS_BY_STREET_ADDRESS = null;
	public static final String EDIT_ADDRESS_LINK_BY_STREET_ADDRESS = "xpath=//address[contains(.,'%s')]/parent::div/following-sibling::div/a[@class='action edit']";
	public static final String SELECT_ADDRESS_LINK_BY_STREET_ADDRESS = "xpath=//address[contains(.,'%s')]/parent::div/following-sibling::div/a[@class='action select']";
	public static final String DEFAULT_BILLING_ADDRESS = "xpath=//strong[text()='Default Billing']/parent::address";
	public static final String DEFAULT_SHIPPING_ADDRESS = "xpath=//strong[text()='Default Shipping']/parent::address";
	public static final String ADD_NEW_ADDRESS_BUTTON = "xpath=//span[text()='Add New Address']/parent::button";
	public static final String BACK_TO_BILLING_INFORMATION_LINK = "xpath=//span[text()='Back to Billing Information']/parent::a";
}
