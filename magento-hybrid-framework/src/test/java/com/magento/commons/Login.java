package com.magento.commons;

import java.util.Set;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import commons.BaseTest;
import commons.PageGeneratorManager;
import pageObjects.CustomerLoginPageObject;
import pageObjects.HomepageObject;

public class Login extends BaseTest {

//	public Access(WebDriver driver) {
//		this.driver = driver;
//		basePage = BasePage.getBasePageObject();
//	}

	@Parameters("browser")
	@BeforeTest
	public void logInWithCookies(String browser) {
		driver = getBrowserDriver(browser);
		homepage = PageGeneratorManager.getHomepage(driver);

		email = Register.email;
		password = Register.password;

		customerLoginPage = homepage.clickSignInLink();
		customerLoginPage.logInAsRegisteredUser(email, password);
		cookies = homepage.getCookies(driver);
	}

	@AfterTest
	public void afterTest() {
		driver.quit();
	}

	private WebDriver driver;
	private String email, password;
	private HomepageObject homepage;
	private CustomerLoginPageObject customerLoginPage;
	public static Set<Cookie> cookies;

}
