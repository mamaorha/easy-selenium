package co.il.nmh.easy.selenium.core.predicate;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;

import com.google.common.base.Predicate;

import co.il.nmh.easy.selenium.core.wrappers.DocumentWrapper;
import co.il.nmh.easy.selenium.exceptions.SeleniumActionTimeout;
import co.il.nmh.easy.selenium.utils.SearchBy;
import co.il.nmh.easy.selenium.utils.WaitCondition;

/**
 * @author Maor Hamami
 */

public class ElementNotExistPredicate implements Predicate<WebDriver>
{
	private DocumentWrapper actionWrapper;
	private SearchContext source;
	private SearchBy searchBy;
	private String searchValue;
	private int index;

	public ElementNotExistPredicate(DocumentWrapper actionWrapper, SearchContext source, SearchBy searchBy, String searchValue, int index)
	{
		this.actionWrapper = actionWrapper;
		this.source = source;
		this.searchBy = searchBy;
		this.searchValue = searchValue;
		this.index = index;
	}

	@Override
	public boolean apply(WebDriver input)
	{
		try
		{
			actionWrapper.getElement(source, searchBy, searchValue, index, WaitCondition.ELEMENT_CREATION, 1);
			return false;
		}
		catch (SeleniumActionTimeout e)
		{
			return true;
		}
	}
}
