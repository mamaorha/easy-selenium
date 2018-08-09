package co.il.nmh.easy.selenium.core.wrappers;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import co.il.nmh.easy.selenium.core.DriverWrapper;
import co.il.nmh.easy.selenium.utils.InputValidationUtils;

/**
 * @author Maor Hamami
 */

public class NavigateWrapper extends DriverWrapper
{
	public NavigateWrapper(WebDriver driver)
	{
		super(driver);
	}

	public void refresh()
	{
		driver.navigate().refresh();
	}

	public void navigate(String url, int timeout) throws TimeoutException
	{
		InputValidationUtils.INSTANCE.validateMinimumValue(1, timeout, "timeout");

		driver.manage().timeouts().pageLoadTimeout(timeout, TimeUnit.SECONDS);

		if (!url.toLowerCase().startsWith("http"))
		{
			url = "http://" + url;
		}

		driver.navigate().to(url);

		if (driver instanceof InternetExplorerDriver && driver.getTitle().contains("Certificate"))
		{
			driver.navigate().to("javascript:document.getElementById('overridelink').click()");
		}
	}

	public void back(int amount)
	{
		InputValidationUtils.INSTANCE.validateMinimumValue(1, amount, "amount");

		for (int i = 0; i < amount; i++)
		{
			driver.navigate().back();
		}
	}

	public String getUrl()
	{
		return driver.getCurrentUrl();
	}

	public String getTitle()
	{
		return driver.getTitle();
	}
}
