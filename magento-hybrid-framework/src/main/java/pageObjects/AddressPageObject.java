package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.BasePage;
import commons.PageGeneratorManager;
import pageUIs.AddressPageUI;

public class AddressPageObject extends BasePage {
	private WebDriver driver;

	public AddressPageObject(WebDriver driver) {
		this.driver = driver;
	}

	public void sendKeysToFirstNameTextbox(String firstName) {
		waitForElementVisible(driver, AddressPageUI.FIRST_NAME_TEXTBOX);
		sendKeysToElement(driver, AddressPageUI.FIRST_NAME_TEXTBOX, firstName);
	}

	public void sendKeysToLastNameTextbox(String lastName) {
		waitForElementVisible(driver, AddressPageUI.LAST_NAME_TEXTBOX);
		sendKeysToElement(driver, AddressPageUI.LAST_NAME_TEXTBOX, lastName);
	}

	public void sendKeysToCompanyTextbox(String company) {
		waitForElementVisible(driver, AddressPageUI.COMPANY_TEXTBOX);
		sendKeysToElement(driver, AddressPageUI.COMPANY_TEXTBOX, company);
	}

	public void sendKeysToPhoneNumberTextbox(String phoneNumber) {
		waitForElementVisible(driver, AddressPageUI.PHONE_NUMBER_TEXTBOX);
		sendKeysToElement(driver, AddressPageUI.PHONE_NUMBER_TEXTBOX, phoneNumber);
	}

	public void sendKeysToStreetAddressTextbox(String address) {
		waitForElementVisible(driver, AddressPageUI.STREET_ADDRESS_FIRST_LINE_TEXTBOX);
		sendKeysToElement(driver, AddressPageUI.STREET_ADDRESS_FIRST_LINE_TEXTBOX, address);
	}

	public void sendKeysToStreetAddressSecondLineTextbox(String address) {
		waitForElementVisible(driver, AddressPageUI.STREET_ADDRESS_SECOND_LINE_TEXTBOX);
		sendKeysToElement(driver, AddressPageUI.STREET_ADDRESS_SECOND_LINE_TEXTBOX, address);
	}

	public void sendKeysToStreetAddressThirdLineTextbox(String address) {
		waitForElementVisible(driver, AddressPageUI.STREET_ADDRESS_THIRD_LINE_TEXTBOX);
		sendKeysToElement(driver, AddressPageUI.STREET_ADDRESS_THIRD_LINE_TEXTBOX, address);
	}

	public void sendKeysToCityTextbox(String city) {
		waitForElementVisible(driver, AddressPageUI.CITY_TEXTBOX);
		sendKeysToElement(driver, AddressPageUI.CITY_TEXTBOX, city);
	}

	public void selectOptionStateProvinceDropdown(String stateProvince) {
		waitForElementVisible(driver, AddressPageUI.STATE_PROVINCE_DROPDOWN);
		selectOptionDefaultDropdown(driver, AddressPageUI.STATE_PROVINCE_DROPDOWN, stateProvince);
	}

	public void sendKeysToZipPostalTextbox(String zipPostalCode) {
		waitForElementVisible(driver, AddressPageUI.ZIP_POSTAL_CODE_TEXTBOX);
		sendKeysToElement(driver, AddressPageUI.ZIP_POSTAL_CODE_TEXTBOX, zipPostalCode);
		clickOutisdeElement(driver);
	}

	public String getInvalidZipPostalCodeWarningMessage() {
		waitForElementVisible(driver, AddressPageUI.INVALID_ZIP_POSTAL_CODE_WARNING_MESSAGE);
		return getElementText(driver, AddressPageUI.INVALID_ZIP_POSTAL_CODE_WARNING_MESSAGE);
	}

	public void selectOptionCountryDropdown(String country) {
		waitForElementVisible(driver, AddressPageUI.COUNTRY_DROPDOWN);
		selectOptionDefaultDropdown(driver, AddressPageUI.COUNTRY_DROPDOWN, country);
	}

	public void checkUseAsMyDefaultBillingAddressCheckbox() {
		waitForElementClickable(driver, AddressPageUI.USE_AS_MY_DEFAULT_BILLING_ADDRESS_CHECKBOX);
		checkDefaultCheckboxRadioButton(driver, AddressPageUI.USE_AS_MY_DEFAULT_BILLING_ADDRESS_CHECKBOX);
	}

	public void checkUseAsMyDefaultShippingAddressCheckbox() {
		waitForElementClickable(driver, AddressPageUI.USE_AS_MY_DEFAULT_SHIPPING_ADDRESS_CHECKBOX);
		checkDefaultCheckboxRadioButton(driver, AddressPageUI.USE_AS_MY_DEFAULT_SHIPPING_ADDRESS_CHECKBOX);
	}

	public String getDefaultAddressInfoMessage() {
		waitForElementVisible(driver, AddressPageUI.DEFAULT_ADDRESS_INFO_MESSAGE);
		return getElementText(driver, AddressPageUI.DEFAULT_ADDRESS_INFO_MESSAGE);
	}

	public AddressBookPageObject clickSaveAddressButton() {
		waitForElementClickable(driver, AddressPageUI.SAVE_ADDRESS_BUTTON);
		clickElementByJS(driver, AddressPageUI.SAVE_ADDRESS_BUTTON);
		return PageGeneratorManager.getAddressBookPage(driver);
	}

	public void completeAddressForm(String[] data) {
		sendKeysToCompanyTextbox(data[0]);
		sendKeysToPhoneNumberTextbox(data[1]);
		sendKeysToStreetAddressTextbox(data[2]);
		sendKeysToStreetAddressSecondLineTextbox(data[3]);
		sendKeysToStreetAddressThirdLineTextbox(data[4]);
		sendKeysToCityTextbox(data[5]);
		sendKeysToZipPostalTextbox(data[7]);
		selectOptionCountryDropdown(data[8]);
		selectOptionStateProvinceDropdown(data[6]);
	}
}
