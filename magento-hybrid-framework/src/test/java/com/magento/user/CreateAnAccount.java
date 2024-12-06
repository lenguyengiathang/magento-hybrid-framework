package com.magento.user;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.BaseTest;
import commons.PageGeneratorManager;
import pageObjects.CreateAnAccountPageObject;
import pageObjects.HomepageObject;
import utilities.DataHelper;

public class CreateAnAccount extends BaseTest {
	@Parameters("browser")
	@BeforeClass
	public void beforeClass(String browser) {
		driver = getBrowserDriver(browser);
		homepage = PageGeneratorManager.getHomepage(driver);

		data = DataHelper.getDataHelper();
		firstName = data.getFirstName();
		lastName = data.getLastName();
		email = firstName.concat(lastName).toLowerCase() + generateRandomNumber() + data.getEmailDomain();
		password = data.getPassword();
	}

	@Test
	public void Create_An_Account_Empty_Data() {
		createAnAccountPage = homepage.clickCreateAnAccountLink();
		createAnAccountPage.sendKeysToFirstNameTextbox("");
		createAnAccountPage.sendKeysToLastNameTextbox("");
		createAnAccountPage.sendKeysToEmailTextbox("");
		createAnAccountPage.sendKeysToPasswordTextbox("");
		createAnAccountPage.sendKeysToConfirmPasswordTextbox("");
		createAnAccountPage.clickCreateAnAccountButton();

		Assert.assertEquals(createAnAccountPage.getFirstNameErrorMessage(), "This is a required field.");
		Assert.assertEquals(createAnAccountPage.getLastNameErrorMessage(), "This is a required field.");
		Assert.assertEquals(createAnAccountPage.getEmailErrorMessage(), "This is a required field.");
		Assert.assertEquals(createAnAccountPage.getPasswordErrorMessage(), "This is a required field.");
		Assert.assertEquals(createAnAccountPage.getConfirmPasswordErrorMessage(), "This is a required field.");
	}

	@Test
	public void Create_An_Account_Invalid_Email_Format() {
		createAnAccountPage.sendKeysToFirstNameTextbox(firstName);
		createAnAccountPage.sendKeysToLastNameTextbox(lastName);
		createAnAccountPage.sendKeysToEmailTextbox("abcdef");
		createAnAccountPage.sendKeysToPasswordTextbox(password);
		createAnAccountPage.sendKeysToConfirmPasswordTextbox(password);
		createAnAccountPage.clickCreateAnAccountButton();

		Assert.assertEquals(createAnAccountPage.getEmailErrorMessage(),
				"Please enter a valid email address (Ex: johndoe@domain.com).");
	}

	@Test
	public void Create_An_Account_Incorrect_Confirmation_Password() {
		createAnAccountPage.sendKeysToFirstNameTextbox(firstName);
		createAnAccountPage.sendKeysToLastNameTextbox(lastName);
		createAnAccountPage.sendKeysToEmailTextbox(email);
		createAnAccountPage.sendKeysToPasswordTextbox(password);
		createAnAccountPage.sendKeysToConfirmPasswordTextbox("abc123@!%&");
		createAnAccountPage.clickCreateAnAccountButton();

		Assert.assertEquals(createAnAccountPage.getConfirmPasswordErrorMessage(), "Please enter the same value again.");
	}

	@Test
	public void Create_An_Account_Password_Less_Than_Minimum_Length() {
		createAnAccountPage.sendKeysToFirstNameTextbox(firstName);
		createAnAccountPage.sendKeysToLastNameTextbox(lastName);
		createAnAccountPage.sendKeysToEmailTextbox(email);
		createAnAccountPage.sendKeysToPasswordTextbox("123456");
		createAnAccountPage.sendKeysToConfirmPasswordTextbox(password);
		createAnAccountPage.clickCreateAnAccountButton();

		Assert.assertEquals(createAnAccountPage.getPasswordErrorMessage(),
				"Minimum length of this field must be equal or greater than 8 symbols. Leading and trailing spaces will be ignored.");
	}

	@Test
	public void Create_An_Account_Password_Meets_Minimum_Length_And_Two_Character_Classes() {
		createAnAccountPage.sendKeysToFirstNameTextbox(firstName);
		createAnAccountPage.sendKeysToLastNameTextbox(lastName);
		createAnAccountPage.sendKeysToEmailTextbox(email);
		createAnAccountPage.sendKeysToPasswordTextbox("12345abcde");
		createAnAccountPage.sendKeysToConfirmPasswordTextbox(password);
		createAnAccountPage.clickCreateAnAccountButton();

		Assert.assertEquals(createAnAccountPage.getPasswordErrorMessage(),
				"Minimum of different classes of characters in password is 3. Classes of characters: Lower Case, Upper Case, Digits, Special Characters.");
	}

	@Test
	public void Create_An_Account_Password_Less_Than_Minimum_Length_And_Three_Character_Classes() {
		createAnAccountPage.sendKeysToFirstNameTextbox(firstName);
		createAnAccountPage.sendKeysToLastNameTextbox(lastName);
		createAnAccountPage.sendKeysToEmailTextbox(email);
		createAnAccountPage.sendKeysToPasswordTextbox("12ab@!");
		createAnAccountPage.sendKeysToConfirmPasswordTextbox(password);
		createAnAccountPage.clickCreateAnAccountButton();

		Assert.assertEquals(createAnAccountPage.getPasswordErrorMessage(),
				"Minimum length of this field must be equal or greater than 8 symbols. Leading and trailing spaces will be ignored.");
	}

	@Test
	public void Create_Valid_Weak_Password() {

	}

	@Test
	public void Create_Valid_Medium_Password() {

	}

	@Test
	public void Create_Valid_Strong_Password() {

	}

	@Test
	public void Create_Valid_Very_Strong_Password() {

	}

	@Test
	public void Create_An_Account_Valid_Data() {

	}

	@Test
	public void Create_An_Account_Existing_Email() {

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeBrowserAndDriver();
	}

	private WebDriver driver;
	private DataHelper data;
	private String firstName, lastName, email, password;
	private HomepageObject homepage;
	private CreateAnAccountPageObject createAnAccountPage;

}
