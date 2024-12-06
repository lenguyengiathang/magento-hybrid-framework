package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.BasePage;
import pageUIs.CreateAnAccountPageUI;

public class CreateAnAccountPageObject extends BasePage {
	private WebDriver driver;

	public CreateAnAccountPageObject(WebDriver driver) {
		this.driver = driver;
	}

	public void sendKeysToFirstNameTextbox(String firstName) {
		waitForElementVisible(driver, CreateAnAccountPageUI.FIRST_NAME_TEXTBOX);
		sendKeysToElement(driver, CreateAnAccountPageUI.FIRST_NAME_TEXTBOX, firstName);
	}

	public void sendKeysToLastNameTextbox(String lastName) {
		waitForElementVisible(driver, CreateAnAccountPageUI.LAST_NAME_TEXTBOX);
		sendKeysToElement(driver, CreateAnAccountPageUI.LAST_NAME_TEXTBOX, lastName);
	}

	public void sendKeysToEmailTextbox(String email) {
		waitForElementVisible(driver, CreateAnAccountPageUI.EMAIL_TEXTBOX);
		sendKeysToElement(driver, CreateAnAccountPageUI.EMAIL_TEXTBOX, email);
	}

	public void sendKeysToPasswordTextbox(String password) {
		waitForElementVisible(driver, CreateAnAccountPageUI.PASSWORD_TEXTBOX);
		sendKeysToElement(driver, CreateAnAccountPageUI.PASSWORD_TEXTBOX, password);
	}

	public String getPasswordStrengthMessage() {
		waitForElementVisible(driver, CreateAnAccountPageUI.PASSWORD_STRENGTH_MESSAGE);
		return getElementText(driver, CreateAnAccountPageUI.PASSWORD_STRENGTH_MESSAGE);
	}

	public void sendKeysToConfirmPasswordTextbox(String confirmationPassword) {
		waitForElementVisible(driver, CreateAnAccountPageUI.CONFIRM_PASSWORD_TEXTBOX);
		sendKeysToElement(driver, CreateAnAccountPageUI.CONFIRM_PASSWORD_TEXTBOX, confirmationPassword);
	}

	public void clickCreateAnAccountButton() {
		waitForElementClickable(driver, CreateAnAccountPageUI.CREATE_AN_ACCOUNT_BUTTON);
		clickElement(driver, CreateAnAccountPageUI.CREATE_AN_ACCOUNT_BUTTON);
	}

	public String getFirstNameErrorMessage() {
		waitForElementVisible(driver, CreateAnAccountPageUI.FIRST_NAME_ERROR_MESSAGE);
		return getElementText(driver, CreateAnAccountPageUI.FIRST_NAME_ERROR_MESSAGE);
	}

	public String getLastNameErrorMessage() {
		waitForElementVisible(driver, CreateAnAccountPageUI.LAST_NAME_ERROR_MESSAGE);
		return getElementText(driver, CreateAnAccountPageUI.LAST_NAME_ERROR_MESSAGE);
	}

	public String getEmailErrorMessage() {
		waitForElementVisible(driver, CreateAnAccountPageUI.EMAIL_ERROR_MESSAGE);
		return getElementText(driver, CreateAnAccountPageUI.EMAIL_ERROR_MESSAGE);
	}

	public String getPasswordErrorMessage() {
		waitForElementVisible(driver, CreateAnAccountPageUI.PASSWORD_ERROR_MESSAGE);
		return getElementText(driver, CreateAnAccountPageUI.PASSWORD_ERROR_MESSAGE);
	}

	public String getConfirmPasswordErrorMessage() {
		waitForElementVisible(driver, CreateAnAccountPageUI.CONFIRM_PASSWORD_ERROR_MESSAGE);
		return getElementText(driver, CreateAnAccountPageUI.CONFIRM_PASSWORD_ERROR_MESSAGE);
	}
}
