package co.il.nmh.easy.selenium.core.wrappers;

import java.util.List;
import java.util.Optional;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.security.UserAndPassword;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import co.il.nmh.easy.selenium.EasySeleniumBrowser;
import co.il.nmh.easy.selenium.core.DriverWrapper;
import co.il.nmh.easy.selenium.core.EasyWebDriverWait;
import co.il.nmh.easy.selenium.enums.AlertAction;
import co.il.nmh.easy.selenium.enums.MouseButton;
import co.il.nmh.easy.selenium.enums.SelectType;
import co.il.nmh.easy.selenium.exceptions.SeleniumActionTimeout;

/**
 * @author Maor Hamami
 */

public class ActionWrapper extends DriverWrapper
{
	public ActionWrapper(EasySeleniumBrowser easySeleniumBrowser)
	{
		super(easySeleniumBrowser);
	}

	/**
	 * 
	 * Use this method to set a textbox value, this will trigger: focus of the element, invoking clear to remove the old data, sending keys to the element, unfocus the element
	 * 
	 * @param element
	 *            we wish to modify
	 * @param value
	 *            we wish the element will have
	 */
	public ActionWrapper setTextboxValue(WebElement element, String value)
	{
		JavascriptExecutor js = (JavascriptExecutor) easySeleniumBrowser.driver();

		js.executeScript("arguments[0].focus();", element);

		element.clear();
		element.sendKeys(value);

		js.executeScript("arguments[0].blur();", element);

		return this;
	}

	/**
	 * Use this method to click on webElement, this will trigger: moving to a given element, clicking it
	 * 
	 * @param element
	 *            we wish to click
	 * @param mouseButton
	 *            that will be used
	 */
	public ActionWrapper click(WebElement element, MouseButton mouseButton)
	{
		Actions actions = new Actions(easySeleniumBrowser.driver());

		switch (mouseButton)
		{
			case LEFT:
				actions.moveToElement(element).click().perform();
				break;

			case RIGHT:
				actions.moveToElement(element);
				actions.contextClick(element).build().perform();
				break;

			case DOUBLE_CLICK:
				actions.moveToElement(element).doubleClick().perform();
				break;
		}

		return this;
	}

	/**
	 * Use this method to scroll to a specific webElement so its visible in the browser
	 * 
	 * @param element
	 *            we wish to scroll to
	 */
	public ActionWrapper scrollToElement(WebElement element)
	{
		((JavascriptExecutor) easySeleniumBrowser.driver()).executeScript("arguments[0].scrollIntoView(true);", element);
		return this;
	}

	/**
	 * Use this method to focus and unfocus a webElement
	 * 
	 * @param element
	 *            we wish to focus/unfocus
	 * @param focus
	 *            true/false
	 */
	public ActionWrapper focus(WebElement element, boolean focus)
	{
		JavascriptExecutor js = (JavascriptExecutor) easySeleniumBrowser.driver();

		if (focus)
		{
			js.executeScript("arguments[0].focus();", element);
		}

		else
		{
			js.executeScript("arguments[0].blur();", element);
		}

		return this;
	}

	/**
	 * Use this method to perform a mouse hover
	 * 
	 * @param element
	 *            we wish to perform mouse hover on
	 */
	public ActionWrapper mouseHover(WebElement element)
	{
		Actions action = new Actions(easySeleniumBrowser.driver());
		action.moveToElement(element).build().perform();

		return this;
	}

	/**
	 * Use this method to drag and drop a webElement to a specific x,y coordinates
	 * 
	 * @param element
	 *            we wish to drag and drop
	 * @param x
	 *            position
	 * @param y
	 *            position
	 */
	public ActionWrapper dragAndDrop(WebElement element, int x, int y)
	{
		Actions actions = new Actions(easySeleniumBrowser.driver());
		actions.dragAndDropBy(element, x, y);

		return this;
	}

	/**
	 * Use this method to drag and drop a webElement on another webElement. for example dragging an item to shopping cart
	 * 
	 * @param sourceElement
	 *            webElement we wish to drag
	 * @param destinationElement
	 *            webElement we wish to drop to
	 */
	public ActionWrapper dragAndDrop(WebElement sourceElement, WebElement destinationElement)
	{
		Actions actions = new Actions(easySeleniumBrowser.driver());
		actions.dragAndDrop(sourceElement, destinationElement);

		return this;
	}

	/**
	 * Executes JavaScript in the context of the currently selected frame or window. The script fragment provided will be executed as the body of an anonymous function.
	 *
	 * <p>
	 * Within the script, use <code>document</code> to refer to the current document. Note that local variables will not be available once the script has finished executing, though global variables will persist.
	 *
	 * <p>
	 * If the script has a return value (i.e. if the script contains a <code>return</code> statement), then the following steps will be taken:
	 *
	 * <ul>
	 * <li>For an HTML element, this method returns a WebElement</li>
	 * <li>For a decimal, a Double is returned</li>
	 * <li>For a non-decimal number, a Long is returned</li>
	 * <li>For a boolean, a Boolean is returned</li>
	 * <li>For all other cases, a String is returned.</li>
	 * <li>For an array, return a List&lt;Object&gt; with each object following the rules above. We support nested lists.</li>
	 * <li>Unless the value is null or there is no return value, in which null is returned</li>
	 * </ul>
	 *
	 * <p>
	 * Arguments must be a number, a boolean, a String, WebElement, or a List of any combination of the above. An exception will be thrown if the arguments do not meet these criteria. The arguments will be made available to the JavaScript via the "arguments" magic variable, as if the function were called via "Function.apply"
	 *
	 * @param script
	 *            The JavaScript to execute
	 * @param args
	 *            The arguments to the script. May be empty
	 * @return One of Boolean, Long, Double, String, List or WebElement. Or null.
	 */
	public Optional<Object> execJs(String script, Object... args)
	{
		easySeleniumBrowser.driver().switchTo().defaultContent();

		JavascriptExecutor js = (JavascriptExecutor) easySeleniumBrowser.driver();
		Object res = js.executeScript(script, args);

		return res != null ? Optional.of(res) : Optional.ofNullable(res);
	}

	/**
	 * Use this method to handle simple alert box that require accept or dismiss
	 * 
	 * @param alertAction
	 * @param timeOutInSeconds
	 *            how much seconds to wait until alert will pop
	 * @throws SeleniumActionTimeout
	 */
	public ActionWrapper handleAlert(AlertAction alertAction, int timeOutInSeconds) throws SeleniumActionTimeout
	{
		EasyWebDriverWait easyWebDriverWait = new EasyWebDriverWait(easySeleniumBrowser.driver(), timeOutInSeconds);

		Alert alert = easyWebDriverWait.until(ExpectedConditions.alertIsPresent());

		switch (alertAction)
		{
			case ACCEPT:
				alert.accept();
				break;

			case DISMISS:
				alert.dismiss();
				break;
		}

		return this;
	}

	/**
	 * Use this method to handle authentication popup
	 * 
	 * @param username
	 * @param password
	 * @param timeOutInSeconds
	 *            how much seconds to wait until alert will pop
	 * @throws SeleniumActionTimeout
	 */
	public ActionWrapper authentication(String username, String password, int timeOutInSeconds) throws SeleniumActionTimeout
	{
		if (easySeleniumBrowser.driver() instanceof InternetExplorerDriver)
		{
			EasyWebDriverWait easyWebDriverWait = new EasyWebDriverWait(easySeleniumBrowser.driver(), timeOutInSeconds);

			Alert alert = easyWebDriverWait.until(ExpectedConditions.alertIsPresent());
			alert.authenticateUsing(new UserAndPassword(username, password));
		}

		else
		{
			try
			{
				EasyWebDriverWait easyWebDriverWait = new EasyWebDriverWait(easySeleniumBrowser.driver(), 0);

				Alert alert = easyWebDriverWait.until(ExpectedConditions.alertIsPresent());
				alert.dismiss();
			}

			catch (Exception e)
			{
			}

			int i = 0;

			String baseURL = easySeleniumBrowser.getUrl();
			String protocol = "http://";

			if (baseURL.toLowerCase().startsWith("https://"))
			{
				protocol = "https://";
				i = protocol.length();
			}

			else if (baseURL.toLowerCase().startsWith("http://"))
			{
				i = protocol.length();
			}

			String loginUrl = protocol + username + ":" + password + "@" + baseURL.substring(i);

			easySeleniumBrowser.navigator().navigate(loginUrl, timeOutInSeconds);
		}

		return this;
	}

	/**
	 * Use this method to simulate typing into an active element, which may set its value.
	 *
	 * @param keys
	 *            character sequence to send to the element
	 */
	public ActionWrapper sendKeys(String keys)
	{
		easySeleniumBrowser.driver().switchTo().activeElement().sendKeys(keys);

		return this;
	}

	/**
	 * Use this method to simulate typing into an element, which may set its value.
	 *
	 * @param keys
	 *            character sequence to send to the element
	 */
	public ActionWrapper sendKeys(WebElement element, String keys)
	{
		element.sendKeys(keys);
		return this;
	}

	/**
	 * Use this method to simulate typing into an active element, which may set its value.
	 *
	 * @param key
	 *            special key to send to the element
	 */
	public ActionWrapper sendKey(Keys key)
	{
		easySeleniumBrowser.driver().switchTo().activeElement().sendKeys(key);
		return this;
	}

	/**
	 * Use this method to simulate typing into an element, which may set its value.
	 *
	 * @param key
	 *            special key to send to the element
	 */
	public ActionWrapper sendKey(WebElement element, Keys key)
	{
		element.sendKeys(key);
		return this;
	}

	/**
	 * Use this method to choose an item from a comboBox
	 * 
	 * @param element
	 *            we wish to choose item from
	 * @param selectType
	 *            how do we with to select
	 * @param value
	 *            what we wish to select
	 * @return true if choosing an element was a success
	 */
	public boolean chooseFromComboBox(WebElement element, SelectType selectType, String value)
	{
		try
		{
			Select select = new Select(element);

			switch (selectType)
			{
				case INDEX:
					select.selectByIndex(Integer.valueOf(value));
					break;

				case VALUE:
					select.selectByValue(value);
					break;

				case TEXT:
					select.selectByVisibleText(value);
					break;
			}
		}

		catch (Exception e)
		{
			List<WebElement> elements = element.findElements(By.tagName("li"));
			element = null;

			switch (selectType)
			{
				case INDEX:
					element = elements.get(Integer.valueOf(value));
					break;

				case VALUE:
				case TEXT:
					for (WebElement webElement : elements)
					{
						String text = webElement.getAttribute("innerHTML");

						if (text.equals(value))
						{
							element = webElement;
							break;
						}
					}
					break;
			}

			if (element == null)
			{
				return false;
			}

			JavascriptExecutor js = (JavascriptExecutor) easySeleniumBrowser.driver();
			js.executeScript("arguments[0].click();", element);
			js.executeScript("arguments[0].click();", element);
		}

		return true;
	}
}
