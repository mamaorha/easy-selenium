package co.il.nmh.easy.selenium.core.predicate;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import co.il.nmh.easy.selenium.enums.AttributeCompare;
import co.il.nmh.easy.selenium.enums.CompareNumber;

/**
 * @author Maor Hamami
 */

public class CompareNumberPredicate extends AbstractComparePredicate
{
	protected WebElement element;
	protected AttributeCompare compareBy;
	protected CompareNumber compareNumber;
	protected float expectedValue;

	public CompareNumberPredicate(WebElement element, AttributeCompare compareBy, CompareNumber compareNumber, float expectedValue)
	{
		this.element = element;
		this.compareBy = compareBy;
		this.compareNumber = compareNumber;
		this.expectedValue = expectedValue;
	}

	@Override
	public boolean apply(WebDriver input)
	{
		try
		{
			Float realValue = Float.valueOf(currValue());

			switch (compareNumber)
			{
				case SMALLER:
					return realValue < expectedValue;
				case BIGGER:
					return realValue > expectedValue;
				case EQUAL:
					return realValue == expectedValue;
				case NOT_EQUAL:
					return realValue != expectedValue;
				case SMALLER_EQUAL:
					return realValue <= expectedValue;
				case BIGGER_EQUAL:
					return realValue >= expectedValue;
			}
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
		return String.valueOf(expectedValue);
	}

	@Override
	public String getComperator()
	{
		return compareNumber.toString();
	}
}
