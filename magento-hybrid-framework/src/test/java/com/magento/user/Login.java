package com.magento.user;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.magento.commons.Register;

import commons.BaseTest;
import commons.PageGeneratorManager;
import pageObjects.CheckoutPageObject;
import pageObjects.CustomerLoginPageObject;
import pageObjects.ForgotYourPasswordPageObject;
import pageObjects.HomepageObject;
import pageObjects.ProductListingPageObject;
import utilities.FakeDataUtils;

public class Login extends BaseTest {
	@Parameters("browser")
	@BeforeClass
	public void beforeClass(String browser) {
		driver = getBrowserDriver(browser);
		homepage = PageGeneratorManager.getHomepage(driver);

		data = FakeDataUtils.getDataHelper();
		email = Register.email;
		password = Register.password;
	}

	@Test(priority = 1, description = "Verify that user is directed to 'Customer Login' page when clicking the 'Sign In' link in the header")
	public void Click_Sign_In_Link_Header() {
		customerLoginPage = homepage.clickSignInLink();

		Assert.assertEquals(customerLoginPage.getPageHeader(driver), "Customer Login");
	}

	@Test(priority = 2, description = "Verify that non-registered customer cannot log in")
	public void Log_In_As_Non_Registered_Customer() {
		customerLoginPage = homepage.clickSignInLink();
		customerLoginPage.sendKeysToEmailTextbox(data.getEmailAddress());
		customerLoginPage.sendKeysToPasswordTextbox(data.getPassword());
		customerLoginPage.clickSignInButton();

		Assert.assertEquals(customerLoginPage.getLoginErrorMessage(),
				"The account sign-in was incorrect or your account is disabled temporarily. Please wait and try again later.");
	}

	@Test(priority = 3, description = "Verify that registered customer can log in with valid credentials")
	public void Log_In_As_Registered_Customer() {
		customerLoginPage = homepage.clickSignInLink();
		homepage = customerLoginPage.logInAsRegisteredUser(email, password);

		Assert.assertEquals(homepage.getPageHeader(driver), "Home Page");
	}

	@Test(priority = 4, description = "Verify that user is directed to the homepage when clicking the 'Sign Out' dropdown link")
	public void Log_Out() {
		homepage.clickCustomerNameDropdown(driver);
		homepage.clickSignOutDropdownLink(driver);

		Assert.assertEquals(homepage.getPageHeader(driver), "You are signed out");
		Assert.assertEquals(homepage.getSignedOutMessage(),
				"You have signed out and will go to our homepage in 5 seconds.");
	}

	@Test(priority = 5, description = "Verify that user is directed to the 'Forgot Your Password?' page when clicking the 'Forgot Your Password?' hyperlink")
	public void Click_Forgot_Password_Link() {
		customerLoginPage = homepage.clickSignInLink();
		forgotYourPasswordPage = customerLoginPage.clickForgotYourPasswordLink();

		Assert.assertEquals(forgotYourPasswordPage.getPageHeader(driver), "Forgot Your Password?");
	}

	@Test(priority = 6, description = "Verify that registered customer can log in with valid credentials by clicking the 'Sign In' hyperlink on the 'Checkout' page")
	public void Click_Sign_In_Link_Shopping_Cart_Page() {
		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear", "Bags");
		productListingPage.clickAddToCartButtonByProductName(driver, "Wayfarer Messenger Bag");
		productListingPage.clickShoppingCartIcon(driver);
		checkoutPage = productListingPage.clickProceedToCheckoutButton(driver);
		checkoutPage.clickSignInLink();
		checkoutPage.sendKeysToSignInModalEmailAddressTextbox(email);
		checkoutPage.sendKeysToSignInModalPasswordTextbox(password);
		checkoutPage.clickSignInModalSignInButton();
		homepage = checkoutPage.clickLumaLogo(driver);

		Assert.assertTrue(homepage.isWelcomeMessageDisplayed(driver));
	}

	@AfterMethod(alwaysRun = true)
	public void logTestResult(ITestResult result) {
		int status = result.getStatus();
		switch (status) {
		case ITestResult.SUCCESS:
			System.out.println("Test passed: " + result.getMethod().getDescription());
			break;
		case ITestResult.FAILURE:
			System.out.println("Test failed: " + result.getMethod().getDescription());
			break;
		case ITestResult.SKIP:
			System.out.println("Test skipped: " + result.getMethod().getDescription());
			break;
		default:
			System.out.println("Unknown status: " + result.getMethod().getDescription());
			break;
		}
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeBrowserAndDriver();
	}

	private WebDriver driver;
	private FakeDataUtils data;
	private String email, password;
	private HomepageObject homepage;
	private CustomerLoginPageObject customerLoginPage;
	private ForgotYourPasswordPageObject forgotYourPasswordPage;
	private ProductListingPageObject productListingPage;
	private CheckoutPageObject checkoutPage;
}
