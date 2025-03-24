package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.BasePage;
import commons.PageGeneratorManager;
import pageUIs.ChangeBillingAddressPageUI;

public class ChangeBillingAddressPageObject extends BasePage {
	private WebDriver driver;

	public ChangeBillingAddressPageObject(WebDriver driver) {
		this.driver = driver;
	}

	public AddressPageObject clickAddNewAddressButton() {
		waitForElementClickable(driver, ChangeBillingAddressPageUI.ADD_NEW_ADDRESS_BUTTON);
		clickElementByJS(driver, ChangeBillingAddressPageUI.ADD_NEW_ADDRESS_BUTTON);
		return PageGeneratorManager.getAddressPageObject(driver);
	}

	public AddressPageObject clickEditAddressLinkByStreetAddress(String streetAddress) {
		waitForElementClickable(driver, ChangeBillingAddressPageUI.EDIT_ADDRESS_LINK_BY_STREET_ADDRESS, streetAddress);
		clickElementByJS(driver, ChangeBillingAddressPageUI.EDIT_ADDRESS_LINK_BY_STREET_ADDRESS, streetAddress);
		return PageGeneratorManager.getAddressPageObject(driver);
	}

	public BillingInformationPageObject clickSelectAddressLinkByStreetAddress(String streetAddress) {
		waitForElementClickable(driver, ChangeBillingAddressPageUI.SELECT_ADDRESS_LINK_BY_STREET_ADDRESS,
				streetAddress);
		clickElementByJS(driver, ChangeBillingAddressPageUI.SELECT_ADDRESS_LINK_BY_STREET_ADDRESS, streetAddress);
		return PageGeneratorManager.getBillingInformationPage(driver);
	}

	public BillingInformationPageObject clickBackToBillingInformationLink() {
		waitForElementClickable(driver, ChangeBillingAddressPageUI.BACK_TO_BILLING_INFORMATION_LINK);
		clickElementByJS(driver, ChangeBillingAddressPageUI.BACK_TO_BILLING_INFORMATION_LINK);
		return PageGeneratorManager.getBillingInformationPage(driver);
	}

	public boolean isBillingAddressByStreetAddressDisplayed(String streetAddress) {
		return isElementDisplayed(driver, ChangeBillingAddressPageUI.DYNAMIC_BILLING_ADDRESS_BY_STREET_ADDRESS,
				streetAddress);
	}

}
