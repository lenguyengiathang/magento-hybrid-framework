package com.magento.user;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.BaseTest;
import commons.PageGeneratorManager;
import pageObjects.CreateNewCustomerAccountPageObject;
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
		firstName = data.getFirstName();
		lastName = data.getLastName();
		email = firstName.concat(lastName).toLowerCase() + generateRandomNumber() + data.getEmailDomain();
		password = data.getPassword();

		createAnAccountPage = homepage.clickCreateAnAccountLink();
		createAnAccountPage.sendKeysToFirstNameTextbox(firstName);
		createAnAccountPage.sendKeysToLastNameTextbox(lastName);
		createAnAccountPage.sendKeysToEmailTextbox(email);
		createAnAccountPage.sendKeysToPasswordTextbox(password);
		createAnAccountPage.sendKeysToConfirmPasswordTextbox(password);
		myAccountPage = createAnAccountPage.clickCreateAnAccountButton();

		Assert.assertEquals(myAccountPage.getRegisterSuccessfulMessage(),
				"Thank you for registering with Main Website Store.");
	}

	@Test
	public void Add_Default_Billing_Address() {

	}

	@Test
	public void Add_Default_Shipping_Address() {

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeBrowserAndDriver();
	}

	private WebDriver driver;
	private DataHelper data;
	private String firstName, lastName, email, password;
	private HomepageObject homepage;
	private CreateNewCustomerAccountPageObject createAnAccountPage;
	private MyAccountPageObject myAccountPage;

}
