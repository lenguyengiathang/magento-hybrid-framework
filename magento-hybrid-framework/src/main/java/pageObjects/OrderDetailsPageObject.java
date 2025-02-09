package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.BasePage;
import commons.PageGeneratorManager;
import pageUIs.OrderDetailsPageUI;

public class OrderDetailsPageObject extends BasePage {
	private WebDriver driver;

	public OrderDetailsPageObject(WebDriver driver) {
		this.driver = driver;
	}

	public String getOrderStatus() {
		waitForElementVisible(driver, OrderDetailsPageUI.ORDER_STATUS);
		return getElementText(driver, OrderDetailsPageUI.ORDER_STATUS);
	}

	public String getOrderDate() {
		waitForElementVisible(driver, OrderDetailsPageUI.ORDER_DATE);
		return getElementText(driver, OrderDetailsPageUI.ORDER_DATE);
	}

	public ShoppingCartPageObject clickReorderLink() {
		waitForElementClickable(driver, OrderDetailsPageUI.REORDER_LINK);
		clickElementByJS(driver, OrderDetailsPageUI.REORDER_LINK);
		return PageGeneratorManager.getShoppingCartPageObject(driver);
	}

	public String getProductSKUByProductName(String productName) {
		waitForElementVisible(driver, OrderDetailsPageUI.DYNAMIC_SKU_BY_PRODUCT_NAME, productName);
		return getElementText(driver, OrderDetailsPageUI.DYNAMIC_SKU_BY_PRODUCT_NAME, productName);
	}

	public Float getProductPriceByProductName(String productName) {
		waitForElementVisible(driver, OrderDetailsPageUI.DYNAMIC_PRICE_BY_PRODUCT_NAME, productName);
		String priceText = getElementText(driver, OrderDetailsPageUI.DYNAMIC_PRICE_BY_PRODUCT_NAME, productName);
		return Float.parseFloat(priceText.replace("$", ""));

	}

	public String getProductQuantityByProductName(String productName) {
		waitForElementVisible(driver, OrderDetailsPageUI.DYNAMIC_QUANTITY_BY_PRODUCT_NAME, productName);
		return getElementText(driver, OrderDetailsPageUI.DYNAMIC_QUANTITY_BY_PRODUCT_NAME, productName).split(" ")[1];
	}

	public Float getProductSubtotalByProductName(String productName) {
		waitForElementVisible(driver, OrderDetailsPageUI.DYNAMIC_PRICE_BY_PRODUCT_NAME, productName);
		String subtotalText = getElementText(driver, OrderDetailsPageUI.DYNAMIC_PRICE_BY_PRODUCT_NAME, productName);
		return Float.parseFloat(subtotalText.replace("$", ""));
	}

	public String getProductSizeByProductName(String productName) {
		waitForElementVisible(driver, OrderDetailsPageUI.DYNAMIC_SIZE_BY_PRODUCT_NAME, productName);
		return getElementText(driver, OrderDetailsPageUI.DYNAMIC_SIZE_BY_PRODUCT_NAME, productName);
	}

	public String getProductColorByProductName(String productName) {
		waitForElementVisible(driver, OrderDetailsPageUI.DYNAMIC_COLOR_BY_PRODUCT_NAME, productName);
		return getElementText(driver, OrderDetailsPageUI.DYNAMIC_COLOR_BY_PRODUCT_NAME, productName);
	}

	public Float getOrderSubtotal() {
		waitForElementVisible(driver, OrderDetailsPageUI.ORDER_SUBTOTAL);
		return Float.parseFloat(getElementText(driver, OrderDetailsPageUI.ORDER_SUBTOTAL).replace("$", ""));
	}

	public Float getOrderDiscount() {
		waitForElementVisible(driver, OrderDetailsPageUI.ORDER_DISCOUNT);
		return Float.parseFloat(getElementText(driver, OrderDetailsPageUI.ORDER_DISCOUNT).replace("$", ""));
	}

	public Float getOrderShippingAndHandling() {
		waitForElementVisible(driver, OrderDetailsPageUI.ORDER_SHIPPING_AND_HANDLING);
		return Float
				.parseFloat(getElementText(driver, OrderDetailsPageUI.ORDER_SHIPPING_AND_HANDLING).replace("$", ""));
	}

	public Float getOrderGrandTotal() {
		waitForElementVisible(driver, OrderDetailsPageUI.ORDER_GRAND_TOTAL);
		return Float.parseFloat(getElementText(driver, OrderDetailsPageUI.ORDER_GRAND_TOTAL).replace("$", ""));
	}

	public String getShippingAddress() {
		waitForElementVisible(driver, OrderDetailsPageUI.SHIPPING_ADDRESS);
		return getElementText(driver, OrderDetailsPageUI.SHIPPING_ADDRESS);
	}

	public String getShippingMethod() {
		waitForElementVisible(driver, OrderDetailsPageUI.SHIPPING_METHOD);
		return getElementText(driver, OrderDetailsPageUI.SHIPPING_METHOD);
	}

	public String getBillingAddress() {
		waitForElementVisible(driver, OrderDetailsPageUI.BILLING_ADDRESS);
		return getElementText(driver, OrderDetailsPageUI.BILLING_ADDRESS);
	}

}
