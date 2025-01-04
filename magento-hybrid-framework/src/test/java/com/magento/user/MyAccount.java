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
import utilities.DataHelper;

public class MyAccount extends BaseTest {
	@Parameters("browser")
	@BeforeClass
	public void beforeClass(String browser) {
		driver = getBrowserDriver(browser);
		homepage = PageGeneratorManager.getHomepage(driver);

		data = DataHelper.getDataHelper();
		email = Register.email;
		password = Register.password;
		newFirstName = data.getFirstName();
		newLastName = data.getLastName();
		newFullName = newFirstName + ' ' + newLastName;

		customerLoginPage = homepage.clickSignInLink();
		homepage = customerLoginPage.logInAsRegisteredUser(email, password);
	}

	@Test
	public void My_Account_01_Account_Information_Page_Navigation() {
		homepage.clickCustomerNameDropdown(driver);
		myAccountPage = homepage.clickMyAccountDropdownLink(driver);
		myAccountPage.clickMyAccountSidebarLinkByLabel(driver, "Account Information");

		Assert.assertEquals(myAccountPage.getPageHeader(driver), "Edit Account Information");
	}

	@Test
	public void My_Account_02_Account_Information_Page_Navigation() {
		myAccountPage.clickEditContactInformationLink();

		Assert.assertEquals(myAccountPage.getPageHeader(driver), "Edit Account Information");
	}

	@Test
	public void My_Account_03_Edit_First_And_Last_Name() {
		String fullName = myAccountPage.getFullName();
		accountInformationPage = myAccountPage.clickEditContactInformationLink();
		accountInformationPage.sendKeysToFirstNameTextbox(newFirstName);
		accountInformationPage.sendKeysToLastNameTextbox(newLastName);
		myAccountPage = accountInformationPage.clickSaveButton();

		Assert.assertEquals(myAccountPage.getAccountInformationSavedSuccessMessage(),
				"You saved the account information.");
		Assert.assertNotEquals(fullName, newFullName);
	}

	@Test
	public void My_Account_04_Change_Email_And_Password_Section_Header() {
		accountInformationPage = myAccountPage.clickEditContactInformationLink();
		accountInformationPage.checkChangeEmailCheckbox();

		Assert.assertEquals(accountInformationPage.getChangeEmailPasswordSectionHeader(), "Change Email");

		accountInformationPage.uncheckChangeEmailCheckbox();
		accountInformationPage.checkChangePasswordCheckbox();

		Assert.assertEquals(accountInformationPage.getChangeEmailPasswordSectionHeader(), "Change Password");

		accountInformationPage.checkChangeEmailCheckbox();

		Assert.assertEquals(accountInformationPage.getChangeEmailPasswordSectionHeader(), "Change Email and Password");
	}

	@Test
	public void My_Account_05_Change_Email() {

	}

	@Test
	public void My_Account_06_Change_Password() {

	}

	@Test
	public void My_Account_07_Add_New_Address_Page_Navigation_No_Address_Added() {

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeBrowserAndDriver();
	}

	private WebDriver driver;
	private DataHelper data;
	private String email, password, newFirstName, newLastName, newFullName;
	private HomepageObject homepage;
	private CustomerLoginPageObject customerLoginPage;
	private MyAccountPageObject myAccountPage;
	private AccountInformationPageObject accountInformationPage;

}
