package com.magento.user.search;

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
import pageObjects.HomepageObject;
import pageObjects.ProductListingPageObject;

public class BasicSearch extends BaseTest {
	@Parameters("browser")
	@BeforeClass
	public void beforeClass(String browser) {
		driver = getBrowserDriver(browser);
		homepage = PageGeneratorManager.getHomepage(driver);
	}

	@Test(description = "Verify the placeholder text of the search bar")
	public void Basic_Search_01_Search_Bar_Placeholder() {
		Assert.assertEquals(homepage.getSearchBarPlaceholder(driver), "Search entire store here...");
	}

	@Test(description = "Verify the warning message displayed when search value is less than 3 characters")
	public void Basic_Search_02_Search_With_Less_Than_3_Characters() {
		productListingPage = homepage.sendKeysToSearchBarAndPressEnter(driver, "pp");

		Assert.assertEquals(productListingPage.getSearchWarningMessage(), "Minimum Search query length is 3");
	}

	@Test(description = "Verify the warning message displayed when search value matches no result")
	public void Basic_Search_03_No_Search_Results_Found_Warning_Message() {
		productListingPage = homepage.sendKeysToSearchBarAndPressEnter(driver, "???");

		Assert.assertEquals(productListingPage.getSearchWarningMessage(), "Your search returned no results.");
	}

	@Test(description = "Verify that search suggestions contain the search value")
	public void Basic_Search_04_Search_Suggestions_Contain_Search_Value() {
		homepage.sendKeysToSearchBar(driver, "yoga");

		Assert.assertTrue(homepage.areSearchSuggestionsDisplayedCorrectly(driver, "yoga"));
	}

	@Test(description = "Verify that the number displayed next to the search suggestion matches the number of products returned in the search results")
	public void Basic_Search_05_Search_Suggestion_Number_Matches_Number_Of_Products_In_Search_Results() {
		Assert.assertTrue(homepage.isSuggestionCountMatchingProductCount(driver, "yoga"));
	}

	@Test(description = "Verify that the correct product(s) is/are displayed corresponding to the search value")
	public void Basic_Search_06_Products_Displayed_Corresponding_To_Search_Value() {
		homepage.refreshCurrentPage(driver);
		productListingPage = homepage.sendKeysToSearchBarAndPressEnter(driver, "Overnight Duffle");

		Assert.assertEquals(productListingPage.getProductName(), "Overnight Duffle");

		productListingPage.sendKeysToSearchBarAndPressEnter(driver, "backpack");

		Assert.assertTrue(productListingPage.areProductsDisplayedCorrectly("backpack"));
	}

	@Test(description = "Verify that each related search term contains the search value")
	public void Basic_Search_07_Related_Search_Term_Contains_Search_Value() {
		productListingPage = homepage.sendKeysToSearchBarAndPressEnter(driver, "yoga");

		Assert.assertTrue(productListingPage.areRelatedSearchTermsDisplayedCorrectly("yoga"));
	}

	@AfterMethod(alwaysRun = true)
	public void afterMethod(ITestResult result) {
		logTestResult(result);
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeBrowserAndDriver();
	}

	private WebDriver driver;
	private HomepageObject homepage;
	private ProductListingPageObject productListingPage;
}
