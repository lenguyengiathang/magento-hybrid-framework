package com.magento.user.search;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;

import commons.BaseTest;
import commons.PageGeneratorManager;
import pageObjects.AdvancedSearchPageObject;
import pageObjects.HomepageObject;
import pageObjects.ProductListingPageObject;
import utilities.JsonUtils;

public class AdvancedSearch extends BaseTest {
	@Parameters("browser")
	@BeforeClass
	public void beforeClass(String browser) {
		driver = getBrowserDriver(browser);
		homepage = PageGeneratorManager.getHomepage(driver);
	}

	@BeforeMethod
	public void getProductAttributes(ITestResult result) {
		if (result.getMethod().getMethodName().contains("Advanced_Search_07")) {
			productData = JsonUtils.getRandomProductNoOptions("gear_products.json");
			productNode = productData.get("product");
			productName = productNode.get("product_name").asText();
			productSKU = productNode.get("sku").asText();
			productPrice = productNode.get("price").asText();
			productDescription = productNode.get("price").asText();
		}
	}

	@Test(description = "Verify that user is directed to the 'Advanced Search' page when clicking the 'Advanced Search' link in the footer")
	public void Advanced_Search_01_Verify_Advanced_Search_Page_Navigation() {
		advancedSearchPage = (AdvancedSearchPageObject) homepage.clickFooterLinkByLabel(driver, "Advanced Search");

		Assert.assertEquals(advancedSearchPage.getPageHeader(driver), "Advanced Search");
	}

	@Test(description = "Verify the error message displayed when searching without a value")
	public void Advanced_Search_02_Search_With_Empty_Value() {
		advancedSearchPage.refreshCurrentPage(driver);
		advancedSearchPage.clickSearchButton();

		Assert.assertEquals(advancedSearchPage.getEmptySearchValueErrorMessage(), "Enter a search term and try again.");
	}

	@Test(description = "Verify that the correct product(s) is/are displayed when searching by product name")
	public void Advanced_Search_03_Search_By_Product_Name() {
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

	@Test(description = "Verify that the correct product(s) is/are displayed when searching by SKU")
	public void Advanced_Search_04_Search_By_SKU() {
		advancedSearchPage = (AdvancedSearchPageObject) homepage.clickFooterLinkByLabel(driver, "Advanced Search");
		advancedSearchPage.sendKeysToSKUTextbox(productSKU);
		productListingPage = advancedSearchPage.clickSearchButton();

		Assert.assertEquals(productListingPage.getSearchFoundMessage(),
				"1 item were found using the following search criteria");
		Assert.assertEquals(productListingPage.getSearchCriteria(), "SKU: " + productSKU);
		Assert.assertEquals(productListingPage.getProductName(), productName);

		advancedSearchPage = productListingPage.clickModifyYourSearchLink();
		advancedSearchPage.sendKeysToSKUTextbox("WJ");
		productListingPage = advancedSearchPage.clickSearchButton();

		Assert.assertEquals(productListingPage.getSearchFoundMessage(),
				"12 items were found using the following search criteria");
		Assert.assertEquals(productListingPage.getSearchCriteria(), "SKU: WJ");
		Assert.assertTrue(productListingPage.areProductSKUsDisplayedCorrectly("WJ"));
	}

	@Test(description = "Verify that the correct product(s) is/are displayed when searching by description")
	public void Advanced_Search_05_Search_By_Description() {
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

	@Test(description = "Verify that the correct product(s) is/are displayed when searching by price")
	public void Advanced_Search_06_Search_By_Price() {
		advancedSearchPage = (AdvancedSearchPageObject) homepage.clickFooterLinkByLabel(driver, "Advanced Search");
		advancedSearchPage.sendKeysToPriceFromTextbox("99");
		productListingPage = advancedSearchPage.clickSearchButton();

		Assert.assertEquals(productListingPage.getSearchFoundMessage(),
				"1 item were found using the following search criteria");
		Assert.assertEquals(productListingPage.getSearchCriteria(), "Price: 99 and greater");
		Assert.assertTrue(productListingPage.areProductPricesDisplayedCorrectly());
	}

	@Test(description = "Verify that the correct product(s) is/are displayed when searching by multiple fields")
	public void Advanced_Search_07_Search_By_Multiple_Fields() {
		advancedSearchPage = (AdvancedSearchPageObject) homepage.clickFooterLinkByLabel(driver, "Advanced Search");
		advancedSearchPage.sendKeysToProductNameTextbox(productName);
		advancedSearchPage.sendKeysToSKUTextbox(productSKU);
		advancedSearchPage.sendKeysToPriceToTextbox(productPrice);
		productListingPage = advancedSearchPage.clickSearchButton();

		Assert.assertEquals(productListingPage.getSearchFoundMessage(),
				"1 item were found using the following search criteria");
		Assert.assertTrue(productListingPage.areProductsDisplayedCorrectly(productName));
	}

	@Test(description = "Verify the warning message displayed when search value matches at least one result")
	public void Advanced_Search_08_Warning_Message_Modify_Your_Search() {
		advancedSearchPage = (AdvancedSearchPageObject) homepage.clickFooterLinkByLabel(driver, "Advanced Search");
		advancedSearchPage.sendKeysToProductNameTextbox("Affirm Water Bottle");
		productListingPage = advancedSearchPage.clickSearchButton();

		Assert.assertEquals(productListingPage.getSearchWarningMessage(),
				"Don't see what you're looking for? Modify your search.");
	}

	@Test(description = "Verify the error message displayed when search value matches no result")
	public void Advanced_Search_09_Error_Message_No_Result_Found() {
		advancedSearchPage = (AdvancedSearchPageObject) homepage.clickFooterLinkByLabel(driver, "Advanced Search");
		advancedSearchPage.sendKeysToProductNameTextbox("abc");
		productListingPage = advancedSearchPage.clickSearchButton();

		Assert.assertEquals(productListingPage.getNoSearchResultFoundErrorMessage(),
				"We can't find any items matching these search criteria. Modify your search.");
	}

	@AfterMethod(alwaysRun = true)
	public void navigateToHomepage() {
		try {
			homepage = productListingPage.clickLumaLogo(driver);
		} catch (Exception e) {
			System.err.println("Error navigating to the homepage: " + e.getMessage());
			e.printStackTrace();
		}
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
	private JsonNode productData, productNode;
	private String productName, productSKU, productPrice, productDescription;
	private HomepageObject homepage;
	private ProductListingPageObject productListingPage;
	private AdvancedSearchPageObject advancedSearchPage;
}
