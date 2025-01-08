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

public class WishList extends BaseTest {
	@Parameters("browser")
	@BeforeClass
	public void beforeClass(String browser) {
		driver = getBrowserDriver(browser);
		homepage = PageGeneratorManager.getHomepage(driver);
		data = DataHelper.getDataHelper();

		email = Register.email;
		password = Register.password;

		customerLoginPage = homepage.clickSignInLink();
		homepage = customerLoginPage.logInAsRegisteredUser(email, password);
	}

	@Test(priority = 1)
	public void Click_My_Wish_List_Dropdown_Link() {
		homepage.clickCustomerNameDropdown(driver);
		myWishListPage = homepage.clickMyWishListDropdownLink(driver);

		Assert.assertEquals(myWishListPage.getPageHeader(driver), "My Wish List");
	}

	@Test(priority = 2)
	public void Click_My_Wish_List_Link_Left_Sidebar() {
		myWishListPage.clickCustomerNameDropdown(driver);
		myAccountPage = myWishListPage.clickMyAccountDropdownLink(driver);
		myWishListPage = (MyWishListPageObject) myAccountPage.clickMyAccountSidebarLinkByLabel(driver, "My Wish List");

		Assert.assertEquals(myWishListPage.getPageHeader(driver), "My Wish List");
	}

	@Test(priority = 3)
	public void No_Items_In_Wish_List_Info_Message() {
		Assert.assertEquals(myWishListPage.getEmptyWishListInfoMessage(driver), "You have no items in your wish list.");
	}

	@Test(priority = 4)
	public void Click_Go_To_Wish_List_Link() {
		productListingPage = myWishListPage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear",
				"Watches");
		myWishListPage = productListingPage.clickWishListIconByProductName("Clamber Watch");
		myWishListPage.clickGoToWishListLink(driver);

		Assert.assertEquals(myWishListPage.getPageHeader(driver), "My Wish List");
	}

	@Test(priority = 5)
	public void No_Items_In_Wish_List_Warning_Message() {
		myWishListPage.clickTrashcanIconByProductName("Clamber Watch");

		Assert.assertEquals(myWishListPage.getEmptyWishListWarningMessage(), "You have no items in your wish list.");
	}

	@Test(priority = 6)
	public void Error_Message_Logged_Out_User_Adding_Products_To_Wish_List() {
		myWishListPage.clickCustomerNameDropdown(driver);
		homepage = myWishListPage.clickSignOutDropdownLink(driver);
		productListingPage = homepage.clickNavigationBarDropdownMultiLevelItemLinkByLabels(driver, "Women", "Tops",
				"Bras & Tanks");
		productListingPage.clickWishListIconByProductName("Zoe Tank");

		Assert.assertEquals(customerLoginPage.getLoginErrorMessage(),
				"You must login or register to add items to your wishlist.");
	}

	@Test(priority = 7)
	public void Add_Product_To_Wish_List() {
		homepage = customerLoginPage.logInAsRegisteredUser(email, password);
		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear",
				"Fitness Equipment");
		myWishListPage = productListingPage.clickWishListIconByProductName("Dual Handle Cardio Ball");

		Assert.assertEquals(myWishListPage.getAddProductToWishListSuccessMessage(),
				"Dual Handle Cardio Ball has been added to your Wish List. Click here to continue shopping.");
	}

	@Test(priority = 8)
	public void Click_Here_Link_In_Success_Message() {
		productListingPage = myWishListPage.clickSuccessMessageHereLink();

		Assert.assertEquals(productListingPage.getPageHeader(driver), "Fitness Equipment");
	}

	@Test(priority = 9)
	public void Add_Comment_For_Product() {
		productListingPage.clickCustomerNameDropdown(driver);
		myWishListPage = productListingPage.clickMyWishListDropdownLink(driver);
		myWishListPage.sendKeysToCommentTextbox("Dual Handle Cardio Ball", "comment");
		myWishListPage.clickUpdateWishListButton();

		Assert.assertEquals(myWishListPage.getCommentValueByProductName("Dual Handle Cardio Ball"), "comment");
	}

	@Test(priority = 10)
	public void Change_Product_Quantity() {
		myWishListPage.sendKeysToQuantityTextboxByProductName("Dual Handle Cardio Ball", "5");
		myWishListPage.clickUpdateWishListButton();

		Assert.assertEquals(myWishListPage.getQuantityValueByProductName("Dual Handle Cardio Ball"), "5");
	}

	@Test(priority = 11)
	public void Share_Wish_List() {
		myWishListPage.clickShareWishListButton();
		myWishListPage.sendKeysToEmailAddressesSeparatedByCommasTextbox(
				data.getEmailAddress() + ", " + data.getEmailAddress());
		myWishListPage.clickShareWishListButton();

		Assert.assertEquals(myWishListPage.getShareWishListSuccessMessage(), "Your wish list has been shared.");
	}

	@Test(priority = 12)
	public void Add_Product_Without_Options_To_Shopping_Cart() {
		productListingPage = myWishListPage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear",
				"Fitness Equipment");
		myWishListPage = productListingPage.clickWishListIconByProductName("Sprite Foam Yoga Brick");
		myWishListPage.clickAddToCartButtonByProductNameWithOptions("Sprite Foam Yoga Brick");

		Assert.assertEquals(myWishListPage.getProductAddedToShoppingCartSuccessMessage(),
				"You added Sprite Foam Yoga Brick to your shopping cart.");

		productListingPage = myWishListPage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear",
				"Fitness Equipment");
		myWishListPage = productListingPage.clickWishListIconByProductName("Sprite Foam Yoga Brick");
		myWishListPage.clickAddToCartButtonByProductNameWithoutOptions(driver, "Sprite Foam Yoga Brick");

		Assert.assertEquals(myWishListPage.getProductAddedToShoppingCartSuccessMessage(),
				"You added Sprite Foam Yoga Brick to your shopping cart.");
	}

	@Test(priority = 13)
	public void Add_Product_With_Options_To_Shopping_Cart() {
		productListingPage = myWishListPage.clickNavigationBarDropdownMultiLevelItemLinkByLabels(driver, "Women",
				"Bottoms", "Shorts");
		myWishListPage = productListingPage.clickWishListIconByProductName("Erika Running Short");
		productDetailsPage = myWishListPage.clickAddToCartButtonByProductNameWithOptions("Erika Running Short");

		Assert.assertEquals(productDetailsPage.getChooseOptionsForItemWarningMessage(),
				"You need to choose options for your item.");

		productListingPage = productDetailsPage.clickNavigationBarDropdownMultiLevelItemLinkByLabels(driver, "Women",
				"Bottoms", "Shorts");
		myWishListPage = productListingPage.clickWishListIconByProductName("Erika Running Short");
		productDetailsPage = myWishListPage.clickAddToCartButtonByProductNameWithOptions(driver, "Erika Running Short");

		Assert.assertEquals(productDetailsPage.getChooseOptionsForItemWarningMessage(),
				"You need to choose options for your item.");
	}

	@Test(priority = 14)
	public void Change_Product_Quantity_From_Product_Details_Page() {
		productDetailsPage.sendKeysToQuantityTextbox("10");
		myWishListPage = productDetailsPage.clickUpdateWishListButton();

		Assert.assertEquals(myWishListPage.getQuantityValueByProductName("Erika Running Short"), "10");
	}

	@Test(priority = 15)
	public void Product_Removed_From_Wish_List_After_Being_Added_To_Shopping_Cart() {
		productListingPage = myWishListPage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear",
				"Watches");
		myWishListPage = productListingPage.clickWishListIconByProductName("Dash Digital Watch");
		myWishListPage.clickAddToCartButtonByProductNameWithoutOptions("Dash Digital Watch");

		Assert.assertEquals(myWishListPage.getProductAddedToShoppingCartSuccessMessage(),
				"You added Dash Digital Watch to your shopping cart.");
		Assert.assertTrue(myWishListPage.isProductNotDisplayedInMyWishListPage("Dash Digital Watch"));
	}

	@Test(priority = 16)
	public void Add_All_Products_Without_Options_To_Shopping_Cart() {
		productListingPage = myWishListPage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear",
				"Watches");
		myWishListPage = productListingPage.clickWishListIconByProductName("Dash Digital Watch");
		productListingPage = myWishListPage.clickSuccessMessageHereLink();
		myWishListPage = productListingPage.clickWishListIconByProductName("Luma Analog Watch");
		myWishListPage.clickAddAllToCartButton();

		Assert.assertEquals(myWishListPage.getAllProductsAddedToShoppingCartSuccessMessage(),
				"2 product(s) have been added to shopping cart: \"Dash Digital Watch\", \"Luma Analog Watch\".");
	}

	@Test(priority = 17)
	public void Remove_Product_From_Wish_List_Page() {
		productListingPage = myWishListPage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear",
				"Watches");
		myWishListPage = productListingPage.clickWishListIconByProductName("Dash Digital Watch");
		myWishListPage.clickTrashcanIconByProductName("Dash Digital Watch");

		Assert.assertEquals(myWishListPage.getProductRemovedFromWishListSuccessMessage(),
				"Dash Digital Watch has been removed from your Wish List.");
		Assert.assertTrue(myWishListPage.isProductNotDisplayedInMyWishListPage("Dash Digital Watch"));
		Assert.assertTrue(myWishListPage.isProductNotDisplayedInMyWishListSection(driver, "Dash Digital Watch"));
	}

	@Test(priority = 18)
	public void Remove_Product_From_Wish_List_Section() {
		productListingPage = myWishListPage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear",
				"Watches");
		myWishListPage = productListingPage.clickWishListIconByProductName("Dash Digital Watch");
		myWishListPage.clickCrossIconByProductName(driver, "Dash Digital Watch");

		Assert.assertEquals(myWishListPage.getProductRemovedFromWishListSuccessMessage(),
				"Dash Digital Watch has been removed from your Wish List.");
		Assert.assertTrue(myWishListPage.isProductNotDisplayedInMyWishListPage("Dash Digital Watch"));
		Assert.assertTrue(myWishListPage.isProductNotDisplayedInMyWishListSection(driver, "Dash Digital Watch"));
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
