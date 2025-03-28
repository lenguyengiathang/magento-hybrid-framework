package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.BasePage;
import commons.PageGeneratorManager;
import pageUIs.BasePageUI;
import pageUIs.CustomerLoginPageUI;

public class CustomerLoginPageObject extends BasePage {

	private WebDriver driver;

	public CustomerLoginPageObject(WebDriver driver) {
		this.driver = driver;
	}

	public void sendKeysToEmailTextbox(String email) {
		waitForElementVisible(driver, CustomerLoginPageUI.EMAIL_TEXTBOX);
		sendKeysToElement(driver, CustomerLoginPageUI.EMAIL_TEXTBOX, email);
	}

	public void sendKeysToPasswordTextbox(String password) {
		waitForElementVisible(driver, CustomerLoginPageUI.PASSWORD_TEXTBOX);
		sendKeysToElement(driver, CustomerLoginPageUI.PASSWORD_TEXTBOX, password);
	}

	public MyAccountPageObject clickSignInButton() {
		waitForElementClickable(driver, CustomerLoginPageUI.SIGN_IN_BUTTON);
		clickElement(driver, CustomerLoginPageUI.SIGN_IN_BUTTON);
		return PageGeneratorManager.getMyAccountPage(driver);
	}

	public HomepageObject logInAsRegisteredUser(String email, String password) {
		sendKeysToEmailTextbox(email);
		sendKeysToPasswordTextbox(password);
		clickSignInButton();
		return PageGeneratorManager.getHomepage(driver);
	}

	public CreateNewCustomerAccountPageObject clickCreateNewAccountButton() {
		waitForElementClickable(driver, CustomerLoginPageUI.CREATE_AN_ACCOUNT_BUTTON);
		clickElement(driver, CustomerLoginPageUI.CREATE_AN_ACCOUNT_BUTTON);
		return PageGeneratorManager.getCreateNewCustomerAccountPage(driver);
	}

	public String getLoginErrorMessage() {
		waitForElementVisible(driver, BasePageUI.MainContent.MESSAGE);
		return getElementText(driver, BasePageUI.MainContent.MESSAGE);
	}

	public ForgotYourPasswordPageObject clickForgotYourPasswordLink() {
		waitForElementClickable(driver, CustomerLoginPageUI.FORGOT_YOUR_PASSWORD_LINK);
		clickElement(driver, CustomerLoginPageUI.FORGOT_YOUR_PASSWORD_LINK);
		return PageGeneratorManager.getForgotYourPasswordPage(driver);
	}

}
