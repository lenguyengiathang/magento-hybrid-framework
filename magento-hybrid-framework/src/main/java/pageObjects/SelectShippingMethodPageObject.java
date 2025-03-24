package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.BasePage;
import commons.PageGeneratorManager;
import pageUIs.BasePageUI;
import pageUIs.SelectShippingMethodPageUI;

public class SelectShippingMethodPageObject extends BasePage {
	private WebDriver driver;

	public SelectShippingMethodPageObject(WebDriver driver) {
		this.driver = driver;
	}

	public AddressPageObject clickChangeLinkByProductName(String productName) {
		waitForElementClickable(driver, SelectShippingMethodPageUI.DYNAMIC_CHANGE_LINK_BY_PRODUCT_NAME, productName);
		clickElementByJS(driver, SelectShippingMethodPageUI.DYNAMIC_CHANGE_LINK_BY_PRODUCT_NAME, productName);
		return PageGeneratorManager.getAddressPage(driver);
	}

	public String getShippingAddressZipCodeByProductName(String productName) {
		waitForElementVisible(driver, SelectShippingMethodPageUI.DYNAMIC_FULL_ADDRESS_BY_PRODUCT_NAME, productName);
		String fullAddress = getElementText(driver, SelectShippingMethodPageUI.DYNAMIC_FULL_ADDRESS_BY_PRODUCT_NAME,
				productName);
		return fullAddress.split("\n")[5].split(",")[2].trim();
	}

	public void selectTableRateRadioButtonByStreetAddress(String streetAddress) {
		waitForElementClickable(driver, SelectShippingMethodPageUI.DYNAMIC_TABLE_RATE_RADIO_BUTTON_BY_STREET_ADDRESS,
				streetAddress);
		checkDefaultCheckboxRadioButton(driver,
				SelectShippingMethodPageUI.DYNAMIC_TABLE_RATE_RADIO_BUTTON_BY_STREET_ADDRESS, streetAddress);
	}

	public void selectFixedRadioButtonByStreetAddress(String streetAddress) {
		waitForElementClickable(driver, SelectShippingMethodPageUI.DYNAMIC_FIXED_RADIO_BUTTON_BY_STREET_ADDRESS,
				streetAddress);
		checkDefaultCheckboxRadioButton(driver, SelectShippingMethodPageUI.DYNAMIC_FIXED_RADIO_BUTTON_BY_STREET_ADDRESS,
				streetAddress);
	}

	public String getShippingMethodNotSelectedErrorMessage() {
		waitForElementVisible(driver, BasePageUI.MainContent.MESSAGE);
		return getElementText(driver, BasePageUI.MainContent.MESSAGE);
	}

	public ShipToMultipleAddressesPageObject clickEditItemsLinkByProductName(String productName) {
		waitForElementClickable(driver, SelectShippingMethodPageUI.DYNAMIC_EDIT_ITEMS_LINK_BY_PRODUCT_NAME,
				productName);
		clickElementByJS(driver, SelectShippingMethodPageUI.DYNAMIC_EDIT_ITEMS_LINK_BY_PRODUCT_NAME, productName);
		return PageGeneratorManager.getShipToMultipleAddressesPage(driver);
	}

	public BillingInformationPageObject clickContinueToBillingInformationButton() {
		waitForElementClickable(driver, SelectShippingMethodPageUI.CONTINUE_TO_BILLING_INFORMATION_BUTTON);
		clickElementByJS(driver, SelectShippingMethodPageUI.CONTINUE_TO_BILLING_INFORMATION_BUTTON);
		return PageGeneratorManager.getBillingInformationPage(driver);
	}

	public ShipToMultipleAddressesPageObject clickBackToSelectAddressesLink() {
		waitForElementClickable(driver, SelectShippingMethodPageUI.BACK_TO_SELECT_ADDRESSES_LINK);
		clickElementByJS(driver, SelectShippingMethodPageUI.BACK_TO_SELECT_ADDRESSES_LINK);
		return PageGeneratorManager.getShipToMultipleAddressesPage(driver);
	}

}
