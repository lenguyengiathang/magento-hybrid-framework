package pageUIs;

public class MyAccountPageUI {
	public static class AccountInformation {
		public static final String FULL_NAME_AND_EMAIL = "xpath=//span[text()='Contact Information']/parent::strong/following-sibling::div/p";
		public static final String EDIT_CONTACT_INFORMARION_LINK = "xpath=//span[text()='Edit']/parent::a";
		public static final String CHANGE_PASSWORD_LINK = "css=.action change-password";
	}

	public static class AddressBook {
		public static final String MANAGE_ADDRESSES_LINK = "xpath=//span[text()='Manage Addresses']/parent::a";
		public static final String EDIT_ADDRESS_LINK_DEFAULT_BILLING_ADDRESS_SECTION = "xpath=//span[text()='Default Billing Address']/parent::strong/following-sibling::div/a";
		public static final String EDIT_ADDRESS_LINK_DEFAULT_SHIPPING_ADDRESS_SECTION = "xpath=//span[text()='Default Shipping Address']/parent::strong/following-sibling::div/a";
	}

	public static class MyRecentReviews {
		public static final String VIEW_ALL_LINK_MY_RECENT_REVIEWS_SECTION = "xpath=//strong[text()='My Recent Reviews']/parent::div/a";
		public static final String DYNAMIC_PRODUCT_LINK_BY_PRODUCT_NAME = "xpath=//strong[text()='My Recent Reviews']/parent::div/following-sibling::div//a[contains(text(),'%s')]";
		public static final String DYNAMIC_SEE_DETAILS_LINK_BY_PRODUCT_NAME = "";
	}

	public static class RecentOrders {
		public static final String RECENT_ORDERS_HEADER = "css=.order strong";
		public static final String VIEW_ALL_LINK = "xpath=//strong[text()='Recent Orders']/following-sibling::a";
	}

	public static final String DYNAMIC_SIDEBAR_LINK_BY_LABEL = "xpath=//div[contains(@class,'sidebar')]//li[@class='nav item']/a[text()='%s']";

}