package com.magento.user.orders;

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
import pageObjects.BillingInformationPageObject;
import pageObjects.CheckoutPageObject;
import pageObjects.CreateNewCustomerAccountPageObject;
import pageObjects.CustomerLoginPageObject;
import pageObjects.HomepageObject;
import pageObjects.MyAccountPageObject;
import pageObjects.MyOrdersPageObject;
import pageObjects.OrderDetailsPageObject;
import pageObjects.OrderSuccessPageObject;
import pageObjects.OrdersAndReturnsPageObject;
import pageObjects.ProductDetailsPageObject;
import pageObjects.ProductListingPageObject;
import pageObjects.ReviewOrderPageObject;
import pageObjects.SelectShippingMethodPageObject;
import pageObjects.ShipToMultipleAddressesPageObject;
import pageObjects.ShoppingCartPageObject;
import utilities.FakeDataUtils;
import utilities.JsonUtils;

public class OrderPlacement extends BaseTest {
	@Parameters("browser")
	@BeforeClass
	public void beforeClass(String browser) {
		driver = getBrowserDriver(browser);
		homepage = PageGeneratorManager.getHomepage(driver);

		data = FakeDataUtils.getDataHelper();
		fileName = "login_data.json";
		email = JsonUtils.getJsonValue(fileName, "existing_user.email");
		password = JsonUtils.getJsonValue(fileName, "existing_user.password");
		defaultBillingAddress = JsonUtils.getJsonValue(fileName, "existing_user.default_billing");
		defaultShippingAddress = JsonUtils.getJsonValue(fileName, "existing_user.default_shipping");
		todaysDateNumeric = getTodaysDateNumeric();
		todaysDateTextual = getTodaysDateTextual(todaysDateNumeric);

		customerLoginPage = homepage.clickSignInLink();
		homepage = customerLoginPage.logInAsRegisteredUser(email, password);
	}

	@BeforeMethod(alwaysRun = true)
	public void getProducts() {
		gearResultNode = JsonUtils.getRandomProductNoOptions("gear_products.json");
		gearCategory = gearResultNode.get("category").asText();
		gearProductName = gearResultNode.get("product").get("product_name").asText();

		menResultNode = JsonUtils.getRandomProductWithOptions("men_products.json");
		menCategory = menResultNode.get("category").asText();
		menSubcategory = menResultNode.get("subcategory").asText();
		menProductName = menResultNode.get("product").get("product_name").asText();
		menProductSize = menResultNode.get("size").asText();
		menProductColor = menResultNode.get("color").asText();

		womenResultNode = JsonUtils.getRandomProductWithOptions("women_products.json");
		womenCategory = womenResultNode.get("category").asText();
		womenSubcategory = womenResultNode.get("subcategory").asText();
		womenProductName = womenResultNode.get("product").get("product_name").asText();
		womenProductSize = womenResultNode.get("size").asText();
		womenProductColor = womenResultNode.get("color").asText();
	}

	@BeforeMethod(onlyForGroups = "logOut")
	public void logOut() {
		homepage.logOut(driver);
	}

	@Test(description = "Verify that user is directed to the homepage when clicking the 'Continue Shopping' button")
	public void Order_Placement_01_Click_Continue_Shopping_Button() {
		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear",
				gearCategory);
		productListingPage.clickAddToCartButtonByProductName(driver, gearProductName);
		productListingPage.clickShoppingCartIcon(driver);
		checkoutPage = productListingPage.clickProceedToCheckoutButton(driver);
		checkoutPage.selectShippingMethodRadioButtonByLabel("Fixed");
		checkoutPage.clickNextButton();
		orderSuccessPage = checkoutPage.clickPlaceOrderButton();
		homepage = orderSuccessPage.clickContinueShoppingButton();

		Assert.assertEquals(homepage.getPageHeader(driver), "Home Page");
	}

	@Test(description = "Verify that user is directed to the order details page when clicking the order number")
	public void Order_Placement_02_Click_Order_Number() {
		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear",
				gearCategory);
		productListingPage.clickAddToCartButtonByProductName(driver, gearProductName);
		productListingPage.clickShoppingCartIcon(driver);
		checkoutPage = productListingPage.clickProceedToCheckoutButton(driver);
		checkoutPage.selectShippingMethodRadioButtonByLabel("Fixed");
		checkoutPage.clickNextButton();
		orderSuccessPage = checkoutPage.clickPlaceOrderButton();
		String orderNumber = orderSuccessPage.getOrderNumber();
		orderDetailsPage = orderSuccessPage.clickOrderNumber();

		Assert.assertEquals(orderDetailsPage.getPageHeader(driver), "Order # " + orderNumber);
	}

	@Test(description = "Verify that the order status is 'Pending' after after the order is placed")
	public void Order_Placement_03_Order_Status_Pending() {
		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear",
				gearCategory);
		productListingPage.clickAddToCartButtonByProductName(driver, gearProductName);
		productListingPage.clickShoppingCartIcon(driver);
		checkoutPage = productListingPage.clickProceedToCheckoutButton(driver);
		checkoutPage.selectShippingMethodRadioButtonByLabel("Fixed");
		checkoutPage.clickNextButton();
		orderSuccessPage = checkoutPage.clickPlaceOrderButton();
		orderDetailsPage = orderSuccessPage.clickOrderNumber();

		Assert.assertEquals(orderDetailsPage.getOrderStatus(), "PENDING");
	}

	@Test(description = "Verify that the information displayed in the order details page is correct")
	public void Order_Placement_04_Correct_Order_Information() {
		productListingPage = homepage.clickNavigationBarDropdownMultiLevelItemLinkByLabels(driver, "Women",
				womenCategory, womenSubcategory);
		productDetailsPage = productListingPage.clickProductLinkByProductName(driver, womenProductName);
		String womenProductSKU = productDetailsPage.getProductSKU();
		float womenProductPrice = productDetailsPage.getProductFinalPrice();
		productDetailsPage.clickSizeButtonByLabel(womenProductSize);
		productDetailsPage.clickColorButtonByLabel(womenProductColor);
		productDetailsPage.sendKeysToQuantityTextbox("5");
		productDetailsPage.clickAddToCartButton();

		productListingPage = productDetailsPage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear",
				gearCategory);
		productDetailsPage = productListingPage.clickProductLinkByProductName(driver, gearProductName);
		String gearProductSKU = productDetailsPage.getProductSKU();
		float gearProductPrice = productDetailsPage.getProductFinalPrice();
		productDetailsPage.clickAddToCartButton();

		productDetailsPage.clickShoppingCartIcon(driver);
		shoppingCartPage = productDetailsPage.clickViewAndEditCartLink(driver);
		shoppingCartPage.clickEstimateShippingAndTaxHeader();
		shoppingCartPage.clickShippingMethodRadioButtonByLabel("Fixed");
		shoppingCartPage.clickEstimateShippingAndTaxHeader();
		float cartSubtotal = shoppingCartPage.getOrderSubtotal();
		float cartDiscount = shoppingCartPage.getOrderDiscount();
		float cartShipping = shoppingCartPage.getOrderShipping();
		float cartTotal = shoppingCartPage.getOrderTotal();
		checkoutPage = shoppingCartPage.clickProceedToCheckoutButton();
		checkoutPage.clickNextButton();

		float checkoutSubtotal = checkoutPage.getCartSubtotal();
		float checkoutDiscount = checkoutPage.getDiscount();
		float checkoutShipping = checkoutPage.getShipping();
		float checkoutTotal = checkoutPage.getOrderTotal();

		Assert.assertEquals(checkoutSubtotal, cartSubtotal);
		Assert.assertEquals(checkoutDiscount, cartDiscount);
		Assert.assertEquals(checkoutShipping, cartShipping);
		Assert.assertEquals(checkoutTotal, cartTotal);

		orderSuccessPage = checkoutPage.clickPlaceOrderButton();
		String orderNumber = orderSuccessPage.getOrderNumber();
		orderDetailsPage = orderSuccessPage.clickOrderNumber();

		Assert.assertTrue(orderDetailsPage.getPageHeader(driver).contains(orderNumber));
		Assert.assertEquals(orderDetailsPage.getOrderDate(), todaysDateTextual);
		Assert.assertEquals(orderDetailsPage.getProductSKUByProductName(womenProductName),
				womenProductSKU + "-" + womenProductSize + "-" + womenProductColor);
		Assert.assertEquals(orderDetailsPage.getProductPriceByProductName(womenProductName), womenProductPrice);
		Assert.assertEquals(orderDetailsPage.getProductQuantityByProductName(womenProductName), "5");
		Assert.assertEquals(orderDetailsPage.getProductSubtotalByProductName(womenProductName), womenProductPrice * 5);
		Assert.assertEquals(orderDetailsPage.getProductSizeByProductName(womenProductName), womenProductSize);
		Assert.assertEquals(orderDetailsPage.getProductColorByProductName(womenProductName), womenProductColor);

		Assert.assertEquals(orderDetailsPage.getProductSKUByProductName(gearProductName), gearProductSKU);
		Assert.assertEquals(orderDetailsPage.getProductPriceByProductName(gearProductName), gearProductPrice);
		Assert.assertEquals(orderDetailsPage.getProductQuantityByProductName(gearProductName), "1");
		Assert.assertEquals(orderDetailsPage.getProductSubtotalByProductName(gearProductName), gearProductPrice);

		Assert.assertEquals(orderDetailsPage.getOrderSubtotal(), checkoutSubtotal);
		Assert.assertEquals(orderDetailsPage.getOrderDiscount(), checkoutDiscount);
		Assert.assertEquals(orderDetailsPage.getOrderShippingAndHandling(), checkoutShipping);
		Assert.assertEquals(orderDetailsPage.getOrderGrandTotal(), checkoutTotal);
		Assert.assertEquals(orderDetailsPage.getShippingAddress(), defaultShippingAddress);
		Assert.assertEquals(orderDetailsPage.getShippingMethod(), "Flat Rate - Fixed");
		Assert.assertEquals(orderDetailsPage.getBillingAddress(), defaultBillingAddress);
	}

	@Test(description = "Verify that user is directed to the 'Shopping Cart' page with the previously ordered products automatically added to the shopping cart when clicking the 'Reorder' hyperlink")
	public void Order_Placement_05_Click_Reorder_Link() {
		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear",
				gearCategory);
		productListingPage.addProductWithoutOptionsToCart(driver, gearProductName);
		productListingPage.clickNavigationBarDropdownMultiLevelItemLinkByLabels(driver, "Women", womenCategory,
				womenSubcategory);
		productListingPage.addProductWithOptionsToCart(driver, womenProductName, womenProductSize, womenProductColor);
		productListingPage.clickShoppingCartIcon(driver);
		checkoutPage = productListingPage.clickProceedToCheckoutButton(driver);
		checkoutPage.selectShippingMethodRadioButtonByLabel("Fixed");
		checkoutPage.clickNextButton();
		orderSuccessPage = checkoutPage.clickPlaceOrderButton();
		orderDetailsPage = orderSuccessPage.clickOrderNumber();
		shoppingCartPage = orderDetailsPage.clickReorderLink();

		Assert.assertTrue(shoppingCartPage.isProducDisplayedInShoppingCart(gearProductName));
		Assert.assertTrue(shoppingCartPage.isProducDisplayedInShoppingCart(womenProductName));
		Assert.assertEquals(shoppingCartPage.getProductSizeByProductName(womenProductName), womenProductSize);
		Assert.assertEquals(shoppingCartPage.getProductColorByProductName(womenProductName), womenProductColor);
	}

	@Test(description = "Verify that user can add recently ordered products to the shopping cart")
	public void Order_Placement_06_Add_Recently_Ordered_Products_To_Cart() {
		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear",
				gearCategory);
		productListingPage.addProductWithoutOptionsToCart(driver, gearProductName);
		productListingPage.clickNavigationBarDropdownMultiLevelItemLinkByLabels(driver, "Women", womenCategory,
				womenSubcategory);
		productListingPage.addProductWithOptionsToCart(driver, womenProductName, womenProductSize, womenProductColor);
		productListingPage.clickShoppingCartIcon(driver);
		checkoutPage = productListingPage.clickProceedToCheckoutButton(driver);
		checkoutPage.selectShippingMethodRadioButtonByLabel("Fixed");
		checkoutPage.clickNextButton();
		orderSuccessPage = checkoutPage.clickPlaceOrderButton();
		orderSuccessPage.clickCustomerNameDropdown(driver);
		myAccountPage = checkoutPage.clickMyAccountDropdownLink(driver);
		myAccountPage.checkRecentlyOrderedCheckboxByProductName(driver, gearProductName);
		myAccountPage.clickAddToCartButtonRecentlyOrderedSection(driver);

		Assert.assertEquals(myAccountPage.getProductAddedToCartSuccessMessage(driver),
				"You added " + gearProductName + " to your shopping cart.");

		myAccountPage.checkRecentlyOrderedCheckboxByProductName(driver, womenProductName);
		myAccountPage.clickAddToCartButtonRecentlyOrderedSection(driver);
		Assert.assertEquals(myAccountPage.getProductAddedToCartSuccessMessage(driver),
				"You added " + womenProductName + " to your shopping cart.");

		myAccountPage.clickShoppingCartIcon(driver);
		shoppingCartPage = myAccountPage.clickViewAndEditCartLink(driver);

		Assert.assertTrue(shoppingCartPage.isProducDisplayedInShoppingCart(gearProductName));
		Assert.assertTrue(shoppingCartPage.isProducDisplayedInShoppingCart(womenProductName));
	}

	@Test(description = "Verify that an error message is displayed when the user clicks the 'Add to Cart' button without selecting any product checkboxes")
	public void Order_Placement_07_Error_Message_Adding_Recently_Ordered_Product_To_Cart() {
		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear",
				gearCategory);
		productListingPage.addProductWithoutOptionsToCart(driver, gearProductName);
		productListingPage.clickShoppingCartIcon(driver);
		checkoutPage = productListingPage.clickProceedToCheckoutButton(driver);
		checkoutPage.selectShippingMethodRadioButtonByLabel("Fixed");
		checkoutPage.clickNextButton();
		orderSuccessPage = checkoutPage.clickPlaceOrderButton();
		orderSuccessPage.clickCustomerNameDropdown(driver);
		myAccountPage = checkoutPage.clickMyAccountDropdownLink(driver);
		myAccountPage.clickAddToCartButtonRecentlyOrderedSection(driver);

		Assert.assertEquals(myAccountPage.getNoRecentlyOrderedProductSelectedErrorMessage(driver),
				"Please select at least one product to add to cart");
	}

	@Test(description = "Verify that user is automatically scrolled to the 'Recent Orders' section when clicking the 'View All' hyperlink in the 'Recently Ordered' section")
	public void Order_Placement_08_Automatically_Scrolled_To_Recent_Orders_Section_When_Clicking_View_All_Link_In_Recently_Ordered_Section() {
		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear",
				gearCategory);
		productListingPage.addProductWithoutOptionsToCart(driver, gearProductName);
		homepage.clickShoppingCartIcon(driver);
		checkoutPage = homepage.clickProceedToCheckoutButton(driver);
		checkoutPage.selectShippingMethodRadioButtonByLabel("Fixed");
		checkoutPage.clickNextButton();
		orderSuccessPage = checkoutPage.clickPlaceOrderButton();
		orderSuccessPage.clickCustomerNameDropdown(driver);
		myAccountPage = checkoutPage.clickMyAccountDropdownLink(driver);
		myAccountPage.clickViewAllLinkRecentlyOrderedSection(driver);

		Assert.assertTrue(myAccountPage.isRecentOrdersSectionDisplayed(driver));
	}

	@Test(description = "Verify the error message displayed when user fills in the 'Orders and Returns' form with invalid information")
	public void Order_Placement_09_Error_Message_Invalid_Information_Filled_In_Orders_And_Returns_Form() {
		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear",
				gearCategory);
		productListingPage.addProductWithoutOptionsToCart(driver, gearProductName);
		homepage.clickShoppingCartIcon(driver);
		checkoutPage = homepage.clickProceedToCheckoutButton(driver);
		checkoutPage.selectShippingMethodRadioButtonByLabel("Fixed");
		checkoutPage.clickNextButton();
		orderSuccessPage = checkoutPage.clickPlaceOrderButton();
		orderAndReturnsPage = (OrdersAndReturnsPageObject) orderSuccessPage.clickFooterLinkByLabel(driver,
				"Orders and Returns");
		orderAndReturnsPage.sendKeysToOrderIdTextbox("12345");
		orderAndReturnsPage.sendKeysToBillingLastNameTextbox("Le");
		orderAndReturnsPage.selectOptionFindOrderByDropdown("Email");
		orderAndReturnsPage.sendKeysToEmailOrZipCodeTextbox(email);
		orderAndReturnsPage.clickContinueButton();

		Assert.assertEquals(orderAndReturnsPage.getIncorrectOrderDataErrorMessage(),
				"You entered incorrect data. Please try again.");
	}

	@Test(description = "Verify that user can find an order using either email or zip code")
	public void Order_Placement_10_Find_Order_Using_Email_Or_Zip_Code() {
		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear",
				gearCategory);
		productListingPage.addProductWithoutOptionsToCart(driver, gearProductName);
		productListingPage.clickShoppingCartIcon(driver);
		checkoutPage = productListingPage.clickProceedToCheckoutButton(driver);
		checkoutPage.selectShippingMethodRadioButtonByLabel("Fixed");
		checkoutPage.clickNextButton();
		String billingZipCode = checkoutPage.getBillingAddressZipCode();
		orderSuccessPage = checkoutPage.clickPlaceOrderButton();
		String orderNumber = orderSuccessPage.getOrderNumber();
		orderAndReturnsPage = (OrdersAndReturnsPageObject) orderSuccessPage.clickFooterLinkByLabel(driver,
				"Orders and Returns");
		orderAndReturnsPage.sendKeysToOrderIdTextbox(orderNumber);
		orderAndReturnsPage.sendKeysToBillingLastNameTextbox("Le");
		orderAndReturnsPage.selectOptionFindOrderByDropdown("Email");
		orderAndReturnsPage.sendKeysToEmailOrZipCodeTextbox(email);
		orderDetailsPage = orderAndReturnsPage.clickContinueButton();

		Assert.assertEquals(orderDetailsPage.getPageHeader(driver), "Order # " + orderNumber);

		orderAndReturnsPage = (OrdersAndReturnsPageObject) orderDetailsPage.clickFooterLinkByLabel(driver,
				"Orders and Returns");
		orderAndReturnsPage.sendKeysToOrderIdTextbox(orderNumber);
		orderAndReturnsPage.sendKeysToBillingLastNameTextbox("Le");
		orderAndReturnsPage.selectOptionFindOrderByDropdown("ZIP Code");
		orderAndReturnsPage.sendKeysToEmailOrZipCodeTextbox(billingZipCode);
		orderDetailsPage = orderAndReturnsPage.clickContinueButton();

		Assert.assertEquals(orderDetailsPage.getPageHeader(driver), "Order # " + orderNumber);
	}

	@Test(description = "Verify that the shipping address is displayed correctly for each order number (order with multiple shipping addresses)")
	public void Order_Placement_11_Shipping_Address_Matches_Order_Number() {
		String shippingAddress1 = "101 Oxford St";
		String shippingAddress2 = "22 Kangaroo Rd";
		String shippingAddress3 = "789 Queen St";

		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear",
				gearCategory);
		productListingPage.addProductWithoutOptionsToCart(driver, gearProductName);
		productListingPage.clickNavigationBarDropdownMultiLevelItemLinkByLabels(driver, "Men", menCategory,
				menSubcategory);
		productListingPage.addProductWithOptionsToCart(driver, menProductName, menProductSize, menProductColor);
		productListingPage.clickNavigationBarDropdownMultiLevelItemLinkByLabels(driver, "Wowomen", womenCategory,
				womenSubcategory);
		productListingPage.addProductWithOptionsToCart(driver, womenProductName, womenProductSize, womenProductColor);
		productListingPage.clickShoppingCartIcon(driver);
		shoppingCartPage = productListingPage.clickViewAndEditCartLink(driver);
		shipToMultipleAddressesPage = shoppingCartPage.clickCheckOutWithMultipleAddressesLink();
		shipToMultipleAddressesPage.selectOptionSendToDropdownByProductName(gearProductName, shippingAddress1);
		shipToMultipleAddressesPage.selectOptionSendToDropdownByProductName(menProductName, shippingAddress2);
		shipToMultipleAddressesPage.selectOptionSendToDropdownByProductName(womenProductName, shippingAddress3);
		selectShippingMethodPage = shipToMultipleAddressesPage.clickGoToShippingInformationButton();
		selectShippingMethodPage.selectFixedRadioButtonByStreetAddress("");
		billingInformationPage = selectShippingMethodPage.clickContinueToBillingInformationButton();
		reviewOrderPage = billingInformationPage.clickGoToReviewYourOrderButton();
		orderSuccessPage = reviewOrderPage.clickPlaceOrderButton();
		orderDetailsPage = orderSuccessPage.clickOrderNumberByShippingAddress(shippingAddress1);

		Assert.assertEquals(orderDetailsPage.getShippingAddress(), shippingAddress1);

		orderDetailsPage.backToPage(driver);
		orderDetailsPage = orderSuccessPage.clickOrderNumberByShippingAddress(shippingAddress2);

		Assert.assertEquals(orderDetailsPage.getShippingAddress(), shippingAddress2);

		orderDetailsPage.backToPage(driver);
		orderDetailsPage = orderSuccessPage.clickOrderNumberByShippingAddress(shippingAddress3);

		Assert.assertEquals(orderDetailsPage.getShippingAddress(), shippingAddress3);
	}

	@Test(groups = "logOut", description = "Verify that user can place an order without an account")
	public void Order_Placement_12_Place_Order_Without_An_Account() {
		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear",
				gearCategory);
		productListingPage.addProductWithoutOptionsToCart(driver, gearProductName);
		productListingPage.clickShoppingCartIcon(driver);
		checkoutPage = productListingPage.clickProceedToCheckoutButton(driver);
		checkoutPage.sendKeysToEmailAddressTextbox(data.getEmailAddress());
		checkoutPage.sendKeysToFirstNameTextbox(data.getFirstName());
		checkoutPage.sendKeysToLastNameTextbox(data.getLastName());
		checkoutPage.sendKeysToStreetAddressTextbox(data.getAddress());
		checkoutPage.sendKeysToCityTextbox(data.getCity());
		checkoutPage.selectOptionStateProvinceDropdown(data.getState());
		checkoutPage.sendKeysToZipPostalCodeTextbox(data.getZipCode());
		checkoutPage.selectOptionCountryDropdown(data.getCountry());
		checkoutPage.sendKeysToPhoneNumberTextbox(data.getPhoneNumber());
		checkoutPage.selectShippingMethodRadioButtonByLabel("Fixed");
		checkoutPage.clickNextButton();
		orderSuccessPage = checkoutPage.clickPlaceOrderButton();

		Assert.assertEquals(orderSuccessPage.getPageHeader(driver), "Thank you for your purchase!");
	}

	@Test(groups = "logOut", description = "Verify that user's first name, last name, and email are automatically filled in the register form when user places an order without an account and click the 'Create an Account' button")
	public void Order_Placement_13_First_Name_Last_Name_And_Email_Automatically_Filled_In_Register_Form() {
		String email = data.getEmailAddress();
		String firstName = data.getFirstName();
		String lastName = data.getLastName();

		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear",
				gearCategory);
		productListingPage.addProductWithoutOptionsToCart(driver, gearProductName);
		productListingPage.clickShoppingCartIcon(driver);
		checkoutPage = productListingPage.clickProceedToCheckoutButton(driver);
		checkoutPage.sendKeysToEmailAddressTextbox(email);
		checkoutPage.sendKeysToFirstNameTextbox(firstName);
		checkoutPage.sendKeysToLastNameTextbox(lastName);
		checkoutPage.sendKeysToStreetAddressTextbox(data.getAddress());
		checkoutPage.sendKeysToCityTextbox(data.getCity());
		checkoutPage.selectOptionStateProvinceDropdown(data.getState());
		checkoutPage.sendKeysToZipPostalCodeTextbox(data.getZipCode());
		checkoutPage.selectOptionCountryDropdown(data.getCountry());
		checkoutPage.sendKeysToPhoneNumberTextbox(data.getPhoneNumber());
		checkoutPage.selectShippingMethodRadioButtonByLabel("Fixed");
		checkoutPage.clickNextButton();
		orderSuccessPage = checkoutPage.clickPlaceOrderButton();
		createNewCustomerAccountPage = orderSuccessPage.clickCreateAnAccountButton();

		Assert.assertEquals(createNewCustomerAccountPage.getInputValueByTextboxLabel("First Name"), firstName);
		Assert.assertEquals(createNewCustomerAccountPage.getInputValueByTextboxLabel("Last Name"), lastName);
		Assert.assertEquals(createNewCustomerAccountPage.getInputValueByTextboxLabel("Email"), email);
	}

	@Test(groups = "logOut", description = "Verify the information message displayed on the 'My Orders' page when user has not placed any order")
	public void Order_Placement_14_No_Orders_Placed_Info_Message() {
		String email = JsonUtils.getJsonValue(fileName, "new_user.email");
		String password = JsonUtils.getJsonValue(fileName, "new_user.password");

		customerLoginPage = homepage.clickSignInLink();
		homepage = customerLoginPage.logInAsRegisteredUser(email, password);
		homepage.clickCustomerNameDropdown(driver);
		myAccountPage = homepage.clickMyAccountDropdownLink(driver);
		myOrdersPage = (MyOrdersPageObject) myAccountPage.clickMyAccountSidebarLinkByLabel(driver, "My Orders");

		Assert.assertEquals(myOrdersPage.getNoOrdersPlacedInfoMessage(), "You have placed no orders.");
	}

	@Test(groups = "logOut", description = "Verify that all placed orders are displayed on the 'My Orders' page")
	public void Order_Placement_15_Display_Of_Placed_Orders_In_My_Orders_Page() {
		createNewCustomerAccountPage = homepage.clickCreateAnAccountLink();
		String email = data.getEmailAddress();
		String password = data.getPassword();
		createNewCustomerAccountPage.completeRegistrationForm(data.getFirstName(), data.getLastName(), email, password);
		myAccountPage = createNewCustomerAccountPage.clickCreateAnAccountButton();
		productListingPage = myAccountPage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear",
				gearCategory);
		productListingPage.addProductWithoutOptionsToCart(driver, gearProductName);
		productListingPage.clickShoppingCartIcon(driver);
		checkoutPage = productListingPage.clickProceedToCheckoutButton(driver);
		checkoutPage.sendKeysToStreetAddressTextbox(data.getAddress());
		checkoutPage.sendKeysToCityTextbox(data.getCity());
		checkoutPage.selectOptionStateProvinceDropdown(data.getState());
		checkoutPage.sendKeysToZipPostalCodeTextbox(data.getZipCode());
		checkoutPage.selectOptionCountryDropdown(data.getCountry());
		checkoutPage.sendKeysToPhoneNumberTextbox(data.getPhoneNumber());
		checkoutPage.selectShippingMethodRadioButtonByLabel("Fixed");
		checkoutPage.clickNextButton();
		orderSuccessPage = checkoutPage.clickPlaceOrderButton();
		String orderNumber1 = orderSuccessPage.getOrderNumber();
		productListingPage = orderSuccessPage.clickNavigationBarDropdownMultiLevelItemLinkByLabels(driver, "Women",
				womenCategory, womenSubcategory);
		productListingPage.addProductWithOptionsToCart(driver, womenProductName, womenProductSize, womenProductColor);
		productListingPage.clickShoppingCartIcon(driver);
		checkoutPage = productListingPage.clickProceedToCheckoutButton(driver);
		checkoutPage.selectShippingMethodRadioButtonByLabel("Fixed");
		checkoutPage.clickNextButton();
		orderSuccessPage = checkoutPage.clickPlaceOrderButton();
		String orderNumber2 = orderSuccessPage.getOrderNumber();
		orderSuccessPage.clickCustomerNameDropdown(driver);
		myAccountPage = orderSuccessPage.clickMyAccountDropdownLink(driver);
		myOrdersPage = (MyOrdersPageObject) myAccountPage.clickMyAccountSidebarLinkByLabel(driver, "My Orders");

		Assert.assertTrue(myOrdersPage.isOrderByOrderNumberDisplayed(orderNumber1));
		Assert.assertTrue(myOrdersPage.isOrderByOrderNumberDisplayed(orderNumber2));
	}

	@AfterMethod(alwaysRun = true)
	public void clearShoppingCart() {
		homepage.clearShoppingCart(driver);
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeBrowserAndDriver();
	}

	private WebDriver driver;
	private JsonNode gearResultNode, menResultNode, womenResultNode;
	private FakeDataUtils data;
	private String fileName, email, password, defaultBillingAddress, defaultShippingAddress, todaysDateNumeric,
			todaysDateTextual, gearCategory, gearProductName, menCategory, menSubcategory, menProductName,
			menProductSize, menProductColor, womenCategory, womenSubcategory, womenProductName, womenProductSize,
			womenProductColor;
	private CreateNewCustomerAccountPageObject createNewCustomerAccountPage;
	private CustomerLoginPageObject customerLoginPage;
	private HomepageObject homepage;
	private MyAccountPageObject myAccountPage;
	private MyOrdersPageObject myOrdersPage;
	private ProductListingPageObject productListingPage;
	private ProductDetailsPageObject productDetailsPage;
	private ShoppingCartPageObject shoppingCartPage;
	private CheckoutPageObject checkoutPage;
	private ShipToMultipleAddressesPageObject shipToMultipleAddressesPage;
	private SelectShippingMethodPageObject selectShippingMethodPage;
	private BillingInformationPageObject billingInformationPage;
	private ReviewOrderPageObject reviewOrderPage;
	private OrderSuccessPageObject orderSuccessPage;
	private OrderDetailsPageObject orderDetailsPage;
	private OrdersAndReturnsPageObject orderAndReturnsPage;
}
