package commons;

import org.openqa.selenium.WebDriver;

import pageObjects.CreateNewCustomerAccountPageObject;
import pageObjects.CustomerLoginPageObject;
import pageObjects.HomepageObject;
import pageObjects.MyAccountPageObject;

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

	public static MyAccountPageObject getMyAccountPage(WebDriver driver) {
		return new MyAccountPageObject(driver);
	}
}
