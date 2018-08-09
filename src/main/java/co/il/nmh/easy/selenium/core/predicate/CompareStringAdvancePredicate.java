package co.il.nmh.easy.selenium.core.predicate;

import org.openqa.selenium.WebElement;

import co.il.nmh.easy.selenium.enums.AttributeCompare;
import co.il.nmh.easy.selenium.enums.AttributeCompareAdvance;
import co.il.nmh.easy.selenium.enums.CompareString;

/**
 * @author Maor Hamami
 */

public class CompareStringAdvancePredicate extends CompareStringPredicate
{
	protected AttributeCompareAdvance compareBy;
	protected String identifier;

	public CompareStringAdvancePredicate(WebElement element, AttributeCompareAdvance compareBy, CompareString compareString, String identifier, String expectedValue, boolean ignoreCase)
	{
		super(element, AttributeCompare.TEXT, compareString, expectedValue, ignoreCase);

		this.identifier = identifier;
		this.compareBy = compareBy;
	}

	@Override
	public String currValue()
	{
		switch (compareBy)
		{
			case ATTRIBUTE:
			{
				return element.getAttribute(identifier);
			}

			case CSS_VALUE:
			{
				return element.getCssValue(identifier);
			}
		}

		return "";
	}
}
