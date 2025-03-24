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

	public SelectShippingMethodPageObject clickBackToShippingInformationLink() {
		waitForElementClickable(driver, BillingInformationPageUI.BACK_TO_SHIPPING_INFORMATION_LINK);
		clickElementByJS(driver, BillingInformationPageUI.BACK_TO_SHIPPING_INFORMATION_LINK);
		return PageGeneratorManager.getSelectShippingMethodPage(driver);
	}

	public ReviewOrderPageObject clickGoToReviewYourOrderButton() {
		waitForElementClickable(driver, BillingInformationPageUI.GO_TO_REVIEW_YOUR_ORDER_BUTTON);
		clickElementByJS(driver, BillingInformationPageUI.GO_TO_REVIEW_YOUR_ORDER_BUTTON);
		return PageGeneratorManager.getReviewOrderPage(driver);
	}

	public boolean isBillingAddressByStreetAddressDisplayed(String address) {
		// TODO Auto-generated method stub
		return false;
	}
}
