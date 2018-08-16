package co.il.nmh.easy.selenium.core;

import co.il.nmh.easy.selenium.EasySeleniumBrowser;

/**
 * @author Maor Hamami
 */

public abstract class DriverWrapper
{
	protected EasySeleniumBrowser easySeleniumBrowser;

	public DriverWrapper(EasySeleniumBrowser easySeleniumBrowser)
	{
		this.easySeleniumBrowser = easySeleniumBrowser;
	}
}
