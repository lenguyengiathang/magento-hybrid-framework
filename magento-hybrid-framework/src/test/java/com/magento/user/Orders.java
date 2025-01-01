package com.magento.user;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.BaseTest;
import commons.PageGeneratorManager;
import pageObjects.HomepageObject;
import pageObjects.ProductDetailsPageObject;
import pageObjects.ProductListingPageObject;
import pageObjects.ShoppingCartPageObject;
import utilities.ProductDataMapperBasic;

public class Orders extends BaseTest {
	@Parameters("browser")
	@BeforeClass
	public void beforeClass(String browser) {
		driver = getBrowserDriver(browser);
		homepage = PageGeneratorManager.getHomepage(driver);
		orderActions = new com.magento.commons.Orders(driver);

		productData = ProductDataMapperBasic.loadProductData("MiscProducts.json");
		productName = productData.getProducts().get(0).getProductName();
		productSize = productData.getProducts().get(0).getSize();
		productColor = productData.getProducts().get(0).getColor();
	}

	@Test(priority = 1)
	public void Orders_01_Info_Message_Empty_Shopping_Cart() {
		homepage.clickShoppingCartIcon(driver);

		Assert.assertEquals(homepage.getEmptyShoppingCartInfoMessage(driver),
				"You have no items in your shopping cart.");
	}

	@Test(priority = 2)
	public void Orders_02_Mini_Cart_Cross_Icon() {
		homepage.clickMiniCartCrossIcon(driver);

		Assert.assertTrue(homepage.isMiniCartNotDisplayed(driver));
	}

	@Test(priority = 3)
	public void Orders_03_Product_And_Options_Displayed_Correctly_In_Mini_Cart() {
		productListingPage = homepage.clickNavigationBarDropdownMultiLevelItemLinkByLabels(driver, "Men", "Tops",
				"Jackets");
		productListingPage.addProductWithOptionsToCart(productName, productSize, productColor);

		Assert.assertEquals(productListingPage.getAddedToCartSuccessMessage(),
				"You added " + productName + " to your shopping cart.");

		productListingPage.clickShoppingCartIcon(driver);
		productListingPage.clickSeeDetailsCollapsibleHeaderByProductName(driver, productName);

		Assert.assertEquals(productListingPage.getSizeValueByProductName(driver, productName), productSize);
		Assert.assertEquals(productListingPage.getColorValueByProductName(driver, productName), productColor);
	}

	@Test(priority = 4)
	public void Orders_Click_View_And_Edit_Card_Link() {
		shoppingCartPage = productListingPage.clickViewAndEditCartLink();

		Assert.assertEquals(shoppingCartPage.getPageHeader(driver), "Shopping Cart");
	}

	@Test(priority = 5)
	public void Orders_Click_Shopping_Cart_Link() {
		productListingPage = shoppingCartPage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear",
				"Bags");
		productListingPage.addProductWithNoOptionsToCart("Driven Backpack");

		Assert.assertEquals(productListingPage.getAddedToCartSuccessMessage(),
				"You added Driven Backpack to your shopping cart.");

		shoppingCartPage = productListingPage.clickShoppingCartLink();

		Assert.assertEquals(shoppingCartPage.getPageHeader(driver), "Shopping Cart");
	}

	@Test(priority = 6)
	public void Orders_Subtotal_And_Total_Quantity_In_Mini_Cart() {
		orderActions.clearShoppingCart();

		productListingPage = homepage.clickNavigationBarDropdownMultiLevelItemLinkByLabels(driver, "Men", "Tops",
				"Jackets");
		productDetailsPage = productListingPage.clickProductLinkByName("Proteus Fitness Jackshirt");
		productDetailsPage.clickSizeButtonByLabel("S");
		productDetailsPage.clickColorButtonByLabel("Orange");
		Float firstProductPrice = productDetailsPage.getProductFinalPrice();
		productDetailsPage.sendKeysToQuantityTextbox("2");
		productDetailsPage.clickAddToCartButton();
		productListingPage = homepage.clickNavigationBarDropdownMultiLevelItemLinkByLabels(driver, "Women", "Tops",
				"Tees");
		productDetailsPage = productListingPage.clickProductLinkByName("Desiree Fitness Tee");
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
	public void Orders_Change_Quantity_In_Mini_Cart() {
		productListingPage = productDetailsPage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear",
				"Bags");
		productListingPage.addProductWithNoOptionsToCart("Driven Backpack");
		productListingPage.clickShoppingCartIcon(driver);
	}

	@Test
	public void Orders_Click_Pen_Icon() {

	}

	@Test
	public void Orders_Click_Trashcan_Icon() {

	}

	@Test
	public void Orders_Click_Proceed_To_Checkout_Button() {

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeBrowserAndDriver();
	}

	private WebDriver driver;
	private ProductDataMapperBasic productData;
	private String productName, productSize, productColor;
	private HomepageObject homepage;
	private ProductListingPageObject productListingPage;
	private ProductDetailsPageObject productDetailsPage;
	private ShoppingCartPageObject shoppingCartPage;
	private com.magento.commons.Orders orderActions;
}
