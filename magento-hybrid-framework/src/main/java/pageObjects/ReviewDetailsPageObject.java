package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.BasePage;

public class ReviewDetailsPageObject extends BasePage {
	private WebDriver driver;

	public ReviewDetailsPageObject(WebDriver driver) {
		this.driver = driver;
	}
}
