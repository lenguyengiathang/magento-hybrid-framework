package com.magento.user;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.magento.commons.Register;

import commons.BaseTest;
import commons.PageGeneratorManager;
import pageObjects.CreateNewCustomerAccountPageObject;
import pageObjects.CustomerLoginPageObject;
import pageObjects.HomepageObject;
import pageObjects.MyAccountPageObject;
import pageObjects.MyProductReviewsPageObject;
import pageObjects.ProductDetailsPageObject;
import pageObjects.ProductListingPageObject;
import pageObjects.ReviewDetailsPageObject;
import utilities.FakeDataUtils;
import utilities.JsonUtils;

public class Reviews extends BaseTest {
	@Parameters("browser")
	@BeforeClass
	public void beforeClass(String browser) {
		driver = getBrowserDriver(browser);
		homepage = PageGeneratorManager.getHomepage(driver);

		data = FakeDataUtils.getDataHelper();
		email = Register.email;
		password = Register.password;
		todaysDateTextual = getTodaysDateTextual(getTodaysDateNumeric());

		customerLoginPage = homepage.clickSignInLink();
		homepage = customerLoginPage.logInAsRegisteredUser(email, password);

	}

	@BeforeMethod(onlyForGroups = "submitAReview")
	public void submitAReview() {
		resultNode = JsonUtils.getRandomProductNoOptions("gear_products.json");
		category = resultNode.get("category").asText();
		productName = resultNode.get("product").get("product_name").asText();

		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear", category);
		productDetailsPage = productListingPage.clickProductLinkByProductName(driver, productName);
		productDetailsPage.clickReviewsTab();
		productDetailsPage.clickRatingStar("4");
		productDetailsPage.sendKeysToNicknameTextbox("Nickname");
		productDetailsPage.sendKeysToSummaryTextbox("Summary");
		productDetailsPage.sendKeysToReviewTextbox("This is a review for " + productName);
		productDetailsPage.clickSubmitReviewButton();
		homepage = productDetailsPage.clickLumaLogo(driver);
	}

	@Test(description = "Verify that user is automatically scrolled to the review section when clicking the '[number] reviews' link in the product card")
	public void Reviews_01_Automatically_Scrolled_To_Reviews_Section_When_Clicking_Reviews_Link_In_Product_Card() {
		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear", "Bags");
		productDetailsPage = productListingPage.clickReviewsLinkByProductName(driver, "Push It Messenger Bag");

		Assert.assertTrue(productDetailsPage.isCustomerReviewsHeaderDisplayed());
	}

	@Test(description = "Verify that user is automatically scrolled to the review section when clicking the 'Be the first to review this product' link")
	public void Reviews_02_Automatically_Scrolled_To_Reviews_Section_When_Clicking_Be_The_First_To_Review_This_Product_Link() {
		productListingPage = homepage.clickNavigationBarDropdownMultiLevelItemLinkByLabels(driver, "Women", "Tops",
				"Jackets");
		productDetailsPage = productListingPage.clickProductLinkByProductName(driver, "Olivia 1/4 Zip Light Jacket");
		productDetailsPage.clickBeTheFirstToReviewThisProductLink();

		Assert.assertTrue(productDetailsPage.isCustomerReviewsHeaderDisplayed());
	}

	@Test(description = "Verify that user is automatically scrolled to the review section when clicking the 'Add Your Review' link")
	public void Reviews_03_Automatically_Scrolled_To_Reviews_Section_When_Clicking_Add_Your_Review_Link() {
		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear", "Bags");
		productDetailsPage = productListingPage.clickProductLinkByProductName(driver, productName);
		productDetailsPage.clickAddYourReviewLink();

		Assert.assertTrue(productDetailsPage.isCustomerReviewsHeaderDisplayed());
	}

	@Test(description = "Verify that user can view all reviews when clicking the 'Reviews' tab")
	public void Reviews_04_Click_Reviews_Tab() {
		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear", "Bags");
		productDetailsPage = productListingPage.clickProductLinkByProductName(driver, productName);
		productDetailsPage.clickReviewsTab();

		Assert.assertTrue(productDetailsPage.isCustomerReviewsHeaderDisplayed());
	}

	@Test(description = "Verify the display of the error messages when user submits a review with empty values")
	public void Reviews_05_Submit_A_Review_With_Empty_Values_Error_Messages() {
		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear", "Bags");
		productDetailsPage = productListingPage.clickProductLinkByProductName(driver, productName);
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
	public void Reviews_06_Display_Of_Rating_Stars_When_Hovered_Over_And_Clicked() {
		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear", "Bags");
		productDetailsPage = productListingPage.clickProductLinkByProductName(driver, "Push It Messenger Bag");
		productDetailsPage.clickReviewsTab();

		Assert.assertEquals(productDetailsPage.getNumberOfRatedStars(), 0);

	}

	@Test(description = "Verify the success message displayed when submitting a review with valid information")
	public void Reviews_07_Review_Submitted_Success_Message() {
		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear", "Bags");
		productDetailsPage = productListingPage.clickProductLinkByProductName(driver, productName);
		productDetailsPage.clickReviewsTab();
		productDetailsPage.clickRatingStar("1");
		productDetailsPage.sendKeysToNicknameTextbox("Nickname");
		productDetailsPage.sendKeysToSummaryTextbox("Summary");
		productDetailsPage.sendKeysToReviewTextbox("This is a review for " + productName);
		productDetailsPage.clickSubmitReviewButton();

		Assert.assertEquals(productDetailsPage.getReviewSubmittedSuccessMessage(),
				"You submitted your review for moderation.");
	}

	@Test(groups = "submitAReview", description = "Verify that user can submit more than one review for the same product")
	public void Reviews_08_Submit_More_Than_One_Review_For_A_Product() {
		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear", category);
		productDetailsPage = productListingPage.clickProductLinkByProductName(driver, productName);
		productDetailsPage.clickReviewsTab();
		productDetailsPage.clickRatingStar("5");
		productDetailsPage.sendKeysToNicknameTextbox("Nickname");
		productDetailsPage.sendKeysToSummaryTextbox("Summary");
		productDetailsPage.sendKeysToReviewTextbox("This is another review for " + productName);
		productDetailsPage.clickSubmitReviewButton();

		Assert.assertEquals(productDetailsPage.getReviewSubmittedSuccessMessage(),
				"You submitted your review for moderation.");
	}

	@Test(description = "Verify that the overall rating equals the average of the sum of ratings from all reviews")
	public void Reviews_09_Overall_Rating_Equals_Sum_Of_Rating_From_All_Reviews() {
		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear", "Bags");
		double overallRatedStars = productListingPage.getProductOverallRating(productName);
		productDetailsPage = productListingPage.clickProductLinkByProductName(driver, productName);

		Assert.assertTrue(productDetailsPage.isOverallRatingDisplayedCorrectly(overallRatedStars));

	}

	@Test(description = "Verify that submitted reviews are displayed correctly in 'My Product Reviews' page")
	public void Reviews_10_Display_Of_Reviews_In_My_Product_Reviews_Page() {
		productListingPage = homepage.clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear", "Bags");
		productDetailsPage = productListingPage.clickProductLinkByProductName(driver, productName);
		productDetailsPage.clickReviewsTab();
		productDetailsPage.clickRatingStar("3");
		productDetailsPage.sendKeysToNicknameTextbox("Nickname");
		productDetailsPage.sendKeysToSummaryTextbox("Summary");
		productDetailsPage.sendKeysToReviewTextbox("This is a review for " + productName);
		productDetailsPage.clickSubmitReviewButton();
		productDetailsPage.clickCustomerNameDropdown(driver);
		myAccountPage = productDetailsPage.clickMyAccountDropdownLink(driver);
		myProductReviewsPage = (MyProductReviewsPageObject) myAccountPage.clickMyAccountSidebarLinkByLabel(driver,
				"My Product Reviews");

		myProductReviewsPage.isReviewByProductNameDisplayed(productName);
	}

	@Test(groups = "submitAReview", description = "Verify that user is directed to 'My Product Reviews' page when clicking the 'View All' link in the 'My Recent Reviews' section")
	public void Reviews_11_Click_View_All_Link() {
		homepage.clickCustomerNameDropdown(driver);
		myAccountPage = productDetailsPage.clickMyAccountDropdownLink(driver);
		myProductReviewsPage = myAccountPage.clickViewAllLinkMyRecentReviewsSection();

		Assert.assertEquals(myProductReviewsPage.getPageHeader(driver), "My Product Reviews");
	}

	@Test(groups = "submitAReview", description = "Verify that user is directed to the 'Review Details' page when clicking the product name in the 'My Recent Reviews' section")
	public void Reviews_12_Click_Product_Name_My_Recent_Reviews_Section() {
		productDetailsPage.clickCustomerNameDropdown(driver);
		myAccountPage = productDetailsPage.clickMyAccountDropdownLink(driver);
		productDetailsPage = myAccountPage.clickProductLinkByProductName(productName);

		Assert.assertEquals(productDetailsPage.getPageHeader(driver), productName);
	}

	@Test(groups = "submitAReview", description = "Verify that user is directed to the 'Review Details' page when clicking the 'See Details' link")
	public void Reviews_13_Click_See_Details_Link() {
		homepage.clickCustomerNameDropdown(driver);
		myAccountPage = homepage.clickMyAccountDropdownLink(driver);
		reviewDetailsPage = myAccountPage.clickSeeDetailsLinkByProductName(productName);

		Assert.assertEquals(reviewDetailsPage.getPageHeader(driver), "Review Details");
	}

	@Test(groups = "submitAReview", description = "Verify that user is directed to the product details page when clicking the product name on the 'My Product Reviews' page")
	public void Reviews_14_Click_Product_Name_My_Product_Reviews_Page() {
		homepage.clickCustomerNameDropdown(driver);
		myAccountPage = homepage.clickMyAccountDropdownLink(driver);
		myProductReviewsPage = (MyProductReviewsPageObject) myAccountPage.clickMyAccountSidebarLinkByLabel(driver,
				"My Product Reviews");
		myProductReviewsPage.clickProductLinkByProductName(productName);
	}

	@Test(groups = "submitAReview", description = "Verify that the '[number] reviews' and 'Add Your Review' hyperlinks are displayed on the 'Review Details' page only for products that already have reviews")
	public void Reviews_15_Display_Of_Reviews_And_Add_Your_Review_Link() {
		homepage.clickCustomerNameDropdown(driver);
		myAccountPage = homepage.clickMyAccountDropdownLink(driver);
		reviewDetailsPage = myAccountPage.clickSeeDetailsLinkByProductName(productName);

		Assert.assertTrue(reviewDetailsPage.isReviewsLinkDisplayed());
		Assert.assertTrue(reviewDetailsPage.isAddYourReviewLinkDisplayed());
	}

	@Test(groups = "submitAReview", description = "Verify that the information displayed on the 'Review Details' page matches the submitted review")
	public void Reviews_16_Correct_Review_Info() {
		homepage.clickCustomerNameDropdown(driver);
		myAccountPage = homepage.clickMyAccountDropdownLink(driver);
		reviewDetailsPage = myAccountPage.clickSeeDetailsLinkByProductName(productName);

		Assert.assertEquals(reviewDetailsPage.getReviewProductName(), productName);
		Assert.assertEquals(reviewDetailsPage.getReviewRating(), "5 stars");
		Assert.assertEquals(reviewDetailsPage.getReviewTitle(), "Summary");
		Assert.assertEquals(reviewDetailsPage.getReviewContent(), "This is a review for " + productName);
		Assert.assertEquals(reviewDetailsPage.getReviewDate(), todaysDateTextual);
	}

	@Test(groups = "submitAReview", description = "Verify that user is directed to the product details page when clicking the '[number] reviews' hyperlink on the 'Review Details' page")
	public void Reviews_17_Click_Number_Reviews_Link() {
		homepage.clickCustomerNameDropdown(driver);
		myAccountPage = homepage.clickMyAccountDropdownLink(driver);
		reviewDetailsPage = myAccountPage.clickSeeDetailsLinkByProductName(productName);
		productDetailsPage = reviewDetailsPage.clickReviewsLink();

		Assert.assertEquals(productDetailsPage.getPageHeader(driver), productName);
	}

	@Test(groups = "submitAReview", description = "Verify that user is directed to the product details page when clicking the 'Add Your Review' hyperlink on the 'Review Details' page")
	public void Reviews_18_Click_Add_Your_Review_Link() {
		homepage.clickCustomerNameDropdown(driver);
		myAccountPage = homepage.clickMyAccountDropdownLink(driver);
		reviewDetailsPage = myAccountPage.clickSeeDetailsLinkByProductName(productName);
		productDetailsPage = reviewDetailsPage.clickAddYourReviewLink();

		Assert.assertEquals(productDetailsPage.getPageHeader(driver), productName);
	}

	@Test(description = "Verify the information message displayed on the 'My Product Reviews' page when user has not submitted any review")
	public void Reviews_19_Info_Message_No_Submitted_Review() {
		createNewCustomerAccountPage = homepage.clickCreateAnAccountLink();
		createNewCustomerAccountPage.sendKeysToFirstNameTextbox(data.getFirstName());
		createNewCustomerAccountPage.sendKeysToLastNameTextbox(data.getLastName());
		createNewCustomerAccountPage.sendKeysToEmailTextbox(data.getEmailAddress());
		createNewCustomerAccountPage.sendKeysToPasswordTextbox(password);
		createNewCustomerAccountPage.sendKeysToConfirmPasswordTextbox(password);
		myAccountPage = createNewCustomerAccountPage.clickCreateAnAccountButton();
		myProductReviewsPage = (MyProductReviewsPageObject) myAccountPage.clickMyAccountSidebarLinkByLabel(driver,
				"My Product Reviews");

		Assert.assertEquals(myProductReviewsPage.getNoReviewSubmittedInfoMessage(), "You have submitted no reviews.");
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeBrowserAndDriver();
	}

	private WebDriver driver;
	private FakeDataUtils data;
	private JsonNode resultNode;
	private String email, password, category, productName, todaysDateTextual;
	private HomepageObject homepage;
	private CreateNewCustomerAccountPageObject createNewCustomerAccountPage;
	private MyAccountPageObject myAccountPage;
	private MyProductReviewsPageObject myProductReviewsPage;
	private ReviewDetailsPageObject reviewDetailsPage;
	private CustomerLoginPageObject customerLoginPage;
	private ProductListingPageObject productListingPage;
	private ProductDetailsPageObject productDetailsPage;
}
