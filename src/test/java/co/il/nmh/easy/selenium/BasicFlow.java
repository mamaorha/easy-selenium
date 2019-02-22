package co.il.nmh.easy.selenium;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import co.il.nmh.easy.selenium.enums.AlertAction;
import co.il.nmh.easy.selenium.enums.BrowserType;
import co.il.nmh.easy.selenium.enums.SearchBy;
import co.il.nmh.easy.selenium.enums.WaitCondition;
import co.il.nmh.easy.selenium.exceptions.SeleniumActionTimeout;

/**
 * @author Maor Hamami
 */

public class BasicFlow
{
	@Test
	public void test() throws SeleniumActionTimeout
	{
		EasySeleniumBrowser easySeleniumBrowser = new EasySeleniumBrowser(BrowserType.CHROME);

		try
		{
			easySeleniumBrowser.navigator().navigate("google.com", 10);

			WebElement searchBox = easySeleniumBrowser.document().getElement(SearchBy.Name, "q", 0, WaitCondition.ELEMENT_CREATION, 5);

			easySeleniumBrowser.action().setTextboxValue(searchBox, "easy-selenium");
			easySeleniumBrowser.action().focus(searchBox, true).sendKey(Keys.ENTER);

			Optional<Object> execJs = easySeleniumBrowser.action().execJs("return document.getElementById('resultStats').innerHTML");
			Assert.assertNotNull(execJs.get());

			easySeleniumBrowser.action().execJs("alert('test')");
			easySeleniumBrowser.action().handleAlert(AlertAction.DISMISS, 5);
		}
		finally
		{
			easySeleniumBrowser.close();
		}
	}
}
