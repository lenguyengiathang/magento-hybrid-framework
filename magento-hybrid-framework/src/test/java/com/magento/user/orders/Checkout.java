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

import com.fasterxml.jackson.databind.JsonNode;

import commons.BaseTest;
import commons.PageGeneratorManager;
import pageObjects.CheckoutPageObject;
import pageObjects.CustomerLoginPageObject;
import pageObjects.HomepageObject;
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

		data = FakeDataUtils.getDataHelper();
		fileName = "login_data.json";
		email = JsonUtils.getJsonValue(fileName, "existing_user.email");
		password = JsonUtils.getJsonValue(fileName, "existing_user.password");
		resultNode = JsonUtils.getRandomProductWithOptions("men_products.json");
		category = resultNode.get("category").asText();
		subcategory = resultNode.get("subcategory").asText();
		productName = resultNode.get("product").get("product_name").asText();
		productSize = resultNode.get("size").asText();
		productColor = resultNode.get("color").asText();

		customerLoginPage = homepage.clickSignInLink();
		homepage = customerLoginPage.logInAsRegisteredUser(email, password);
		productListingPage = homepage.clickNavigationBarDropdownMultiLevelItemLinkByLabels(driver, "Men", category,
				subcategory);
		productListingPage.clickSizeButtonByProductNameAndLabel(driver, productName, productSize);
		productListingPage.clickColorButtonByProductNameAndLabel(driver, productName, productColor);
		productListingPage.clickAddToCartButtonByProductName(driver, productName);
		homepage = productListingPage.clickLumaLogo(driver);
	}

	@BeforeMethod(alwaysRun = true, onlyForGroups = "addressData")
	public String[] getRandomAddressData() throws IOException {
		randomRow = CSVUtils.getRandomRowFromCSVFile("src/test/resources/addresses.csv");
		return randomRow;
	}

	@BeforeMethod(alwaysRun = true)
	public void logOut(Method method) {
		if (method.getName().contains("Checkout_01") || method.getName().contains("Checkout_05")) {
			homepage.clickCustomerNameDropdown(driver);
			homepage.clickSignOutDropdownLink(driver);
			homepage.refreshCurrentPage(driver);
		}
	}

	@Test(groups = "addProductToCart", description = "Verify the elements displayed when user is not logged in and fills in the 'Email Address' field with the email linked to their account")
	public void Checkout_01_Elements_Displayed_When_User_Is_Logged_Out_And_Fills_In_Email_Linked_To_Account() {
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

	@Test(description = "Verify that the 'Table Rate - Best Way' shipping option is only available when the shipping country is the United States")
	public void Checkout_06_Table_Rate_Shipping_Option_Only_Available_For_United_States() {
		homepage.clickShoppingCartIcon(driver);
		checkoutPage = homepage.clickProceedToCheckoutButton(driver);
		checkoutPage.selectOptionCountryDropdown("United States");

		Assert.assertTrue(checkoutPage.isTableRateRadioButtonDisplayed());

		checkoutPage.selectOptionCountryDropdown("Vietnam");

		Assert.assertFalse(checkoutPage.isTableRateRadioButtonDisplayed());
	}

	@Test(description = "Verify that user is directed to the 'Shipping' step when clicking the 'Shipping' label")
	public void Checkout_07_Click_Shipping_Label() {
		homepage.clickShoppingCartIcon(driver);
		checkoutPage = homepage.clickProceedToCheckoutButton(driver);
		checkoutPage.selectShippingMethodRadioButtonByLabel("Fixed");
		checkoutPage.clickNextButton();
		checkoutPage.clickShippingStepLabel();

		Assert.assertTrue(checkoutPage.isUserOnShippingStep());
	}

	@Test(description = "Verify that the billing address matches the shipping address when the 'My billing and shipping address are the same' checkbox is selected")
	public void Checkout_08_Select_My_Billing_And_Shipping_Are_The_Same_Checkbox() {
		homepage.clickShoppingCartIcon(driver);
		checkoutPage = homepage.clickProceedToCheckoutButton(driver);
		checkoutPage.selectShippingMethodRadioButtonByLabel("Fixed");
		checkoutPage.clickNextButton();
		checkoutPage.checkMyBillingAndShippingAddressAreTheSameCheckbox();
		String billingAddress = checkoutPage.getBillingAddress();
		String shippingAddress = checkoutPage.getShippingAddress();

		Assert.assertEquals(billingAddress, shippingAddress);
	}

	@Test(description = "Verify that user can select a different billing address when deselecting the 'My billing and shipping address are the same' checkbox")
	public void Checkout_09_Select_Different_Billing_Address() {
		homepage.clickShoppingCartIcon(driver);
		checkoutPage = homepage.clickProceedToCheckoutButton(driver);
		checkoutPage.selectShippingMethodRadioButtonByLabel("Fixed");
	}

	@Test(description = "Verify that the 'My billing and shipping address are the same' checkbox is selected when user clicks the 'Cancel' hyperlink")
	public void Checkout_10_My_Billing_And_Shipping_Are_The_Same_Checkbox_Is_Selected_When_Clicking_Cancel_Link() {

	}

	@Test(description = "Verify that user can add a new billing address")
	public void Checkout_11_Add_New_Billing_Address() {

	}

	@Test(description = "Verify the display of the success message when user applies a discount code")
	public void Checkout_12_Discount_Code_Applied_Success_Message() {

	}

	@Test(description = "Verify the display of the success message when user removes the discount code")
	public void Checkout_13_Discount_Code_Removed_Success_Message() {

	}

	@Test(description = "Verify the display of the error message when an invalid discount code is used")
	public void Checkout_14_Invalid_Discount_Code_Error_Message() {

	}

	@Test(description = "Verify that user is directed to the 'Shipping' step when clicking the pen icon in the 'Ship To' section")
	public void Checkout_15_Click_Pen_Icon_Ship_To_Section() {

	}

	@Test(description = "Verify that user is directed to the 'Shipping' step when clicking the pen icon in the 'Shipping Method' section")
	public void Checkout_16_Click_Pen_Icon_Shipping_Method_Section() {

	}

	@AfterMethod(alwaysRun = true, onlyForGroups = "clearCart")
	public void clearCart(Method method) {
		if (Arrays.asList(method.getAnnotation(Test.class).groups()).contains("clearCart")) {
			shoppingCartPage.clearShoppingCart(driver);
			homepage = shoppingCartPage.clickHereToContinueShoppingLink();
		}
	}

	@AfterMethod(alwaysRun = true)
	public void logIn(Method method) {
		if (method.getName().contains("Checkout_01")) {
			homepage = checkoutPage.clickLumaLogo(driver);
			customerLoginPage = homepage.clickSignInLink();
			homepage = customerLoginPage.logInAsRegisteredUser(email, password);
		}
	}

	@AfterMethod(alwaysRun = true)
	public void navigateToHomepage() {
		homepage = checkoutPage.clickLumaLogo(driver);
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeBrowserAndDriver();
	}

	private WebDriver driver;
	private FakeDataUtils data;
	private JsonNode resultNode;
	private String category, subcategory, productName, productSize, productColor, streetAddress, city, state, zipCode,
			phoneNumber, fileName, email, password;
	private String[] randomRow;
	private HomepageObject homepage;
	private CustomerLoginPageObject customerLoginPage;
	private ProductListingPageObject productListingPage;
	private ShoppingCartPageObject shoppingCartPage;
	private CheckoutPageObject checkoutPage;
}
