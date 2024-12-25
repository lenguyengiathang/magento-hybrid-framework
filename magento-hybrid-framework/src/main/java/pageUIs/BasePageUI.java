package pageUIs;

public class BasePageUI {
	public static final String UPLOAD_FILE = "";

	public static final String LUMA_LOGO = "css=.logo";
	public static final String WELCOME_MESSAGE = "css=.logged-in";
	public static final String SEARCH_BAR = "id=search";
	public static final String SEARCH_SUGGESTION = "css=#search_autocomplete li";
	public static final String SEARCH_SUGGESTION_TEXT = "css=#search_autocomplete li>span:nth-of-type(1)";
	public static final String SEARCH_SUGGESTION_NUMBER = "css=#search_autocomplete li>span:nth-of-type(2)";
	public static final String DYNAMIC_NAVIGATION_BAR_DROPDOWN_BY_LABEL = "xpath=//nav//span[text()=\"%s\"]/parent::a";
	public static final String DYNAMIC_NAVIGATION_BAR_DROPDOWN_FIRST_LEVEL_ITEM_BY_LABEL = "xpath=//nav//span[text()=\"%s\"]/parent::a/following-sibling::ul//span[text()='%s']/parent::a";
	public static final String DYNAMIC_NAVIGATION_BAR_DROPDOWN_SECOND_LEVEL_ITEM_BY_LABEL = "xpath=//nav//span[text()=\"%s\"]/parent::a/following-sibling::ul//span[text()='%s']/parent::a/following-sibling::ul//span[text()='%s']/parent::a";
	public static final String SHOPPING_CART_ICON = "css=.minicart-wrapper";
	public static final String SHOPPING_CART_QUANTITY = "xpath=//span[text()='My Cart']/following-sibling::span[@class='counter qty']";
	public static final String PAGE_HEADER = "css=h1>span";
	public static final String MESSAGE = "css=div[data-bind='html: $parent.prepareMessageForHtml(message.text)']";

	public static final String DYNAMIC_FOOTER_LINK = "xpath=//footer//a[contains(text(),'%s')]";

}
