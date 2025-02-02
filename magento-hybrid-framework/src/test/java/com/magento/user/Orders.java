package com.magento.user;

import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.magento.commons.Products;

import commons.BaseTest;
import commons.PageGeneratorManager;
import pageObjects.CheckoutPageObject;
import pageObjects.HomepageObject;
import pageObjects.ProductDetailsPageObject;
import pageObjects.ProductListingPageObject;
import pageObjects.ShoppingCartPageObject;

public class Orders extends BaseTest {
	@Parameters("browser")
	@BeforeClass
	public void beforeClass(String browser) {
		driver = getBrowserDriver(browser);
		homepage = PageGeneratorManager.getHomepage(driver);
		productActions = new com.magento.commons.Products(driver);
	}

	@BeforeMethod
	public void addProductToCart(ITestResult result) {
		String[] groups = result.getMethod().getGroups();
		Random random = new Random();

		for (String group : groups) {
			switch (group) {
			case "addProductWithoutOptions":
				productActions.addRandomProductWithoutOptionsToCart();
				break;
			case "addProductWithOptions":
				String groupChoice = random.nextBoolean() ? "Men" : "Women";
				productActions.addRandomProductWithOptionsToCart(groupChoice);
				break;
			default:
				continue;
			}
			productName = Products.productName;
			System.out.println("The @BeforeMethod executed successfully: " + productName
					+ " is added to the shopping cart successfully.");
			break;
		}
		homepage.clickLumaLogo(driver);
	}

	@Test(priority = 1, groups = "miniCart", description = "Verify the information message displayed when the shopping cart is empty")
	public void Info_Message_Empty_Shopping_Cart() {
		homepage.clickShoppingCartIcon(driver);

		Assert.assertEquals(homepage.getEmptyShoppingCartInfoMessage(driver),
				"You have no items in your shopping cart.");
	}

	@Test(priority = 2, groups = "miniCart", description = "Verify that the mini cart is not displayed when user clicks the cross icon")
	public void Cross_Icon() {
		homepage.clickShoppingCartIcon(driver);
		homepage.clickMiniCartCrossIcon(driver);

		Assert.assertTrue(homepage.isMiniCartNotDisplayed(driver));
	}

	@Test(priority = 3, groups = { "miniCart",
			"addProductWithOptions" }, description = "Verify that the corresponding product and its options are displayed correctly in the mini cart when user adds it to the shopping cart")
	public void Product_Options_Displayed_Correctly() {
		Assert.assertEquals(productListingPage.getAddedToShoppingCartSuccessMessage(),
				"You added " + productName + " to your shopping cart.");

		productListingPage.clickShoppingCartIcon(driver);
		productListingPage.clickSeeDetailsCollapsibleHeaderByProductName(driver, productName);

		Assert.assertEquals(productListingPage.getSizeValueByProductName(driver, productName), productSize);
		Assert.assertEquals(productListingPage.getColorValueByProductName(driver, productName), productColor);
	}

	@Test(priority = 4, groups = "miniCart", description = "Verify that user is directed to the 'Shopping Cart' page when clicking the 'View and Edit Cart' hyperlink")
	public void Click_View_And_Edit_Card_Link() {
		productListingPage.refreshCurrentPage(driver);
		productListingPage.clickShoppingCartIcon(driver);
		shoppingCartPage = productListingPage.clickViewAndEditCartLink(driver);

		Assert.assertEquals(shoppingCartPage.getPageHeader(driver), "Shopping Cart");
	}

	@Test(priority = 5)
	public void Click_Shopping_Cart_Link() {
		homepage = shoppingCartPage.clickLumaLogo(driver);
		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear", "Bags");
		productListingPage.addProductWithNoOptionsToCart(driver, "Driven Backpack");

		Assert.assertEquals(productListingPage.getAddedToShoppingCartSuccessMessage(),
				"You added Driven Backpack to your shopping cart.");

		shoppingCartPage = productListingPage.clickShoppingCartLinkSuccessMessage();

		Assert.assertEquals(shoppingCartPage.getPageHeader(driver), "Shopping Cart");
	}

	@Test(priority = 6)
	public void Orders_Subtotal_And_Total_Quantity() {
		productActions.clearShoppingCart();

		productListingPage = homepage.clickNavigationBarDropdownMultiLevelItemLinkByLabels(driver, "Men", "Tops",
				"Jackets");
		productDetailsPage = productListingPage.clickProductLinkByProductName(driver, "Proteus Fitness Jackshirt");
		productDetailsPage.clickSizeButtonByLabel("S");
		productDetailsPage.clickColorButtonByLabel("Orange");
		Float firstProductPrice = productDetailsPage.getProductFinalPrice();
		productDetailsPage.sendKeysToQuantityTextbox("2");
		productDetailsPage.clickAddToCartButton();
		productListingPage = homepage.clickNavigationBarDropdownMultiLevelItemLinkByLabels(driver, "Women", "Tops",
				"Tees");
		productDetailsPage = productListingPage.clickProductLinkByProductName(driver, "Desiree Fitness Tee");
		productDetailsPage.clickSizeButtonByLabel("XL");
		productDetailsPage.clickColorButtonByLabel("Orange");
		Float secondProductPrice = productDetailsPage.getProductFinalPrice();
		productDetailsPage.sendKeysToQuantityTextbox("3");
		productDetailsPage.clickAddToCartButton();
		productDetailsPage.clickShoppingCartIcon(driver);
		Float subtotal = productDetailsPage.getMiniCartSubtotal(driver);

		Assert.assertTrue(productDetailsPage.getMiniCartQuantity(driver).contains("5"));
		Assert.assertEquals(subtotal, firstProductPrice * 2 + secondProductPrice * 3);
	}

	@Test(priority = 7)
	public void Change_Quantity() {
		productListingPage = productDetailsPage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear",
				"Bags");
		productListingPage.addProductWithNoOptionsToCart(driver, "Driven Backpack");
		productListingPage.clickShoppingCartIcon(driver);
		productListingPage.sendKeysToQuantityTextboxByProductName(driver, "5", "Driven Backpack");
		productListingPage.clickUpdateButton(driver);

		Assert.assertEquals(productListingPage.getQuantityValueByProductName(driver, "Driven Backpack"), "5");
	}

	@Test(priority = 8)
	public void Click_Pen_Icon() {
		productDetailsPage = productListingPage.clickPenIconByProductName(driver, "Driven Backpack");

		Assert.assertEquals(productDetailsPage.getPageHeader(driver), "Driven Backpack");
	}

	@Test(priority = 9)
	public void Click_Trashcan_Icon() {
		productDetailsPage.clickShoppingCartIcon(driver);
		productDetailsPage.clickTrashcanIconByProductName(driver, "Driven Backpack");
		productDetailsPage.clickConfirmationPopupOKButton(driver);
		productDetailsPage.clickShoppingCartIcon(driver);

		Assert.assertTrue(productDetailsPage.isProductNotDisplayedInMiniCart(driver, "Driven Backpack"));
	}

	@Test(priority = 10)
	public void Click_Proceed_To_Checkout_Button() {
		checkoutPage = productDetailsPage.clickProceedToCheckoutButton(driver);

		Assert.assertEquals(productDetailsPage.getPageHeader(driver), "Shipping Address");
	}

	@Test(groups = "productListing")
	public void Warning_Message_Adding_Product_With_Options_To_Shopping_Cart() {

	}

	@Test(groups = "promotion")
	public void Apply_Twenty_Percent_Discount_For_Order_Equal_To_Or_Greater_Than_200() {
		productDetailsPage = productListingPage.clickProductLinkByProductName(driver, "Driven Backpack");
		productDetailsPage.sendKeysToQuantityTextbox("10");
		productDetailsPage.clickAddToCartButton();
		productDetailsPage.clickShoppingCartIcon(driver);
		shoppingCartPage = productDetailsPage.clickViewAndEditCartLink(driver);
		Float subtotal = shoppingCartPage.getOrderSubtotal();
		Float total = shoppingCartPage.getOrderTotal();

		Assert.assertEquals(subtotal * 0.8, total, 0.01);
	}

	@Test(groups = "promotion")
	public void Apply_Twenty_Percent_Discount_With_Code_20poff() {
		productDetailsPage = productListingPage.clickProductLinkByProductName(driver, "Driven Backpack");
		productDetailsPage.clickAddToCartButton();
		productDetailsPage.clickShoppingCartIcon(driver);
		shoppingCartPage = productDetailsPage.clickViewAndEditCartLink(driver);
		shoppingCartPage.clickApplyDiscountCodeHeader();
		shoppingCartPage.sendKeysToEnterDiscountCodeTextbox("20poff");
		shoppingCartPage.clickApplyDiscountButton();
		Float subtotal = shoppingCartPage.getOrderSubtotal();
		Float total = shoppingCartPage.getOrderTotal();

		Assert.assertEquals(subtotal * 0.8, total, 0.01);
	}

	@Test(groups = "promotion")
	public void Discount_Code_Applied_Success_Message() {
		homepage.clickShoppingCartIcon(driver);
		shoppingCartPage = homepage.clickViewAndEditCartLink(driver);
		shoppingCartPage.clickApplyDiscountCodeHeader();
		shoppingCartPage.sendKeysToEnterDiscountCodeTextbox("20poff");
		shoppingCartPage.clickApplyDiscountButton();

		Assert.assertEquals(shoppingCartPage.getDiscountCodeAppliedSuccessMessage(),
				"You used coupon code \"20poff\".");
	}

	@Test(groups = "promotion", dependsOnMethods = "Discount_Code_Applied_Success_Message", description = "Verify the display of the success message when user removes a discount code")
	public void Discount_Code_Removed_Success_Message() {
		shoppingCartPage.clickCancelCouponButton();

		Assert.assertEquals(shoppingCartPage.getDiscountCodeRemovedSuccessMessage(), "You canceled the coupon code.");
	}

	@Test(groups = "promotion", description = "Verify the display of the error message when an invalid discount code is used")
	public void Invalid_Discount_Code_Error_Message() {
		homepage.clickShoppingCartIcon(driver);
		shoppingCartPage = homepage.clickViewAndEditCartLink(driver);
		shoppingCartPage.clickApplyDiscountCodeHeader();
		shoppingCartPage.sendKeysToEnterDiscountCodeTextbox("invalid");
		shoppingCartPage.clickApplyDiscountButton();

		Assert.assertEquals(shoppingCartPage.getInvalidDiscountCodeErrorMessage(),
				"The coupon code \"invalid\" is not valid.");
	}

	@Test(groups = "promotion", description = "Verify that shipping is free with table rate shipping when the order total is $50 or more")
	public void Free_Shipping_For_Order_Equal_To_Or_Greater_Than_50() {

	}

	@Test(groups = "promotion", description = "")
	public void Four_Tees_For_The_Price_Of_Three() {

	}

	@Test(groups = "promotion", description = "")
	public void Multiple_Promotions_Applied_To_Order() {

	}

	@Test(groups = "checkout", description = "Verify the elements displayed when user is not logged in and fills in the 'Email Address' field with the email linked to their account")
	public void Elements_Displayed_When_User_Fills_In_Email_Linked_To_Account() {

	}

	@Test
	public void Display_Of_Shipping_Address_Card_When_Selected() {

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeBrowserAndDriver();
	}

	private WebDriver driver;
	private String productName, productSize, productColor;
	private HomepageObject homepage;
	private ProductListingPageObject productListingPage;
	private ProductDetailsPageObject productDetailsPage;
	private ShoppingCartPageObject shoppingCartPage;
	private CheckoutPageObject checkoutPage;
	private com.magento.commons.Products productActions;
}
