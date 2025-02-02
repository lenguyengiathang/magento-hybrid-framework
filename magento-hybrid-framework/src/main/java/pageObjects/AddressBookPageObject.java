package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.BasePage;
import commons.PageGeneratorManager;
import pageUIs.AddressBookPageUI;

public class AddressBookPageObject extends BasePage {
	private WebDriver driver;

	public AddressBookPageObject(WebDriver driver) {
		this.driver = driver;
	}

	public String getDefaultBillingAddressFullName() {
		waitForElementVisible(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_BILLING_ADDRESS);
		String fullAddress = getElementText(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_BILLING_ADDRESS);
		return fullAddress.split("\n")[0].trim();
	}

	public String getDefaultBillingAddressCompany() {
		waitForElementVisible(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_BILLING_ADDRESS);
		String fullAddress = getElementText(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_BILLING_ADDRESS);
		return fullAddress.split("\n")[1].trim();
	}

	public String getDefaultBillingAddressAddressFirstLine() {
		waitForElementVisible(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_BILLING_ADDRESS);
		String fullAddress = getElementText(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_BILLING_ADDRESS);
		return fullAddress.split("\n")[2].trim();
	}

	public String getDefaultBillingAddressAddressSecondLine() {
		waitForElementVisible(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_BILLING_ADDRESS);
		String fullAddress = getElementText(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_BILLING_ADDRESS);
		return fullAddress.split("\n")[3].trim();
	}

	public String getDefaultBillingAddressAddressThirdLine() {
		waitForElementVisible(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_BILLING_ADDRESS);
		String fullAddress = getElementText(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_BILLING_ADDRESS);
		return fullAddress.split("\n")[4].trim();
	}

	public String getDefaultBillingAddressCity() {
		waitForElementVisible(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_BILLING_ADDRESS);
		String fullAddress = getElementText(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_BILLING_ADDRESS);
		return fullAddress.split("\n")[5].split(",")[0].trim();
	}

	public String getDefaultBillingAddressState() {
		waitForElementVisible(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_BILLING_ADDRESS);
		String fullAddress = getElementText(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_BILLING_ADDRESS);
		return fullAddress.split("\n")[5].split(",")[1].trim();
	}

	public String getDefaultBillingAddressZipPostalCode() {
		waitForElementVisible(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_BILLING_ADDRESS);
		String fullAddress = getElementText(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_BILLING_ADDRESS);
		return fullAddress.split("\n")[5].split(",")[2].trim();
	}

	public String getDefaultBillingAddressCountry() {
		waitForElementVisible(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_BILLING_ADDRESS);
		String fullAddress = getElementText(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_BILLING_ADDRESS);
		return fullAddress.split("\n")[6].trim();
	}

	public String getDefaultBillingAddressPhone() {
		waitForElementVisible(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_BILLING_ADDRESS_PHONE);
		return getElementText(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_BILLING_ADDRESS_PHONE);
	}

	public String getDefaultShippingAddressFullName() {
		waitForElementVisible(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_SHIPPING_ADDRESS);
		String fullAddress = getElementText(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_SHIPPING_ADDRESS);
		return fullAddress.split("<br>")[0].trim();
	}

	public String getDefaultShippingAddressCompany() {
		waitForElementVisible(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_SHIPPING_ADDRESS);
		String fullAddress = getElementText(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_SHIPPING_ADDRESS);
		return fullAddress.split("<br>")[1].trim();
	}

	public String getDefaultShippingAddressAddressFirstLine() {
		waitForElementVisible(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_SHIPPING_ADDRESS);
		String fullAddress = getElementText(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_SHIPPING_ADDRESS);
		return fullAddress.split("<br>")[2].trim();
	}

	public String getDefaultShippingAddressAddressSecondLine() {
		waitForElementVisible(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_SHIPPING_ADDRESS);
		String fullAddress = getElementText(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_SHIPPING_ADDRESS);
		return fullAddress.split("<br>")[3].trim();
	}

	public String getDefaultShippingAddressAddressThirdLine() {
		waitForElementVisible(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_SHIPPING_ADDRESS);
		String fullAddress = getElementText(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_SHIPPING_ADDRESS);
		return fullAddress.split("<br>")[4].trim();
	}

	public String getDefaultShippingAddressCity() {
		waitForElementVisible(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_SHIPPING_ADDRESS);
		String fullAddress = getElementText(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_SHIPPING_ADDRESS);
		return fullAddress.split("<br>")[5].split(",")[0].trim();
	}

	public String getDefaultShippingAddressState() {
		waitForElementVisible(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_SHIPPING_ADDRESS);
		String fullAddress = getElementText(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_SHIPPING_ADDRESS);
		return fullAddress.split("<br>")[5].split(",")[1].trim();
	}

	public String getDefaultShippingAddressZipPostalCode() {
		waitForElementVisible(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_SHIPPING_ADDRESS);
		String fullAddress = getElementText(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_SHIPPING_ADDRESS);
		return fullAddress.split("<br>")[5].split(",")[2].trim();
	}

	public String getDefaultShippingAddressCountry() {
		waitForElementVisible(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_SHIPPING_ADDRESS);
		String fullAddress = getElementText(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_SHIPPING_ADDRESS);
		return fullAddress.split("<br>")[6].trim();
	}

	public String getDefaultShippingAddressPhone() {
		waitForElementVisible(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_SHIPPING_ADDRESS_PHONE);
		return getElementText(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_SHIPPING_ADDRESS_PHONE);
	}

	public String getAdditionalAddressValueByRowIndexAndColumnName(String rowIndex, String columnName) {
		waitForElementVisible(driver,
				AddressBookPageUI.AdditionalAddressEntries.DYNAMIC_ADDITIONAL_ADDRESS_VALUE_BY_ROW_INDEX_AND_COLUMN_NAME,
				rowIndex, columnName);
		return getElementText(driver,
				AddressBookPageUI.AdditionalAddressEntries.DYNAMIC_ADDITIONAL_ADDRESS_VALUE_BY_ROW_INDEX_AND_COLUMN_NAME,
				rowIndex, columnName);
	}

	public AddressPageObject clickAddNewAddressButton() {
		waitForElementClickable(driver, AddressBookPageUI.ADD_NEW_ADDRESS_BUTTON);
		clickElementByJS(driver, AddressBookPageUI.ADD_NEW_ADDRESS_BUTTON);
		return PageGeneratorManager.getAddressPage(driver);
	}
}
