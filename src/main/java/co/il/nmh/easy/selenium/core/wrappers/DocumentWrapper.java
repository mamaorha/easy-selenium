package co.il.nmh.easy.selenium.core.wrappers;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import co.il.nmh.easy.selenium.core.DriverWrapper;
import co.il.nmh.easy.selenium.core.EasyWebDriverWait;
import co.il.nmh.easy.selenium.core.predicate.ElementCreationInListPredicate;
import co.il.nmh.easy.selenium.core.predicate.ElementNotExistPredicate;
import co.il.nmh.easy.selenium.core.predicate.PageReadyPredicate;
import co.il.nmh.easy.selenium.exceptions.SeleniumActionTimeout;
import co.il.nmh.easy.selenium.utils.InputValidationUtils;
import co.il.nmh.easy.selenium.utils.SearchBy;
import co.il.nmh.easy.selenium.utils.WaitCondition;

/**
 * @author Maor Hamami
 */

public class DocumentWrapper extends DriverWrapper
{
	public DocumentWrapper(WebDriver driver)
	{
		super(driver);
	}

	public WebElement getElement(SearchBy searchBy, String searchValue, int index, WaitCondition waitCondition, int timeOutInSeconds) throws SeleniumActionTimeout
	{
		return getElement(driver, searchBy, searchValue, index, waitCondition, timeOutInSeconds);
	}

	public WebElement getElement(SearchContext source, SearchBy searchBy, String searchValue, int index, WaitCondition waitCondition, int timeOutInSeconds) throws SeleniumActionTimeout
	{
		EasyWebDriverWait easyWebDriverWait = new EasyWebDriverWait(driver, timeOutInSeconds);

		ElementCreationInListPredicate elementCreationInListPredicate = new ElementCreationInListPredicate(this, source, searchBy, searchValue, index);
		easyWebDriverWait.until(elementCreationInListPredicate);

		WebElement webElement = elementCreationInListPredicate.getElements().get(index);

		switch (waitCondition)
		{
			case Visibility_of_Element:
				easyWebDriverWait.until(ExpectedConditions.visibilityOf(webElement));
				break;

			case Invisibility_of_Element:
				easyWebDriverWait.until(ExpectedConditions.not(ExpectedConditions.visibilityOf(webElement)));
				break;

			case Clickability_of_Element:
				easyWebDriverWait.until(ExpectedConditions.elementToBeClickable(webElement));
				break;

			case Selected_Element:
				easyWebDriverWait.until(ExpectedConditions.elementToBeSelected(webElement));
				break;

			case ELEMENT_CREATION:
				break;
		}

		return webElement;
	}

	public List<WebElement> getElements(SearchBy searchBy, String searchValue)
	{
		return getElements(driver, searchBy, searchValue);
	}

	public List<WebElement> getElements(SearchContext source, SearchBy searchBy, String searchValue)
	{
		List<WebElement> elements = null;

		if (searchBy == SearchBy.Class_Name && searchValue.contains(" "))
		{
			searchBy = SearchBy.CSS_Selector;
			searchValue = "*[class^='" + searchValue + "']";
		}

		switch (searchBy)
		{
			case ID:
				elements = source.findElements(By.id(searchValue));
				break;

			case Name:
				elements = source.findElements(By.name(searchValue));
				break;

			case Tag_Name:
				elements = source.findElements(By.tagName(searchValue));
				break;

			case Class_Name:
				elements = source.findElements(By.className(searchValue));
				break;

			case CSS_Selector:
				elements = source.findElements(By.cssSelector(searchValue));
				break;

			case Link_Text:
				elements = source.findElements(By.linkText(searchValue));
				break;

			case Partial_Link:
				elements = source.findElements(By.partialLinkText(searchValue));
				break;

			case XPath:
				elements = source.findElements(By.xpath(searchValue));
				break;
		}

		return elements;
	}

	public void elementIsNotPartOfPage(SearchBy searchBy, String searchValue, int index, int timeOutInSeconds) throws SeleniumActionTimeout
	{
		elementIsNotPartOfPage(driver, searchBy, searchValue, index, timeOutInSeconds);
	}

	public void elementIsNotPartOfPage(SearchContext source, SearchBy searchBy, String searchValue, int index, int timeOutInSeconds) throws SeleniumActionTimeout
	{
		EasyWebDriverWait easyWebDriverWait = new EasyWebDriverWait(driver, timeOutInSeconds);
		easyWebDriverWait.until(new ElementNotExistPredicate(this, source, searchBy, searchValue, index));
	}

	public void waitForPageLoad(int timeOutInSeconds) throws SeleniumActionTimeout
	{
		EasyWebDriverWait easyWebDriverWait = new EasyWebDriverWait(driver, timeOutInSeconds);
		easyWebDriverWait.until(new PageReadyPredicate());
	}

	public void switchToOriginalFrame()
	{
		this.driver.switchTo().defaultContent();
	}

	public void switchFrame(String index)
	{
		String[] indexes = index.split(",");

		for (String currIndex : indexes)
		{
			int indexInt = Integer.valueOf(currIndex);

			InputValidationUtils.INSTANCE.validateMinimumValue(0, indexInt, "index");
			driver = driver.switchTo().frame(indexInt);
		}
	}

	public void switchFrame(WebElement element)
	{
		driver = driver.switchTo().frame(element);
	}
}
