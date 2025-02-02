package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.BasePage;
import pageUIs.BasePageUI;

public class StoredPaymentMethodsPageObject extends BasePage {
	private WebDriver driver;

	public StoredPaymentMethodsPageObject(WebDriver driver) {
		this.driver = driver;
	}

	public String getNoStoredPaymentMethodsWarningMessage() {
		waitForElementVisible(driver, BasePageUI.MainContent.MESSAGE);
		return getElementText(driver, BasePageUI.MainContent.MESSAGE);
	}
}
