package com.magento.user.orders;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
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

public class MiniCart extends BaseTest {
	@Parameters("browser")
	@BeforeClass
	public void beforeClass(String browser) {
		driver = getBrowserDriver(browser);
		homepage = PageGeneratorManager.getHomepage(driver);

		productActions = new com.magento.commons.Products(driver);
	}

	@BeforeMethod(alwaysRun = true, onlyForGroups = "addProductToCart")
	public void addProductToCart(Method method) {
		if (Arrays.asList(method.getAnnotation(Test.class).groups()).contains("addProductToCart")) {
			try {
				productActions.addRandomProductWithOptionsToCart("men_products.json");
				productName = Products.productName;
				productSize = Products.productSize;
				productColor = Products.productColor;
			} catch (Exception e) {
				System.err.println("Error adding product to cart: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	@Test(description = "Verify the information message displayed when the shopping cart is empty")
	public void Mini_Cart_01_Info_Message_Empty_Shopping_Cart() {
		homepage.clickShoppingCartIcon(driver);

		Assert.assertEquals(homepage.getEmptyShoppingCartInfoMessage(driver),
				"You have no items in your shopping cart.");
	}

	@Test(description = "Verify that the mini cart is not displayed when user clicks the cross icon")
	public void Mini_Cart_02_Click_Cross_Icon() {
		homepage.clickShoppingCartIcon(driver);
		homepage.clickMiniCartCrossIcon(driver);

		Assert.assertTrue(homepage.isMiniCartNotDisplayed(driver));
	}

	@Test(groups = { "addProductToCart",
			"clearCart" }, description = "Verify that the corresponding product and its options are displayed correctly in the mini cart when user adds it to the shopping cart")
	public void Mini_Cart_03_Product_Options_Displayed_Correctly() {
		homepage.clickShoppingCartIcon(driver);
		homepage.clickSeeDetailsCollapsibleHeaderByProductName(driver, productName);

		Assert.assertEquals(homepage.getSizeValueByProductName(driver, productName), productSize);
		Assert.assertEquals(homepage.getColorValueByProductName(driver, productName), productColor);
	}

	@Test(groups = { "addProductToCart",
			"clearCart" }, description = "Verify that user is directed to the 'Shopping Cart' page when clicking the 'View and Edit Cart' hyperlink")
	public void Mini_Cart_04_Click_View_And_Edit_Card_Link() {
		homepage.clickShoppingCartIcon(driver);
		shoppingCartPage = homepage.clickViewAndEditCartLink(driver);

		Assert.assertEquals(shoppingCartPage.getPageHeader(driver), "Shopping Cart");
	}

	@Test(groups = "clearCart", description = "Verify that the subtotal equals the sum of the prices of the added products")
	public void Mini_Cart_05_Orders_Subtotal_Equals_Sum_Of_Product_Prices() {
		productListingPage = homepage.clickNavigationBarDropdownMultiLevelItemLinkByLabels(driver, "Men", "Tops",
				"Tees");
		productDetailsPage = productListingPage.clickProductLinkByProductName(driver, "Aero Daily Fitness Tee");
		productDetailsPage.clickSizeButtonByLabel("S");
		productDetailsPage.clickColorButtonByLabel("Brown");
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

		Assert.assertEquals(subtotal, firstProductPrice * 2 + secondProductPrice * 3);
	}

	@Test(groups = "clearCart", description = "Verify that the total quantity equals the sum of the quantities of the added products")
	public void Mini_Cart_06_Total_Quantity_Equals_Sum_Of_Product_Quantity() {
		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear",
				"Fitness Equipment");
		productDetailsPage = productListingPage.clickProductLinkByProductName(driver, "Sprite Foam Roller");
		productDetailsPage.sendKeysToQuantityTextbox("2");
		productDetailsPage.clickAddToCartButton();
		productListingPage = productDetailsPage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear",
				"Fitness Equipment");
		productDetailsPage = productListingPage.clickProductLinkByProductName(driver, "Affirm Water Bottle");
		productDetailsPage.sendKeysToQuantityTextbox("3");
		productDetailsPage.clickAddToCartButton();
		productDetailsPage.clickShoppingCartIcon(driver);

		Assert.assertTrue(productDetailsPage.getMiniCartQuantity(driver).contains("5"));
	}

	@Test(groups = { "addProductToCart",
			"clearCart" }, description = "Verify that user can change the quantity of added products")
	public void Mini_Cart_07_Change_Product_Quantity() {
		homepage.clickShoppingCartIcon(driver);
		homepage.sendKeysToQuantityTextboxByProductName(driver, "3", productName);
		homepage.clickUpdateButton(driver);

		Assert.assertEquals(homepage.getQuantityValueByProductName(driver, productName), "3");
	}

	@Test(groups = { "addProductToCart",
			"clearCart" }, description = "Verify that user is directed to \"Product Details\" page when clicking the pen icon")
	public void Mini_Cart_08_Click_Pen_Icon() {
		homepage.clickShoppingCartIcon(driver);
		productDetailsPage = homepage.clickPenIconByProductName(driver, productName);

		Assert.assertEquals(productDetailsPage.getPageHeader(driver), productName);
	}

	@Test(groups = "addProductToCart", description = "Verify that the product is not displayed in the mini cart when clicking the trashcan icon")
	public void Mini_Cart_09_Click_Trashcan_Icon() {
		homepage.clickShoppingCartIcon(driver);
		homepage.clickTrashcanIconByProductName(driver, productName);
		homepage.clickConfirmationPopupOKButton(driver);

		Assert.assertTrue(homepage.isProductNotDisplayedInMiniCart(driver, productName));
	}

	@Test(groups = "addProductToCart", description = "Verify that user is directed to the 'Shipping' step of the 'Checkout' page when clicking the 'Proceed to Checkout' button")
	public void Mini_Cart_10_Click_Proceed_To_Checkout_Button() {
		homepage.clickShoppingCartIcon(driver);
		checkoutPage = homepage.clickProceedToCheckoutButton(driver);

		Assert.assertTrue(checkoutPage.isUserOnShippingStep());
	}

	@AfterMethod(alwaysRun = true, onlyForGroups = "clearCart")
	public void clearCart(Method method) {
		if (Arrays.asList(method.getAnnotation(Test.class).groups()).contains("clearCart")) {
			try {
				productActions.clearShoppingCart();
			} catch (Exception e) {
				System.err.println("Error clearing the shopping cart: " + e.getMessage());
				e.printStackTrace();
			}
		}
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
