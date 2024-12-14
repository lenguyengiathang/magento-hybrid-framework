package com.magento.user;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.BasePage;
import commons.BaseTest;
import pageObjects.AdvancedSearchPageObject;
import pageObjects.PopularSearchTermsPageObject;
import pageObjects.ProductListingPageObject;

public class SearchProducts extends BaseTest {
	@Parameters("browser")
	@BeforeClass
	public void beforeClass(String browser) {
		driver = getBrowserDriver(browser);
		basePage = BasePage.getBasePageObject();
	}

	@Test(priority = 1, groups = { "navigation", "popularSearchTerms" })
	public void Search_01_Verify_Popular_Search_Terms_Page_Navigation() {
		popularSearchTermsPage = (PopularSearchTermsPageObject) basePage.clickFooterLinkByLabel(driver, "Search Terms");

		Assert.assertEquals(basePage.getPageHeader(driver), "Popular Search Terms");
	}

	@Test(priority = 2, groups = "popularSearchTerms")
	public void Search_02_Verify_Display_Of_Search_Result_When_Clicking_Search_Term() {
		popularSearchTermsPage.clickSearchTermByLabel("Aero Daily Fitness Tee");

		Assert.assertEquals(basePage.getPageHeader(driver), "Search results for: 'Aero Daily Fitness Tee'");
	}

	@Test(priority = 3)
	public void Search_03_Search_With_Less_Than_3_Characters() {
		productListingPage = basePage.sendKeysToSearchBarAndPressEnter(driver, "pp");

		Assert.assertEquals(productListingPage.getSearchResultMessage(), "Minimum Search query length is 3");
	}

	@Test(priority = 4)
	public void Search_04_No_Search_Results_Found_Warning_Message() {
		basePage.sendKeysToSearchBarAndPressEnter(driver, "???");

		Assert.assertEquals(productListingPage.getSearchResultMessage(), "Your search returned no results.");
	}

	@Test(priority = 5, groups = "basicSearch")
	public void Search_05_Products_Displayed_Corresponding_To_Search_Value() {
		basePage.sendKeysToSearchBarAndPressEnter(driver, "Overnight Duffle");

		Assert.assertEquals(productListingPage.getProductName(), "Overnight Duffle");

		basePage.sendKeysToSearchBarAndPressEnter(driver, "backpack");

		Assert.assertTrue(productListingPage.areProductsDisplayedCorrectly("backpack"));
	}

	@Test(priority = 6)
	public void Search_06_Related_Search_Term_Contains_Search_Value() {
		basePage.sendKeysToSearchBarAndPressEnter(driver, "yoga");

		Assert.assertTrue(productListingPage.areRelatedSearchTermsDisplayedCorrectly("yoga"));
	}

	@Test(priority = 7)
	public void Search_07_Verify_Advanced_Search_Page_Navigation() {
		advancedSearchPage = (AdvancedSearchPageObject) basePage.clickFooterLinkByLabel(driver, "Advanced Search");

		Assert.assertEquals(basePage.getPageHeader(driver), "Advanced Search");
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeBrowserAndDriver();
	}

	private WebDriver driver;
	private BasePage basePage;
	private ProductListingPageObject productListingPage;
	private PopularSearchTermsPageObject popularSearchTermsPage;
	private AdvancedSearchPageObject advancedSearchPage;

}
