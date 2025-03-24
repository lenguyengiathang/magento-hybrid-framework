package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.BasePage;
import commons.PageGeneratorManager;
import pageUIs.OrderSuccessPageUI;

public class OrderSuccessPageObject extends BasePage {
	private WebDriver driver;

	public OrderSuccessPageObject(WebDriver driver) {
		this.driver = driver;
	}

	public HomepageObject clickContinueShoppingButton() {
		waitForElementClickable(driver, OrderSuccessPageUI.CONTINUE_SHOPPING_BUTTON);
		clickElementByJS(driver, OrderSuccessPageUI.CONTINUE_SHOPPING_BUTTON);
		return PageGeneratorManager.getHomepage(driver);
	}

	public OrderDetailsPageObject clickOrderNumber() {
		waitForElementClickable(driver, OrderSuccessPageUI.ORDER_NUMBER);
		clickElementByJS(driver, OrderSuccessPageUI.ORDER_NUMBER);
		return PageGeneratorManager.getOrderDetaisPage(driver);
	}

	public String getOrderNumber() {
		waitForElementVisible(driver, OrderSuccessPageUI.ORDER_NUMBER);
		return getElementText(driver, OrderSuccessPageUI.ORDER_NUMBER);
	}

	public OrderDetailsPageObject clickOrderNumberByShippingAddress(String shippingAddress) {
		waitForElementClickable(driver, OrderSuccessPageUI.DYNAMIC_ORDER_NUMBER_BY_SHIPPING_ADDRESS, shippingAddress);
		clickElementByJS(driver, OrderSuccessPageUI.DYNAMIC_ORDER_NUMBER_BY_SHIPPING_ADDRESS, shippingAddress);
		return PageGeneratorManager.getOrderDetaisPage(driver);
	}

	public CreateNewCustomerAccountPageObject clickCreateAnAccountButton() {
		waitForElementClickable(driver, OrderSuccessPageUI.CREATE_AN_ACCOUNT_BUTTON);
		clickElementByJS(driver, OrderSuccessPageUI.CREATE_AN_ACCOUNT_BUTTON);
		return PageGeneratorManager.getCreateNewCustomerAccountPage(driver);
	}
}
