package co.il.nmh.easy.selenium.core.predicate;

import org.openqa.selenium.WebElement;

import co.il.nmh.easy.selenium.enums.AttributeCompare;
import co.il.nmh.easy.selenium.enums.AttributeCompareAdvance;
import co.il.nmh.easy.selenium.enums.CompareNumber;

/**
 * @author Maor Hamami
 */

public class CompareNumberAdvancePredicate extends CompareNumberPredicate
{
	protected String identifier;
	protected AttributeCompareAdvance compareBy;

	public CompareNumberAdvancePredicate(WebElement element, AttributeCompareAdvance compareBy, CompareNumber compareNumber, String identifier, float expectedValue)
	{
		super(element, AttributeCompare.TEXT, compareNumber, expectedValue);

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
