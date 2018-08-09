package co.il.nmh.easy.selenium;

import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;

import co.il.nmh.easy.selenium.core.WebDriverManager;
import co.il.nmh.easy.selenium.core.wrappers.ActionWrapper;
import co.il.nmh.easy.selenium.core.wrappers.DocumentWrapper;
import co.il.nmh.easy.selenium.core.wrappers.NavigateWrapper;
import co.il.nmh.easy.selenium.core.wrappers.VerifyWrapper;
import co.il.nmh.easy.selenium.core.wrappers.WindowWrapper;
import co.il.nmh.easy.selenium.enums.BrowserType;
import lombok.Getter;

/**
 * @author Maor Hamami
 */

@Getter
public class EasySeleniumBrowser
{
	private WebDriver driver;
	private ActionWrapper action;
	private DocumentWrapper document;
	private NavigateWrapper navigator;
	private VerifyWrapper veirfy;
	private WindowWrapper window;

	public EasySeleniumBrowser(BrowserType browser)
	{
		this(WebDriverManager.INSTANCE.buildDriver(browser));
	}

	public EasySeleniumBrowser(WebDriver driver)
	{
		this.driver = driver;

		this.document = new DocumentWrapper(driver);
		this.navigator = new NavigateWrapper(driver);
		this.window = new WindowWrapper(driver);
		this.action = new ActionWrapper(driver, navigator);
		this.veirfy = new VerifyWrapper(driver, action, navigator, window);
	}

	public boolean isAvailable()
	{
		try
		{
			driver.getTitle();
			return true;
		}

		catch (UnhandledAlertException e)
		{
			return true;
		}

		catch (Exception e)
		{
			return false;
		}
	}

	public void close()
	{
		driver.close();
		driver.quit();
	}
}
