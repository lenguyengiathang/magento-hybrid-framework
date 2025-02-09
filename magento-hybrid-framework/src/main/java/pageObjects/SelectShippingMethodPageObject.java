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

	public void selectTableRateRadioButtonByProductName(String productName) {
		waitForElementClickable(driver, SelectShippingMethodPageUI.DYNAMIC_TABLE_RATE_RADIO_BUTTON_BY_PRODUCT_NAME,
				productName);
		checkDefaultCheckboxRadioButton(driver,
				SelectShippingMethodPageUI.DYNAMIC_TABLE_RATE_RADIO_BUTTON_BY_PRODUCT_NAME, productName);
	}

	public void selectFixedRadioButtonByProductName(String productName) {
		waitForElementClickable(driver, SelectShippingMethodPageUI.DYNAMIC_FIXED_RADIO_BUTTON_BY_PRODUCT_NAME,
				productName);
		checkDefaultCheckboxRadioButton(driver, SelectShippingMethodPageUI.DYNAMIC_FIXED_RADIO_BUTTON_BY_PRODUCT_NAME,
				productName);
	}

	public String getShippingMethodNotSelectedErrorMessage() {
		waitForElementVisible(driver, BasePageUI.MainContent.MESSAGE);
		return getElementText(driver, BasePageUI.MainContent.MESSAGE);
	}

	public ShipToMultipleAddressesPageObject clickEditItemsLink(String productName) {
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

}
