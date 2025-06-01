package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.BasePage;
import io.qameta.allure.Step;
import pageUIs.AddressPageUI;

public class AddressPageObject extends BasePage {
	private WebDriver driver;

	public AddressPageObject(WebDriver driver) {
		this.driver = driver;
	}

	@Step("Fill in the 'First Name' field with the value '{firstName}'")
	public void sendKeysToFirstNameTextbox(String firstName) {
		waitForElementVisible(driver, AddressPageUI.FIRST_NAME_TEXTBOX);
		sendKeysToElement(driver, AddressPageUI.FIRST_NAME_TEXTBOX, firstName);
	}

	@Step("Fill in the 'Last Name' field with the value '{lastName}'")
	public void sendKeysToLastNameTextbox(String lastName) {
		waitForElementVisible(driver, AddressPageUI.LAST_NAME_TEXTBOX);
		sendKeysToElement(driver, AddressPageUI.LAST_NAME_TEXTBOX, lastName);
	}

	@Step("Fill in the 'Company' field with the value '{company}'")
	public void sendKeysToCompanyTextbox(String company) {
		waitForElementVisible(driver, AddressPageUI.COMPANY_TEXTBOX);
		sendKeysToElement(driver, AddressPageUI.COMPANY_TEXTBOX, company);
	}

	@Step("Fill in the 'Phone Number' field with the value '{phoneNumber}'")
	public void sendKeysToPhoneNumberTextbox(String phoneNumber) {
		waitForElementVisible(driver, AddressPageUI.PHONE_NUMBER_TEXTBOX);
		sendKeysToElement(driver, AddressPageUI.PHONE_NUMBER_TEXTBOX, phoneNumber);
	}

	@Step("Fill in the first 'Street Address' field with the value '{address}'")
	public void sendKeysToStreetAddressTextbox(String address) {
		waitForElementVisible(driver, AddressPageUI.STREET_ADDRESS_FIRST_LINE_TEXTBOX);
		sendKeysToElement(driver, AddressPageUI.STREET_ADDRESS_FIRST_LINE_TEXTBOX, address);
	}

	@Step("Fill in the second 'Street Address' field with the value '{address}'")
	public void sendKeysToStreetAddressSecondLineTextbox(String address) {
		waitForElementVisible(driver, AddressPageUI.STREET_ADDRESS_SECOND_LINE_TEXTBOX);
		sendKeysToElement(driver, AddressPageUI.STREET_ADDRESS_SECOND_LINE_TEXTBOX, address);
	}

	@Step("Fill in the third 'Street Address' field with the value '{address}'")
	public void sendKeysToStreetAddressThirdLineTextbox(String address) {
		waitForElementVisible(driver, AddressPageUI.STREET_ADDRESS_THIRD_LINE_TEXTBOX);
		sendKeysToElement(driver, AddressPageUI.STREET_ADDRESS_THIRD_LINE_TEXTBOX, address);
	}

	@Step("Fill in the 'City' field with the value '{city}'")
	public void sendKeysToCityTextbox(String city) {
		waitForElementVisible(driver, AddressPageUI.CITY_TEXTBOX);
		sendKeysToElement(driver, AddressPageUI.CITY_TEXTBOX, city);
	}

	@Step("Select the '{stateProvince}' option from the 'State/Province' dropdown")
	public void selectOptionStateProvinceDropdown(String stateProvince) {
		waitForElementVisible(driver, AddressPageUI.STATE_PROVINCE_DROPDOWN);
		selectOptionDefaultDropdown(driver, AddressPageUI.STATE_PROVINCE_DROPDOWN, stateProvince);
	}

	@Step("Fill in the 'Zip/Postal Code' field with the value '{zipPostalCode}'")
	public void sendKeysToZipPostalTextbox(String zipPostalCode) {
		waitForElementVisible(driver, AddressPageUI.ZIP_POSTAL_CODE_TEXTBOX);
		sendKeysToElement(driver, AddressPageUI.ZIP_POSTAL_CODE_TEXTBOX, zipPostalCode);
		clickOutisdeElement(driver);
	}

	@Step("Get the warning message displayed when an invalid zip or postal code is filled in")
	public String getInvalidZipPostalCodeWarningMessage() {
		waitForElementVisible(driver, AddressPageUI.INVALID_ZIP_POSTAL_CODE_WARNING_MESSAGE);
		return getElementText(driver, AddressPageUI.INVALID_ZIP_POSTAL_CODE_WARNING_MESSAGE);
	}

	@Step("Select the '{country}' option from the 'Country' dropdown")
	public void selectOptionCountryDropdown(String country) {
		waitForElementVisible(driver, AddressPageUI.COUNTRY_DROPDOWN);
		selectOptionDefaultDropdown(driver, AddressPageUI.COUNTRY_DROPDOWN, country);
	}

	@Step("Check the 'Use as my default billing address' checkbox")
	public void checkUseAsMyDefaultBillingAddressCheckbox() {
		waitForElementClickable(driver, AddressPageUI.USE_AS_MY_DEFAULT_BILLING_ADDRESS_CHECKBOX);
		checkDefaultCheckboxRadioButton(driver, AddressPageUI.USE_AS_MY_DEFAULT_BILLING_ADDRESS_CHECKBOX);
	}

	@Step("Check the 'Use as my default shipping address' checkbox")
	public void checkUseAsMyDefaultShippingAddressCheckbox() {
		waitForElementClickable(driver, AddressPageUI.USE_AS_MY_DEFAULT_SHIPPING_ADDRESS_CHECKBOX);
		checkDefaultCheckboxRadioButton(driver, AddressPageUI.USE_AS_MY_DEFAULT_SHIPPING_ADDRESS_CHECKBOX);
	}

	@Step("Get the default address info message")
	public String getDefaultAddressInfoMessage() {
		waitForElementVisible(driver, AddressPageUI.DEFAULT_ADDRESS_INFO_MESSAGE);
		return getElementText(driver, AddressPageUI.DEFAULT_ADDRESS_INFO_MESSAGE);
	}

	@Step("Get the default address info message at index {index}")
	public String getDefaultAddressInfoMessageByIndex(String index) {
		waitForElementVisible(driver, AddressPageUI.DYNAMIC_DEFAULT_ADDRESS_INFO_MESSAGE_BY_INDEX, index);
		return getElementText(driver, AddressPageUI.DYNAMIC_DEFAULT_ADDRESS_INFO_MESSAGE_BY_INDEX, index);
	}

	@Step("Complete address form by filling in the mandatory fields with valid information")
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
