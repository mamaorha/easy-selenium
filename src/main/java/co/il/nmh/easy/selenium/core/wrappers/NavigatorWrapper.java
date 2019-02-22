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

public class NavigatorWrapper extends DriverWrapper
{
	public NavigatorWrapper(EasySeleniumBrowser easySeleniumBrowser)
	{
		super(easySeleniumBrowser);
	}

	/**
	 * Refresh the current page
	 */
	public NavigatorWrapper refresh()
	{
		easySeleniumBrowser.driver().navigate().refresh();
		return this;
	}

	/**
	 * Load a new web page in the current browser window. This is done using an HTTP GET operation, and the method will block until the load is complete. This will follow redirects issued either by the server or as a meta-redirect from within the returned HTML. Should a meta-redirect "rest" for any duration of time, it is best to wait until this timeout is over, since should the underlying page change whilst your test is executing the results of future calls against this interface will be
	 * against the freshly loaded page.
	 *
	 * @param url
	 *            The URL to load. It is best to use a fully qualified URL
	 * @param timeOutInSeconds
	 *            - maximum amount of seconds to wait for the page to load
	 */
	public NavigatorWrapper navigateWithoutException(String url, int timeOutInSeconds)
	{
		try
		{
			navigate(url, timeOutInSeconds);
		}
		catch (SeleniumActionTimeout e)
		{
		}

		return this;
	}

	/**
	 * Load a new web page in the current browser window. This is done using an HTTP GET operation, and the method will block until the load is complete. This will follow redirects issued either by the server or as a meta-redirect from within the returned HTML. Should a meta-redirect "rest" for any duration of time, it is best to wait until this timeout is over, since should the underlying page change whilst your test is executing the results of future calls against this interface will be
	 * against the freshly loaded page.
	 *
	 * @param url
	 *            The URL to load. It is best to use a fully qualified URL
	 * @param timeOutInSeconds
	 *            - maximum amount of seconds to wait for the page to load
	 * @throws SeleniumActionTimeout
	 */
	public NavigatorWrapper navigate(String url, int timeOutInSeconds) throws SeleniumActionTimeout
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

		return this;
	}

	/**
	 * Move back in the browser's history for a given amount of times.
	 * 
	 * @param amount
	 *            - how many times to invoke the back function, minimum value is 1
	 */
	public NavigatorWrapper back(int amount)
	{
		InputValidationUtils.INSTANCE.validateMinimumValue(1, amount, "amount");

		for (int i = 0; i < amount; i++)
		{
			easySeleniumBrowser.driver().navigate().back();
		}

		return this;
	}

	/**
	 * Move forward in the browser's history for a given amount of times. Does nothing if we are on the latest page viewed.
	 * 
	 * @param amount
	 *            - how many times to invoke the forward function, minimum value is 1
	 */
	public NavigatorWrapper forward(int amount)
	{
		InputValidationUtils.INSTANCE.validateMinimumValue(1, amount, "amount");

		for (int i = 0; i < amount; i++)
		{
			easySeleniumBrowser.driver().navigate().forward();
		}

		return this;
	}
}
