package com.magento.user;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.BaseTest;
import commons.PageGeneratorManager;
import pageObjects.CompareProductsPageObject;
import pageObjects.CustomerLoginPageObject;
import pageObjects.HomepageObject;
import pageObjects.MyAccountPageObject;
import pageObjects.MyWishListPageObject;
import pageObjects.ProductDetailsPageObject;
import pageObjects.ProductListingPageObject;
import utilities.JsonUtils;

public class CompareProducts extends BaseTest {
	@Parameters("browser")
	@BeforeClass
	public void beforeClass(String browser) {
		driver = getBrowserDriver(browser);
		homepage = PageGeneratorManager.getHomepage(driver);

		email = JsonUtils.getJsonValue("login_data.json", "existing_user.email");
		password = JsonUtils.getJsonValue("login_data.json", "existing_user.password");
		productCategory = "Gear";
		productSubcategory = "Watches";
		productName = "Didi Sport Watch";

		customerLoginPage = homepage.clickSignInLink();
		homepage = customerLoginPage.logInAsRegisteredUser(email, password);
	}

	@BeforeMethod(onlyForGroups = "addProductToComparisonList")
	public void addProductToComparisonList() {
		homepage.addProductWithoutOptionsToComparisonList(driver);
	}

	@Test(description = "Verify the information message displayed in the 'Compare Products' section when no product is added to the comparison list")
	public void Compare_Products_01_Info_Message_No_Product_In_Comparison_List() {
		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, productCategory,
				"Bags");

		Assert.assertEquals(productListingPage.getEmptyComparisonListInfoMessage(driver),
				"You have no items to compare.");
	}

	@Test(groups = { "addProductToComparisonList",
			"clearComparisonList" }, description = "Verify that the 'Compare Products ([number] items)' hyperlink is only displayed when at least one product is added to the comparison list")
	public void Compare_Products_02_Display_Of_Compare_Products_Link() {
		Assert.assertTrue(homepage.isCompareProductsLinkDisplayed(driver));

		compareProductsPage = homepage.clickCompareProductsLink(driver);
		compareProductsPage.clickCrossIcon();
		compareProductsPage.clickOKButtonConfirmationPopup(driver);

		Assert.assertFalse(compareProductsPage.isCompareProductsLinkDisplayed(driver));
	}

	@Test(groups = { "addProductToComparisonList",
			"clearComparisonList" }, description = "Verify that user is directed to the 'Compare Products' page when clicking the 'Compare Products ([number] item(s))' hyperlink")
	public void Compare_Products_03_Click_Compare_Products_Link() {
		compareProductsPage = homepage.clickCompareProductsLink(driver);

		Assert.assertEquals(compareProductsPage.getPageHeader(driver), "Compare Products");
	}

	@Test(groups = { "addProductToComparisonList",
			"clearComparisonList" }, description = "Verify that user is directed to the 'Compare Products' page when clicking the 'Compare' button in the 'Compare Products' section")
	public void Compare_Products_04_Click_Compare_Button() {
		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, productCategory,
				"Bags");
		compareProductsPage = productListingPage.clickCompareButton(driver);

		Assert.assertEquals(compareProductsPage.getPageHeader(driver), "Compare Products");
	}

	@Test(groups = "clearComparisonList", description = "Verify the success message displayed when user adds a product to the comparison list")
	public void Compare_Products_05_Success_Message_Product_Added_To_Comparison_List() {
		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, productCategory,
				productSubcategory);
		productListingPage.clickCompareIconByProductName(driver, "Didi Sport Watch");

		Assert.assertEquals(productListingPage.getProductAddedToComparisonListSuccessMessage(),
				"You added product Didi Sport Watch to the comparison list.");
	}

	@Test(groups = "clearComparisonList", description = "Verify that user is directed to the 'Compare Products' page when clicking the 'comparison list' hyperlink in the 'You added product [product name] to the comparison list.' success message")
	public void Compare_Products_06_Click_Comparison_List_Link_In_Success_Message() {
		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, productCategory,
				productSubcategory);
		productListingPage.clickCompareIconByProductName(driver, "Didi Sport Watch");
		compareProductsPage = productListingPage.clickComparisonListLinkSuccessMessage();

		Assert.assertEquals(compareProductsPage.getPageHeader(driver), "Compare Products");
	}

	@Test(groups = { "addProductToComparisonList",
			"clearComparisonList" }, description = "Verify that the SKU, description, and activity information are displayed correctly for the corresponding product")
	public void Compare_Products_07_Product_Information_Displayed_In_Comparison_List() {
		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, productCategory,
				productSubcategory);
		productDetailsPage = productListingPage.clickProductLinkByProductName(driver, "Didi Sport Watch");
		String sku = productDetailsPage.getProductSKU();
		String description = productDetailsPage.getProductDescription();
		productDetailsPage.clickMoreInformationTab();
		String activity = productDetailsPage.getProductActivity();
		compareProductsPage = productDetailsPage.clickCompareProductsLink(driver);

		Assert.assertEquals(
				compareProductsPage.getProductAttributeValueByProductNameAndLabel("Didi Sport Watch", "SKU"), sku);
		Assert.assertEquals(
				compareProductsPage.getProductAttributeValueByProductNameAndLabel("Didi Sport Watch", "Description"),
				description);
		Assert.assertEquals(
				compareProductsPage.getProductAttributeValueByProductNameAndLabel("Didi Sport Watch", "Activity"),
				activity);
	}

	@Test(groups = { "addProductToComparisonList",
			"clearComparisonList" }, description = "Verify that user can add a product without options from the comparison list to shopping cart when clicking the 'Add to Cart' button")
	public void Compare_Products_08_Add_Product_Without_Options_To_Cart() {
		compareProductsPage = productListingPage.clickComparisonListLinkSuccessMessage();
		compareProductsPage.clickAddToCartButtonByProductName("Didi Sport Watch");

		Assert.assertEquals(compareProductsPage.getProductAddedToCartSuccessMessage(driver),
				"You added Didi Sport Watch to your shopping cart.");
	}

	@Test(description = "Verify the warning message displayed when user clicks the 'Add to Cart' button for any product with options")
	public void Compare_Products_09_Warning_Message_Adding_Product_With_Options_To_Cart() {
		productListingPage = homepage.clickNavigationBarDropdownMultiLevelItemLinkByLabels(driver, "Women", "", "");
		productListingPage.clickCompareIconByProductName(driver, "");
		productListingPage.clickComparisonListLinkSuccessMessage();
	}

	@Test(description = "Verify that user is directed to the 'Compare Products' page when clicking the 'here' hyperlink in the '[product name] has been added to your Wish List. Click here to continue shopping.' success message")
	public void Compare_Products_10_Click_Here_Link_In_Product_Added_To_Wish_List_Success_Message() {
		myWishListPage = compareProductsPage.clickWishListIconByProductName(driver, productName);
		compareProductsPage = (CompareProductsPageObject) myWishListPage.clickHereLinkSuccessMessage();

		Assert.assertEquals(compareProductsPage.getPageHeader(driver), "Compare Products");
	}

	@Test(description = "Verify that the product is removed from the 'Compare Products' page and the 'Compare Products' section when clicking the cross icon next to it in the 'Compare Products' page")
	public void Compare_Products_11_Click_Cross_Icon_For_A_Product_Compare_Products_Page() {
		compareProductsPage.clickCrossIconByProductName(productName);

		Assert.assertTrue(compareProductsPage.isProductNotDisplayedInCompareProductsPage(productName));

		compareProductsPage.clickCustomerNameDropdown(driver);
		myAccountPage = compareProductsPage.clickMyAccountDropdownLink(driver);

		Assert.assertTrue(myAccountPage.isProductNotDisplayedInCompareProductsSection(driver, productName));
	}

	@Test(description = "Verify that the product is removed from the 'Compare Products' page and the 'Compare Products' section when clicking the cross icon next to it in the 'Compare Products' section")
	public void Compare_Products_12_Click_Cross_Icon_For_A_Product_Compare_Products_Section() {
		productListingPage = homepage.clickNavigationBarDropdownMultiLevelItemLinkByLabels(driver, "Men", "Tops",
				"Jackets");
		productListingPage.clickCompareIconByProductName(driver, "Proteus Fitness Jackshirt");
		productListingPage.clickCompareIconByProductName(driver, "Montana Wind Jacket");
		compareProductsPage = productListingPage.clickCompareProductsLink(driver);
	}

	@Test(description = "Verify that all products are removed from the 'Compare Products' section when clicking the 'Clear All' hyperlink in the 'Compare Products' section")
	public void Compare_Products_13_Click_Clear_All_Link_Compare_Products_Section() {
		productListingPage = homepage.clickNavigationBarDropdownMultiLevelItemLinkByLabels(driver, "Women", "Tops",
				"Jackets");
		productListingPage.clickCompareIconByProductName(driver, "Juno Jacket");
		productListingPage.clickCompareIconByProductName(driver, "Neve Studio Dance Jacket");
		productListingPage.clickCompareIconByProductName(driver, "Nadia Elements Shell");
		productListingPage.clickCustomerNameDropdown(driver);
		myAccountPage = productListingPage.clickMyAccountDropdownLink(driver);
		myAccountPage.clickClearAllLinkCompareProductsSection(driver);
		myAccountPage.clickOKButtonConfirmationPopup(driver);

		Assert.assertEquals(myAccountPage.getComparisonListClearedSuccessMessage(driver),
				"You cleared the comparison list.");
		Assert.assertEquals(myAccountPage.getEmptyComparisonListInfoMessage(driver), "You have no items to compare.");
	}

	@Test(description = "Verify the display of the warning message when no product is added to the comparison list")
	public void Compare_Products_14_Warning_Message_No_Product_Added_To_Comparison_List() {
		homepage.clickCustomerNameDropdown(driver);
		myAccountPage = homepage.clickMyAccountDropdownLink(driver);

		Assert.assertEquals(myAccountPage.getEmptyComparisonListInfoMessage(driver), "You have no items to compare.");
	}

	@Test(description = "Verify that non-registered user can add products to the comparison list")
	public void Compare_Products_15_Non_Registered_User_Can_Add_Product_To_Comparison_List() {
		homepage.logOut(driver);
		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, productCategory,
				productSubcategory);
		productListingPage.clickCompareIconByProductName(driver, "Didi Sport Watch");
		compareProductsPage = productListingPage.clickComparisonListLinkSuccessMessage();

		Assert.assertFalse(compareProductsPage.isProductNotDisplayedInCompareProductsPage(productName));
	}

	@AfterMethod(alwaysRun = true, onlyForGroups = "clearComparisonList")
	public void clearComparisonList() {
		homepage.clearComparisonList(driver);
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeBrowserAndDriver();
	}

	private WebDriver driver;
	private HomepageObject homepage;
	private CustomerLoginPageObject customerLoginPage;
	private MyAccountPageObject myAccountPage;
	private ProductListingPageObject productListingPage;
	private ProductDetailsPageObject productDetailsPage;
	private CompareProductsPageObject compareProductsPage;
	private MyWishListPageObject myWishListPage;
	private String email, password, productCategory, productSubcategory, productName;

}
