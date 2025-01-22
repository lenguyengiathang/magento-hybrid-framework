package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.BasePage;
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

}
