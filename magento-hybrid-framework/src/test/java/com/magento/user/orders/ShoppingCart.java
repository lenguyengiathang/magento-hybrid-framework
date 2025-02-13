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

public class ShoppingCart extends BaseTest {
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

	@Test(description = "Verify that user is directed to the 'Shopping Cart' page when clicking the 'View and Edit Cart' hyperlink")
	public void Checkout_04_Click_View_And_Edit_Cart_Link() {
		homepage.clickShoppingCartIcon(driver);
		shoppingCartPage = homepage.clickViewAndEditCartLink(driver);

		Assert.assertEquals(shoppingCartPage.getPageHeader(driver), "Shopping Cart");
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
