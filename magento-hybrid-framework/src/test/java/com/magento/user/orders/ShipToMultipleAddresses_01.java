package com.magento.user.orders;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;

import commons.BaseTest;
import commons.PageGeneratorManager;
import pageObjects.AddressPageObject;
import pageObjects.CustomerLoginPageObject;
import pageObjects.HomepageObject;
import pageObjects.ProductListingPageObject;
import pageObjects.SelectShippingMethodPageObject;
import pageObjects.ShipToMultipleAddressesPageObject;
import pageObjects.ShoppingCartPageObject;
import utilities.FakeDataUtils;
import utilities.JsonUtils;

public class ShipToMultipleAddresses_01 extends BaseTest {
	@Parameters("browser")
	@BeforeClass
	public void beforeClass(String browser) {
		driver = getBrowserDriver(browser);
		homepage = PageGeneratorManager.getHomepage(driver);

		data = FakeDataUtils.getDataHelper();
		fileName = "login_data.json";
		email = JsonUtils.getJsonValue(fileName, "existing_user.email");
		password = JsonUtils.getJsonValue(fileName, "existing_user.password");
		resultNode = JsonUtils.getRandomProductNoOptions("gear_products.json");
		category = resultNode.get("category").asText();
		productName = resultNode.get("product").get("product_name").asText();

		customerLoginPage = homepage.clickSignInLink();
		homepage = customerLoginPage.logInAsRegisteredUser(email, password);
		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear", category);
		productListingPage.clickAddToCartButtonByProductName(driver, productName);
		homepage = productListingPage.clickLumaLogo(driver);
	}

	@Test(description = "Verify that user is directed to the 'Ship to Multiple Addresses' page when clicking the 'Check Out with Multiple Addresses' hyperlink")
	public void Ship_To_Multiple_Addresses_01_Click_Check_Out_With_Multiple_Addresses_Link() {
		homepage.clickShoppingCartIcon(driver);
		shoppingCartPage = homepage.clickViewAndEditCartLink(driver);
		shipToMultipleAddressesPage = shoppingCartPage.clickCheckOutWithMultipleAddressesLink();

		Assert.assertEquals(shipToMultipleAddressesPage.getPageHeader(driver), "Ship to Multiple Addresses");
	}

	@Test(description = "Verify that user can change the product quantity")
	public void Ship_To_Multiple_Addresses_02_Change_Product_Quantity() {
		homepage.clickShoppingCartIcon(driver);
		shoppingCartPage = homepage.clickViewAndEditCartLink(driver);
		shipToMultipleAddressesPage = shoppingCartPage.clickCheckOutWithMultipleAddressesLink();
		shipToMultipleAddressesPage.sendKeysToQuantityTextboxByProductName(productName, "10");
		shipToMultipleAddressesPage.clickUpdateQuantityAndAddressesButton();

		Assert.assertEquals(shipToMultipleAddressesPage.getQuantityValueByProductName(productName), "10");
	}

	@Test(description = "Verify that the user can select different shipping addresses for each product within the same order")
	public void Ship_To_Multiple_Addresses_03_Select_Different_Shipping_Address_For_Each_Product() {
		String productName1 = "Quest Lumaflex™ Band";
		String productName2 = "Sprite Foam Yoga Brick";
		String productName3 = "Zing Jump Rope";
		String shippingAddress1 = "101 Oxford St";
		String shippingAddress2 = "789 Pine St";
		String shippingAddress3 = "321 Oak St";
		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear",
				"Fitness Equipment");
		productListingPage.addProductWithNoOptionsToCart(driver, productName1);
		productListingPage.addProductWithNoOptionsToCart(driver, productName2);
		productListingPage.addProductWithNoOptionsToCart(driver, productName3);
		productListingPage.clickShoppingCartIcon(driver);
		shoppingCartPage = productListingPage.clickViewAndEditCartLink(driver);
		shipToMultipleAddressesPage = shoppingCartPage.clickCheckOutWithMultipleAddressesLink();
		shipToMultipleAddressesPage.selectOptionSendToDropdownByProductName(productName1, shippingAddress1);
		shipToMultipleAddressesPage.selectOptionSendToDropdownByProductName(productName2, shippingAddress2);
		shipToMultipleAddressesPage.selectOptionSendToDropdownByProductName(productName3, shippingAddress3);
		shipToMultipleAddressesPage.clickUpdateQuantityAndAddressesButton();

		Assert.assertEquals(shipToMultipleAddressesPage.getSelectedOptionSendToDropdownByProductName(productName1),
				shippingAddress1);
		Assert.assertEquals(shipToMultipleAddressesPage.getSelectedOptionSendToDropdownByProductName(productName2),
				shippingAddress2);
		Assert.assertEquals(shipToMultipleAddressesPage.getSelectedOptionSendToDropdownByProductName(productName3),
				shippingAddress3);
	}

	@Test(description = "Verify that the product is removed from the 'Ship to Multiple Addresses' page when clicking the \"Remove item\" hyperlink")
	public void Ship_To_Multiple_Addresses_04_Click_Remove_Item_Link() {
		homepage.clickShoppingCartIcon(driver);
		shoppingCartPage = homepage.clickViewAndEditCartLink(driver);
		shipToMultipleAddressesPage = shoppingCartPage.clickCheckOutWithMultipleAddressesLink();
		shipToMultipleAddressesPage.clickRemoveItemLinkByProductName(productName);

		Assert.assertTrue(shipToMultipleAddressesPage.isProductNotDisplayedInShipToMultipleAddressesPage(productName));
	}

	@Test(description = "Verify that user can add a new address")
	public void Ship_To_Multiple_Addresses_05_Add_New_Shipping_Address() {
		homepage.clickShoppingCartIcon(driver);
		shoppingCartPage = homepage.clickViewAndEditCartLink(driver);
		shipToMultipleAddressesPage = shoppingCartPage.clickCheckOutWithMultipleAddressesLink();
		addressPage = shipToMultipleAddressesPage.clickEnterANewAddressButton();
		addressPage.sendKeysToPhoneNumberTextbox(data.getPhoneNumber());
		String address = data.getAddress();
		addressPage.sendKeysToStreetAddressTextbox(address);
		addressPage.sendKeysToCityTextbox(data.getCity());
		addressPage.selectOptionStateProvinceDropdown(data.getState());
		addressPage.sendKeysToZipPostalTextbox(data.getZipCode());
		addressPage.selectOptionCountryDropdown(data.getCountry());
		shipToMultipleAddressesPage = (ShipToMultipleAddressesPageObject) addressPage.clickSaveAddressButton(driver);

		Assert.assertEquals(shipToMultipleAddressesPage.getSelectedOptionSendToDropdownByProductName(productName),
				address);
	}

	@Test(description = "Verify that user's default shipping address is selected for each product by default")
	public void Ship_To_Multiple_Addresses_06_Default_Shipping_Address_Selected_By_Default() {
		homepage.clickShoppingCartIcon(driver);
		shoppingCartPage = homepage.clickViewAndEditCartLink(driver);
		shipToMultipleAddressesPage = shoppingCartPage.clickCheckOutWithMultipleAddressesLink();

		Assert.assertEquals(shipToMultipleAddressesPage.getSelectedOptionSendToDropdownByProductName(""),
				"Thang Le, 123 Main St Apt 4B Floor 12, New York, New York 10001, United States");
	}

	@Test(description = "Verify that user is directed to the 'Select Shipping Method' page when clicking the 'Go to Shipping Information' button")
	public void Ship_To_Multiple_Addresses_07_Click_Go_To_Shipping_Information_Button() {
		homepage.clickShoppingCartIcon(driver);
		shoppingCartPage = homepage.clickViewAndEditCartLink(driver);
		shipToMultipleAddressesPage = shoppingCartPage.clickCheckOutWithMultipleAddressesLink();
		selectShippingMethodPage = shipToMultipleAddressesPage.clickGoToShippingInformationButton();

		Assert.assertEquals(selectShippingMethodPage.getPageHeader(driver), "Select Shipping Method");
	}

	@Test(description = "Verify that user is directed to the 'Shopping Cart' page when clicking the 'Back to Shopping Cart' hyperlink")
	public void Ship_To_Multiple_Addresses_08_Click_Back_To_Shopping_Cart_Link() {
		homepage.clickShoppingCartIcon(driver);
		shoppingCartPage = homepage.clickViewAndEditCartLink(driver);
		shipToMultipleAddressesPage = shoppingCartPage.clickCheckOutWithMultipleAddressesLink();
		shoppingCartPage = shipToMultipleAddressesPage.clickBackToShoppingCartLink();

		Assert.assertEquals(shoppingCartPage.getPageHeader(driver), "Shopping Cart");
	}

	@AfterMethod(alwaysRun = true)
	public void navigateToHomepage() {
		homepage.navigateToHomepage(driver);
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeBrowserAndDriver();
	}

	private WebDriver driver;
	private FakeDataUtils data;
	private JsonNode resultNode;
	private String fileName, email, password, category, productName;
	private HomepageObject homepage;
	private CustomerLoginPageObject customerLoginPage;
	private ProductListingPageObject productListingPage;
	private ShoppingCartPageObject shoppingCartPage;
	private ShipToMultipleAddressesPageObject shipToMultipleAddressesPage;
	private SelectShippingMethodPageObject selectShippingMethodPage;
	private AddressPageObject addressPage;
}
