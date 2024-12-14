package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.BasePage;

public class OrdersAndReturnsPageObject extends BasePage {
	private WebDriver driver;

	public OrdersAndReturnsPageObject(WebDriver driver) {
		this.driver = driver;
	}
}
