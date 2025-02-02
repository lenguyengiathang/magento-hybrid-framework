package com.magento.commons;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import commons.BaseTest;
import commons.PageGeneratorManager;
import pageObjects.CreateNewCustomerAccountPageObject;
import pageObjects.HomepageObject;
import pageObjects.MyAccountPageObject;
import utilities.FakeDataHelper;

public class Register extends BaseTest {

	@Parameters("browser")
	@BeforeTest
	public void RegisterNewCustomer(String browserName) {
		driver = getBrowserDriver(browserName);
		homepage = PageGeneratorManager.getHomepage(driver);

		data = FakeDataHelper.getDataHelper();
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

		driver.quit();
	}

	private WebDriver driver;
	private FakeDataHelper data;
	public static String firstName, lastName, email, password;
	private HomepageObject homepage;
	private CreateNewCustomerAccountPageObject createNewCustomerAccountPage;
	private MyAccountPageObject myAccountPage;

}
