package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.BasePage;
import commons.PageGeneratorManager;
import pageUIs.BasePageUI;
import pageUIs.CreateNewCustomerAccountPageUI;

public class CreateNewCustomerAccountPageObject extends BasePage {
	private WebDriver driver;

	public CreateNewCustomerAccountPageObject(WebDriver driver) {
		this.driver = driver;
	}

	public void sendKeysToFirstNameTextbox(String firstName) {
		waitForElementVisible(driver, CreateNewCustomerAccountPageUI.FIRST_NAME_TEXTBOX);
		sendKeysToElement(driver, CreateNewCustomerAccountPageUI.FIRST_NAME_TEXTBOX, firstName);
	}

	public void sendKeysToLastNameTextbox(String lastName) {
		waitForElementVisible(driver, CreateNewCustomerAccountPageUI.LAST_NAME_TEXTBOX);
		sendKeysToElement(driver, CreateNewCustomerAccountPageUI.LAST_NAME_TEXTBOX, lastName);
	}

	public void sendKeysToEmailTextbox(String email) {
		waitForElementVisible(driver, CreateNewCustomerAccountPageUI.EMAIL_TEXTBOX);
		sendKeysToElement(driver, CreateNewCustomerAccountPageUI.EMAIL_TEXTBOX, email);
	}

	public void sendKeysToPasswordTextbox(String password) {
		waitForElementVisible(driver, CreateNewCustomerAccountPageUI.PASSWORD_TEXTBOX);
		sendKeysToElement(driver, CreateNewCustomerAccountPageUI.PASSWORD_TEXTBOX, password);
	}

	public String getPasswordStrengthMessage() {
		waitForElementVisible(driver, CreateNewCustomerAccountPageUI.PASSWORD_STRENGTH_MESSAGE);
		return getElementText(driver, CreateNewCustomerAccountPageUI.PASSWORD_STRENGTH_MESSAGE);
	}

	public void sendKeysToConfirmPasswordTextbox(String confirmationPassword) {
		waitForElementVisible(driver, CreateNewCustomerAccountPageUI.CONFIRM_PASSWORD_TEXTBOX);
		sendKeysToElement(driver, CreateNewCustomerAccountPageUI.CONFIRM_PASSWORD_TEXTBOX, confirmationPassword);
	}

	public MyAccountPageObject clickCreateAnAccountButton() {
		waitForElementClickable(driver, CreateNewCustomerAccountPageUI.CREATE_AN_ACCOUNT_BUTTON);
		clickElement(driver, CreateNewCustomerAccountPageUI.CREATE_AN_ACCOUNT_BUTTON);
		return PageGeneratorManager.getMyAccountPage(driver);
	}

	public String getFirstNameErrorMessage() {
		waitForElementVisible(driver, CreateNewCustomerAccountPageUI.FIRST_NAME_ERROR_MESSAGE);
		return getElementText(driver, CreateNewCustomerAccountPageUI.FIRST_NAME_ERROR_MESSAGE);
	}

	public String getLastNameErrorMessage() {
		waitForElementVisible(driver, CreateNewCustomerAccountPageUI.LAST_NAME_ERROR_MESSAGE);
		return getElementText(driver, CreateNewCustomerAccountPageUI.LAST_NAME_ERROR_MESSAGE);
	}

	public String getEmailErrorMessage() {
		waitForElementVisible(driver, CreateNewCustomerAccountPageUI.EMAIL_ERROR_MESSAGE);
		return getElementText(driver, CreateNewCustomerAccountPageUI.EMAIL_ERROR_MESSAGE);
	}

	public String getPasswordErrorMessage() {
		waitForElementVisible(driver, CreateNewCustomerAccountPageUI.PASSWORD_ERROR_MESSAGE);
		return getElementText(driver, CreateNewCustomerAccountPageUI.PASSWORD_ERROR_MESSAGE);
	}

	public String getConfirmPasswordErrorMessage() {
		waitForElementVisible(driver, CreateNewCustomerAccountPageUI.CONFIRM_PASSWORD_ERROR_MESSAGE);
		return getElementText(driver, CreateNewCustomerAccountPageUI.CONFIRM_PASSWORD_ERROR_MESSAGE);
	}

	public String getExistingEmailErrorMessage() {
		waitForElementVisible(driver, BasePageUI.MESSAGE);
		return getElementText(driver, BasePageUI.MESSAGE);
	}
}
