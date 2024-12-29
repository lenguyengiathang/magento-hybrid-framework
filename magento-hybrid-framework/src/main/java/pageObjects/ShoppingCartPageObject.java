package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.BasePage;

public class ShoppingCartPageObject extends BasePage {
	private WebDriver driver;

	public ShoppingCartPageObject(WebDriver driver) {
		this.driver = driver;
	}
}
