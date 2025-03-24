package pageUIs;

public class ShoppingCartPageUI {
	public static final String DYNAMIC_PRODUCT_LINK_BY_PRODUCT_NAME = "xpath=(//a[contains(text(),'%s')])[2]";
	public static final String DYNAMIC_PRODUCT_SIZE_BY_PRODUCT_NAME = "xpath=(//a[contains(text(),'%s')])[2]/parent::strong/following-sibling::dl//dd[1]";
	public static final String DYNAMIC_PRODUCT_COLOR_BY_PRODUCT_NAME = "xpath=(//a[contains(text(),'%s')])[2]/parent::strong/following-sibling::dl//dd[2]";
	public static final String DYNAMIC_QUANTITY_TEXTBOX_BY_PRODUCT_NAME = "xpath=//a[contains(text(),'%s')]/ancestor::td/following-sibling::td//input[@title='Qty']";
	public static final String DYNAMIC_MOVE_TO_WISH_LIST_LINK_BY_PRODUCT_NAME = "xpath=(//a[contains(text(),'%s')])[2]/ancestor::tr/following-sibling::tr//a[contains(@class,'towishlist')]";
	public static final String DYNAMIC_PEN_ICON_BY_PRODUCT_NAME = "xpath=(//a[contains(text(),'%s')])[2]/ancestor::tr/following-sibling::tr//a[contains(@class,'edit')]";
	public static final String DYNAMIC_TRASHCAN_ICON_BY_PRODUCT_NAME = "xpath=(//a[contains(text(),'%s')])[2]/ancestor::tr/following-sibling::tr//a[contains(@class,'delete')]";
	public static final String TRASHCAN_ICON = "css=.action-delete";

	public static final String APPLY_DISCOUNT_CODE_HEADER = "id=block-discount-heading";
	public static final String ENTER_DISCOUNT_CODE_TEXTBOX = "id=coupon_code";
	public static final String APPLY_DISCOUNT_BUTTON = "css=button[value='Apply Discount']";
	public static final String CANCEL_COUPON_BUTTON = "css=button[value='Cancel Coupon']";
	public static final String UPDATE_SHOPPING_CART_BUTTON = "css=button[title='Update Shopping Cart']";
	public static final String ESTIMATED_SHIPPING_AND_TAX_HEADER = "id=block-shipping-heading";
	public static final String COUNTRY_DROPDOWN = "css=select[name='country_id']";
	public static final String STATE_PROVINCE_DROPDOWN = "css=select[name='region_id']";
	public static final String ZIP_POSTAL_CODE_TEXTBOX = "css=input[name='postcode']";
	public static final String DYNAMIC_SHIPPING_METHOD_RADIO_BUTTON_BY_LABEL = "xpath=//label[text()='%s']/preceding-sibling::input";
	public static final String PROCEED_TO_CHECKOUT_BUTTON = "xpath=(//button[@title='Proceed to Checkout'])[2]";
	public static final String CHECK_OUT_WITH_MULTIPLE_ADDRESSES = "xpath=//span[text()='Check Out with Multiple Addresses']/parent::a";
	public static final String CART_EMPTY_INFO_MESSAGE = "css=.cart-empty";
	public static final String MY_BILLING_AND_SHIPPING_ADDRESS_ARE_THE_SAME_CHECKBOX = "css=input[name='billing-address-same-as-shipping']";
	public static final String PLACE_ORDER_BUTTON = "css=button[title='Place Order']";
	public static final String ORDER_NUMBER = "css=.checkout-success strong";
	public static final String CONTINUE_SHOPPING_BUTTON = "css=a.continue";
	public static final String ORDER_SUBTOTAL = "css=span[data-th='Subtotal']";
	public static final String ORDER_DISCOUNT = "css=td[data-th='Discount']>span>span";
	public static final String ORDER_SHIPPING = "css=span[data-th='Shipping']";
	public static final String ORDER_TOTAL = "css=td[data-th='Order Total']>strong>span";
	public static final String SUMMARY_LOADING_ICON = "css=img[title='Loading...']";
	public static final String HERE_LINK = "css=.cart-empty>p:nth-of-type(2)>a";
}
