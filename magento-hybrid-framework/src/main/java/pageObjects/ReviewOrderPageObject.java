package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.BasePage;
import commons.PageGeneratorManager;
import pageUIs.ReviewOrderPageUI;

public class ReviewOrderPageObject extends BasePage {
	private WebDriver driver;

	public ReviewOrderPageObject(WebDriver driver) {
		this.driver = driver;
	}

	public BillingInformationPageObject clickBackToBillingInformationLink() {
		waitForElementClickable(driver, ReviewOrderPageUI.BACK_TO_BILLING_INFORMATION_LINK);
		clickElementByJS(driver, ReviewOrderPageUI.BACK_TO_BILLING_INFORMATION_LINK);
		return PageGeneratorManager.getBillingInformationPage(driver);
	}

	public AddressPageObject clickChangeBillingAddressLink() {
		waitForElementClickable(driver, ReviewOrderPageUI.CHANGE_BILLING_ADDRESS_LINK);
		clickElementByJS(driver, ReviewOrderPageUI.CHANGE_BILLING_ADDRESS_LINK);
		return PageGeneratorManager.getAddressPage(driver);
	}

	public BillingInformationPageObject clickChangePaymentMethodLink() {
		waitForElementClickable(driver, ReviewOrderPageUI.CHANGE_PAYMENT_METHOD_LINK);
		clickElementByJS(driver, ReviewOrderPageUI.CHANGE_PAYMENT_METHOD_LINK);
		return PageGeneratorManager.getBillingInformationPage(driver);
	}

	public AddressPageObject clickChangeShippingAddressLinkByProductName(String productName) {
		waitForElementClickable(driver, ReviewOrderPageUI.DYNAMIC_CHANGE_SHIPPING_ADDRESS_LINK_BY_PRODUCT_NAME,
				productName);
		clickElementByJS(driver, ReviewOrderPageUI.DYNAMIC_CHANGE_SHIPPING_ADDRESS_LINK_BY_PRODUCT_NAME, productName);
		return PageGeneratorManager.getAddressPage(driver);
	}

	public SelectShippingMethodPageObject clickChangeShippingMethodLinkByProductName(String productName) {
		waitForElementClickable(driver, ReviewOrderPageUI.DYNAMIC_CHANGE_SHIPPING_METHOD_LINK_BY_PRODUCT_NAME,
				productName);
		clickElementByJS(driver, ReviewOrderPageUI.DYNAMIC_CHANGE_SHIPPING_METHOD_LINK_BY_PRODUCT_NAME, productName);
		return PageGeneratorManager.getSelectShippingMethodPage(driver);
	}

	public OrderSuccessPageObject clickPlaceOrderButton() {
		waitForElementClickable(driver, ReviewOrderPageUI.PLACE_ORDER_BUTTON);
		clickElementByJS(driver, ReviewOrderPageUI.PLACE_ORDER_BUTTON);
		return PageGeneratorManager.getOrderSuccessPage(driver);
	}

}
