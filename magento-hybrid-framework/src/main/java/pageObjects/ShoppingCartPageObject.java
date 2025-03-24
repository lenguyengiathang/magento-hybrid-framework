package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.BasePage;
import commons.GlobalConstants;
import commons.PageGeneratorManager;
import pageUIs.BasePageUI;
import pageUIs.ShoppingCartPageUI;

public class ShoppingCartPageObject extends BasePage {
	private WebDriver driver;

	public ShoppingCartPageObject(WebDriver driver) {
		this.driver = driver;
	}

	public String getProductSizeByProductName(String productName) {
		waitForElementVisible(driver, ShoppingCartPageUI.DYNAMIC_PRODUCT_SIZE_BY_PRODUCT_NAME, productName);
		return getElementText(driver, ShoppingCartPageUI.DYNAMIC_PRODUCT_SIZE_BY_PRODUCT_NAME, productName);
	}

	public String getProductColorByProductName(String productName) {
		waitForElementVisible(driver, ShoppingCartPageUI.DYNAMIC_PRODUCT_COLOR_BY_PRODUCT_NAME, productName);
		return getElementText(driver, ShoppingCartPageUI.DYNAMIC_PRODUCT_COLOR_BY_PRODUCT_NAME, productName);
	}

	public void sendKeysToQuantityTextboxByProductName(String quantity, String productName) {
		waitForElementVisible(driver, ShoppingCartPageUI.DYNAMIC_QUANTITY_TEXTBOX_BY_PRODUCT_NAME, productName);
		sendKeysToElement(driver, ShoppingCartPageUI.DYNAMIC_QUANTITY_TEXTBOX_BY_PRODUCT_NAME, quantity, productName);
	}

	public String getQuantityValueByProductName(String productName) {
		waitForElementVisible(driver, ShoppingCartPageUI.DYNAMIC_QUANTITY_TEXTBOX_BY_PRODUCT_NAME, productName);
		return getElementValueByJS(driver, ShoppingCartPageUI.DYNAMIC_QUANTITY_TEXTBOX_BY_PRODUCT_NAME, productName);
	}

	public void clickUpdateShoppingCartButton() {
		waitForElementClickable(driver, ShoppingCartPageUI.UPDATE_SHOPPING_CART_BUTTON);
		clickElementByJS(driver, ShoppingCartPageUI.UPDATE_SHOPPING_CART_BUTTON);
	}

	public void clickApplyDiscountCodeHeader() {
		waitForElementClickable(driver, ShoppingCartPageUI.APPLY_DISCOUNT_CODE_HEADER);
		clickElementByJS(driver, ShoppingCartPageUI.APPLY_DISCOUNT_CODE_HEADER);
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

	public void clickEstimateShippingAndTaxHeader() {
		waitForElementClickable(driver, ShoppingCartPageUI.ESTIMATED_SHIPPING_AND_TAX_HEADER);
		clickElementByJS(driver, ShoppingCartPageUI.ESTIMATED_SHIPPING_AND_TAX_HEADER);
	}

	public void selectOptionCountryDropdown(String country) {
		waitForElementVisible(driver, ShoppingCartPageUI.COUNTRY_DROPDOWN);
		selectOptionDefaultDropdown(driver, ShoppingCartPageUI.COUNTRY_DROPDOWN, country);
		sleepInSecond(GlobalConstants.SHORT_TIMEOUT);
	}

	public void clickShippingMethodRadioButtonByLabel(String label) {
		waitForElementClickable(driver, ShoppingCartPageUI.DYNAMIC_SHIPPING_METHOD_RADIO_BUTTON_BY_LABEL, label);
		clickElementByJS(driver, ShoppingCartPageUI.DYNAMIC_SHIPPING_METHOD_RADIO_BUTTON_BY_LABEL, label);
		sleepInSecond(GlobalConstants.SHORT_TIMEOUT);
	}

	public boolean isTableRateRadioButtonDisplayed() {
		return isElementDisplayed(driver, ShoppingCartPageUI.DYNAMIC_SHIPPING_METHOD_RADIO_BUTTON_BY_LABEL,
				"Table Rate");
	}

	public boolean isTableRateRadioButtonNotDisplayed() {
		return isElementNotDisplayed(driver, ShoppingCartPageUI.DYNAMIC_SHIPPING_METHOD_RADIO_BUTTON_BY_LABEL,
				"Table Rate");
	}

	public float getOrderSubtotal() {
		waitForElementVisible(driver, ShoppingCartPageUI.ORDER_SUBTOTAL);
		return Float.parseFloat(getElementText(driver, ShoppingCartPageUI.ORDER_SUBTOTAL).replace("$", ""));
	}

	public float getOrderDiscount() {
		waitForElementVisible(driver, ShoppingCartPageUI.ORDER_DISCOUNT);
		return Float.parseFloat(getElementText(driver, ShoppingCartPageUI.ORDER_DISCOUNT).replace("-$", ""));
	}

	public float getOrderShipping() {
		waitForElementVisible(driver, ShoppingCartPageUI.ORDER_SHIPPING);
		return Float.parseFloat(getElementText(driver, ShoppingCartPageUI.ORDER_SHIPPING).replace("$", ""));
	}

	public float getOrderTotal() {
		waitForElementVisible(driver, ShoppingCartPageUI.ORDER_TOTAL);
		return Float.parseFloat(getElementText(driver, ShoppingCartPageUI.ORDER_TOTAL).replace("$", ""));
	}

	public CheckoutPageObject clickProceedToCheckoutButton() {
		waitForElementClickable(driver, ShoppingCartPageUI.PROCEED_TO_CHECKOUT_BUTTON);
		clickElementByJS(driver, ShoppingCartPageUI.PROCEED_TO_CHECKOUT_BUTTON);
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

	public boolean isSummaryLoadingIconNotDisplayed() {
		return isElementNotDisplayed(driver, ShoppingCartPageUI.SUMMARY_LOADING_ICON);
	}

	public ProductDetailsPageObject clickPenIconByProductName(String productName) {
		waitForElementClickable(driver, ShoppingCartPageUI.DYNAMIC_PEN_ICON_BY_PRODUCT_NAME, productName);
		clickElementByJS(driver, ShoppingCartPageUI.DYNAMIC_PEN_ICON_BY_PRODUCT_NAME, productName);
		return PageGeneratorManager.getProductDetailsPageObject(driver);
	}

	public void clickTrashcanIconByProductName(String productName) {
		waitForElementClickable(driver, ShoppingCartPageUI.DYNAMIC_TRASHCAN_ICON_BY_PRODUCT_NAME, productName);
		clickElementByJS(driver, ShoppingCartPageUI.DYNAMIC_TRASHCAN_ICON_BY_PRODUCT_NAME, productName);
	}

	public boolean isProducDisplayedInShoppingCart(String productName) {
		return isElementDisplayed(driver, ShoppingCartPageUI.DYNAMIC_PRODUCT_LINK_BY_PRODUCT_NAME, productName);
	}

	public boolean isProductNotDisplayedInShoppingCart(String productName) {
		return isElementNotDisplayed(driver, ShoppingCartPageUI.DYNAMIC_PRODUCT_LINK_BY_PRODUCT_NAME, productName);
	}

	public HomepageObject clickHereToContinueShoppingLink() {
		waitForElementClickable(driver, ShoppingCartPageUI.HERE_LINK);
		clickElementByJS(driver, ShoppingCartPageUI.HERE_LINK);
		return PageGeneratorManager.getHomepage(driver);
	};

}
