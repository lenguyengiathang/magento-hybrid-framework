package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.BasePage;
import commons.PageGeneratorManager;
import pageUIs.BasePageUI;
import pageUIs.ShoppingCartPageUI;

public class ShoppingCartPageObject extends BasePage {
	private WebDriver driver;

	public ShoppingCartPageObject(WebDriver driver) {
		this.driver = driver;
	}

	public void sendKeysToQuantityTextboxByProductName(String quantity, String productName) {
		waitForElementVisible(driver, ShoppingCartPageUI.DYNAMIC_QUANTITY_TEXTBOX_BY_PRODUCT_NAME, productName);
		sendKeysToElement(driver, ShoppingCartPageUI.DYNAMIC_QUANTITY_TEXTBOX_BY_PRODUCT_NAME, productName, quantity);
	}

	public void clickUpdateShoppingCartButton() {
		waitForElementClickable(driver, ShoppingCartPageUI.UPDATE_SHOPPING_CART_BUTTON);
		clickElementByJS(driver, ShoppingCartPageUI.UPDATE_SHOPPING_CART_BUTTON);
	}

	public void clickApplyDiscountCodeHeader() {
		waitForElementClickable(driver, ShoppingCartPageUI.UPDATE_SHOPPING_CART_BUTTON);
		clickElementByJS(driver, ShoppingCartPageUI.UPDATE_SHOPPING_CART_BUTTON);
	}

	public void sendKeysToEnterDiscountCodeTextbox(String discountCode) {
		waitForElementVisible(driver, ShoppingCartPageUI.ENTER_DISCOUNT_CODE_TEXTBOX);
		sendKeysToElement(driver, ShoppingCartPageUI.ENTER_DISCOUNT_CODE_TEXTBOX, discountCode);
	}

	public void clickApplyDiscountButton() {
		waitForElementClickable(driver, ShoppingCartPageUI.APPLY_DISCOUNT_BUTTON);
		clickElementByJS(driver, ShoppingCartPageUI.APPLY_DISCOUNT_BUTTON);
	}

	public String getDiscountCodeAppliedSuccessMessage() {
		waitForElementVisible(driver, BasePageUI.MainContent.MESSAGE);
		return getElementText(driver, BasePageUI.MainContent.MESSAGE);
	}

	public void clickCancelCouponButton() {
		waitForElementClickable(driver, ShoppingCartPageUI.CANCEL_COUPON_BUTTON);
		clickElementByJS(driver, ShoppingCartPageUI.CANCEL_COUPON_BUTTON);
	}

	public String getDiscountCodeRemovedSuccessMessage() {
		waitForElementVisible(driver, BasePageUI.MainContent.MESSAGE);
		return getElementText(driver, BasePageUI.MainContent.MESSAGE);
	}

	public String getInvalidDiscountCodeErrorMessage() {
		waitForElementVisible(driver, BasePageUI.MainContent.MESSAGE);
		return getElementText(driver, BasePageUI.MainContent.MESSAGE);
	}

	public void clickShippingMethodRadioButtonByLabel(String label) {
		waitForElementClickable(driver, ShoppingCartPageUI.DYNAMIC_SHIPPING_METHOD_RADIO_BUTTON_BY_LABEL, label);
		clickElementByJS(driver, ShoppingCartPageUI.DYNAMIC_SHIPPING_METHOD_RADIO_BUTTON_BY_LABEL, label);
	}

	public float getOrderSubtotal() {
		waitForElementVisible(driver, ShoppingCartPageUI.ORDER_SUBTOTAL);
		return Float.parseFloat(getElementText(driver, ShoppingCartPageUI.ORDER_SUBTOTAL));
	}

	public float getOrderDiscount() {
		waitForElementVisible(driver, ShoppingCartPageUI.ORDER_DISCOUNT);
		return Float.parseFloat(getElementText(driver, ShoppingCartPageUI.ORDER_DISCOUNT));
	}

	public float getOrderShipping() {
		waitForElementVisible(driver, ShoppingCartPageUI.ORDER_SHIPPING);
		return Float.parseFloat(getElementText(driver, ShoppingCartPageUI.ORDER_SHIPPING));
	}

	public float getOrderTotal() {
		waitForElementVisible(driver, ShoppingCartPageUI.ORDER_TOTAL);
		return Float.parseFloat(getElementText(driver, ShoppingCartPageUI.ORDER_TOTAL));
	}

	public CheckoutPageObject clickProceedToCheckoutButton() {
		waitForElementClickable(driver, ShoppingCartPageUI.PROCEED_TO_CHECKOUT);
		clickElementByJS(driver, ShoppingCartPageUI.PROCEED_TO_CHECKOUT);
		return PageGeneratorManager.getCheckoutPageObject(driver);
	}

	public ShipToMultipleAddressesPageObject clickCheckOutWithMultipleAddressesLink() {
		waitForElementClickable(driver, ShoppingCartPageUI.CHECK_OUT_WITH_MULTIPLE_ADDRESSES);
		clickElementByJS(driver, ShoppingCartPageUI.CHECK_OUT_WITH_MULTIPLE_ADDRESSES);
		return PageGeneratorManager.getShipToMultipleAddressesPage(driver);
	}

	public void clickMoveToWishListLinkByProductName(String productName) {
		waitForElementClickable(driver, ShoppingCartPageUI.DYNAMIC_MOVE_TO_WISH_LIST_LINK_BY_PRODUCT_NAME, productName);
		clickElementByJS(driver, ShoppingCartPageUI.DYNAMIC_MOVE_TO_WISH_LIST_LINK_BY_PRODUCT_NAME, productName);
	}

	public String getMovedToWishListSuccessMessage() {
		waitForElementVisible(driver, BasePageUI.MainContent.MESSAGE);
		return getElementText(driver, BasePageUI.MainContent.MESSAGE);
	}
}
