package commons;

import org.openqa.selenium.WebDriver;

import pageObjects.AccountInformationPageObject;
import pageObjects.AddressBookPageObject;
import pageObjects.AdvancedSearchPageObject;
import pageObjects.CreateNewCustomerAccountPageObject;
import pageObjects.CustomerLoginPageObject;
import pageObjects.HomepageObject;
import pageObjects.MyAccountPageObject;
import pageObjects.MyDownloadableProductsPageObject;
import pageObjects.MyOrdersPageObject;
import pageObjects.MyProductReviewsPageObject;
import pageObjects.MyWishlistPageObject;
import pageObjects.OrdersAndReturnsPageObject;
import pageObjects.PopularSearchTermsPageObject;
import pageObjects.ProductDetailsPageObject;
import pageObjects.ProductListingPageObject;
import pageObjects.StoredPaymentMethodsPageObject;

public class PageGeneratorManager {

	public static HomepageObject getHomepage(WebDriver driver) {
		return new HomepageObject(driver);
	}

	public static CreateNewCustomerAccountPageObject getCreateNewCustomerAccountPage(WebDriver driver) {
		return new CreateNewCustomerAccountPageObject(driver);
	}

	public static CustomerLoginPageObject getCustomerLoginPage(WebDriver driver) {
		return new CustomerLoginPageObject(driver);
	}

	public static ProductListingPageObject getProductListingPageObject(WebDriver driver) {
		return new ProductListingPageObject(driver);
	}

	public static ProductDetailsPageObject getProductDetailsPageObject(WebDriver driver) {
		return new ProductDetailsPageObject(driver);
	}

	public static MyAccountPageObject getMyAccountPage(WebDriver driver) {
		return new MyAccountPageObject(driver);
	}

	public static MyOrdersPageObject getMyOrdersPage(WebDriver driver) {
		return new MyOrdersPageObject(driver);
	}

	public static MyDownloadableProductsPageObject getMyDownloadableProductsPage(WebDriver driver) {
		return new MyDownloadableProductsPageObject(driver);
	}

	public static MyWishlistPageObject getMyWishlistPage(WebDriver driver) {
		return new MyWishlistPageObject(driver);
	}

	public static AddressBookPageObject getAddressBookPage(WebDriver driver) {
		return new AddressBookPageObject(driver);
	}

	public static AccountInformationPageObject getAccountInformationPage(WebDriver driver) {
		return new AccountInformationPageObject(driver);
	}

	public static StoredPaymentMethodsPageObject getStoredPaymentMethodsPage(WebDriver driver) {
		return new StoredPaymentMethodsPageObject(driver);
	}

	public static MyProductReviewsPageObject getMyProductReviewsPage(WebDriver driver) {
		return new MyProductReviewsPageObject(driver);
	}

	public static PopularSearchTermsPageObject getPopularSearchTermsPage(WebDriver driver) {
		return new PopularSearchTermsPageObject(driver);
	}

	public static AdvancedSearchPageObject getAdvancedSearchPage(WebDriver driver) {
		return new AdvancedSearchPageObject(driver);
	}

	public static OrdersAndReturnsPageObject getOrdersAndReturnsPage(WebDriver driver) {
		return new OrdersAndReturnsPageObject(driver);
	}

}
