package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.BasePage;
import commons.PageGeneratorManager;
import pageUIs.BasePageUI;
import pageUIs.MyAccountPageUI;

public class MyAccountPageObject extends BasePage {
	private WebDriver driver;

	public MyAccountPageObject(WebDriver driver) {
		this.driver = driver;
	}

	public String getRegisterSuccessfulMessage() {
		waitForElementVisible(driver, BasePageUI.MainContent.MESSAGE);
		return getElementText(driver, BasePageUI.MainContent.MESSAGE);
	}

	public String getAccountInformationSavedSuccessMessage() {
		waitForElementVisible(driver, BasePageUI.MainContent.MESSAGE);
		return getElementText(driver, BasePageUI.MainContent.MESSAGE);
	}

	public String getFullName() {
		waitForElementVisible(driver, MyAccountPageUI.AccountInformation.FULL_NAME_AND_EMAIL);
		String fullNameAndEmail = getElementText(driver, MyAccountPageUI.AccountInformation.FULL_NAME_AND_EMAIL);
		return fullNameAndEmail.split("\n")[0];
	}

	public String getEmail() {
		waitForElementVisible(driver, MyAccountPageUI.AccountInformation.FULL_NAME_AND_EMAIL);
		String fullNameAndEmail = getElementText(driver, MyAccountPageUI.AccountInformation.FULL_NAME_AND_EMAIL);
		return fullNameAndEmail.split("\n")[1];
	}

	public AccountInformationPageObject clickEditContactInformationLink() {
		waitForElementClickable(driver, MyAccountPageUI.AccountInformation.EDIT_CONTACT_INFORMARION_LINK);
		clickElementByJS(driver, MyAccountPageUI.AccountInformation.EDIT_CONTACT_INFORMARION_LINK);
		return PageGeneratorManager.getAccountInformationPage(driver);
	}

	public AddressBookPageObject clickManageAddressesLink() {
		waitForElementClickable(driver, MyAccountPageUI.AddressBook.MANAGE_ADDRESSES_LINK);
		clickElement(driver, MyAccountPageUI.AddressBook.MANAGE_ADDRESSES_LINK);
		return PageGeneratorManager.getAddressBookPage(driver);
	}

	public MyProductReviewsPageObject clickViewAllLinkMyRecentReviewsSection() {
		waitForElementClickable(driver, MyAccountPageUI.MyRecentReviews.VIEW_ALL_LINK_MY_RECENT_REVIEWS_SECTION);
		clickElementByJS(driver, MyAccountPageUI.MyRecentReviews.VIEW_ALL_LINK_MY_RECENT_REVIEWS_SECTION);
		return PageGeneratorManager.getMyProductReviewsPage(driver);
	}

	public ProductDetailsPageObject clickProductLinkByProductName(String productName) {
		waitForElementClickable(driver, MyAccountPageUI.MyRecentReviews.DYNAMIC_PRODUCT_LINK_BY_PRODUCT_NAME,
				productName);
		clickElementByJS(driver, MyAccountPageUI.MyRecentReviews.DYNAMIC_PRODUCT_LINK_BY_PRODUCT_NAME, productName);
		return PageGeneratorManager.getProductDetailsPageObject(driver);
	}

	public ReviewDetailsPageObject clickSeeDetailsLinkByProductName(String productName) {
		waitForElementClickable(driver, MyAccountPageUI.MyRecentReviews.DYNAMIC_SEE_DETAILS_LINK_BY_PRODUCT_NAME,
				productName);
		clickElementByJS(driver, MyAccountPageUI.MyRecentReviews.DYNAMIC_SEE_DETAILS_LINK_BY_PRODUCT_NAME, productName);
		return PageGeneratorManager.getReviewDetailsPage(driver);
	}

	public AddressPageObject clickEditAddressLinkDefaultBillingAddressSection() {
		waitForElementClickable(driver, MyAccountPageUI.AddressBook.EDIT_ADDRESS_LINK_DEFAULT_BILLING_ADDRESS_SECTION);
		clickElementByJS(driver, MyAccountPageUI.AddressBook.EDIT_ADDRESS_LINK_DEFAULT_BILLING_ADDRESS_SECTION);
		return PageGeneratorManager.getAddressPage(driver);
	}

	public AddressPageObject clickEditAddressLinkDefaultShippingAddressSection() {
		waitForElementClickable(driver, MyAccountPageUI.AddressBook.EDIT_ADDRESS_LINK_DEFAULT_SHIPPING_ADDRESS_SECTION);
		clickElementByJS(driver, MyAccountPageUI.AddressBook.EDIT_ADDRESS_LINK_DEFAULT_SHIPPING_ADDRESS_SECTION);
		return PageGeneratorManager.getAddressPage(driver);
	}

}
