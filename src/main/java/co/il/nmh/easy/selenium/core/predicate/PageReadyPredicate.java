package co.il.nmh.easy.selenium.core.predicate;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import com.google.common.base.Predicate;

/**
 * @author Maor Hamami
 */

public class PageReadyPredicate implements Predicate<WebDriver>
{
	@Override
	public boolean apply(WebDriver driver)
	{
		final JavascriptExecutor js = (JavascriptExecutor) driver;

		try
		{
			String readyState = js.executeScript("return document.readyState").toString();
			return readyState.equals("complete");
		}

		catch (Exception e)
		{
			return false;
		}
	}
}
