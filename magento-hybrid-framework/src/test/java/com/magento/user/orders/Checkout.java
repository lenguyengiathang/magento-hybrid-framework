package com.magento.user.orders;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.magento.commons.Products;

import commons.BaseTest;
import commons.PageGeneratorManager;
import pageObjects.CheckoutPageObject;
import pageObjects.CustomerLoginPageObject;
import pageObjects.HomepageObject;
import pageObjects.ProductDetailsPageObject;
import pageObjects.ProductListingPageObject;
import pageObjects.ShoppingCartPageObject;
import utilities.CSVUtils;
import utilities.FakeDataUtils;
import utilities.JsonUtils;

public class Checkout extends BaseTest {
	@Parameters("browser")
	@BeforeClass
	public void beforeClass(String browser) {
		driver = getBrowserDriver(browser);
		homepage = PageGeneratorManager.getHomepage(driver);
		productActions = new Products(driver);

		data = FakeDataUtils.getDataHelper();
		fileName = "login_data.json";
		email = JsonUtils.getJsonValue(fileName, "email");
		password = JsonUtils.getJsonValue(fileName, "password");

		customerLoginPage = homepage.clickSignInLink();
		homepage = customerLoginPage.logInAsRegisteredUser(email, password);
		productActions.addRandomProductWithoutOptionsToCart();
	}

	@BeforeMethod(alwaysRun = true, onlyForGroups = "addressData")
	public String[] getRandomAddressData() throws IOException {
		randomRow = CSVUtils.getRandomRowFromCSVFile("src/test/resources/addresses.csv");
		return randomRow;
	}

	@BeforeMethod(alwaysRun = true)
	public void logOut(Method method) {
		try {
			if (method.getName().contains("Checkout_01") || method.getName().contains("Checkout_05")) {
				homepage.clickCustomerNameDropdown(driver);
				homepage.clickSignOutDropdownLink(driver);
			}
		} catch (Exception e) {
			System.err.println("Error logging out: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Test(groups = "addProductToCart", description = "Verify the elements displayed when user is not logged in and fills in the 'Email Address' field with the email linked to their account")
	public void Checkout_01_Elements_Displayed_When_User_Is_Logged_Out_And_Fills_In_Email_Linked_To_Account() {
		productActions.addRandomProductWithoutOptionsToCart();

		homepage.clickShoppingCartIcon(driver);
		checkoutPage = homepage.clickProceedToCheckoutButton(driver);
		checkoutPage.sendKeysToEmailAddressTextbox(email);

		Assert.assertTrue(checkoutPage.isPasswordTextboxDisplayed());
		Assert.assertTrue(checkoutPage.isLoginButtonDisplayed());
		Assert.assertTrue(checkoutPage.isForgotYourPasswordLinkDisplayed());
	}

	@Test(description = "Verify the display of the shipping address card when selected")
	public void Checkout_02_Display_Of_Shipping_Address_Card_When_Selected() {
		homepage.clickShoppingCartIcon(driver);
		checkoutPage = homepage.clickProceedToCheckoutButton(driver);
		checkoutPage.clickShipHereButtonByStreetAddress("22 Kangaroo Rd, Level 3, Central Business District");

		Assert.assertTrue(checkoutPage
				.isAddressCardByStreetAddressSelected("22 Kangaroo Rd, Level 3, Central Business District"));
	}

	@Test(groups = "addressData", description = "Verify that user can add a new shipping address")
	public void Checkout_03_Add_Shipping_Address() {
		homepage.clickShoppingCartIcon(driver);
		checkoutPage = homepage.clickProceedToCheckoutButton(driver);
		checkoutPage.clickNewAddressButton();
		String streetAddress = data.getAddress();
		checkoutPage.sendKeysToStreetAddressTextbox(streetAddress);
		checkoutPage.sendKeysToCityTextbox(data.getCity());
		checkoutPage.selectOptionStateProvinceDropdown(data.getState());
		checkoutPage.sendKeysToZipPostalCodeTextbox(data.getZipCode());
		checkoutPage.selectOptionCountryDropdown("United States");
		checkoutPage.sendKeysToPhoneNumberTextbox(data.getPhoneNumber());
		checkoutPage.clickShippingAddressPopupShipHereButton();

		Assert.assertTrue(checkoutPage.isShippingAddressDisplayed(streetAddress));
	}

	@Test(description = "Verify that user is directed to the 'Review & Payments' step when clicking the 'Next' button")
	public void Checkout_04_Click_Next_Button() {
		homepage.clickShoppingCartIcon(driver);
		checkoutPage = homepage.clickProceedToCheckoutButton(driver);
		checkoutPage.selectShippingMethodRadioButtonByLabel("Fixed");
		checkoutPage.clickNextButton();

		Assert.assertTrue(checkoutPage.isUserOnReviewAndPaymentStep());
	}

	@Test(description = "Verify the warning message displayed when user clicks the 'Next' button without selecting any shipping option")
	public void Checkout_05_Click_Next_Button_Without_Selecting_Shipping_Option() {
		productActions.addRandomProductWithoutOptionsToCart();

		homepage.clickShoppingCartIcon(driver);
		checkoutPage = homepage.clickProceedToCheckoutButton(driver);
		checkoutPage.sendKeysToEmailAddressTextbox(data.getEmailAddress());
		checkoutPage.sendKeysToFirstNameTextbox(data.getFirstName());
		checkoutPage.sendKeysToLastNameTextbox(data.getLastName());
		checkoutPage.sendKeysToStreetAddressTextbox(data.getAddress());
		checkoutPage.sendKeysToCityTextbox(data.getCity());
		checkoutPage.selectOptionStateProvinceDropdown(data.getState());
		checkoutPage.sendKeysToZipPostalCodeTextbox(data.getZipCode());
		checkoutPage.selectOptionCountryDropdown("United States");
		checkoutPage.sendKeysToPhoneNumberTextbox(data.getPhoneNumber());
		checkoutPage.clickNextButton();

		Assert.assertEquals(checkoutPage.getShippingMethodNotSelectedWarningMessage(),
				"The shipping method is missing. Select the shipping method and try again.");
	}

	@AfterMethod(alwaysRun = true, onlyForGroups = "clearCart")
	public void clearCart(Method method) {
		if (Arrays.asList(method.getAnnotation(Test.class).groups()).contains("clearCart")) {
			try {
				productActions.clearShoppingCart();
			} catch (Exception e) {
				System.err.println("Error clearing the shopping cart: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	@AfterMethod(alwaysRun = true)
	public void logIn(Method method) {
		try {
			if (method.getName().contains("Checkout_01")) {
				homepage = checkoutPage.clickLumaLogo(driver);
				customerLoginPage = homepage.clickSignInLink();
				homepage = customerLoginPage.logInAsRegisteredUser(email, password);
			}
		} catch (Exception e) {
			System.err.println("Error logging in: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@AfterMethod(alwaysRun = true)
	public void navigateToHomepage() {
		try {
			homepage = checkoutPage.clickLumaLogo(driver);
		} catch (Exception e) {
			System.err.println("Error clearing the shopping cart: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		productActions.clearShoppingCart();
		closeBrowserAndDriver();
	}

	private WebDriver driver;
	private FakeDataUtils data;
	private String streetAddress, city, state, zipCode, phoneNumber, fileName, email, password;
	private String[] randomRow;
	private HomepageObject homepage;
	private CustomerLoginPageObject customerLoginPage;
	private ProductListingPageObject productListingPage;
	private ProductDetailsPageObject productDetailsPage;
	private ShoppingCartPageObject shoppingCartPage;
	private CheckoutPageObject checkoutPage;
	private com.magento.commons.Products productActions;
}
