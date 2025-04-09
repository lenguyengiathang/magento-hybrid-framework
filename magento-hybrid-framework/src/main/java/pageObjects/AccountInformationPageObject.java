package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.BasePage;
import io.qameta.allure.Step;
import pageUIs.AccountInformationPageUI;

public class AccountInformationPageObject extends BasePage {
	private WebDriver driver;

	public AccountInformationPageObject(WebDriver driver) {
		this.driver = driver;
	}

	@Step("Fill in the 'First Name' field with value '{0}'")
	public void sendKeysToFirstNameTextbox(String firstName) {
		waitForElementVisible(driver, AccountInformationPageUI.FIRST_NAME_TEXTBOX);
		sendKeysToElement(driver, AccountInformationPageUI.FIRST_NAME_TEXTBOX, firstName);
	}

	@Step("Fill in the 'Last Name' field with value '{0}'")
	public void sendKeysToLastNameTextbox(String lastName) {
		waitForElementVisible(driver, AccountInformationPageUI.LAST_NAME_TEXTBOX);
		sendKeysToElement(driver, AccountInformationPageUI.LAST_NAME_TEXTBOX, lastName);
	}

	@Step("Fill in the 'Email' field with value '{0}'")
	public void sendKeysToEmailTextbox(String currentPassword) {
		waitForElementVisible(driver, AccountInformationPageUI.EMAIL_TEXTBOX);
		sendKeysToElement(driver, AccountInformationPageUI.EMAIL_TEXTBOX, currentPassword);
	}

	@Step("Fill in the 'Current Password' field with value '{0}'")
	public void sendKeysToCurrentPasswordTextbox(String currentPassword) {
		waitForElementVisible(driver, AccountInformationPageUI.CURRENT_PASSWORD_TEXTBOX);
		sendKeysToElement(driver, AccountInformationPageUI.CURRENT_PASSWORD_TEXTBOX, currentPassword);
	}

	@Step("Fill in the 'New Password' field with value '{0}'")
	public void sendKeysToNewPasswordTextbox(String newPassword) {
		waitForElementVisible(driver, AccountInformationPageUI.NEW_PASSWORD_TEXTBOX);
		sendKeysToElement(driver, AccountInformationPageUI.NEW_PASSWORD_TEXTBOX, newPassword);
	}

	@Step("Fill in the 'Confirm New Password' field with value '{0}'")
	public void sendKeysToConfirmNewPasswordTextbox(String confirmationPassword) {
		waitForElementVisible(driver, AccountInformationPageUI.CONFIRM_NEW_PASSWORD_TEXTBOX);
		sendKeysToElement(driver, AccountInformationPageUI.CONFIRM_NEW_PASSWORD_TEXTBOX, confirmationPassword);
	}

	@Step("Check the 'Change Email' checkbox")
	public void checkChangeEmailCheckbox() {
		waitForElementClickable(driver, AccountInformationPageUI.CHANGE_EMAIL_CHECKBOX);
		checkDefaultCheckboxRadioButton(driver, AccountInformationPageUI.CHANGE_EMAIL_CHECKBOX);
	}

	@Step("Uncheck the 'Change Email' checkbox")
	public void uncheckChangeEmailCheckbox() {
		waitForElementClickable(driver, AccountInformationPageUI.CHANGE_EMAIL_CHECKBOX);
		uncheckDefaultCheckbox(driver, AccountInformationPageUI.CHANGE_EMAIL_CHECKBOX);
	}

	@Step("Check the 'Change Password' checkbox")
	public void checkChangePasswordCheckbox() {
		waitForElementClickable(driver, AccountInformationPageUI.CHANGE_PASSWORD_CHECKBOX);
		checkDefaultCheckboxRadioButton(driver, AccountInformationPageUI.CHANGE_PASSWORD_CHECKBOX);
	}

	@Step("Uncheck the 'Change Password' checkbox")
	public void uncheckChangePasswordCheckbox() {
		waitForElementClickable(driver, AccountInformationPageUI.CHANGE_PASSWORD_CHECKBOX);
		uncheckDefaultCheckbox(driver, AccountInformationPageUI.CHANGE_PASSWORD_CHECKBOX);
	}

	@Step("Get 'Change Email' or 'Change Password' section header")
	public String getChangeEmailPasswordSectionHeader() {
		waitForElementVisible(driver, AccountInformationPageUI.CHANGE_EMAIL_PASSWORD_SECTION_HEADER);
		return getElementText(driver, AccountInformationPageUI.CHANGE_EMAIL_PASSWORD_SECTION_HEADER);
	}

	@Step("Click the 'Save' button")
	public BasePage clickSaveButton() {
		String currentPasswordValue = getElementAttribute(driver, AccountInformationPageUI.CURRENT_PASSWORD_TEXTBOX,
				"value");
		waitForElementClickable(driver, AccountInformationPageUI.SAVE_BUTTON);
		clickElementByJS(driver, AccountInformationPageUI.SAVE_BUTTON);
		if (currentPasswordValue.isEmpty()) {
			return new MyAccountPageObject(driver);
		}
		return new CustomerLoginPageObject(driver);
	}

}
