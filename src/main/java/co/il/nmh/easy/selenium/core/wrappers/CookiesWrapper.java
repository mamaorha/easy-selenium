package co.il.nmh.easy.selenium.core.wrappers;

import java.util.Set;

import org.openqa.selenium.Cookie;

import co.il.nmh.easy.selenium.EasySeleniumBrowser;
import co.il.nmh.easy.selenium.core.DriverWrapper;

/**
 * @author Maor Hamami
 */

public class CookiesWrapper extends DriverWrapper
{
	public CookiesWrapper(EasySeleniumBrowser easySeleniumBrowser)
	{
		super(easySeleniumBrowser);
	}

	/**
	 * Add a specific cookie. If the cookie's domain name is left blank, it is assumed that the cookie is meant for the domain of the current document.
	 *
	 * @param cookie
	 *            The cookie to add.
	 */
	public CookiesWrapper addCookie(Cookie cookie)
	{
		easySeleniumBrowser.driver().manage().addCookie(cookie);
		return this;
	}

	/**
	 * Delete all the cookies for the current domain.
	 */
	public CookiesWrapper deleteAllCookies()
	{
		easySeleniumBrowser.driver().manage().deleteAllCookies();
		return this;
	}

	/**
	 * Delete the named cookie from the current domain. This is equivalent to setting the named cookie's expiry date to some time in the past.
	 *
	 * @param name
	 *            The name of the cookie to delete
	 */
	public CookiesWrapper deleteCookieNamed(String name)
	{
		easySeleniumBrowser.driver().manage().deleteCookieNamed(name);
		return this;
	}

	/**
	 * Get a cookie with a given name.
	 *
	 * @param name
	 *            the name of the cookie
	 * @return the cookie, or null if no cookie with the given name is present
	 */
	public Cookie getCookieNamed(String name)
	{
		return easySeleniumBrowser.driver().manage().getCookieNamed(name);
	}

	public Set<Cookie> getCookies()
	{
		return easySeleniumBrowser.driver().manage().getCookies();
	}
}
