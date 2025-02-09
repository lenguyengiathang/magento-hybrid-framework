package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.BasePage;

public class ReviewOrderPageObject extends BasePage {
	private WebDriver driver;

	public ReviewOrderPageObject(WebDriver driver) {
		this.driver = driver;
	}
}
