package co.il.nmh.easy.selenium.core.predicate;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import co.il.nmh.easy.selenium.enums.AttributeCompare;
import co.il.nmh.easy.selenium.enums.CompareString;

/**
 * @author Maor Hamami
 */

public class CompareStringPredicate extends AbstractComparePredicate
{
	protected WebElement element;
	protected AttributeCompare compareBy;
	protected CompareString compareString;
	protected String expectedValue;
	protected boolean ignoreCase;

	public CompareStringPredicate(WebElement element, AttributeCompare compareBy, CompareString compareString, String expectedValue, boolean ignoreCase)
	{
		this.element = element;
		this.compareBy = compareBy;
		this.compareString = compareString;
		this.expectedValue = expectedValue;
		this.ignoreCase = ignoreCase;
	}

	@Override
	public boolean apply(WebDriver input)
	{
		try
		{
			String realValue = currValue();

			if (ignoreCase)
			{
				if (null != realValue)
				{
					realValue = realValue.toLowerCase();
				}

				if (null != expectedValue)
				{
					expectedValue = expectedValue.toLowerCase();
				}
			}

			switch (compareString)
			{
				case CONTAINS:
					return realValue.contains(expectedValue);

				case NOT_CONTAINS:
					return !realValue.contains(expectedValue);

				case EQUAL:
					return realValue.equals(expectedValue);

				case NOT_EQUAL:
					return !realValue.equals(expectedValue);
			}

			return false;
		}

		catch (Exception e)
		{
		}

		return false;
	}

	@Override
	public String currValue()
	{
		switch (compareBy)
		{
			case TEXT:
				return element.getText();
			case TAG_NAME:
				return element.getTagName();
		}

		return "";
	}

	@Override
	public String expectedValue()
	{
		return expectedValue;
	}

	@Override
	public String getComperator()
	{
		return compareString.toString();
	}
}
