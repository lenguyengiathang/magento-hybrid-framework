package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import commons.BasePage;
import commons.GlobalConstants;
import commons.PageGeneratorManager;
import pageUIs.BasePageUI;
import pageUIs.CheckoutPageUI;

public class CheckoutPageObject extends BasePage {
	private WebDriver driver;

	public CheckoutPageObject(WebDriver driver) {
		this.driver = driver;
	}

	public boolean isAddressCardByStreetAddressSelected(String streetAddress) {
		String value = getElementAttribute(driver, CheckoutPageUI.Shipping.DYNAMIC_ADDRESS_CARD_BY_STREET_ADDRESS,
				"class", streetAddress);
		if (value.contains("not-selected-item")) {
			return false;
		}
		return true;
	}

	public void clickShipHereButtonByStreetAddress(String streetAddress) {
		waitForElementClickable(driver, CheckoutPageUI.Shipping.DYNAMIC_SHIP_HERE_BUTTON_BY_STREET_ADDRESS,
				streetAddress);
		clickElementByJS(driver, CheckoutPageUI.Shipping.DYNAMIC_SHIP_HERE_BUTTON_BY_STREET_ADDRESS, streetAddress);
	}

	public AddressPageObject clickNewAddressButton() {
		waitForElementClickable(driver, CheckoutPageUI.Shipping.NEW_ADDRESS_BUTTON);
		clickElementByJS(driver, CheckoutPageUI.Shipping.NEW_ADDRESS_BUTTON);
		return PageGeneratorManager.getAddressPage(driver);
	}

	public void sendKeysToFirstNameTextbox(String firstName) {
		waitForElementVisible(driver, CheckoutPageUI.Shipping.FIRST_NAME_TEXTBOX);
		sendKeysToElement(driver, CheckoutPageUI.Shipping.FIRST_NAME_TEXTBOX, firstName);
	}

	public void sendKeysToLastNameTextbox(String lastName) {
		waitForElementVisible(driver, CheckoutPageUI.Shipping.LAST_NAME_TEXTBOX);
		sendKeysToElement(driver, CheckoutPageUI.Shipping.LAST_NAME_TEXTBOX, lastName);
	}

	public void sendKeysToStreetAddressTextbox(String streetAddress) {
		waitForElementVisible(driver, CheckoutPageUI.Shipping.STREET_ADDRESS_TEXTBOX);
		sendKeysToElement(driver, CheckoutPageUI.Shipping.STREET_ADDRESS_TEXTBOX, streetAddress);
	}

	public void sendKeysToCityTextbox(String city) {
		waitForElementVisible(driver, CheckoutPageUI.Shipping.CITY_TEXTBOX);
		sendKeysToElement(driver, CheckoutPageUI.Shipping.CITY_TEXTBOX, city);
	}

	public void selectOptionStateProvinceDropdown(String stateProvince) {
		waitForElementVisible(driver, CheckoutPageUI.Shipping.STATE_PROVINCE_DROPDOWN);
		selectOptionDefaultDropdown(driver, CheckoutPageUI.Shipping.STATE_PROVINCE_DROPDOWN, stateProvince);
	}

	public void sendKeysToZipPostalCodeTextbox(String zipPostalCode) {
		waitForElementVisible(driver, CheckoutPageUI.Shipping.ZIP_POSTAL_CODE_TEXTBOX);
		sendKeysToElement(driver, CheckoutPageUI.Shipping.ZIP_POSTAL_CODE_TEXTBOX, zipPostalCode);
	}

	public void selectOptionCountryDropdown(String country) {
		waitForElementVisible(driver, CheckoutPageUI.Shipping.COUNTRY_DROPDOWN);
		selectOptionDefaultDropdown(driver, CheckoutPageUI.Shipping.COUNTRY_DROPDOWN, country);
	}

	public void sendKeysToPhoneNumberTextbox(String phoneNumber) {
		waitForElementVisible(driver, CheckoutPageUI.Shipping.PHONE_NUMBER_TEXTBOX);
		sendKeysToElement(driver, CheckoutPageUI.Shipping.PHONE_NUMBER_TEXTBOX, phoneNumber);
	}

	public void checkSaveInAddressBookCheckbox() {
		waitForElementClickable(driver, CheckoutPageUI.Shipping.SAVE_IN_ADDRESS_BOOK_CHECKBOX);
		checkDefaultCheckboxRadioButton(driver, CheckoutPageUI.Shipping.SAVE_IN_ADDRESS_BOOK_CHECKBOX);
	}

	public void clickShippingAddressPopupShipHereButton() {
		waitForElementClickable(driver, CheckoutPageUI.Shipping.SHIPPING_ADDRESS_POPUP_SHIP_HERE_BUTTON);
		checkDefaultCheckboxRadioButton(driver, CheckoutPageUI.Shipping.SHIPPING_ADDRESS_POPUP_SHIP_HERE_BUTTON);
	}

	public boolean isShippingAddressDisplayed(String streetAddress) {
		return isElementDisplayed(driver, CheckoutPageUI.Shipping.DYNAMIC_ADDRESS_CARD_BY_STREET_ADDRESS,
				streetAddress);
	}

	public void clickNextButton() {
		waitForElementClickable(driver, CheckoutPageUI.Shipping.NEXT_BUTTON);
		clickElementByJS(driver, CheckoutPageUI.Shipping.NEXT_BUTTON);
	}

	public boolean isUserOnShippingStep() {
		String value = getElementAttribute(driver, CheckoutPageUI.General.SHIPPING_STEP_LABEL, "class");
		if (value.contains("active")) {
			return true;
		}
		return false;
	}

	public boolean isUserOnReviewAndPaymentStep() {
		String value = getElementAttribute(driver, CheckoutPageUI.General.REVIEW_AND_PAYMENT_STEP_LABEL, "class");
		if (value.contains("active")) {
			return true;
		}
		return false;
	}

	public void clickSignInLink() {
		waitForElementClickable(driver, CheckoutPageUI.General.SIGN_IN_LINK);
		clickElementByJS(driver, CheckoutPageUI.General.SIGN_IN_LINK);
	}

	public void sendKeysToSignInModalEmailAddressTextbox(String email) {
		waitForElementVisible(driver, CheckoutPageUI.General.SIGN_IN_MODAL_EMAIL_ADDRESS_TEXTBOX);
		sendKeysToElement(driver, CheckoutPageUI.General.SIGN_IN_MODAL_EMAIL_ADDRESS_TEXTBOX, email);
	}

	public void sendKeysToSignInModalPasswordTextbox(String password) {
		waitForElementVisible(driver, CheckoutPageUI.General.SIGN_IN_MODAL_PASSWORD_TEXTBOX);
		sendKeysToElement(driver, CheckoutPageUI.General.SIGN_IN_MODAL_PASSWORD_TEXTBOX, password);
	}

	public void clickSignInModalSignInButton() {
		waitForElementClickable(driver, CheckoutPageUI.General.SIGN_IN_MODAL_SIGN_IN_BUTTON);
		clickElementByJS(driver, CheckoutPageUI.General.SIGN_IN_MODAL_SIGN_IN_BUTTON);
	}

	public void selectShippingMethodRadioButtonByLabel(String label) {
		waitForElementClickable(driver, CheckoutPageUI.Shipping.DYNAMIC_SHIPPING_METHOD_RADIO_BUTTON_BY_LABEL, label);
		clickElementByJS(driver, CheckoutPageUI.Shipping.DYNAMIC_SHIPPING_METHOD_RADIO_BUTTON_BY_LABEL, label);
	}

	public String getShippingMethodNotSelectedWarningMessage() {
		waitForElementVisible(driver, CheckoutPageUI.Shipping.SHIPPING_METHOD_NOT_SELECTED_WARNING_MESSAGE);
		return getElementText(driver, CheckoutPageUI.Shipping.SHIPPING_METHOD_NOT_SELECTED_WARNING_MESSAGE);
	}

	public boolean isTableRateRadioButtonDisplayed() {
		return isElementDisplayed(driver, CheckoutPageUI.Shipping.DYNAMIC_SHIPPING_METHOD_RADIO_BUTTON_BY_LABEL,
				"Table Rate");
	}

	public void clickShippingStepLabel() {
		waitForElementClickable(driver, CheckoutPageUI.General.SHIPPING_STEP_LABEL);
		clickElementByJS(driver, CheckoutPageUI.General.SHIPPING_STEP_LABEL);

	}

	public void checkMyBillingAndShippingAddressAreTheSame() {
		waitForElementClickable(driver, CheckoutPageUI.ReviewAndPayment.MY_BILLING_AND_SHIPPING_ADDRESS_ARE_THE_SAME);
		checkDefaultCheckboxRadioButton(driver,
				CheckoutPageUI.ReviewAndPayment.MY_BILLING_AND_SHIPPING_ADDRESS_ARE_THE_SAME);
	}

	public void uncheckMyBillingAndShippingAddressAreTheSameCheckbox() {
		waitForElementClickable(driver, CheckoutPageUI.ReviewAndPayment.MY_BILLING_AND_SHIPPING_ADDRESS_ARE_THE_SAME);
		uncheckDefaultCheckbox(driver, CheckoutPageUI.ReviewAndPayment.MY_BILLING_AND_SHIPPING_ADDRESS_ARE_THE_SAME);
	}

	public String getBillingAddress() {
		// TODO Auto-generated method stub
		return null;
	}

	public void clickEditBillingAddressButton() {
		waitForElementClickable(driver, CheckoutPageUI.ReviewAndPayment.EDIT_BUTTON);
		clickElementByJS(driver, CheckoutPageUI.ReviewAndPayment.EDIT_BUTTON);
	}

	public void selectOptionBillingAddressDropdown(String streetAddress) {
		waitForElementVisible(driver, CheckoutPageUI.ReviewAndPayment.BILLING_ADDRESS_DROPDOWN);
		selectOptionDefaultDropdown(driver, CheckoutPageUI.ReviewAndPayment.BILLING_ADDRESS_DROPDOWN, streetAddress);
	}

	public void clickCancelLink() {
		waitForElementClickable(driver, CheckoutPageUI.ReviewAndPayment.CANCEL_LINK);
		clickElementByJS(driver, CheckoutPageUI.ReviewAndPayment.CANCEL_LINK);
	}

	public boolean isMyBillingAndShippingAddressAreTheSameCheckboxChecked() {
		WebElement checkbox = getWebElement(driver,
				CheckoutPageUI.ReviewAndPayment.MY_BILLING_AND_SHIPPING_ADDRESS_ARE_THE_SAME);
		if (checkbox.isSelected()) {
			return true;
		}
		return false;
	}

	public void clickApplyDiscountCodeHeader() {
		waitForElementClickable(driver, CheckoutPageUI.ReviewAndPayment.APPLY_DISCOUNT_CODE_HEADER);
		clickElementByJS(driver, CheckoutPageUI.ReviewAndPayment.APPLY_DISCOUNT_CODE_HEADER);
	}

	public void sendKeysToEnterDiscountCodeTextbox(String discountCode) {
		waitForElementVisible(driver, CheckoutPageUI.ReviewAndPayment.ENTER_DISCOUNT_CODE_TEXTBOX);
		sendKeysToElement(driver, CheckoutPageUI.ReviewAndPayment.ENTER_DISCOUNT_CODE_TEXTBOX, discountCode);
	}

	public void clickApplyDiscountButton() {
		waitForElementClickable(driver, CheckoutPageUI.ReviewAndPayment.APPLY_DISCOUNT_BUTTON);
		clickElementByJS(driver, CheckoutPageUI.ReviewAndPayment.APPLY_DISCOUNT_BUTTON);
	}

	public String getDiscountCodeAppliedSuccessMessage() {
		waitForElementVisible(driver, BasePageUI.MainContent.MESSAGE);
		return getElementText(driver, BasePageUI.MainContent.MESSAGE);
	}

	public float getCartSubtotal() {
		waitForElementVisible(driver, CheckoutPageUI.ReviewAndPayment.CART_SUBTOTAL);
		return Float.parseFloat(getElementText(driver, CheckoutPageUI.ReviewAndPayment.CART_SUBTOTAL).replace("$", ""));
	}

	public float getDiscount() {
		waitForElementVisible(driver, CheckoutPageUI.ReviewAndPayment.DISCOUNT);
		return Float.parseFloat(getElementText(driver, CheckoutPageUI.ReviewAndPayment.DISCOUNT).replace("-$", ""));
	}

	public float getShipping() {
		waitForElementVisible(driver, CheckoutPageUI.ReviewAndPayment.SHIPPING);
		return Float.parseFloat(getElementText(driver, CheckoutPageUI.ReviewAndPayment.SHIPPING).replace("$", ""));
	}

	public float getOrderTotal() {
		waitForElementVisible(driver, CheckoutPageUI.ReviewAndPayment.ORDER_TOTAL);
		return Float.parseFloat(getElementText(driver, CheckoutPageUI.ReviewAndPayment.ORDER_TOTAL).replace("$", ""));
	}

	public void clickPenIconShipToSection() {
		waitForElementClickable(driver, CheckoutPageUI.ReviewAndPayment.PEN_ICON_SHIP_TO_SECTION);
		clickElementByJS(driver, CheckoutPageUI.ReviewAndPayment.PEN_ICON_SHIP_TO_SECTION);
	}

	public void clickPenIconShippingMethodSection() {
		waitForElementClickable(driver, CheckoutPageUI.ReviewAndPayment.PEN_ICON_SHIPPING_METHOD_SECTION);
		clickElementByJS(driver, CheckoutPageUI.ReviewAndPayment.PEN_ICON_SHIPPING_METHOD_SECTION);
	}

	public void clickPlaceOrderButton() {
		waitForElementClickable(driver, CheckoutPageUI.ReviewAndPayment.PLACE_ORDER_BUTTON);
		clickElementByJS(driver, CheckoutPageUI.ReviewAndPayment.PLACE_ORDER_BUTTON);
	}

	public HomepageObject clickContinueShoppingButton() {
		waitForElementClickable(driver, CheckoutPageUI.OrderSuccess.CONTINUE_SHOPPING_BUTTON);
		clickElementByJS(driver, CheckoutPageUI.OrderSuccess.CONTINUE_SHOPPING_BUTTON);
		return PageGeneratorManager.getHomepage(driver);
	}

	public OrderDetailsPageObject clickOrderNumber() {
		waitForElementClickable(driver, CheckoutPageUI.OrderSuccess.ORDER_NUMBER);
		clickElementByJS(driver, CheckoutPageUI.OrderSuccess.ORDER_NUMBER);
		return PageGeneratorManager.getOrderDetaisPage(driver);
	}

	public String getOrderNumber() {
		waitForElementVisible(driver, CheckoutPageUI.OrderSuccess.ORDER_NUMBER);
		return getElementText(driver, CheckoutPageUI.OrderSuccess.ORDER_NUMBER);
	}

	public void sendKeysToEmailAddressTextbox(String email) {
		waitForElementVisible(driver, CheckoutPageUI.Shipping.EMAIL_ADDRESS_TEXTBOX);
		sendKeysToElement(driver, CheckoutPageUI.Shipping.EMAIL_ADDRESS_TEXTBOX, email);
		clickOutisdeElement(driver);
		sleepInSecond(GlobalConstants.SHORT_TIMEOUT);
	}

	public void sendKeysToPasswordTextbox(String password) {
		waitForElementVisible(driver, CheckoutPageUI.Shipping.PASSWORD_TEXTBOX);
		sendKeysToElement(driver, CheckoutPageUI.Shipping.PASSWORD_TEXTBOX, password);
	}

	public void clickLoginButton() {
		waitForElementClickable(driver, CheckoutPageUI.Shipping.LOGIN_BUTTON);
		clickElementByJS(driver, CheckoutPageUI.Shipping.LOGIN_BUTTON);
	}

	public boolean isPasswordTextboxDisplayed() {
		return isElementDisplayed(driver, CheckoutPageUI.Shipping.PASSWORD_TEXTBOX);
	}

	public boolean isLoginButtonDisplayed() {
		return isElementDisplayed(driver, CheckoutPageUI.Shipping.LOGIN_BUTTON);
	}

	public boolean isForgotYourPasswordLinkDisplayed() {
		return isElementDisplayed(driver, CheckoutPageUI.Shipping.FORGOT_YOUR_PASSWORD_LINK);
	}

}
