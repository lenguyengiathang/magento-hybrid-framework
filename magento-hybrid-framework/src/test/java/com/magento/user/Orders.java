package com.magento.user;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import com.magento.commons.Register;

import commons.BasePage;
import commons.BaseTest;
import commons.PageGeneratorManager;
import pageObjects.AccountInformationPageObject;
import pageObjects.CustomerLoginPageObject;
import pageObjects.HomepageObject;
import pageObjects.MyAccountPageObject;
import utilities.DataHelper;

public class Orders extends BaseTest {
	@Parameters("browser")
	@BeforeClass
	public void beforeClass(String browser) {
		driver = getBrowserDriver(browser);
		basePage = BasePage.getBasePageObject();
		homepage = PageGeneratorManager.getHomepage(driver);

		data = DataHelper.getDataHelper();
		email = Register.email;
		password = Register.password;
		newFirstName = data.getFirstName();
		newLastName = data.getLastName();
		newFullName = newFirstName + ' ' + newLastName;

		customerLoginPage = homepage.clickSignInLink();
		myAccountPage = customerLoginPage.logInAsRegisteredUser(email, password);
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeBrowserAndDriver();
	}

	private WebDriver driver;
	private DataHelper data;
	private String email, password, newFirstName, newLastName, newFullName;
	private BasePage basePage;
	private HomepageObject homepage;
	private CustomerLoginPageObject customerLoginPage;
	private MyAccountPageObject myAccountPage;
	private AccountInformationPageObject accountInformationPage;
}
