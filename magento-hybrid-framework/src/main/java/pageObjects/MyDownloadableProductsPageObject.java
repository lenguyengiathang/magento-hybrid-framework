package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.BasePage;

public class MyDownloadableProductsPageObject extends BasePage {
	private WebDriver driver;

	public MyDownloadableProductsPageObject(WebDriver driver) {
		this.driver = driver;
	}
}
