package com.magento.user;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.BaseTest;
import commons.PageGeneratorManager;
import pageObjects.CreateNewCustomerAccountPageObject;
import pageObjects.CustomerLoginPageObject;
import pageObjects.HomepageObject;
import pageObjects.MyAccountPageObject;
import utilities.FakeDataUtils;
import utilities.JsonUtils;

public class Register extends BaseTest {
	@Parameters("browser")
	@BeforeClass
	public void beforeClass(String browser) {
		driver = getBrowserDriver(browser);
		homepage = PageGeneratorManager.getHomepage(driver);

		data = FakeDataUtils.getDataHelper();
		firstName = data.getFirstName();
		lastName = data.getLastName();
		email = firstName.concat(lastName).toLowerCase() + generateRandomNumber() + "@email.com";
		password = data.getPassword();
		existingEmail = JsonUtils.getJsonValue("login_data.json", "existing_user.email");
	}

	@Test(description = "Verify that user is directed to 'Create New Customer Account' page when clicking the 'Create an Account' link in the header")
	public void Register_01_Click_Create_An_Account_Link() {
		createNewCustomerAccountPage = homepage.clickCreateAnAccountLink();

		Assert.assertEquals(createNewCustomerAccountPage.getPageHeader(driver), "Create New Customer Account");
	}

	@Test(description = "Verify that user is directed to 'Create New Customer Account' page when clicking the 'Create an Account' button in the 'Customer Login' page")
	public void Register_02_Click_Create_An_Account_Button() {
		customerLoginPage = homepage.clickSignInLink();
		createNewCustomerAccountPage = customerLoginPage.clickCreateNewAccountButton();

		Assert.assertEquals(createNewCustomerAccountPage.getPageHeader(driver), "Create New Customer Account");
	}

	@Test(description = "Verify the error messages displayed when the required fields are not filled")
	public void Register_03_Error_Message_Empty_Data() {
		createNewCustomerAccountPage = homepage.clickCreateAnAccountLink();
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

	@Test(description = "Verify the error message displayed when creating an account with an invalid email format")
	public void Register_04_Error_Message_Invalid_Email_Format() {
		createNewCustomerAccountPage = homepage.clickCreateAnAccountLink();
		createNewCustomerAccountPage.sendKeysToFirstNameTextbox(firstName);
		createNewCustomerAccountPage.sendKeysToLastNameTextbox(lastName);
		createNewCustomerAccountPage.sendKeysToEmailTextbox("abcdef");
		createNewCustomerAccountPage.sendKeysToPasswordTextbox(password);
		createNewCustomerAccountPage.sendKeysToConfirmPasswordTextbox(password);
		createNewCustomerAccountPage.clickCreateAnAccountButton();

		Assert.assertEquals(createNewCustomerAccountPage.getEmailErrorMessage(),
				"Please enter a valid email address (Ex: johndoe@domain.com).");
	}

	@Test(description = "Verify the error message displayed when creating an account with incorrect confirm password")
	public void Register_05_Error_Message_Incorrect_Confirmation_Password() {
		createNewCustomerAccountPage = homepage.clickCreateAnAccountLink();
		createNewCustomerAccountPage.sendKeysToFirstNameTextbox(firstName);
		createNewCustomerAccountPage.sendKeysToLastNameTextbox(lastName);
		createNewCustomerAccountPage.sendKeysToEmailTextbox(email);
		createNewCustomerAccountPage.sendKeysToPasswordTextbox(password);
		createNewCustomerAccountPage.sendKeysToConfirmPasswordTextbox("abc123@!%&");
		createNewCustomerAccountPage.clickCreateAnAccountButton();

		Assert.assertEquals(createNewCustomerAccountPage.getConfirmPasswordErrorMessage(),
				"Please enter the same value again.");
	}

	@Test(description = "Verify the error message displayed when creating an account with password less than minimum length (8 characters)")
	public void Register_06_Error_Message_Password_Less_Than_Minimum_Length() {
		createNewCustomerAccountPage = homepage.clickCreateAnAccountLink();
		createNewCustomerAccountPage.sendKeysToFirstNameTextbox(firstName);
		createNewCustomerAccountPage.sendKeysToLastNameTextbox(lastName);
		createNewCustomerAccountPage.sendKeysToEmailTextbox(email);
		createNewCustomerAccountPage.sendKeysToPasswordTextbox("123456");
		createNewCustomerAccountPage.sendKeysToConfirmPasswordTextbox(password);
		createNewCustomerAccountPage.clickCreateAnAccountButton();

		Assert.assertEquals(createNewCustomerAccountPage.getPasswordErrorMessage(),
				"Minimum length of this field must be equal or greater than 8 symbols. Leading and trailing spaces will be ignored.");
	}

	@Test(description = "Verify the error message displayed when creating an account with password that meets minimum length (8 characters) and has two character classes")
	public void Register_07_Error_Message_Password_Meets_Minimum_Length_And_Two_Character_Classes() {
		createNewCustomerAccountPage = homepage.clickCreateAnAccountLink();
		createNewCustomerAccountPage.sendKeysToFirstNameTextbox(firstName);
		createNewCustomerAccountPage.sendKeysToLastNameTextbox(lastName);
		createNewCustomerAccountPage.sendKeysToEmailTextbox(email);
		createNewCustomerAccountPage.sendKeysToPasswordTextbox("12345abcde");
		createNewCustomerAccountPage.sendKeysToConfirmPasswordTextbox(password);
		createNewCustomerAccountPage.clickCreateAnAccountButton();

		Assert.assertEquals(createNewCustomerAccountPage.getPasswordErrorMessage(),
				"Minimum of different classes of characters in password is 3. Classes of characters: Lower Case, Upper Case, Digits, Special Characters.");
	}

	@Test(description = "Verify the error message displayed when creating an account with password less than minimum length (8 characters) and has three character classes")
	public void Register_08_Error_Message_Password_Less_Than_Minimum_Length_And_Three_Character_Classes() {
		createNewCustomerAccountPage = homepage.clickCreateAnAccountLink();
		createNewCustomerAccountPage.sendKeysToFirstNameTextbox(firstName);
		createNewCustomerAccountPage.sendKeysToLastNameTextbox(lastName);
		createNewCustomerAccountPage.sendKeysToEmailTextbox(email);
		createNewCustomerAccountPage.sendKeysToPasswordTextbox("12ab@!");
		createNewCustomerAccountPage.sendKeysToConfirmPasswordTextbox(password);
		createNewCustomerAccountPage.clickCreateAnAccountButton();

		Assert.assertEquals(createNewCustomerAccountPage.getPasswordErrorMessage(),
				"Minimum length of this field must be equal or greater than 8 symbols. Leading and trailing spaces will be ignored.");
	}

	@Test(description = "Verify the error message displayed when creating an account with password that matches the email address")
	public void Register_09_Error_Message_Password_Matches_Email_Address() {
		createNewCustomerAccountPage = homepage.clickCreateAnAccountLink();
		createNewCustomerAccountPage.sendKeysToFirstNameTextbox(firstName);
		createNewCustomerAccountPage.sendKeysToLastNameTextbox(lastName);
		createNewCustomerAccountPage.sendKeysToEmailTextbox(email);
		createNewCustomerAccountPage.sendKeysToPasswordTextbox(email);
		createNewCustomerAccountPage.sendKeysToConfirmPasswordTextbox(email);
		createNewCustomerAccountPage.clickCreateAnAccountButton();

		Assert.assertEquals(createNewCustomerAccountPage.getPasswordErrorMessage(),
				"The password can't be the same as the email address. Create a new password and try again.");
	}

	@Test(description = "Verify the display of password strength message when creating a weak password")
	public void Register_10_Weak_Password_Strength_Info_Message() {
		createNewCustomerAccountPage = homepage.clickCreateAnAccountLink();
		createNewCustomerAccountPage.sendKeysToPasswordTextbox("abc");

		Assert.assertEquals(createNewCustomerAccountPage.getPasswordStrengthMessage(), "Weak");
	}

	@Test(description = "Verify the display of password strength message when creating a medium password")
	public void Register_11_Medium_Password_Strength_Info_Message() {
		createNewCustomerAccountPage = homepage.clickCreateAnAccountLink();
		createNewCustomerAccountPage.sendKeysToPasswordTextbox("abc1234*");

		Assert.assertEquals(createNewCustomerAccountPage.getPasswordStrengthMessage(), "Medium");
	}

	@Test(description = "Verify the display of password strength message when creating a strong password")
	public void Register_12_Strong_Password_Strength_Info_Message() {
		createNewCustomerAccountPage = homepage.clickCreateAnAccountLink();
		createNewCustomerAccountPage.sendKeysToPasswordTextbox("abcd1234*");

		Assert.assertEquals(createNewCustomerAccountPage.getPasswordStrengthMessage(), "Strong");
	}

	@Test(description = "Verify the display of password strength message when creating a very strong password")
	public void Register_13_Very_Strong_Password_Strength_Info_Message() {
		createNewCustomerAccountPage = homepage.clickCreateAnAccountLink();
		createNewCustomerAccountPage.sendKeysToPasswordTextbox("Abcd1234@!*&%");

		Assert.assertEquals(createNewCustomerAccountPage.getPasswordStrengthMessage(), "Very Strong");
	}

	@Test(description = "Verify that user can create an account successfully when filling in all mandatory fields with valid information")
	public void Register_14_Create_An_Account_Valid_Data() {
		createNewCustomerAccountPage = homepage.clickCreateAnAccountLink();
		createNewCustomerAccountPage.sendKeysToFirstNameTextbox(firstName);
		createNewCustomerAccountPage.sendKeysToLastNameTextbox(lastName);
		createNewCustomerAccountPage.sendKeysToEmailTextbox(email);
		createNewCustomerAccountPage.sendKeysToPasswordTextbox(password);
		createNewCustomerAccountPage.sendKeysToConfirmPasswordTextbox(password);
		myAccountPage = createNewCustomerAccountPage.clickCreateAnAccountButton();

		Assert.assertEquals(myAccountPage.getRegisterSuccessfulMessage(),
				"Thank you for registering with Main Website Store.");
		Assert.assertEquals(myAccountPage.getFullName(), firstName + " " + lastName);
		Assert.assertEquals(myAccountPage.getEmail(), email);
	}

	@Test(description = "Verify that user cannot create an account with an email belonging to another account")
	public void Register_15_Create_An_Account_Existing_Email() {
		createNewCustomerAccountPage = homepage.clickCreateAnAccountLink();
		createNewCustomerAccountPage.sendKeysToFirstNameTextbox(data.getFirstName());
		createNewCustomerAccountPage.sendKeysToLastNameTextbox(data.getLastName());
		createNewCustomerAccountPage.sendKeysToEmailTextbox(existingEmail);
		createNewCustomerAccountPage.sendKeysToPasswordTextbox(password);
		createNewCustomerAccountPage.sendKeysToConfirmPasswordTextbox(password);
		createNewCustomerAccountPage.clickCreateAnAccountButton();

		Assert.assertEquals(createNewCustomerAccountPage.getExistingEmailErrorMessage(),
				"There is already an account with this email address. If you are sure that it is your email address, click here to get your password and access your account.");
	}

	@AfterMethod(alwaysRun = true)
	public void logOut(ITestResult result) {
		if (result.getMethod().getMethodName().contains("Register_14")) {
			homepage.clickCustomerNameDropdown(driver);
			homepage.clickSignOutDropdownLink(driver);
			homepage.refreshCurrentPage(driver);
		}
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeBrowserAndDriver();
	}

	private WebDriver driver;
	private FakeDataUtils data;
	private String firstName, lastName, email, password, existingEmail;
	private HomepageObject homepage;
	private CreateNewCustomerAccountPageObject createNewCustomerAccountPage;
	private CustomerLoginPageObject customerLoginPage;
	private MyAccountPageObject myAccountPage;

}
