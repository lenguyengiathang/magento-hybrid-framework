package commons;

import org.openqa.selenium.WebDriver;

import pageObjects.AdvancedSearchPageObject;
import pageObjects.CreateNewCustomerAccountPageObject;
import pageObjects.CustomerLoginPageObject;
import pageObjects.HomepageObject;
import pageObjects.MyAccountPageObject;
import pageObjects.OrdersAndReturnsPageObject;
import pageObjects.PopularSearchTermsPageObject;
import pageObjects.ProductListingPageObject;

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

	public static MyAccountPageObject getMyAccountPage(WebDriver driver) {
		return new MyAccountPageObject(driver);
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
