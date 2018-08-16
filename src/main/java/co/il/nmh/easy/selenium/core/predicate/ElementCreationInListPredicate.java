package co.il.nmh.easy.selenium.core.predicate;

import java.util.List;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.google.common.base.Predicate;

import co.il.nmh.easy.selenium.core.wrappers.DocumentWrapper;
import co.il.nmh.easy.selenium.enums.SearchBy;

/**
 * @author Maor Hamami
 */

public class ElementCreationInListPredicate implements Predicate<WebDriver>
{
	private DocumentWrapper actionWrapper;
	private SearchContext source;
	private SearchBy searchBy;
	private String searchValue;
	private int index;

	private List<WebElement> elements;

	public ElementCreationInListPredicate(DocumentWrapper actionWrapper, SearchContext source, SearchBy searchBy, String searchValue, int index)
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
		elements = actionWrapper.getElements(source, searchBy, searchValue);
		return null != elements && elements.size() > index;
	}

	public List<WebElement> getElements()
	{
		return elements;
	}
}
