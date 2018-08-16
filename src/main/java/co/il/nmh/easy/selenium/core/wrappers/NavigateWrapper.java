package co.il.nmh.easy.selenium.core.wrappers;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.ie.InternetExplorerDriver;

import co.il.nmh.easy.selenium.EasySeleniumBrowser;
import co.il.nmh.easy.selenium.core.DriverWrapper;
import co.il.nmh.easy.selenium.exceptions.SeleniumActionTimeout;
import co.il.nmh.easy.selenium.utils.InputValidationUtils;

/**
 * @author Maor Hamami
 */

public class NavigateWrapper extends DriverWrapper
{
	public NavigateWrapper(EasySeleniumBrowser easySeleniumBrowser)
	{
		super(easySeleniumBrowser);
	}

	public void refresh()
	{
		easySeleniumBrowser.driver().navigate().refresh();
	}

	public void navigate(String url, int timeOutInSeconds) throws SeleniumActionTimeout
	{
		InputValidationUtils.INSTANCE.validateMinimumValue(1, timeOutInSeconds, "timeOutInSeconds");

		easySeleniumBrowser.driver().manage().timeouts().pageLoadTimeout(timeOutInSeconds, TimeUnit.SECONDS);

		if (!url.toLowerCase().startsWith("http"))
		{
			url = "http://" + url;
		}

		try
		{
			easySeleniumBrowser.driver().navigate().to(url);

			if (easySeleniumBrowser.driver() instanceof InternetExplorerDriver && easySeleniumBrowser.driver().getTitle().contains("Certificate"))
			{
				easySeleniumBrowser.driver().navigate().to("javascript:document.getElementById('overridelink').click()");
			}
		}
		catch (TimeoutException e)
		{
			throw new SeleniumActionTimeout(e);
		}
	}

	public void back(int amount)
	{
		InputValidationUtils.INSTANCE.validateMinimumValue(1, amount, "amount");

		for (int i = 0; i < amount; i++)
		{
			easySeleniumBrowser.driver().navigate().back();
		}
	}

	public String getUrl()
	{
		return easySeleniumBrowser.driver().getCurrentUrl();
	}

	public String getTitle()
	{
		return easySeleniumBrowser.driver().getTitle();
	}
}
