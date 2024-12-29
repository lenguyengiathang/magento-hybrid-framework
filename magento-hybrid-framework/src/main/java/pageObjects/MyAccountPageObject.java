package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.BasePage;
import pageUIs.BasePageUI;
import pageUIs.MyAccountPageUI;

public class MyAccountPageObject extends BasePage {
	private WebDriver driver;

	public MyAccountPageObject(WebDriver driver) {
		this.driver = driver;
	}

	public String getRegisterSuccessfulMessage() {
		waitForElementVisible(driver, BasePageUI.MESSAGE);
		return getElementText(driver, BasePageUI.MESSAGE);
	}

	public String getAccountInformationSavedSuccessMessage() {
		waitForElementVisible(driver, BasePageUI.MESSAGE);
		return getElementText(driver, BasePageUI.MESSAGE);
	}

	public String getFullName() {
		waitForElementVisible(driver, MyAccountPageUI.FULL_NAME_AND_EMAIL);
		String fullNameAndEmail = getElementText(driver, MyAccountPageUI.FULL_NAME_AND_EMAIL);
		return fullNameAndEmail.split("\n")[0];
	}

	public String getEmail() {
		waitForElementVisible(driver, MyAccountPageUI.FULL_NAME_AND_EMAIL);
		String fullNameAndEmail = getElementText(driver, MyAccountPageUI.FULL_NAME_AND_EMAIL);
		return fullNameAndEmail.split("\n")[1];
	}

	public AccountInformationPageObject clickEditContactInformationLink() {
		waitForElementClickable(driver, MyAccountPageUI.EDIT_CONTACT_INFORMARION_LINK);
		clickElement(driver, MyAccountPageUI.EDIT_CONTACT_INFORMARION_LINK);
		return new AccountInformationPageObject(driver);
	}

}
