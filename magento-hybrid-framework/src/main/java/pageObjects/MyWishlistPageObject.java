package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.BasePage;

public class MyWishlistPageObject extends BasePage {
	private WebDriver driver;

	public MyWishlistPageObject(WebDriver driver) {
		this.driver = driver;
	}
}
