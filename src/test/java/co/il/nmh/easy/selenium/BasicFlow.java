package co.il.nmh.easy.selenium;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import co.il.nmh.easy.selenium.enums.AlertAction;
import co.il.nmh.easy.selenium.enums.BrowserType;
import co.il.nmh.easy.selenium.enums.MouseButton;
import co.il.nmh.easy.selenium.exceptions.SeleniumActionTimeout;
import co.il.nmh.easy.selenium.utils.SearchBy;
import co.il.nmh.easy.selenium.utils.WaitCondition;

/**
 * @author Maor Hamami
 */

public class BasicFlow
{
	@Test
	public void test() throws SeleniumActionTimeout
	{
		EasySeleniumBrowser easySeleniumBrowser = new EasySeleniumBrowser(BrowserType.Chrome);

		try
		{
			easySeleniumBrowser.navigator().navigate("google.com", 10);

			WebElement searchBox = easySeleniumBrowser.document().getElement(SearchBy.ID, "lst-ib", 0, WaitCondition.ELEMENT_CREATION, 5);
			WebElement searchButton = easySeleniumBrowser.document().getElement(SearchBy.Name, "btnK", 0, WaitCondition.ELEMENT_CREATION, 5);

			easySeleniumBrowser.action().setTextboxValue(searchBox, "easy-selenium");
			easySeleniumBrowser.action().click(searchButton, MouseButton.Left);

			String execJs = easySeleniumBrowser.action().execJs("return document.getElementById('resultStats').innerHTML");
			Assert.assertNotNull(execJs);

			easySeleniumBrowser.action().execJs("alert('test')");
			easySeleniumBrowser.action().handleAlert(AlertAction.DISMISS, 5);
		}
		finally
		{
			easySeleniumBrowser.close();
		}
	}
}
