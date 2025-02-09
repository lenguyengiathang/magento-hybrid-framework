package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.BasePage;
import commons.PageGeneratorManager;
import pageUIs.BillingInformationPageUI;

public class BillingInformationPageObject extends BasePage {
	private WebDriver driver;

	public BillingInformationPageObject(WebDriver driver) {
		this.driver = driver;
	}

	public ChangeBillingAddressPageObject clickChangeBillingAddressLink() {
		waitForElementClickable(driver, BillingInformationPageUI.CHANGE_BILLING_ADDRESS_LINK);
		clickElementByJS(driver, BillingInformationPageUI.CHANGE_BILLING_ADDRESS_LINK);
		return PageGeneratorManager.getChangeBillingAddressPage(driver);
	}
}
