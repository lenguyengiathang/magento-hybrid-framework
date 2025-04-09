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
import pageObjects.BillingInformationPageObject;
import pageObjects.ChangeBillingAddressPageObject;
import pageObjects.CustomerLoginPageObject;
import pageObjects.HomepageObject;
import pageObjects.ProductListingPageObject;
import pageObjects.ReviewOrderPageObject;
import pageObjects.SelectShippingMethodPageObject;
import pageObjects.ShipToMultipleAddressesPageObject;
import pageObjects.ShoppingCartPageObject;
import utilities.FakeDataUtils;
import utilities.JsonUtils;

public class ShipToMultipleAddresses_05 extends BaseTest {
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

	@Test(description = "Verify that user is directed to the 'Billing Information' page when clicking the 'Back to Billing Information' hyperlink")
	public void Ship_To_Multiple_Addresses_01_Click_Back_To_Billing_Information_Link() {
		homepage.clickShoppingCartIcon(driver);
		shoppingCartPage = homepage.clickViewAndEditCartLink(driver);
		shipToMultipleAddressesPage = shoppingCartPage.clickCheckOutWithMultipleAddressesLink();
		selectShippingMethodPage = shipToMultipleAddressesPage.clickGoToShippingInformationButton();
		selectShippingMethodPage.selectTableRateRadioButtonByStreetAddress("123 Main St");
		billingInformationPage = selectShippingMethodPage.clickContinueToBillingInformationButton();
		reviewOrderPage = billingInformationPage.clickGoToReviewYourOrderButton();
		billingInformationPage = reviewOrderPage.clickBackToBillingInformationLink();

		Assert.assertEquals(billingInformationPage.getPageHeader(driver), "Billing Information");
	}

	@Test(description = "Verify that the product details, billing address, shipping address, and order summary are correctly displayed for each product")
	public void Ship_To_Multiple_Addresses_02_Correct_Product_Details_Billing_Address_Shipping_Address_And_Order_Summary() {

	}

	@Test(description = "Verify that user can change the billing address by clicking the Change' hyperlink next to the 'Billing Address' label")
	public void Ship_To_Multiple_Addresses_03_Click_Change_Billing_Address_Link() {
		homepage.clickShoppingCartIcon(driver);
		shoppingCartPage = homepage.clickViewAndEditCartLink(driver);
		shipToMultipleAddressesPage = shoppingCartPage.clickCheckOutWithMultipleAddressesLink();
		selectShippingMethodPage = shipToMultipleAddressesPage.clickGoToShippingInformationButton();
		selectShippingMethodPage.selectTableRateRadioButtonByStreetAddress("123 Main St");
		billingInformationPage = selectShippingMethodPage.clickContinueToBillingInformationButton();
		reviewOrderPage = billingInformationPage.clickGoToReviewYourOrderButton();
		addressPage = reviewOrderPage.clickChangeBillingAddressLink();

		Assert.assertEquals(addressPage.getPageHeader(driver), "Change Billing Address");
	}

	@Test(description = "Verify that user is directed to the 'Billing Information' page when clicking the 'Change' hyperlink next to the 'Payment Method' label")
	public void Ship_To_Multiple_Addresses_04_Click_Change_Payment_Method_Link() {
		homepage.clickShoppingCartIcon(driver);
		shoppingCartPage = homepage.clickViewAndEditCartLink(driver);
		shipToMultipleAddressesPage = shoppingCartPage.clickCheckOutWithMultipleAddressesLink();
		selectShippingMethodPage = shipToMultipleAddressesPage.clickGoToShippingInformationButton();
		selectShippingMethodPage.selectTableRateRadioButtonByStreetAddress("123 Main St");
		billingInformationPage = selectShippingMethodPage.clickContinueToBillingInformationButton();
		reviewOrderPage = billingInformationPage.clickGoToReviewYourOrderButton();
		billingInformationPage = reviewOrderPage.clickChangePaymentMethodLink();

		Assert.assertEquals(billingInformationPage.getPageHeader(driver), "Billing Information");
	}

	@Test(description = "Verify that user can edit a shipping address by clicking the 'Change' hyperlink next to the 'Shipping To' label")
	public void Ship_To_Multiple_Addresses_05_Edit_Shipping_Address() {
		homepage.clickShoppingCartIcon(driver);
		shoppingCartPage = homepage.clickViewAndEditCartLink(driver);
		shipToMultipleAddressesPage = shoppingCartPage.clickCheckOutWithMultipleAddressesLink();
		selectShippingMethodPage = shipToMultipleAddressesPage.clickGoToShippingInformationButton();
		selectShippingMethodPage.selectTableRateRadioButtonByStreetAddress("123 Main St");
		billingInformationPage = selectShippingMethodPage.clickContinueToBillingInformationButton();
		reviewOrderPage = billingInformationPage.clickGoToReviewYourOrderButton();
		addressPage = reviewOrderPage.clickChangeShippingAddressLinkByProductName(productName);
		String zipCode = data.getZipCode();
		addressPage.sendKeysToZipPostalTextbox(zipCode);
		selectShippingMethodPage = (SelectShippingMethodPageObject) addressPage.clickSaveAddressButton(driver);

		Assert.assertEquals(selectShippingMethodPage.getShippingAddressZipCodeByProductName(productName), zipCode);
	}

	@Test(description = "Verify that user is directed to the 'Shipping Method' page when clicking the 'Change' hyperlink next to the 'Shipping Method' label")
	public void Ship_To_Multiple_Addresses_06_Click_Change_Shipping_Method_Link() {
		homepage.clickShoppingCartIcon(driver);
		shoppingCartPage = homepage.clickViewAndEditCartLink(driver);
		shipToMultipleAddressesPage = shoppingCartPage.clickCheckOutWithMultipleAddressesLink();
		selectShippingMethodPage = shipToMultipleAddressesPage.clickGoToShippingInformationButton();
		selectShippingMethodPage.selectTableRateRadioButtonByStreetAddress("123 Main St");
		billingInformationPage = selectShippingMethodPage.clickContinueToBillingInformationButton();
		reviewOrderPage = billingInformationPage.clickGoToReviewYourOrderButton();
		selectShippingMethodPage = reviewOrderPage.clickChangeShippingMethodLinkByProductName(productName);

		Assert.assertEquals(selectShippingMethodPage.getPageHeader(driver), "Select Shipping Method");
	}

	@Test(description = "Verify that user is directed to the 'Ship to Multiple Addresses' page when clicking any 'Edit' hyperlink")
	public void Ship_To_Multiple_Addresses_07_Click_Edit_Link() {
		homepage.clickShoppingCartIcon(driver);
		shoppingCartPage = homepage.clickViewAndEditCartLink(driver);
		shipToMultipleAddressesPage = shoppingCartPage.clickCheckOutWithMultipleAddressesLink();
		selectShippingMethodPage = shipToMultipleAddressesPage.clickGoToShippingInformationButton();
		selectShippingMethodPage.selectTableRateRadioButtonByStreetAddress("123 Main St");
		billingInformationPage = selectShippingMethodPage.clickContinueToBillingInformationButton();
		reviewOrderPage = billingInformationPage.clickGoToReviewYourOrderButton();
		shipToMultipleAddressesPage = reviewOrderPage.clickEditLinkByShippingAddress("123 Main St");

		Assert.assertEquals(reviewOrderPage.getPageHeader(driver), "Ship to Multiple Addresses");
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
	private JsonNode resultNode;
	private FakeDataUtils data;
	private String fileName, email, password, category, productName;
	private HomepageObject homepage;
	private CustomerLoginPageObject customerLoginPage;
	private ProductListingPageObject productListingPage;
	private ShoppingCartPageObject shoppingCartPage;
	private ShipToMultipleAddressesPageObject shipToMultipleAddressesPage;
	private SelectShippingMethodPageObject selectShippingMethodPage;
	private BillingInformationPageObject billingInformationPage;
	private ChangeBillingAddressPageObject changeBillingAddressPage;
	private AddressPageObject addressPage;
	private ReviewOrderPageObject reviewOrderPage;
}
