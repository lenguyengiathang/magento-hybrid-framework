package pageObjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import commons.BasePage;
import commons.PageGeneratorManager;
import pageUIs.CompareProductsPageUI;

public class CompareProductsPageObject extends BasePage {
	private WebDriver driver;

	public CompareProductsPageObject(WebDriver driver) {
		this.driver = driver;
	}

	public String getProductAttributeValueByProductNameAndLabel(String productName, String attribute) {
		waitForElementVisible(driver, CompareProductsPageUI.DYNAMIC_PRODUCT_ATTRIBUTE_VALUE_BY_PRODUCT_NAME_AND_LABEL,
				productName, attribute);
		return getElementText(driver, CompareProductsPageUI.DYNAMIC_PRODUCT_ATTRIBUTE_VALUE_BY_PRODUCT_NAME_AND_LABEL,
				productName, attribute);
	}

	public void clickCrossIcon() {
		waitForElementClickable(driver, CompareProductsPageUI.CROSS_ICON);
		clickElementByJS(driver, CompareProductsPageUI.CROSS_ICON);
	}

	public BasePage clickAddToCartButtonByProductName(String productName) {
		String url = getPageUrl(driver);
		waitForElementClickable(driver, CompareProductsPageUI.DYNAMIC_ADD_TO_CART_BUTTON_BY_PRODUCT_NAME, productName);
		clickElementByJS(driver, CompareProductsPageUI.DYNAMIC_ADD_TO_CART_BUTTON_BY_PRODUCT_NAME, productName);

		if (getPageUrl(driver).equals(url)) {
			return PageGeneratorManager.getCompareProductsPage(driver);
		}
		return PageGeneratorManager.getProductDetailsPageObject(driver);
	}

	public void clickCrossIconByProductName(String productName) {
		int index = 0;
		List<WebElement> links = getWebElements(driver, CompareProductsPageUI.PRODUCT_LINK);

		for (int i = 0; i < links.size(); i++) {
			if (links.get(i).getText().equals(productName)) {
				index = i;
			}
		}

		waitForElementClickable(driver, CompareProductsPageUI.DYNAMIC_CROSS_ICON_BY_INDEX, String.valueOf(index));
		clickElementByJS(driver, CompareProductsPageUI.DYNAMIC_CROSS_ICON_BY_INDEX, String.valueOf(index));
	}

	public boolean isProductNotDisplayedInCompareProductsPage(String productName) {
		return isElementNotDisplayed(driver, CompareProductsPageUI.DYNAMIC_PRODUCT_LINK_BY_PRODUCT_NAME, productName);
	}

}
