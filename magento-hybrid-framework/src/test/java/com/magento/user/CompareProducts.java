package com.magento.user;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.magento.commons.Register;

import commons.BaseTest;
import commons.PageGeneratorManager;
import pageObjects.CustomerLoginPageObject;
import pageObjects.HomepageObject;
import pageObjects.MyAccountPageObject;
import pageObjects.MyWishListPageObject;
import pageObjects.ProductDetailsPageObject;
import pageObjects.ProductListingPageObject;
import utilities.DataHelper;

public class CompareProducts extends BaseTest {
	@Parameters("browser")
	@BeforeClass
	public void beforeClass(String browser) {
		driver = getBrowserDriver(browser);
		homepage = PageGeneratorManager.getHomepage(driver);

		email = Register.email;
		password = Register.password;

		customerLoginPage = homepage.clickSignInLink();
		homepage = customerLoginPage.logInAsRegisteredUser(email, password);
	}

	@Test
	public void Info_Message_No_Product_In_Comparison_List() {
		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear", "Bags");

		Assert.assertEquals(productListingPage.getEmptyComparisonListInfoMessage(driver),
				"You have no items to compare.");
	}

	@Test
	public void Display_Of_Compare_Products_Link() {
		Assert.assertFalse(productListingPage.isCompareProductsLinkDisplayed(driver));

		productListingPage.clickCompareIconByProductName("Overnight Duffle");

		Assert.assertTrue(productListingPage.isCompareProductsLinkDisplayed(driver));
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeBrowserAndDriver();
	}

	private WebDriver driver;
	private DataHelper data;
	private String email, password;
	private HomepageObject homepage;
	private MyAccountPageObject myAccountPage;
	private CustomerLoginPageObject customerLoginPage;
	private MyWishListPageObject myWishListPage;
	private ProductListingPageObject productListingPage;
	private ProductDetailsPageObject productDetailsPage;
}
