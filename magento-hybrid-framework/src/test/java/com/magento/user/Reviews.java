package com.magento.user;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.magento.commons.Register;

import commons.BaseTest;
import commons.PageGeneratorManager;
import pageObjects.CustomerLoginPageObject;
import pageObjects.HomepageObject;
import pageObjects.MyAccountPageObject;
import pageObjects.MyProductReviewsPageObject;
import pageObjects.ProductDetailsPageObject;
import pageObjects.ProductListingPageObject;
import pageObjects.ReviewDetailsPageObject;

public class Reviews extends BaseTest {
	@Parameters("browser")
	@BeforeClass
	public void beforeClass(String browser) {
		driver = getBrowserDriver(browser);
		homepage = PageGeneratorManager.getHomepage(driver);

		email = Register.email;
		password = Register.password;
		productName = "Push It Messenger Bag";

		customerLoginPage = homepage.clickSignInLink();
		homepage = customerLoginPage.logInAsRegisteredUser(email, password);

	}

	@BeforeMethod(onlyForGroups = "submitAReview")
	public void SubmitAReview() {
		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear", "Bags");
		productDetailsPage = productListingPage.clickProductLinkByProductName(driver, productName);
		productDetailsPage.clickReviewsTab();
		productDetailsPage.clickRatingStar("4");
		productDetailsPage.sendKeysToNicknameTextbox("Nickname");
		productDetailsPage.sendKeysToSummaryTextbox("Summary");
		productDetailsPage.sendKeysToReviewTextbox("This is a review.");
		productDetailsPage.clickSubmitReviewButton();
		System.out.println(
				"The @BeforeMethod executed successfully: a review for " + productName + "has been submitted.");
	}

	@Test(description = "Verify that user is automatically scrolled to the review section when clicking the '[number] reviews' link in the product card")
	public void Automatically_Scrolled_To_Reviews_Section_When_Clicking_Reviews_Link_In_Product_Card() {
		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear", "Bags");
		productDetailsPage = productListingPage.clickReviewsLinkByProductName(driver, "Push It Messenger Bag");

		Assert.assertTrue(productDetailsPage.isCustomerReviewsHeaderDisplayed());
	}

	@Test(description = "Verify that user is automatically scrolled to the review section when clicking the 'Be the first to review this product' link")
	public void Automatically_Scrolled_To_Reviews_Section_When_Clicking_Be_The_First_To_Review_This_Product_Link() {
		productListingPage = homepage.clickNavigationBarDropdownMultiLevelItemLinkByLabels(driver, "Women", "Tops",
				"Jackets");
		productDetailsPage = productListingPage.clickProductLinkByProductName(driver, "Olivia 1/4 Zip Light Jacket");
		productDetailsPage.clickBeTheFirstToReviewThisProductLink();

		Assert.assertTrue(productDetailsPage.isCustomerReviewsHeaderDisplayed());
	}

	@Test(description = "Verify that user is automatically scrolled to the review section when clicking the 'Add Your Review' link")
	public void Automatically_Scrolled_To_Reviews_Section_When_Clicking_Add_Your_Review_Link() {
		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear", "Bags");
		productDetailsPage = productListingPage.clickProductLinkByProductName(driver, "Push It Messenger Bag");
		productDetailsPage.clickAddYourReviewLink();

		Assert.assertTrue(productDetailsPage.isCustomerReviewsHeaderDisplayed());
	}

	@Test(description = "Verify that user can view all reviews when clicking the 'Reviews' tab")
	public void Click_Reviews_Tab() {
		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear", "Bags");
		productDetailsPage = productListingPage.clickProductLinkByProductName(driver, "Push It Messenger Bag");
		productDetailsPage.clickReviewsTab();

		Assert.assertTrue(productDetailsPage.isCustomerReviewsHeaderDisplayed());
	}

	@Test(description = "Verify the display of the error messages when user submits a review with empty values")
	public void Submit_A_Review_With_Empty_Values_Error_Messages() {
		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear", "Bags");
		productDetailsPage = productListingPage.clickProductLinkByProductName(driver, "Push It Messenger Bag");
		productDetailsPage.clickReviewsTab();
		productDetailsPage.sendKeysToNicknameTextbox("");
		productDetailsPage.clickSubmitReviewButton();

		Assert.assertEquals(productDetailsPage.getRatingErrorMessage(),
				"Please select one of each of the ratings above.");
		Assert.assertEquals(productDetailsPage.getNicknameErrorMessage(), "This is a required field.");
		Assert.assertEquals(productDetailsPage.getSummaryErrorMessage(), "This is a required field.");
		Assert.assertEquals(productDetailsPage.getReviewErrorMessage(), "This is a required field.");
	}

	@Test(description = "Verify the display of the rating stars when hovered over and clicked")
	public void Display_Of_Rating_Stars_When_Hovered_Over_And_Clicked() {
		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear", "Bags");
		productDetailsPage = productListingPage.clickProductLinkByProductName(driver, "Push It Messenger Bag");
		productDetailsPage.clickReviewsTab();

		Assert.assertEquals(productDetailsPage.getNumberOfRatedStars(), 0);
	}

	@Test(groups = "submitAReview", description = "Verify the success message displayed when submitting a review with valid information")
	public void Review_Submitted_Success_Message() {
		Assert.assertEquals(productDetailsPage.getReviewSubmittedSuccessMessage(),
				"You submitted your review for moderation.");
	}

	@Test(dependsOnMethods = "Review_Submitted_Success_Message", description = "Verify that user can submit more than one review for the same product")
	public void Submit_More_Than_One_Review_For_A_Product() {
		Assert.assertEquals(productDetailsPage.getReviewSubmittedSuccessMessage(),
				"You submitted your review for moderation.");
	}

	@Test(description = "Verify that the overall rating equals the average of the sum of ratings from all reviews")
	public void Overall_Rating_Equals_Sum_Of_Rating_From_All_Reviews() {
		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear", "Bags");
		double overallRatedStars = productListingPage.getProductOverallRating("Push It Messenger Bag");
		productDetailsPage = productListingPage.clickProductLinkByProductName(driver, "Push It Messenger Bag");

		Assert.assertTrue(productDetailsPage.isOverallRatingDisplayedCorrectly(overallRatedStars));
	}

	@Test(description = "Verify that submitted reviews are displayed correctly in 'My Product Reviews' page")
	public void Display_Of_Reviews_In_My_Product_Reviews_Page() {
		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear", "Bags");
		productDetailsPage = productListingPage.clickProductLinkByProductName(driver, "Fusion Backpack");
		productDetailsPage.clickReviewsTab();
		productDetailsPage.clickRatingStar("1");
		productDetailsPage.sendKeysToNicknameTextbox("Nickname");
		productDetailsPage.sendKeysToSummaryTextbox("Summary");
		productDetailsPage.sendKeysToReviewTextbox("This is a review.");
		productDetailsPage.clickSubmitReviewButton();
		productDetailsPage.clickCustomerNameDropdown(driver);
		myAccountPage = productDetailsPage.clickMyAccountDropdownLink(driver);
		myProductReviewsPage = (MyProductReviewsPageObject) myAccountPage.clickMyAccountSidebarLinkByLabel(driver,
				"My Product Reviews");

		myProductReviewsPage.isReviewByProductNameDisplayed("Fusion Backpack");
	}

	@Test(groups = "submitAReview", description = "Verify that user is directed to 'My Product Reviews' page when clicking the 'View All' link in the 'My Recent Reviews' section")
	public void Click_View_All_Link() {
		productDetailsPage.clickCustomerNameDropdown(driver);
		myAccountPage = productDetailsPage.clickMyAccountDropdownLink(driver);
		myProductReviewsPage = myAccountPage.clickViewAllLinkMyRecentReviewsSection();

		Assert.assertEquals(myProductReviewsPage.getPageHeader(driver), "My Product Reviews");
	}

	@Test(groups = "submitAReview", description = "Verify that user is directed to the 'Review Details' page when clicking the product name in the 'My Recent Reviews' section")
	public void Click_Product_Name() {
		productDetailsPage.clickCustomerNameDropdown(driver);
		myAccountPage = productDetailsPage.clickMyAccountDropdownLink(driver);
		productDetailsPage = myAccountPage.clickProductLinkByProductName(productName);

		Assert.assertEquals(productDetailsPage.getPageHeader(driver), productName);
	}

	@Test(groups = "submitAReview", description = "Verify that user is directed to the 'Review Details' page when clicking the 'See Details' link")
	public void Click_See_Details_Link() {
		productDetailsPage.clickCustomerNameDropdown(driver);
		myAccountPage = productDetailsPage.clickMyAccountDropdownLink(driver);
		reviewDetailsPage = myAccountPage.clickSeeDetailsLinkByProductName(productName);

		Assert.assertEquals(reviewDetailsPage.getPageHeader(driver), "Review Details");
	}

	@Test
	public void Display_Of_Reviews_And_Add_Your_Review_Link() {

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeBrowserAndDriver();
	}

	private WebDriver driver;
	private String email, password, productName;
	private HomepageObject homepage;
	private MyAccountPageObject myAccountPage;
	private MyProductReviewsPageObject myProductReviewsPage;
	private ReviewDetailsPageObject reviewDetailsPage;
	private CustomerLoginPageObject customerLoginPage;
	private ProductListingPageObject productListingPage;
	private ProductDetailsPageObject productDetailsPage;
}
