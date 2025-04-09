package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.BasePage;
import commons.PageGeneratorManager;
import io.qameta.allure.Step;
import pageUIs.BasePageUI;
import pageUIs.CustomerLoginPageUI;

public class CustomerLoginPageObject extends BasePage {

	private WebDriver driver;

	public CustomerLoginPageObject(WebDriver driver) {
		this.driver = driver;
	}

	@Step("Fill in the 'Email' field with the value '{0}'")
	public void sendKeysToEmailTextbox(String email) {
		waitForElementVisible(driver, CustomerLoginPageUI.EMAIL_TEXTBOX);
		sendKeysToElement(driver, CustomerLoginPageUI.EMAIL_TEXTBOX, email);
	}

	@Step("Fill in the 'Password' field with the value '{0}'")
	public void sendKeysToPasswordTextbox(String password) {
		waitForElementVisible(driver, CustomerLoginPageUI.PASSWORD_TEXTBOX);
		sendKeysToElement(driver, CustomerLoginPageUI.PASSWORD_TEXTBOX, password);
	}

	@Step("Click the 'Sign In' button")
	public MyAccountPageObject clickSignInButton() {
		waitForElementClickable(driver, CustomerLoginPageUI.SIGN_IN_BUTTON);
		clickElement(driver, CustomerLoginPageUI.SIGN_IN_BUTTON);
		return PageGeneratorManager.getMyAccountPage(driver);
	}

	@Step("Fill in the 'Email' and 'Password' fields with the values '{0}' and '{1}', then click the 'Sign In' button")
	public HomepageObject logInAsRegisteredUser(String email, String password) {
		sendKeysToEmailTextbox(email);
		sendKeysToPasswordTextbox(password);
		clickSignInButton();
		return PageGeneratorManager.getHomepage(driver);
	}

	@Step("Click the 'Create New Account' button")
	public CreateNewCustomerAccountPageObject clickCreateNewAccountButton() {
		waitForElementClickable(driver, CustomerLoginPageUI.CREATE_AN_ACCOUNT_BUTTON);
		clickElement(driver, CustomerLoginPageUI.CREATE_AN_ACCOUNT_BUTTON);
		return PageGeneratorManager.getCreateNewCustomerAccountPage(driver);
	}

	@Step("Get login error message")
	public String getLoginErrorMessage() {
		waitForElementVisible(driver, BasePageUI.MainContent.MESSAGE);
		return getElementText(driver, BasePageUI.MainContent.MESSAGE);
	}

	@Step("Click the 'Forgot Your Password' link")
	public ForgotYourPasswordPageObject clickForgotYourPasswordLink() {
		waitForElementClickable(driver, CustomerLoginPageUI.FORGOT_YOUR_PASSWORD_LINK);
		clickElement(driver, CustomerLoginPageUI.FORGOT_YOUR_PASSWORD_LINK);
		return PageGeneratorManager.getForgotYourPasswordPage(driver);
	}

}
