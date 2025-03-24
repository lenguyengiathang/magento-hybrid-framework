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
import pageObjects.BillingInformationPageObject;
import pageObjects.ChangeBillingAddressPageObject;
import pageObjects.CustomerLoginPageObject;
import pageObjects.HomepageObject;
import pageObjects.ProductListingPageObject;
import pageObjects.ReviewOrderPageObject;
import pageObjects.SelectShippingMethodPageObject;
import pageObjects.ShipToMultipleAddressesPageObject;
import pageObjects.ShoppingCartPageObject;
import utilities.JsonUtils;

public class ShipToMultipleAddresses_03 extends BaseTest {
	@Parameters("browser")
	@BeforeClass
	public void beforeClass(String browser) {
		driver = getBrowserDriver(browser);
		homepage = PageGeneratorManager.getHomepage(driver);

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

	@Test(description = "Verify that user is directed to the 'Select Shipping Method' page when clicking the 'Back to Shipping Information' hyperlink")
	public void Ship_To_Multiple_Addresses_01_Click_Back_To_Shipping_Information_Link() {
		homepage.clickShoppingCartIcon(driver);
		shoppingCartPage = homepage.clickViewAndEditCartLink(driver);
		shipToMultipleAddressesPage = shoppingCartPage.clickCheckOutWithMultipleAddressesLink();
		selectShippingMethodPage = shipToMultipleAddressesPage.clickGoToShippingInformationButton();
		selectShippingMethodPage.selectTableRateRadioButtonByStreetAddress("123 Main St");
		billingInformationPage = selectShippingMethodPage.clickContinueToBillingInformationButton();
		selectShippingMethodPage = billingInformationPage.clickBackToShippingInformationLink();

		Assert.assertEquals(selectShippingMethodPage.getPageHeader(driver), "Select Shipping Method");
	}

	@Test(description = "Verify that user is directed to the 'Change Billing Address' page when clicking the 'Change' hyperlink")
	public void Ship_To_Multiple_Addresses_02_Click_Change_Link() {
		homepage.clickShoppingCartIcon(driver);
		shoppingCartPage = homepage.clickViewAndEditCartLink(driver);
		shipToMultipleAddressesPage = shoppingCartPage.clickCheckOutWithMultipleAddressesLink();
		selectShippingMethodPage = shipToMultipleAddressesPage.clickGoToShippingInformationButton();
		selectShippingMethodPage.selectTableRateRadioButtonByStreetAddress("123 Main St");
		billingInformationPage = selectShippingMethodPage.clickContinueToBillingInformationButton();
		changeBillingAddressPage = billingInformationPage.clickChangeBillingAddressLink();

		Assert.assertEquals(changeBillingAddressPage.getPageHeader(driver), "Change Billing Address");
	}

	@Test(description = "Verify that user is directed to the 'Review Order' page when clicking the 'Go to Review Your Order' button")
	public void Ship_To_Multiple_Addresses_03_Click_Go_To_Review_Your_Order() {
		homepage.clickShoppingCartIcon(driver);
		shoppingCartPage = homepage.clickViewAndEditCartLink(driver);
		shipToMultipleAddressesPage = shoppingCartPage.clickCheckOutWithMultipleAddressesLink();
		selectShippingMethodPage = shipToMultipleAddressesPage.clickGoToShippingInformationButton();
		selectShippingMethodPage.selectTableRateRadioButtonByStreetAddress("123 Main St");
		billingInformationPage = selectShippingMethodPage.clickContinueToBillingInformationButton();
		reviewOrderPage = billingInformationPage.clickGoToReviewYourOrderButton();

		Assert.assertEquals(reviewOrderPage.getPageHeader(driver), "Review Order");
	}

	@AfterMethod(alwaysRun = true)
	public void navigateToHomepage() {
		homepage = selectShippingMethodPage.clickLumaLogo(driver);
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeBrowserAndDriver();
	}

	private WebDriver driver;
	private JsonNode resultNode;
	private String fileName, email, password, category, productName;
	private HomepageObject homepage;
	private CustomerLoginPageObject customerLoginPage;
	private ProductListingPageObject productListingPage;
	private ShoppingCartPageObject shoppingCartPage;
	private ShipToMultipleAddressesPageObject shipToMultipleAddressesPage;
	private SelectShippingMethodPageObject selectShippingMethodPage;
	private BillingInformationPageObject billingInformationPage;
	private ChangeBillingAddressPageObject changeBillingAddressPage;
	private ReviewOrderPageObject reviewOrderPage;
}
