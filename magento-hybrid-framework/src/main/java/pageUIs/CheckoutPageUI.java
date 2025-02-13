package pageUIs;

public class CheckoutPageUI {
	public static class General {
		public static final String SHIPPING_STEP_LABEL = "xpath=//span[text()='Shipping']/parent::li";
		public static final String REVIEW_AND_PAYMENT_STEP_LABEL = "xpath=//span[text()='Review & Payments']/parent::li";

		public static final String SIGN_IN_LINK = "xpath=//span[text()='Sign In']/parent::button[@class='action action-auth-toggle']";
		public static final String SIGN_IN_MODAL_EMAIL_ADDRESS_TEXTBOX = "css=.block-customer-login form[data-role='login'] input#login-email";
		public static final String SIGN_IN_MODAL_PASSWORD_TEXTBOX = "css=.block-customer-login form[data-role='login'] input#login-password";
		public static final String SIGN_IN_MODAL_SIGN_IN_BUTTON = "css=.block-customer-login form[data-role='login'] button";
	}

	public static class Shipping {
		public static final String ADDRESS_CARD = "css=.shipping-address-item";
		public static final String DYNAMIC_ADDRESS_CARD_BY_STREET_ADDRESS = "xpath=//div[contains(@class,'shipping-address-item ') and contains(.,'%s')]";
		public static final String DYNAMIC_SHIP_HERE_BUTTON_BY_STREET_ADDRESS = "xpath=//div[contains(@class,'shipping-address-item ') and contains(.,'%s')]/button[2]";
		public static final String NEW_ADDRESS_BUTTON = "css=.new-address-popup>button";

		public static final String FIRST_NAME_TEXTBOX = "css=input[name='firstname']";
		public static final String LAST_NAME_TEXTBOX = "css=input[name='lastname']";
		public static final String STREET_ADDRESS_TEXTBOX = "css=input[name='street[0]']";
		public static final String CITY_TEXTBOX = "css=input[name='city']";
		public static final String STATE_PROVINCE_DROPDOWN = "css=select[name='region_id']";
		public static final String ZIP_POSTAL_CODE_TEXTBOX = "css=input[name='postcode']";
		public static final String COUNTRY_DROPDOWN = "css=select[name='country_id']";
		public static final String PHONE_NUMBER_TEXTBOX = "css=input[name='telephone']";
		public static final String QUESTION_MARK_ICON = "id=tooltip";
		public static final String SAVE_IN_ADDRESS_BOOK_CHECKBOX = "id=shipping-save-in-address-book";
		public static final String SHIPPING_ADDRESS_POPUP_SHIP_HERE_BUTTON = "css=.action-save-address";
		public static final String DYNAMIC_SHIPPING_METHOD_RADIO_BUTTON_BY_LABEL = "xpath=//td[text()='%s']/preceding-sibling::td/input";
		public static final String NEXT_BUTTON = "css=.continue";
		public static final String EMAIL_ADDRESS_TEXTBOX = "id=customer-email";
		public static final String PASSWORD_TEXTBOX = "id=customer-password";
		public static final String LOGIN_BUTTON = "css=.login.primary";
		public static final String FORGOT_YOUR_PASSWORD_LINK = "css=.remind";
		public static final String SHIPPING_METHOD_NOT_SELECTED_WARNING_MESSAGE = "css=span[data-bind='text: errorValidationMessage()']";
	}

	public static class ReviewAndPayment {
		public static final String MY_BILLING_AND_SHIPPING_ADDRESS_ARE_THE_SAME = "css=input[name='billing-address-same-as-shipping']";
		public static final String BILLING_ADDRESS = "css=.billing-address-details";
		public static final String EDIT_BUTTON = "css=.action-edit-address";
		public static final String BILLING_ADDRESS_DROPDOWN = "css=select[name='billing_address_id']";
		public static final String FIRST_NAME_TEXTBOX = "css=input[name='firstname']";
		public static final String LAST_NAME_TEXTBOX = "css=input[name='lastname']";
		public static final String STREET_ADDRESS_TEXTBOX = "css=input[name='street[0]']";
		public static final String CITY_TEXTBOX = "css=input[name='city']";
		public static final String STATE_PROVINCE_DROPDOWN = "css=select[name='region_id']";
		public static final String ZIP_POSTAL_CODE_TEXTBOX = "css=input[name='postcode']";
		public static final String COUNTRY_DROPDOWN = "css=select[name='country_id']";
		public static final String PHONE_NUMBER_TEXTBOX = "css=input[name='telephone']";
		public static final String QUESTION_MARK_ICON = "id=tooltip";
		public static final String SAVE_IN_ADDRESS_BOOK_CHECKBOX = "id=shipping-save-in-address-book";
		public static final String CANCEL_LINK = "xpath=//span[text()='Cancel']/parent::button";
		public static final String UPDATE_BUTTON = "css=.action-update";
		public static final String APPLY_DISCOUNT_CODE_HEADER = "id=block-discount-heading";
		public static final String ENTER_DISCOUNT_CODE_TEXTBOX = "id=discount-code";
		public static final String APPLY_DISCOUNT_BUTTON = "css=.action-apply";
		public static final String CART_SUBTOTAL = "css=span[data-th='Cart Subtotal']";
		public static final String DISCOUNT = "css=.totals.discount td>span";
		public static final String SHIPPING = "css=span[data-th='Shipping']";
		public static final String ORDER_TOTAL = "css=.grand.totals td span";
		public static final String PEN_ICON_SHIP_TO_SECTION = "xpath=//span[text()='Ship To:']/following-sibling::button";
		public static final String PEN_ICON_SHIPPING_METHOD_SECTION = "xpath=//span[text()='Shipping Method:']/following-sibling::button";
		public static final String PLACE_ORDER_BUTTON = "css=button[title='Place Order']";
	}

	public static class OrderSuccess {
		public static final String ORDER_NUMBER = "css=.checkout-success strong";
		public static final String CONTINUE_SHOPPING_BUTTON = "css=a.continue";
	}
}
