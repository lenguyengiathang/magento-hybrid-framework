package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.BasePage;
import commons.PageGeneratorManager;
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

	public void clickCustomerNameDropdown() {
		waitForElementClickable(driver, MyAccountPageUI.CUSTOMER_NAME_DROPDOWN);
		clickElement(driver, MyAccountPageUI.CUSTOMER_NAME_DROPDOWN);
	}

	public HomepageObject clickMyAccountDropdownLink() {
		waitForElementClickable(driver, MyAccountPageUI.MY_ACCOUNT_DROPDOWN_LINK);
		clickElement(driver, MyAccountPageUI.MY_ACCOUNT_DROPDOWN_LINK);
		return PageGeneratorManager.getHomepage(driver);
	}

	public HomepageObject clickMyWishListDropdownLink() {
		waitForElementClickable(driver, MyAccountPageUI.MY_WISH_LIST_DROPDOWN_LINK);
		clickElement(driver, MyAccountPageUI.MY_WISH_LIST_DROPDOWN_LINK);
		return PageGeneratorManager.getHomepage(driver);
	}

	public HomepageObject clickSignOutDropdownLink() {
		waitForElementClickable(driver, MyAccountPageUI.SIGN_OUT_DROPDOWN_LINK);
		clickElement(driver, MyAccountPageUI.SIGN_OUT_DROPDOWN_LINK);
		sleepInSecond(5);
		return PageGeneratorManager.getHomepage(driver);
	}

	public String getUserFullName() {
		waitForElementVisible(driver, MyAccountPageUI.FULL_NAME_AND_EMAIL);
		String fullNameAndEmail = getElementText(driver, MyAccountPageUI.FULL_NAME_AND_EMAIL);
		return fullNameAndEmail.split("\n")[0];
	}

	public String getUserEmail() {
		waitForElementVisible(driver, MyAccountPageUI.FULL_NAME_AND_EMAIL);
		String fullNameAndEmail = getElementText(driver, MyAccountPageUI.FULL_NAME_AND_EMAIL);
		return fullNameAndEmail.split("\n")[1];
	}

	public void clickEditContactInformationLink() {

	}
}
