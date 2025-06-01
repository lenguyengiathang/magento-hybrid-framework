package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.BasePage;
import commons.PageGeneratorManager;
import io.qameta.allure.Step;
import pageUIs.AddressBookPageUI;

public class AddressBookPageObject extends BasePage {
	private WebDriver driver;

	public AddressBookPageObject(WebDriver driver) {
		this.driver = driver;
	}

	@Step("Click the 'Change Billing Address' link")
	public AddressPageObject clickChangeBillingAddressLink() {
		waitForElementClickable(driver, AddressBookPageUI.DefaultAddresses.CHANGE_BILLING_ADDRESS_LINK);
		clickElementByJS(driver, AddressBookPageUI.DefaultAddresses.CHANGE_BILLING_ADDRESS_LINK);
		return PageGeneratorManager.getAddressPage(driver);
	}

	@Step("Click the 'Change Shipping Address' link")
	public AddressPageObject clickChangeShippingAddressLink() {
		waitForElementClickable(driver, AddressBookPageUI.DefaultAddresses.CHANGE_SHIPPING_ADDRESS_LINK);
		clickElementByJS(driver, AddressBookPageUI.DefaultAddresses.CHANGE_SHIPPING_ADDRESS_LINK);
		return PageGeneratorManager.getAddressPage(driver);
	}

	@Step("Get the full name of the default billing address")
	public String getDefaultBillingAddressFullName() {
		waitForElementVisible(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_BILLING_ADDRESS);
		String fullAddress = getElementText(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_BILLING_ADDRESS);
		return fullAddress.split("\n")[0].trim();
	}

	@Step("Get the company of the default billing address")
	public String getDefaultBillingAddressCompany() {
		waitForElementVisible(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_BILLING_ADDRESS);
		String fullAddress = getElementText(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_BILLING_ADDRESS);
		return fullAddress.split("\n")[1].trim();
	}

	@Step("Get the address (first line) of the default billing address")
	public String getDefaultBillingAddressAddressFirstLine() {
		waitForElementVisible(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_BILLING_ADDRESS);
		String fullAddress = getElementText(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_BILLING_ADDRESS);
		return fullAddress.split("\n")[2].trim();
	}

	@Step("Get the address (second line) of the default billing address")
	public String getDefaultBillingAddressAddressSecondLine() {
		waitForElementVisible(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_BILLING_ADDRESS);
		String fullAddress = getElementText(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_BILLING_ADDRESS);
		return fullAddress.split("\n")[3].trim();
	}

	@Step("Get the address (third line) of the default billing address")
	public String getDefaultBillingAddressAddressThirdLine() {
		waitForElementVisible(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_BILLING_ADDRESS);
		String fullAddress = getElementText(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_BILLING_ADDRESS);
		return fullAddress.split("\n")[4].trim();
	}

	@Step("Get the city of the default billing address")
	public String getDefaultBillingAddressCity() {
		waitForElementVisible(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_BILLING_ADDRESS);
		String fullAddress = getElementText(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_BILLING_ADDRESS);
		return fullAddress.split("\n")[5].split(",")[0].trim();
	}

	@Step("Get the state of the default billing address")
	public String getDefaultBillingAddressState() {
		waitForElementVisible(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_BILLING_ADDRESS);
		String fullAddress = getElementText(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_BILLING_ADDRESS);
		return fullAddress.split("\n")[5].split(",")[1].trim();
	}

	@Step("Get the zip or postal code of the default billing address")
	public String getDefaultBillingAddressZipPostalCode() {
		waitForElementVisible(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_BILLING_ADDRESS);
		String fullAddress = getElementText(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_BILLING_ADDRESS);
		return fullAddress.split("\n")[5].split(",")[2].trim();
	}

	@Step("Get the country of the default billing address")
	public String getDefaultBillingAddressCountry() {
		waitForElementVisible(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_BILLING_ADDRESS);
		String fullAddress = getElementText(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_BILLING_ADDRESS);
		return fullAddress.split("\n")[6].trim();
	}

	@Step("Get the phone number of the default billing address")
	public String getDefaultBillingAddressPhone() {
		waitForElementVisible(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_BILLING_ADDRESS_PHONE);
		return getElementText(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_BILLING_ADDRESS_PHONE);
	}

	@Step("Get the full name of the default shipping address")
	public String getDefaultShippingAddressFullName() {
		waitForElementVisible(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_SHIPPING_ADDRESS);
		String fullAddress = getElementText(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_SHIPPING_ADDRESS);
		return fullAddress.split("\n")[0].trim();
	}

	@Step("Get the company of the default shipping address")
	public String getDefaultShippingAddressCompany() {
		waitForElementVisible(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_SHIPPING_ADDRESS);
		String fullAddress = getElementText(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_SHIPPING_ADDRESS);
		return fullAddress.split("\n")[1].trim();
	}

	@Step("Get the address (first line) of the default shipping address")
	public String getDefaultShippingAddressAddressFirstLine() {
		waitForElementVisible(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_SHIPPING_ADDRESS);
		String fullAddress = getElementText(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_SHIPPING_ADDRESS);
		return fullAddress.split("\n")[2].trim();
	}

	@Step("Get the address (second line) of the default shipping address")
	public String getDefaultShippingAddressAddressSecondLine() {
		waitForElementVisible(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_SHIPPING_ADDRESS);
		String fullAddress = getElementText(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_SHIPPING_ADDRESS);
		return fullAddress.split("\n")[3].trim();
	}

	@Step("Get the address (third line) of the default shipping address")
	public String getDefaultShippingAddressAddressThirdLine() {
		waitForElementVisible(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_SHIPPING_ADDRESS);
		String fullAddress = getElementText(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_SHIPPING_ADDRESS);
		return fullAddress.split("\n")[4].trim();
	}

	@Step("Get the city of the default shipping address")
	public String getDefaultShippingAddressCity() {
		waitForElementVisible(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_SHIPPING_ADDRESS);
		String fullAddress = getElementText(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_SHIPPING_ADDRESS);
		return fullAddress.split("\n")[5].split(",")[0].trim();
	}

	@Step("Get the state or province of the default shipping address")
	public String getDefaultShippingAddressState() {
		waitForElementVisible(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_SHIPPING_ADDRESS);
		String fullAddress = getElementText(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_SHIPPING_ADDRESS);
		return fullAddress.split("\n")[5].split(",")[1].trim();
	}

	@Step("Get the zip or postal code of the default shipping address")
	public String getDefaultShippingAddressZipPostalCode() {
		waitForElementVisible(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_SHIPPING_ADDRESS);
		String fullAddress = getElementText(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_SHIPPING_ADDRESS);
		return fullAddress.split("\n")[5].split(",")[2].trim();
	}

	@Step("Get the country of the default shipping address")
	public String getDefaultShippingAddressCountry() {
		waitForElementVisible(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_SHIPPING_ADDRESS);
		String fullAddress = getElementText(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_SHIPPING_ADDRESS);
		return fullAddress.split("\n")[6].trim();
	}

	@Step("Get the phone number of the default shipping address")
	public String getDefaultShippingAddressPhone() {
		waitForElementVisible(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_SHIPPING_ADDRESS_PHONE);
		return getElementText(driver, AddressBookPageUI.DefaultAddresses.DEFAULT_SHIPPING_ADDRESS_PHONE);
	}

	@Step("Get the '{columnName}' value of the additional address in row {rowIndex}")
	public String getAdditionalAddressValueByRowIndexAndColumnName(String rowIndex, String columnName) {
		waitForElementVisible(driver,
				AddressBookPageUI.AdditionalAddressEntries.DYNAMIC_ADDITIONAL_ADDRESS_VALUE_BY_ROW_INDEX_AND_COLUMN_NAME,
				rowIndex, columnName);
		return getElementText(driver,
				AddressBookPageUI.AdditionalAddressEntries.DYNAMIC_ADDITIONAL_ADDRESS_VALUE_BY_ROW_INDEX_AND_COLUMN_NAME,
				rowIndex, columnName);
	}

	@Step("Click the 'Add New Address' button")
	public AddressPageObject clickAddNewAddressButton() {
		waitForElementClickable(driver, AddressBookPageUI.ADD_NEW_ADDRESS_BUTTON);
		clickElementByJS(driver, AddressBookPageUI.ADD_NEW_ADDRESS_BUTTON);
		return PageGeneratorManager.getAddressPage(driver);
	}

	@Step("Get the info message displayed when there are no other address entries")
	public String getNoOtherAddressEntriesInfoMessage() {
		waitForElementVisible(driver, AddressBookPageUI.AdditionalAddressEntries.NO_OTHER_ADDRESS_ENTRIES_INFO_MESSAGE);
		return getElementText(driver, AddressBookPageUI.AdditionalAddressEntries.NO_OTHER_ADDRESS_ENTRIES_INFO_MESSAGE);
	}

	@Step("Click the 'Edit' link for the address with street '{streetAddress}'")
	public AddressPageObject clickEditLinkByStreetAddress(String streetAddress) {
		waitForElementClickable(driver, AddressBookPageUI.AdditionalAddressEntries.DYNAMIC_EDIT_LINK_BY_STREET_ADDRESS,
				streetAddress);
		clickElementByJS(driver, AddressBookPageUI.AdditionalAddressEntries.DYNAMIC_EDIT_LINK_BY_STREET_ADDRESS,
				streetAddress);
		return PageGeneratorManager.getAddressPage(driver);
	}

	@Step("Click the 'Delete' link for the address with street '{streetAddress}'")
	public void clickDeleteLinkByStreetAddress(String streetAddress) {
		waitForElementClickable(driver,
				AddressBookPageUI.AdditionalAddressEntries.DYNAMIC_DELETE_LINK_BY_STREET_ADDRESS, streetAddress);
		clickElementByJS(driver, AddressBookPageUI.AdditionalAddressEntries.DYNAMIC_DELETE_LINK_BY_STREET_ADDRESS,
				streetAddress);
	}

	@Step("Verify that the additional address with street address '{streetAddress}' is not displayed")
	public boolean isAdditionalAddressByStreetAddressDisplayed(String streetAddress) {
		return isElementNotDisplayed(driver,
				AddressBookPageUI.AdditionalAddressEntries.DYNAMIC_DELETE_LINK_BY_STREET_ADDRESS, streetAddress);
	}

}
