package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.BasePage;

public class ShippingAddressPageObject extends BasePage {
	private WebDriver driver;

	public ShippingAddressPageObject(WebDriver driver) {
		this.driver = driver;
	}
}
