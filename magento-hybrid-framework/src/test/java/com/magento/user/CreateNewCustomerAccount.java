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

public class CreateNewCustomerAccount extends BaseTest {
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
	}

	@Test(priority = 1)
	public void Register_01_Verify_Create_New_Customer_Account_Page_Navigation() {
		createNewCustomerAccountPage = homepage.clickCreateAnAccountLink();

		basePage.isPageTitleDisplayedCorrectly(driver, "Create New Customer Account");
	}

	@Test(priority = 2)
	public void Register_02_Verify_Create_New_Customer_Account_Page_Navigation() {
		homepage = createNewCustomerAccountPage.clickLumaLogo(driver);
		customerLoginPage = homepage.clickSignInLink();
		createNewCustomerAccountPage = customerLoginPage.clickCreateNewAccountButton();

		basePage.isPageTitleDisplayedCorrectly(driver, "Create New Customer Account");
	}

	@Test(priority = 3)
	public void Register_03_Create_An_Account_Empty_Data() {
		createNewCustomerAccountPage.sendKeysToFirstNameTextbox("");
		createNewCustomerAccountPage.sendKeysToLastNameTextbox("");
		createNewCustomerAccountPage.sendKeysToEmailTextbox("");
		createNewCustomerAccountPage.sendKeysToPasswordTextbox("");
		createNewCustomerAccountPage.sendKeysToConfirmPasswordTextbox("");
		createNewCustomerAccountPage.clickCreateAnAccountButton();

		Assert.assertEquals(createNewCustomerAccountPage.getFirstNameErrorMessage(), "This is a required field.");
		Assert.assertEquals(createNewCustomerAccountPage.getLastNameErrorMessage(), "This is a required field.");
		Assert.assertEquals(createNewCustomerAccountPage.getEmailErrorMessage(), "This is a required field.");
		Assert.assertEquals(createNewCustomerAccountPage.getPasswordErrorMessage(), "This is a required field.");
		Assert.assertEquals(createNewCustomerAccountPage.getConfirmPasswordErrorMessage(), "This is a required field.");
	}

	@Test(priority = 4)
	public void Register_04_Create_An_Account_Invalid_Email_Format() {
		createNewCustomerAccountPage.sendKeysToFirstNameTextbox(firstName);
		createNewCustomerAccountPage.sendKeysToLastNameTextbox(lastName);
		createNewCustomerAccountPage.sendKeysToEmailTextbox("abcdef");
		createNewCustomerAccountPage.sendKeysToPasswordTextbox(password);
		createNewCustomerAccountPage.sendKeysToConfirmPasswordTextbox(password);
		createNewCustomerAccountPage.clickCreateAnAccountButton();

		Assert.assertEquals(createNewCustomerAccountPage.getEmailErrorMessage(),
				"Please enter a valid email address (Ex: johndoe@domain.com).");
	}

	@Test(priority = 5)
	public void Register_05_Create_An_Account_Incorrect_Confirmation_Password() {
		createNewCustomerAccountPage.sendKeysToFirstNameTextbox(firstName);
		createNewCustomerAccountPage.sendKeysToLastNameTextbox(lastName);
		createNewCustomerAccountPage.sendKeysToEmailTextbox(email);
		createNewCustomerAccountPage.sendKeysToPasswordTextbox(password);
		createNewCustomerAccountPage.sendKeysToConfirmPasswordTextbox("abc123@!%&");
		createNewCustomerAccountPage.clickCreateAnAccountButton();

		Assert.assertEquals(createNewCustomerAccountPage.getConfirmPasswordErrorMessage(),
				"Please enter the same value again.");
	}

	@Test(priority = 6)
	public void Register_06_Create_An_Account_Password_Less_Than_Minimum_Length() {
		createNewCustomerAccountPage.sendKeysToFirstNameTextbox(firstName);
		createNewCustomerAccountPage.sendKeysToLastNameTextbox(lastName);
		createNewCustomerAccountPage.sendKeysToEmailTextbox(email);
		createNewCustomerAccountPage.sendKeysToPasswordTextbox("123456");
		createNewCustomerAccountPage.sendKeysToConfirmPasswordTextbox(password);
		createNewCustomerAccountPage.clickCreateAnAccountButton();

		Assert.assertEquals(createNewCustomerAccountPage.getPasswordErrorMessage(),
				"Minimum length of this field must be equal or greater than 8 symbols. Leading and trailing spaces will be ignored.");
	}

	@Test(priority = 7)
	public void Register_07_Create_An_Account_Password_Meets_Minimum_Length_And_Two_Character_Classes() {
		createNewCustomerAccountPage.sendKeysToFirstNameTextbox(firstName);
		createNewCustomerAccountPage.sendKeysToLastNameTextbox(lastName);
		createNewCustomerAccountPage.sendKeysToEmailTextbox(email);
		createNewCustomerAccountPage.sendKeysToPasswordTextbox("12345abcde");
		createNewCustomerAccountPage.sendKeysToConfirmPasswordTextbox(password);
		createNewCustomerAccountPage.clickCreateAnAccountButton();

		Assert.assertEquals(createNewCustomerAccountPage.getPasswordErrorMessage(),
				"Minimum of different classes of characters in password is 3. Classes of characters: Lower Case, Upper Case, Digits, Special Characters.");
	}

	@Test(priority = 8)
	public void Register_08_Create_An_Account_Password_Less_Than_Minimum_Length_And_Three_Character_Classes() {
		createNewCustomerAccountPage.sendKeysToFirstNameTextbox(firstName);
		createNewCustomerAccountPage.sendKeysToLastNameTextbox(lastName);
		createNewCustomerAccountPage.sendKeysToEmailTextbox(email);
		createNewCustomerAccountPage.sendKeysToPasswordTextbox("12ab@!");
		createNewCustomerAccountPage.sendKeysToConfirmPasswordTextbox(password);
		createNewCustomerAccountPage.clickCreateAnAccountButton();

		Assert.assertEquals(createNewCustomerAccountPage.getPasswordErrorMessage(),
				"Minimum length of this field must be equal or greater than 8 symbols. Leading and trailing spaces will be ignored.");
	}

	@Test(priority = 9)
	public void Register_09_Create_Weak_Password() {
		createNewCustomerAccountPage.sendKeysToPasswordTextbox("abc");

		Assert.assertEquals(createNewCustomerAccountPage.getPasswordStrengthMessage(), "Weak");
	}

	@Test(priority = 10)
	public void Register_10_Create_Medium_Password() {
		createNewCustomerAccountPage.sendKeysToPasswordTextbox("abc1234*");

		Assert.assertEquals(createNewCustomerAccountPage.getPasswordStrengthMessage(), "Medium");
	}

	@Test(priority = 11)
	public void Register_11_Create_Strong_Password() {
		createNewCustomerAccountPage.sendKeysToPasswordTextbox("abcd1234*");

		Assert.assertEquals(createNewCustomerAccountPage.getPasswordStrengthMessage(), "Strong");
	}

	@Test(priority = 12)
	public void Register_12_Create_Very_Strong_Password() {
		createNewCustomerAccountPage.sendKeysToPasswordTextbox("Abcd1234@!*&%");

		Assert.assertEquals(createNewCustomerAccountPage.getPasswordStrengthMessage(), "Very Strong");
	}

	@Test(priority = 13)
	public void Register_13_Create_An_Account_Valid_Data() {
		createNewCustomerAccountPage.sendKeysToFirstNameTextbox(firstName);
		createNewCustomerAccountPage.sendKeysToLastNameTextbox(lastName);
		createNewCustomerAccountPage.sendKeysToEmailTextbox(email);
		createNewCustomerAccountPage.sendKeysToPasswordTextbox(password);
		createNewCustomerAccountPage.sendKeysToConfirmPasswordTextbox(password);
		myAccountPage = createNewCustomerAccountPage.clickCreateAnAccountButton();

		Assert.assertEquals(myAccountPage.getRegisterSuccessfulMessage(),
				"Thank you for registering with Main Website Store.");
		Assert.assertEquals(myAccountPage.getUserFullName(), firstName + " " + lastName);
		Assert.assertEquals(myAccountPage.getUserEmail(), email);
	}

	@Test(dependsOnMethods = "Register_13_Create_An_Account_Valid_Data")
	public void Register_14_Create_An_Account_Existing_Email() {
		myAccountPage.clickCustomerNameDropdown();
		homepage = myAccountPage.clickSignOutDropdownLink();
		createNewCustomerAccountPage = homepage.clickCreateAnAccountLink();
		createNewCustomerAccountPage.sendKeysToFirstNameTextbox(data.getFirstName());
		createNewCustomerAccountPage.sendKeysToLastNameTextbox(data.getLastName());
		createNewCustomerAccountPage.sendKeysToEmailTextbox(email);
		createNewCustomerAccountPage.sendKeysToPasswordTextbox(password);
		createNewCustomerAccountPage.sendKeysToConfirmPasswordTextbox(password);
		myAccountPage = createNewCustomerAccountPage.clickCreateAnAccountButton();

		Assert.assertEquals(createNewCustomerAccountPage.getExistingEmailErrorMessage(),
				"There is already an account with this email address. If you are sure that it is your email address, click here to get your password and access your account.");
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
