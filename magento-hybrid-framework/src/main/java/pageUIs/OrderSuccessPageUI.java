package pageUIs;

public class OrderSuccessPageUI {
	public static final String ORDER_NUMBER = "css=.checkout-success strong";
	public static final String DYNAMIC_ORDER_NUMBER_BY_SHIPPING_ADDRESS = "xpath=//span[contains(text(),'%s')]/parent::div/preceding-sibling::div/a";
	public static final String CONTINUE_SHOPPING_BUTTON = "css=a.continue";
	public static final String CREATE_AN_ACCOUNT_BUTTON = "xpath=(//span[text()='Create an Account']/parent::a)[1]";
}
