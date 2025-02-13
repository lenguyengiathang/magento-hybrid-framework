package com.magento.user;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.magento.commons.Access;
import com.magento.commons.Products;
import com.magento.commons.Register;

import commons.BaseTest;
import commons.PageGeneratorManager;
import pageObjects.CheckoutPageObject;
import pageObjects.CustomerLoginPageObject;
import pageObjects.ForgotYourPasswordPageObject;
import pageObjects.HomepageObject;
import utilities.FakeDataUtils;

public class Login extends BaseTest {
	@Parameters("browser")
	@BeforeClass
	public void beforeClass(String browser) {
		driver = getBrowserDriver(browser);
		homepage = PageGeneratorManager.getHomepage(driver);

		accessActions = new Access(driver);
		productActions = new Products(driver);
		data = FakeDataUtils.getDataHelper();
		email = Register.email;
		password = Register.password;
	}

	@BeforeMethod(alwaysRun = true, onlyForGroups = "addProductToCart")
	public void addProductToCart(Method method) {
		if (Arrays.asList(method.getAnnotation(Test.class).groups()).contains("addProductToCart")) {
			try {
				productActions.addRandomProductWithoutOptionsToCart();
			} catch (Exception e) {
				System.err.println("Error adding product to cart: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	@Test(description = "Verify that user is directed to 'Customer Login' page when clicking the 'Sign In' link in the header")
	public void Login_01_Click_Sign_In_Link_Header() {
		customerLoginPage = homepage.clickSignInLink();

		Assert.assertEquals(customerLoginPage.getPageHeader(driver), "Customer Login");
	}

	@Test(description = "Verify that non-registered customer cannot log in")
	public void Login_02_Log_In_As_Non_Registered_Customer() {
		customerLoginPage = homepage.clickSignInLink();
		customerLoginPage.sendKeysToEmailTextbox(data.getEmailAddress());
		customerLoginPage.sendKeysToPasswordTextbox(data.getPassword());
		customerLoginPage.clickSignInButton();

		Assert.assertEquals(customerLoginPage.getLoginErrorMessage(),
				"The account sign-in was incorrect or your account is disabled temporarily. Please wait and try again later.");
	}

	@Test(description = "Verify that registered customer can log in with valid credentials")
	public void Login_03_Log_In_As_Registered_Customer() {
		customerLoginPage = homepage.clickSignInLink();
		homepage = customerLoginPage.logInAsRegisteredUser(email, password);

		Assert.assertTrue(homepage.isWelcomeMessageDisplayed(driver));
	}

	@Test(description = "Verify that user is directed to the homepage when clicking the 'Sign Out' dropdown link")
	public void Login_04_Log_Out() {
		customerLoginPage = homepage.clickSignInLink();
		homepage = customerLoginPage.logInAsRegisteredUser(email, password);
		homepage.clickCustomerNameDropdown(driver);
		homepage.clickSignOutDropdownLink(driver);

		Assert.assertEquals(homepage.getPageHeader(driver), "You are signed out");
		Assert.assertEquals(homepage.getSignedOutMessage(),
				"You have signed out and will go to our homepage in 5 seconds.");
	}

	@Test(description = "Verify that user is directed to the 'Forgot Your Password?' page when clicking the 'Forgot Your Password?' hyperlink")
	public void Login_05_Click_Forgot_Password_Link() {
		customerLoginPage = homepage.clickSignInLink();
		forgotYourPasswordPage = customerLoginPage.clickForgotYourPasswordLink();

		Assert.assertEquals(forgotYourPasswordPage.getPageHeader(driver), "Forgot Your Password?");
	}

	@Test(groups = "addProductToCart", description = "Verify that registered customer can log in from the 'Checkout' page using the 'Sign In' modal")
	public void Login_06_Log_In_From_Checkout_Page_Using_Sign_In_Modal() {
		homepage.clickShoppingCartIcon(driver);
		checkoutPage = homepage.clickProceedToCheckoutButton(driver);
		checkoutPage.clickSignInLink();
		checkoutPage.sendKeysToSignInModalEmailAddressTextbox(email);
		checkoutPage.sendKeysToSignInModalPasswordTextbox(password);
		checkoutPage.clickSignInModalSignInButton();
		homepage = checkoutPage.clickLumaLogo(driver);

		Assert.assertTrue(homepage.isWelcomeMessageDisplayed(driver));
	}

	@Test(groups = "addProductToCart", description = "Verify that registered customer can log in from the 'Checkout' page when filling in 'Email' and 'Password' field with valid credentials")
	public void Login_07_Log_In_From_Checkout_Page_By_Filling_In_Email_And_Password_Field() {
		homepage.clickShoppingCartIcon(driver);
		checkoutPage = homepage.clickProceedToCheckoutButton(driver);
		checkoutPage.sendKeysToEmailAddressTextbox(email);
		checkoutPage.sendKeysToPasswordTextbox(password);
		checkoutPage.clickLoginButton();
		homepage = checkoutPage.clickLumaLogo(driver);

		Assert.assertTrue(homepage.isWelcomeMessageDisplayed(driver));
	}

	@AfterMethod(alwaysRun = true)
	public void logOut(ITestResult result) {
		if (result.getMethod().getMethodName().contains("Login_03")
				|| result.getMethod().getMethodName().contains("Login_06")) {
			try {
				accessActions.logOut();
			} catch (Exception e) {
				System.err.println("Error logging out: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	@AfterMethod(alwaysRun = true)
	public void afterMethod(ITestResult result) {
		logTestResult(result);
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
	private CheckoutPageObject checkoutPage;
	private Access accessActions;
	private Products productActions;
}
