package other;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.Scenario;

public class CommonMethods {
	
	public static HashMap<String, String> appProperties = new HashMap<String, String>();
	public static HashMap<String, String> testData = new HashMap<String, String>();
	public static Scenario scenario;
	
	public static void loadApplicationProperties() {
		
		Properties prop = new Properties();
		FileInputStream input = null;
		
		try {
			input = new FileInputStream("app.properties");
			prop.load(input);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
			
		for (Object key : prop.keySet()) {
//			System.out.println((String) prop.get(key.toString()));
			appProperties.put(key.toString(), (String) prop.get(key.toString()));
		}
		
	}

	public static void constWait(int seconds) {
		try {
			Thread.sleep(seconds*1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void implicitWait(WebDriver driver, int seconds) {
		driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
	}
	
	public static WebElement waitTillElementIsPresent(WebDriver driver, By locator, int seconds) {
		return new WebDriverWait(driver, seconds).until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	public static WebElement waitTillElementIsPresentWithPollFrequency(WebDriver driver, By locator, int seconds, int pollFrequency) {
		return new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(seconds))
				.pollingEvery(Duration.ofSeconds(pollFrequency))
				.ignoring(NoSuchElementException.class).until(new Function<WebDriver, WebElement>() {
					@Override
					public WebElement apply(WebDriver driver) {
						return findElement(driver, locator);
					}
				});
	}

	public static WebElement waitTillElementIsClickable(WebDriver driver, By locator, int seconds) {
		return new WebDriverWait(driver, seconds).until(ExpectedConditions.elementToBeClickable(locator));
	}

	public static Alert waitTillAlertIsPresent(WebDriver driver, int seconds) {
		return new WebDriverWait(driver, seconds).until(ExpectedConditions.alertIsPresent());
	}
	
	public static WebElement findElement(WebDriver driver, By locator) {
		return driver.findElement(locator);
	}
	
	public static List<WebElement> findElements(WebDriver driver, By locator) {
		return driver.findElements(locator);
	}
	
	public static boolean isDisplayed(WebDriver driver, By locator) {
		return findElement(driver, locator).isDisplayed();
	}
	
	public static boolean isEnabled(WebDriver driver, By locator) {
		return findElement(driver, locator).isEnabled();
	}
	
	public static boolean isSelected(WebDriver driver, By locator) {
		return findElement(driver, locator).isSelected();
	}
	
	public static String getPageTitle(WebDriver driver) {
		return driver.getTitle();
	}
	
	public static void setText(WebDriver driver, By locator, String text) {
		findElement(driver, locator).sendKeys(text);
	}
	
	public static String getText(WebDriver driver, By locator) {
		return findElement(driver, locator).getText();
	}
	
	public static String getAttributeValue(WebDriver driver, By locator, String attribute) {
		return findElement(driver, locator).getAttribute(attribute);
	}
	
	public static String getCSSAttributeValue(WebDriver driver, By locator, String attribute) {
		return findElement(driver, locator).getCssValue(attribute);
	}
	
	public static void clickElement(WebDriver driver, By locator) {
		findElement(driver, locator).click();
	}
	
	public static void selectUnselectCheckbox(WebDriver driver, By locator, String condition) {
		boolean isSelected = findElement(driver, locator).isSelected();
		if("select".equalsIgnoreCase(condition) && !isSelected) {
			findElement(driver, locator).click();
		} else if("unselect".equalsIgnoreCase(condition) && isSelected) {
			findElement(driver, locator).click();
		}
	}
	
	public static void selectDropdownValue(WebDriver driver, By locator, String valueToSelect) {
		new Select(findElement(driver, locator)).deselectByVisibleText(valueToSelect);
	}
	
	public static void mouseOver(WebDriver driver, By locator) {
		new Actions(driver).moveToElement(findElement(driver, locator)).build().perform();
	}
	
	public static void acceptAlert(WebDriver driver) {
		driver.switchTo().alert().accept();
	}
	
	public static void dismissAlert(WebDriver driver) {
		driver.switchTo().alert().dismiss();
	}
	
	public static String getAlertText(WebDriver driver) {
		return driver.switchTo().alert().getText();
	}
	
	public static void switchToNewWindow(WebDriver driver) {
		Set<String> windows = driver.getWindowHandles();
		Iterator<String> iterator = windows.iterator();
		if(iterator.hasNext()) {
			driver.switchTo().window(iterator.next());
		}
	}
	
	public static void switchToFrameByIndex(WebDriver driver, int index) {
		driver.switchTo().frame(index);
	}
	
	public static void switchToFrameByNameOrID(WebDriver driver, String nameOrID) {
		driver.switchTo().frame(nameOrID);
	}
	
	public static void switchBackToMainFrame(WebDriver driver) {
		driver.switchTo().defaultContent();
	}
	
	public static void takeScreenshot(WebDriver driver) {
		((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	}
	
	public static void jsExecutor(WebDriver driver, String command) {
		((JavascriptExecutor)driver).executeScript(command);
	}
	
	public static void readTestData(String testName) {
		try {
			Workbook workBook = new XSSFWorkbook(new FileInputStream(new File(CommonMethods.appProperties.get("TESTDATA_PATH"))));
			Sheet sheet = workBook.getSheet(CommonMethods.appProperties.get("TESTDATA_SHEET"));
			int rowCount = sheet.getLastRowNum()-sheet.getFirstRowNum();

			int reqTestRowIndex = -1;
			for (int rowIndex = 1; rowIndex <= rowCount; rowIndex++) {
				String currentTestName = sheet.getRow(rowIndex).getCell(1).toString();
				if(testName.equalsIgnoreCase(currentTestName)) {
					reqTestRowIndex = rowIndex;
					break;
				}
			}
			
			if(reqTestRowIndex!=-1) {
				Row headerRow = sheet.getRow(0);
				Row dataRow = sheet.getRow(reqTestRowIndex);
				for (int colIndex = 0; colIndex < dataRow.getLastCellNum(); colIndex++) {
					Cell data = dataRow.getCell(colIndex);
					if(data!=null) {
//						System.out.println(dataRow.getCell(colIndex).toString());
						testData.put(headerRow.getCell(colIndex).toString().toUpperCase(), data.toString());
					}
				}
			}
			
			System.out.println(testData);
			workBook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Excel Creation
	
	//JSON Read
	
	//JSON Write
	
	//XML Read
	
	//XML Write
}
