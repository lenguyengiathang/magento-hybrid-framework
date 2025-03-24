package com.magento.commons;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import commons.BasePage;
import commons.BaseTest;
import commons.GlobalConstants;
import commons.PageGeneratorManager;
import pageObjects.ProductListingPageObject;
import pageUIs.BasePageUI;
import pageUIs.CompareProductsPageUI;
import pageUIs.MyWishListPageUI;
import utilities.ProductDataMapperAdvanced;
import utilities.ProductDataMapperAdvanced.Product;
import utilities.ProductDataMapperAdvanced.Subcategory;
import utilities.ProductDataMapperBasic;
import utilities.ProductDataMapperBasic.Category;

public class Products extends BaseTest {

	public Products(WebDriver driver) {
		this.driver = driver;
		basePage = BasePage.getBasePageObject();
	}

	public void loadBasicProductData() {
		if (productWithoutOptionsData == null) {
			productWithoutOptionsData = ProductDataMapperBasic.loadProductData("gear_products.json");
		}
	}

	public void loadAdvancedProductData(String jsonFile) {
		if (productWithOptionsData == null) {
			productWithOptionsData = ProductDataMapperAdvanced.loadProductData(jsonFile);
		}
	}

	public ProductDataMapperBasic.Category getRandomBasicCategory() {
		loadBasicProductData();
		randomBasicCategory = productWithoutOptionsData.getRandomCategory();
		return randomBasicCategory;
	}

	public ProductDataMapperBasic.Product getRandomBasicProductFromCategory(ProductDataMapperBasic.Category category) {
		randomBasicProduct = productWithoutOptionsData.getRandomProductFromCategory(category);
		return randomBasicProduct;
	}

	public ProductDataMapperAdvanced.Category getRandomAdvancedCategory() {
		randomAdvancedCategory = productWithOptionsData.getRandomCategory();
		return randomAdvancedCategory;
	}

	public ProductDataMapperAdvanced.Subcategory getRandomAdvancedSubcategory(
			ProductDataMapperAdvanced.Category category) {
		randomAdvancedSubcategory = productWithOptionsData.getRandomSubcategory(category);
		return randomAdvancedSubcategory;
	}

	public ProductDataMapperAdvanced.Product getRandomAdvancedProduct(
			ProductDataMapperAdvanced.Subcategory subcategory) {
		randomAdvancedProduct = productWithOptionsData.getRandomProductFromSubcategory(subcategory);
		return randomAdvancedProduct;
	}

	public String getRandomSizeOption(ProductDataMapperAdvanced.Product product) {
		randomAdvancedProductSize = productWithOptionsData.getRandomSize(product);
		return randomAdvancedProductSize;
	}

	public String getRandomColorOption(ProductDataMapperAdvanced.Product product) {
		randomAdvancedProductColor = productWithOptionsData.getRandomColor(product);
		return randomAdvancedProductColor;
	}

	public void addRandomProductWithOptionsToCart(String jsonFile) {
		try {
			loadAdvancedProductData(jsonFile);

			String group = jsonFile.contains("women") ? "Women" : "Men";

			utilities.ProductDataMapperAdvanced.Category category = getRandomAdvancedCategory();
			Subcategory subcategory = getRandomAdvancedSubcategory(category);
			Product product = getRandomAdvancedProduct(subcategory);
			productName = product.getProductName();
			productSize = getRandomSizeOption(product);
			productColor = getRandomColorOption(product);

			basePage.clickNavigationBarDropdownMultiLevelItemLinkByLabels(driver, group, category.getCategoryName(),
					subcategory.getSubcategoryName());
			basePage.addProductWithOptionsToCart(driver, productName, productSize, productColor);
			basePage.clickLumaLogo(driver);
		} catch (Exception e) {
			System.err.println("Error adding product to shopping cart: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void addRandomProductWithoutOptionsToCart() {
		try {
			Category category = getRandomBasicCategory();
			productName = getRandomBasicProductFromCategory(category).getProductName();

			basePage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear", category.getCategoryName());
			basePage.addProductWithNoOptionsToCart(driver, productName);
			basePage.clickLumaLogo(driver);
		} catch (Exception e) {
			System.err.println("Error adding product to shopping cart: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void addRandomProductWithOptionsToWishList(String jsonFile) {
		try {
			loadAdvancedProductData(jsonFile);

			String group = jsonFile.contains("women") ? "Women" : "Men";

			utilities.ProductDataMapperAdvanced.Category category = getRandomAdvancedCategory();
			Subcategory subcategory = getRandomAdvancedSubcategory(category);
			productName = getRandomAdvancedProduct(subcategory).getProductName();

			basePage.clickNavigationBarDropdownMultiLevelItemLinkByLabels(driver, group, category.getCategoryName(),
					subcategory.getSubcategoryName());
			basePage.clickWishListIconByProductName(driver, productName);
		} catch (Exception e) {
			System.err.println("Error adding product to wish list: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void addRandomProductWithoutOptionsToWishList() {
		try {
			Category category = getRandomBasicCategory();
			productName = getRandomBasicProductFromCategory(category).getProductName();

			basePage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear", category.getCategoryName());
			basePage.clickWishListIconByProductName(driver, productName);
		} catch (Exception e) {
			System.err.println("Error adding product to wish list: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public ProductListingPageObject addRandomProductWithOptionsToComparisonList(String jsonFile) {
		try {
			loadAdvancedProductData(jsonFile);

			String group = jsonFile.contains("women") ? "Women" : "Men";

			utilities.ProductDataMapperAdvanced.Category category = getRandomAdvancedCategory();
			Subcategory subcategory = getRandomAdvancedSubcategory(category);
			productName = getRandomAdvancedProduct(subcategory).getProductName();

			basePage.clickNavigationBarDropdownMultiLevelItemLinkByLabels(driver, group, category.getCategoryName(),
					subcategory.getSubcategoryName());
			basePage.clickCompareIconByProductName(driver, productName);

		} catch (Exception e) {
			System.err.println("Error adding product to comparison list: " + e.getMessage());
			e.printStackTrace();
		}
		return PageGeneratorManager.getProductListingPageObject(driver);
	}

	public ProductListingPageObject addRandomProductWithoutOptionsToComparisonList() {
		try {
			Category category = getRandomBasicCategory();
			productName = getRandomBasicProductFromCategory(category).getProductName();

			basePage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear", category.getCategoryName());
			basePage.clickCompareIconByProductName(driver, productName);
		} catch (Exception e) {
			System.err.println("Error adding product to comparison list: " + e.getMessage());
			e.printStackTrace();
		}
		return PageGeneratorManager.getProductListingPageObject(driver);
	}

	public void clearWishList() {
		try {
			basePage.clickCustomerNameDropdown(driver);
			basePage.clickMyWishListDropdownLink(driver);
			while (true) {
				List<WebElement> trashcanIcons = basePage.getWebElements(driver, MyWishListPageUI.TRASHCAN_ICON);
				if (trashcanIcons.isEmpty()) {
					break;
				}
				basePage.hoverOverElement(driver, MyWishListPageUI.FIRST_PRODUCT_CARD);
				basePage.clickElementByJS(driver, MyWishListPageUI.TRASHCAN_ICON);
				basePage.waitForElementVisible(driver, BasePageUI.MainContent.PAGE_HEADER);
			}
		} catch (Exception e) {
			System.err.println("Error clearing wish list: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void clearComparisonList() {
		try {
			basePage.clickCompareProductsLink(driver);
			while (true) {
				List<WebElement> crossIcons = basePage.getWebElements(driver, CompareProductsPageUI.CROSS_ICON);
				if (crossIcons.isEmpty()) {
					break;
				}
				crossIcons.get(0).click();
				basePage.clickConfirmationPopupOKButton(driver);
				basePage.waitForElementVisible(driver, BasePageUI.MainContent.PAGE_HEADER);
			}
			sleepInSecond(GlobalConstants.SHORT_TIMEOUT);
		} catch (Exception e) {
			System.err.println("Error clearing comparison list: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private WebDriver driver;
	private BasePage basePage;
	private ProductDataMapperBasic productWithoutOptionsData;
	private ProductDataMapperAdvanced productWithOptionsData;
	private ProductDataMapperBasic.Category randomBasicCategory;
	private ProductDataMapperBasic.Product randomBasicProduct;
	private ProductDataMapperAdvanced.Category randomAdvancedCategory;
	private ProductDataMapperAdvanced.Subcategory randomAdvancedSubcategory;
	private ProductDataMapperAdvanced.Product randomAdvancedProduct;
	private String randomAdvancedProductSize, randomAdvancedProductColor;
	public static String productName, productSize, productColor;
}
