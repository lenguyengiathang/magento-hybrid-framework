package commons;

import org.openqa.selenium.WebDriver;

import pageObjects.CreateAnAccountPageObject;
import pageObjects.HomepageObject;

public class PageGeneratorManager {

	public static HomepageObject getHomepage(WebDriver driver) {
		return new HomepageObject(driver);
	}

	public static CreateAnAccountPageObject getCreateAnAccountPage(WebDriver driver) {
		return new CreateAnAccountPageObject(driver);
	}
}
