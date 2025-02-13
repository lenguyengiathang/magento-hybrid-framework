package com.magento.user.myaccount;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.magento.commons.Access;
import com.magento.commons.Register;

import commons.BaseTest;
import commons.PageGeneratorManager;
import pageObjects.AddressBookPageObject;
import pageObjects.AddressPageObject;
import pageObjects.CheckoutPageObject;
import pageObjects.CustomerLoginPageObject;
import pageObjects.HomepageObject;
import pageObjects.MyAccountPageObject;
import pageObjects.ProductListingPageObject;
import utilities.CSVUtils;
import utilities.FakeDataUtils;
import utilities.JsonUtils;

public class AddressBook extends BaseTest {
	@Parameters("browser")
	@BeforeClass
	public void beforeClass(String browser) {
		driver = getBrowserDriver(browser);
		homepage = PageGeneratorManager.getHomepage(driver);

		accessActions = new Access(driver);
		data = FakeDataUtils.getDataHelper();
	}

	@BeforeMethod(onlyForGroups = "addressData")
	public String[] getRandomAddressData() throws IOException {
		randomRow = CSVUtils.getRandomRowFromCSVFile("src/test/resources/addresses.csv");
		return randomRow;
	}

	@BeforeMethod(onlyForGroups = "accountWithoutAddress")
	public void createNewAccount() {
		email = Register.email;
		password = Register.password;
		firstName = Register.firstName;
		lastName = Register.lastName;
		fullName = firstName + " " + lastName;

		try {
			customerLoginPage = homepage.clickSignInLink();
			homepage = customerLoginPage.logInAsRegisteredUser(email, password);
			homepage.clickCustomerNameDropdown(driver);
			myAccountPage = homepage.clickMyAccountDropdownLink(driver);
		} catch (Exception e) {
			System.err.println("Error creating a new account: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@BeforeMethod(onlyForGroups = "accountWithAddress")
	public void logInExistingAccountWithAddresses() {
		String fileName = "login_data.json";
		String email = JsonUtils.getJsonValue(fileName, "email");
		String password = JsonUtils.getJsonValue(fileName, "password");

		try {
			customerLoginPage = homepage.clickSignInLink();
			homepage = customerLoginPage.logInAsRegisteredUser(email, password);
			homepage.clickCustomerNameDropdown(driver);
			myAccountPage = homepage.clickMyAccountDropdownLink(driver);
		} catch (Exception e) {
			System.err.println("Error logging into existing account with addresses: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Test(groups = "accountWithoutAddress", description = "Verify that user is directed to the 'Add New Address' page when clicking the 'Manage Addresses' hyperlink (no address has been added yet)")
	public void Address_Book_01_Click_Manage_Addresses_Link_No_Address_Yet() {
		addressBookPage = myAccountPage.clickManageAddressesLink();

		Assert.assertEquals(addressBookPage.getPageHeader(driver), "Add New Address");
	}

	@Test(groups = "accountWithAddress", description = "Verify that user is directed to the 'Address Book' page when clicking the 'Manage Addresses' hyperlink (at least one address has been added)")
	public void Address_Book_02_Click_Manage_Addresses_Link_At_Least_One_Address_Added() {
		addressBookPage = myAccountPage.clickManageAddressesLink();

		Assert.assertEquals(addressBookPage.getPageHeader(driver), "Address Book");
	}

	@Test(groups = "accountWithoutAddress", description = "Verify that user is directed to 'Add New Address' page when clicking the 'Edit Address' hyperlink (no address has been added yet)")
	public void Address_Book_03_Click_Edit_Address_Link_No_Address_Yet() {
		addressPage = myAccountPage.clickEditAddressLinkDefaultBillingAddressSection();

		Assert.assertEquals(addressPage.getPageHeader(driver), "Add New Address");

		addressPage.clickCustomerNameDropdown(driver);
		myAccountPage = addressPage.clickMyAccountDropdownLink(driver);
		addressPage = myAccountPage.clickEditAddressLinkDefaultShippingAddressSection();

		Assert.assertEquals(addressPage.getPageHeader(driver), "Add New Address");
	}

	@Test(groups = "accountWithAddress", description = "Verify that user is directed to 'Add New Address' page when clicking the 'Edit Address' hyperlink (at least one address has been added)")
	public void Address_Book_04_Click_Edit_Address_Link_At_Least_One_Address_Added() {
		addressPage = myAccountPage.clickEditAddressLinkDefaultBillingAddressSection();

		Assert.assertEquals(addressPage.getPageHeader(driver), "Edit Address");

		addressPage.clickCustomerNameDropdown(driver);
		myAccountPage = addressPage.clickMyAccountDropdownLink(driver);
		addressPage = myAccountPage.clickEditAddressLinkDefaultShippingAddressSection();

		Assert.assertEquals(addressPage.getPageHeader(driver), "Edit Address");
	}

	@Test(groups = "accountWithAddress", description = "Verify the warning message displayed when an invalid zip or postal code is filled")
	public void Address_Book_05_Warning_Message_Invalid_Zip_Or_Postal_Code() {
		addressPage = myAccountPage.clickEditAddressLinkDefaultBillingAddressSection();
		addressPage.selectOptionCountryDropdown("United States");
		addressPage.sendKeysToZipPostalTextbox("123");

		Assert.assertEquals(addressPage.getInvalidZipPostalCodeWarningMessage(),
				"Provided Zip/Postal Code seems to be invalid. Example: 12345-6789; 12345. If you believe it is the right one you can ignore this notice.");
	}

	@Test(groups = { "accountWithoutAddress",
			"addressData" }, description = "Verify that user can add a new address (no address has been added yet)")
	public void Address_Book_06_Add_New_Address_No_Address_Yet() {
		addressPage = myAccountPage.clickEditAddressLinkDefaultBillingAddressSection();
		addressPage.completeAddressForm(randomRow);
		addressBookPage = addressPage.clickSaveAddressButton();

		Assert.assertEquals(addressBookPage.getDefaultBillingAddressFullName(), fullName);
		Assert.assertEquals(addressBookPage.getDefaultBillingAddressCompany(), randomRow[0]);
		Assert.assertEquals(addressBookPage.getDefaultBillingAddressAddressFirstLine(), randomRow[2]);
		Assert.assertEquals(addressBookPage.getDefaultBillingAddressAddressSecondLine(), randomRow[3]);
		Assert.assertEquals(addressBookPage.getDefaultBillingAddressAddressThirdLine(), randomRow[4]);
		Assert.assertEquals(addressBookPage.getDefaultBillingAddressCity(), randomRow[5]);
		Assert.assertEquals(addressBookPage.getDefaultBillingAddressState(), randomRow[6]);
		Assert.assertEquals(addressBookPage.getDefaultBillingAddressZipPostalCode(), randomRow[7]);
		Assert.assertEquals(addressBookPage.getDefaultBillingAddressCountry(), randomRow[8]);
		Assert.assertEquals(addressBookPage.getDefaultBillingAddressPhone(), randomRow[1]);
	}

	@Test(groups = { "accountWithAddress",
			"addressData" }, description = "Verify that user can add a new address (no address has been added yet)")
	public void Address_Book_07_Add_New_Address_At_Least_One_Address_Added() {
		addressBookPage = myAccountPage.clickManageAddressesLink();
		addressPage = addressBookPage.clickAddNewAddressButton();
		addressPage.completeAddressForm(randomRow);
		addressBookPage = addressPage.clickSaveAddressButton();

		Assert.assertEquals(addressBookPage.getAdditionalAddressValueByRowIndexAndColumnName("1", "First Name"),
				firstName);
		Assert.assertEquals(addressBookPage.getAdditionalAddressValueByRowIndexAndColumnName("1", "Last Name"),
				lastName);
		Assert.assertEquals(addressBookPage.getAdditionalAddressValueByRowIndexAndColumnName("1", "Street Address"),
				String.join(", ", randomRow[2], randomRow[3], randomRow[4]));
		Assert.assertEquals(addressBookPage.getAdditionalAddressValueByRowIndexAndColumnName("1", "City"),
				randomRow[5]);
		Assert.assertEquals(addressBookPage.getAdditionalAddressValueByRowIndexAndColumnName("1", "Country"),
				randomRow[8]);
		Assert.assertEquals(addressBookPage.getAdditionalAddressValueByRowIndexAndColumnName("1", "State"),
				randomRow[6]);
		Assert.assertEquals(addressBookPage.getAdditionalAddressValueByRowIndexAndColumnName("1", "Zip/Postal Code"),
				randomRow[7]);
		Assert.assertEquals(addressBookPage.getAdditionalAddressValueByRowIndexAndColumnName("1", "Phone"),
				randomRow[1]);
	}

	@Test(description = "Verify that the first added address is automatically set as the default billing and shipping address")
	public void Address_Book_08_First_Address_Automatically_Added_As_Default_Billing_And_Shipping_Address() {

	}

	@Test(groups = { "accountWithAddress",
			"addressData" }, description = "Verify that user can set an address as the default billing address")
	public void Address_Book_09_Set_Default_Billing_Address() {
		addressBookPage = myAccountPage.clickManageAddressesLink();
		addressPage = addressBookPage.clickAddNewAddressButton();
		addressPage.completeAddressForm(randomRow);
		addressPage.checkUseAsMyDefaultBillingAddressCheckbox();
		addressPage.clickSaveAddressButton();

		Assert.assertEquals(addressBookPage.getDefaultBillingAddressFullName(), fullName);
		Assert.assertEquals(addressBookPage.getDefaultBillingAddressCompany(), randomRow[0]);
		Assert.assertEquals(addressBookPage.getDefaultBillingAddressAddressFirstLine(), randomRow[2]);
		Assert.assertEquals(addressBookPage.getDefaultBillingAddressAddressSecondLine(), randomRow[3]);
		Assert.assertEquals(addressBookPage.getDefaultBillingAddressAddressThirdLine(), randomRow[4]);
		Assert.assertEquals(addressBookPage.getDefaultBillingAddressCity(), randomRow[5]);
		Assert.assertEquals(addressBookPage.getDefaultBillingAddressState(), randomRow[6]);
		Assert.assertEquals(addressBookPage.getDefaultBillingAddressZipPostalCode(), randomRow[7]);
		Assert.assertEquals(addressBookPage.getDefaultBillingAddressCountry(), randomRow[8]);
		Assert.assertEquals(addressBookPage.getDefaultBillingAddressPhone(), randomRow[1]);
	}

	@Test(groups = { "accountWithAddress",
			"addressData" }, description = "Verify that user can set an address as the default shipping address")
	public void Address_Book_10_Set_Default_Shipping_Address() {
		addressBookPage = myAccountPage.clickManageAddressesLink();
		addressPage = addressBookPage.clickAddNewAddressButton();
		addressPage.completeAddressForm(randomRow);
		addressPage.checkUseAsMyDefaultShippingAddressCheckbox();
		addressPage.clickSaveAddressButton();

		Assert.assertEquals(addressBookPage.getDefaultShippingAddressFullName(), fullName);
		Assert.assertEquals(addressBookPage.getDefaultShippingAddressCompany(), randomRow[0]);
		Assert.assertEquals(addressBookPage.getDefaultShippingAddressAddressFirstLine(), randomRow[2]);
		Assert.assertEquals(addressBookPage.getDefaultShippingAddressAddressSecondLine(), randomRow[3]);
		Assert.assertEquals(addressBookPage.getDefaultShippingAddressAddressThirdLine(), randomRow[4]);
		Assert.assertEquals(addressBookPage.getDefaultShippingAddressCity(), randomRow[5]);
		Assert.assertEquals(addressBookPage.getDefaultShippingAddressState(), randomRow[6]);
		Assert.assertEquals(addressBookPage.getDefaultShippingAddressZipPostalCode(), randomRow[7]);
		Assert.assertEquals(addressBookPage.getDefaultShippingAddressCountry(), randomRow[8]);
		Assert.assertEquals(addressBookPage.getDefaultShippingAddressPhone(), randomRow[1]);
	}

	@Test(groups = "accountWithAddress", description = "Verify the information message displayed when an address is set as the default billing and/or shipping address")
	public void Address_Book_11_Default_Billing_Shipping_Address_Info_Message() {
		addressPage = myAccountPage.clickEditAddressLinkDefaultBillingAddressSection();
		addressPage.completeAddressForm(randomRow);
		addressBookPage = addressPage.clickSaveAddressButton();
		addressPage = addressBookPage.clickChangeBillingAddressLink();

		Assert.assertEquals(addressPage.getDefaultAddressInfoMessageByIndex("1"), "It's a default billing address.");
		Assert.assertEquals(addressPage.getDefaultAddressInfoMessageByIndex("2"), "It's a default shipping address.");
	}

	@Test(groups = "accountWithAddressd", description = "Verify that user can edit the default billing address")
	public void Address_Book_12_Edit_Default_Billing_Address() {
		addressBookPage = myAccountPage.clickManageAddressesLink();
		addressPage = addressBookPage.clickChangeBillingAddressLink();
		addressPage.sendKeysToPhoneNumberTextbox("012-345-6789");
		addressBookPage = addressPage.clickSaveAddressButton();

		Assert.assertEquals(addressBookPage.getDefaultBillingAddressPhone(), "012-345-6789");
	}

	@Test(groups = "accountWithAddress", description = "Verify that user can edit the default shipping address")
	public void Address_Book_13_Edit_Default_Shipping_Address() {
		addressBookPage = myAccountPage.clickManageAddressesLink();
		addressPage = addressBookPage.clickChangeShippingAddressLink();
		addressPage.sendKeysToPhoneNumberTextbox("012-345-6789");
		addressBookPage = addressPage.clickSaveAddressButton();

		Assert.assertEquals(addressBookPage.getDefaultShippingAddressPhone(), "012-345-6789");
	}

	@Test(groups = { "addressData",
			"accountWithAddress" }, description = "Verify that user can edit an additional address")
	public void Address_Book_14_Edit_Additional_Address() {
		addressBookPage = myAccountPage.clickManageAddressesLink();
		addressPage = addressBookPage.clickAddNewAddressButton();
		addressPage.completeAddressForm(randomRow);
		String fullAddress = String.join(", ", randomRow[2], randomRow[3], randomRow[4]);
		addressBookPage = addressPage.clickSaveAddressButton();
		addressPage = addressBookPage.clickEditLinkByStreetAddress(fullAddress);
		addressPage.sendKeysToPhoneNumberTextbox("012-345-6789");
		addressBookPage = addressPage.clickSaveAddressButton();

		Assert.assertEquals(addressBookPage.getAdditionalAddressValueByRowIndexAndColumnName("1", "Phone"),
				"012-345-6789");
	}

	@Test(groups = { "addressData",
			"accountWithAddress" }, description = "Verify that user can delete an additional address")
	public void Address_Book_15_Delete_Additional_Address() {
		addressBookPage = myAccountPage.clickManageAddressesLink();
		addressPage = addressBookPage.clickAddNewAddressButton();
		addressPage.completeAddressForm(randomRow);
		String fullAddress = String.join(", ", randomRow[2], randomRow[3], randomRow[4]);
		addressBookPage = addressPage.clickSaveAddressButton();
		addressBookPage.clickDeleteLinkByStreetAddress(fullAddress);
		addressBookPage.clickConfirmationPopupOKButton(driver);

		Assert.assertFalse(addressBookPage.isAdditionalAddressByStreetAddressDisplayed(fullAddress));
	}

	@Test(groups = { "addressData",
			"accountWithoutAddress" }, description = "Verify the information message displayed when there is no additional address")
	public void Address_Book_16_Info_Message_No_Additional_Address() {
		addressPage = myAccountPage.clickEditAddressLinkDefaultBillingAddressSection();
		addressPage.completeAddressForm(randomRow);
		addressBookPage = addressPage.clickSaveAddressButton();

		Assert.assertEquals(addressBookPage.getNoOtherAddressEntriesInfoMessage(),
				"You have no other address entries in your address book.");
	}

	@Test(groups = { "addressData",
			"accountWithAddress" }, description = "Verify that the new address is saved to the address book when the 'Save in address book' is checked")
	public void Address_Book_17_Address_Saved_To_Address_Book_When_Save_In_Address_Book_Checkbox_Is_Checked() {
		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear",
				"Fitness Equipment");
		productListingPage.addProductWithNoOptionsToCart(driver, "Affirm Water Bottle");
		productListingPage.clickShoppingCartIcon(driver);
		checkoutPage = productListingPage.clickProceedToCheckoutButton(driver);
		checkoutPage.clickNewAddressButton();
		String address = data.getAddress();
		checkoutPage.sendKeysToStreetAddressTextbox(address);
		checkoutPage.sendKeysToCityTextbox(data.getCity());
		checkoutPage.selectOptionStateProvinceDropdown(data.getState());
		checkoutPage.sendKeysToZipPostalCodeTextbox(data.getZipCode());
		checkoutPage.sendKeysToPhoneNumberTextbox(data.getPhoneNumber());
		checkoutPage.checkSaveInAddressBookCheckbox();
		checkoutPage.clickShippingAddressPopupShipHereButton();
		checkoutPage.selectShippingMethodRadioButtonByLabel("Fixed");
		checkoutPage.clickNextButton();
		checkoutPage.clickPlaceOrderButton();
		checkoutPage.clickCustomerNameDropdown(driver);
		myAccountPage = checkoutPage.clickMyAccountDropdownLink(driver);
		addressBookPage = myAccountPage.clickManageAddressesLink();

		Assert.assertTrue(addressBookPage.isAdditionalAddressByStreetAddressDisplayed(address));
	}

	@AfterMethod(alwaysRun = true)
	public void logOut(ITestResult result) {
		try {
			accessActions.logOut();
		} catch (Exception e) {
			System.err.println("Error logging out: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@AfterMethod(alwaysRun = true)
	public void afterMethod(ITestResult result) {
		logTestResult(result);
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeBrowserAndDriver();
	}

	private WebDriver driver;
	private FakeDataUtils data;
	private String email, password, firstName, lastName, fullName;
	private String[] randomRow;
	private HomepageObject homepage;
	private CustomerLoginPageObject customerLoginPage;
	private MyAccountPageObject myAccountPage;
	private AddressBookPageObject addressBookPage;
	private AddressPageObject addressPage;
	private ProductListingPageObject productListingPage;
	private CheckoutPageObject checkoutPage;
	private Access accessActions;
}
