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
import pageUIs.BasePageUI;
import pageUIs.MyAccountPageUI;
import pageUIs.ProductListingPageUI;

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
		element.clear();
		element.sendKeys(text);
	}

	public void sendKeysToElement(WebDriver driver, String locatorType, String text, String... dynamicValues) {
		WebElement element = getWebElement(driver, getDynamicXpath(locatorType, dynamicValues));
		element.clear();
		element.sendKeys(text);
	}

	public void clearText(WebDriver driver, String locatorType) {
		WebElement element = getWebElement(driver, locatorType);
		element.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
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
		select.selectByVisibleText(optionLabel);
	}

	public void selectOptionDefaultDropdown(WebDriver driver, String locatorType, String optionLabel,
			String... dynamicValues) {
		Select select = new Select(getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)));
		select.selectByVisibleText(optionLabel);
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
		List<String> list = new ArrayList<String>();
		List<WebElement> elements = getWebElements(driver, locator);

		for (WebElement element : elements) {
			list.add(element.getText());
		}

		List<String> sortedList = new ArrayList<String>();

		for (String element : list) {
			sortedList.add(element);
		}

		Collections.sort(sortedList);
		return list.equals(sortedList);
	}

	public boolean isDataSortedDescending(WebDriver driver, String locator) {
		List<String> list = new ArrayList<String>();
		List<WebElement> elements = getWebElements(driver, locator);

		for (WebElement element : elements) {
			list.add(element.getText());
		}

		List<String> sortedList = new ArrayList<String>();

		for (String element : list) {
			sortedList.add(element);
		}

		Collections.reverse(sortedList);
		return list.equals(sortedList);
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

	public HomepageObject clickLumaLogo(WebDriver driver) {
		waitForElementClickable(driver, BasePageUI.Header.LUMA_LOGO);
		clickElementByJS(driver, BasePageUI.Header.LUMA_LOGO);
		return PageGeneratorManager.getHomepage(driver);
	}

	public String getPageHeader(WebDriver driver) {
		waitForElementVisible(driver, BasePageUI.MainContent.PAGE_HEADER);
		return getElementText(driver, BasePageUI.MainContent.PAGE_HEADER);
	}

	public String getSearchBarPlaceholder(WebDriver driver) {
		waitForElementVisible(driver, BasePageUI.Header.SEARCH_BAR);
		return getElementAttribute(driver, BasePageUI.Header.SEARCH_BAR, "placeholder");
	}

	public void sendKeysToSearchBar(WebDriver driver, String searchValue) {
		waitForElementVisible(driver, BasePageUI.Header.SEARCH_BAR);
		sendKeysToElement(driver, BasePageUI.Header.SEARCH_BAR, searchValue);
	}

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

	public ProductListingPageObject sendKeysToSearchBarAndPressEnter(WebDriver driver, String searchValue) {
		waitForElementVisible(driver, BasePageUI.Header.SEARCH_BAR);
		sendKeysToElement(driver, BasePageUI.Header.SEARCH_BAR, searchValue);
		pressKeyOnElement(driver, BasePageUI.Header.SEARCH_BAR, Keys.ENTER);
		return PageGeneratorManager.getProductListingPageObject(driver);
	}

	public void clickShoppingCartIcon(WebDriver driver) {
		waitForElementClickable(driver, BasePageUI.Header.MiniCart.SHOPPING_CART_ICON);
		clickElementByJS(driver, BasePageUI.Header.MiniCart.SHOPPING_CART_ICON);
	}

	public Float getMiniCartSubtotal(WebDriver driver) {
		waitForElementVisible(driver, BasePageUI.Header.MiniCart.CART_SUBTOTAL);
		return Float.parseFloat(getElementText(driver, BasePageUI.Header.MiniCart.CART_SUBTOTAL).replace("$", ""));
	}

	public String getMiniCartQuantity(WebDriver driver) {
		waitForElementVisible(driver, BasePageUI.Header.MiniCart.MINI_CART_QUANTITY);
		return getElementText(driver, BasePageUI.Header.MiniCart.MINI_CART_QUANTITY);
	}

	public CheckoutPageObject clickProceedToCheckoutButton(WebDriver driver) {
		waitForElementClickable(driver, BasePageUI.Header.MiniCart.PROCEED_TO_CHECKOUT_BUTTON);
		clickElementByJS(driver, BasePageUI.Header.MiniCart.PROCEED_TO_CHECKOUT_BUTTON);
		return PageGeneratorManager.getCheckoutPageObject(driver);
	}

	public void clickMiniCartCrossIcon(WebDriver driver) {
		waitForElementClickable(driver, BasePageUI.Header.MiniCart.MINI_CART_CROSS_ICON);
		clickElementByJS(driver, BasePageUI.Header.MiniCart.MINI_CART_CROSS_ICON);
	}

	public boolean isMiniCartNotDisplayed(WebDriver driver) {
		return isElementNotDisplayed(driver, BasePageUI.Header.MiniCart.MINI_CART_MODAL);
	}

	public String getEmptyShoppingCartInfoMessage(WebDriver driver) {
		waitForElementVisible(driver, BasePageUI.Header.MiniCart.MINI_CART_EMPTY_SHOPPING_CART_INFO_MESSAGE);
		return getElementText(driver, BasePageUI.Header.MiniCart.MINI_CART_EMPTY_SHOPPING_CART_INFO_MESSAGE);
	}

	public void clickSeeDetailsCollapsibleHeaderByProductName(WebDriver driver, String productName) {
		waitForElementClickable(driver,
				BasePageUI.Header.MiniCart.DYNAMIC_SEE_DETAILS_COLLAPSIBLE_HEADER_BY_PRODUCT_NAME, productName);
		clickElementByJS(driver, BasePageUI.Header.MiniCart.DYNAMIC_SEE_DETAILS_COLLAPSIBLE_HEADER_BY_PRODUCT_NAME,
				productName);
	}

	public String getSizeValueByProductName(WebDriver driver, String productName) {
		waitForElementVisible(driver, BasePageUI.Header.MiniCart.DYNAMIC_SIZE_VALUE_BY_PRODUCT_NAME, productName);
		return getElementText(driver, BasePageUI.Header.MiniCart.DYNAMIC_SIZE_VALUE_BY_PRODUCT_NAME, productName);
	}

	public String getColorValueByProductName(WebDriver driver, String productName) {
		waitForElementVisible(driver, BasePageUI.Header.MiniCart.DYNAMIC_COLOR_VALUE_BY_PRODUCT_NAME, productName);
		return getElementText(driver, BasePageUI.Header.MiniCart.DYNAMIC_COLOR_VALUE_BY_PRODUCT_NAME, productName);
	}

	public void sendKeysToQuantityTextboxByProductName(WebDriver driver, String quantity, String productName) {
		waitForElementVisible(driver, BasePageUI.Header.MiniCart.DYNAMIC_QUANTITY_TEXTBOX_BY_PRODUCT_NAME, productName);
		sendKeysToElement(driver, BasePageUI.Header.MiniCart.DYNAMIC_QUANTITY_TEXTBOX_BY_PRODUCT_NAME, quantity,
				productName);
	}

	public String getQuantityValueByProductName(WebDriver driver, String productName) {
		waitForElementVisible(driver, BasePageUI.Header.MiniCart.DYNAMIC_QUANTITY_TEXTBOX_BY_PRODUCT_NAME, productName);
		return getElementValueByJS(driver, BasePageUI.Header.MiniCart.DYNAMIC_QUANTITY_TEXTBOX_BY_PRODUCT_NAME,
				productName);
	}

	public void clickUpdateButton(WebDriver driver) {
		waitForElementClickable(driver, BasePageUI.Header.MiniCart.UPDATE_BUTTON);
		clickElementByJS(driver, BasePageUI.Header.MiniCart.UPDATE_BUTTON);
		sleepInSecond(GlobalConstants.SHORT_TIMEOUT);
	}

	public ProductDetailsPageObject clickPenIconByProductName(WebDriver driver, String productName) {
		waitForElementClickable(driver, BasePageUI.Header.MiniCart.DYNAMIC_PEN_ICON_BY_PRODUCT_NAME, productName);
		clickElementByJS(driver, BasePageUI.Header.MiniCart.DYNAMIC_PEN_ICON_BY_PRODUCT_NAME, productName);
		return PageGeneratorManager.getProductDetailsPageObject(driver);
	}

	public void clickTrashcanIconByProductName(WebDriver driver, String productName) {
		waitForElementClickable(driver, BasePageUI.Header.MiniCart.DYNAMIC_TRASHCAN_ICON_BY_PRODUCT_NAME, productName);
		clickElementByJS(driver, BasePageUI.Header.MiniCart.DYNAMIC_TRASHCAN_ICON_BY_PRODUCT_NAME, productName);
	}

	public boolean isProductNotDisplayedInMiniCart(WebDriver driver, String productName) {
		return isElementNotDisplayed(driver, BasePageUI.Header.MiniCart.DYNAMIC_PRODUCT_LINK_BY_NAME, productName);
	}

	public ShoppingCartPageObject clickViewAndEditCartLink(WebDriver driver) {
		waitForElementClickable(driver, BasePageUI.Header.MiniCart.VIEW_AND_EDIT_CART_LINK);
		clickElementByJS(driver, BasePageUI.Header.MiniCart.VIEW_AND_EDIT_CART_LINK);
		return PageGeneratorManager.getShoppingCartPageObject(driver);
	}

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
		return PageGeneratorManager.getProductListingPageObject(driver);
	}

	public ProductListingPageObject clickNavigationBarDropdownSingleLevelItemLinkByLabels(WebDriver driver,
			String dropdownLabel, String label) {
		waitForElementVisible(driver, BasePageUI.Header.DYNAMIC_NAVIGATION_BAR_DROPDOWN_BY_LABEL, dropdownLabel);
		hoverOverElement(driver, BasePageUI.Header.DYNAMIC_NAVIGATION_BAR_DROPDOWN_BY_LABEL, dropdownLabel);
		waitForElementClickable(driver, BasePageUI.Header.DYNAMIC_NAVIGATION_BAR_DROPDOWN_SINGLE_LEVEL_ITEM_BY_LABEL,
				label);
		clickElementByJS(driver, BasePageUI.Header.DYNAMIC_NAVIGATION_BAR_DROPDOWN_SINGLE_LEVEL_ITEM_BY_LABEL, label);
		return PageGeneratorManager.getProductListingPageObject(driver);
	}

	public void clickCustomerNameDropdown(WebDriver driver) {
		waitForElementClickable(driver, BasePageUI.Header.ACCOUNT_NAME_DROPDOWN);
		clickElementByJS(driver, BasePageUI.Header.ACCOUNT_NAME_DROPDOWN);
	}

	public MyAccountPageObject clickMyAccountDropdownLink(WebDriver driver) {
		waitForElementClickable(driver, BasePageUI.Header.MY_ACCOUNT_DROPDOWN_LINK);
		clickElementByJS(driver, BasePageUI.Header.MY_ACCOUNT_DROPDOWN_LINK);
		return PageGeneratorManager.getMyAccountPage(driver);
	}

	public MyWishListPageObject clickMyWishListDropdownLink(WebDriver driver) {
		waitForElementClickable(driver, BasePageUI.Header.MY_WISH_LIST_DROPDOWN_LINK);
		clickElementByJS(driver, BasePageUI.Header.MY_WISH_LIST_DROPDOWN_LINK);
		return PageGeneratorManager.getMyWishlistPage(driver);
	}

	public HomepageObject clickSignOutDropdownLink(WebDriver driver) {
		waitForElementClickable(driver, BasePageUI.Header.SIGN_OUT_DROPDOWN_LINK);
		clickElementByJS(driver, BasePageUI.Header.SIGN_OUT_DROPDOWN_LINK);
		return PageGeneratorManager.getHomepage(driver);
	}

	public boolean isWelcomeMessageDisplayed(WebDriver driver) {
		return isElementDisplayed(driver, BasePageUI.Header.WELCOME_MESSAGE);
	}

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

	public String getEmptyWishListInfoMessage(WebDriver driver) {
		waitForElementVisible(driver, BasePageUI.MyWishListSection.EMPTY_WISH_LIST_INFO_MESSAGE);
		return getElementText(driver, BasePageUI.MyWishListSection.EMPTY_WISH_LIST_INFO_MESSAGE);
	}

	public void clickAddToCartButtonByProductNameWithoutOptions(WebDriver driver, String productName) {
		waitForElementClickable(driver, BasePageUI.MyWishListSection.DYNAMIC_ADD_TO_CART_BUTTON_BY_PRODUCT_NAME,
				productName);
		clickElementByJS(driver, BasePageUI.MyWishListSection.DYNAMIC_ADD_TO_CART_BUTTON_BY_PRODUCT_NAME, productName);
	}

	public ProductDetailsPageObject clickAddToCartButtonByProductNameWithOptions(WebDriver driver, String productName) {
		waitForElementClickable(driver, BasePageUI.MyWishListSection.DYNAMIC_ADD_TO_CART_BUTTON_BY_PRODUCT_NAME,
				productName);
		clickElementByJS(driver, BasePageUI.MyWishListSection.DYNAMIC_ADD_TO_CART_BUTTON_BY_PRODUCT_NAME, productName);
		return PageGeneratorManager.getProductDetailsPageObject(driver);
	}

	public void clickCrossIconByProductName(WebDriver driver, String productName) {
		waitForElementClickable(driver, BasePageUI.MyWishListSection.DYNAMIC_CROSS_ICON_BY_PRODUCT_NAME, productName);
		clickElementByJS(driver, BasePageUI.MyWishListSection.DYNAMIC_CROSS_ICON_BY_PRODUCT_NAME, productName);
	}

	public boolean isProductNotDisplayedInMyWishListSection(WebDriver driver, String productName) {
		return isElementNotDisplayed(driver, BasePageUI.MyWishListSection.DYNAMIC_PRODUCT_LINK_BY_NAME, productName);
	}

	public MyWishListPageObject clickGoToWishListLink(WebDriver driver) {
		waitForElementClickable(driver, BasePageUI.MyWishListSection.GO_TO_WISH_LIST_LINK);
		clickElementByJS(driver, BasePageUI.MyWishListSection.GO_TO_WISH_LIST_LINK);
		return PageGeneratorManager.getMyWishlistPage(driver);
	}

	public String getEmptyComparisonListInfoMessage(WebDriver driver) {
		waitForElementVisible(driver, BasePageUI.CompareProductsSection.EMPTY_COMPARISON_LIST_INFO_MESSAGE);
		return getElementText(driver, BasePageUI.CompareProductsSection.EMPTY_COMPARISON_LIST_INFO_MESSAGE);
	}

	public CompareProductsPageObject clickCompareProductsLink(WebDriver driver) {
		waitForElementClickable(driver, BasePageUI.Header.COMPARE_PRODUCTS_LINK);
		clickElementByJS(driver, BasePageUI.Header.COMPARE_PRODUCTS_LINK);
		return PageGeneratorManager.getCompareProductsPage(driver);
	}

	public boolean isCompareProductsLinkDisplayed(WebDriver driver) {
		return isElementDisplayed(driver, BasePageUI.Header.COMPARE_PRODUCTS_LINK);
	}

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

	public void clickConfirmationPopupOKButton(WebDriver driver) {
		waitForElementClickable(driver, BasePageUI.General.CONFIRMATION_POPUP_OK_BUTTON);
		clickElementByJS(driver, BasePageUI.General.CONFIRMATION_POPUP_OK_BUTTON);
	}

	public ProductDetailsPageObject clickProductLinkByProductName(WebDriver driver, String productName) {
		waitForElementClickable(driver, BasePageUI.ProductCard.DYNAMIC_PRODUCT_LINK_BY_PRODUCT_NAME, productName);
		clickElementByJS(driver, BasePageUI.ProductCard.DYNAMIC_PRODUCT_LINK_BY_PRODUCT_NAME, productName);
		return PageGeneratorManager.getProductDetailsPageObject(driver);
	}

	public void clickAddToCartButtonByProductName(WebDriver driver, String productName) {
		waitForElementVisible(driver, BasePageUI.ProductCard.DYNAMIC_PRODUCT_LINK_BY_PRODUCT_NAME, productName);
		hoverOverElement(driver, BasePageUI.ProductCard.DYNAMIC_PRODUCT_LINK_BY_PRODUCT_NAME, productName);
		waitForElementClickable(driver, BasePageUI.ProductCard.DYNAMIC_ADD_TO_CART_BUTTON_BY_PRODUCT_NAME, productName);
		clickElementByJS(driver, BasePageUI.ProductCard.DYNAMIC_ADD_TO_CART_BUTTON_BY_PRODUCT_NAME, productName);
		sleepInSecond(GlobalConstants.SHORT_TIMEOUT);
	}

	public void clickSizeButtonByProductNameAndLabel(WebDriver driver, String productName, String sizeLabel) {
		waitForElementClickable(driver, BasePageUI.ProductCard.DYNAMIC_SIZE_BUTTON_BY_PRODUCT_NAME_AND_LABEL,
				productName, sizeLabel);
		clickElementByJS(driver, BasePageUI.ProductCard.DYNAMIC_SIZE_BUTTON_BY_PRODUCT_NAME_AND_LABEL, productName,
				sizeLabel);
	}

	public void clickColorButtonByProductNameAndLabel(WebDriver driver, String productName, String colorLabel) {
		waitForElementClickable(driver, BasePageUI.ProductCard.DYNAMIC_COLOR_BUTTON_BY_PRODUCT_NAME_AND_LABEL,
				productName, colorLabel);
		clickElementByJS(driver, BasePageUI.ProductCard.DYNAMIC_COLOR_BUTTON_BY_PRODUCT_NAME_AND_LABEL, productName,
				colorLabel);
	}

	public MyWishListPageObject clickWishListIconByProductName(WebDriver driver, String productName) {
		scrollToElement(driver, BasePageUI.ProductCard.DYNAMIC_PRODUCT_LINK_BY_PRODUCT_NAME, productName);
		waitForElementVisible(driver, BasePageUI.ProductCard.DYNAMIC_PRODUCT_LINK_BY_PRODUCT_NAME, productName);
		hoverOverElement(driver, BasePageUI.ProductCard.DYNAMIC_PRODUCT_LINK_BY_PRODUCT_NAME, productName);
		waitForElementClickable(driver, BasePageUI.ProductCard.DYNAMIC_WISH_LIST_ICON_BY_PRODUCT_NAME, productName);
		clickElementByJS(driver, BasePageUI.ProductCard.DYNAMIC_WISH_LIST_ICON_BY_PRODUCT_NAME, productName);
		return PageGeneratorManager.getMyWishlistPage(driver);
	}

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
		clickSizeButtonByProductNameAndLabel(driver, productName, sizeLabel);
		clickColorButtonByProductNameAndLabel(driver, productName, colorLabel);
		clickAddToCartButtonByProductName(driver, productName);
	}

	public void addProductWithNoOptionsToCart(WebDriver driver, String productName) {
		clickAddToCartButtonByProductName(driver, productName);
	}

}
