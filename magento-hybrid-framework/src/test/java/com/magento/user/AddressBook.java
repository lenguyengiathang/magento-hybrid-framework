package com.magento.user;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
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
import utilities.CSVHelper;

public class AddressBook extends BaseTest {
	@Parameters("browser")
	@BeforeClass
	public void beforeClass(String browser) {
		driver = getBrowserDriver(browser);
		homepage = PageGeneratorManager.getHomepage(driver);

		email = Register.email;
		password = Register.password;
		firstName = Register.firstName;
		lastName = Register.lastName;
		fullName = firstName + " " + lastName;

		customerLoginPage = homepage.clickSignInLink();
		homepage = customerLoginPage.logInAsRegisteredUser(email, password);
	}

	@BeforeMethod(onlyForGroups = "addressData")
	public String[] getRandomAddressData() throws IOException {
		randomRow = CSVHelper.getRandomRowFromCSVFile("src/test/resources/addresses.csv");
		return randomRow;
	}

	@Test(description = "Verify that user is directed to the 'Add New Address' page when clicking the 'Manage Addresses' hyperlink (no address has been added yet)")
	public void Click_Manage_Addresses_Link_No_Address_Yet() {
		homepage.clickCustomerNameDropdown(driver);
		myAccountPage = homepage.clickMyAccountDropdownLink(driver);
		addressBookPage = myAccountPage.clickManageAddressesLink();

		Assert.assertEquals(addressBookPage.getPageHeader(driver), "Add New Address");
	}

	@Test(description = "Verify that user is directed to the 'Address Book' page when clicking the 'Manage Addresses\' hyperlink (at least one address has been added)")
	public void Click_Manage_Addresses_Link_At_Least_One_Address_Added() {
		homepage.clickCustomerNameDropdown(driver);
		myAccountPage = homepage.clickMyAccountDropdownLink(driver);
		addressBookPage = myAccountPage.clickManageAddressesLink();

		Assert.assertEquals(addressBookPage.getPageHeader(driver), "Address Book");
	}

	@Test(description = "Verify that user is directed to 'Add New Address' page when clicking the 'Edit Address' hyperlink (no address has been added yet)")
	public void Click_Edit_Address_Link_No_Address_Yet() {
		homepage.clickCustomerNameDropdown(driver);
		myAccountPage = homepage.clickMyAccountDropdownLink(driver);
		addressPage = myAccountPage.clickEditAddressLinkDefaultBillingAddressSection();

		Assert.assertEquals(addressPage.getPageHeader(driver), "Add New Address");

		addressPage.clickCustomerNameDropdown(driver);
		myAccountPage = addressPage.clickMyAccountDropdownLink(driver);
		addressPage = myAccountPage.clickEditAddressLinkDefaultShippingAddressSection();

		Assert.assertEquals(addressPage.getPageHeader(driver), "Add New Address");
	}

	@Test(description = "Verify that user is directed to 'Add New Address' page when clicking the 'Edit Address' hyperlink (at least one address has been added)")
	public void Click_Edit_Address_Link_At_Least_One_Address_Added() {
		homepage.clickCustomerNameDropdown(driver);
		myAccountPage = homepage.clickMyAccountDropdownLink(driver);
		addressPage = myAccountPage.clickEditAddressLinkDefaultBillingAddressSection();

		Assert.assertEquals(addressPage.getPageHeader(driver), "Edit Address");

		addressPage.clickCustomerNameDropdown(driver);
		myAccountPage = addressPage.clickMyAccountDropdownLink(driver);
		addressPage = myAccountPage.clickEditAddressLinkDefaultShippingAddressSection();

		Assert.assertEquals(addressPage.getPageHeader(driver), "Edit Address");
	}

	@Test(description = "Verify the warning message displayed when an invalid zip or postal code is filled")
	public void Warning_Message_Invalid_Zip_Or_Postal_Code() {
		homepage.clickCustomerNameDropdown(driver);
		myAccountPage = homepage.clickMyAccountDropdownLink(driver);
		addressPage = myAccountPage.clickEditAddressLinkDefaultBillingAddressSection();
		addressPage.selectOptionCountryDropdown("United States");
		addressPage.sendKeysToZipPostalTextbox("123");

		Assert.assertEquals(addressPage.getInvalidZipPostalCodeWarningMessage(),
				"Provided Zip/Postal Code seems to be invalid. Example: 12345-6789; 12345. If you believe it is the right one you can ignore this notice.");
	}

	@Test(groups = "addressData", description = "Verify that user can add a new address (no address has been added yet)")
	public void Add_New_Address_No_Address_Yet() {
		homepage.clickCustomerNameDropdown(driver);
		myAccountPage = homepage.clickMyAccountDropdownLink(driver);
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
		homepage.clickCustomerNameDropdown(driver);
		myAccountPage = homepage.clickMyAccountDropdownLink(driver);
		addressBookPage = myAccountPage.clickManageAddressesLink();
		addressPage = addressBookPage.clickAddNewAddressButton();
		addressPage.completeAddressForm(randomRow);
		addressBookPage = addressPage.clickSaveAddressButton();

		Assert.assertEquals(addressBookPage.getAdditionalAddressValueByRowIndexAndColumnName("1", "First Name"),
				firstName);
		Assert.assertEquals(addressBookPage.getAdditionalAddressValueByRowIndexAndColumnName("1", "Last Name"),
				lastName);
		Assert.assertEquals(addressBookPage.getAdditionalAddressValueByRowIndexAndColumnName("1", "Street Address"),
				randomRow[2] + ", " + randomRow[3] + ", " + randomRow[4]);
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
