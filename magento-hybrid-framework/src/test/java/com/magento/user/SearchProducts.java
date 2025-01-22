package com.magento.user;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
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

	@Test(groups = "popularSearchTerms")
	public void Search_01_Popular_Search_Terms_Page_Navigation() {
		popularSearchTermsPage = (PopularSearchTermsPageObject) homepage.clickFooterLinkByLabel(driver, "Search Terms");

		Assert.assertEquals(popularSearchTermsPage.getPageHeader(driver), "Popular Search Terms");
	}

	@Test(groups = "popularSearchTerms")
	public void Search_02_Display_Of_Search_Result_When_Clicking_Search_Term() {
		productListingPage = popularSearchTermsPage.clickSearchTermByLabel("Aero Daily Fitness Tee");

		Assert.assertEquals(productListingPage.getPageHeader(driver), "Search results for: 'Aero Daily Fitness Tee'");
	}

	@Test()
	public void Search_03_Search_Bar_Placeholder() {
		Assert.assertEquals(homepage.getSearchBarPlaceholder(driver), "Search entire store here...");
	}

	@Test()
	public void Search_04_Search_With_Less_Than_3_Characters() {
		productListingPage = homepage.sendKeysToSearchBarAndPressEnter(driver, "pp");

		Assert.assertEquals(productListingPage.getSearchWarningMessage(), "Minimum Search query length is 3");
	}

	@Test()
	public void Search_05_No_Search_Results_Found_Warning_Message() {
		productListingPage = homepage.sendKeysToSearchBarAndPressEnter(driver, "???");

		Assert.assertEquals(productListingPage.getSearchWarningMessage(), "Your search returned no results.");
	}

	@Test()
	public void Search_06_Search_Suggestions_Contain_Search_Value() {
		homepage.sendKeysToSearchBar(driver, "yoga");

		Assert.assertTrue(homepage.areSearchSuggestionsDisplayedCorrectly(driver, "yoga"));
	}

	@Test()
	public void Search_07_Search_Suggestion_Number_Matches_Number_Of_Products_In_Search_Results() {
		Assert.assertTrue(homepage.isSuggestionCountMatchingProductCount(driver, "yoga"));
	}

	@Test()
	public void Search_08_Products_Displayed_Corresponding_To_Search_Value() {
		homepage.refreshCurrentPage(driver);
		productListingPage = homepage.sendKeysToSearchBarAndPressEnter(driver, "Overnight Duffle");

		Assert.assertEquals(productListingPage.getProductName(), "Overnight Duffle");

		productListingPage.sendKeysToSearchBarAndPressEnter(driver, "backpack");

		Assert.assertTrue(productListingPage.areProductsDisplayedCorrectly("backpack"));
	}

	@Test()
	public void Search_09_Related_Search_Term_Contains_Search_Value() {
		productListingPage = homepage.sendKeysToSearchBarAndPressEnter(driver, "yoga");

		Assert.assertTrue(productListingPage.areRelatedSearchTermsDisplayedCorrectly("yoga"));
	}

	@Test()
	public void Search_10_Verify_Advanced_Search_Page_Navigation() {
		advancedSearchPage = (AdvancedSearchPageObject) homepage.clickFooterLinkByLabel(driver, "Advanced Search");

		Assert.assertEquals(advancedSearchPage.getPageHeader(driver), "Advanced Search");
	}

	@Test
	public void Search_11_Search_With_Empty_Value() {
		advancedSearchPage.refreshCurrentPage(driver);
		advancedSearchPage.clickSearchButton();

		Assert.assertEquals(advancedSearchPage.getEmptySearchValueErrorMessage(), "Enter a search term and try again.");
	}

	@Test
	public void Search_12_Search_By_Product_Name() {
		advancedSearchPage.refreshCurrentPage(driver);
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

	@Test
	public void Search_13_Search_By_SKU() {
		advancedSearchPage = (AdvancedSearchPageObject) homepage.clickFooterLinkByLabel(driver, "Advanced Search");
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

	@Test
	public void Search_14_Search_By_Description() {
		advancedSearchPage = (AdvancedSearchPageObject) homepage.clickFooterLinkByLabel(driver, "Advanced Search");
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

	@Test
	public void Search_15_Search_By_Price() {
		advancedSearchPage = (AdvancedSearchPageObject) homepage.clickFooterLinkByLabel(driver, "Advanced Search");
		advancedSearchPage.sendKeysToPriceFromTextbox("99");
		productListingPage = advancedSearchPage.clickSearchButton();

		Assert.assertEquals(productListingPage.getSearchFoundMessage(),
				"1 item were found using the following search criteria");
		Assert.assertEquals(productListingPage.getSearchCriteria(), "Price: 99 and greater");
	}

	@Test
	public void Search_By_Multiple_Fields() {
		advancedSearchPage = (AdvancedSearchPageObject) homepage.clickFooterLinkByLabel(driver, "Advanced Search");
		advancedSearchPage.sendKeysToProductNameTextbox("Affirm Water Bottle");
		advancedSearchPage.sendKeysToSKUTextbox("24-UG06");
		advancedSearchPage.sendKeysToPriceToTextbox("7");
		productListingPage = advancedSearchPage.clickSearchButton();

		Assert.assertEquals(productListingPage.getSearchFoundMessage(),
				"1 item were found using the following search criteria");
		Assert.assertTrue(productListingPage.areProductsDisplayedCorrectly("Affirm Water Bottle"));
	}

	@Test(groups = "advancedSearch", dependsOnMethods = "Search_By_Multiple_Fields")
	public void Warning_Message_Modify_Your_Search() {
		Assert.assertEquals(productListingPage.getSearchWarningMessage(),
				"Don't see what you're looking for? Modify your search.");
	}

	@Test
	public void Error_Message_No_Result_Found() {
		advancedSearchPage = (AdvancedSearchPageObject) homepage.clickFooterLinkByLabel(driver, "Advanced Search");
		advancedSearchPage.sendKeysToProductNameTextbox("abc");
		productListingPage = advancedSearchPage.clickSearchButton();

		Assert.assertEquals(productListingPage.getNoSearchResultFoundErrorMessage(),
				"We can't find any items matching these search criteria. Modify your search.");
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
	private HomepageObject homepage;
	private ProductListingPageObject productListingPage;
	private PopularSearchTermsPageObject popularSearchTermsPage;
	private AdvancedSearchPageObject advancedSearchPage;
}
