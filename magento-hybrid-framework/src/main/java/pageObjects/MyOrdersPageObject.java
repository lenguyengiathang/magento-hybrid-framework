package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.BasePage;
import pageUIs.MyOrdersPageUI;

public class MyOrdersPageObject extends BasePage {
	private WebDriver driver;

	public MyOrdersPageObject(WebDriver driver) {
		this.driver = driver;
	}

	public String getNoOrdersPlacedInfoMessage() {
		waitForElementVisible(driver, MyOrdersPageUI.NO_PLACED_ORDERS_INFO_MESSAGE);
		return getElementText(driver, MyOrdersPageUI.NO_PLACED_ORDERS_INFO_MESSAGE);
	}

	public boolean isOrderByOrderNumberDisplayed(String orderNumber) {
		return isElementDisplayed(driver, MyOrdersPageUI.DYNAMIC_ORDER_NUMBER, orderNumber);
	}
}
