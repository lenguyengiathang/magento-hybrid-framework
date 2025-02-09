package pageUIs;

public class OrderDetailsPageUI {
	public static final String ORDER_STATUS = "css=.order-status";
	public static final String ORDER_DATE = "css=.order-date>span:nth-of-type(2)";
	public static final String REORDER_LINK = "xpath=//span[text()='Reorder']/parent::a";
	public static final String DYNAMIC_SKU_BY_PRODUCT_NAME = "xpath=//tbody//strong[text()='%s']/parent::td/following-sibling::td[@data-th='SKU']";
	public static final String DYNAMIC_PRICE_BY_PRODUCT_NAME = "xpath=//tbody//strong[text()='%s']/parent::td/following-sibling::td[@data-th='Price']//span[@class='price']";
	public static final String DYNAMIC_QUANTITY_BY_PRODUCT_NAME = "xpath=//tbody//strong[text()='%s']/parent::td/following-sibling::td[@data-th='Qty']//span[@class='content']";
	public static final String DYNAMIC_SUBTOTAL_BY_PRODUCT_NAME = "xpath=//tbody//strong[text()='%s']/parent::td/following-sibling::td[@data-th='Subtotal']//span[@class='price']";
	public static final String DYNAMIC_SIZE_BY_PRODUCT_NAME = "xpath=//tbody//strong[text()='%s']/parent::td/dl/dd[1]";
	public static final String DYNAMIC_COLOR_BY_PRODUCT_NAME = "xpath=//tbody//strong[text()='%s']/parent::td/dl/dd[2]";
	public static final String ORDER_SUBTOTAL = "css=tfoot>tr.subtotal span";
	public static final String ORDER_DISCOUNT = "css=tfoot>tr.discount span";
	public static final String ORDER_SHIPPING_AND_HANDLING = "css=tfoot>tr.shipping span";
	public static final String ORDER_GRAND_TOTAL = "css=tfoot>tr.grand_total span";

	public static final String SHIPPING_ADDRESS = "css=.box-order-shipping-address address";
	public static final String SHIPPING_METHOD = "css=.box-order-shipping-method>div";
	public static final String BILLING_ADDRESS = "css=.box-order-billing-address address";
}
