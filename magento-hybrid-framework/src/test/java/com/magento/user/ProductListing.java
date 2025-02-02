package com.magento.user;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.BaseTest;
import commons.PageGeneratorManager;
import pageObjects.HomepageObject;
import pageObjects.ProductListingPageObject;

public class ProductListing extends BaseTest {
	@Parameters("browser")
	@BeforeClass
	public void beforeClass(String browser) {
		driver = getBrowserDriver(browser);
		homepage = PageGeneratorManager.getHomepage(driver);
	}

	@Test(description = "Verify that user can sort products by product name")
	public void Sort_Products_By_Product_Name() {
		productListingPage = homepage.clickNavigationBarDropdownMultiLevelItemLinkByLabels(driver, "Women", "Tops",
				"Jackets");
		productListingPage.selectOptionSortByDropdownByLabel("Product Name");

		Assert.assertTrue(productListingPage.areProductsSortedAscending("Product Name"));

		productListingPage.clickSortArrowIcon();

		Assert.assertTrue(productListingPage.areProductsSortedDescending("Product Name"));
	}

	@Test(description = "Verify that user can sort products by price")
	public void Sort_Products_By_Price() {
		productListingPage = homepage.clickNavigationBarDropdownMultiLevelItemLinkByLabels(driver, "Women", "Tops",
				"Jackets");
		productListingPage.selectOptionSortByDropdownByLabel("Price");

		Assert.assertTrue(productListingPage.areProductsSortedAscending("Price"));

		productListingPage.clickSortArrowIcon();

		Assert.assertTrue(productListingPage.areProductsSortedDescending("Price"));
	}

	@Test(description = "Verify that the number of products displayed per page can be set to 12 (default), 24, or 36 in grid view")
	public void Number_Of_Products_Per_Page_Grid_View() {
		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Women", "Tops");

		Assert.assertTrue(productListingPage.isNumberOfProductsPerPageCorrect(12));

		productListingPage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Women", "Bottoms");
		productListingPage.selectOptionLimiterDropdown(driver, "24");

		Assert.assertTrue(productListingPage.isNumberOfProductsPerPageCorrect(24));

		productListingPage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Women", "Tops");
		productListingPage.selectOptionLimiterDropdown(driver, "36");

		Assert.assertTrue(productListingPage.isNumberOfProductsPerPageCorrect(36));
	}

	@Test(description = "Verify that the number of products displayed per page can be set to 10 (default), 5, 15, 20, or 25 in list view")
	public void Number_Of_Products_Per_Page_List_View() {
		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Women", "Bottoms");
		productListingPage.clickViewToggleButton("List");

		Assert.assertTrue(productListingPage.isNumberOfProductsPerPageCorrect(10));

		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Women", "Tops");
		productListingPage.selectOptionLimiterDropdown(driver, "5");

		Assert.assertTrue(productListingPage.isNumberOfProductsPerPageCorrect(5));

		productListingPage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Women", "Bottoms");
		productListingPage.selectOptionLimiterDropdown(driver, "15");

		Assert.assertTrue(productListingPage.isNumberOfProductsPerPageCorrect(15));

		productListingPage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Women", "Tops");
		productListingPage.selectOptionLimiterDropdown(driver, "20");

		Assert.assertTrue(productListingPage.isNumberOfProductsPerPageCorrect(20));

		productListingPage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Women", "Bottoms");
		productListingPage.selectOptionLimiterDropdown(driver, "25");

		Assert.assertTrue(productListingPage.isNumberOfProductsPerPageCorrect(25));
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeBrowserAndDriver();
	}

	private WebDriver driver;
	private HomepageObject homepage;
	private ProductListingPageObject productListingPage;
}
