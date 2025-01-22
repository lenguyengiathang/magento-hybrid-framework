package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.BasePage;
import commons.PageGeneratorManager;
import pageUIs.ShipToMultipleAddressesPageUI;

public class ShipToMultipleAddressesPageObject extends BasePage {
	private WebDriver driver;

	public ShipToMultipleAddressesPageObject(WebDriver driver) {
		this.driver = driver;
	}

	public void sendKeysToQuantityTextboxByProductName(String productName, String quantity) {
		waitForElementVisible(driver, ShipToMultipleAddressesPageUI.DYNAMIC_QUANTITY_TEXTBOX_BY_PRODUCT_NAME,
				productName);
		sendKeysToElement(driver, ShipToMultipleAddressesPageUI.DYNAMIC_QUANTITY_TEXTBOX_BY_PRODUCT_NAME, productName,
				quantity);
	}

	public void selectItemSendToDropdownByProductName(String productName, String shippingAddress) {
		selectOptionDefaultDropdown(driver, ShipToMultipleAddressesPageUI.DYNAMIC_SEND_TO_DROPDOWN_BY_PRODUCT_NAME,
				shippingAddress, productName);
	}

	public void clickRemoveItemLinkByProductName(String productName) {
		waitForElementClickable(driver, ShipToMultipleAddressesPageUI.DYNAMIC_REMOVE_ITEM_LINK_BY_PRODUCT_NAME,
				productName);
		clickElementByJS(driver, ShipToMultipleAddressesPageUI.DYNAMIC_REMOVE_ITEM_LINK_BY_PRODUCT_NAME, productName);
	}

	public SelectShippingMethodPageObject clickGoToShippingInformationButton() {
		waitForElementClickable(driver, ShipToMultipleAddressesPageUI.GO_TO_SHIPPING_INFORMATION_BUTTON);
		clickElementByJS(driver, ShipToMultipleAddressesPageUI.GO_TO_SHIPPING_INFORMATION_BUTTON);
		return PageGeneratorManager.getSelectShippingMethodPage(driver);
	}

	public ShoppingCartPageObject clickBackToShoppingCartLink() {
		waitForElementClickable(driver, ShipToMultipleAddressesPageUI.BACK_TO_SHOPPING_CART_LINK);
		clickElementByJS(driver, ShipToMultipleAddressesPageUI.BACK_TO_SHOPPING_CART_LINK);
		return PageGeneratorManager.getShoppingCartPageObject(driver);
	}

	public void clickUpdateQuantityAndAddressesButton() {
		waitForElementClickable(driver, ShipToMultipleAddressesPageUI.UPDATE_QUANTITY_AND_ADDRESSES_BUTTON);
		clickElementByJS(driver, ShipToMultipleAddressesPageUI.UPDATE_QUANTITY_AND_ADDRESSES_BUTTON);
	}

	public void clickEnterANewAddressButton() {
		waitForElementClickable(driver, ShipToMultipleAddressesPageUI.ENTER_A_NEW_ADDRESS_BUTTON);
		clickElementByJS(driver, ShipToMultipleAddressesPageUI.ENTER_A_NEW_ADDRESS_BUTTON);
	}

}
