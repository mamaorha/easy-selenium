package co.il.nmh.easy.selenium;

import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import co.il.nmh.easy.selenium.core.WebDriverManager;
import co.il.nmh.easy.selenium.core.wrappers.ActionWrapper;
import co.il.nmh.easy.selenium.core.wrappers.DocumentWrapper;
import co.il.nmh.easy.selenium.core.wrappers.NavigateWrapper;
import co.il.nmh.easy.selenium.core.wrappers.VerifyWrapper;
import co.il.nmh.easy.selenium.core.wrappers.WindowWrapper;
import co.il.nmh.easy.selenium.enums.BrowserType;
import co.il.nmh.easy.selenium.utils.InputValidationUtils;

/**
 * @author Maor Hamami
 */

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

		this.document = new DocumentWrapper(this);
		this.navigator = new NavigateWrapper(this);
		this.window = new WindowWrapper(this);
		this.action = new ActionWrapper(this);
		this.veirfy = new VerifyWrapper(this);
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

	public WebDriver driver()
	{
		return driver;
	}

	public ActionWrapper action()
	{
		return action;
	}

	public DocumentWrapper document()
	{
		return document;
	}

	public NavigateWrapper navigator()
	{
		return navigator;
	}

	public VerifyWrapper veirfy()
	{
		return veirfy;
	}

	public WindowWrapper window()
	{
		return window;
	}

	public void switchFrame(String index)
	{
		String[] indexes = index.split(",");

		for (String currIndex : indexes)
		{
			int indexInt = Integer.valueOf(currIndex);

			InputValidationUtils.INSTANCE.validateMinimumValue(0, indexInt, "index");
			driver = driver.switchTo().frame(indexInt);
		}
	}

	public void switchFrame(WebElement element)
	{
		driver = driver.switchTo().frame(element);
	}
}
