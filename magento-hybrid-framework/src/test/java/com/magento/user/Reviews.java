package com.magento.user;

import org.openqa.selenium.WebDriver;
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

public class Reviews extends BaseTest {
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
	public void Click_Reviews_Link_In_Product_Card() {
		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear", "Bags");
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
