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
import pageObjects.PopularSearchTermsPageObject;
import pageObjects.ProductListingPageObject;

public class PopularSearchTerms extends BaseTest {
	@Parameters("browser")
	@BeforeClass
	public void beforeClass(String browser) {
		driver = getBrowserDriver(browser);
		homepage = PageGeneratorManager.getHomepage(driver);

	}

	@Test(description = "Verify that user is directed to the 'Popular Search Terms' page when clicking the 'Search Terms' link in the footer")
	public void Search_01_Popular_Search_Terms_Page_Navigation() {
		popularSearchTermsPage = (PopularSearchTermsPageObject) homepage.clickFooterLinkByLabel(driver, "Search Terms");

		Assert.assertEquals(popularSearchTermsPage.getPageHeader(driver), "Popular Search Terms");
	}

	@Test(description = "Verify that user is directed to the product listing page when clicking any search term in the 'Popular Search Terms' page")
	public void Search_02_Display_Of_Search_Result_When_Clicking_Search_Term() {
		productListingPage = popularSearchTermsPage.clickSearchTermByLabel("Aero Daily Fitness Tee");

		Assert.assertEquals(productListingPage.getPageHeader(driver), "Search results for: 'Aero Daily Fitness Tee'");
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
	private PopularSearchTermsPageObject popularSearchTermsPage;

}
