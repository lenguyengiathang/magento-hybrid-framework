package com.magento.user;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.BaseTest;
import pageObjects.HomepageObject;
import pageObjects.MyWishListPageObject;
import pageObjects.ProductListingPageObject;

public class WishList extends BaseTest {
	@Parameters("browser")
	@BeforeClass
	public void beforeClass(String browser) {

	}

	@Test
	public void Wish_List_Click_My_Wish_List_Dropdown_Link() {
		homepage.clickCustomerNameDropdown(driver);
		myWishListPage = homepage.clickMyWishListDropdownLink(driver);

		Assert.assertEquals(myWishListPage.getPageHeader(driver), "My Wish List");
	}

	@Test
	public void Wish_List_Click_My_Wish_List_Left_Sidebar() {
		myWishListPage.clickMyAccountSidebarLinkByLabel(driver, "My Wish List");

		Assert.assertEquals(myWishListPage.getPageHeader(driver), "My Wish List");
	}

	@Test
	public void Wish_List_No_Items_In_Wish_List_Info_Message() {
		Assert.assertEquals(myWishListPage.getEmptyWishListInfoMessage(driver), "You have no items in your wish list.");
	}

	@Test
	public void Wish_List_Click_Go_To_Wish_List_Link() {
		productListingPage = myWishListPage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear",
				"Watches");
		myWishListPage = productListingPage.clickWishListIconByProductName("Clamber Watch");
		myWishListPage.clickGoToWishListLink(driver);

		Assert.assertEquals(myWishListPage.getPageHeader(driver), "My Wish List");
	}

	@Test
	public void Wish_List_No_Items_In_Wish_List_Warning_Message() {
		myWishListPage.clickTrashcanIconByProductName("Clamber Watch");

		Assert.assertEquals(myWishListPage.getEmptyWishListWarningMessage(), "You have no items in your wish list.");
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeBrowserAndDriver();
	}

	private WebDriver driver;
	private HomepageObject homepage;
	private MyWishListPageObject myWishListPage;
	private ProductListingPageObject productListingPage;

}
