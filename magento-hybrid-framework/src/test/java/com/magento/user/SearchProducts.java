package com.magento.user;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.BaseTest;
import commons.PageGeneratorManager;
import pageObjects.AdvancedSearchPageObject;
import pageObjects.HomepageObject;
import pageObjects.PopularSearchTermsPageObject;
import pageObjects.ProductListingPageObject;

public class SearchProducts extends BaseTest {
	@Parameters("browser")
	@BeforeClass
	public void beforeClass(String browser) {
		driver = getBrowserDriver(browser);
		homepage = PageGeneratorManager.getHomepage(driver);
	}

	@Test(priority = 1, groups = { "navigation", "popularSearchTerms" })
	public void Search_01_Popular_Search_Terms_Page_Navigation() {
		popularSearchTermsPage = (PopularSearchTermsPageObject) homepage.clickFooterLinkByLabel(driver, "Search Terms");

		Assert.assertEquals(popularSearchTermsPage.getPageHeader(driver), "Popular Search Terms");
	}

	@Test(priority = 2, groups = "popularSearchTerms")
	public void Search_02_Display_Of_Search_Result_When_Clicking_Search_Term() {
		productListingPage = popularSearchTermsPage.clickSearchTermByLabel("Aero Daily Fitness Tee");

		Assert.assertEquals(productListingPage.getPageHeader(driver), "Search results for: 'Aero Daily Fitness Tee'");
	}

	@Test(priority = 3)
	public void Search_03_Search_Bar_Placeholder() {
		Assert.assertEquals(productListingPage.getSearchBarPlaceholder(driver), "Search entire store here...");
	}

	@Test(priority = 4)
	public void Search_04_Search_With_Less_Than_3_Characters() {
		productListingPage.sendKeysToSearchBarAndPressEnter(driver, "pp");

		Assert.assertEquals(productListingPage.getSearchWarningMessage(), "Minimum Search query length is 3");
	}

	@Test(priority = 5)
	public void Search_05_No_Search_Results_Found_Warning_Message() {
		productListingPage.sendKeysToSearchBarAndPressEnter(driver, "???");

		Assert.assertEquals(productListingPage.getSearchWarningMessage(), "Your search returned no results.");
	}

	@Test(priority = 6)
	public void Search_06_Search_Suggestions_Contain_Search_Value() {
		productListingPage.sendKeysToSearchBar(driver, "yoga");

		Assert.assertTrue(productListingPage.areSearchSuggestionsDisplayedCorrectly(driver, "yoga"));
	}

	@Test(priority = 7)
	public void Search_07_Search_Suggestion_Number_Matches_Number_Of_Products_In_Search_Results() {
		Assert.assertTrue(productListingPage.isSuggestionCountMatchingProductCount(driver, "yoga"));
	}

	@Test(priority = 8)
	public void Search_08_Products_Displayed_Corresponding_To_Search_Value() {
		productListingPage.refreshCurrentPage(driver);
		productListingPage.sendKeysToSearchBarAndPressEnter(driver, "Overnight Duffle");

		Assert.assertEquals(productListingPage.getProductName(), "Overnight Duffle");

		productListingPage.sendKeysToSearchBarAndPressEnter(driver, "backpack");

		Assert.assertTrue(productListingPage.areProductsDisplayedCorrectly("backpack"));
	}

	@Test(priority = 9)
	public void Search_09_Related_Search_Term_Contains_Search_Value() {
		productListingPage.sendKeysToSearchBarAndPressEnter(driver, "yoga");

		Assert.assertTrue(productListingPage.areRelatedSearchTermsDisplayedCorrectly("yoga"));
	}

	@Test(priority = 10)
	public void Search_10_Verify_Advanced_Search_Page_Navigation() {
		advancedSearchPage = (AdvancedSearchPageObject) productListingPage.clickFooterLinkByLabel(driver,
				"Advanced Search");

		Assert.assertEquals(advancedSearchPage.getPageHeader(driver), "Advanced Search");
	}

	@Test(priority = 11)
	public void Search_11_Search_With_Empty_Value() {
		advancedSearchPage.clickSearchButton();

		Assert.assertEquals(advancedSearchPage.getEmptySearchValueErrorMessage(), "Enter a search term and try again.");
	}

	@Test(priority = 12)
	public void Search_12_Search_By_Product_Name() {
		advancedSearchPage.sendKeysToProductNameTextbox("Proteus Fitness Jackshirt");
		productListingPage = advancedSearchPage.clickSearchButton();

		Assert.assertEquals(productListingPage.getSearchFoundMessage(),
				"1 item were found using the following search criteria");
		Assert.assertEquals(productListingPage.getSearchCriteria(), "Product Name: Proteus Fitness Jackshirt");

		advancedSearchPage = productListingPage.clickModifyYourSearchLink();
		advancedSearchPage.sendKeysToProductNameTextbox("Yoga Short");
		productListingPage = advancedSearchPage.clickSearchButton();

		Assert.assertEquals(productListingPage.getSearchFoundMessage(),
				"4 items were found using the following search criteria");
		Assert.assertEquals(productListingPage.getSearchCriteria(), "Product Name: Yoga Short");
		Assert.assertTrue(productListingPage.areProductsDisplayedCorrectly("yoga short"));
	}

	@Test(priority = 13)
	public void Search_13_Search_By_SKU() {
		advancedSearchPage = productListingPage.clickModifyYourSearchLink();
		advancedSearchPage.sendKeysToProductNameTextbox("");
		advancedSearchPage.sendKeysToSKUTextbox("WJ06");
		productListingPage = advancedSearchPage.clickSearchButton();

		Assert.assertEquals(productListingPage.getSearchFoundMessage(),
				"1 item were found using the following search criteria");
		Assert.assertEquals(productListingPage.getSearchCriteria(), "SKU: WJ06");
		Assert.assertEquals(productListingPage.getProductName(), "Juno Jacket");

		advancedSearchPage = productListingPage.clickModifyYourSearchLink();
		advancedSearchPage.sendKeysToSKUTextbox("WJ");
		productListingPage = advancedSearchPage.clickSearchButton();

		Assert.assertEquals(productListingPage.getSearchFoundMessage(),
				"12 items were found using the following search criteria");
		Assert.assertEquals(productListingPage.getSearchCriteria(), "SKU: WJ");
		Assert.assertTrue(productListingPage.areProductSKUsDisplayedCorrectly("WJ"));
	}

	@Test(priority = 14)
	public void Search_14_Search_By_Description() {
		advancedSearchPage = productListingPage.clickModifyYourSearchLink();
		advancedSearchPage.sendKeysToSKUTextbox("");
		advancedSearchPage.sendKeysToDescriptionTextbox("yoga kit");
		productListingPage = advancedSearchPage.clickSearchButton();

		Assert.assertEquals(productListingPage.getSearchFoundMessage(),
				"1 item were found using the following search criteria");
		Assert.assertEquals(productListingPage.getSearchCriteria(), "Description: yoga kit");
		Assert.assertEquals(productListingPage.getProductName(), "Sprite Yoga Companion Kit");

		advancedSearchPage = productListingPage.clickModifyYourSearchLink();
		advancedSearchPage.sendKeysToDescriptionTextbox("yoga studio");
		productListingPage = advancedSearchPage.clickSearchButton();

		Assert.assertTrue(productListingPage.areProductDescriptionsDisplayedCorrectly("yoga studio"));
	}

	@Test(priority = 15)
	public void Search_15_Search_By_Price() {
		advancedSearchPage = productListingPage.clickModifyYourSearchLink();
		advancedSearchPage.sendKeysToDescriptionTextbox("");
		advancedSearchPage.sendKeysToPriceFromTextbox("99");
		productListingPage = advancedSearchPage.clickSearchButton();

		Assert.assertEquals(productListingPage.getSearchFoundMessage(),
				"1 item were found using the following search criteria");
		Assert.assertEquals(productListingPage.getSearchCriteria(), "Price: 99 and greater");
	}

	public void Search_16_Search_By_Multiple_Fields() {

	}

	public void Search_17_Warning_Message_Modify_Your_Search() {

	}

	public void Search_18_Error_Message_No_Result_Found() {

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeBrowserAndDriver();
	}

	private WebDriver driver;
	private HomepageObject homepage;
	private ProductListingPageObject productListingPage;
	private PopularSearchTermsPageObject popularSearchTermsPage;
	private AdvancedSearchPageObject advancedSearchPage;

}
