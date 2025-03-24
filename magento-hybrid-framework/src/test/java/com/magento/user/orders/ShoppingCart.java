package com.magento.user.orders;

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
import pageObjects.HomepageObject;
import pageObjects.ProductDetailsPageObject;
import pageObjects.ProductListingPageObject;
import pageObjects.ShoppingCartPageObject;
import utilities.JsonUtils;

public class ShoppingCart extends BaseTest {
	@Parameters("browser")
	@BeforeClass
	public void beforeClass(String browser) {
		driver = getBrowserDriver(browser);
		homepage = PageGeneratorManager.getHomepage(driver);
	}

	@BeforeMethod(onlyForGroups = "getProductNoOptions")
	public void getProductNoOptions() {
		resultNode = JsonUtils.getRandomProductNoOptions("gear_products.json");
		category = resultNode.get("category").asText();
		productName = resultNode.get("product").get("product_name").asText();
	}

	@BeforeMethod(onlyForGroups = "getProductWithOptions")
	public void getProductWithOptions() {
		resultNode = JsonUtils.getRandomProductWithOptions("women_products.json");
		category = resultNode.get("category").asText();
		subcategory = resultNode.get("subcategory").asText();
		productName = resultNode.get("product").get("product_name").asText();
		productSize = resultNode.get("size").asText();
		productColor = resultNode.get("color").asText();
	}

	@Test(groups = "getProductWithOptions", description = "Verify the warning message displayed when user clicks the 'Add to Cart' button for any product with options from the product listing page")
	public void Shopping_Cart_01_Warning_Message_Choose_Options_For_Product_With_Options() {
		productListingPage = homepage.clickNavigationBarDropdownMultiLevelItemLinkByLabels(driver, "Women", category,
				subcategory);
		productDetailsPage = (ProductDetailsPageObject) productListingPage.clickAddToCartButtonByProductName(driver,
				productName);

		Assert.assertEquals(productDetailsPage.getChooseOptionsForItemWarningMessage(),
				"You need to choose options for your item.");
	}

	@Test(groups = "getProductNoOptions", description = "Verify the success message displayed when user clicks the 'Add to Cart' button for any product without options from the product listing page")
	public void Shopping_Cart_02_Success_Message_Product_Without_Options_Added_To_Cart() {
		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear", category);
		productListingPage.clickAddToCartButtonByProductName(driver, productName);

		Assert.assertEquals(productListingPage.getAddedToShoppingCartSuccessMessage(),
				"You added " + productName + " to your shopping cart.");
	}

	@Test(groups = { "getProductNoOptions",
			"clearCart" }, description = "Verify that user is directed to the 'Shopping Cart' page when clicking the 'shopping cart' hyperlink in the 'You added [product name] to your shopping cart.' success message")
	public void Shopping_Cart_03_Click_Shopping_Cart_Hyperlink_In_The_Product_Added_To_Cart_Success_Message() {
		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear", category);
		productListingPage.clickAddToCartButtonByProductName(driver, productName);
		productListingPage.clickShoppingCartIcon(driver);
		shoppingCartPage = homepage.clickViewAndEditCartLink(driver);

		Assert.assertEquals(shoppingCartPage.getPageHeader(driver), "Shopping Cart");
	}

	@Test(groups = { "getProductNoOptions",
			"clearCart" }, description = "Verify that user can add multiple products to the shopping cart at once using the 'Qty' field")
	public void Shopping_Cart_04_Add_Multiple_Products_Using_Qty_Field() {
		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear", category);
		productDetailsPage = productListingPage.clickProductLinkByProductName(driver, productName);
		productDetailsPage.sendKeysToQuantityTextbox("5");
		productDetailsPage.clickAddToCartButton();
		productDetailsPage.clickShoppingCartIcon(driver);

		Assert.assertEquals(productDetailsPage.getQuantityValueByProductName(driver, productName), "5");
	}

	@Test(description = "Verify that the number of products next to the shopping cart icon is displayed correctly")
	public void Shopping_Cart_05_Display_Of_Number_Of_Products_Next_To_Shopping_Cart_Icon() {
		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear", "Bags");
		productDetailsPage = productListingPage.clickProductLinkByProductName(driver, "Overnight Duffle");
		productDetailsPage.sendKeysToQuantityTextbox("2");
		productDetailsPage.clickAddToCartButton();
		productListingPage = homepage.clickNavigationBarDropdownMultiLevelItemLinkByLabels(driver, "Women", "Tops",
				"Jackets");
		productDetailsPage = productListingPage.clickProductLinkByProductName(driver, "Juno Jacket");
		productDetailsPage.clickSizeButtonByLabel("S");
		productDetailsPage.clickColorButtonByLabel("Blue");
		productDetailsPage.sendKeysToQuantityTextbox("4");
		productDetailsPage.clickAddToCartButton();
		productListingPage = homepage.clickNavigationBarDropdownMultiLevelItemLinkByLabels(driver, "Men", "Bottoms",
				"Shorts");
		productDetailsPage = productListingPage.clickProductLinkByProductName(driver, "Pierce Gym Short");
		productDetailsPage.clickSizeButtonByLabel("32");
		productDetailsPage.clickColorButtonByLabel("Black");
		productDetailsPage.sendKeysToQuantityTextbox("4");
		productDetailsPage.clickAddToCartButton();

		Assert.assertEquals(productDetailsPage.getShoppingCartQuantity(driver), "10");
	}

	@Test(groups = { "getProductWithOptions",
			"clearCart" }, description = "Verify that user can only add a product with options to the shopping cart when its color and size options are selected")
	public void Shopping_Cart_06_Add_Product_With_Options_To_Cart_When_Color_And_Size_Options_Are_Selected() {
		productListingPage = homepage.clickNavigationBarDropdownMultiLevelItemLinkByLabels(driver, "Women", category,
				subcategory);
		productDetailsPage = productListingPage.clickProductLinkByProductName(driver, productName);
		productDetailsPage.clickSizeButtonByLabel(productSize);
		productDetailsPage.clickAddToCartButton();

		Assert.assertEquals(productDetailsPage.getColorNotSelectedErrorMessage(), "This is a required field.");

		productDetailsPage.clickSizeButtonByLabel(productSize);
		productDetailsPage.clickColorButtonByLabel(productColor);
		productDetailsPage.clickAddToCartButton();

		Assert.assertEquals(productDetailsPage.getSizeNotSelectedErrorMessage(), "This is a required field.");

		productDetailsPage.clickSizeButtonByLabel(productSize);
		productDetailsPage.clickAddToCartButton();

		Assert.assertEquals(productDetailsPage.getProductAddedToCartSuccessMessage(driver),
				"You added " + productName + " to your shopping cart.");

	}

	@Test(groups = "getProductNoOptions", description = "Verify the error message displayed when user adds a product to the shopping cart with a quantity larger than 10000")
	public void Shopping_Cart_07_Error_Message_Adding_Product_With_A_Quantity_Larger_Than_10000() {
		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear", category);
		productDetailsPage = productListingPage.clickProductLinkByProductName(driver, productName);
		productDetailsPage.sendKeysToQuantityTextbox("15000");
		productDetailsPage.clickAddToCartButton();

		Assert.assertEquals(productDetailsPage.getMaximumQuantityErrorMessage(),
				"The maximum you may purchase is 10000.");
	}

	@Test(description = "Verify the error message displayed when a user adds a product to the shopping cart with a quantity exceeding the cart's maximum limit")
	public void Shopping_Cart_08_Error_Message_Adding_Product_With_A_Quantity_Exceeding_Cart_Maximum_Limit() {
		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear", "Watches");
		productDetailsPage = productListingPage.clickProductLinkByProductName(driver, "Luma Analog Watch");
		productDetailsPage.sendKeysToQuantityTextbox("1000");
		productDetailsPage.clickAddToCartButton();
		productDetailsPage.sendKeysToQuantityTextbox("10000");
		productDetailsPage.clickAddToCartButton();

		Assert.assertEquals(productDetailsPage.getMaximumCartQuantityExceeded(),
				"The requested qty exceeds the maximum qty allowed in shopping cart");
	}

	@Test(groups = "getProductNoOptions", description = "Verify the error message displayed when zero or negative values are filled in the 'Qty' field")
	public void Shopping_Cart_09_Error_Message_Filling_In_Qty_Field_With_Zero_Or_Negative_Value() {
		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear", category);
		productDetailsPage = productListingPage.clickProductLinkByProductName(driver, productName);
		productDetailsPage.sendKeysToQuantityTextbox("0");
		productDetailsPage.clickAddToCartButton();

		Assert.assertEquals(productDetailsPage.getMaximumQuantityErrorMessage(),
				"Please enter a quantity greater than 0.");

		productDetailsPage.sendKeysToQuantityTextbox("-5");
		productDetailsPage.clickAddToCartButton();

		Assert.assertEquals(productDetailsPage.getMaximumQuantityErrorMessage(),
				"Please enter a quantity greater than 0.");
	}

	@Test(groups = "getProductNoOptions", description = "Verify the error message displayed when no value is filled in the 'Qty' field")
	public void Shopping_Cart_10_Error_Message_No_Value_Is_Filled_In_Qty_Field() {
		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear", category);
		productDetailsPage = productListingPage.clickProductLinkByProductName(driver, productName);
		productDetailsPage.sendKeysToQuantityTextbox("");
		productDetailsPage.clickAddToCartButton();

		Assert.assertEquals(productDetailsPage.getMaximumQuantityErrorMessage(),
				"Please enter a valid number in this field.");
	}

	@Test(groups = { "getProductNoOptions",
			"clearCart" }, description = "Verify that the 'Table Rate - Best Way' shipping option is only available when the shipping country is the United States")
	public void Shopping_Cart_11_Table_Rate_Shipping_Method_Only_Available_To_United_States() {
		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear", category);
		productListingPage.clickAddToCartButtonByProductName(driver, productName);
		productListingPage.clickShoppingCartIcon(driver);
		shoppingCartPage = productListingPage.clickViewAndEditCartLink(driver);
		shoppingCartPage.clickEstimateShippingAndTaxHeader();
		shoppingCartPage.selectOptionCountryDropdown("Vietnam");

		Assert.assertTrue(shoppingCartPage.isTableRateRadioButtonNotDisplayed());

		shoppingCartPage.selectOptionCountryDropdown("United States");

		Assert.assertTrue(shoppingCartPage.isTableRateRadioButtonDisplayed());
	}

	@Test(groups = { "getProductNoOptions",
			"clearCart" }, description = "Verify that user can change the product quantity")
	public void Shopping_Cart_12_Update_Product_Quantity() {
		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear", category);
		productListingPage.clickAddToCartButtonByProductName(driver, productName);
		productListingPage.clickShoppingCartIcon(driver);
		shoppingCartPage = productListingPage.clickViewAndEditCartLink(driver);
		shoppingCartPage.sendKeysToQuantityTextboxByProductName("10", productName);
		shoppingCartPage.clickUpdateShoppingCartButton();

		Assert.assertEquals(shoppingCartPage.getQuantityValueByProductName(productName), "10");
	}

	@Test(groups = { "getProductNoOptions",
			"clearCart" }, description = "Verify that user is directed to the product details page when clicking the pen icon")
	public void Shopping_Cart_13_Click_Pen_Icon() {
		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear", category);
		productListingPage.clickAddToCartButtonByProductName(driver, productName);
		productListingPage.clickShoppingCartIcon(driver);
		shoppingCartPage = productListingPage.clickViewAndEditCartLink(driver);
		productDetailsPage = shoppingCartPage.clickPenIconByProductName(productName);

		Assert.assertEquals(productDetailsPage.getPageHeader(driver), productName);
	}

	@Test(groups = { "getProductNoOptions",
			"clearCart" }, description = "Verify that the product is removed from the shopping cart when clicking the trashcan icon")
	public void Shopping_Cart_14_Click_Trashcan_Icon() {
		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear", category);
		productListingPage.clickAddToCartButtonByProductName(driver, productName);
		productListingPage.clickShoppingCartIcon(driver);
		shoppingCartPage = productListingPage.clickViewAndEditCartLink(driver);
		shoppingCartPage.clickTrashcanIconByProductName(productName);

		Assert.assertTrue(shoppingCartPage.isProductNotDisplayedInShoppingCart(productName));
	}

	@AfterMethod(onlyForGroups = "clearCart")
	public void clearCartAndNavigateToHomepage() {
		homepage.clearShoppingCart(driver);
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeBrowserAndDriver();
	}

	private WebDriver driver;
	private JsonNode resultNode;
	private String category, subcategory, productName, productSize, productColor;
	private HomepageObject homepage;
	private ProductListingPageObject productListingPage;
	private ProductDetailsPageObject productDetailsPage;
	private ShoppingCartPageObject shoppingCartPage;
}
