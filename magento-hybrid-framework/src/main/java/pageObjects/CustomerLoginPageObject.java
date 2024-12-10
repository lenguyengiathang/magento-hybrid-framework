package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.BasePage;
import commons.PageGeneratorManager;
import pageUIs.CustomerLoginPageUI;

public class CustomerLoginPageObject extends BasePage {

	private WebDriver driver;

	public CustomerLoginPageObject(WebDriver driver) {
		this.driver = driver;
	}

	public CreateNewCustomerAccountPageObject clickCreateNewAccountButton() {
		waitForElementClickable(driver, CustomerLoginPageUI.CREATE_AN_ACCOUNT_BUTTON);
		clickElement(driver, CustomerLoginPageUI.CREATE_AN_ACCOUNT_BUTTON);
		return PageGeneratorManager.getCreateNewCustomerAccountPage(driver);
	}

}
