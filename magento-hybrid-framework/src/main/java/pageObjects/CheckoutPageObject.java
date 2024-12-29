package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.BasePage;
import pageUIs.CheckoutPageUI;

public class CheckoutPageObject extends BasePage {
	private WebDriver driver;

	public CheckoutPageObject(WebDriver driver) {
		this.driver = driver;
	}

	public boolean isAddressCardSelected() {
		return true;
	}

	public void clickAddressCardShipHereButton() {
		waitForElementClickable(driver, CheckoutPageUI.ADDRESS_CARD_SHIP_HERE_BUTTON);
		clickElement(driver, CheckoutPageUI.ADDRESS_CARD_SHIP_HERE_BUTTON);
	}

	public void clickNewAddressButton() {
		waitForElementClickable(driver, CheckoutPageUI.NEW_ADDRESS_BUTTON);
		clickElement(driver, CheckoutPageUI.NEW_ADDRESS_BUTTON);
	}

	public void sendKeysToFirstNameTextbox(String firstName) {
		waitForElementVisible(driver, CheckoutPageUI.FIRST_NAME_TEXTBOX);
		sendKeysToElement(driver, CheckoutPageUI.FIRST_NAME_TEXTBOX, firstName);
	}

	public void sendKeysToLastNameTextbox(String lastName) {
		waitForElementVisible(driver, CheckoutPageUI.LAST_NAME_TEXTBOX);
		sendKeysToElement(driver, CheckoutPageUI.LAST_NAME_TEXTBOX, lastName);
	}

	public void sendKeysToStreetAddressTextbox(String streetAddress) {
		waitForElementVisible(driver, CheckoutPageUI.STREET_ADDRESS_TEXTBOX);
		sendKeysToElement(driver, CheckoutPageUI.STREET_ADDRESS_TEXTBOX, streetAddress);
	}

	public void sendKeysToCityTextbox(String city) {
		waitForElementVisible(driver, CheckoutPageUI.CITY_TEXTBOX);
		sendKeysToElement(driver, CheckoutPageUI.CITY_TEXTBOX, city);
	}

	public void sendKeysToZipPostalCodeTextbox(String zipPostalCode) {
		waitForElementVisible(driver, CheckoutPageUI.ZIP_POSTAL_CODE_TEXTBOX);
		sendKeysToElement(driver, CheckoutPageUI.ZIP_POSTAL_CODE_TEXTBOX, zipPostalCode);
	}

	public void selectOptionCountryDropdown(String country) {
		waitForElementVisible(driver, CheckoutPageUI.COUNTRY_DROPDOWN);
		selectOptionDefaultDropdown(driver, CheckoutPageUI.COUNTRY_DROPDOWN, country);
	}

	public void sendKeysToPhoneNumberTextbox(String phoneNumber) {
		waitForElementVisible(driver, CheckoutPageUI.PHONE_NUMBER_TEXTBOX);
		sendKeysToElement(driver, CheckoutPageUI.PHONE_NUMBER_TEXTBOX, phoneNumber);
	}

	public void checkSaveInAddressBookCheckbox() {
		waitForElementClickable(driver, CheckoutPageUI.SAVE_IN_ADDRESS_BOOK_CHECKBOX);
		checkDefaultCheckboxRadioButton(driver, CheckoutPageUI.SAVE_IN_ADDRESS_BOOK_CHECKBOX);
	}

	public void clickShippingAddressPopupShipHereButton() {
		waitForElementClickable(driver, CheckoutPageUI.SHIPPING_ADDRESS_POPUP_SHIP_HERE_BUTTON);
		checkDefaultCheckboxRadioButton(driver, CheckoutPageUI.SHIPPING_ADDRESS_POPUP_SHIP_HERE_BUTTON);
	}

	public void clickNextButton() {
		waitForElementClickable(driver, CheckoutPageUI.NEXT_BUTTON);
		clickElement(driver, CheckoutPageUI.NEXT_BUTTON);
	}

}
