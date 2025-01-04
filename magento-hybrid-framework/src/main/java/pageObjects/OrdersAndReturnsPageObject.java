package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.BasePage;
import pageUIs.OrdersAndReturnsPageUI;

public class OrdersAndReturnsPageObject extends BasePage {
	private WebDriver driver;

	public OrdersAndReturnsPageObject(WebDriver driver) {
		this.driver = driver;
	}

	public void sendKeysToOrderIdTextbox(String orderId) {
		waitForElementVisible(driver, OrdersAndReturnsPageUI.ORDER_ID_TEXTBOX);
		sendKeysToElement(driver, OrdersAndReturnsPageUI.ORDER_ID_TEXTBOX, orderId);
	}

	public void sendKeysToBillingLastNameTextbox(String billingLastName) {
		waitForElementVisible(driver, OrdersAndReturnsPageUI.BILLING_LAST_NAME_TEXTBOX);
		sendKeysToElement(driver, OrdersAndReturnsPageUI.BILLING_LAST_NAME_TEXTBOX, billingLastName);
	}

	public void selectItemFindOrderByDropdown(String emailOrZipCode) {
		selectOptionDefaultDropdown(driver, OrdersAndReturnsPageUI.FIND_ORDER_BY_DROPDOWN, emailOrZipCode);
	}

	public void sendKeysToEmailOrZipCodeTextbox(String emailOrZipCode) {
		String selectedOption = getSelectedOptionDefaultDropdown(driver, OrdersAndReturnsPageUI.FIND_ORDER_BY_DROPDOWN);
		if (selectedOption.equals("Email")) {
			waitForElementVisible(driver, OrdersAndReturnsPageUI.EMAIL_TEXTBOX);
			sendKeysToElement(driver, OrdersAndReturnsPageUI.EMAIL_TEXTBOX, emailOrZipCode);
		} else {
			waitForElementVisible(driver, OrdersAndReturnsPageUI.BILLING_ZIP_CODE_TEXTBOX);
			sendKeysToElement(driver, OrdersAndReturnsPageUI.BILLING_ZIP_CODE_TEXTBOX, emailOrZipCode);
		}
	}
}
