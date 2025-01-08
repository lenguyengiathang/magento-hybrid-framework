package com.magento.commons;

import org.openqa.selenium.WebDriver;

import commons.BasePage;
import commons.BaseTest;
import utilities.ProductDataMapperAdvanced;
import utilities.ProductDataMapperBasic;

public class WishList extends BaseTest {

	public WishList(WebDriver driver) {
		this.driver = driver;
		basePage = BasePage.getBasePageObject();
	}

	private WebDriver driver;
	private BasePage basePage;
	private ProductDataMapperBasic productWithoutOptionsData;
	private ProductDataMapperAdvanced productWithOptionsData;

}
