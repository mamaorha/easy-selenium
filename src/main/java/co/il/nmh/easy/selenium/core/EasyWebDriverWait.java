package co.il.nmh.easy.selenium.core;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;
import com.google.common.base.Predicate;

import co.il.nmh.easy.selenium.exceptions.SeleniumActionTimeout;
import co.il.nmh.easy.selenium.utils.InputValidationUtils;

/**
 * @author Maor Hamami
 */

public class EasyWebDriverWait
{
	protected WebDriverWait webDriverWait;

	public EasyWebDriverWait(WebDriver driver, long timeOutInSeconds)
	{
		InputValidationUtils.INSTANCE.validateMinimumValue(0, timeOutInSeconds, "timeOutInSeconds");
		webDriverWait = new WebDriverWait(driver, timeOutInSeconds);
	}

	public void until(Predicate<WebDriver> isTrue) throws SeleniumActionTimeout
	{
		try
		{
			webDriverWait.until(isTrue);
		}
		catch (TimeoutException e)
		{
			throw new SeleniumActionTimeout(e);
		}

		webDriverWait.until(isTrue);
	}

	public <T> T until(Function<WebDriver, T> isTrue) throws SeleniumActionTimeout
	{
		try
		{
			return webDriverWait.until(isTrue);
		}
		catch (TimeoutException e)
		{
			throw new SeleniumActionTimeout(e);
		}
	}
}
