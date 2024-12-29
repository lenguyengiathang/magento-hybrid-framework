package pageUIs;

public class MyAccountPageUI {
	public static final String DYNAMIC_SIDEBAR_LINK_BY_LABEL = "xpath=//div[contains(@class,'sidebar')]//li[@class='nav item']/a[text()='%s']";
	public static final String FULL_NAME_AND_EMAIL = "xpath=//span[text()='Contact Information']/parent::strong/following-sibling::div/p";
	public static final String EDIT_CONTACT_INFORMARION_LINK = "xpath=//span[text()='Edit']/parent::a";
	public static final String CHANGE_PASSWORD_LINK = "css=.action change-password";
	public static final String MANAGE_ADDRESSES_LINK = "xpath=//span[text()='Manage Addresses']/parent::a";
}