package pageUIs;

public class BasePageUI {
	public static class General {
		public static final String UPLOAD_FILE = "";
		public static final String CONFIRMATION_POPUP_OK_BUTTON = "css=.modal-footer button.action-accept";
		public static final String LOADING_ICON = "css=.loader";
	}

	public static class Header {
		public static final String LUMA_LOGO = "css=.logo";
		public static final String WELCOME_MESSAGE = "css=.logged-in";
		public static final String ACCOUNT_NAME_DROPDOWN = "css=.customer-name";
		public static final String MY_ACCOUNT_DROPDOWN_LINK = "css=div[class=customer-menu][aria-hidden=false] li:nth-of-type(1)>a";
		public static final String MY_WISH_LIST_DROPDOWN_LINK = "css=div[class=customer-menu][aria-hidden=false] li:nth-of-type(2)>a";
		public static final String SIGN_OUT_DROPDOWN_LINK = "css=div[class=customer-menu][aria-hidden=false] li:nth-of-type(3)>a";
		public static final String COMPARE_PRODUCTS_LINK = "css=a[title='Compare Products']";
		public static final String SEARCH_BAR = "id=search";
		public static final String SEARCH_SUGGESTION = "css=#search_autocomplete li";
		public static final String SEARCH_SUGGESTION_TEXT = "css=#search_autocomplete li>span:nth-of-type(1)";
		public static final String SEARCH_SUGGESTION_NUMBER = "css=#search_autocomplete li>span:nth-of-type(2)";
		public static final String DYNAMIC_NAVIGATION_BAR_DROPDOWN_BY_LABEL = "xpath=//nav//span[text()='%s']/parent::a";
		public static final String DYNAMIC_NAVIGATION_BAR_DROPDOWN_SINGLE_LEVEL_ITEM_BY_LABEL = "xpath=//span[text()='%s']/parent::a";
		public static final String DYNAMIC_NAVIGATION_BAR_DROPDOWN_MULTI_LEVEL_ITEM_BY_LABEL = "xpath=//span[text()='%s']/parent::a[contains(@href,'/%s')]";

		public static class MiniCart {
			public static final String SHOPPING_CART_ICON = "css=.showcart";
			public static final String SHOPPING_CART_QUANTITY = "css=.qty>.counter-number";
			public static final String MINI_CART_MODAL = "css=.block-minicart";
			public static final String MINI_CART_QUANTITY = "css=.items-total>span.count";
			public static final String MINI_CART_EMPTY_SHOPPING_CART_INFO_MESSAGE = "css=.block-content .empty";
			public static final String NO_ITEMS_IN_CART_INFO_MESSAGE = "css=#minicart-content-wrapper strong.empty";
			public static final String DYNAMIC_SEE_DETAILS_COLLAPSIBLE_HEADER_BY_PRODUCT_NAME = "xpath=//ol[@id='mini-cart']//li[contains(@class,'product')]//strong[@class='product-item-name']/a[contains(text(),'%s')]/parent::strong/following-sibling::div[contains(@class,'product options')]/span";
			public static final String DYNAMIC_PRODUCT_LINK_BY_NAME = "xpath=//ol[@id='mini-cart']//li[contains(@class,'product')]//strong[@class='product-item-name']/a[contains(text(),'%s')]";
			public static final String DYNAMIC_SIZE_VALUE_BY_PRODUCT_NAME = "xpath=//ol[@id='mini-cart']//li[contains(@class,'product')]//strong[@class='product-item-name']/a[contains(text(),'%s')]/parent::strong/following-sibling::div[contains(@class,'product options')]/div//dd[1]/span";
			public static final String DYNAMIC_COLOR_VALUE_BY_PRODUCT_NAME = "xpath=//ol[@id='mini-cart']//li[contains(@class,'product')]//strong[@class='product-item-name']/a[contains(text(),'%s')]/parent::strong/following-sibling::div[contains(@class,'product options')]/div//dd[2]/span";
			public static final String DYNAMIC_QUANTITY_TEXTBOX_BY_PRODUCT_NAME = "xpath=//ol[@id='mini-cart']//li[contains(@class,'product')]//a[contains(text(),'%s')]/parent::strong/following-sibling::div[contains(@class,'pricing')]/div[contains(@class,'qty')]//input";
			public static final String UPDATE_BUTTON = "css=.update-cart-item";
			public static final String DYNAMIC_PEN_ICON_BY_PRODUCT_NAME = "xpath=//ol[@id='mini-cart']//li[contains(@class,'product')]//a[contains(text(),'%s')]/parent::strong/following-sibling::div[contains(@class,'actions')]//a[@class='action edit']";
			public static final String DYNAMIC_TRASHCAN_ICON_BY_PRODUCT_NAME = "xpath=//ol[@id='mini-cart']//li[contains(@class,'product')]//a[contains(text(),'%s')]/parent::strong/following-sibling::div[contains(@class,'actions')]//a[@class='action delete']";
			public static final String CART_SUBTOTAL = "css=span[data-bind='html: cart().subtotal_excl_tax']>span";
			public static final String PROCEED_TO_CHECKOUT_BUTTON = "id=top-cart-btn-checkout";
			public static final String VIEW_AND_EDIT_CART_LINK = "css=.viewcart";
			public static final String MINI_CART_CROSS_ICON = "id=btn-minicart-close";
		}
	}

	public static class MainContent {
		public static final String PAGE_HEADER = "css=h1>span";
		public static final String MESSAGE = "css=div[data-bind='html: $parent.prepareMessageForHtml(message.text)']";
		public static final String MAIN_CONTENT = "id=maincontent";
	}

	public static class ProductCard {
		public static final String DYNAMIC_PRODUCT_LINK_BY_PRODUCT_NAME = "xpath=(//a[contains(text(),'%s')])[1]";
		public static final String DYNAMIC_RATING_BY_PRODUCT_NAME = "xpath=//a[contains(text(),'%s')]/parent::strong/following-sibling::div//div[@class='rating-result']";
		public static final String DYNAMIC_SIZE_BUTTON_BY_PRODUCT_NAME_AND_LABEL = "xpath=//li[contains(@class,'product-item')]//a[contains(text(),'%s')]/parent::strong/following-sibling::div/div[contains(@class,'size')]/div/div[text()='%s']";
		public static final String DYNAMIC_COLOR_BUTTON_BY_PRODUCT_NAME_AND_LABEL = "xpath=//li[contains(@class,'product-item')]//a[contains(text(),'%s')]/parent::strong/following-sibling::div/div[contains(@class,'color')]/div/div[@option-label='%s']";
		public static final String DYNAMIC_REVIEWS_LINK_BY_PRODUCT_NAME = "xpath=//li[contains(@class,'product-item')]//a[contains(text(),'%s')]/parent::strong/following-sibling::div[contains(@class,'reviews')]//a";
		public static final String DYNAMIC_ADD_TO_CART_BUTTON_BY_PRODUCT_NAME = "xpath=//li[contains(@class,'product-item')]//a[contains(text(),'%s')]/parent::strong/following-sibling::div[@class='product-item-inner']//button";
		public static final String DYNAMIC_WISH_LIST_ICON_BY_PRODUCT_NAME = "xpath=//a[contains(text(),'%s')]/parent::strong/following-sibling::div[last()]//a[@class='action towishlist']";
		public static final String DYNAMIC_COMPARE_ICON_BY_PRODUCT_NAME = "xpath=//a[contains(text(),'%s')]/parent::strong/following-sibling::div[last()]//a[@class='action tocompare']";
	}

	public static class CompareProductsSection {
		public static final String EMPTY_COMPARISON_LIST_INFO_MESSAGE = "css=.block-compare>.empty";
		public static final String DYNAMIC_CROSS_ICON_BY_PRODUCT_NAME = "xpath=//a[text()='%s']/parent::strong/following-sibling::a[@class='action delete']";
		public static final String COMPARE_BUTTON = "css=div.block-compare a.compare";
		public static final String CLEAR_ALL_LINK = "id=compare-clear-all";
	}

	public static class RecentlyOrderedSection {
		public static final String DYNAMIC_CHECKBOX_BY_PRODUCT_NAME = "xpath=//span[text()='%s']/ancestor::strong/preceding-sibling::div//input";
		public static final String ADD_TO_CART_BUTTON = "css=.block-reorder button[title='Add to Cart']";
		public static final String VIEW_ALL_LINK = "css=.block-reorder .view";
	}

	public static class MyWishListSection {
		public static final String EMPTY_WISH_LIST_INFO_MESSAGE = "css=.block-wishlist .empty";
		public static final String DYNAMIC_PRODUCT_LINK_BY_PRODUCT_NAME = "xpath=//div[contains(@class,'block-wishlist')]//span[text()='%s']";
		public static final String DYNAMIC_ADD_TO_CART_BUTTON_BY_PRODUCT_NAME = "xpath=//div[contains(@class,'block-wishlist')]//span[text()='%s']/ancestor::strong/following-sibling::div//button";
		public static final String DYNAMIC_CROSS_ICON_BY_PRODUCT_NAME = "xpath=//div[contains(@class,'block-wishlist')]//span[text()='%s']/ancestor::strong/following-sibling::div//a";
		public static final String GO_TO_WISH_LIST_LINK = "css=a[title='Go to Wish List']";
	}

	public static class Footer {
		public static final String DYNAMIC_FOOTER_LINK = "xpath=//footer//a[contains(text(),'%s')]";
	}

}
