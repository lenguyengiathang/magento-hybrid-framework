package com.magento.user;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.magento.commons.Products;

import commons.BaseTest;
import commons.GlobalConstants;
import commons.PageGeneratorManager;
import pageObjects.CompareProductsPageObject;
import pageObjects.HomepageObject;
import pageObjects.ProductDetailsPageObject;
import pageObjects.ProductListingPageObject;

public class CompareProducts extends BaseTest {
	@Parameters("browser")
	@BeforeClass
	public void beforeClass(String browser) {
		driver = getBrowserDriver(browser);
		homepage = PageGeneratorManager.getHomepage(driver);
		productActions = new Products(driver);
	}

	@BeforeMethod
	public void addProductToComparisonList(ITestResult result) {
		productActions.addRandomProductWithoutOptionsToComparisonList();
	}

	@Test(description = "Verify the information message displayed in the 'Compare Products' section when no product is added to the comparison list")
	public void Compare_Products_01_Info_Message_No_Product_In_Comparison_List() {
		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear", "Bags");

		Assert.assertEquals(productListingPage.getEmptyComparisonListInfoMessage(driver),
				"You have no items to compare.");
	}

	@Test(groups = "addProductWithoutOptions", description = "Verify that the 'Compare Products ([number] items)' hyperlink is only displayed when at least one product is added to the comparison list")
	public void Compare_Products_02_Display_Of_Compare_Products_Link() {
		sleepInSecond(GlobalConstants.SHORT_TIMEOUT);
		Assert.assertTrue(homepage.isCompareProductsLinkDisplayed(driver));

		compareProductsPage = homepage.clickCompareProductsLink(driver);
		compareProductsPage.clickCrossIcon();
		compareProductsPage.clickConfirmationPopupOKButton(driver);

		Assert.assertFalse(compareProductsPage.isCompareProductsLinkDisplayed(driver));
	}

	@Test(groups = { "addProductWithoutOptions",
			"clearComparisonList" }, description = "Verify that user is directed to the 'Compare Products' page when clicking the 'Compare Products ([number] item(s))' hyperlink")
	public void Compare_Products_03_Click_Compare_Products_Link() {
		compareProductsPage = homepage.clickCompareProductsLink(driver);

		Assert.assertEquals(compareProductsPage.getPageHeader(driver), "Compare Products");
	}

	@Test(groups = { "addProductWithoutOptions",
			"clearComparisonList" }, description = "Verify that user is directed to the 'Compare Products' page when clicking the 'Compare' button in the 'Compare Products' section")
	public void Compare_Products_04_Click_Compare_Button() {
		compareProductsPage = productListingPage.clickCompareButton(driver);

		Assert.assertEquals(compareProductsPage.getPageHeader(driver), "Compare Products");
	}

	@Test(groups = { "addProductWithoutOptions",
			"clearComparisonList" }, description = "Verify the success message displayed when user adds a product to the comparison list")
	public void Success_Message_Product_Added_To_Comparison_List() {
		Assert.assertEquals(productListingPage.getProductAddedToComparisonListSuccessMessage(),
				"You added product " + productName + " to the comparison list.");
	}

	@Test(groups = { "addProductWithoutOptions",
			"clearComparisonList" }, description = "Verify that user is directed to the 'Compare Products' page when clicking the 'comparison list' hyperlink in the 'You added product [product name] to the comparison list.' success message")
	public void Click_Comparison_List_Link_In_Success_Message() {
		compareProductsPage = productListingPage.clickComparisonListLinkSuccessMessage();

		Assert.assertEquals(compareProductsPage.getPageHeader(driver), "Compare Products");
	}

	@Test(groups = { "addProductWithoutOptions",
			"clearComparisonList" }, description = "Verify that the SKU, description, and activity information are displayed correctly for the corresponding product")
	public void Product_Information_Displayed_In_Comparison_List() {
		productDetailsPage = productListingPage.clickProductLinkByProductName(driver, productName);
		String sku = productDetailsPage.getProductSKU();
		String description = productDetailsPage.getProductDescription();
		productDetailsPage.clickMoreInformationTab();
		String activity = productDetailsPage.getProductActivity();
		compareProductsPage = productDetailsPage.clickCompareProductsLink(driver);

		Assert.assertEquals(compareProductsPage.getProductAttributeValueByProductNameAndLabel(productName, "SKU"), sku);
		Assert.assertEquals(
				compareProductsPage.getProductAttributeValueByProductNameAndLabel(productName, "Description"),
				description);
		Assert.assertEquals(compareProductsPage.getProductAttributeValueByProductNameAndLabel(productName, "Activity"),
				activity);
	}

	@Test(groups = { "addProductWithoutOptions",
			"clearComparisonList" }, description = "Verify that user can add a product without options from the comparison list to shopping cart when clicking the 'Add to Cart' button")
	public void Add_Product_Without_Options_To_Cart() {
		compareProductsPage = productListingPage.clickComparisonListLinkSuccessMessage();
	}

	@AfterMethod(onlyForGroups = "clearComparisonList")
	public void clearComparisonList(ITestResult result) {
		productActions.clearComparisonList();
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeBrowserAndDriver();
	}

	private WebDriver driver;
	private HomepageObject homepage;
	private ProductListingPageObject productListingPage;
	private ProductDetailsPageObject productDetailsPage;
	private CompareProductsPageObject compareProductsPage;
	private Products productActions;
	private String productName;

}
