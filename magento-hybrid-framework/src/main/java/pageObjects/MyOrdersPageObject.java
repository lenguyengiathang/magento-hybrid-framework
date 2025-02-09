package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.BasePage;
import pageUIs.BasePageUI;

public class MyOrdersPageObject extends BasePage {
	private WebDriver driver;

	public MyOrdersPageObject(WebDriver driver) {
		this.driver = driver;
	}

	public String getNoOrdersPlacedInfoMessage() {
		waitForElementVisible(driver, BasePageUI.MainContent.MESSAGE);
		return getElementText(driver, BasePageUI.MainContent.MESSAGE);
	}
}
