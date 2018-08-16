package co.il.nmh.easy.selenium.core.wrappers;

import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.security.UserAndPassword;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

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
	private NavigateWrapper navigateWrapper;

	public ActionWrapper(WebDriver driver, NavigateWrapper navigateWrapper)
	{
		super(driver);

		this.navigateWrapper = navigateWrapper;
	}

	public void setTextboxValue(WebElement element, String value)
	{
		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript("arguments[0].focus();", element);

		element.clear();
		element.sendKeys(value);

		js.executeScript("arguments[0].blur();", element);
	}

	public void click(WebElement element, MouseButton mouseButton)
	{
		Actions actions = new Actions(driver);

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
	}

	public void scrollToElement(WebElement element)
	{
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
	}

	public void focus(WebElement element, boolean focus)
	{
		JavascriptExecutor js = (JavascriptExecutor) driver;

		if (focus)
		{
			js.executeScript("arguments[0].focus();", element);
		}

		else
		{
			js.executeScript("arguments[0].blur();", element);
		}
	}

	public void mouseHover(WebElement element)
	{
		Actions action = new Actions(driver);
		action.moveToElement(element).build().perform();
	}

	public void dragAndDrop(WebElement element, int x, int y)
	{
		Actions actions = new Actions(driver);
		actions.dragAndDropBy(element, x, y);
	}

	public void dragAndDrop(WebElement sourceElement, WebElement destinationElement)
	{
		Actions actions = new Actions(driver);
		actions.dragAndDrop(sourceElement, destinationElement);
	}

	public String execJs(String script)
	{
		driver.switchTo().defaultContent();

		JavascriptExecutor js = (JavascriptExecutor) driver;
		Object res = js.executeScript(script);

		String answer = res != null ? res.toString() : null;
		return answer;
	}

	public void handleAlert(AlertAction alertAction, int timeOutInSeconds) throws SeleniumActionTimeout
	{
		EasyWebDriverWait easyWebDriverWait = new EasyWebDriverWait(driver, timeOutInSeconds);

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

	}

	public void authentication(String username, String password, int timeOutInSeconds) throws SeleniumActionTimeout
	{
		if (driver instanceof InternetExplorerDriver)
		{
			EasyWebDriverWait easyWebDriverWait = new EasyWebDriverWait(driver, timeOutInSeconds);

			Alert alert = easyWebDriverWait.until(ExpectedConditions.alertIsPresent());
			alert.authenticateUsing(new UserAndPassword(username, password));
		}

		else
		{
			try
			{
				EasyWebDriverWait easyWebDriverWait = new EasyWebDriverWait(driver, 0);

				Alert alert = easyWebDriverWait.until(ExpectedConditions.alertIsPresent());
				alert.dismiss();
			}

			catch (Exception e)
			{
			}

			int i = 0;

			String baseURL = navigateWrapper.getUrl();
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

			navigateWrapper.navigate(loginUrl, timeOutInSeconds);
		}
	}

	public void sendKeys(String keys)
	{
		driver.switchTo().activeElement().sendKeys(keys);
	}

	public void sendKeys(WebElement element, String keys)
	{
		element.sendKeys(keys);
	}

	public void sendKey(Keys key)
	{
		driver.switchTo().activeElement().sendKeys(key);
	}

	public void sendKey(WebElement element, Keys key)
	{
		element.sendKeys(key);
	}

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

			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", element);
			js.executeScript("arguments[0].click();", element);
		}

		return true;
	}
}
