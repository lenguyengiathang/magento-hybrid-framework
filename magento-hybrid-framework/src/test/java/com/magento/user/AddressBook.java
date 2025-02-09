package com.magento.user;

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

import com.magento.commons.Register;

import commons.BaseTest;
import commons.PageGeneratorManager;
import pageObjects.AddressBookPageObject;
import pageObjects.AddressPageObject;
import pageObjects.CustomerLoginPageObject;
import pageObjects.HomepageObject;
import pageObjects.MyAccountPageObject;
import utilities.CSVUtils;
import utilities.JsonUtils;

public class AddressBook extends BaseTest {
	@Parameters("browser")
	@BeforeClass
	public void beforeClass(String browser) {
		driver = getBrowserDriver(browser);
		homepage = PageGeneratorManager.getHomepage(driver);
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

		customerLoginPage = homepage.clickSignInLink();
		homepage = customerLoginPage.logInAsRegisteredUser(email, password);
		homepage.clickCustomerNameDropdown(driver);
		myAccountPage = homepage.clickMyAccountDropdownLink(driver);
	}

	@BeforeMethod(onlyForGroups = "accountWithAddress")
	public void logInExistingAccountWithAddresses() {
		String fileName = "login_data.json";
		String email = JsonUtils.getJsonValue(fileName, "email");
		String password = JsonUtils.getJsonValue(fileName, "password");

		customerLoginPage = homepage.clickSignInLink();
		homepage = customerLoginPage.logInAsRegisteredUser(email, password);
		homepage.clickCustomerNameDropdown(driver);
		myAccountPage = homepage.clickMyAccountDropdownLink(driver);
	}

	@Test(description = "Verify that user is directed to the 'Add New Address' page when clicking the 'Manage Addresses' hyperlink (no address has been added yet)")
	public void Click_Manage_Addresses_Link_No_Address_Yet() {
		addressBookPage = myAccountPage.clickManageAddressesLink();

		Assert.assertEquals(addressBookPage.getPageHeader(driver), "Add New Address");
	}

	@Test(description = "Verify that user is directed to the 'Address Book' page when clicking the 'Manage Addresses\' hyperlink (at least one address has been added)")
	public void Click_Manage_Addresses_Link_At_Least_One_Address_Added() {
		addressBookPage = myAccountPage.clickManageAddressesLink();

		Assert.assertEquals(addressBookPage.getPageHeader(driver), "Address Book");
	}

	@Test(description = "Verify that user is directed to 'Add New Address' page when clicking the 'Edit Address' hyperlink (no address has been added yet)")
	public void Click_Edit_Address_Link_No_Address_Yet() {
		addressPage = myAccountPage.clickEditAddressLinkDefaultBillingAddressSection();

		Assert.assertEquals(addressPage.getPageHeader(driver), "Add New Address");

		addressPage.clickCustomerNameDropdown(driver);
		myAccountPage = addressPage.clickMyAccountDropdownLink(driver);
		addressPage = myAccountPage.clickEditAddressLinkDefaultShippingAddressSection();

		Assert.assertEquals(addressPage.getPageHeader(driver), "Add New Address");
	}

	@Test(description = "Verify that user is directed to 'Add New Address' page when clicking the 'Edit Address' hyperlink (at least one address has been added)")
	public void Click_Edit_Address_Link_At_Least_One_Address_Added() {
		addressPage = myAccountPage.clickEditAddressLinkDefaultBillingAddressSection();

		Assert.assertEquals(addressPage.getPageHeader(driver), "Edit Address");

		addressPage.clickCustomerNameDropdown(driver);
		myAccountPage = addressPage.clickMyAccountDropdownLink(driver);
		addressPage = myAccountPage.clickEditAddressLinkDefaultShippingAddressSection();

		Assert.assertEquals(addressPage.getPageHeader(driver), "Edit Address");
	}

	@Test(description = "Verify the warning message displayed when an invalid zip or postal code is filled")
	public void Warning_Message_Invalid_Zip_Or_Postal_Code() {
		addressPage = myAccountPage.clickEditAddressLinkDefaultBillingAddressSection();
		addressPage.selectOptionCountryDropdown("United States");
		addressPage.sendKeysToZipPostalTextbox("123");

		Assert.assertEquals(addressPage.getInvalidZipPostalCodeWarningMessage(),
				"Provided Zip/Postal Code seems to be invalid. Example: 12345-6789; 12345. If you believe it is the right one you can ignore this notice.");
	}

	@Test(groups = "addressData", description = "Verify that user can add a new address (no address has been added yet)")
	public void Add_New_Address_No_Address_Yet() {
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

	@Test(groups = "addressData", description = "Verify that user can add a new address (no address has been added yet)")
	public void Add_New_Address_At_Least_One_Address_Added() {
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

	@Test(description = "Verify that user can set an address as the default billing address")
	public void Set_Default_Billing_Address() {
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

	@Test(description = "Verify that user can set an address as the default shipping address")
	public void Set_Default_Shipping_Address() {
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

	@Test(description = "Verify the information message displayed when an address is set as the default billing and/or shipping address")
	public void Default_Billing_Shipping_Address_Info_Message() {
		addressPage = myAccountPage.clickEditAddressLinkDefaultBillingAddressSection();
		addressPage.completeAddressForm(randomRow);
		addressBookPage = addressPage.clickSaveAddressButton();
		addressPage = addressBookPage.clickChangeBillingAddressLink();

		Assert.assertEquals(addressPage.getDefaultAddressInfoMessageByIndex("1"), "It's a default billing address.");
		Assert.assertEquals(addressPage.getDefaultAddressInfoMessageByIndex("2"), "It's a default shipping address.");
	}

	@Test(description = "Verify that user can edit the default billing address")
	public void Edit_Default_Billing_Address() {
		addressBookPage = myAccountPage.clickManageAddressesLink();
		addressPage = addressBookPage.clickChangeBillingAddressLink();
		addressPage.sendKeysToPhoneNumberTextbox("012-345-6789");
		addressBookPage = addressPage.clickSaveAddressButton();

		Assert.assertEquals(addressBookPage.getDefaultBillingAddressPhone(), "012-345-6789");
	}

	@Test(description = "Verify that user can edit the default shipping address")
	public void Edit_Default_Shipping_Address() {
		addressBookPage = myAccountPage.clickManageAddressesLink();
		addressPage = addressBookPage.clickChangeShippingAddressLink();
		addressPage.sendKeysToPhoneNumberTextbox("012-345-6789");
		addressBookPage = addressPage.clickSaveAddressButton();

		Assert.assertEquals(addressBookPage.getDefaultShippingAddressPhone(), "012-345-6789");
	}

	@Test(description = "Verify that user can edit an additional address")
	public void Edit_Additional_Address() {
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

	@Test(description = "Verify that user can delete an additional address")
	public void Delete_Additional_Address() {
		addressBookPage = myAccountPage.clickManageAddressesLink();
		addressPage = addressBookPage.clickAddNewAddressButton();
		addressPage.completeAddressForm(randomRow);
		String fullAddress = String.join(", ", randomRow[2], randomRow[3], randomRow[4]);
		addressBookPage = addressPage.clickSaveAddressButton();
		addressBookPage.clickDeleteLinkByStreetAddress(fullAddress);
		addressBookPage.clickConfirmationPopupOKButton(driver);

		Assert.assertTrue(addressBookPage.isAdditionalAddressByStreetAddressNotDisplayed(fullAddress));
	}

	@Test(description = "Verify the information message displayed when there is no additional address")
	public void Info_Message_No_Additional_Address() {
		addressPage = myAccountPage.clickEditAddressLinkDefaultBillingAddressSection();
		addressPage.completeAddressForm(randomRow);
		addressBookPage = addressPage.clickSaveAddressButton();

		Assert.assertEquals(addressBookPage.getNoOtherAddressEntriesInfoMessage(),
				"You have no other address entries in your address book.");
	}

	@AfterMethod(alwaysRun = true)
	public void logTestResult(ITestResult result) {
		int status = result.getStatus();
		switch (status) {
		case ITestResult.SUCCESS:
			System.out.println("Test passed: " + result.getMethod().getDescription());
			break;
		case ITestResult.FAILURE:
			System.out.println("Test failed: " + result.getMethod().getDescription());
			break;
		case ITestResult.SKIP:
			System.out.println("Test skipped: " + result.getMethod().getDescription());
			break;
		default:
			System.out.println("Unknown status: " + result.getMethod().getDescription());
			break;
		}
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeBrowserAndDriver();
	}

	private WebDriver driver;
	private String email, password, firstName, lastName, fullName;
	private String[] randomRow;
	private HomepageObject homepage;
	private CustomerLoginPageObject customerLoginPage;
	private MyAccountPageObject myAccountPage;
	private AddressBookPageObject addressBookPage;
	private AddressPageObject addressPage;
}
