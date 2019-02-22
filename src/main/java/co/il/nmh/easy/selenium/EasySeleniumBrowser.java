package co.il.nmh.easy.selenium;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import co.il.nmh.easy.selenium.core.EasyWebDriverWait;
import co.il.nmh.easy.selenium.core.WebDriverManager;
import co.il.nmh.easy.selenium.core.predicate.PageReadyPredicate;
import co.il.nmh.easy.selenium.core.wrappers.ActionWrapper;
import co.il.nmh.easy.selenium.core.wrappers.CookiesWrapper;
import co.il.nmh.easy.selenium.core.wrappers.DocumentWrapper;
import co.il.nmh.easy.selenium.core.wrappers.NavigatorWrapper;
import co.il.nmh.easy.selenium.core.wrappers.VerificationWrapper;
import co.il.nmh.easy.selenium.core.wrappers.WindowWrapper;
import co.il.nmh.easy.selenium.enums.BrowserType;
import co.il.nmh.easy.selenium.exceptions.SeleniumActionTimeout;
import co.il.nmh.easy.selenium.utils.InputValidationUtils;

/**
 * @author Maor Hamami
 */

public class EasySeleniumBrowser implements Closeable
{
	private WebDriver driver;
	private ActionWrapper action;
	private DocumentWrapper document;
	private NavigatorWrapper navigator;
	private VerificationWrapper verification;
	private WindowWrapper window;
	private CookiesWrapper cookies;

	public EasySeleniumBrowser(BrowserType browser)
	{
		this(WebDriverManager.INSTANCE.buildDriver(browser));
	}

	public EasySeleniumBrowser(WebDriver driver)
	{
		this.driver = driver;

		this.document = new DocumentWrapper(this);
		this.navigator = new NavigatorWrapper(this);
		this.window = new WindowWrapper(this);
		this.action = new ActionWrapper(this);
		this.verification = new VerificationWrapper(this);
		this.cookies = new CookiesWrapper(this);
	}

	/**
	 * 
	 * Use this method to check if the browser can handle commands
	 */
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

	/**
	 * 
	 * Use this method to close the browser and the driver that is attached to it
	 */
	@Override
	public void close()
	{
		try
		{
			driver.close();
			driver.quit();
		}
		catch (Exception e)
		{
			try
			{
				driver.quit();
				driver.close();
			}
			catch (Exception ex)
			{
			}
		}
	}

	/**
	 * 
	 * Use this method to in case easySelenium is missing functionality you can always work with the original webDriver
	 * 
	 * @return {@link org.openqa.selenium.WebDriver}
	 */
	public WebDriver driver()
	{
		return driver;
	}

	/**
	 * An abstraction allowing easy interaction with WebElements. for example: click, setText, focus, etc...
	 *
	 * @return {@link co.il.nmh.easy.selenium.core.wrappers.ActionWrapper}
	 */
	public ActionWrapper action()
	{
		return action;
	}

	/**
	 * An abstraction allowing easy extraction of WebElements by multiple criteria and waiting options.
	 *
	 * @return {@link co.il.nmh.easy.selenium.core.wrappers.DocumentWrapper}
	 */
	public DocumentWrapper document()
	{
		return document;
	}

	/**
	 * An abstraction allowing easy navigation in the browser.
	 *
	 * @return {@link co.il.nmh.easy.selenium.core.wrappers.NavigatorWrapper}
	 */
	public NavigatorWrapper navigator()
	{
		return navigator;
	}

	/**
	 * An abstraction allowing easy verification of WebElement/Page state and data.
	 *
	 * @return {@link co.il.nmh.easy.selenium.core.wrappers.VerificationWrapper}
	 */
	public VerificationWrapper veirfy()
	{
		return verification;
	}

	/**
	 * An abstraction allowing easy control the browser. for example: setSize, maximize, etc...
	 *
	 * @return {@link co.il.nmh.easy.selenium.core.wrappers.VerificationWrapper}
	 */
	public WindowWrapper window()
	{
		return window;
	}

	/**
	 * An abstraction allowing easy control the browser cookies. for example: deleteAllCookies, getCookieNamed, etc...
	 *
	 * @return {@link co.il.nmh.easy.selenium.core.wrappers.CookiesWrapper}
	 */
	public CookiesWrapper getCookiesWrapper()
	{
		return cookies;
	}

	/**
	 * 
	 * Use this method to switch the driver frame to a given index
	 * 
	 * @param index
	 *            expected to be number, can be separated by ',' for example "0,0" will move to iframe 0 and inside that iframe will go to iframe 0
	 */
	public EasySeleniumBrowser switchFrame(String index)
	{
		String[] indexes = index.split(",");

		for (String currIndex : indexes)
		{
			int indexInt = Integer.valueOf(currIndex);

			InputValidationUtils.INSTANCE.validateMinimumValue(0, indexInt, "index");
			driver = driver.switchTo().frame(indexInt);
		}

		return this;
	}

	/**
	 * 
	 * Use this method to switch the driver to specific webElement
	 * 
	 * @param element
	 *            usually an iframe
	 */
	public EasySeleniumBrowser switchFrame(WebElement element)
	{
		driver = driver.switchTo().frame(element);
		return this;
	}

	/**
	 * Use this method to switch the driver back to its original frame
	 */
	public EasySeleniumBrowser switchToOriginalFrame()
	{
		driver = driver.switchTo().defaultContent();
		return this;
	}

	/**
	 * 
	 * Use this method to wait for page to finish loading with a given timeout
	 * 
	 * @param timeOutInSeconds
	 *            - maximum amount of seconds to wait until page is loaded
	 * @throws SeleniumActionTimeout
	 */
	public EasySeleniumBrowser waitForPageLoad(int timeOutInSeconds) throws SeleniumActionTimeout
	{
		EasyWebDriverWait easyWebDriverWait = new EasyWebDriverWait(driver, timeOutInSeconds);
		easyWebDriverWait.until(new PageReadyPredicate());

		return this;
	}

	/**
	 * Get a string representing the current URL that the browser is looking at.
	 *
	 * @return The URL of the page currently loaded in the browser
	 */
	public String getUrl()
	{
		return driver.getCurrentUrl();
	}

	/**
	 * The title of the current page.
	 *
	 * @return The title of the current page, with leading and trailing whitespace stripped, or null if one is not already set
	 */
	public String getTitle()
	{
		return driver.getTitle();
	}

	/**
	 * Capture the screenshot and store it in the specified location.
	 *
	 * <p>
	 * For WebDriver extending TakesScreenshot, this makes a best effort depending on the browser to return the following in order of preference:
	 * <ul>
	 * <li>Entire page</li>
	 * <li>Current window</li>
	 * <li>Visible portion of the current frame</li>
	 * <li>The screenshot of the entire display containing the browser</li>
	 * </ul>
	 *
	 * <p>
	 * For WebElement extending TakesScreenshot, this makes a best effort depending on the browser to return the following in order of preference: - The entire content of the HTML element - The visible portion of the HTML element *
	 * 
	 * @return BufferedImage in which is stored information about the screenshot.
	 * @throws IOException
	 *             on failure when parsing from byte[] to BufferedImage.
	 * @throws WebDriverException
	 *             on failure.
	 */
	public BufferedImage screenshot() throws IOException, WebDriverException
	{
		byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

		InputStream in = new ByteArrayInputStream(screenshotBytes);
		BufferedImage bufferedImage = ImageIO.read(in);

		return bufferedImage;
	}
}
