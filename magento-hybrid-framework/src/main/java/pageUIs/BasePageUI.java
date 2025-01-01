package pageUIs;

public class BasePageUI {
	public static final String UPLOAD_FILE = "";
	public static final String CONFIRMATION_POPUP_OK_BUTTON = "css=.modal-footer button.action-accept";

	public static final String LUMA_LOGO = "css=.logo";
	public static final String WELCOME_MESSAGE = "css=.logged-in";
	public static final String SEARCH_BAR = "id=search";
	public static final String SEARCH_SUGGESTION = "css=#search_autocomplete li";
	public static final String SEARCH_SUGGESTION_TEXT = "css=#search_autocomplete li>span:nth-of-type(1)";
	public static final String SEARCH_SUGGESTION_NUMBER = "css=#search_autocomplete li>span:nth-of-type(2)";
	public static final String DYNAMIC_NAVIGATION_BAR_DROPDOWN_BY_LABEL = "xpath=//nav//span[text()=\"%s\"]/parent::a";
	public static final String DYNAMIC_NAVIGATION_BAR_DROPDOWN_SINGLE_LEVEL_ITEM_BY_LABEL = "xpath=//span[text()='%s']/parent::a";
	public static final String DYNAMIC_NAVIGATION_BAR_DROPDOWN_MULTI_LEVEL_ITEM_BY_LABEL = "xpath=//span[text()='%s']/parent::a[contains(@href,'/%s')]";

	public static final String ACCOUNT_NAME_DROPDOWN = "css=.customer-name";
	public static final String MY_ACCOUNT_DROPDOWN_LINK = "css=div[class=customer-menu][aria-hidden=false] li:nth-of-type(1)>a";
	public static final String MY_WISH_LIST_DROPDOWN_LINK = "css=div[class=customer-menu][aria-hidden=false] li:nth-of-type(2)>a";
	public static final String SIGN_OUT_DROPDOWN_LINK = "css=div[class=customer-menu][aria-hidden=false] li:nth-of-type(3)>a";

	public static final String SHOPPING_CART_ICON = "css=.showcart";
	public static final String MINI_CART_MODAL = "css=.block-minicart";
	public static final String SHOPPING_CART_QUANTITY = "xpath=//span[text()='My Cart']/following-sibling::span[@class='counter qty']";
	public static final String MINI_CART_QUANTITY = "css=.items-total>span.count";
	public static final String MINI_CART_EMPTY_SHOPPING_CART_INFO_MESSAGE = "css=.block-content .empty";
	public static final String NO_ITEMS_IN_CART_INFO_MESSAGE = "css=#minicart-content-wrapper strong.empty";
	public static final String MINI_CART_CROSS_ICON = "id=btn-minicart-close";
	public static final String DYNAMIC_SEE_DETAILS_COLLAPSIBLE_HEADER_BY_PRODUCT_NAME = "xpath=//ol[@id='mini-cart']//li[contains(@class,'product')]//strong[@class='product-item-name']/a[contains(text(),'%s')]/parent::strong/following-sibling::div[contains(@class,'product options')]/span";
	public static final String DYNAMIC_SIZE_VALUE_BY_PRODUCT_NAME = "xpath=//ol[@id='mini-cart']//li[contains(@class,'product')]//strong[@class='product-item-name']/a[contains(text(),'%s')]/parent::strong/following-sibling::div[contains(@class,'product options')]/div//dd[1]/span";
	public static final String DYNAMIC_COLOR_VALUE_BY_PRODUCT_NAME = "xpath=//ol[@id='mini-cart']//li[contains(@class,'product')]//strong[@class='product-item-name']/a[contains(text(),'%s')]/parent::strong/following-sibling::div[contains(@class,'product options')]/div//dd[2]/span";
	public static final String DYNAMIC_QUANTITY_TEXTBOX_BY_PRODUCT_NAME = "xpath=//ol[@id='mini-cart']//li[contains(@class,'product')]//a[contains(text(),'%s')]/parent::strong/following-sibling::div[contains(@class,'pricing')]/div[contains(@class,'qty')]//input";
	public static final String TRASHCAN_ICON = "css=#mini-cart .delete";
	public static final String CART_SUBTOTAL = "css=span[data-bind='html: cart().subtotal_excl_tax']>span";
	public static final String PROCEED_TO_CHECKOUT_BUTTON = "id=top-cart-btn-checkout";
	public static final String VIEW_AND_EDIT_CART_LINK = "css=.viewcart";

	public static final String PAGE_HEADER = "css=h1>span";
	public static final String MESSAGE = "css=div[data-bind='html: $parent.prepareMessageForHtml(message.text)']";

	public static final String DYNAMIC_RECENTLY_ORDER_PRODUCT_CHECKBOX_BY_LABEL = "xpath=//span[text()='Breathe-Easy Tank']/ancestor::strong/preceding-sibling::div//input";
	public static final String DYNAMIC_CROSS_ICON_BY_PRODUCT_NAME = "xpath=//a[text()='%s']/parent::strong/following-sibling::a[@class='action delete']";
	public static final String REMOVE_ITEM_COMPARISON_LIST_POPUP_OK_BUTTON = "css=.action-accept";
	public static final String COMPARE_BUTTON = "css=div.block-compare a.compare";
	public static final String CLEAR_ALL_LINK = "id=compare-clear-all";
	public static final String RECENTLY_ORDERED_ADD_TO_CART_BUTTON = "css=.block-reorder button[title='Add to Cart']";
	public static final String RECENTLY_ORDERED_VIEW_ALL_LINK = "css=.block-reorder .view";
	public static final String EMPTY_WISH_LIST_INFO_MESSAGE = "css=.block-wishlist .empty";
	public static final String GO_TO_WISH_LIST_LINK = "css=a[title='Go to Wish List']";

	public static final String DYNAMIC_FOOTER_LINK = "xpath=//footer//a[contains(text(),'%s')]";

}
