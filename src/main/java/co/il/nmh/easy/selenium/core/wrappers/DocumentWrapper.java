package co.il.nmh.easy.selenium.core.wrappers;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import co.il.nmh.easy.selenium.EasySeleniumBrowser;
import co.il.nmh.easy.selenium.core.DriverWrapper;
import co.il.nmh.easy.selenium.core.EasyWebDriverWait;
import co.il.nmh.easy.selenium.core.predicate.ElementCreationInListPredicate;
import co.il.nmh.easy.selenium.core.predicate.PageReadyPredicate;
import co.il.nmh.easy.selenium.enums.SearchBy;
import co.il.nmh.easy.selenium.enums.WaitCondition;
import co.il.nmh.easy.selenium.exceptions.SeleniumActionTimeout;

/**
 * @author Maor Hamami
 */

public class DocumentWrapper extends DriverWrapper
{
	public DocumentWrapper(EasySeleniumBrowser easySeleniumBrowser)
	{
		super(easySeleniumBrowser);
	}

	public WebElement getElement(SearchBy searchBy, String searchValue, int index, WaitCondition waitCondition, int timeOutInSeconds) throws SeleniumActionTimeout
	{
		return getElement(easySeleniumBrowser.driver(), searchBy, searchValue, index, waitCondition, timeOutInSeconds);
	}

	public WebElement getElement(SearchContext source, SearchBy searchBy, String searchValue, int index, WaitCondition waitCondition, int timeOutInSeconds) throws SeleniumActionTimeout
	{
		EasyWebDriverWait easyWebDriverWait = new EasyWebDriverWait(easySeleniumBrowser.driver(), timeOutInSeconds);

		ElementCreationInListPredicate elementCreationInListPredicate = new ElementCreationInListPredicate(this, source, searchBy, searchValue, index);
		easyWebDriverWait.until(elementCreationInListPredicate);

		WebElement webElement = elementCreationInListPredicate.getElements().get(index);

		switch (waitCondition)
		{
			case VISIBILITY_OF_ELEMENT:
				easyWebDriverWait.until(ExpectedConditions.visibilityOf(webElement));
				break;

			case INVISIBILITY_OF_ELEMENT:
				easyWebDriverWait.until(ExpectedConditions.not(ExpectedConditions.visibilityOf(webElement)));
				break;

			case CLICKABILITY_OF_ELEMENT:
				easyWebDriverWait.until(ExpectedConditions.elementToBeClickable(webElement));
				break;

			case SELECTED_ELEMENT:
				easyWebDriverWait.until(ExpectedConditions.elementToBeSelected(webElement));
				break;

			case ELEMENT_CREATION:
				break;
		}

		return webElement;
	}

	public List<WebElement> getElements(SearchBy searchBy, String searchValue)
	{
		return getElements(easySeleniumBrowser.driver(), searchBy, searchValue);
	}

	public List<WebElement> getElements(SearchContext source, SearchBy searchBy, String searchValue)
	{
		List<WebElement> elements = null;

		if (searchBy == SearchBy.CLASS_NAME && searchValue.contains(" "))
		{
			searchBy = SearchBy.CSS_SELECTOR;
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

			case TAG_NAME:
				elements = source.findElements(By.tagName(searchValue));
				break;

			case CLASS_NAME:
				elements = source.findElements(By.className(searchValue));
				break;

			case CSS_SELECTOR:
				elements = source.findElements(By.cssSelector(searchValue));
				break;

			case LINK_TEXT:
				elements = source.findElements(By.linkText(searchValue));
				break;

			case PARTIAL_LINK:
				elements = source.findElements(By.partialLinkText(searchValue));
				break;

			case XPATH:
				elements = source.findElements(By.xpath(searchValue));
				break;
		}

		return elements;
	}

	public void waitForPageLoad(int timeOutInSeconds) throws SeleniumActionTimeout
	{
		EasyWebDriverWait easyWebDriverWait = new EasyWebDriverWait(easySeleniumBrowser.driver(), timeOutInSeconds);
		easyWebDriverWait.until(new PageReadyPredicate());
	}

	public void switchToOriginalFrame()
	{
		this.easySeleniumBrowser.driver().switchTo().defaultContent();
	}
}
