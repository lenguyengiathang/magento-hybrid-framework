package com.magento.commons;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;

import commons.BasePage;
import commons.BaseTest;
import pageUIs.BasePageUI;
import pageUIs.ShoppingCartPageUI;
import utilities.ProductDataMapperAdvanced;
import utilities.ProductDataMapperBasic;

public class Orders extends BaseTest {

	public Orders(WebDriver driver) {
		this.driver = driver;
		basePage = BasePage.getBasePageObject();
	}

	public void addProductWithOptionsToCart(String fileName) {

	}

	public void addProductWithoutOptionsToCart(String fileName) {
		productWithoutOptionsData = ProductDataMapperBasic.loadProductData(fileName);

	}

	@AfterMethod
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

}
