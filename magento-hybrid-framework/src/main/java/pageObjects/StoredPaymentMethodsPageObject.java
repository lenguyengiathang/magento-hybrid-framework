package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.BasePage;

public class StoredPaymentMethodsPageObject extends BasePage {
	private WebDriver driver;

	public StoredPaymentMethodsPageObject(WebDriver driver) {
		this.driver = driver;
	}
}
