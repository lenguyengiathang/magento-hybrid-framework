package pageUIs;

public class ReviewOrderPageUI {
	public static final String CHANGE_BILLING_ADDRESS_LINK = "xpath=//span[text()='Billing Address']/following-sibling::a";
	public static final String CHANGE_PAYMENT_METHOD_LINK = "xpath=//span[text()='Payment Method']/following-sibling::a";
	public static final String DYNAMIC_CHANGE_SHIPPING_ADDRESS_LINK_BY_PRODUCT_NAME = "xpath=//a[text()='%s']/ancestor::div[@class='block-content']//div[@class='box box-shipping-address']//a[@class='action edit']";
	public static final String DYNAMIC_CHANGE_SHIPPING_METHOD_LINK_BY_PRODUCT_NAME = "xpath=//a[text()='%s']/ancestor::div[@class='block-content']//div[@class='box box-shipping-method']//a[@class='action edit']";
	public static final String DYNAMIC_EDIT_ITEM_LINK_BY_PRODUCT_NAME = "xpath=//a[text()='%s']/ancestor::tbody/preceding-sibling::thead//a";
	public static final String REVIEW_ORDER_BUTTON = "id=review-button";
	public static final String BACK_TO_BILLING_INFORMATION_LINK = "css=.back";
	public static final String PLACE_ORDER_BUTTON = "id=review-button";

}
