package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.BasePage;
import commons.PageGeneratorManager;
import io.qameta.allure.Step;
import pageUIs.BasePageUI;
import pageUIs.CreateNewCustomerAccountPageUI;

public class CreateNewCustomerAccountPageObject extends BasePage {
	private WebDriver driver;

	public CreateNewCustomerAccountPageObject(WebDriver driver) {
		this.driver = driver;
	}

	@Step("Fill in the 'First Name' field with the value '{0}'")
	public void sendKeysToFirstNameTextbox(String firstName) {
		waitForElementVisible(driver, CreateNewCustomerAccountPageUI.FIRST_NAME_TEXTBOX);
		sendKeysToElement(driver, CreateNewCustomerAccountPageUI.FIRST_NAME_TEXTBOX, firstName);
	}

	@Step("Fill in the 'Last Name' field with the value '{0}'")
	public void sendKeysToLastNameTextbox(String lastName) {
		waitForElementVisible(driver, CreateNewCustomerAccountPageUI.LAST_NAME_TEXTBOX);
		sendKeysToElement(driver, CreateNewCustomerAccountPageUI.LAST_NAME_TEXTBOX, lastName);
	}

	@Step("Fill in the 'Email' field with the value '{0}'")
	public void sendKeysToEmailTextbox(String email) {
		waitForElementVisible(driver, CreateNewCustomerAccountPageUI.EMAIL_TEXTBOX);
		sendKeysToElement(driver, CreateNewCustomerAccountPageUI.EMAIL_TEXTBOX, email);
	}

	@Step("Fill in the 'Password' field with the value '{0}'")
	public void sendKeysToPasswordTextbox(String password) {
		waitForElementVisible(driver, CreateNewCustomerAccountPageUI.PASSWORD_TEXTBOX);
		sendKeysToElement(driver, CreateNewCustomerAccountPageUI.PASSWORD_TEXTBOX, password);
	}

	@Step("Fill in the 'First Name', 'Last Name', 'Email', 'Password', and 'Confirm Password' fields with the values '{0}', '{1}', '{2}', '{3}', and '{4}'")
	public void completeRegistrationForm(String firstName, String lastName, String email, String password) {
		sendKeysToFirstNameTextbox(firstName);
		sendKeysToLastNameTextbox(lastName);
		sendKeysToEmailTextbox(email);
		sendKeysToPasswordTextbox(password);
		sendKeysToConfirmPasswordTextbox(password);
	}

	@Step("Get password strength message")
	public String getPasswordStrengthMessage() {
		waitForElementVisible(driver, CreateNewCustomerAccountPageUI.PASSWORD_STRENGTH_MESSAGE);
		return getElementText(driver, CreateNewCustomerAccountPageUI.PASSWORD_STRENGTH_MESSAGE);
	}

	@Step("Fill in the 'Confirm Password' field with the value '{0}'")
	public void sendKeysToConfirmPasswordTextbox(String confirmationPassword) {
		waitForElementVisible(driver, CreateNewCustomerAccountPageUI.CONFIRM_PASSWORD_TEXTBOX);
		sendKeysToElement(driver, CreateNewCustomerAccountPageUI.CONFIRM_PASSWORD_TEXTBOX, confirmationPassword);
	}

	@Step("Click the 'Create an Account' button")
	public MyAccountPageObject clickCreateAnAccountButton() {
		waitForElementClickable(driver, CreateNewCustomerAccountPageUI.CREATE_AN_ACCOUNT_BUTTON);
		clickElement(driver, CreateNewCustomerAccountPageUI.CREATE_AN_ACCOUNT_BUTTON);
		return PageGeneratorManager.getMyAccountPage(driver);
	}

	@Step("Get first name error message")
	public String getFirstNameErrorMessage() {
		waitForElementVisible(driver, CreateNewCustomerAccountPageUI.FIRST_NAME_ERROR_MESSAGE);
		return getElementText(driver, CreateNewCustomerAccountPageUI.FIRST_NAME_ERROR_MESSAGE);
	}

	@Step("Get last name error message")
	public String getLastNameErrorMessage() {
		waitForElementVisible(driver, CreateNewCustomerAccountPageUI.LAST_NAME_ERROR_MESSAGE);
		return getElementText(driver, CreateNewCustomerAccountPageUI.LAST_NAME_ERROR_MESSAGE);
	}

	@Step("Get email error message")
	public String getEmailErrorMessage() {
		waitForElementVisible(driver, CreateNewCustomerAccountPageUI.EMAIL_ERROR_MESSAGE);
		return getElementText(driver, CreateNewCustomerAccountPageUI.EMAIL_ERROR_MESSAGE);
	}

	@Step("Get password error message")
	public String getPasswordErrorMessage() {
		waitForElementVisible(driver, CreateNewCustomerAccountPageUI.PASSWORD_ERROR_MESSAGE);
		return getElementText(driver, CreateNewCustomerAccountPageUI.PASSWORD_ERROR_MESSAGE);
	}

	@Step("Get confirm password error message")
	public String getConfirmPasswordErrorMessage() {
		waitForElementVisible(driver, CreateNewCustomerAccountPageUI.CONFIRM_PASSWORD_ERROR_MESSAGE);
		return getElementText(driver, CreateNewCustomerAccountPageUI.CONFIRM_PASSWORD_ERROR_MESSAGE);
	}

	@Step("Get existing email error message")
	public String getExistingEmailErrorMessage() {
		waitForElementVisible(driver, BasePageUI.MainContent.MESSAGE);
		return getElementText(driver, BasePageUI.MainContent.MESSAGE);
	}

	@Step("Get input value of the '{label}' field")
	public String getInputValueByTextboxLabel(String label) {
		waitForElementVisible(driver, CreateNewCustomerAccountPageUI.DYNAMIC_TEXTBOX_BY_LABEL, label);
		return getElementValueByJS(driver, CreateNewCustomerAccountPageUI.DYNAMIC_TEXTBOX_BY_LABEL, label);
	}
}
