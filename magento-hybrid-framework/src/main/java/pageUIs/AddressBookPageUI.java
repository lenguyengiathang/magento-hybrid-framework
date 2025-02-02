package pageUIs;

public class AddressBookPageUI {
	public static final String ADD_NEW_ADDRESS_BUTTON = "css=button[title='Add New Address']";

	public static class DefaultAddresses {
		public static final String DEFAULT_BILLING_ADDRESS = "xpath=//span[text()='Default Billing Address']/parent::strong/following-sibling::div/address";
		public static final String DEFAULT_BILLING_ADDRESS_PHONE = "xpath=//span[text()='Default Billing Address']/parent::strong/following-sibling::div/address/a";
		public static final String CHANGE_BILLING_ADDRESS_LINK = "xpath=//span[text()='Change Billing Address']/parent::a";
		public static final String DEFAULT_SHIPPING_ADDRESS = "xpath=//span[text()='Default Shipping Address']/parent::strong/following-sibling::div/address";
		public static final String DEFAULT_SHIPPING_ADDRESS_PHONE = "xpath=//span[text()='Default Shipping Address']/parent::strong/following-sibling::div/address/a";
		public static final String CHANGE_SHIPPING_ADDRESS_LINK = "xpath=//span[text()='Change Shipping Address']/parent::a";
	}

	public static class AdditionalAddressEntries {
		public static final String DYNAMIC_ADDITIONAL_ADDRESS_VALUE_BY_ROW_INDEX_AND_COLUMN_NAME = "xpath=//tbody/tr[%s]/td[@data-th='%s']";
	}
}
