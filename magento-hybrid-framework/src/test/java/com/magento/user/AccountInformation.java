package com.magento.user;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.magento.commons.Register;

import commons.BaseTest;
import commons.PageGeneratorManager;
import pageObjects.AccountInformationPageObject;
import pageObjects.CustomerLoginPageObject;
import pageObjects.HomepageObject;
import pageObjects.MyAccountPageObject;
import utilities.FakeDataUtils;

public class AccountInformation extends BaseTest {
	@Parameters("browser")
	@BeforeClass
	public void beforeClass(String browser) {
		driver = getBrowserDriver(browser);
		homepage = PageGeneratorManager.getHomepage(driver);

		data = FakeDataUtils.getDataHelper();
		email = Register.email;
		password = Register.password;
		newFirstName = data.getFirstName();
		newLastName = data.getLastName();
		newFullName = newFirstName + ' ' + newLastName;
		newEmail = data.getEmailAddress();
		newPassword = password + generateRandomNumber();

		customerLoginPage = homepage.clickSignInLink();
		homepage = customerLoginPage.logInAsRegisteredUser(email, password);
	}

	@Test(description = "Verify that user is directed to 'Account Information' page when clicking the 'Account Information' link in the left sidebar")
	public void Click_Account_Information_Link() {
		homepage.clickCustomerNameDropdown(driver);
		myAccountPage = homepage.clickMyAccountDropdownLink(driver);
		myAccountPage.clickMyAccountSidebarLinkByLabel(driver, "Account Information");

		Assert.assertEquals(myAccountPage.getPageHeader(driver), "Edit Account Information");
	}

	@Test(description = "Verify that user is directed to 'Account Information' page when clicking the 'Edit' link in the 'Contact Information' section")
	public void Click_Edit_Link_In_Contact_Information_Section() {
		homepage.clickCustomerNameDropdown(driver);
		myAccountPage = homepage.clickMyAccountDropdownLink(driver);
		accountInformationPage = myAccountPage.clickEditContactInformationLink();

		Assert.assertEquals(accountInformationPage.getPageHeader(driver), "Edit Account Information");
	}

	@Test(description = "Verify that user can change their first and last name")
	public void Edit_First_And_Last_Name() {
		homepage.clickCustomerNameDropdown(driver);
		myAccountPage = homepage.clickMyAccountDropdownLink(driver);
		String fullName = myAccountPage.getFullName();
		accountInformationPage = myAccountPage.clickEditContactInformationLink();
		accountInformationPage.sendKeysToFirstNameTextbox(newFirstName);
		accountInformationPage.sendKeysToLastNameTextbox(newLastName);
		myAccountPage = (MyAccountPageObject) accountInformationPage.clickSaveButton();

		Assert.assertEquals(myAccountPage.getAccountInformationSavedSuccessMessage(),
				"You saved the account information.");
		Assert.assertNotEquals(fullName, newFullName);
	}

	@Test(description = "Verify the header of the section for changing email and/or password")
	public void Display_Of_Change_Email_And_Password_Section_Header() {
		homepage.clickCustomerNameDropdown(driver);
		myAccountPage = homepage.clickMyAccountDropdownLink(driver);
		accountInformationPage = myAccountPage.clickEditContactInformationLink();
		accountInformationPage.checkChangeEmailCheckbox();

		Assert.assertEquals(accountInformationPage.getChangeEmailPasswordSectionHeader(), "Change Email");

		accountInformationPage.uncheckChangeEmailCheckbox();
		accountInformationPage.checkChangePasswordCheckbox();

		Assert.assertEquals(accountInformationPage.getChangeEmailPasswordSectionHeader(), "Change Password");

		accountInformationPage.checkChangeEmailCheckbox();

		Assert.assertEquals(accountInformationPage.getChangeEmailPasswordSectionHeader(), "Change Email and Password");
	}

	@Test(priority = 1, description = "Verify that user can change the email linked to their account")
	public void Change_Email() {
		homepage.clickCustomerNameDropdown(driver);
		myAccountPage = homepage.clickMyAccountDropdownLink(driver);
		accountInformationPage = myAccountPage.clickEditContactInformationLink();
		accountInformationPage.checkChangeEmailCheckbox();
		accountInformationPage.sendKeysToEmailTextbox(newEmail);
		accountInformationPage.sendKeysToCurrentPasswordTextbox(password);
		customerLoginPage = (CustomerLoginPageObject) accountInformationPage.clickSaveButton();
		homepage = customerLoginPage.logInAsRegisteredUser(newEmail, password);

		Assert.assertTrue(homepage.isWelcomeMessageDisplayed(driver));
	}

	@Test(priority = 2, description = "Verify that user can change the password linked to their account")
	public void Change_Password() {
		homepage.clickCustomerNameDropdown(driver);
		myAccountPage = homepage.clickMyAccountDropdownLink(driver);
		accountInformationPage = myAccountPage.clickEditContactInformationLink();
		accountInformationPage.checkChangePasswordCheckbox();
		accountInformationPage.sendKeysToCurrentPasswordTextbox(password);
		accountInformationPage.sendKeysToNewPasswordTextbox(newPassword);
		accountInformationPage.sendKeysToConfirmNewPasswordTextbox(newPassword);
		customerLoginPage = (CustomerLoginPageObject) accountInformationPage.clickSaveButton();
		homepage = customerLoginPage.logInAsRegisteredUser(newEmail, newPassword);

		Assert.assertTrue(homepage.isWelcomeMessageDisplayed(driver));
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeBrowserAndDriver();
	}

	private WebDriver driver;
	private FakeDataUtils data;
	private String email, password, newFirstName, newLastName, newFullName, newEmail, newPassword;
	private HomepageObject homepage;
	private CustomerLoginPageObject customerLoginPage;
	private MyAccountPageObject myAccountPage;
	private AccountInformationPageObject accountInformationPage;

}
