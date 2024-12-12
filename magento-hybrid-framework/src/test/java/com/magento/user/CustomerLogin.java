package com.magento.user;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.BasePage;
import commons.BaseTest;
import commons.PageGeneratorManager;
import pageObjects.CreateNewCustomerAccountPageObject;
import pageObjects.CustomerLoginPageObject;
import pageObjects.HomepageObject;
import pageObjects.MyAccountPageObject;
import utilities.DataHelper;

public class CustomerLogin extends BaseTest {
	@Parameters("browser")
	@BeforeClass
	public void beforeClass(String browser) {
		driver = getBrowserDriver(browser);
		basePage = BasePage.getBasePageObject();
		homepage = PageGeneratorManager.getHomepage(driver);

		data = DataHelper.getDataHelper();
		firstName = data.getFirstName();
		lastName = data.getLastName();
		email = firstName.concat(lastName).toLowerCase() + generateRandomNumber() + "@email.co";
		password = data.getPassword();

		createNewCustomerAccountPage = homepage.clickCreateAnAccountLink();
		createNewCustomerAccountPage.sendKeysToFirstNameTextbox(firstName);
		createNewCustomerAccountPage.sendKeysToLastNameTextbox(lastName);
		createNewCustomerAccountPage.sendKeysToEmailTextbox(email);
		createNewCustomerAccountPage.sendKeysToPasswordTextbox(password);
		createNewCustomerAccountPage.sendKeysToConfirmPasswordTextbox(password);
		myAccountPage = createNewCustomerAccountPage.clickCreateAnAccountButton();

		Assert.assertEquals(myAccountPage.getRegisterSuccessfulMessage(),
				"Thank you for registering with Main Website Store.");

		myAccountPage.clickCustomerNameDropdown();
		homepage = myAccountPage.clickSignOutDropdownLink();
	}

	@Test(priority = 1)
	public void Login_01_Verify_Customer_Login_Page_Navigation() {
		customerLoginPage = homepage.clickSignInLink();

		Assert.assertEquals(basePage.getPageHeader(driver), "Customer Login");
	}

	@Test(priority = 2)
	public void Login_02_Log_In_As_Registered_Customer() {
		customerLoginPage.sendKeysToEmailTextbox(email);
		customerLoginPage.sendKeysToPasswordTextbox(password);
		myAccountPage = customerLoginPage.clickSignInButton();

		Assert.assertEquals(basePage.getPageHeader(driver), "Home Page");
	}

	@Test(priority = 3)
	public void Login_03_Log_In_As_Non_Registered_Customer() {
		myAccountPage.clickCustomerNameDropdown();
		homepage = myAccountPage.clickSignOutDropdownLink();
		customerLoginPage = homepage.clickSignInLink();
		customerLoginPage.sendKeysToEmailTextbox(data.getEmailAddress());
		customerLoginPage.sendKeysToPasswordTextbox(data.getPassword());
		customerLoginPage.clickSignInButton();

		Assert.assertEquals(customerLoginPage.getLoginErrorMessage(),
				"The account sign-in was incorrect or your account is disabled temporarily. Please wait and try again later.");
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeBrowserAndDriver();
	}

	private WebDriver driver;
	private DataHelper data;
	private String firstName, lastName, email, password;
	private BasePage basePage;
	private HomepageObject homepage;
	private CreateNewCustomerAccountPageObject createNewCustomerAccountPage;
	private CustomerLoginPageObject customerLoginPage;
	private MyAccountPageObject myAccountPage;
}
