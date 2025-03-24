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

import com.magento.commons.Login;
import com.magento.commons.Register;

import commons.BaseTest;
import commons.PageGeneratorManager;
import pageObjects.AddressBookPageObject;
import pageObjects.AddressPageObject;
import pageObjects.CreateNewCustomerAccountPageObject;
import pageObjects.CustomerLoginPageObject;
import pageObjects.HomepageObject;
import pageObjects.MyAccountPageObject;
import utilities.CSVUtils;
import utilities.FakeDataUtils;

public class AddressBook_01 extends BaseTest {
	@Parameters("browser")
	@BeforeClass
	public void beforeClass(String browser) {
		driver = getBrowserDriver(browser);
		homepage = PageGeneratorManager.getHomepage(driver);

		data = FakeDataUtils.getDataHelper();
		firstName = Register.firstName;
		lastName = Register.lastName;
		fullName = firstName + " " + lastName;

		homepage.setCookies(driver, Login.cookies);
		homepage.refreshCurrentPage(driver);
	}

	@BeforeMethod(onlyForGroups = "addressData")
	public String[] getRandomAddressData() throws IOException {
		randomRow = CSVUtils.getRandomRowFromCSVFile("src/test/resources/addresses.csv");
		return randomRow;
	}

	@Test(description = "Verify that user is directed to the 'Add New Address' page when clicking the 'Manage Addresses' hyperlink (no address has been added yet)")
	public void Address_Book_01_Click_Manage_Addresses_Link_No_Address_Yet() {
		homepage.clickCustomerNameDropdown(driver);
		myAccountPage = homepage.clickMyAccountDropdownLink(driver);
		addressBookPage = myAccountPage.clickManageAddressesLink();

		Assert.assertEquals(addressBookPage.getPageHeader(driver), "Add New Address");
	}

	@Test(description = "Verify that user is directed to 'Add New Address' page when clicking the 'Edit Address' hyperlink (no address has been added yet)")
	public void Address_Book_02_Click_Edit_Address_Link_No_Address_Yet() {
		homepage.clickCustomerNameDropdown(driver);
		myAccountPage = homepage.clickMyAccountDropdownLink(driver);
		addressPage = myAccountPage.clickEditAddressLinkDefaultBillingAddressSection();

		Assert.assertEquals(addressPage.getPageHeader(driver), "Add New Address");

		addressPage.clickCustomerNameDropdown(driver);
		myAccountPage = addressPage.clickMyAccountDropdownLink(driver);
		addressPage = myAccountPage.clickEditAddressLinkDefaultShippingAddressSection();

		Assert.assertEquals(addressPage.getPageHeader(driver), "Add New Address");
	}

	@Test(groups = "addressData", description = "Verify that user can add a new address (no address has been added yet)")
	public void Address_Book_03_Add_New_Address_No_Address_Yet() {
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

	@Test(groups = "addressData", description = "Verify the information message displayed when there is no additional address")
	public void Address_Book_04_Info_Message_No_Additional_Address() {
		homepage.clickCustomerNameDropdown(driver);
		myAccountPage = homepage.clickMyAccountDropdownLink(driver);
		addressPage = myAccountPage.clickEditAddressLinkDefaultBillingAddressSection();
		addressPage.completeAddressForm(randomRow);
		addressBookPage = addressPage.clickSaveAddressButton();

		Assert.assertEquals(addressBookPage.getNoOtherAddressEntriesInfoMessage(),
				"You have no other address entries in your address book.");
	}

	@Test(description = "Verify that the first added address is automatically set as the default billing and shipping address")
	public void Address_Book_05_First_Address_Automatically_Added_As_Default_Billing_And_Shipping_Address() {
		String firstName = data.getFirstName();
		String lastName = data.getLastName();
		String fullName = firstName + " " + lastName;
		String email = data.getEmailAddress();
		String password = data.getPassword();

		createNewCustomerAccountPage = homepage.clickCreateAnAccountLink();
		createNewCustomerAccountPage.sendKeysToFirstNameTextbox(firstName);
		createNewCustomerAccountPage.sendKeysToLastNameTextbox(lastName);
		createNewCustomerAccountPage.sendKeysToEmailTextbox(email);
		createNewCustomerAccountPage.sendKeysToPasswordTextbox(password);
		createNewCustomerAccountPage.sendKeysToConfirmPasswordTextbox(password);
		myAccountPage = createNewCustomerAccountPage.clickCreateAnAccountButton();
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

	@AfterMethod(alwaysRun = true)
	public void logOut(ITestResult result) {
		if (result.getMethod().getMethodName().contains("Address_Book_04")) {
			homepage.clickCustomerNameDropdown(driver);
			homepage.clickSignOutDropdownLink(driver);
			homepage.refreshCurrentPage(driver);
		}
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeBrowserAndDriver();
	}

	private WebDriver driver;
	private FakeDataUtils data;
	private String firstName, lastName, fullName;
	private String[] randomRow;
	private HomepageObject homepage;
	private CreateNewCustomerAccountPageObject createNewCustomerAccountPage;
	private CustomerLoginPageObject customerLoginPage;
	private MyAccountPageObject myAccountPage;
	private AddressBookPageObject addressBookPage;
	private AddressPageObject addressPage;

}
