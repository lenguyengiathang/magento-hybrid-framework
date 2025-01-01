package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.BasePage;
import pageUIs.MyWishListPageUI;

public class MyWishListPageObject extends BasePage {
	private WebDriver driver;

	public MyWishListPageObject(WebDriver driver) {
		this.driver = driver;
	}

	public String getEmptyWishListWarningMessage() {
		waitForElementVisible(driver, MyWishListPageUI.EMPTY_WISH_LIST_WARNING_MESSAGE);
		return getElementText(driver, MyWishListPageUI.EMPTY_WISH_LIST_WARNING_MESSAGE);
	}

	public void clickTrashcanIconByProductName(String productName) {
		waitForElementClickable(driver, MyWishListPageUI.DYNAMIC_TRASHCAN_ICON_BY_PRODUCT_NAME, productName);
		clickElementByJS(driver, MyWishListPageUI.DYNAMIC_TRASHCAN_ICON_BY_PRODUCT_NAME, productName);
	}
}
