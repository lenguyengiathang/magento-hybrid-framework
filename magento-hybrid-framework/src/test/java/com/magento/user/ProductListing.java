package com.magento.user;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.BasePage;
import commons.BaseTest;
import commons.PageGeneratorManager;
import pageObjects.HomepageObject;
import pageObjects.ProductListingPageObject;

public class ProductListing extends BaseTest {
	@Parameters("browser")
	@BeforeClass
	public void beforeClass(String browser) {
		driver = getBrowserDriver(browser);
		basePage = BasePage.getBasePageObject();
		homepage = PageGeneratorManager.getHomepage(driver);
	}

	@Test
	public void Product_Listing_01_Filter_With_Shopping_Options() {
		productListingPage = basePage.clickNavigationBarDropdownLinkByLabel(driver, "Women", "Tops", "Tees");
		productListingPage.clickShoppingFilterDropdownByLabel("Style");
		String teeProducts = productListingPage.getShoppingFilterOptionProductCount();
		productListingPage.clickShoppingFilterOptionByLabel("Tee");

		Assert.assertEquals(productListingPage.getTotalNumberOfProductItems(), teeProducts);
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeBrowserAndDriver();
	}

	private WebDriver driver;
	private BasePage basePage;
	private HomepageObject homepage;
	private ProductListingPageObject productListingPage;
}
