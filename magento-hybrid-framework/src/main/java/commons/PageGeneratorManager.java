package commons;

import org.openqa.selenium.WebDriver;

import pageObjects.AccountInformationPageObject;
import pageObjects.AddressBookPageObject;
import pageObjects.AddressPageObject;
import pageObjects.AdvancedSearchPageObject;
import pageObjects.CheckoutPageObject;
import pageObjects.CompareProductsPageObject;
import pageObjects.CreateNewCustomerAccountPageObject;
import pageObjects.CustomerLoginPageObject;
import pageObjects.ForgotYourPasswordPageObject;
import pageObjects.HomepageObject;
import pageObjects.MyAccountPageObject;
import pageObjects.MyDownloadableProductsPageObject;
import pageObjects.MyOrdersPageObject;
import pageObjects.MyProductReviewsPageObject;
import pageObjects.MyWishListPageObject;
import pageObjects.OrdersAndReturnsPageObject;
import pageObjects.PopularSearchTermsPageObject;
import pageObjects.ProductDetailsPageObject;
import pageObjects.ProductListingPageObject;
import pageObjects.ReviewDetailsPageObject;
import pageObjects.SelectShippingMethodPageObject;
import pageObjects.ShoppingCartPageObject;
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

	public static ForgotYourPasswordPageObject getForgotYourPasswordPage(WebDriver driver) {
		return new ForgotYourPasswordPageObject(driver);
	}

	public static ProductListingPageObject getProductListingPageObject(WebDriver driver) {
		return new ProductListingPageObject(driver);
	}

	public static ShoppingCartPageObject getShoppingCartPageObject(WebDriver driver) {
		return new ShoppingCartPageObject(driver);
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

	public static MyWishListPageObject getMyWishlistPage(WebDriver driver) {
		return new MyWishListPageObject(driver);
	}

	public static AddressBookPageObject getAddressBookPage(WebDriver driver) {
		return new AddressBookPageObject(driver);
	}

	public static AddressPageObject getAddressPage(WebDriver driver) {
		return new AddressPageObject(driver);
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

	public static ReviewDetailsPageObject getReviewDetailsPage(WebDriver driver) {
		return new ReviewDetailsPageObject(driver);
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

	public static CheckoutPageObject getCheckoutPageObject(WebDriver driver) {
		return new CheckoutPageObject(driver);
	}

	public static CompareProductsPageObject getCompareProductsPage(WebDriver driver) {
		return new CompareProductsPageObject(driver);
	}

	public static SelectShippingMethodPageObject getSelectShippingMethodPage(WebDriver driver) {
		return new SelectShippingMethodPageObject(driver);
	}

	public static AddressPageObject getAddressPageObject(WebDriver driver) {
		return new AddressPageObject(driver);
	}

}
