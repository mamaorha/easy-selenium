package co.il.nmh.easy.selenium.core.predicate;

import org.openqa.selenium.WebDriver;

import com.google.common.base.Predicate;

/**
 * @author Maor Hamami
 */

public abstract class AbstractComparePredicate implements Predicate<WebDriver>
{
	public abstract String currValue();

	public abstract String expectedValue();

	public abstract String getComperator();

	public String message(boolean success)
	{
		if (!success)
		{
			return "Couldn't find a match between '" + currValue() + "' and '" + expectedValue() + "'";
		}

		else
		{
			return "Successfull match: " + currValue() + " " + getComperator() + " " + expectedValue();
		}
	}
}
