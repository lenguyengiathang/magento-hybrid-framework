package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.BasePage;
import commons.PageGeneratorManager;
import pageUIs.HomepageUI;

public class HomepageObject extends BasePage {
	private WebDriver driver;

	public HomepageObject(WebDriver driver) {
		this.driver = driver;
	}

	public CreateAnAccountPageObject clickCreateAnAccountLink() {
		waitForElementClickable(driver, HomepageUI.CREATE_AN_ACCOUNT_LINK);
		clickElement(driver, HomepageUI.CREATE_AN_ACCOUNT_LINK);
		return PageGeneratorManager.getCreateAnAccountPage(driver);
	}

	public void clickSignInLink() {
		waitForElementClickable(driver, HomepageUI.SIGN_IN_LINK);
		clickElement(driver, HomepageUI.SIGN_IN_LINK);
	}
}
