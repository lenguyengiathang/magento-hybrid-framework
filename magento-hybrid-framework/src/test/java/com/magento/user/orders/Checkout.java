package com.magento.user.orders;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.BaseTest;
import commons.PageGeneratorManager;
import pageObjects.CheckoutPageObject;
import pageObjects.CustomerLoginPageObject;
import pageObjects.HomepageObject;
import pageObjects.ProductListingPageObject;
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

		customerLoginPage = homepage.clickSignInLink();
		homepage = customerLoginPage.logInAsRegisteredUser(email, password);
		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear", "Watches");
		productListingPage.clickAddToCartButtonByProductName(driver, "Didi Sport Watch");
		homepage = productListingPage.clickLumaLogo(driver);
	}

	@BeforeMethod(alwaysRun = true, onlyForGroups = "logOut")
	public void logOut() {
		homepage.logOut(driver);
	}

	@Test(description = "Verify the display of the shipping address card when selected")
	public void Checkout_01_Display_Of_Shipping_Address_Card_When_Selected() {
		homepage.clickShoppingCartIcon(driver);
		checkoutPage = homepage.clickProceedToCheckoutButton(driver);
		checkoutPage.clickShipHereButtonByStreetAddress("22 Kangaroo Rd, Level 3, Central Business District");

		Assert.assertTrue(checkoutPage
				.isAddressCardByStreetAddressSelected("22 Kangaroo Rd, Level 3, Central Business District"));
	}

	@Test(description = "Verify that user can add a new shipping address")
	public void Checkout_02_Add_Shipping_Address() {
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
	public void Checkout_03_Click_Next_Button() {
		homepage.clickShoppingCartIcon(driver);
		checkoutPage = homepage.clickProceedToCheckoutButton(driver);
		checkoutPage.selectShippingMethodRadioButtonByLabel("Fixed");
		checkoutPage.clickNextButton();

		Assert.assertTrue(checkoutPage.isUserOnReviewAndPaymentStep());
	}

	@Test(groups = "logOut", description = "Verify the warning message displayed when user clicks the 'Next' button without selecting any shipping option")
	public void Checkout_04_Click_Next_Button_Without_Selecting_Shipping_Option() {
		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear", "Bags");
		productListingPage.clickAddToCartButtonByProductName(driver, "Driven Backpack");
		productListingPage.clickShoppingCartIcon(driver);
		checkoutPage = productListingPage.clickProceedToCheckoutButton(driver);
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
	public void Checkout_05_Table_Rate_Shipping_Option_Only_Available_For_United_States() {
		homepage.clickShoppingCartIcon(driver);
		checkoutPage = homepage.clickProceedToCheckoutButton(driver);
		checkoutPage.selectOptionCountryDropdown("United States");

		Assert.assertTrue(checkoutPage.isTableRateRadioButtonDisplayed());

		checkoutPage.selectOptionCountryDropdown("Vietnam");

		Assert.assertFalse(checkoutPage.isTableRateRadioButtonDisplayed());
	}

	@Test(description = "Verify that user is directed to the 'Shipping' step when clicking the 'Shipping' label")
	public void Checkout_06_Click_Shipping_Label() {
		homepage.clickShoppingCartIcon(driver);
		checkoutPage = homepage.clickProceedToCheckoutButton(driver);
		checkoutPage.selectShippingMethodRadioButtonByLabel("Fixed");
		checkoutPage.clickNextButton();
		checkoutPage.clickShippingStepLabel();

		Assert.assertTrue(checkoutPage.isUserOnShippingStep());
	}

	@Test(description = "Verify that the billing address matches the shipping address when the 'My billing and shipping address are the same' checkbox is selected")
	public void Checkout_07_Select_My_Billing_And_Shipping_Are_The_Same_Checkbox() {
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
	public void Checkout_08_Select_Different_Billing_Address() {
		homepage.clickShoppingCartIcon(driver);
		checkoutPage = homepage.clickProceedToCheckoutButton(driver);
		checkoutPage.selectShippingMethodRadioButtonByLabel("Fixed");
		checkoutPage.clickNextButton();
		checkoutPage.checkMyBillingAndShippingAddressAreTheSameCheckbox();
		checkoutPage.uncheckMyBillingAndShippingAddressAreTheSameCheckbox();
		String billingAddress1 = checkoutPage.getSelectedOptionBillingAddressDropdown();
		checkoutPage.selectOptionBillingAddressDropdown("");
		checkoutPage.clickUpdateButton();
		String billingAddress2 = checkoutPage.getSelectedOptionBillingAddressDropdown();

		Assert.assertNotEquals(billingAddress1, billingAddress2);
	}

	@Test(description = "Verify that the 'My billing and shipping address are the same' checkbox is selected when user clicks the 'Cancel' hyperlink")
	public void Checkout_09_My_Billing_And_Shipping_Are_The_Same_Checkbox_Is_Selected_When_Clicking_Cancel_Link() {
		homepage.clickShoppingCartIcon(driver);
		checkoutPage = homepage.clickProceedToCheckoutButton(driver);
		checkoutPage.selectShippingMethodRadioButtonByLabel("Fixed");
		checkoutPage.clickNextButton();
		checkoutPage.checkMyBillingAndShippingAddressAreTheSameCheckbox();
		checkoutPage.uncheckMyBillingAndShippingAddressAreTheSameCheckbox();
		String billingAddress1 = checkoutPage.getSelectedOptionBillingAddressDropdown();
		checkoutPage.clickCancelLink();
		String billingAddress2 = checkoutPage.getSelectedOptionBillingAddressDropdown();

		Assert.assertEquals(billingAddress1, billingAddress2);
	}

	@Test(description = "Verify that user can add a new billing address")
	public void Checkout_10_Add_New_Billing_Address() {
		homepage.clickShoppingCartIcon(driver);
		checkoutPage = homepage.clickProceedToCheckoutButton(driver);
		checkoutPage.selectShippingMethodRadioButtonByLabel("Fixed");
		checkoutPage.clickNextButton();
		checkoutPage.checkMyBillingAndShippingAddressAreTheSameCheckbox();
		checkoutPage.uncheckMyBillingAndShippingAddressAreTheSameCheckbox();
		checkoutPage.selectOptionBillingAddressDropdown("New Address");
		String streetAddress = data.getAddress();
		String city = data.getCity();
		String state = data.getState();
		String zipCode = data.getZipCode();
		String phoneNumber = data.getPhoneNumber();
		checkoutPage.sendKeysToStreetAddressTextbox(streetAddress);
		checkoutPage.sendKeysToCityTextbox(city);
		checkoutPage.selectOptionStateProvinceDropdown(state);
		checkoutPage.sendKeysToZipPostalCodeTextbox(zipCode);
		checkoutPage.sendKeysToPhoneNumberTextbox(phoneNumber);
		checkoutPage.uncheckSaveInAddressBookCheckbox();
		checkoutPage.clickUpdateButton();

		Assert.assertEquals(checkoutPage.getSelectedOptionBillingAddressDropdown(), "Thang Le, " + streetAddress);
	}

	@Test(description = "Verify the display of the success message when user applies a discount code")
	public void Checkout_11_Discount_Code_Applied_Success_Message() {
		homepage.clickShoppingCartIcon(driver);
		checkoutPage = homepage.clickProceedToCheckoutButton(driver);
		checkoutPage.selectShippingMethodRadioButtonByLabel("Fixed");
		checkoutPage.clickNextButton();
		checkoutPage.clickApplyDiscountCodeHeader();
		checkoutPage.sendKeysToEnterDiscountCodeTextbox("20poff");
		checkoutPage.clickApplyDiscountButton();

		Assert.assertEquals(checkoutPage.getDiscountCodeAppliedSuccessMessage(),
				"Your coupon was successfully applied.");
	}

	@Test(description = "Verify the display of the success message when user removes the discount code")
	public void Checkout_12_Discount_Code_Removed_Success_Message() {
		homepage.clickShoppingCartIcon(driver);
		checkoutPage = homepage.clickProceedToCheckoutButton(driver);
		checkoutPage.selectShippingMethodRadioButtonByLabel("Fixed");
		checkoutPage.clickNextButton();
		checkoutPage.clickApplyDiscountCodeHeader();
		checkoutPage.sendKeysToEnterDiscountCodeTextbox("20poff");
		checkoutPage.clickApplyDiscountButton();
		checkoutPage.clickCancelCouponButton();

		Assert.assertEquals(checkoutPage.getDiscountCodeRemovedSuccessMessage(),
				"Your coupon was successfully removed.");
	}

	@Test(description = "Verify the display of the error message when an invalid discount code is used")
	public void Checkout_13_Invalid_Discount_Code_Error_Message() {
		homepage.clickShoppingCartIcon(driver);
		checkoutPage = homepage.clickProceedToCheckoutButton(driver);
		checkoutPage.selectShippingMethodRadioButtonByLabel("Fixed");
		checkoutPage.clickNextButton();
		checkoutPage.clickApplyDiscountCodeHeader();
		checkoutPage.sendKeysToEnterDiscountCodeTextbox("invalid");
		checkoutPage.clickApplyDiscountButton();

		Assert.assertEquals(checkoutPage.getInvalidDiscountCodeErrorMessage(),
				"The coupon code isn't valid. Verify the code and try again.");
	}

	@Test(description = "Verify that user is directed to the 'Shipping' step when clicking the pen icon in the 'Ship To' section")
	public void Checkout_14_Click_Pen_Icon_Ship_To_Section() {
		homepage.clickShoppingCartIcon(driver);
		checkoutPage = homepage.clickProceedToCheckoutButton(driver);
		checkoutPage.selectShippingMethodRadioButtonByLabel("Fixed");
		checkoutPage.clickNextButton();
		checkoutPage.clickPenIconShipToSection();

		Assert.assertTrue(checkoutPage.isUserOnShippingStep());
	}

	@Test(groups = "logOut", description = "Verify that user is directed to the 'Shipping' step when clicking the pen icon in the 'Shipping Method' section")
	public void Checkout_16_Click_Pen_Icon_Shipping_Method_Section() {
		homepage.clickShoppingCartIcon(driver);
		checkoutPage = homepage.clickProceedToCheckoutButton(driver);
		checkoutPage.selectShippingMethodRadioButtonByLabel("Fixed");
		checkoutPage.clickNextButton();
		checkoutPage.clickPenIconShippingMethodSection();

		Assert.assertTrue(checkoutPage.isUserOnShippingStep());
	}

	@Test(description = "Verify the elements displayed when user is not logged in and fills in the 'Email Address' field with the email linked to their account")
	public void Checkout_17_Elements_Displayed_When_User_Is_Logged_Out_And_Fills_In_Email_Linked_To_Account() {
		productListingPage = homepage.clickNavigationBarDropdownMultiLevelItemLinkByLabels(driver, "Men", "Gear",
				"Fitness Equipment");
		productListingPage.clickAddToCartButtonByProductName(driver, "Sprite Foam Yoga Brick");
		productListingPage.clickShoppingCartIcon(driver);
		checkoutPage = productListingPage.clickProceedToCheckoutButton(driver);
		checkoutPage.sendKeysToEmailAddressTextbox(email);

		Assert.assertTrue(checkoutPage.isPasswordTextboxDisplayed());
		Assert.assertTrue(checkoutPage.isLoginButtonDisplayed());
		Assert.assertTrue(checkoutPage.isForgotYourPasswordLinkDisplayed());
	}

	@AfterMethod(alwaysRun = true)
	public void navigateToHomepage() {
		homepage.navigateToHomepage(driver);
	}

	@AfterMethod(alwaysRun = true, onlyForGroups = "clearCart")
	public void clearShoppingCart() {
		homepage.clearShoppingCart(driver);
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeBrowserAndDriver();
	}

	private WebDriver driver;
	private FakeDataUtils data;
	private String fileName, email, password;
	private HomepageObject homepage;
	private CustomerLoginPageObject customerLoginPage;
	private ProductListingPageObject productListingPage;
	private CheckoutPageObject checkoutPage;
}
