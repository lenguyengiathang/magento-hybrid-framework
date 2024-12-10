package pageUIs;

public class MyAccountPageUI {
	public static final String CUSTOMER_NAME_DROPDOWN = "css=.customer-name";
	public static final String MY_ACCOUNT_DROPDOWN_LINK = "css=div[class=customer-menu][aria-hidden=false] li:nth-of-type(1)>a";
	public static final String MY_WISH_LIST_DROPDOWN_LINK = "css=div[class=customer-menu][aria-hidden=false] li:nth-of-type(2)>a";
	public static final String SIGN_OUT_DROPDOWN_LINK = "css=div[class=customer-menu][aria-hidden=false] li:nth-of-type(3)>a";

	public static final String FULL_NAME_AND_EMAIL = "xpath=//span[text()='Contact Information']/parent::strong/following-sibling::div/p";
	public static final String EDIT_CONTACT_INFORMARION_LINK = "xpath=//span[text()='Edit']/parent::a";
	public static final String CHANGE_PASSWORD_LINK = "css=.action change-password";
	public static final String MANAGE_ADDRESSES_LINK = "xpath=//span[text()='Manage Addresses']/parent::a";

}