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
import pageObjects.SelectShippingMethodPageObject;
import pageObjects.ShipToMultipleAddressesPageObject;
import pageObjects.ShoppingCartPageObject;
import utilities.FakeDataUtils;
import utilities.JsonUtils;

public class ShipToMultipleAddresses_04 extends BaseTest {
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
		resultNode = JsonUtils.getRandomProductNoOptions("gear_products.json");
		category = resultNode.get("category").asText();
		productName = resultNode.get("product").get("product_name").asText();

		customerLoginPage = homepage.clickSignInLink();
		homepage = customerLoginPage.logInAsRegisteredUser(email, password);
		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear", category);
		productListingPage.clickAddToCartButtonByProductName(driver, productName);
		homepage = productListingPage.clickLumaLogo(driver);
	}

	@Test(description = "Verify that user can add a new billing address")
	public void Ship_To_Multiple_Addresses_01_Add_New_Billing_Address() {
		homepage.clickShoppingCartIcon(driver);
		shoppingCartPage = homepage.clickViewAndEditCartLink(driver);
		shipToMultipleAddressesPage = shoppingCartPage.clickCheckOutWithMultipleAddressesLink();
		selectShippingMethodPage = shipToMultipleAddressesPage.clickGoToShippingInformationButton();
		selectShippingMethodPage.selectTableRateRadioButtonByStreetAddress("123 Main St");
		billingInformationPage = selectShippingMethodPage.clickContinueToBillingInformationButton();
		changeBillingAddressPage = billingInformationPage.clickChangeBillingAddressLink();
		addressPage = changeBillingAddressPage.clickAddNewAddressButton();
		addressPage.sendKeysToPhoneNumberTextbox(data.getPhoneNumber());
		String address = data.getAddress();
		addressPage.sendKeysToStreetAddressTextbox(address);
		addressPage.sendKeysToCityTextbox(data.getCity());
		addressPage.selectOptionStateProvinceDropdown(data.getState());
		addressPage.sendKeysToZipPostalTextbox(data.getZipCode());
		changeBillingAddressPage = (ChangeBillingAddressPageObject) addressPage.clickSaveAddressButton(driver);
		System.out.println(changeBillingAddressPage);

		Assert.assertTrue(changeBillingAddressPage.isBillingAddressByStreetAddressDisplayed(address));
	}

	@Test(description = "Verify that user can edit a billing address")
	public void Ship_To_Multiple_Addresses_02_Edit_Billing_Address() {
		homepage.clickShoppingCartIcon(driver);
		shoppingCartPage = homepage.clickViewAndEditCartLink(driver);
		shipToMultipleAddressesPage = shoppingCartPage.clickCheckOutWithMultipleAddressesLink();
		selectShippingMethodPage = shipToMultipleAddressesPage.clickGoToShippingInformationButton();
		selectShippingMethodPage.selectTableRateRadioButtonByStreetAddress("123 Main St");
		billingInformationPage = selectShippingMethodPage.clickContinueToBillingInformationButton();
		changeBillingAddressPage = billingInformationPage.clickChangeBillingAddressLink();
		addressPage = changeBillingAddressPage.clickEditAddressLinkByStreetAddress("");
		changeBillingAddressPage = (ChangeBillingAddressPageObject) addressPage.clickSaveAddressButton(driver);

	}

	@Test(description = "Verify that user can select a different billing address")
	public void Ship_To_Multiple_Addresses_03_Select_Billing_Address() {
		homepage.clickShoppingCartIcon(driver);
		shoppingCartPage = homepage.clickViewAndEditCartLink(driver);
		shipToMultipleAddressesPage = shoppingCartPage.clickCheckOutWithMultipleAddressesLink();
		selectShippingMethodPage = shipToMultipleAddressesPage.clickGoToShippingInformationButton();
		selectShippingMethodPage.selectTableRateRadioButtonByStreetAddress("123 Main St");
		billingInformationPage = selectShippingMethodPage.clickContinueToBillingInformationButton();
		changeBillingAddressPage = billingInformationPage.clickChangeBillingAddressLink();
		billingInformationPage = changeBillingAddressPage.clickSelectAddressLinkByStreetAddress("");

	}

	@Test(description = "Verify that user is directed to the 'Billing Information' page when clicking the 'Back to Billing Information' hyperlink")
	public void Ship_To_Multiple_Addresses_04_Click_Back_To_Billing_Information_Link() {
		homepage.clickShoppingCartIcon(driver);
		shoppingCartPage = homepage.clickViewAndEditCartLink(driver);
		shipToMultipleAddressesPage = shoppingCartPage.clickCheckOutWithMultipleAddressesLink();
		selectShippingMethodPage = shipToMultipleAddressesPage.clickGoToShippingInformationButton();
		selectShippingMethodPage.selectTableRateRadioButtonByStreetAddress("123 Main St");
		billingInformationPage = selectShippingMethodPage.clickContinueToBillingInformationButton();
		changeBillingAddressPage = billingInformationPage.clickChangeBillingAddressLink();
		billingInformationPage = changeBillingAddressPage.clickBackToBillingInformationLink();

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
	private FakeDataUtils data;
	private String fileName, email, password, defaultBillingAddress, defaultShippingAddress, category, productName;
	private HomepageObject homepage;
	private CustomerLoginPageObject customerLoginPage;
	private ProductListingPageObject productListingPage;
	private ShoppingCartPageObject shoppingCartPage;
	private ShipToMultipleAddressesPageObject shipToMultipleAddressesPage;
	private SelectShippingMethodPageObject selectShippingMethodPage;
	private BillingInformationPageObject billingInformationPage;
	private ChangeBillingAddressPageObject changeBillingAddressPage;
	private AddressPageObject addressPage;
}
