package base;//import java.util.*;

import com.google.common.io.Files;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PredefinedActions {
    private static WebDriver driver;
    private static WebDriverWait wait;
    private static Actions actions;
    static Logger log = Logger.getLogger(PredefinedActions.class);

    public static void initializeBrowser(String url, String browser) {
        switch (browser.toUpperCase()) {
            case "CHROME":
                ChromeOptions options = new ChromeOptions();
                driver = new ChromeDriver(options);
                break;
            case "FIREFOX":
                driver = new FirefoxDriver();
                break;
            case "EDGE":
                driver = new EdgeDriver();
            default:
                System.out.println("Illegal Browser Name");
                break;
        }
        driver.manage().window().maximize();
        log.trace("User able to open the browser");
        driver.get(url);
        log.trace("User able to open the " + url);
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    protected void editCookies(String cookieName, String cookieValue) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        driver.manage().addCookie(cookie);
    }

    public static String getCurrentUrlInString() {
        return driver.getCurrentUrl();
    }

    public static boolean isUrlAsExpected(String expectedUrl) {
        return wait.until(ExpectedConditions.urlContains(expectedUrl));
    }

    protected Cookie getCookieByName(String cookieName) {
        return driver.manage().getCookieNamed(cookieName);
    }

    protected void deleteSpecificCookie(String cookieName) {
        try {
            driver.manage().deleteCookieNamed(cookieName);
        } catch (NullPointerException ne) {
            log.trace("Not able to delete cookies");
        }
    }

    public void deleteAllCookies() {
        driver.manage().deleteAllCookies();
    }

    protected Set<Cookie> getAllCookie() {
        return driver.manage().getCookies();
    }

    public static void refreshTab() {
        driver.get(driver.getCurrentUrl());
    }


    public static void navigateToUrl(String url) {
        driver.navigate().to(url);
    }

    protected void selectElementByValue(WebElement element, String selectOptionValue) {
        Select select = new Select(element);
        select.selectByValue(selectOptionValue);
        log.trace("User is able to select the element");
    }

    protected void selectElementByVisibleText(WebElement element, String selectOptionVisibleText) {
        Select select = new Select(element);
        select.selectByVisibleText(selectOptionVisibleText);
        log.trace("User is able to select the element");
    }

    protected void hoverOnElement(WebElement target, boolean isWaitRequired) {
        actions = new Actions(driver);
        if (isWaitRequired) {
            wait.until(ExpectedConditions.visibilityOf(target));
            actions.moveToElement(target).build().perform();
        } else {
            actions.moveToElement(target).build().perform();
        }
        log.trace("User able to Hover on element");
    }

    protected String getWindowHandle() {
        return driver.getWindowHandle();
    }

    protected Set<String> getWindowHandles() {
        return driver.getWindowHandles();
    }

    protected void switchToWindow(String windowId) {
        driver.switchTo().window(windowId);
    }

    protected List<WebElement> getWebElementList(String locator, boolean isWaitRequired) {
        String locatorType = getLocatorType(locator);
        String locatorValue = getLocatorValue(locator);
        log.trace("User is trying to get the list of WebElement");
        return driver.findElements(getByReference(locatorType, locatorValue));
    }

    protected List<String> getWebElementListInString(String locator, boolean isWaitRequired) {
        List<WebElement> webElements = getWebElementList(locator, isWaitRequired);
        List<String> elementListString = new ArrayList<>();
        for (WebElement element : webElements) {
            elementListString.add(element.getText());
        }
        log.trace("User is trying to get the list of String");
        return elementListString;
    }

    protected List<Integer> getWebElementListInInteger(String locator, boolean isWaitRequired) {
        List<WebElement> webElements = getWebElementList(locator, isWaitRequired);
        List<Integer> elementListInteger = new ArrayList<>();
        for (WebElement element : webElements) {
            elementListInteger.add(Integer.parseInt(element.getText()));
        }
        log.trace("User is trying to get the list of Integer");
        return elementListInteger;
    }

    protected List<Double> getWebElementListInDouble(String locator, boolean isWaitRequired) {
        List<WebElement> webElements = getWebElementList(locator, isWaitRequired);
        List<Double> elementListInteger = new ArrayList<>();
        for (WebElement element : webElements) {
            elementListInteger.add(Double.parseDouble(element.getText().replace(".", "").replace("%", "")) / 100);
        }
        log.trace("User is trying to get the list of Double");
        return elementListInteger;
    }

    protected WebElement getElement(String locator, boolean isWaitRequired) {
        WebElement element = null;
        String locatorType = getLocatorType(locator);
        String locatorValue = getLocatorValue(locator);
        if (isWaitRequired)
            element = wait.until(ExpectedConditions.visibilityOfElementLocated(getByReference(locatorType, locatorValue)));
        else
            element = driver.findElement(getByReference(locatorType, locatorValue));
        log.trace("User is trying to get the element");
        return element;
    }


    private String getLocatorType(String locator) {
        String locatorType = null;
        try {
            locatorType = locator.split("]:-")[0].substring(1);
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            log.warn("Check locator splitter in property file");
        }
        return locatorType;
    }

    private String getLocatorValue(String locator) {
        String locatorValue = null;
        try {
            locatorValue = locator.split("]:-")[1];
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            log.warn("Check locator splitter in property file");
        }
        return locatorValue;
    }

    private By getByReference(String locatorType, String locatorValue) {
        switch (locatorType.toUpperCase()) {
            case "XPATH":
                return By.xpath(locatorValue);
            case "ID":
                return By.id(locatorValue);
            case "CLASSNAME":
                return By.className(locatorValue);
            case "PARTIALLINK":
                return By.partialLinkText(locatorValue);
            case "LINKTEXT":
                return By.linkText(locatorValue);
            case "CSS":
                return By.cssSelector(locatorValue);
            case "TAGNAME":
                return By.tagName(locatorValue);
            case "NAME":
                return By.name(locatorValue);
            default:
                log.trace("Invalid Locator Type");
        }
        return null;
    }
    protected String getCssValueColor(String locator,boolean isWaitRequired)
    {
        WebElement element = getElement(locator,isWaitRequired);
        return element.getCssValue("color");
    }

    private void drawBorder(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].style.border='2Px solid red'", element);
    }

    protected String getElementText(WebElement element) {
        String text = element.getText();
        log.trace("User is trying to get Element text");
        return text;
    }

    protected String getAttribute(WebElement element, String attribute) {
        log.trace("User is trying to get attribute value");
        return element.getAttribute(attribute);
    }

    protected String getWebpageTitle() {
        log.trace("User is trying to get page title");
        return driver.getTitle();
    }

    protected void clickOnElement(WebElement element, boolean isWaitRequired) {
        if (isWaitRequired == true)
            wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
        log.trace("User able to click on Element");
    }

    protected void clickOnElementWithJS(String locator, boolean isWaitRequired) {
        WebElement element = getElement(locator, isWaitRequired);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }

    protected void clickOnElement(String locator, boolean isWaitRequired) {
        WebElement element = getElement(locator, isWaitRequired);
        if (isWaitRequired == true)
            element = wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
        log.trace("User able to click on Elements");
    }

    protected void enterText(WebElement element, String textToBe) {
        if (element.isEnabled())
            element.sendKeys(textToBe);
        else element.sendKeys(textToBe);
        log.trace("User able to enter the text");
    }

    protected void clickEnter(String locator, boolean isWaitRequired) {
        WebElement element = getElement(locator, isWaitRequired);
        element.sendKeys(Keys.ENTER);
        log.trace("User click the Enter Key");
    }

    protected static void clickEnter() {
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    protected void openNewTab() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.open();");
        log.trace("User able to open new tab using JS");
    }

    public void navigateBack() {
        driver.navigate().back();
    }

    protected void clearElementField(String locator, boolean isWaitRequired) {
        WebElement element = getElement(locator, isWaitRequired);
        element.clear();
        log.trace("User is able to clear the given element");
    }

    protected void acceptAlert() {
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }

    protected void dismissAlert() {
        Alert alert = driver.switchTo().alert();
        alert.dismiss();
    }

    public static void takeScreenshot(String fileName) {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File screenshotFile = ts.getScreenshotAs(OutputType.FILE);

        File file = new File("src/test/Screenshot" + fileName + ".png");
        try {
            Files.copy(screenshotFile, file);
        } catch (IOException e) {
            log.error("User not able to copy Screenshot file");
            throw new RuntimeException();
        }
    }

    public static void closeBrowser() {
        driver.close();
        log.trace("User Closed the browser");
    }
}
