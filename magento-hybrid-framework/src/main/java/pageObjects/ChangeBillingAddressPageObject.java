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

}
