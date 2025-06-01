package commons;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import pageObjects.CheckoutPageObject;
import pageObjects.CompareProductsPageObject;
import pageObjects.HomepageObject;
import pageObjects.MyAccountPageObject;
import pageObjects.MyWishListPageObject;
import pageObjects.ProductDetailsPageObject;
import pageObjects.ProductListingPageObject;
import pageObjects.ShoppingCartPageObject;
import pageUIs.AddressPageUI;
import pageUIs.BasePageUI;
import pageUIs.CompareProductsPageUI;
import pageUIs.MyAccountPageUI;
import pageUIs.MyWishListPageUI;
import pageUIs.ProductListingPageUI;
import pageUIs.ShoppingCartPageUI;

public class BasePage {

	private long shortTimeout = GlobalConstants.SHORT_TIMEOUT;
	private long longTimeout = GlobalConstants.LONG_TIMEOUT;

	public static BasePage getBasePageObject() {
		return new BasePage();
	}

	public void navigateToPageUrl(WebDriver driver, String pageUrl) {
		driver.get(pageUrl);
	}

	public String getPageTitle(WebDriver driver) {
		return driver.getTitle();
	}

	public String getPageUrl(WebDriver driver) {
		return driver.getCurrentUrl();
	}

	public String getPageSourceCode(WebDriver driver) {
		return driver.getPageSource();
	}

	public void backToPage(WebDriver driver) {
		driver.navigate().back();
	}

	public void forwardToPage(WebDriver driver) {
		driver.navigate().forward();
	}

	public void refreshCurrentPage(WebDriver driver) {
		driver.navigate().refresh();
	}

	public void setCookies(WebDriver driver, Set<Cookie> cookies) {
		for (Cookie cookie : cookies) {
			driver.manage().addCookie(cookie);
		}
		sleepInSecond(3);
	}

	public Set<Cookie> getCookies(WebDriver driver) {
		return driver.manage().getCookies();
	}

	public Alert waitForAlertPresence(WebDriver driver) {
		WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
		return explicitWait.until(ExpectedConditions.alertIsPresent());
	}

	public void acceptAlert(WebDriver driver) {
		waitForAlertPresence(driver).accept();
	}

	public void dismissAlert(WebDriver driver) {
		waitForAlertPresence(driver).dismiss();
	}

	public String getAlertText(WebDriver driver) {
		return waitForAlertPresence(driver).getText();
	}

	public void sendKeysToAlert(WebDriver driver, String text) {
		waitForAlertPresence(driver).sendKeys(text);
	}

	public String getCurrentWindowHandle(WebDriver driver) {
		return driver.getWindowHandle();
	}

	public void switchToWindowByTitle(WebDriver driver, String windowTitle) {
		Set<String> allWindowIds = driver.getWindowHandles();

		for (String id : allWindowIds) {
			driver.switchTo().window(id);
			String expectedPageTitle = driver.getTitle();
			if (expectedPageTitle.equals(windowTitle)) {
				break;
			}
		}
	}

	public void closeAllWindowsExceptParent(WebDriver driver, String parentWindowId) {
		Set<String> allWindowIds = driver.getWindowHandles();

		for (String id : allWindowIds) {
			if (!id.equals(parentWindowId)) {
				driver.switchTo().window(id);
				driver.close();
			}
		}

		driver.switchTo().window(parentWindowId);

	}

	public void switchToFrameIframe(WebDriver driver, String locatorType) {
		driver.switchTo().frame(getWebElement(driver, locatorType));
	}

	public void switchToDefaultContent(WebDriver driver) {
		driver.switchTo().defaultContent();
	}

	private By getByLocator(String locatorType) {
		By by = null;
		if (locatorType.startsWith("id=") || locatorType.startsWith("Id=") || locatorType.startsWith("ID=")) {
			by = By.id(locatorType.substring(3));
		} else if (locatorType.startsWith("xpath=") || locatorType.startsWith("Xpath=")
				|| locatorType.startsWith("XPATH=")) {
			by = By.xpath(locatorType.substring(6));
		} else if (locatorType.startsWith("css=") || locatorType.startsWith("Css=") || locatorType.startsWith("CSS=")) {
			by = By.cssSelector(locatorType.substring(4));
		} else if (locatorType.startsWith("name=") || locatorType.startsWith("Name=")
				|| locatorType.startsWith("NAME=")) {
			by = By.name(locatorType.substring(5));
		} else if (locatorType.startsWith("class=") || locatorType.startsWith("Class=")
				|| locatorType.startsWith("CLASS=")) {
			by = By.className(locatorType.substring(6));
		} else {
			throw new RuntimeException("Locator name is not supported");
		}
		return by;

	}

	private String getDynamicXpath(String locatorType, String... dynamicValues) {
		if (locatorType.startsWith("xpath=") || locatorType.startsWith("Xpath=") || locatorType.startsWith("XPATH=")) {
			locatorType = String.format(locatorType, (Object[]) dynamicValues);
		}
		return locatorType;
	}

	public WebElement getWebElement(WebDriver driver, String locatorType) {
		return driver.findElement(getByLocator(locatorType));
	}

	public List<WebElement> getWebElements(WebDriver driver, String locatorType) {
		return driver.findElements(getByLocator(locatorType));
	}

	public void waitForElementVisible(WebDriver driver, String locatorType) {
		WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(getByLocator(locatorType)));
	}

	public void waitForElementVisible(WebDriver driver, String locatorType, String... dynamicValues) {
		WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
		explicitWait.until(ExpectedConditions
				.visibilityOfElementLocated(getByLocator(getDynamicXpath(locatorType, dynamicValues))));
	}

	public void waitForAllElementsVisible(WebDriver driver, String locatorType) {
		WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
		explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getByLocator(locatorType)));
	}

	public void waitForAllElementsVisible(WebDriver driver, String locatorType, String... dynamicValues) {
		WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
		explicitWait.until(ExpectedConditions
				.visibilityOfAllElementsLocatedBy(getByLocator(getDynamicXpath(locatorType, dynamicValues))));
	}

	public void waitForElementInvisible(WebDriver driver, String locatorType) {
		WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getByLocator(locatorType)));
	}

	public void waitForElementInvisible(WebDriver driver, String locatorType, String... dynamicValues) {
		WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
		explicitWait.until(ExpectedConditions
				.invisibilityOfElementLocated(getByLocator(getDynamicXpath(locatorType, dynamicValues))));
	}

	public void waitForAllElementsInvisible(WebDriver driver, String locatorType) {
		WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
		explicitWait.until(ExpectedConditions.invisibilityOfAllElements(getWebElements(driver, locatorType)));
	}

	public void waitForAllElementsInvisible(WebDriver driver, String locatorType, String... dynamicValues) {
		WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
		explicitWait.until(ExpectedConditions
				.invisibilityOfAllElements(getWebElements(driver, getDynamicXpath(locatorType, dynamicValues))));
	}

	public void waitForElementClickable(WebDriver driver, String locatorType) {
		WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
		explicitWait.until(ExpectedConditions.elementToBeClickable(getByLocator(locatorType)));
	}

	public void waitForElementClickable(WebDriver driver, String locatorType, String... dynamicValues) {
		WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
		explicitWait.until(
				ExpectedConditions.elementToBeClickable(getByLocator(getDynamicXpath(locatorType, dynamicValues))));
	}

	public void clickElement(WebDriver driver, String locatorType) {
		getWebElement(driver, locatorType).click();
	}

	public void clickElement(WebDriver driver, String locatorType, String... dynamicValues) {
		getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)).click();
	}

	public void sendKeysToElement(WebDriver driver, String locatorType, String text) {
		WebElement element = getWebElement(driver, locatorType);
		clearText(driver, locatorType);
		element.sendKeys(text);
	}

	public void sendKeysToElement(WebDriver driver, String locatorType, String text, String... dynamicValues) {
		WebElement element = getWebElement(driver, getDynamicXpath(locatorType, dynamicValues));
		clearText(driver, getDynamicXpath(locatorType, dynamicValues));
		element.sendKeys(text);
	}

	public void clearText(WebDriver driver, String locatorType) {
		WebElement element = getWebElement(driver, locatorType);
		element.clear();
	}

	public String getElementText(WebDriver driver, String locatorType) {
		return getWebElement(driver, locatorType).getText().trim();
	}

	public String getElementText(WebDriver driver, String locatorType, String... dynamicValues) {
		return getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)).getText().trim();
	}

	public String getElementAttribute(WebDriver driver, String locatorType, String attributeValue) {
		return getWebElement(driver, locatorType).getAttribute(attributeValue);
	}

	public String getElementAttribute(WebDriver driver, String locatorType, String attributeValue,
			String... dynamicValues) {
		return getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)).getAttribute(attributeValue);
	}

	public String getElementCssValue(WebDriver driver, String locatorType, String propertyName) {
		return getWebElement(driver, locatorType).getCssValue(propertyName);
	}

	public boolean isElementDisplayed(WebDriver driver, String locatorType) {
		try {
			return getWebElement(driver, locatorType).isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public boolean isElementDisplayed(WebDriver driver, String locatorType, String... dynamicValues) {
		return getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)).isDisplayed();
	}

	public boolean isElementNotDisplayed(WebDriver driver, String locatorType) {
		overrideImplicitTimeout(driver, shortTimeout);
		List<WebElement> elements = getWebElements(driver, locatorType);
		overrideImplicitTimeout(driver, longTimeout);

		if (elements.size() == 0) {
			return true;
		} else if (elements.size() > 0 && !elements.get(0).isDisplayed()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isElementNotDisplayed(WebDriver driver, String locatorType, String... dynamicValues) {
		overrideImplicitTimeout(driver, shortTimeout);
		List<WebElement> elements = getWebElements(driver, getDynamicXpath(locatorType, dynamicValues));
		overrideImplicitTimeout(driver, longTimeout);

		if (elements.size() == 0) {
			return true;
		} else if (elements.size() > 0 && !elements.get(0).isDisplayed()) {
			return true;
		} else {
			return false;
		}
	}

	public void overrideImplicitTimeout(WebDriver driver, long timeout) {
		driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
	}

	public boolean isElementSelected(WebDriver driver, String locatorType) {
		return getWebElement(driver, locatorType).isSelected();
	}

	public boolean isElementEnabled(WebDriver driver, String locatorType) {
		return getWebElement(driver, locatorType).isEnabled();
	}

	public void selectOptionDefaultDropdown(WebDriver driver, String locatorType, String optionLabel) {
		Select select = new Select(getWebElement(driver, locatorType));
		String trimmedLabel = optionLabel.trim();

		for (WebElement option : select.getOptions()) {
			if (option.getText().trim().equals(trimmedLabel)) {
				option.click();
				return;
			}
		}
	}

	public void selectOptionDefaultDropdown(WebDriver driver, String locatorType, String optionLabel,
			String... dynamicValues) {
		Select select = new Select(getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)));

		for (WebElement option : select.getOptions()) {
			if (option.getText().contains(optionLabel.trim())) {
				option.click();
				return;
			}
		}

	}

	public String getSelectedOptionDefaultDropdown(WebDriver driver, String locatorType) {
		Select select = new Select(getWebElement(driver, locatorType));
		return select.getFirstSelectedOption().getText();
	}

	public String getSelectedOptionDefaultDropdown(WebDriver driver, String locatorType, String... dynamicValues) {
		Select select = new Select(getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)));
		return select.getFirstSelectedOption().getText();
	}

	public boolean isDropdownMultiple(WebDriver driver, String locatorType) {
		Select select = new Select(getWebElement(driver, locatorType));
		return select.isMultiple();
	}

	public void selectOptionCustomDropdown(WebDriver driver, String dropdownLocator, String optionLocator,
			String optionLabel) {
		getWebElement(driver, dropdownLocator).click();
		sleepInSecond(1);

		WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
		List<WebElement> allItems = explicitWait
				.until(ExpectedConditions.presenceOfAllElementsLocatedBy(getByLocator(optionLocator)));

		for (WebElement item : allItems) {
			if (item.getText().trim().equals(optionLabel)) {
				JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
				jsExecutor.executeScript("arguments[0].scrollIntoView(true);", item);
				sleepInSecond(1);
				item.click();
				break;
			}
		}
	}

	public String convertRGBAToHexaColor(String rgbaValue) {
		return Color.fromString(rgbaValue).asHex();
	}

	public int getNumberOfElements(WebDriver driver, String locatorType) {
		return getWebElements(driver, locatorType).size();
	}

	public int getNumberOfElements(WebDriver driver, String locatorType, String... dynamicValues) {
		return getWebElements(driver, getDynamicXpath(locatorType, dynamicValues)).size();
	}

	public void checkDefaultCheckboxRadioButton(WebDriver driver, String locatorType) {
		WebElement element = getWebElement(driver, locatorType);
		if (!element.isSelected()) {
			element.click();
		}
	}

	public void checkDefaultCheckboxRadioButton(WebDriver driver, String locatorType, String... dynamicValues) {
		WebElement element = getWebElement(driver, getDynamicXpath(locatorType, dynamicValues));
		if (!element.isSelected()) {
			element.click();
		}
	}

	public void uncheckDefaultCheckbox(WebDriver driver, String locatorType) {
		WebElement element = getWebElement(driver, locatorType);
		if (element.isSelected()) {
			element.click();
		}
	}

	public void hoverOverElement(WebDriver driver, String locatorType) {
		Actions action = new Actions(driver);
		action.moveToElement(getWebElement(driver, locatorType)).perform();
	}

	public void hoverOverElement(WebDriver driver, String locatorType, String... dynamicValues) {
		Actions action = new Actions(driver);
		action.moveToElement(getWebElement(driver, getDynamicXpath(locatorType, dynamicValues))).perform();
	}

	public void pressKeyOnElement(WebDriver driver, String locatorType, Keys key) {
		Actions action = new Actions(driver);
		action.sendKeys(getWebElement(driver, locatorType), key).perform();
	}

	public void pressKeyOnElement(WebDriver driver, String locatorType, Keys key, String... dynamicValues) {
		Actions action = new Actions(driver);
		action.sendKeys(getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)), key).perform();
	}

	public void uploadMultipleFiles(WebDriver driver, String... fileNames) {
		String filePath = GlobalConstants.UPLOAD_FILE_PATH;
		String fullFileName = "";
		for (String file : fileNames) {
			fullFileName = fullFileName + filePath + File.separator + file + "\n";
		}
		fullFileName = fullFileName.trim();
		getWebElement(driver, BasePageUI.General.UPLOAD_FILE).sendKeys(fullFileName);
	}

	public String getElementValueByJS(WebDriver driver, String xpathLocator) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		if (xpathLocator.startsWith("xpath=") || xpathLocator.startsWith("Xpath=")
				|| xpathLocator.startsWith("XPATH=")) {
			xpathLocator = xpathLocator.substring(6);
		}
		return (String) jsExecutor.executeScript(
				"var element = document.evaluate(arguments[0], document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;"
						+ "return element ? element.value || element.textContent || null : null;",
				xpathLocator);
	}

	public String getElementValueByJS(WebDriver driver, String xpathLocator, String... dynamicValues) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		xpathLocator = String.format(xpathLocator, (Object[]) dynamicValues);
		if (xpathLocator.startsWith("xpath=") || xpathLocator.startsWith("Xpath=")
				|| xpathLocator.startsWith("XPATH=")) {
			xpathLocator = xpathLocator.substring(6);
		}
		return (String) jsExecutor.executeScript(
				"var element = document.evaluate(arguments[0], document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;"
						+ "return element ? element.value || element.textContent || null : null;",
				xpathLocator);
	}

	public boolean isDataSortedAscending(WebDriver driver, String locator) {
		List<String> originalList = new ArrayList<>();
		List<WebElement> elements = getWebElements(driver, locator);

		for (WebElement element : elements) {
			originalList.add(element.getText().trim());
		}

		List<String> sortedList = new ArrayList<>(originalList);
		Collections.sort(sortedList);

		return originalList.equals(sortedList);
	}

	public boolean isDataSortedDescending(WebDriver driver, String locator) {
		List<String> originalList = new ArrayList<>();
		List<WebElement> elements = getWebElements(driver, locator);

		for (WebElement element : elements) {
			originalList.add(element.getText().trim());
		}

		List<String> sortedList = new ArrayList<>(originalList);
		sortedList.sort(Collections.reverseOrder());

		return originalList.equals(sortedList);
	}

	public void clickOutisdeElement(WebDriver driver) {
		WebElement body = getWebElement(driver, BasePageUI.MainContent.MAIN_CONTENT);
		body.click();
	}

	public void scrollToBottom(WebDriver driver) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.scrollBy(0,document.body.scrollHeight)");
	}

	public void highlightElement(WebDriver driver, String locatorType) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		WebElement element = getWebElement(driver, locatorType);
		String originalStyle = element.getAttribute("style");
		jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style",
				"border: 2px solid red; border-style: dashed;");
		sleepInSecond(1);
		jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style",
				originalStyle);
	}

	public void highlightElement(WebDriver driver, String locatorType, String... dynamicValues) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		WebElement element = getWebElement(driver, getDynamicXpath(locatorType, dynamicValues));
		String originalStyle = element.getAttribute("style");
		jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style",
				"border: 2px solid red; border-style: dashed;");
		sleepInSecond(1);
		jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style",
				originalStyle);
	}

	public void clickElementByJS(WebDriver driver, String locatorType) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].click();", getWebElement(driver, locatorType));
	}

	public void clickElementByJS(WebDriver driver, String locatorType, String... dynamicValues) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].click();",
				getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)));
	}

	public void scrollToElement(WebDriver driver, String locatorType) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", getWebElement(driver, locatorType));
	}

	public void scrollToElement(WebDriver driver, String locatorType, String... dynamicValues) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		WebElement element = getWebElement(driver, getDynamicXpath(locatorType, dynamicValues));
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
	}

	public void removeAttributeInDOM(WebDriver driver, String locatorType, String attribute) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].removeAttribute('" + attribute + "');",
				getWebElement(driver, locatorType));
	}

	public boolean areJQueryAndJSLoadedSuccess(WebDriver driver) {
		WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

		ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				try {
					return ((Long) jsExecutor.executeScript("return jQuery.active") == 0);
				} catch (Exception e) {
					return true;
				}
			}
		};

		ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return jsExecutor.executeScript("return document.readyState").toString().equals("complete");
			}
		};

		return explicitWait.until(jQueryLoad) && explicitWait.until(jsLoad);
	}

	public String getElementValidationMessage(WebDriver driver, String locatorType) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		return (String) jsExecutor.executeScript("return arguments[0].validationMessage;",
				getWebElement(driver, locatorType));
	}

	public boolean isImageLoaded(WebDriver driver, String locatorType) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		boolean status = (boolean) jsExecutor.executeScript(
				"return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0",
				getWebElement(driver, locatorType));
		if (status) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isImageLoaded(WebDriver driver, String locatorType, String... dynamicValues) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		boolean status = (boolean) jsExecutor.executeScript(
				"return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0",
				getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)));
		if (status) {
			return true;
		} else {
			return false;
		}
	}

	public void sleepInSecond(long timeInSecond) {
		try {
			Thread.sleep(timeInSecond * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// =====================================
	// Magento Luma - Common Actions
	// =====================================

	/**
	 * Verifies that the loading icon is not displayed on the page.
	 *
	 * @param driver the WebDriver instance used to interact with the web page.
	 * @return {@code true} if the loading icon is not displayed, {@code false}
	 *         otherwise.
	 */
	public boolean isLoadingIconNotDisplayed(WebDriver driver) {
		return isElementNotDisplayed(driver, BasePageUI.General.LOADING_ICON);
	}

	/**
	 * Clicks the Luma logo to navigate to the homepage.
	 *
	 * @param driver the WebDriver instance used to interact with the web page.
	 * @return the HomepageObject after clicking the Luma logo.
	 */
	public HomepageObject clickLumaLogo(WebDriver driver) {
		waitForElementClickable(driver, BasePageUI.Header.LUMA_LOGO);
		clickElementByJS(driver, BasePageUI.Header.LUMA_LOGO);
		return PageGeneratorManager.getHomepage(driver);
	}

	/**
	 * Retrieves the text of the page header.
	 *
	 * @param driver the WebDriver instance used to interact with the web page.
	 * @return the text of the page header.
	 */
	public String getPageHeader(WebDriver driver) {
		waitForElementVisible(driver, BasePageUI.MainContent.PAGE_HEADER);
		return getElementText(driver, BasePageUI.MainContent.PAGE_HEADER);
	}

	/**
	 * Retrieves the placeholder text of the search bar.
	 *
	 * @param driver the WebDriver instance used to interact with the web page.
	 * @return the placeholder text of the search bar.
	 */
	public String getSearchBarPlaceholder(WebDriver driver) {
		waitForElementVisible(driver, BasePageUI.Header.SEARCH_BAR);
		return getElementAttribute(driver, BasePageUI.Header.SEARCH_BAR, "placeholder");
	}

	/**
	 * Fills in the search bar with the specified text.
	 *
	 * @param driver      the WebDriver instance used to interact with the web page.
	 * @param searchValue the text to fill in the search bar.
	 */
	public void sendKeysToSearchBar(WebDriver driver, String searchValue) {
		waitForElementVisible(driver, BasePageUI.Header.SEARCH_BAR);
		sendKeysToElement(driver, BasePageUI.Header.SEARCH_BAR, searchValue);
	}

	/**
	 * Verifies that all search suggestions contain the specified search value.
	 *
	 * @param driver      the WebDriver instance used to interact with the web page.
	 * @param searchValue the text to check within the search suggestions.
	 * @return {@code true} if all search suggestions contain the search value,
	 *         {@code false} otherwise.
	 */
	public boolean areSearchSuggestionsDisplayedCorrectly(WebDriver driver, String searchValue) {
		waitForAllElementsVisible(driver, BasePageUI.Header.SEARCH_SUGGESTION_TEXT);
		List<WebElement> suggestionTexts = getWebElements(driver, BasePageUI.Header.SEARCH_SUGGESTION_TEXT);

		for (WebElement suggestionText : suggestionTexts) {
			if (!suggestionText.getText().toLowerCase().contains(searchValue)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Verifies that the number of search suggestions matches the product count for
	 * each suggestion.
	 *
	 * @param driver      the WebDriver instance used to interact with the web page.
	 * @param searchValue the text to enter into the search bar and verify
	 *                    suggestions for.
	 * @return {@code true} if the suggestion count matches the product count for
	 *         each suggestion, {@code false} otherwise.
	 */
	public boolean isSuggestionCountMatchingProductCount(WebDriver driver, String searchValue) {
		sendKeysToSearchBar(driver, searchValue);
		waitForAllElementsVisible(driver, BasePageUI.Header.SEARCH_SUGGESTION);

		List<WebElement> suggestions = getWebElements(driver, BasePageUI.Header.SEARCH_SUGGESTION);
		List<WebElement> suggestionNumbers = getWebElements(driver, BasePageUI.Header.SEARCH_SUGGESTION_NUMBER);

		for (int i = 0; i < suggestions.size(); i++) {
			String suggestionNumber = suggestionNumbers.get(i).getText();
			suggestions.get(i).click();
			waitForElementVisible(driver, ProductListingPageUI.TOTAL_NUMBER_OF_ITEMS);
			String totalProducts = getElementText(driver, ProductListingPageUI.TOTAL_NUMBER_OF_ITEMS);

			if (!suggestionNumber.equals(totalProducts)) {
				return false;
			}
			sendKeysToSearchBar(driver, searchValue);
			waitForAllElementsVisible(driver, BasePageUI.Header.SEARCH_SUGGESTION);
			suggestions = getWebElements(driver, BasePageUI.Header.SEARCH_SUGGESTION);
			suggestionNumbers = getWebElements(driver, BasePageUI.Header.SEARCH_SUGGESTION_NUMBER);
		}

		return true;
	}

	/**
	 * Fills in the search bar with the specified text and presses Enter to perform
	 * a search.
	 *
	 * @param driver      the WebDriver instance used to interact with the web page.
	 * @param searchValue the text to fill in the search bar.
	 * @return the ProductListingPageObject after performing the search.
	 */
	public ProductListingPageObject sendKeysToSearchBarAndPressEnter(WebDriver driver, String searchValue) {
		waitForElementVisible(driver, BasePageUI.Header.SEARCH_BAR);
		sendKeysToElement(driver, BasePageUI.Header.SEARCH_BAR, searchValue);
		pressKeyOnElement(driver, BasePageUI.Header.SEARCH_BAR, Keys.ENTER);
		return PageGeneratorManager.getProductListingPageObject(driver);
	}

	/**
	 * Clicks the shopping cart icon to open the mini cart modal if it is not
	 * displayed.
	 *
	 * @param driver the WebDriver instance used to interact with the web page.
	 */
	public void clickShoppingCartIcon(WebDriver driver) {
		WebElement miniCartModal = getWebElement(driver, BasePageUI.Header.MiniCart.MINI_CART_MODAL);
		if (!miniCartModal.isDisplayed()) {
			waitForElementClickable(driver, BasePageUI.Header.MiniCart.SHOPPING_CART_ICON);
			clickElementByJS(driver, BasePageUI.Header.MiniCart.SHOPPING_CART_ICON);
		}
	}

	/**
	 * Retrieves the subtotal value from the mini cart.
	 *
	 * @param driver the WebDriver instance used to interact with the web page.
	 * @return the subtotal value from the mini cart as a float.
	 */
	public Float getMiniCartSubtotal(WebDriver driver) {
		waitForElementVisible(driver, BasePageUI.Header.MiniCart.CART_SUBTOTAL);
		return Float.parseFloat(getElementText(driver, BasePageUI.Header.MiniCart.CART_SUBTOTAL).replace("$", ""));
	}

	/**
	 * Retrieves the number of products the mini cart.
	 *
	 * @param driver the WebDriver instance used to interact with the web page.
	 * @return the number of products in the mini cart as a string.
	 */
	public String getMiniCartQuantity(WebDriver driver) {
		waitForElementVisible(driver, BasePageUI.Header.MiniCart.MINI_CART_QUANTITY);
		return getElementText(driver, BasePageUI.Header.MiniCart.MINI_CART_QUANTITY);
	}

	/**
	 * Clicks the "Proceed to Checkout" button in the mini cart and navigates to the
	 * checkout page.
	 *
	 * @param driver the WebDriver instance used to interact with the web page.
	 * @return the CheckoutPageObject after proceeding to checkout.
	 */
	public CheckoutPageObject clickProceedToCheckoutButton(WebDriver driver) {
		waitForElementClickable(driver, BasePageUI.Header.MiniCart.PROCEED_TO_CHECKOUT_BUTTON);
		clickElementByJS(driver, BasePageUI.Header.MiniCart.PROCEED_TO_CHECKOUT_BUTTON);
		sleepInSecond(GlobalConstants.SHORT_TIMEOUT);
		return PageGeneratorManager.getCheckoutPageObject(driver);
	}

	/**
	 * Clicks the cross icon in the mini cart to close the mini cart.
	 *
	 * @param driver the WebDriver instance used to interact with the web page.
	 */
	public void clickMiniCartCrossIcon(WebDriver driver) {
		waitForElementClickable(driver, BasePageUI.Header.MiniCart.MINI_CART_CROSS_ICON);
		clickElementByJS(driver, BasePageUI.Header.MiniCart.MINI_CART_CROSS_ICON);
	}

	/**
	 * Verifies if the mini cart is not displayed on the page.
	 *
	 * @param driver the WebDriver instance used to interact with the web page.
	 * @return {@code true} if the mini cart is not displayed, {@code false}
	 *         otherwise.
	 */
	public boolean isMiniCartNotDisplayed(WebDriver driver) {
		return isElementNotDisplayed(driver, BasePageUI.Header.MiniCart.MINI_CART_MODAL);
	}

	/**
	 * Retrieves the info message displayed when the shopping cart is empty.
	 *
	 * @param driver the WebDriver instance used to interact with the web page.
	 * @return the info message displayed when the shopping cart is empty.
	 */
	public String getEmptyShoppingCartInfoMessage(WebDriver driver) {
		waitForElementVisible(driver, BasePageUI.Header.MiniCart.MINI_CART_EMPTY_SHOPPING_CART_INFO_MESSAGE);
		return getElementText(driver, BasePageUI.Header.MiniCart.MINI_CART_EMPTY_SHOPPING_CART_INFO_MESSAGE);
	}

	/**
	 * Clicks the "See Details" collapsible header for the specified product.
	 *
	 * @param driver      the WebDriver instance used to interact with the web page.
	 * @param productName the product name for which the "See Details" header is
	 *                    clicked.
	 */
	public void clickSeeDetailsCollapsibleHeaderByProductName(WebDriver driver, String productName) {
		waitForElementClickable(driver,
				BasePageUI.Header.MiniCart.DYNAMIC_SEE_DETAILS_COLLAPSIBLE_HEADER_BY_PRODUCT_NAME, productName);
		clickElementByJS(driver, BasePageUI.Header.MiniCart.DYNAMIC_SEE_DETAILS_COLLAPSIBLE_HEADER_BY_PRODUCT_NAME,
				productName);
	}

	/**
	 * Retrieves the size value for the specified product.
	 *
	 * @param driver      the WebDriver instance used to interact with the web page.
	 * @param productName the name of the product for which the size value is
	 *                    retrieved.
	 * @return the size value of the specified product.
	 */
	public String getSizeValueByProductName(WebDriver driver, String productName) {
		waitForElementVisible(driver, BasePageUI.Header.MiniCart.DYNAMIC_SIZE_VALUE_BY_PRODUCT_NAME, productName);
		return getElementText(driver, BasePageUI.Header.MiniCart.DYNAMIC_SIZE_VALUE_BY_PRODUCT_NAME, productName);
	}

	/**
	 * Retrieves the color value for the specified product.
	 *
	 * @param driver      the WebDriver instance used to interact with the web page.
	 * @param productName the name of the product for which the color value is
	 *                    retrieved.
	 * @return the color value of the specified product.
	 */
	public String getColorValueByProductName(WebDriver driver, String productName) {
		waitForElementVisible(driver, BasePageUI.Header.MiniCart.DYNAMIC_COLOR_VALUE_BY_PRODUCT_NAME, productName);
		return getElementText(driver, BasePageUI.Header.MiniCart.DYNAMIC_COLOR_VALUE_BY_PRODUCT_NAME, productName);
	}

	/**
	 * Fills in the quantity textbox with the specified quantity for a specific
	 * product in the mini cart.
	 *
	 * @param driver      the WebDriver instance used to interact with the web page.
	 * @param quantity    the quantity to be filled in the textbox.
	 * @param productName the product name for which the quantity is being filled
	 *                    in.
	 */
	public void sendKeysToQuantityTextboxByProductName(WebDriver driver, String quantity, String productName) {
		waitForElementVisible(driver, BasePageUI.Header.MiniCart.DYNAMIC_QUANTITY_TEXTBOX_BY_PRODUCT_NAME, productName);
		sendKeysToElement(driver, BasePageUI.Header.MiniCart.DYNAMIC_QUANTITY_TEXTBOX_BY_PRODUCT_NAME, quantity,
				productName);
	}

	/**
	 * Retrieves the quantity value from the quantity textbox for a specific product
	 * in the mini cart.
	 *
	 * @param driver      the WebDriver instance used to interact with the web page.
	 * @param productName the product name for which the quantity value is being
	 *                    retrieved.
	 * @return the quantity value of the specified product in the mini cart.
	 */
	public String getQuantityValueByProductName(WebDriver driver, String productName) {
		waitForElementVisible(driver, BasePageUI.Header.MiniCart.DYNAMIC_QUANTITY_TEXTBOX_BY_PRODUCT_NAME, productName);
		return getElementValueByJS(driver, BasePageUI.Header.MiniCart.DYNAMIC_QUANTITY_TEXTBOX_BY_PRODUCT_NAME,
				productName);
	}

	/**
	 * Clicks the "Update" button in the mini cart.
	 *
	 * @param driver the WebDriver instance used to interact with the web page.
	 */
	public void clickUpdateButton(WebDriver driver) {
		waitForElementClickable(driver, BasePageUI.Header.MiniCart.UPDATE_BUTTON);
		clickElementByJS(driver, BasePageUI.Header.MiniCart.UPDATE_BUTTON);
		sleepInSecond(GlobalConstants.SHORT_TIMEOUT);
	}

	/**
	 * Clicks the pen icon for the specified product in the mini cart and navigates
	 * to the product details page.
	 *
	 * @param driver      the WebDriver instance used to interact with the web page.
	 * @param productName the product name for which pen icon is to be clicked.
	 * @return the ProductDetailsPageObject after clicking the pen icon.
	 */
	public ProductDetailsPageObject clickPenIconByProductName(WebDriver driver, String productName) {
		waitForElementClickable(driver, BasePageUI.Header.MiniCart.DYNAMIC_PEN_ICON_BY_PRODUCT_NAME, productName);
		clickElementByJS(driver, BasePageUI.Header.MiniCart.DYNAMIC_PEN_ICON_BY_PRODUCT_NAME, productName);
		return PageGeneratorManager.getProductDetailsPageObject(driver);
	}

	/**
	 * Clicks the trashcan icon for the specified product in the mini cart.
	 *
	 * @param driver      the WebDriver instance used to interact with the web page.
	 * @param productName the product name for which trashcan icon is to be clicked.
	 */
	public void clickTrashcanIconByProductName(WebDriver driver, String productName) {
		waitForElementClickable(driver, BasePageUI.Header.MiniCart.DYNAMIC_TRASHCAN_ICON_BY_PRODUCT_NAME, productName);
		clickElementByJS(driver, BasePageUI.Header.MiniCart.DYNAMIC_TRASHCAN_ICON_BY_PRODUCT_NAME, productName);
	}

	/**
	 * Verifies that the specified product is not displayed in the mini cart.
	 *
	 * @param driver      the WebDriver instance used to interact with the web page.
	 * @param productName the product name to check for display in the mini cart.
	 * @return {@code true} if the product is not displayed in the mini cart,
	 *         {@code false} otherwise.
	 */
	public boolean isProductNotDisplayedInMiniCart(WebDriver driver, String productName) {
		return isElementNotDisplayed(driver, BasePageUI.Header.MiniCart.DYNAMIC_PRODUCT_LINK_BY_NAME, productName);
	}

	/**
	 * Clicks the "View and Edit Cart" link in the mini cart and navigates to the
	 * shopping cart page.
	 *
	 * @param driver the WebDriver instance used to interact with the web page.
	 * @return the ShoppingCartPageObject after navigating to the shopping cart
	 *         page.
	 */
	public ShoppingCartPageObject clickViewAndEditCartLink(WebDriver driver) {
		waitForElementClickable(driver, BasePageUI.Header.MiniCart.VIEW_AND_EDIT_CART_LINK);
		clickElementByJS(driver, BasePageUI.Header.MiniCart.VIEW_AND_EDIT_CART_LINK);
		return PageGeneratorManager.getShoppingCartPageObject(driver);
	}

	/**
	 * Retrieves the number of products displayed next to the shopping cart icon.
	 *
	 * @param driver the WebDriver instance used to interact with the web page.
	 * @return the number of products displayed next to the shopping cart icon as a
	 *         string.
	 */
	public String getShoppingCartQuantity(WebDriver driver) {
		waitForElementVisible(driver, BasePageUI.Header.MiniCart.SHOPPING_CART_QUANTITY);
		return getElementText(driver, BasePageUI.Header.MiniCart.SHOPPING_CART_QUANTITY);
	}

	/**
	 * Clicks on a multi-level dropdown item in the navigation bar based on the
	 * provided labels and navigates to the product listing page.
	 *
	 * @param driver           the WebDriver instance used to interact with the web
	 *                         page.
	 * @param dropdownLabel    the label of the dropdown menu to be opened.
	 * @param firstLevelLabel  the label of the first level item in the dropdown
	 *                         menu.
	 * @param secondLevelLabel the label of the second level item to be clicked.
	 * @return the ProductListingPageObject after navigating to the product listing
	 *         page.
	 */
	public ProductListingPageObject clickNavigationBarDropdownMultiLevelItemLinkByLabels(WebDriver driver,
			String dropdownLabel, String firstLevelLabel, String secondLevelLabel) {
		waitForElementVisible(driver, BasePageUI.Header.DYNAMIC_NAVIGATION_BAR_DROPDOWN_BY_LABEL, dropdownLabel);
		hoverOverElement(driver, BasePageUI.Header.DYNAMIC_NAVIGATION_BAR_DROPDOWN_BY_LABEL, dropdownLabel);
		waitForElementVisible(driver, BasePageUI.Header.DYNAMIC_NAVIGATION_BAR_DROPDOWN_MULTI_LEVEL_ITEM_BY_LABEL,
				firstLevelLabel, dropdownLabel.toLowerCase());
		hoverOverElement(driver, BasePageUI.Header.DYNAMIC_NAVIGATION_BAR_DROPDOWN_MULTI_LEVEL_ITEM_BY_LABEL,
				firstLevelLabel, dropdownLabel.toLowerCase());
		waitForElementClickable(driver, BasePageUI.Header.DYNAMIC_NAVIGATION_BAR_DROPDOWN_MULTI_LEVEL_ITEM_BY_LABEL,
				secondLevelLabel, dropdownLabel.toLowerCase());
		clickElementByJS(driver, BasePageUI.Header.DYNAMIC_NAVIGATION_BAR_DROPDOWN_MULTI_LEVEL_ITEM_BY_LABEL,
				secondLevelLabel, dropdownLabel.toLowerCase());
		sleepInSecond(GlobalConstants.SHORT_TIMEOUT);
		return PageGeneratorManager.getProductListingPageObject(driver);
	}

	/**
	 * Clicks on a single-level dropdown item in the navigation bar based on the
	 * provided labels and navigates to the product listing page.
	 *
	 * @param driver        the WebDriver instance used to interact with the web
	 *                      page.
	 * @param dropdownLabel the label of the dropdown menu to be opened.
	 * @param label         the label of the single-level item to be clicked.
	 * @return the ProductListingPageObject after navigating to the product listing
	 *         page.
	 */
	public ProductListingPageObject clickNavigationBarDropdownSingleLevelItemLinkByLabels(WebDriver driver,
			String dropdownLabel, String label) {
		waitForElementVisible(driver, BasePageUI.Header.DYNAMIC_NAVIGATION_BAR_DROPDOWN_BY_LABEL, dropdownLabel);
		hoverOverElement(driver, BasePageUI.Header.DYNAMIC_NAVIGATION_BAR_DROPDOWN_BY_LABEL, dropdownLabel);
		waitForElementClickable(driver, BasePageUI.Header.DYNAMIC_NAVIGATION_BAR_DROPDOWN_SINGLE_LEVEL_ITEM_BY_LABEL,
				label);
		clickElementByJS(driver, BasePageUI.Header.DYNAMIC_NAVIGATION_BAR_DROPDOWN_SINGLE_LEVEL_ITEM_BY_LABEL, label);
		sleepInSecond(GlobalConstants.SHORT_TIMEOUT);
		return PageGeneratorManager.getProductListingPageObject(driver);
	}

	/**
	 * Clicks on the customer name dropdown in the header to reveal account options.
	 *
	 * @param driver the WebDriver instance used to interact with the web page.
	 */
	public void clickCustomerNameDropdown(WebDriver driver) {
		waitForElementClickable(driver, BasePageUI.Header.ACCOUNT_NAME_DROPDOWN);
		clickElementByJS(driver, BasePageUI.Header.ACCOUNT_NAME_DROPDOWN);
	}

	/**
	 * Clicks on the "My Account" dropdown link in the header and navigates to the
	 * "My Account" page.
	 *
	 * @param driver the WebDriver instance used to interact with the web page.
	 * @return the MyAccountPageObject after navigating to the My Account page.
	 */
	public MyAccountPageObject clickMyAccountDropdownLink(WebDriver driver) {
		waitForElementClickable(driver, BasePageUI.Header.MY_ACCOUNT_DROPDOWN_LINK);
		clickElementByJS(driver, BasePageUI.Header.MY_ACCOUNT_DROPDOWN_LINK);
		return PageGeneratorManager.getMyAccountPage(driver);
	}

	/**
	 * Clicks the "My Wish List" dropdown link in the header and navigates to the
	 * "My Wish List" page.
	 *
	 * @param driver the WebDriver instance used to interact with the web page.
	 * @return the MyWishListPageObject after navigating to the My Wish List page.
	 */
	public MyWishListPageObject clickMyWishListDropdownLink(WebDriver driver) {
		waitForElementClickable(driver, BasePageUI.Header.MY_WISH_LIST_DROPDOWN_LINK);
		clickElementByJS(driver, BasePageUI.Header.MY_WISH_LIST_DROPDOWN_LINK);
		return PageGeneratorManager.getMyWishlistPage(driver);
	}

	/**
	 * Clicks the "Sign Out" dropdown link in the header and navigates to the
	 * homepage.
	 *
	 * @param driver the WebDriver instance used to interact with the web page.
	 * @return the HomepageObject after navigating to the homepage.
	 */
	public HomepageObject clickSignOutDropdownLink(WebDriver driver) {
		waitForElementClickable(driver, BasePageUI.Header.SIGN_OUT_DROPDOWN_LINK);
		clickElementByJS(driver, BasePageUI.Header.SIGN_OUT_DROPDOWN_LINK);
		return PageGeneratorManager.getHomepage(driver);
	}

	/**
	 * Verifies if the welcome message is displayed in the header.
	 *
	 * @param driver the WebDriver instance used to interact with the web page.
	 * @return {@code true} if the welcome message is displayed, {@code false}
	 *         otherwise.
	 */
	public boolean isWelcomeMessageDisplayed(WebDriver driver) {
		return isElementDisplayed(driver, BasePageUI.Header.WELCOME_MESSAGE);
	}

	/**
	 * Clicks the sidebar link in the "My Account" section based on the provided
	 * label.
	 *
	 * @param driver the WebDriver instance used to interact with the web page.
	 * @param label  the label of the sidebar link to be clicked.
	 * @return the appropriate page object after clicking the sidebar link, which
	 *         could be: MyAccountPageObject, MyOrdersPageObject,
	 *         MyDownloadableProductsPageObject, MyWishlistPageObject,
	 *         AddressBookPageObject, AccountInformationPageObject,
	 *         StoredPaymentMethodsPageObject, or MyProductReviewsPageObject.
	 */
	public BasePage clickMyAccountSidebarLinkByLabel(WebDriver driver, String label) {
		waitForElementClickable(driver, MyAccountPageUI.DYNAMIC_SIDEBAR_LINK_BY_LABEL, label);
		clickElementByJS(driver, MyAccountPageUI.DYNAMIC_SIDEBAR_LINK_BY_LABEL, label);

		switch (label) {
		case "My Account":
			return PageGeneratorManager.getMyAccountPage(driver);
		case "My Orders":
			return PageGeneratorManager.getMyOrdersPage(driver);
		case "My Downloadable Products":
			return PageGeneratorManager.getMyDownloadableProductsPage(driver);
		case "My Wish List":
			return PageGeneratorManager.getMyWishlistPage(driver);
		case "Address Book":
			return PageGeneratorManager.getAddressBookPage(driver);
		case "Account Information":
			return PageGeneratorManager.getAccountInformationPage(driver);
		case "Stored Payment Methods":
			return PageGeneratorManager.getStoredPaymentMethodsPage(driver);
		case "My Product Reviews":
			return PageGeneratorManager.getMyProductReviewsPage(driver);
		default:
			throw new RuntimeException("Page name is invalid");
		}
	}

	/**
	 * Checks the checkbox for the specified product in the "Recently Ordered"
	 * section.
	 *
	 * @param driver      the WebDriver instance used to interact with the web page.
	 * @param productName the name of the for which checkbox is to be checked.
	 */
	public void checkRecentlyOrderedCheckboxByProductName(WebDriver driver, String productName) {
		waitForElementVisible(driver, BasePageUI.RecentlyOrderedSection.DYNAMIC_CHECKBOX_BY_PRODUCT_NAME, productName);
		checkDefaultCheckboxRadioButton(driver, BasePageUI.RecentlyOrderedSection.DYNAMIC_CHECKBOX_BY_PRODUCT_NAME,
				productName);
	}

	/**
	 * Clicks the "View All" link in the "Recently Ordered" section.
	 *
	 * @param driver the WebDriver instance used to interact with the web page.
	 */
	public void clickViewAllLinkRecentlyOrderedSection(WebDriver driver) {
		waitForElementClickable(driver, BasePageUI.RecentlyOrderedSection.VIEW_ALL_LINK);
		clickElementByJS(driver, BasePageUI.RecentlyOrderedSection.VIEW_ALL_LINK);
	}

	/**
	 * Retrieves the error message displayed when no recently ordered product is
	 * selected.
	 *
	 * @param driver the WebDriver instance used to interact with the web page.
	 * @return the text of the error message displayed when no recently ordered
	 *         product is selected.
	 */
	public String getNoRecentlyOrderedProductSelectedErrorMessage(WebDriver driver) {
		waitForElementVisible(driver, BasePageUI.MainContent.MESSAGE);
		return getElementText(driver, BasePageUI.MainContent.MESSAGE);
	}

	/**
	 * Clicks the "Add to Cart" button in the "Recently Ordered" section.
	 *
	 * @param driver the WebDriver instance used to interact with the web page.
	 */
	public void clickAddToCartButtonRecentlyOrderedSection(WebDriver driver) {
		waitForElementClickable(driver, BasePageUI.RecentlyOrderedSection.ADD_TO_CART_BUTTON);
		clickElementByJS(driver, BasePageUI.RecentlyOrderedSection.ADD_TO_CART_BUTTON);
	}

	/**
	 * Retrieves the information message displayed when the wish list is empty.
	 *
	 * @param driver the WebDriver instance used to interact with the web page.
	 * @return the text of the empty wish list info message.
	 */
	public String getEmptyWishListInfoMessage(WebDriver driver) {
		waitForElementVisible(driver, BasePageUI.MyWishListSection.EMPTY_WISH_LIST_INFO_MESSAGE);
		return getElementText(driver, BasePageUI.MyWishListSection.EMPTY_WISH_LIST_INFO_MESSAGE);
	}

	/**
	 * Clicks the "Add to Cart" button for the specified product in the wish list
	 * without selecting any options.
	 *
	 * @param driver      the WebDriver instance used to interact with the web page.
	 * @param productName the name of the product for which the "Add to Cart" button
	 *                    will be clicked.
	 */
	public void clickAddToCartButtonByProductNameWithoutOptions(WebDriver driver, String productName) {
		waitForElementClickable(driver, BasePageUI.MyWishListSection.DYNAMIC_ADD_TO_CART_BUTTON_BY_PRODUCT_NAME,
				productName);
		clickElementByJS(driver, BasePageUI.MyWishListSection.DYNAMIC_ADD_TO_CART_BUTTON_BY_PRODUCT_NAME, productName);
	}

	/**
	 * Clicks the "Add to Cart" button for the specified product in the wish list
	 * and navigates to the product details page.
	 *
	 * @param driver      the WebDriver instance used to interact with the web page.
	 * @param productName the name of the product for which the "Add to Cart" button
	 *                    will be clicked.
	 * @return the ProductDetailsPageObject after clicking the "Add to Cart" button.
	 */
	public ProductDetailsPageObject clickAddToCartButtonByProductNameWithOptions(WebDriver driver, String productName) {
		waitForElementClickable(driver, BasePageUI.MyWishListSection.DYNAMIC_ADD_TO_CART_BUTTON_BY_PRODUCT_NAME,
				productName);
		clickElementByJS(driver, BasePageUI.MyWishListSection.DYNAMIC_ADD_TO_CART_BUTTON_BY_PRODUCT_NAME, productName);
		return PageGeneratorManager.getProductDetailsPageObject(driver);
	}

	/**
	 * Clicks the cross icon to remove the specified product from the wish list.
	 *
	 * @param driver      the WebDriver instance used to interact with the web page.
	 * @param productName the name of the product for which the cross icon will be
	 *                    clicked to remove it from the wish list.
	 */
	public void clickCrossIconByProductName(WebDriver driver, String productName) {
		waitForElementClickable(driver, BasePageUI.MyWishListSection.DYNAMIC_CROSS_ICON_BY_PRODUCT_NAME, productName);
		clickElementByJS(driver, BasePageUI.MyWishListSection.DYNAMIC_CROSS_ICON_BY_PRODUCT_NAME, productName);
	}

	/**
	 * Verifies if the specified product is not displayed in the "My Wish List"
	 * section.
	 *
	 * @param driver      the WebDriver instance used to interact with the web page.
	 * @param productName the name of the product to check in the "My Wish List"
	 *                    section.
	 * @return true if the product is not displayed in the "My Wish List" section,
	 *         otherwise false.
	 */
	public boolean isProductNotDisplayedInMyWishListSection(WebDriver driver, String productName) {
		return isElementNotDisplayed(driver, BasePageUI.MyWishListSection.DYNAMIC_PRODUCT_LINK_BY_PRODUCT_NAME,
				productName);
	}

	/**
	 * Clicks the "Go to Wish List" link in the "My Wish List" section and navigates
	 * to the "My Wish List" page.
	 *
	 * @param driver the WebDriver instance used to interact with the web page.
	 * @return the MyWishListPageObject after clicking the "Go to Wish List" link.
	 */
	public MyWishListPageObject clickGoToWishListLink(WebDriver driver) {
		waitForElementClickable(driver, BasePageUI.MyWishListSection.GO_TO_WISH_LIST_LINK);
		clickElementByJS(driver, BasePageUI.MyWishListSection.GO_TO_WISH_LIST_LINK);
		return PageGeneratorManager.getMyWishlistPage(driver);
	}

	/**
	 * Retrieves the info message displayed when the comparison list is empty.
	 *
	 * @param driver the WebDriver instance used to interact with the web page.
	 * @return the text of info message displayed when the comparison list is empty.
	 */
	public String getEmptyComparisonListInfoMessage(WebDriver driver) {
		waitForElementVisible(driver, BasePageUI.CompareProductsSection.EMPTY_COMPARISON_LIST_INFO_MESSAGE);
		return getElementText(driver, BasePageUI.CompareProductsSection.EMPTY_COMPARISON_LIST_INFO_MESSAGE);
	}

	public String getComparisonListClearedSuccessMessage(WebDriver driver) {
		waitForElementVisible(driver, BasePageUI.MainContent.MESSAGE);
		return getElementText(driver, BasePageUI.MainContent.MESSAGE);
	}

	public void clickClearAllLinkCompareProductsSection(WebDriver driver) {
		waitForElementClickable(driver, BasePageUI.CompareProductsSection.CLEAR_ALL_LINK);
		clickElementByJS(driver, BasePageUI.CompareProductsSection.CLEAR_ALL_LINK);
	}

	/**
	 * Verifies if the specified product is not displayed in the "Compare Products"
	 * section.
	 *
	 * @param driver      the WebDriver instance used to interact with the web page.
	 * @param productName the name of the product to check in the "Compare Products"
	 *                    section.
	 * @return true if the product is not displayed in the "Compare Products"
	 *         section, otherwise false.
	 */
	public boolean isProductNotDisplayedInCompareProductsSection(WebDriver driver, String productName) {
		return isElementNotDisplayed(driver, BasePageUI.CompareProductsSection.DYNAMIC_CROSS_ICON_BY_PRODUCT_NAME,
				productName);
	}

	/**
	 * Clicks the "Compare Products" link in the header and navigates to the
	 * "Compare Products" page.
	 *
	 * @param driver the WebDriver instance used to interact with the web page.
	 * @return the CompareProductsPageObject after clicking the "Compare Products"
	 *         link.
	 */
	public CompareProductsPageObject clickCompareProductsLink(WebDriver driver) {
		waitForElementClickable(driver, BasePageUI.Header.COMPARE_PRODUCTS_LINK);
		clickElementByJS(driver, BasePageUI.Header.COMPARE_PRODUCTS_LINK);
		return PageGeneratorManager.getCompareProductsPage(driver);
	}

	/**
	 * Verifies if the "Compare Products" link is displayed in the header.
	 *
	 * @param driver the WebDriver instance used to interact with the web page.
	 * @return true if the "Compare Products" link is displayed, otherwise false.
	 */
	public boolean isCompareProductsLinkDisplayed(WebDriver driver) {
		String classValue = getElementAttribute(driver, BasePageUI.Header.COMPARE_PRODUCTS_LINK, "class");
		if (!classValue.contains("no-display")) {
			return true;
		}
		return false;
	}

	/**
	 * Clicks the "Compare" button in the "Compare Products" section and navigates
	 * to the "Compare Products" page.
	 *
	 * @param driver the WebDriver instance used to interact with the web page.
	 * @return the CompareProductsPageObject after clicking the "Compare" button.
	 */
	public CompareProductsPageObject clickCompareButton(WebDriver driver) {
		waitForElementClickable(driver, BasePageUI.CompareProductsSection.COMPARE_BUTTON);
		clickElementByJS(driver, BasePageUI.CompareProductsSection.COMPARE_BUTTON);
		return PageGeneratorManager.getCompareProductsPage(driver);
	}

	/**
	 * Verifies if the "Recent Orders" section is displayed on the My Account page.
	 *
	 * @param driver the WebDriver instance used to interact with the web page.
	 * @return true if the "Recent Orders" section is displayed, otherwise false.
	 */
	public boolean isRecentOrdersSectionDisplayed(WebDriver driver) {
		return isElementDisplayed(driver, MyAccountPageUI.RecentOrders.RECENT_ORDERS_HEADER);
	}

	/**
	 * Clicks a footer link by its label and navigates to the corresponding page.
	 *
	 * @param driver the WebDriver instance used to interact with the web page.
	 * @param label  the label of the footer link to click.
	 * @return the appropriate page object after clicking the footer link, which
	 *         could be: PopularSearchTermsPageObject, AdvancedSearchPageObject, or
	 *         OrdersAndReturnsPageObject.
	 * @throws RuntimeException if the label does not match any predefined footer
	 *                          links.
	 */
	public BasePage clickFooterLinkByLabel(WebDriver driver, String label) {
		waitForElementClickable(driver, BasePageUI.Footer.DYNAMIC_FOOTER_LINK, label);
		clickElementByJS(driver, BasePageUI.Footer.DYNAMIC_FOOTER_LINK, label);

		switch (label) {
		case "Search Terms":
			return PageGeneratorManager.getPopularSearchTermsPage(driver);
		case "Advanced Search":
			return PageGeneratorManager.getAdvancedSearchPage(driver);
		case "Orders and Returns":
			return PageGeneratorManager.getOrdersAndReturnsPage(driver);
		default:
			throw new RuntimeException("Page name is invalid");
		}

	}

	/**
	 * Clicks the "OK" button on the confirmation popup.
	 *
	 * @param driver the WebDriver instance used to interact with the page.
	 */
	public void clickOKButtonConfirmationPopup(WebDriver driver) {
		waitForElementClickable(driver, BasePageUI.General.CONFIRMATION_POPUP_OK_BUTTON);
		clickElementByJS(driver, BasePageUI.General.CONFIRMATION_POPUP_OK_BUTTON);
		sleepInSecond(GlobalConstants.SHORT_TIMEOUT);
	}

	/**
	 * Clicks the product link on the product listing page for the specified product
	 * name.
	 *
	 * @param driver      the WebDriver instance used to interact with the page.
	 * @param productName the name of the product for which the link is to be
	 *                    clicked.
	 * @return the ProductDetailsPageObject after clicking on the product link.
	 */
	public ProductDetailsPageObject clickProductLinkByProductName(WebDriver driver, String productName) {
		waitForElementClickable(driver, BasePageUI.ProductCard.DYNAMIC_PRODUCT_LINK_BY_PRODUCT_NAME, productName);
		clickElementByJS(driver, BasePageUI.ProductCard.DYNAMIC_PRODUCT_LINK_BY_PRODUCT_NAME, productName);
		return PageGeneratorManager.getProductDetailsPageObject(driver);
	}

	/**
	 * Clicks the "Add to Cart" button on the product listing page for the specified
	 * product name. If clicking redirects to a new page, returns
	 * ProductDetailsPageObject. Otherwise, stays on the same page and returns
	 * ProductListingPageObject.
	 *
	 * @param driver      the WebDriver instance used to interact with the page
	 * @param productName the name of the product to add to the cart
	 * @return the appropriate page object after clicking: ProductDetailsPageObject
	 *         if redirected, otherwise ProductListingPageObject
	 */
	public BasePage clickAddToCartButtonByProductName(WebDriver driver, String productName) {
		String currentUrl = getPageUrl(driver);
		scrollToElement(driver, BasePageUI.ProductCard.DYNAMIC_PRODUCT_LINK_BY_PRODUCT_NAME, productName);
		waitForElementVisible(driver, BasePageUI.ProductCard.DYNAMIC_PRODUCT_LINK_BY_PRODUCT_NAME, productName);
		hoverOverElement(driver, BasePageUI.ProductCard.DYNAMIC_PRODUCT_LINK_BY_PRODUCT_NAME, productName);
		waitForElementClickable(driver, BasePageUI.ProductCard.DYNAMIC_ADD_TO_CART_BUTTON_BY_PRODUCT_NAME, productName);
		clickElementByJS(driver, BasePageUI.ProductCard.DYNAMIC_ADD_TO_CART_BUTTON_BY_PRODUCT_NAME, productName);
		sleepInSecond(GlobalConstants.SHORT_TIMEOUT);

		if (!currentUrl.equals(getPageUrl(driver))) {
			return PageGeneratorManager.getProductDetailsPageObject(driver);
		}
		return PageGeneratorManager.getProductListingPageObject(driver);
	}

	/**
	 * Clicks the size button for the specified product name and size label.
	 *
	 * @param driver      the WebDriver instance used to interact with the page.
	 * @param productName the name of the product for which the size button is to be
	 *                    clicked.
	 * @param sizeLabel   the size label.
	 */
	public void clickSizeButtonByProductNameAndLabel(WebDriver driver, String productName, String sizeLabel) {
		waitForElementClickable(driver, BasePageUI.ProductCard.DYNAMIC_SIZE_BUTTON_BY_PRODUCT_NAME_AND_LABEL,
				productName, sizeLabel);
		clickElementByJS(driver, BasePageUI.ProductCard.DYNAMIC_SIZE_BUTTON_BY_PRODUCT_NAME_AND_LABEL, productName,
				sizeLabel);
		sleepInSecond(GlobalConstants.SHORT_TIMEOUT);
	}

	/**
	 * Clicks the color button for the specified product name and color label.
	 *
	 * @param driver      the WebDriver instance used to interact with the page.
	 * @param productName the name of the product for which the color button is to
	 *                    be clicked.
	 * @param colorLabel  the color label.
	 */
	public void clickColorButtonByProductNameAndLabel(WebDriver driver, String productName, String colorLabel) {
		waitForElementClickable(driver, BasePageUI.ProductCard.DYNAMIC_COLOR_BUTTON_BY_PRODUCT_NAME_AND_LABEL,
				productName, colorLabel);
		clickElementByJS(driver, BasePageUI.ProductCard.DYNAMIC_COLOR_BUTTON_BY_PRODUCT_NAME_AND_LABEL, productName,
				colorLabel);
		sleepInSecond(GlobalConstants.SHORT_TIMEOUT);
	}

	/**
	 * Clicks the wish list icon for the specified product name.
	 *
	 * @param driver      the WebDriver instance used to interact with the page.
	 * @param productName the product name for which the wish list icon is to be
	 *                    clicked.
	 * @return the MyWishListPageObject after clicking the wish list icon.
	 */
	public MyWishListPageObject clickWishListIconByProductName(WebDriver driver, String productName) {
		scrollToElement(driver, BasePageUI.ProductCard.DYNAMIC_PRODUCT_LINK_BY_PRODUCT_NAME, productName);
		waitForElementVisible(driver, BasePageUI.ProductCard.DYNAMIC_PRODUCT_LINK_BY_PRODUCT_NAME, productName);
		hoverOverElement(driver, BasePageUI.ProductCard.DYNAMIC_PRODUCT_LINK_BY_PRODUCT_NAME, productName);
		waitForElementClickable(driver, BasePageUI.ProductCard.DYNAMIC_WISH_LIST_ICON_BY_PRODUCT_NAME, productName);
		clickElementByJS(driver, BasePageUI.ProductCard.DYNAMIC_WISH_LIST_ICON_BY_PRODUCT_NAME, productName);
		return PageGeneratorManager.getMyWishlistPage(driver);
	}

	/**
	 * Clicks the compare icon for the specified product name.
	 *
	 * @param driver      the WebDriver instance used to interact with the page.
	 * @param productName the product name for which the compare icon is to be
	 *                    clicked.
	 * @return the MyWishListPageObject after clicking the wish list icon.
	 */
	public void clickCompareIconByProductName(WebDriver driver, String productName) {
		scrollToElement(driver, BasePageUI.ProductCard.DYNAMIC_PRODUCT_LINK_BY_PRODUCT_NAME, productName);
		waitForElementVisible(driver, BasePageUI.ProductCard.DYNAMIC_PRODUCT_LINK_BY_PRODUCT_NAME, productName);
		hoverOverElement(driver, BasePageUI.ProductCard.DYNAMIC_PRODUCT_LINK_BY_PRODUCT_NAME, productName);
		waitForElementClickable(driver, BasePageUI.ProductCard.DYNAMIC_COMPARE_ICON_BY_PRODUCT_NAME, productName);
		clickElementByJS(driver, BasePageUI.ProductCard.DYNAMIC_COMPARE_ICON_BY_PRODUCT_NAME, productName);
	}

	public ProductDetailsPageObject clickReviewsLinkByProductName(WebDriver driver, String productName) {
		waitForElementClickable(driver, BasePageUI.ProductCard.DYNAMIC_REVIEWS_LINK_BY_PRODUCT_NAME, productName);
		clickElementByJS(driver, BasePageUI.ProductCard.DYNAMIC_REVIEWS_LINK_BY_PRODUCT_NAME, productName);
		return PageGeneratorManager.getProductDetailsPageObject(driver);
	}

	public void addProductWithOptionsToCart(WebDriver driver, String productName, String sizeLabel, String colorLabel) {
		scrollToElement(driver, BasePageUI.ProductCard.DYNAMIC_PRODUCT_LINK_BY_PRODUCT_NAME, productName);
		clickSizeButtonByProductNameAndLabel(driver, productName, sizeLabel);
		sleepInSecond(1);
		clickColorButtonByProductNameAndLabel(driver, productName, colorLabel);
		sleepInSecond(1);
		clickAddToCartButtonByProductName(driver, productName);
	}

	public void addProductWithoutOptionsToCart(WebDriver driver, String productName) {
		clickAddToCartButtonByProductName(driver, productName);
	}

	public String getProductAddedToCartSuccessMessage(WebDriver driver) {
		waitForElementVisible(driver, BasePageUI.MainContent.MESSAGE);
		return getElementText(driver, BasePageUI.MainContent.MESSAGE);
	}

	/**
	 * Clicks the next page button on the product listing page to navigate to the
	 * next page.
	 *
	 * @param driver the WebDriver instance used to interact with the web page.
	 */
	public void clickNextPageButton(WebDriver driver) {
		waitForElementClickable(driver, ProductListingPageUI.NEXT_PAGE_BUTTON);
		clickElementByJS(driver, ProductListingPageUI.NEXT_PAGE_BUTTON);
	}

	/**
	 * Selects the specified product count option from the limiter dropdown on the
	 * product listing page.
	 *
	 * @param driver       the WebDriver instance used to interact with the web
	 *                     page.
	 * @param productCount the product count to be selected from the dropdown.
	 */
	public void selectOptionLimiterDropdown(WebDriver driver, String productCount) {
		scrollToBottom(driver);
		waitForElementVisible(driver, ProductListingPageUI.LIMITER_DROPDOWN);
		selectOptionDefaultDropdown(driver, ProductListingPageUI.LIMITER_DROPDOWN, productCount);
		waitForAllElementsVisible(driver, ProductListingPageUI.PRODUCT_NAME);
	}

	/**
	 * Clicks the "Save Address" button and navigates to the appropriate page based
	 * on the current URL.
	 *
	 * @param driver the WebDriver instance used to interact with the web page.
	 * @return the appropriate page object after saving the address, which could be:
	 *         AddressBookPageObject, ShipToMultipleAddressesPageObject,
	 *         SelectShippingMethodPageObject, or ChangeBillingAddressPageObject.
	 */
	public BasePage clickSaveAddressButton(WebDriver driver) {
		String pageUrl = getPageUrl(driver);
		scrollToBottom(driver);
		waitForElementClickable(driver, AddressPageUI.SAVE_ADDRESS_BUTTON);
		clickElementByJS(driver, AddressPageUI.SAVE_ADDRESS_BUTTON);
		if (pageUrl.contains("/customer/address/edit")) {
			return PageGeneratorManager.getAddressBookPage(driver);
		} else if (pageUrl.contains("/multishipping/checkout_address/newShipping/")) {
			return PageGeneratorManager.getShipToMultipleAddressesPage(driver);
		} else if (pageUrl.contains("/multishipping/checkout_address/editShipping/")) {
			return PageGeneratorManager.getSelectShippingMethodPage(driver);
		} else if (pageUrl.contains("multishipping/checkout_address/editAddress")
				|| pageUrl.contains("multishipping/checkout_address/newBilling")) {
			return PageGeneratorManager.getChangeBillingAddressPage(driver);
		}
		return null;
	}

	/**
	 * Navigates to the homepage by clicking the Luma logo.
	 *
	 * @param driver the WebDriver instance used to interact with the web page.
	 * @return the HomepageObject after navigating to the homepage.
	 */
	public HomepageObject navigateToHomepage(WebDriver driver) {
		try {
			clickLumaLogo(driver);
			return PageGeneratorManager.getHomepage(driver);
		} catch (Exception e) {
			System.err.println("Error logging out: " + e.getMessage());
			e.printStackTrace();
		}
		return PageGeneratorManager.getHomepage(driver);
	}

	/**
	 * Logs out by clicking the customer name dropdown, followed by the "Sign Out"
	 * dropdown link.
	 *
	 * <p>
	 * This method is typically used as a setup or cleanup step in test execution
	 * (e.g., in @BeforeMethod or @AfterMethod).
	 * </p>
	 *
	 * @param driver the WebDriver instance used to interact with the web page.
	 * @return the HomepageObject after logging out.
	 */
	public HomepageObject logOut(WebDriver driver) {
		try {
			clickCustomerNameDropdown(driver);
			clickSignOutDropdownLink(driver);
		} catch (Exception e) {
			System.err.println("Error logging out: " + e.getMessage());
			e.printStackTrace();
		}
		return PageGeneratorManager.getHomepage(driver);
	}

	/**
	 * Navigates to the product listing page of the category under the Gear section
	 * and adds a specified product to the wish list.
	 * 
	 * <p>
	 * This method is typically used as a setup or cleanup step in test execution
	 * (e.g., in @BeforeMethod or @AfterMethod).
	 * </p>
	 * 
	 * @param driver      the WebDriver instance used to interact with the web page.
	 * @param category    the name of the category under the main category.
	 * @param productName the name of the product to be added to the wish list.
	 */
	public void addProductWithoutOptionsToWishList(WebDriver driver, String category, String productName) {
		try {
			clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear", category);
			clickWishListIconByProductName(driver, productName);
		} catch (Exception e) {
			System.err.println("Error adding product to wish list: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Navigates to the product listing page of the sub-category under the specified
	 * section and adds a specified product to the wish list.
	 * 
	 * *
	 * <p>
	 * This method is typically used as a setup or cleanup step in test execution
	 * (e.g., in @BeforeMethod or @AfterMethod).
	 * </p>
	 * 
	 * @param driver      the WebDriver instance used to interact with the web page.
	 * @param group       the name of the main category in the navigation menu.
	 * @param category    the name of the category under the main category.
	 * @param subcategory the name of the sub-category under the category.
	 * @param productName the name of the product to be added to the wish list.
	 */
	public void addProductWithOptionsToWishList(WebDriver driver, String group, String category, String subcategory,
			String productName) {
		try {
			clickNavigationBarDropdownMultiLevelItemLinkByLabels(driver, group, category, subcategory);
			clickWishListIconByProductName(driver, productName);
		} catch (Exception e) {
			System.err.println("Error adding product to wish list: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Navigates to the wish list and clear all products.
	 * 
	 * <p>
	 * This method is typically used as a setup or cleanup step in test execution
	 * (e.g., in @BeforeMethod or @AfterMethod).
	 * </p>
	 *
	 * @param driver the WebDriver instance used to interact with the web page.
	 * @throws Exception if an error occurs while clearing the wish list.
	 */
	public void clearWishList(WebDriver driver) {
		try {
			clickCustomerNameDropdown(driver);
			clickMyWishListDropdownLink(driver);
			while (true) {
				List<WebElement> trashcanIcons = getWebElements(driver, MyWishListPageUI.TRASHCAN_ICON);
				if (trashcanIcons.isEmpty()) {
					break;
				}
				hoverOverElement(driver, MyWishListPageUI.FIRST_PRODUCT_CARD);
				clickElementByJS(driver, MyWishListPageUI.TRASHCAN_ICON);
				waitForElementVisible(driver, BasePageUI.MainContent.PAGE_HEADER);
			}
		} catch (Exception e) {
			System.err.println("Error clearing wish list: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void addProductWithoutOptionsToComparisonList(WebDriver driver) {
		try {
			clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear", "Watches");
			clickCompareIconByProductName(driver, "Didi Sport Watch");
			clickCompareProductsLink(driver);
			waitForElementVisible(driver, CompareProductsPageUI.DYNAMIC_PRODUCT_LINK_BY_PRODUCT_NAME,
					"Didi Sport Watch");
		} catch (Exception e) {
			System.err.println("Error adding " + "Didi Sport Watch" + " to comparison list: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Navigates to the comparison list and clears all products.
	 * 
	 * <p>
	 * This method is typically used as a setup or cleanup step in test execution
	 * (e.g., in @BeforeMethod or @AfterMethod).
	 * </p>
	 *
	 * @param driver the WebDriver instance used to interact with the web page.
	 * @throws Exception if an error occurs while clearing the comparison list.
	 */
	public void clearComparisonList(WebDriver driver) {
		try {
			clickCompareProductsLink(driver);
			while (true) {
				List<WebElement> crossIcons = getWebElements(driver, CompareProductsPageUI.CROSS_ICON);
				if (crossIcons.isEmpty()) {
					break;
				}
				crossIcons.get(0).click();
				clickOKButtonConfirmationPopup(driver);
				waitForElementVisible(driver, BasePageUI.MainContent.PAGE_HEADER);
			}
			sleepInSecond(GlobalConstants.SHORT_TIMEOUT);
		} catch (Exception e) {
			System.err.println("Error clearing comparison list: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Navigates to the product listing page of the category under the Gear section
	 * and adds a specified product to the shopping cart.
	 * 
	 * <p>
	 * This method is typically used as a setup or cleanup step in test execution
	 * (e.g., in @BeforeMethod or @AfterMethod).
	 * </p>
	 *
	 * @param driver      the WebDriver instance used to interact with the web page.
	 * @param category    the category under the Gear section.
	 * @param productName the name of the product to be added to the shopping cart.
	 * @throws Exception if an error occurs while adding the product to the cart.
	 */
	public void addProductWithoutOptionsToCart(WebDriver driver, String category, String productName) {
		try {
			clickNavigationBarDropdownSingleLevelItemLinkByLabels(driver, "Gear", category);
			clickAddToCartButtonByProductName(driver, productName);
			clickLumaLogo(driver);
		} catch (Exception e) {
			System.err.println("Error adding the product " + productName + " to the shopping cart: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Navigates to the shopping cart page and clears all items from the shopping
	 * cart.
	 *
	 *
	 * <p>
	 * This method is typically used as a setup or cleanup step in test execution
	 * (e.g., in @BeforeMethod or @AfterMethod).
	 * </p>
	 * 
	 * @param driver the WebDriver instance used to interact with the web page.
	 * @throws Exception if an error occurs while clearing the cart.
	 */
	public void clearShoppingCart(WebDriver driver) {
		try {
			clickLumaLogo(driver);
			clickShoppingCartIcon(driver);
			clickViewAndEditCartLink(driver);
			while (true) {
				List<WebElement> trashcanIcons = getWebElements(driver, ShoppingCartPageUI.TRASHCAN_ICON);
				if (trashcanIcons.isEmpty()) {
					break;
				}
				trashcanIcons.get(0).click();
				waitForElementVisible(driver, BasePageUI.MainContent.PAGE_HEADER);
			}
			sleepInSecond(GlobalConstants.SHORT_TIMEOUT);
			clickLumaLogo(driver);
		} catch (Exception e) {
			System.err.println("Error clearing shopping cart: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
