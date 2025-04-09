package com.magento.user.orders;

import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;

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

		fileName = "login_data.json";
		email = JsonUtils.getJsonValue(fileName, "existing_user.email");
		password = JsonUtils.getJsonValue(fileName, "existing_user.password");
		customerLoginPage = homepage.clickSignInLink();
		customerLoginPage.logInAsRegisteredUser(email, password);
	}

	@BeforeMethod(alwaysRun = true, onlyForGroups = "addProductToCart")
	public void addProductToCart(Method method) {
		resultNode = JsonUtils.getRandomProductWithOptions("men_products.json");
		category = resultNode.get("category").asText();
		subcategory = resultNode.get("subcategory").asText();
		productName = resultNode.get("product").get("product_name").asText();
		productSize = resultNode.get("size").asText();
		productColor = resultNode.get("color").asText();

		productListingPage = homepage.clickNavigationBarDropdownMultiLevelItemLinkByLabels(driver, "Men", category,
				subcategory);
		productListingPage.addProductWithOptionsToCart(driver, productName, productSize, productColor);
		homepage = productListingPage.clickLumaLogo(driver);
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
		shoppingCartPage.sendKeysToEnterDiscountCodeTextbox("20poff");
		shoppingCartPage.clickApplyDiscountButton();
		shoppingCartPage.refreshCurrentPage(driver);
		Float subtotal = shoppingCartPage.getOrderSubtotal();
		Float total = shoppingCartPage.getOrderTotal();

		Assert.assertEquals(subtotal * 0.8, total, 0.01);
	}

	@Test(groups = "clearCart", description = "Verify that the 'H20' discount code applies a 70% discount to the 'Affirm Water Bottle' product")
	public void Promotion_03_Apply_Seventy_Percent_Discount_For_Affirm_Water_Bottle_With_Code_H20() {
		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear",
				"Fitness Equipment");
		productListingPage.clickAddToCartButtonByProductName(driver, "Affirm Water Bottle");
		productListingPage.clickShoppingCartIcon(driver);
		shoppingCartPage = productListingPage.clickViewAndEditCartLink(driver);
		shoppingCartPage.clickApplyDiscountCodeHeader();
		shoppingCartPage.sendKeysToEnterDiscountCodeTextbox("H20");
		shoppingCartPage.clickApplyDiscountButton();
		shoppingCartPage.refreshCurrentPage(driver);
		float subtotal = shoppingCartPage.getOrderSubtotal();
		float total = shoppingCartPage.getOrderTotal();

		Assert.assertEquals(subtotal * 0.3, total, 0.01);
	}

	@Test(groups = { "addProductToCart",
			"clearCart" }, description = "Verify the display of the success message when user applies a discount code")
	public void Promotion_04_Discount_Code_Applied_Success_Message() {
		homepage.clickShoppingCartIcon(driver);
		shoppingCartPage = homepage.clickViewAndEditCartLink(driver);
		shoppingCartPage.clickApplyDiscountCodeHeader();
		shoppingCartPage.sendKeysToEnterDiscountCodeTextbox("20poff");
		shoppingCartPage.clickApplyDiscountButton();

		Assert.assertEquals(shoppingCartPage.getDiscountCodeAppliedSuccessMessage(),
				"You used coupon code \"20poff\".");
	}

	@Test(groups = { "addProductToCart",
			"clearCart" }, description = "Verify the display of the success message when user removes a discount code")
	public void Promotion_05_Discount_Code_Removed_Success_Message() {
		homepage.clickShoppingCartIcon(driver);
		shoppingCartPage = homepage.clickViewAndEditCartLink(driver);
		shoppingCartPage.clickApplyDiscountCodeHeader();
		shoppingCartPage.sendKeysToEnterDiscountCodeTextbox("20poff");
		shoppingCartPage.clickApplyDiscountButton();
		shoppingCartPage.clickCancelCouponButton();

		Assert.assertEquals(shoppingCartPage.getDiscountCodeRemovedSuccessMessage(), "You canceled the coupon code.");
	}

	@Test(groups = { "addProductToCart",
			"clearCart" }, description = "Verify the display of the error message when an invalid discount code is used")
	public void Promotion_06_Invalid_Discount_Code_Error_Message() {
		homepage.clickShoppingCartIcon(driver);
		shoppingCartPage = homepage.clickViewAndEditCartLink(driver);
		shoppingCartPage.clickApplyDiscountCodeHeader();
		shoppingCartPage.sendKeysToEnterDiscountCodeTextbox("invalid");
		shoppingCartPage.clickApplyDiscountButton();

		Assert.assertEquals(shoppingCartPage.getInvalidDiscountCodeErrorMessage(),
				"The coupon code \"invalid\" is not valid.");
	}

	@Test(groups = { "addProductToCart",
			"clearCart" }, description = "Verify that shipping is free with table rate shipping when the order total is $50 or more")
	public void Promotion_07_Free_Shipping_For_Order_Equal_To_Or_Greater_Than_50() {
		homepage.clickShoppingCartIcon(driver);
		shoppingCartPage = homepage.clickViewAndEditCartLink(driver);
		shoppingCartPage.sendKeysToQuantityTextboxByProductName("3", productName);
		shoppingCartPage.clickUpdateShoppingCartButton();
		shoppingCartPage.clickEstimateShippingAndTaxHeader();
		shoppingCartPage.clickShippingMethodRadioButtonByLabel("Table Rate");

		Assert.assertEquals(shoppingCartPage.getOrderShipping(), 0.0);
	}

	@Test(groups = "clearCart", description = "Verify that user can purchase 4 tees of the same product for the price of 3")
	public void Promotion_08_Four_Tees_For_The_Price_Of_Three() {
		productListingPage = homepage.clickNavigationBarDropdownMultiLevelItemLinkByLabels(driver, "Women", "Tops",
				"Tees");
		productDetailsPage = productListingPage.clickProductLinkByProductName(driver, "Desiree Fitness Tee");
		productDetailsPage.clickSizeButtonByLabel("S");
		productDetailsPage.clickColorButtonByLabel("Black");
		productDetailsPage.sendKeysToQuantityTextbox("4");
		float price = productDetailsPage.getProductFinalPrice();
		productDetailsPage.clickAddToCartButton();
		productDetailsPage.clickShoppingCartIcon(driver);
		shoppingCartPage = productDetailsPage.clickViewAndEditCartLink(driver);

		Assert.assertEquals(shoppingCartPage.getOrderTotal(), price * 3);
	}

	@Test(groups = "clearCart", description = "Verify that the order total is displayed correctly when multiple promotions are applied to the order")
	public void Promotion_09_Multiple_Promotions_Applied_To_Order() {
		productListingPage = homepage.clickNavigationBarDropdownMultiLevelItemLinkByLabels(driver, "Women", "Tops",
				"Tees");
		productDetailsPage = productListingPage.clickProductLinkByProductName(driver, "Desiree Fitness Tee");
		productDetailsPage.clickSizeButtonByLabel("S");
		productDetailsPage.clickColorButtonByLabel("Black");
		productDetailsPage.sendKeysToQuantityTextbox("4");
		float womenTeePrice = productDetailsPage.getProductFinalPrice();
		productDetailsPage.clickAddToCartButton();
		productDetailsPage.clickShoppingCartIcon(driver);
		shoppingCartPage = productDetailsPage.clickViewAndEditCartLink(driver);
		shoppingCartPage.clickEstimateShippingAndTaxHeader();
		shoppingCartPage.clickShippingMethodRadioButtonByLabel("Table Rate");

		Assert.assertEquals(shoppingCartPage.getOrderSubtotal(), womenTeePrice * 4);
		Assert.assertEquals(shoppingCartPage.getOrderDiscount(), womenTeePrice);
		Assert.assertEquals(shoppingCartPage.getOrderTotal(), womenTeePrice * 3);
		Assert.assertEquals(shoppingCartPage.getOrderShipping(), 0.0);

		float orderTotal = shoppingCartPage.getOrderTotal();
		shoppingCartPage.clickApplyDiscountCodeHeader();
		shoppingCartPage.sendKeysToEnterDiscountCodeTextbox("20poff");
		shoppingCartPage.clickApplyDiscountButton();

		Assert.assertEquals(shoppingCartPage.getOrderTotal(), orderTotal * 0.8, 0.01);

		productListingPage = homepage.clickNavigationBarDropdownMultiLevelItemLinkByLabels(driver, "Women", "Tops",
				"Jackets");
		productDetailsPage = productListingPage.clickProductLinkByProductName(driver, "Olivia 1/4 Zip Light Jacket");
		productDetailsPage.clickSizeButtonByLabel("XS");
		productDetailsPage.clickColorButtonByLabel("Blue");
		productDetailsPage.sendKeysToQuantityTextbox("2");
		float womenJacketPrice = productDetailsPage.getProductFinalPrice();
		productDetailsPage.clickAddToCartButton();
		productDetailsPage.clickShoppingCartIcon(driver);
		shoppingCartPage = productDetailsPage.clickViewAndEditCartLink(driver);

		Assert.assertEquals(shoppingCartPage.getOrderTotal(), (orderTotal + womenJacketPrice * 2) * 0.8 * 0.8, 0.01);
	}

	@AfterMethod(alwaysRun = true)
	public void clearShoppingCart(Method method) {
		homepage.clearShoppingCart(driver);
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeBrowserAndDriver();
	}

	private WebDriver driver;
	private JsonNode resultNode;
	private String fileName, email, password, category, subcategory, productName, productSize, productColor;
	private HomepageObject homepage;
	private CustomerLoginPageObject customerLoginPage;
	private ProductListingPageObject productListingPage;
	private ProductDetailsPageObject productDetailsPage;
	private ShoppingCartPageObject shoppingCartPage;
}
