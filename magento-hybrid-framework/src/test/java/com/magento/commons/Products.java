package com.magento.commons;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import commons.BasePage;
import commons.BaseTest;
import pageUIs.BasePageUI;
import pageUIs.CompareProductsPageUI;
import pageUIs.MyWishListPageUI;
import pageUIs.ShoppingCartPageUI;
import utilities.ProductDataMapperAdvanced;
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
			productWithoutOptionsData = ProductDataMapperBasic.loadProductData("GearProducts.json");
		}
	}

	public void loadAdvancedProductData(String jsonFile) {
		if (productWithoutOptionsData == null) {
			productWithOptionsData = ProductDataMapperAdvanced.loadProductData(jsonFile);
		}
	}

	public ProductDataMapperBasic.Category getRandomBasicCategory() {
		loadBasicProductData();
		randomBasicCategory = productWithoutOptionsData.getRandomCategory();
		return randomBasicCategory;
	}

	public ProductDataMapperBasic.Product getRandomBasicProductFromCategory(ProductDataMapperBasic.Category category) {
		randomBasicProduct = productWithoutOptionsData.getRandomProductFromCategory(getRandomBasicCategory());
		return randomBasicProduct;
	}

	public ProductDataMapperAdvanced.Category getRandomAdvancedCategory() {
		randomAdvancedCategory = productWithOptionsData.getRandomCategory();
		return randomAdvancedCategory;
	}

	public ProductDataMapperAdvanced.Subcategory getRandomAdvancedSubcategory() {
		randomAdvancedSubcategory = productWithOptionsData.getRandomSubcategory(randomAdvancedCategory);
		return randomAdvancedSubcategory;
	}

	public ProductDataMapperAdvanced.Product getRandomAdvancedProduct() {
		randomAdvancedProduct = productWithOptionsData.getRandomProductFromSubcategory(randomAdvancedSubcategory);
		return randomAdvancedProduct;
	}

	public String getRandomSizeOption() {
		return productWithOptionsData.getRandomSize(randomAdvancedProduct);
	}

	public String getRandomColorOption() {
		return productWithOptionsData.getRandomColor(randomAdvancedProduct);
	}

	public void addRandomProductWithOptionsToCart(String group) {
		if (group.equalsIgnoreCase("Men") || group.equalsIgnoreCase("Women")) {
			String jsonFile = group.equalsIgnoreCase("Men") ? "MenProducts.json" : "WomenProducts.json";

			loadAdvancedProductData(jsonFile);

			utilities.ProductDataMapperAdvanced.Category category = getRandomAdvancedCategory();
			Subcategory subcategory = getRandomAdvancedSubcategory();
			productName = getRandomAdvancedProduct().getProductName();

			basePage.clickNavigationBarDropdownMultiLevelItemLinkByLabels(driver, group, category.getCategoryName(),
					subcategory.getSubcategoryName());
			basePage.addProductWithOptionsToCart(driver, productName, getRandomSizeOption(), getRandomColorOption());
		}
	}

	public void addRandomProductWithoutOptionsToCart() {
		Category category = getRandomBasicCategory();
		productName = getRandomBasicProductFromCategory(category).getProductName();

		basePage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear", category.getCategoryName());
		basePage.clickAddToCartButtonByProductNameWithoutOptions(driver, productName);
	}

	public void addProductWithOptionsToWishList(String group) {
		if (group.equalsIgnoreCase("Men") || group.equalsIgnoreCase("Women")) {
			String jsonFile = group.equalsIgnoreCase("Men") ? "MenProducts.json" : "WomenProducts.json";

			loadAdvancedProductData(jsonFile);

			utilities.ProductDataMapperAdvanced.Category category = getRandomAdvancedCategory();
			Subcategory subcategory = getRandomAdvancedSubcategory();
			productName = getRandomAdvancedProduct().getProductName();

			basePage.clickNavigationBarDropdownMultiLevelItemLinkByLabels(driver, group, category.getCategoryName(),
					subcategory.getSubcategoryName());
			basePage.clickWishListIconByProductName(driver, productName);
		}
	}

	public void addProductWithoutOptionsToWishList() {
		Category category = getRandomBasicCategory();
		productName = getRandomBasicProductFromCategory(category).getProductName();

		basePage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear", category.getCategoryName());
		basePage.clickWishListIconByProductName(driver, productName);
	}

	public void clearComparisonList() {
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
	}

	public void clearWishList() {
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
	}

	public void clearShoppingCart() {
		basePage.clickShoppingCartIcon(driver);
		basePage.clickViewAndEditCartLink(driver);
		while (true) {
			List<WebElement> trashcanIcons = basePage.getWebElements(driver, ShoppingCartPageUI.TRASHCAN_ICON);
			if (trashcanIcons.isEmpty()) {
				break;
			}
			trashcanIcons.get(0).click();
			basePage.waitForElementVisible(driver, BasePageUI.MainContent.PAGE_HEADER);
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
	public static String productName;
}
