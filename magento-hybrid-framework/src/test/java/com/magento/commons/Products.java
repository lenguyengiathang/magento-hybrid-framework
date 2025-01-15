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
import utilities.ProductDataMapperBasic;

public class Products extends BaseTest {

	public Products(WebDriver driver) {
		this.driver = driver;
		basePage = BasePage.getBasePageObject();
	}

	public void addProductWithOptionsToCart(String fileName) {

	}

	public void addProductWithoutOptionsToCart(String fileName) {
		productWithoutOptionsData = ProductDataMapperBasic.loadProductData(fileName);

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
		basePage.clickLumaLogo(driver);
	}

	public void clearWishList() {
		basePage.clickCustomerNameDropdown(driver);
		basePage.clickMyWishListDropdownLink(driver);
		while (true) {
			List<WebElement> trashcanIcons = basePage.getWebElements(driver, MyWishListPageUI.TRASHCAN_ICON);
			if (trashcanIcons.isEmpty()) {
				break;
			}
			trashcanIcons.get(0).click();
			basePage.waitForElementVisible(driver, BasePageUI.MainContent.PAGE_HEADER);
		}
		basePage.clickLumaLogo(driver);
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
		basePage.clickLumaLogo(driver);
	}

	private WebDriver driver;
	private BasePage basePage;
	private ProductDataMapperBasic productWithoutOptionsData;
	private ProductDataMapperAdvanced productWithOptionsData;

}
