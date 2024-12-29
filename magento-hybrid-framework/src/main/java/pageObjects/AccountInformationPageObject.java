package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.BasePage;
import pageUIs.AccountInformationPageUI;

public class AccountInformationPageObject extends BasePage {
	private WebDriver driver;

	public AccountInformationPageObject(WebDriver driver) {
		this.driver = driver;
	}

	public void sendKeysToFirstNameTextbox(String firstName) {
		waitForElementVisible(driver, AccountInformationPageUI.FIRST_NAME_TEXTBOX);
		sendKeysToElement(driver, AccountInformationPageUI.FIRST_NAME_TEXTBOX, firstName);
	}

	public void sendKeysToLastNameTextbox(String lastName) {
		waitForElementVisible(driver, AccountInformationPageUI.LAST_NAME_TEXTBOX);
		sendKeysToElement(driver, AccountInformationPageUI.LAST_NAME_TEXTBOX, lastName);
	}

	public void sendKeysToEmailTextbox(String currentPassword) {
		waitForElementVisible(driver, AccountInformationPageUI.EMAIL_TEXTBOX);
		sendKeysToElement(driver, AccountInformationPageUI.EMAIL_TEXTBOX, currentPassword);
	}

	public void sendKeysToCurrentPasswordTextbox(String currentPassword) {
		waitForElementVisible(driver, AccountInformationPageUI.CURRENT_PASSWORD_TEXTBOX);
		sendKeysToElement(driver, AccountInformationPageUI.CURRENT_PASSWORD_TEXTBOX, currentPassword);
	}

	public void sendKeysToNewPasswordTextbox(String newPassword) {
		waitForElementVisible(driver, AccountInformationPageUI.NEW_PASSWORD_TEXTBOX);
		sendKeysToElement(driver, AccountInformationPageUI.NEW_PASSWORD_TEXTBOX, newPassword);
	}

	public void sendKeysToConfirmNewPasswordTextbox(String confirmationPassword) {
		waitForElementVisible(driver, AccountInformationPageUI.CONFIRM_NEW_PASSWORD_TEXTBOX);
		sendKeysToElement(driver, AccountInformationPageUI.CONFIRM_NEW_PASSWORD_TEXTBOX, confirmationPassword);
	}

	public void checkChangeEmailCheckbox() {
		waitForElementClickable(driver, AccountInformationPageUI.CHANGE_EMAIL_CHECKBOX);
		checkDefaultCheckboxRadioButton(driver, AccountInformationPageUI.CHANGE_EMAIL_CHECKBOX);
	}

	public void uncheckChangeEmailCheckbox() {
		waitForElementClickable(driver, AccountInformationPageUI.CHANGE_EMAIL_CHECKBOX);
		uncheckDefaultCheckbox(driver, AccountInformationPageUI.CHANGE_EMAIL_CHECKBOX);
	}

	public void checkChangePasswordCheckbox() {
		waitForElementClickable(driver, AccountInformationPageUI.CHANGE_PASSWORD_CHECKBOX);
		checkDefaultCheckboxRadioButton(driver, AccountInformationPageUI.CHANGE_PASSWORD_CHECKBOX);
	}

	public void uncheckChangePasswordCheckbox() {
		waitForElementClickable(driver, AccountInformationPageUI.CHANGE_PASSWORD_CHECKBOX);
		uncheckDefaultCheckbox(driver, AccountInformationPageUI.CHANGE_PASSWORD_CHECKBOX);
	}

	public String getChangeEmailPasswordSectionHeader() {
		waitForElementVisible(driver, AccountInformationPageUI.CHANGE_EMAIL_PASSWORD_SECTION_HEADER);
		return getElementText(driver, AccountInformationPageUI.CHANGE_EMAIL_PASSWORD_SECTION_HEADER);
	}

	public MyAccountPageObject clickSaveButton() {
		waitForElementClickable(driver, AccountInformationPageUI.SAVE_BUTTON);
		clickElement(driver, AccountInformationPageUI.SAVE_BUTTON);
		return new MyAccountPageObject(driver);
	}

}
