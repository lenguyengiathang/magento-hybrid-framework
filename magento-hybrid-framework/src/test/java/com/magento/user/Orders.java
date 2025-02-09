package com.magento.user;

import java.io.IOException;
import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.magento.commons.Access;
import com.magento.commons.Products;

import commons.BaseTest;
import commons.PageGeneratorManager;
import pageObjects.AddressPageObject;
import pageObjects.CheckoutPageObject;
import pageObjects.CustomerLoginPageObject;
import pageObjects.HomepageObject;
import pageObjects.MyAccountPageObject;
import pageObjects.MyOrdersPageObject;
import pageObjects.OrderDetailsPageObject;
import pageObjects.ProductDetailsPageObject;
import pageObjects.ProductListingPageObject;
import pageObjects.SelectShippingMethodPageObject;
import pageObjects.ShipToMultipleAddressesPageObject;
import pageObjects.ShoppingCartPageObject;
import utilities.CSVUtils;

public class Orders extends BaseTest {
	@Parameters("browser")
	@BeforeClass
	public void beforeClass(String browser) {
		driver = getBrowserDriver(browser);
		homepage = PageGeneratorManager.getHomepage(driver);
		accessActions = new Access(driver);
		productActions = new com.magento.commons.Products(driver);

		todaysDateNumeric = getTodaysDateNumeric();
		todaysDateTextual = getTodaysDateTextual(todaysDateNumeric);
	}

	@BeforeMethod
	public void addProductToCart(ITestResult result) {
		String[] groups = result.getMethod().getGroups();
		Random random = new Random();

		for (String group : groups) {
			switch (group) {
			case "addProductWithoutOptions":
				productActions.addRandomProductWithoutOptionsToCart();
				break;
			case "addProductWithOptions":
				String groupChoice = random.nextBoolean() ? "Men" : "Women";
				productActions.addRandomProductWithOptionsToCart(groupChoice);
				break;
			default:
				continue;
			}
			productName = Products.productName;
			System.out.println("The @BeforeMethod executed successfully: " + productName
					+ " is added to the shopping cart successfully.");
			break;
		}
		homepage.clickLumaLogo(driver);
	}

	@BeforeMethod(onlyForGroups = "addressData")
	public String[] getRandomAddressData() throws IOException {
		randomRow = CSVUtils.getRandomRowFromCSVFile("src/test/resources/addresses.csv");
		return randomRow;
	}

	@Test(groups = "productListing")
	public void Warning_Message_Adding_Product_With_Options_To_Shopping_Cart() {

	}

	@Test(groups = "promotion")
	public void Apply_Twenty_Percent_Discount_For_Order_Equal_To_Or_Greater_Than_200() {
		productDetailsPage = productListingPage.clickProductLinkByProductName(driver, "Driven Backpack");
		productDetailsPage.sendKeysToQuantityTextbox("10");
		productDetailsPage.clickAddToCartButton();
		productDetailsPage.clickShoppingCartIcon(driver);
		shoppingCartPage = productDetailsPage.clickViewAndEditCartLink(driver);
		Float subtotal = shoppingCartPage.getOrderSubtotal();
		Float total = shoppingCartPage.getOrderTotal();

		Assert.assertEquals(subtotal * 0.8, total, 0.01);
	}

	@Test(groups = "promotion")
	public void Apply_Twenty_Percent_Discount_With_Code_20poff() {
		productDetailsPage = productListingPage.clickProductLinkByProductName(driver, "Driven Backpack");
		productDetailsPage.clickAddToCartButton();
		productDetailsPage.clickShoppingCartIcon(driver);
		shoppingCartPage = productDetailsPage.clickViewAndEditCartLink(driver);
		shoppingCartPage.clickApplyDiscountCodeHeader();
		shoppingCartPage.sendKeysToEnterDiscountCodeTextbox("20poff");
		shoppingCartPage.clickApplyDiscountButton();
		Float subtotal = shoppingCartPage.getOrderSubtotal();
		Float total = shoppingCartPage.getOrderTotal();

		Assert.assertEquals(subtotal * 0.8, total, 0.01);
	}

	@Test(groups = "promotion")
	public void Discount_Code_Applied_Success_Message() {
		homepage.clickShoppingCartIcon(driver);
		shoppingCartPage = homepage.clickViewAndEditCartLink(driver);
		shoppingCartPage.clickApplyDiscountCodeHeader();
		shoppingCartPage.sendKeysToEnterDiscountCodeTextbox("20poff");
		shoppingCartPage.clickApplyDiscountButton();

		Assert.assertEquals(shoppingCartPage.getDiscountCodeAppliedSuccessMessage(),
				"You used coupon code \"20poff\".");
	}

	@Test(groups = "promotion", dependsOnMethods = "Discount_Code_Applied_Success_Message", description = "Verify the display of the success message when user removes a discount code")
	public void Discount_Code_Removed_Success_Message() {
		shoppingCartPage.clickCancelCouponButton();

		Assert.assertEquals(shoppingCartPage.getDiscountCodeRemovedSuccessMessage(), "You canceled the coupon code.");
	}

	@Test(groups = "promotion", description = "Verify the display of the error message when an invalid discount code is used")
	public void Invalid_Discount_Code_Error_Message() {
		homepage.clickShoppingCartIcon(driver);
		shoppingCartPage = homepage.clickViewAndEditCartLink(driver);
		shoppingCartPage.clickApplyDiscountCodeHeader();
		shoppingCartPage.sendKeysToEnterDiscountCodeTextbox("invalid");
		shoppingCartPage.clickApplyDiscountButton();

		Assert.assertEquals(shoppingCartPage.getInvalidDiscountCodeErrorMessage(),
				"The coupon code \"invalid\" is not valid.");
	}

	@Test(groups = "promotion", description = "Verify that shipping is free with table rate shipping when the order total is $50 or more")
	public void Free_Shipping_For_Order_Equal_To_Or_Greater_Than_50() {
		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear", "Bags");
		productListingPage.clickAddToCartButtonByProductName(driver, "Compete Track Tote");
		shoppingCartPage = productListingPage.clickShoppingCartLinkSuccessMessage();
		shoppingCartPage.sendKeysToQuantityTextboxByProductName("2", "Compete Track Tote");
		shoppingCartPage.clickUpdateShoppingCartButton();

		Assert.assertEquals(shoppingCartPage.getOrderShipping(), "$0.00");
	}

	@Test(groups = "promotion", description = "Verify that user can purchase 4 tees of the same product for the price of 3")
	public void Four_Tees_For_The_Price_Of_Three() {
		productListingPage = homepage.clickNavigationBarDropdownMultiLevelItemLinkByLabels(driver, "Women", "Tops",
				"Tees");
		productDetailsPage = productListingPage.clickProductLinkByProductName(driver, "Desiree Fitness Tee");
		productDetailsPage.clickSizeButtonByLabel("S");
		productDetailsPage.clickColorButtonByLabel("Black");
		productDetailsPage.sendKeysToQuantityTextbox("4");
		Float price = productDetailsPage.getProductFinalPrice();
		productDetailsPage.clickAddToCartButton();
		productDetailsPage.clickShoppingCartIcon(driver);
		shoppingCartPage = productDetailsPage.clickViewAndEditCartLink(driver);

		Assert.assertEquals(shoppingCartPage.getOrderSubtotal(), price * 4);
		Assert.assertEquals(shoppingCartPage.getOrderDiscount(), price);
		Assert.assertEquals(shoppingCartPage.getOrderTotal(), price * 3);
	}

	@Test(groups = "promotion", description = "Verify that the order total is displayed correctly when multiple promotions are applied to the order")
	public void Multiple_Promotions_Applied_To_Order() {
		productListingPage = homepage.clickNavigationBarDropdownMultiLevelItemLinkByLabels(driver, "Women", "Tops",
				"Tees");
		productDetailsPage = productListingPage.clickProductLinkByProductName(driver, "Desiree Fitness Tee");
		productDetailsPage.clickSizeButtonByLabel("S");
		productDetailsPage.clickColorButtonByLabel("Black");
		productDetailsPage.sendKeysToQuantityTextbox("4");
		Float price = productDetailsPage.getProductFinalPrice();
		productDetailsPage.clickAddToCartButton();
		productDetailsPage.clickShoppingCartIcon(driver);
		shoppingCartPage = productDetailsPage.clickViewAndEditCartLink(driver);

		Assert.assertEquals(shoppingCartPage.getOrderSubtotal(), price * 4);
		Assert.assertEquals(shoppingCartPage.getOrderDiscount(), price);
		Assert.assertEquals(shoppingCartPage.getOrderTotal(), price * 3);
		Assert.assertEquals(shoppingCartPage.getOrderShipping(), "$0.00");

		Float orderTotal = shoppingCartPage.getOrderTotal();
		shoppingCartPage.clickApplyDiscountCodeHeader();
		shoppingCartPage.sendKeysToEnterDiscountCodeTextbox("20poff");
		shoppingCartPage.clickApplyDiscountButton();

		Assert.assertEquals(shoppingCartPage.getOrderTotal(), orderTotal * 0.8);

		orderTotal = shoppingCartPage.getOrderTotal();
		shoppingCartPage.sendKeysToQuantityTextboxByProductName("10", "Desiree Fitness Tee");
		shoppingCartPage.clickUpdateShoppingCartButton();

		Assert.assertEquals(shoppingCartPage.getOrderTotal(), orderTotal * 0.8);
	}

	@Test(groups = "checkout", description = "Verify the elements displayed when user is not logged in and fills in the 'Email Address' field with the email linked to their account")
	public void Elements_Displayed_When_User_Fills_In_Email_Linked_To_Account() {

	}

	@Test(description = "Verify the display of the shipping address card when selected")
	public void Display_Of_Shipping_Address_Card_When_Selected() {

	}

	@Test(description = "Verify that user can add a new shipping address")
	public void Add_Shipping_Address() {
		checkoutPage = shoppingCartPage.clickProceedToCheckoutButton();
		checkoutPage.clickNewAddressButton();
		checkoutPage.sendKeysToStreetAddressTextbox("");
		checkoutPage.sendKeysToCityTextbox("");
		checkoutPage.selectOptionStateProvinceDropdown("");
		checkoutPage.sendKeysToZipPostalCodeTextbox("");
		checkoutPage.selectOptionCountryDropdown("");
		checkoutPage.sendKeysToPhoneNumberTextbox("");
		checkoutPage.clickShippingAddressPopupShipHereButton();

		Assert.assertTrue(checkoutPage.isShippingAddressDisplayed(""));
	}

	@Test(description = "Verify that user is directed to the 'Shopping Cart' page when clicking the 'View and Edit Cart' hyperlink")
	public void Click_View_And_Edit_Cart_Link() {
		shoppingCartPage = checkoutPage.clickViewAndEditCartLink(driver);

		Assert.assertEquals(shoppingCartPage.getPageHeader(driver), "Shopping Cart");
	}

	@Test(description = "Verify that user is directed to the 'Review & Payments' step when clicking the 'Next' button")
	public void Click_Next_Button() {
		checkoutPage.selectShippingMethodRadioButtonByLabel("Fixed");
		checkoutPage.clickNextButton();

		Assert.assertTrue(checkoutPage.isUserOnReviewAndPaymentStep());
	}

	@Test(description = "Verify the warning message displayed when user clicks the 'Next' button without selecting any shipping option")
	public void Click_Next_Button_Without_Selecting_Shipping_Option() {
		checkoutPage.clickNextButton();

		Assert.assertEquals(checkoutPage.getShippingMethodNotSelectedWarningMessage(),
				"The shipping method is missing. Select the shipping method and try again.");
	}

	@Test(description = "Verify that the 'Table Rate - Best Way' shipping option is only available when the shipping country is the United States")
	public void Table_Rate_Shipping_Method_Only_Available_To_United_States() {
		homepage.clickShoppingCartIcon(driver);
		checkoutPage = homepage.clickProceedToCheckoutButton(driver);
		checkoutPage.clickShipHereButtonByStreetAddress("");

		Assert.assertTrue(checkoutPage.isTableRateRadioButtonDisplayed());

		checkoutPage.clickShipHereButtonByStreetAddress("");

		Assert.assertFalse(checkoutPage.isTableRateRadioButtonDisplayed());
	}

	@Test(description = "Verify that user is directed to the 'Shipping' step when clicking the 'Shipping' label")
	public void Click_Shipping_Label() {
		homepage.clickShoppingCartIcon(driver);
		checkoutPage = homepage.clickProceedToCheckoutButton(driver);
		checkoutPage.selectShippingMethodRadioButtonByLabel("Fixed");
		checkoutPage.clickNextButton();
		checkoutPage.clickShippingStepLabel();

		Assert.assertTrue(checkoutPage.isUserOnShippingStep());
	}

	@Test(description = "Verify that the billing address matches the shipping address when the 'My billing and shipping address are the same' checkbox is selected")
	public void Check_My_Billing_And_Shipping_Address_Are_The_Same_Checkbox() {
		homepage.clickShoppingCartIcon(driver);
		checkoutPage = homepage.clickProceedToCheckoutButton(driver);
		String shippingAddress = "";
		checkoutPage.clickShipHereButtonByStreetAddress(shippingAddress);
		checkoutPage.selectShippingMethodRadioButtonByLabel("Fixed");
		checkoutPage.clickNextButton();
		checkoutPage.checkMyBillingAndShippingAddressAreTheSame();

		Assert.assertEquals(checkoutPage.getBillingAddress(), shippingAddress);
	}

	@Test(description = "Verify that user can select a different billing address when deselecting the 'My billing and shipping address are the same' checkbox")
	public void Select_Billing_Address_Different_From_Shipping_Address() {
		homepage.clickShoppingCartIcon(driver);
		checkoutPage = homepage.clickProceedToCheckoutButton(driver);
		String shippingAddress = "";
		checkoutPage.clickShipHereButtonByStreetAddress(shippingAddress);
		checkoutPage.selectShippingMethodRadioButtonByLabel("Fixed");
		checkoutPage.clickNextButton();
		checkoutPage.uncheckMyBillingAndShippingAddressAreTheSameCheckbox();
		checkoutPage.selectOptionBillingAddressDropdown("");
		checkoutPage.clickUpdateButton(driver);

		Assert.assertNotEquals(checkoutPage.getBillingAddress(), shippingAddress);
	}

	@Test(description = "Verify that the 'My billing and shipping address are the same' checkbox is selected when user clicks the 'Cancel' hyperlink")
	public void Click_Cancel_Link() {
		homepage.clickShoppingCartIcon(driver);
		checkoutPage = homepage.clickProceedToCheckoutButton(driver);
		checkoutPage.clickShipHereButtonByStreetAddress("");
		checkoutPage.selectShippingMethodRadioButtonByLabel("Fixed");
		checkoutPage.clickNextButton();
		checkoutPage.uncheckMyBillingAndShippingAddressAreTheSameCheckbox();
		checkoutPage.clickCancelLink();

		Assert.assertTrue(checkoutPage.isMyBillingAndShippingAddressAreTheSameCheckboxChecked());
	}

	@Test(description = "Verify that user can add a new billing address")
	public void Add_New_Billing_Address() {
		homepage.clickShoppingCartIcon(driver);
		checkoutPage = homepage.clickProceedToCheckoutButton(driver);
		checkoutPage.clickShipHereButtonByStreetAddress("");
		checkoutPage.selectShippingMethodRadioButtonByLabel("Fixed");
		checkoutPage.clickNextButton();
		checkoutPage.selectOptionBillingAddressDropdown("New Address");
	}

	@Test(description = "Verify the display of the success message when user applies a discount code")
	public void Orders_076_Discount_Code_Applied_Success_Message() {
		homepage.clickShoppingCartIcon(driver);
		checkoutPage = homepage.clickProceedToCheckoutButton(driver);
		checkoutPage.clickShipHereButtonByStreetAddress("");
		checkoutPage.selectShippingMethodRadioButtonByLabel("Fixed");
		checkoutPage.clickNextButton();
		checkoutPage.clickApplyDiscountCodeHeader();
		checkoutPage.sendKeysToEnterDiscountCodeTextbox("20poff");
		checkoutPage.clickApplyDiscountButton();

		Assert.assertEquals(checkoutPage.getDiscountCodeAppliedSuccessMessage(), "You used coupon code \"20poff\".");
	}

	@Test(description = "Verify the display of the success message when user removes the discount code")
	public void Orders_077_Discount_Code_Removed_Success_Message() {

	}

	@Test(description = "Verify the display of the error message when an invalid discount code is used")
	public void Orders_078_Invalid_Discount_Code_Error_Message() {

	}

	@Test(description = "Verify that user is directed to the 'Shipping' step when clicking the pen icon in the 'Ship To' section")
	public void Orders_079_Click_Pen_Icon_Ship_To_Section() {
		homepage.clickShoppingCartIcon(driver);
		checkoutPage = homepage.clickProceedToCheckoutButton(driver);
		checkoutPage.selectShippingMethodRadioButtonByLabel("Fixed");
		checkoutPage.clickNextButton();
		checkoutPage.clickPenIconShipToSection();

		Assert.assertTrue(checkoutPage.isUserOnShippingStep());
	}

	@Test(description = "Verify that user is directed to the 'Shipping' step when clicking the pen icon in the 'Shipping Method' section")
	public void Orders_080_Click_Pen_Icon_Ship_To_Section() {
		homepage.clickShoppingCartIcon(driver);
		checkoutPage = homepage.clickProceedToCheckoutButton(driver);
		checkoutPage.selectShippingMethodRadioButtonByLabel("Fixed");
		checkoutPage.clickNextButton();
		checkoutPage.clickPenIconShippingMethodSection();

		Assert.assertTrue(checkoutPage.isUserOnShippingStep());
	}

	@Test(description = "Verify that user is directed to the homepage when clicking the 'Continue Shopping' button")
	public void Orders_081_Click_Continue_Shopping_Button() {
		homepage.clickShoppingCartIcon(driver);
		checkoutPage = homepage.clickProceedToCheckoutButton(driver);
		checkoutPage.selectShippingMethodRadioButtonByLabel("Fixed");
		checkoutPage.clickNextButton();
		checkoutPage.clickPlaceOrderButton();
		homepage = checkoutPage.clickContinueShoppingButton();

		Assert.assertEquals(homepage.getPageHeader(driver), "Home Page");
	}

	@Test(description = "Verify that user is directed to the order details page when clicking the order number")
	public void Orders_082_Click_Order_Number() {
		homepage.clickShoppingCartIcon(driver);
		checkoutPage = homepage.clickProceedToCheckoutButton(driver);
		checkoutPage.selectShippingMethodRadioButtonByLabel("Fixed");
		checkoutPage.clickNextButton();
		checkoutPage.clickPlaceOrderButton();
		String orderNumber = checkoutPage.getOrderNumber();
		orderDetailsPage = checkoutPage.clickOrderNumber();

		Assert.assertEquals(orderDetailsPage.getPageHeader(driver), "Order # " + orderNumber);
	}

	@Test(description = "Verify that the order status is 'Pending' after after the order is placed")
	public void Orders_083_Order_Status_Pending() {
		homepage.clickShoppingCartIcon(driver);
		checkoutPage = homepage.clickProceedToCheckoutButton(driver);
		checkoutPage.selectShippingMethodRadioButtonByLabel("Fixed");
		checkoutPage.clickNextButton();
		checkoutPage.clickPlaceOrderButton();
		orderDetailsPage = checkoutPage.clickOrderNumber();

		Assert.assertEquals(orderDetailsPage.getOrderStatus(), "Pending");
	}

	@Test(description = "Verify that the information displayed in the order details page is correct")

	public void Orders_084_Correct_Order_Information() {
		String womenProductName = "Olivia 1/4 Zip Light Jacket";
		String menProductName = "Pierce Gym Short";
		String gearProductName = "Affirm Water Bottle";

		productListingPage = homepage.clickNavigationBarDropdownMultiLevelItemLinkByLabels(driver, "Women", "Tops",
				"Jackets");
		productDetailsPage = productListingPage.clickProductLinkByProductName(driver, womenProductName);
		String womenProductSKU = productDetailsPage.getProductSKU();
		Float womenProductPrice = productDetailsPage.getProductFinalPrice();
		productDetailsPage.clickSizeButtonByLabel("S");
		productDetailsPage.clickColorButtonByLabel("Black");
		productDetailsPage.sendKeysToQuantityTextbox("3");
		productDetailsPage.clickAddToCartButton();
		productListingPage = productDetailsPage.clickNavigationBarDropdownMultiLevelItemLinkByLabels(driver, "Men",
				"Bottoms", "Shorts");
		productDetailsPage = productListingPage.clickProductLinkByProductName(driver, menProductName);
		String menProductSKU = productDetailsPage.getProductSKU();
		Float menProductPrice = productDetailsPage.getProductFinalPrice();
		productDetailsPage.clickSizeButtonByLabel("32");
		productDetailsPage.clickColorButtonByLabel("Red");
		productDetailsPage.sendKeysToQuantityTextbox("2");
		productDetailsPage.clickAddToCartButton();
		productListingPage = productDetailsPage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear",
				"Fitness Equipment");
		productDetailsPage = productListingPage.clickProductLinkByProductName(driver, gearProductName);
		String gearProductSKU = productDetailsPage.getProductSKU();
		Float gearProductPrice = productDetailsPage.getProductFinalPrice();
		productDetailsPage.clickAddToCartButton();
		productDetailsPage.clickShoppingCartIcon(driver);
		shoppingCartPage = productDetailsPage.clickViewAndEditCartLink(driver);
		shoppingCartPage.clickShippingMethodRadioButtonByLabel("Fixed");
		float cartSubtotal = shoppingCartPage.getOrderSubtotal();
		float cartDiscount = shoppingCartPage.getOrderDiscount();
		float cartShipping = shoppingCartPage.getOrderShipping();
		float cartTotal = shoppingCartPage.getOrderTotal();
		checkoutPage = shoppingCartPage.clickProceedToCheckoutButton(driver);
		checkoutPage.selectShippingMethodRadioButtonByLabel("Fixed");
		checkoutPage.clickNextButton();

		float checkoutSubtotal = checkoutPage.getCartSubtotal();
		float checkoutDiscount = checkoutPage.getDiscount();
		float checkoutShipping = checkoutPage.getShipping();
		float checkoutTotal = checkoutPage.getOrderTotal();

		Assert.assertEquals(checkoutSubtotal, cartSubtotal);
		Assert.assertEquals(checkoutDiscount, cartDiscount);
		Assert.assertEquals(checkoutShipping, cartShipping);
		Assert.assertEquals(checkoutTotal, cartTotal);

		checkoutPage.clickPlaceOrderButton();
		String orderNumber = checkoutPage.getOrderNumber();
		orderDetailsPage = checkoutPage.clickOrderNumber();

		Assert.assertTrue(orderDetailsPage.getPageHeader(driver).contains(orderNumber));
		Assert.assertEquals(orderDetailsPage.getOrderDate(), todaysDateTextual);
		Assert.assertEquals(orderDetailsPage.getProductSKUByProductName(womenProductName), womenProductSKU);
		Assert.assertEquals(orderDetailsPage.getProductPriceByProductName(womenProductName), womenProductPrice);
		Assert.assertEquals(orderDetailsPage.getProductQuantityByProductName(womenProductName), "3");
		Assert.assertEquals(orderDetailsPage.getProductSubtotalByProductName(womenProductName), womenProductPrice * 3);
		Assert.assertEquals(orderDetailsPage.getProductSizeByProductName(womenProductName), "S");
		Assert.assertEquals(orderDetailsPage.getProductColorByProductName(womenProductName), "Black");
		Assert.assertEquals(orderDetailsPage.getProductSKUByProductName(menProductName), menProductSKU);
		Assert.assertEquals(orderDetailsPage.getProductPriceByProductName(menProductName), menProductPrice);
		Assert.assertEquals(orderDetailsPage.getProductQuantityByProductName(menProductName), "2");
		Assert.assertEquals(orderDetailsPage.getProductSubtotalByProductName(menProductName), menProductPrice * 2);
		Assert.assertEquals(orderDetailsPage.getProductSizeByProductName(menProductName), "32");
		Assert.assertEquals(orderDetailsPage.getProductColorByProductName(menProductName), "Red");
		Assert.assertEquals(orderDetailsPage.getProductSKUByProductName(gearProductName), gearProductSKU);
		Assert.assertEquals(orderDetailsPage.getProductPriceByProductName(gearProductName), gearProductPrice);
		Assert.assertEquals(orderDetailsPage.getProductQuantityByProductName(gearProductName), "1");
		Assert.assertEquals(orderDetailsPage.getProductSubtotalByProductName(gearProductName), gearProductPrice);
		Assert.assertEquals(orderDetailsPage.getOrderSubtotal(), checkoutSubtotal);
		Assert.assertEquals(orderDetailsPage.getOrderDiscount(), checkoutDiscount);
		Assert.assertEquals(orderDetailsPage.getOrderShippingAndHandling(), checkoutShipping);
		Assert.assertEquals(orderDetailsPage.getOrderGrandTotal(), checkoutTotal);
		Assert.assertEquals(orderDetailsPage.getShippingAddress(), "");
		Assert.assertEquals(orderDetailsPage.getShippingMethod(), "Flat Rate - Fixed");
		Assert.assertEquals(orderDetailsPage.getBillingAddress(), "");

	}

	@Test(description = "Verify the information message displayed on the 'My Orders' page when user has not placed any order")
	public void Orders_085_No_Orders_Placed_Info_Message() {
		homepage.clickCustomerNameDropdown(driver);
		myAccountPage = homepage.clickMyAccountDropdownLink(driver);
		myOrdersPage = (MyOrdersPageObject) myAccountPage.clickMyAccountSidebarLinkByLabel(driver, "My Orders");

		Assert.assertEquals(myOrdersPage.getNoOrdersPlacedInfoMessage(), "You have placed no orders.");
	}

	@Test(description = "Verify that all placed orders are displayed on the 'My Orders' page")
	public void Orders_086_Display_Of_Placed_Orders() {

	}

	@Test(description = "Verify that user is directed to the 'Shopping Cart' page with the previously ordered products automatically added to the shopping cart when clicking the 'Reorder' hyperlink")
	public void Orders_087_Click_Reorder_Link() {

	}

	@Test
	public void Number_Of_Number_Orders_Matches_Number_Of_Shipping_Addresses() {
		shipToMultipleAddressesPage = shoppingCartPage.clickCheckOutWithMultipleAddressesLink();
		shipToMultipleAddressesPage.selectItemSendToDropdownByProductName("", "");
		shipToMultipleAddressesPage.selectItemSendToDropdownByProductName("", "");
		shipToMultipleAddressesPage.selectItemSendToDropdownByProductName("", "");
		selectShippingMethodPage = shipToMultipleAddressesPage.clickGoToShippingInformationButton();
	}

	@Test
	public void Correct_Shipping_Address_For_Each_Order_Number() {

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeBrowserAndDriver();
	}

	private WebDriver driver;
	private String productName, productSize, productColor, todaysDateNumeric, todaysDateTextual;
	private String[] randomRow;
	private HomepageObject homepage;
	private CustomerLoginPageObject customerLoginPage;
	private MyAccountPageObject myAccountPage;
	private MyOrdersPageObject myOrdersPage;
	private ProductListingPageObject productListingPage;
	private ProductDetailsPageObject productDetailsPage;
	private ShoppingCartPageObject shoppingCartPage;
	private CheckoutPageObject checkoutPage;
	private OrderDetailsPageObject orderDetailsPage;
	private ShipToMultipleAddressesPageObject shipToMultipleAddressesPage;
	private SelectShippingMethodPageObject selectShippingMethodPage;
	private AddressPageObject addressPage;
	private com.magento.commons.Access accessActions;
	private com.magento.commons.Products productActions;
}
