package com.magento.user;

import java.util.Arrays;
import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.magento.commons.Access;
import com.magento.commons.Products;
import com.magento.commons.Register;

import commons.BaseTest;
import commons.PageGeneratorManager;
import pageObjects.CompareProductsPageObject;
import pageObjects.CustomerLoginPageObject;
import pageObjects.HomepageObject;
import pageObjects.MyAccountPageObject;
import pageObjects.MyWishListPageObject;
import pageObjects.ProductDetailsPageObject;
import pageObjects.ProductListingPageObject;
import pageObjects.ShoppingCartPageObject;
import utilities.DataHelper;

public class WishList extends BaseTest {
	@Parameters("browser")
	@BeforeClass
	public void beforeClass(String browser) {
		driver = getBrowserDriver(browser);
		homepage = PageGeneratorManager.getHomepage(driver);
		productActions = new Products(driver);
		data = DataHelper.getDataHelper();

		email = Register.email;
		password = Register.password;

		customerLoginPage = homepage.clickSignInLink();
		homepage = customerLoginPage.logInAsRegisteredUser(email, password);
	}

	@BeforeMethod
	public void addProductToWishList(ITestResult result) {
		String[] groups = result.getMethod().getGroups();
		Random random = new Random();

		for (String group : groups) {
			switch (group) {
			case "addProductWithoutOptions":
				productActions.addProductWithoutOptionsToWishList();
				break;
			case "addProductWithOptions":
				String groupChoice = random.nextBoolean() ? "Men" : "Women";
				productActions.addProductWithOptionsToWishList(groupChoice);
				break;
			default:
				continue;
			}
			productName = Products.productName;
			System.out.println("The @BeforeMethod executed successfully: " + productName
					+ " is added to the wish list successfully.");
			break;
		}
	}

	@BeforeMethod
	public void logOut(ITestResult result) {
		Access accessActions = new Access(driver);
		if (result.getMethod().getMethodName().equals("Error_Message_Logged_Out_User_Adding_Products_To_Wish_List")) {
			accessActions.logOut();
			System.out.println("User is logged out successfully.");
		}
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
		myWishListPage = productListingPage.clickWishListIconByProductName(driver, "Clamber Watch");
		myWishListPage.clickGoToWishListLink(driver);

		Assert.assertEquals(myWishListPage.getPageHeader(driver), "My Wish List");
	}

	@Test(priority = 5)
	public void No_Items_In_Wish_List_Warning_Message() {
		myWishListPage.clickTrashcanIconByProductName("Clamber Watch");

		Assert.assertEquals(myWishListPage.getEmptyWishListWarningMessage(), "You have no items in your wish list.");
	}

	@Test(priority = 6, groups = "clearWishList")
	public void Click_Wish_List_Icon() {
		homepage = myWishListPage.clickLumaLogo(driver);
		homepage.scrollToBottom(driver);
		myWishListPage = homepage.clickWishListIconByProductName(driver, "Radiant Tee");

		Assert.assertEquals(myWishListPage.getPageHeader(driver), "My Wish List");
		Assert.assertFalse(myWishListPage.isProductNotDisplayedInMyWishListPage("Radiant Tee"));

		productListingPage = myWishListPage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear",
				"Fitness Equipment");
		myWishListPage = productListingPage.clickWishListIconByProductName(driver, "Dual Handle Cardio Ball");

		Assert.assertEquals(myWishListPage.getPageHeader(driver), "My Wish List");
		Assert.assertFalse(myWishListPage.isProductNotDisplayedInMyWishListPage("Dual Handle Cardio Ball"));

		productListingPage = myWishListPage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear",
				"Bags");
		productListingPage.clickCompareIconByProductName(driver, "Compete Track Tote");
		compareProductsPage = productListingPage.clickCompareProductsLink(driver);
		myWishListPage = compareProductsPage.clickWishListIconByProductName(driver, "Compete Track Tote");

		Assert.assertEquals(myWishListPage.getPageHeader(driver), "My Wish List");
		Assert.assertFalse(myWishListPage.isProductNotDisplayedInMyWishListPage("Compete Track Tote"));
	}

	@Test(priority = 7, groups = "clearWishList")
	public void Success_Message_When_Clicking_Wish_List_Icon() {
		homepage = myWishListPage.clickLumaLogo(driver);
		homepage.scrollToBottom(driver);
		myWishListPage = homepage.clickWishListIconByProductName(driver, "Breathe-Easy Tank");

		Assert.assertEquals(myWishListPage.getAddProductToWishListSuccessMessage(),
				"Breathe-Easy Tank has been added to your Wish List. Click here to continue shopping.");

		productListingPage = myWishListPage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear",
				"Fitness Equipment");
		myWishListPage = productListingPage.clickWishListIconByProductName(driver, "Dual Handle Cardio Ball");

		Assert.assertEquals(myWishListPage.getAddProductToWishListSuccessMessage(),
				"Dual Handle Cardio Ball has been added to your Wish List. Click here to continue shopping.");

		productListingPage = myWishListPage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear",
				"Bags");
		productListingPage.clickCompareIconByProductName(driver, "Rival Field Messenger");
		compareProductsPage = productListingPage.clickCompareProductsLink(driver);
		myWishListPage = compareProductsPage.clickWishListIconByProductName(driver, "Rival Field Messenger");

		Assert.assertEquals(myWishListPage.getAddProductToWishListSuccessMessage(),
				"Rival Field Messenger has been added to your Wish List. Click here to continue shopping.");
	}

	@Test(priority = 8, groups = "clearWishList")
	public void Click_Here_Link_In_Success_Message() {
		homepage = myWishListPage.clickLumaLogo(driver);
		homepage.scrollToBottom(driver);
		myWishListPage = homepage.clickWishListIconByProductName(driver, "Breathe-Easy Tank");
		homepage = (HomepageObject) myWishListPage.clickSuccessMessageHereLink();

		Assert.assertEquals(homepage.getPageHeader(driver), "Home Page");

		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear",
				"Fitness Equipment");
		myWishListPage = productListingPage.clickWishListIconByProductName(driver, "Dual Handle Cardio Ball");
		productListingPage = (ProductListingPageObject) myWishListPage.clickSuccessMessageHereLink();

		Assert.assertEquals(productListingPage.getPageHeader(driver), "Fitness Equipment");

		productListingPage = myWishListPage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear",
				"Bags");
		productListingPage.clickCompareIconByProductName(driver, "Rival Field Messenger");
		compareProductsPage = productListingPage.clickCompareProductsLink(driver);
		myWishListPage = compareProductsPage.clickWishListIconByProductName(driver, "Rival Field Messenger");
		compareProductsPage = (CompareProductsPageObject) myWishListPage.clickSuccessMessageHereLink();

		Assert.assertEquals(compareProductsPage.getPageHeader(driver), "Compare Products");
	}

	@Test(priority = 9, groups = "clearWishList")
	public void Click_Add_To_Wish_List_Link() {
		productListingPage = homepage.clickNavigationBarDropdownMultiLevelItemLinkByLabels(driver, "Women", "Tops",
				"Hoodies & Sweatshirts");
		productDetailsPage = productListingPage.clickProductLinkByProductName(driver, "Circe Hooded Ice Fleece");
		myWishListPage = productDetailsPage.clickAddToWishlistLink();

		Assert.assertEquals(myWishListPage.getPageHeader(driver), "My Wish List");
		Assert.assertFalse(myWishListPage.isProductNotDisplayedInMyWishListPage("Circe Hooded Ice Fleece"));
	}

	@Test(priority = 10, groups = "clearWishList")
	public void Success_Message_When_Clicking_Add_To_Wish_List_Link() {
		productListingPage = homepage.clickNavigationBarDropdownMultiLevelItemLinkByLabels(driver, "Women", "Tops",
				"Bras & Tanks");
		productDetailsPage = productListingPage.clickProductLinkByProductName(driver, "Prima Compete Bra Top");
		myWishListPage = productDetailsPage.clickAddToWishlistLink();

		Assert.assertEquals(myWishListPage.getAddProductToWishListSuccessMessage(),
				"Prima Compete Bra Top has been added to your Wish List. Click here to continue shopping.");
	}

	@Test(priority = 11, groups = { "addProductWithoutOptions", "clearWishList" })
	public void Add_Comment_For_Product() {
		myWishListPage.sendKeysToCommentTextbox(productName, "comment");
		myWishListPage.clickUpdateWishListButton();

		Assert.assertEquals(myWishListPage.getCommentValueByProductName(productName), "comment");
	}

	@Test(priority = 12, groups = { "addProductWithoutOptions", "clearWishList" })
	public void Change_Product_Quantity() {
		myWishListPage.sendKeysToQuantityTextboxByProductName(productName, "5");
		myWishListPage.clickUpdateWishListButton();

		Assert.assertEquals(myWishListPage.getQuantityValueByProductName(productName), "5");
	}

	@Test(priority = 13, groups = { "addProductWithoutOptions", "clearWishList" })
	public void Share_Wish_List() {
		myWishListPage.clickShareWishListButton();
		myWishListPage.sendKeysToEmailAddressesSeparatedByCommasTextbox(
				data.getEmailAddress() + ", " + data.getEmailAddress());
		myWishListPage.clickShareWishListButton();

		Assert.assertEquals(myWishListPage.getShareWishListSuccessMessage(), "Your wish list has been shared.");
	}

	@Test(priority = 14, groups = { "addProductWithoutOptions", "clearWishList" })
	public void Add_Product_Without_Options_To_Shopping_Cart() {
		myWishListPage.clickAddToCartButtonByProductNameWithOptions(productName);

		Assert.assertEquals(myWishListPage.getProductAddedToShoppingCartSuccessMessage(),
				"You added " + productName + " to your shopping cart.");

		productListingPage = myWishListPage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear",
				"Bags");
		myWishListPage = productListingPage.clickWishListIconByProductName(driver, "Impulse Duffle");
		myWishListPage.clickAddToCartButtonByProductNameWithoutOptions(driver, "Impulse Duffle");

		Assert.assertEquals(myWishListPage.getProductAddedToShoppingCartSuccessMessage(),
				"You added Impulse Duffle to your shopping cart.");
	}

	@Test(priority = 15, groups = "addProductWithOptions")
	public void Error_Message_Adding_Product_With_Options_To_Shopping_Cart() {
		productDetailsPage = myWishListPage.clickAddToCartButtonByProductNameWithOptions("Erika Running Short");

		Assert.assertEquals(productDetailsPage.getChooseOptionsForItemWarningMessage(),
				"You need to choose options for your item.");

		productListingPage = productDetailsPage.clickNavigationBarDropdownMultiLevelItemLinkByLabels(driver, "Women",
				"Bottoms", "Shorts");
		myWishListPage = productListingPage.clickWishListIconByProductName(driver, "Erika Running Short");
		productDetailsPage = myWishListPage.clickAddToCartButtonByProductNameWithOptions(driver, "Erika Running Short");

		Assert.assertEquals(productDetailsPage.getChooseOptionsForItemWarningMessage(),
				"You need to choose options for your item.");
	}

	@Test(priority = 16, groups = { "addProductWithoutOptions", "clearWishList" })
	public void Change_Product_Quantity_From_Product_Details_Page() {
		productDetailsPage.sendKeysToQuantityTextbox("10");
		myWishListPage = productDetailsPage.clickUpdateWishListButton();

		Assert.assertEquals(myWishListPage.getQuantityValueByProductName(productName), "10");
	}

	@Test(priority = 17, groups = "addProductWithoutOptions")
	public void Product_Removed_From_Wish_List_After_Being_Added_To_Shopping_Cart() {
		myWishListPage.clickAddToCartButtonByProductNameWithoutOptions(productName);

		Assert.assertEquals(myWishListPage.getProductAddedToShoppingCartSuccessMessage(),
				"You added " + productName + " to your shopping cart.");
		Assert.assertTrue(myWishListPage.isProductNotDisplayedInMyWishListPage(productName));
	}

	@Test(priority = 18)
	public void Add_All_Products_Without_Options_To_Shopping_Cart() {
		productListingPage = myWishListPage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear",
				"Watches");
		myWishListPage = productListingPage.clickWishListIconByProductName(driver, "Dash Digital Watch");
		myWishListPage.clickSuccessMessageHereLink();
		myWishListPage.clickAddAllToCartButton();

		Assert.assertEquals(myWishListPage.getAllProductsAddedToShoppingCartSuccessMessage(),
				"2 product(s) have been added to shopping cart: \"Dash Digital Watch\", \"Push It Messenger Bag\".");
	}

	@Test(priority = 19, groups = "addProductWithoutOptions")
	public void Remove_Product_From_Wish_List_Page() {
		myWishListPage.clickTrashcanIconByProductName(productName);

		Assert.assertEquals(myWishListPage.getProductRemovedFromWishListSuccessMessage(),
				productName + " has been removed from your Wish List.");
		Assert.assertTrue(myWishListPage.isProductNotDisplayedInMyWishListPage(productName));
		Assert.assertTrue(myWishListPage.isProductNotDisplayedInMyWishListSection(driver, productName));
	}

	@Test(priority = 20, groups = "clearWishList")
	public void Remove_Product_From_Wish_List_Section() {
		myWishListPage.clickCrossIconByProductName(driver, productName);

		Assert.assertEquals(myWishListPage.getProductRemovedFromWishListSuccessMessage(),
				productName + " has been removed from your Wish List.");
		Assert.assertTrue(myWishListPage.isProductNotDisplayedInMyWishListPage(productName));
		Assert.assertTrue(myWishListPage.isProductNotDisplayedInMyWishListSection(driver, productName));
	}

	@Test(priority = 21, groups = "clearWishList")
	public void Add_Products_From_Shopping_Cart_To_Wish_List() {
		shoppingCartPage.clickMoveToWishListLinkByProductName(productName);

		Assert.assertEquals(shoppingCartPage.getMovedToWishListSuccessMessage(),
				productName + " has been moved to your wish list.");

		shoppingCartPage.clickCustomerNameDropdown(driver);
		myWishListPage = shoppingCartPage.clickMyWishListDropdownLink(driver);

		Assert.assertFalse(myWishListPage.isProductNotDisplayedInMyWishListPage(productName));
	}

	@Test(priority = 22, groups = "clearWishList")
	public void Hover_Over_See_Details_Text() {
		shoppingCartPage.clickMoveToWishListLinkByProductName(productName);
		shoppingCartPage.clickCustomerNameDropdown(driver);
		myWishListPage = shoppingCartPage.clickMyWishListDropdownLink(driver);
		myWishListPage.hoverOverSeeDetailsTextByProductName(productName);

		Assert.assertEquals(myWishListPage.getOptionsDetails(productName), "");
	}

	@Test(priority = 23, groups = { "addProductWithOptions", "clearWishList" })
	public void Product_Quantity_Displayed_Correctly_When_Added_From_Shopping_Cart_To_Wish_List() {
		shoppingCartPage.sendKeysToQuantityTextboxByProductName("5", productName);
		shoppingCartPage.clickUpdateShoppingCartButton();
		shoppingCartPage.clickMoveToWishListLinkByProductName(productName);
		shoppingCartPage.clickCustomerNameDropdown(driver);
		myWishListPage = shoppingCartPage.clickMyWishListDropdownLink(driver);
		myWishListPage.hoverOverProductCardByName(productName);

		Assert.assertEquals(myWishListPage.getQuantityValueByProductName(productName), "5");
	}

	@Test(priority = 24, groups = { "logOut", "addProductWithoutOptions" })
	public void Error_Message_Logged_Out_User_Adding_Products_To_Wish_List() {
		Assert.assertEquals(customerLoginPage.getLoginErrorMessage(),
				"You must login or register to add items to your wishlist.");
	}

	@AfterMethod
	public void clearWishList(ITestResult result) {
		if (Arrays.asList(result.getMethod().getGroups()).contains("clearWishList")) {
			productActions.clearWishList();
			System.out.println("The @AfterMethod executed successfully: all products are removed from the wish list.");
		}
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeBrowserAndDriver();
	}

	private WebDriver driver;
	private String email, password;
	private DataHelper data;
	private HomepageObject homepage;
	private MyAccountPageObject myAccountPage;
	private CustomerLoginPageObject customerLoginPage;
	private CompareProductsPageObject compareProductsPage;
	private MyWishListPageObject myWishListPage;
	private ProductListingPageObject productListingPage;
	private ProductDetailsPageObject productDetailsPage;
	private ShoppingCartPageObject shoppingCartPage;
	private Products productActions;
	private String productName;
}
