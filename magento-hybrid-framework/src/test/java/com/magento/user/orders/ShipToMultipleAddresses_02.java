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
import pageObjects.CustomerLoginPageObject;
import pageObjects.HomepageObject;
import pageObjects.ProductListingPageObject;
import pageObjects.SelectShippingMethodPageObject;
import pageObjects.ShipToMultipleAddressesPageObject;
import pageObjects.ShoppingCartPageObject;
import utilities.JsonUtils;

public class ShipToMultipleAddresses_02 extends BaseTest {
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

	@Test(description = "Verify that user can edit a shipping address information by clicking the 'Change' hyperlink")
	public void Ship_To_Multiple_Addresses_01_Click_Change_Link() {
		homepage.clickShoppingCartIcon(driver);
		shoppingCartPage = homepage.clickViewAndEditCartLink(driver);
		shipToMultipleAddressesPage = shoppingCartPage.clickCheckOutWithMultipleAddressesLink();
		selectShippingMethodPage = shipToMultipleAddressesPage.clickGoToShippingInformationButton();
		addressPage = selectShippingMethodPage.clickChangeLinkByProductName(productName);
		addressPage.sendKeysToZipPostalTextbox("10002");
		selectShippingMethodPage = (SelectShippingMethodPageObject) addressPage.clickSaveAddressButton(driver);

		Assert.assertEquals(selectShippingMethodPage.getShippingAddressZipCodeByProductName(productName), "10002");
	}

	@Test(description = "Verify that user can edit a shipping address information by clicking the 'Change' hyperlink")
	public void Ship_To_Multiple_Addresses_02_Click_Edit_Items_Link() {
		homepage.clickShoppingCartIcon(driver);
		shoppingCartPage = homepage.clickViewAndEditCartLink(driver);
		shipToMultipleAddressesPage = shoppingCartPage.clickCheckOutWithMultipleAddressesLink();
		selectShippingMethodPage = shipToMultipleAddressesPage.clickGoToShippingInformationButton();
		shipToMultipleAddressesPage = selectShippingMethodPage.clickEditItemsLinkByProductName(productName);

		Assert.assertEquals(shipToMultipleAddressesPage.getPageHeader(driver), "Ship to Multiple Addresses");
	}

	@Test(description = "Verify that user can edit a shipping address information by clicking the 'Change' hyperlink")
	public void Ship_To_Multiple_Addresses_03_Click_Back_To_Select_Addresses_Link() {
		homepage.clickShoppingCartIcon(driver);
		shoppingCartPage = homepage.clickViewAndEditCartLink(driver);
		shipToMultipleAddressesPage = shoppingCartPage.clickCheckOutWithMultipleAddressesLink();
		selectShippingMethodPage = shipToMultipleAddressesPage.clickGoToShippingInformationButton();
		shipToMultipleAddressesPage = selectShippingMethodPage.clickBackToSelectAddressesLink();

		Assert.assertEquals(shipToMultipleAddressesPage.getPageHeader(driver), "Ship to Multiple Addresses");
	}

	@Test(description = "Verify the error message displayed when the shipping method of at least one address is not selected")
	public void Ship_To_Multiple_Addresses_04_Error_Message_Shipping_Method_Not_Selected() {
		homepage.clickShoppingCartIcon(driver);
		shoppingCartPage = homepage.clickViewAndEditCartLink(driver);
		shipToMultipleAddressesPage = shoppingCartPage.clickCheckOutWithMultipleAddressesLink();
		selectShippingMethodPage = shipToMultipleAddressesPage.clickGoToShippingInformationButton();
		selectShippingMethodPage.clickContinueToBillingInformationButton();

		Assert.assertEquals(selectShippingMethodPage.getShippingMethodNotSelectedErrorMessage(),
				"Set shipping methods for all addresses. Verify the shipping methods and try again.");
	}

	@Test(description = "Verify that user is directed to the 'Billing Information' page when clicking the 'Continue to Billing Information' button")
	public void Ship_To_Multiple_Addresses_05_Click_Continue_To_Billing_Information_Button() {
		homepage.clickShoppingCartIcon(driver);
		shoppingCartPage = homepage.clickViewAndEditCartLink(driver);
		shipToMultipleAddressesPage = shoppingCartPage.clickCheckOutWithMultipleAddressesLink();
		selectShippingMethodPage = shipToMultipleAddressesPage.clickGoToShippingInformationButton();
		selectShippingMethodPage.selectTableRateRadioButtonByStreetAddress("123 Main St");
		billingInformationPage = selectShippingMethodPage.clickContinueToBillingInformationButton();

		Assert.assertEquals(billingInformationPage.getPageHeader(driver), "Billing Information");
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
	private String fileName, email, password, category, productName;
	private HomepageObject homepage;
	private CustomerLoginPageObject customerLoginPage;
	private ProductListingPageObject productListingPage;
	private ShoppingCartPageObject shoppingCartPage;
	private ShipToMultipleAddressesPageObject shipToMultipleAddressesPage;
	private SelectShippingMethodPageObject selectShippingMethodPage;
	private BillingInformationPageObject billingInformationPage;
	private AddressPageObject addressPage;
}
