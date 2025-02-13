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
import pageObjects.CustomerLoginPageObject;
import pageObjects.HomepageObject;
import pageObjects.ProductDetailsPageObject;
import pageObjects.ProductListingPageObject;
import pageObjects.ShoppingCartPageObject;
import utilities.JsonUtils;

public class Promotion extends BaseTest {
	@Parameters("browser")
	@BeforeClass
	public void beforeClass(String browser) {
		driver = getBrowserDriver(browser);
		homepage = PageGeneratorManager.getHomepage(driver);

		productActions = new com.magento.commons.Products(driver);
		couponCode = "20poff";
		fileName = "login_data.json";
		email = JsonUtils.getJsonValue(fileName, "email");
		password = JsonUtils.getJsonValue(fileName, "password");
		customerLoginPage = homepage.clickSignInLink();
		customerLoginPage.logInAsRegisteredUser(email, password);
	}

	@BeforeMethod(alwaysRun = true, onlyForGroups = "addProductToCart")
	public void addProductToCart(Method method) {
		if (Arrays.asList(method.getAnnotation(Test.class).groups()).contains("addProductToCart")) {
			try {
				productActions.addRandomProductWithoutOptionsToCart();
				productName = Products.productName;
			} catch (Exception e) {
				System.err.println("Error adding product to cart: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	@Test(groups = { "addProductToCart",
			"clearCart" }, description = "Verify that a 20% discount is applied to the order when the subtotal is $200 or more")
	public void Promotion_01_Apply_Twenty_Percent_Discount_For_Order_Equal_To_Or_Greater_Than_200() {
		homepage.clickShoppingCartIcon(driver);
		homepage.sendKeysToQuantityTextboxByProductName(driver, "30", productName);
		homepage.clickUpdateButton(driver);
		shoppingCartPage = homepage.clickViewAndEditCartLink(driver);
		Float subtotal = shoppingCartPage.getOrderSubtotal();
		Float total = shoppingCartPage.getOrderTotal();

		Assert.assertEquals(subtotal * 0.8, total, 0.01);
	}

	@Test(groups = { "addProductToCart",
			"clearCart" }, description = "Verify that the '20poff' discount code applies a 20% discount to the order regardless of the minimum order value")
	public void Promotion_02_Apply_Twenty_Percent_Discount_With_Code_20poff() {
		homepage.clickShoppingCartIcon(driver);
		shoppingCartPage = homepage.clickViewAndEditCartLink(driver);
		shoppingCartPage.clickApplyDiscountCodeHeader();
		shoppingCartPage.sendKeysToEnterDiscountCodeTextbox(couponCode);
		shoppingCartPage.clickApplyDiscountButton();
		shoppingCartPage.refreshCurrentPage(driver);
		Float subtotal = shoppingCartPage.getOrderSubtotal();
		Float total = shoppingCartPage.getOrderTotal();

		Assert.assertEquals(subtotal * 0.8, total, 0.01);
	}

	@Test(groups = { "addProductToCart",
			"clearCart" }, description = "Verify the display of the success message when user applies a discount code")
	public void Promotion_03_Discount_Code_Applied_Success_Message() {
		homepage.clickShoppingCartIcon(driver);
		shoppingCartPage = homepage.clickViewAndEditCartLink(driver);
		shoppingCartPage.clickApplyDiscountCodeHeader();
		shoppingCartPage.sendKeysToEnterDiscountCodeTextbox(couponCode);
		shoppingCartPage.clickApplyDiscountButton();

		Assert.assertEquals(shoppingCartPage.getDiscountCodeAppliedSuccessMessage(),
				"You used coupon code \"" + couponCode + "\".");
	}

	@Test(groups = { "addProductToCart",
			"clearCart" }, description = "Verify the display of the success message when user removes a discount code")
	public void Promotion_04_Discount_Code_Removed_Success_Message() {
		homepage.clickShoppingCartIcon(driver);
		shoppingCartPage = homepage.clickViewAndEditCartLink(driver);
		shoppingCartPage.clickApplyDiscountCodeHeader();
		shoppingCartPage.sendKeysToEnterDiscountCodeTextbox(couponCode);
		shoppingCartPage.clickApplyDiscountButton();
		shoppingCartPage.clickCancelCouponButton();

		Assert.assertEquals(shoppingCartPage.getDiscountCodeRemovedSuccessMessage(), "You canceled the coupon code.");
	}

	@Test(groups = { "addProductToCart",
			"clearCart" }, description = "Verify the display of the error message when an invalid discount code is used")
	public void Promotion_05_Invalid_Discount_Code_Error_Message() {
		homepage.clickShoppingCartIcon(driver);
		shoppingCartPage = homepage.clickViewAndEditCartLink(driver);
		shoppingCartPage.clickApplyDiscountCodeHeader();
		shoppingCartPage.sendKeysToEnterDiscountCodeTextbox("invalid");
		shoppingCartPage.clickApplyDiscountButton();

		Assert.assertEquals(shoppingCartPage.getInvalidDiscountCodeErrorMessage(),
				"The coupon code \"invalid\" is not valid.");
	}

	@Test(groups = "clearCart", description = "Verify that shipping is free with table rate shipping when the order total is $50 or more")
	public void Promotion_06_Free_Shipping_For_Order_Equal_To_Or_Greater_Than_50() {
		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear", "Bags");
		productListingPage.clickAddToCartButtonByProductName(driver, "Compete Track Tote");
		shoppingCartPage = productListingPage.clickShoppingCartLinkSuccessMessage();
		shoppingCartPage.sendKeysToQuantityTextboxByProductName("2", "Compete Track Tote");
		shoppingCartPage.clickUpdateShoppingCartButton();

		Assert.assertEquals(shoppingCartPage.getOrderShipping(), "0.00");
	}

	@Test(groups = "clearCart", description = "Verify that user can purchase 4 tees of the same product for the price of 3")
	public void Promotion_07_Four_Tees_For_The_Price_Of_Three() {
		productListingPage = homepage.clickNavigationBarDropdownMultiLevelItemLinkByLabels(driver, "Women", "Tops",
				"Tees");
		productDetailsPage = productListingPage.clickProductLinkByProductName(driver, "Desiree Fitness Tee");
		productDetailsPage.clickSizeButtonByLabel("S");
		productDetailsPage.clickColorButtonByLabel("Black");
		productDetailsPage.sendKeysToQuantityTextbox("4");
		Float price = productDetailsPage.getProductFinalPrice();
		productDetailsPage.clickAddToCartButton();
		productDetailsPage.clickShoppingCartIcon(driver);
		shoppingCartPage = productDetailsPage.clickViewAndEditCartLink(driver);

		Assert.assertEquals(shoppingCartPage.getOrderSubtotal(), price * 4);
		Assert.assertEquals(shoppingCartPage.getOrderDiscount(), price);
		Assert.assertEquals(shoppingCartPage.getOrderTotal(), price * 3);
	}

	@Test(groups = "clearCart", description = "Verify that the order total is displayed correctly when multiple promotions are applied to the order")
	public void Promotion_08_Multiple_Promotions_Applied_To_Order() {
		productListingPage = homepage.clickNavigationBarDropdownMultiLevelItemLinkByLabels(driver, "Women", "Tops",
				"Tees");
		productDetailsPage = productListingPage.clickProductLinkByProductName(driver, "Desiree Fitness Tee");
		productDetailsPage.clickSizeButtonByLabel("S");
		productDetailsPage.clickColorButtonByLabel("Black");
		productDetailsPage.sendKeysToQuantityTextbox("4");
		Float price = productDetailsPage.getProductFinalPrice();
		productDetailsPage.clickAddToCartButton();
		productDetailsPage.clickShoppingCartIcon(driver);
		shoppingCartPage = productDetailsPage.clickViewAndEditCartLink(driver);

		Assert.assertEquals(shoppingCartPage.getOrderSubtotal(), price * 4);
		Assert.assertEquals(shoppingCartPage.getOrderDiscount(), price);
		Assert.assertEquals(shoppingCartPage.getOrderTotal(), price * 3);
		Assert.assertEquals(shoppingCartPage.getOrderShipping(), "0.00");

		Float orderTotal = shoppingCartPage.getOrderTotal();
		shoppingCartPage.clickApplyDiscountCodeHeader();
		shoppingCartPage.sendKeysToEnterDiscountCodeTextbox("20poff");
		shoppingCartPage.clickApplyDiscountButton();

		Assert.assertEquals(shoppingCartPage.getOrderTotal(), orderTotal * 0.8);

		orderTotal = shoppingCartPage.getOrderTotal();
		shoppingCartPage.sendKeysToQuantityTextboxByProductName("10", "Desiree Fitness Tee");
		shoppingCartPage.clickUpdateShoppingCartButton();

		Assert.assertEquals(shoppingCartPage.getOrderTotal(), orderTotal * 0.8);
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
	private String fileName, email, password, productName, couponCode;
	private HomepageObject homepage;
	private CustomerLoginPageObject customerLoginPage;
	private ProductListingPageObject productListingPage;
	private ProductDetailsPageObject productDetailsPage;
	private ShoppingCartPageObject shoppingCartPage;
	private com.magento.commons.Products productActions;
}
