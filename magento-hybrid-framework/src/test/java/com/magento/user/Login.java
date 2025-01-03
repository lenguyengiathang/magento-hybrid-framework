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
import pageObjects.CustomerLoginPageObject;
import pageObjects.HomepageObject;
import utilities.DataHelper;

public class Login extends BaseTest {
	@Parameters("browser")
	@BeforeClass
	public void beforeClass(String browser) {
		driver = getBrowserDriver(browser);
		homepage = PageGeneratorManager.getHomepage(driver);

		data = DataHelper.getDataHelper();
		email = Register.email;
		password = Register.password;
	}

	@Test(priority = 1)
	public void Login_01_Click_Sign_In_Link_Header() {
		customerLoginPage = homepage.clickSignInLink();

		Assert.assertEquals(customerLoginPage.getPageHeader(driver), "Customer Login");
	}

	@Test(priority = 2)
	public void Login_02_Log_In_As_Non_Registered_Customer() {
		customerLoginPage.sendKeysToEmailTextbox(data.getEmailAddress());
		customerLoginPage.sendKeysToPasswordTextbox(data.getPassword());
		customerLoginPage.clickSignInButton();

		Assert.assertEquals(customerLoginPage.getLoginErrorMessage(),
				"The account sign-in was incorrect or your account is disabled temporarily. Please wait and try again later.");
	}

	@Test(priority = 3)
	public void Login_03_Log_In_As_Registered_Customer() {
		System.out.println(email);
		System.out.println(password);

		customerLoginPage.sendKeysToEmailTextbox(email);
		customerLoginPage.sendKeysToPasswordTextbox(password);
		customerLoginPage.clickSignInButton();

		Assert.assertEquals(customerLoginPage.getPageHeader(driver), "My Account");
	}

	@Test(dependsOnMethods = "Login_03_Log_In_As_Registered_Customer")
	public void Login_04_Log_Out() {
		customerLoginPage.clickCustomerNameDropdown(driver);
		homepage = customerLoginPage.clickSignOutDropdownLink(driver);

		Assert.assertEquals(homepage.getPageHeader(driver), "You are signed out");
		Assert.assertEquals(homepage.getSignedOutMessage(), "");
	}

	@Test(dependsOnMethods = "Login_03_Log_In_As_Registered_Customer")
	public void Login_05_Click_Sign_In_Link_Shopping_Cart_Page() {

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeBrowserAndDriver();
	}

	private WebDriver driver;
	private DataHelper data;
	private String email, password;
	private HomepageObject homepage;
	private CustomerLoginPageObject customerLoginPage;
}
