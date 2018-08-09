package co.il.nmh.easy.selenium;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import co.il.nmh.easy.selenium.enums.AlertAction;
import co.il.nmh.easy.selenium.enums.BrowserType;
import co.il.nmh.easy.selenium.enums.MouseButton;
import co.il.nmh.easy.selenium.utils.SearchBy;
import co.il.nmh.easy.selenium.utils.WaitCondition;

/**
 * @author Maor Hamami
 */

public class BasicFlow
{
	@Test
	public void test()
	{
		EasySeleniumBrowser easySeleniumBrowser = new EasySeleniumBrowser(BrowserType.Chrome);

		try
		{
			easySeleniumBrowser.getNavigator().navigate("google.com", 10);

			WebElement searchBox = easySeleniumBrowser.getDocument().getElement(SearchBy.ID, "lst-ib", 0, WaitCondition.ELEMENT_CREATION, 5);
			WebElement searchButton = easySeleniumBrowser.getDocument().getElement(SearchBy.Name, "btnK", 0, WaitCondition.ELEMENT_CREATION, 5);

			easySeleniumBrowser.getAction().setTextboxValue(searchBox, "easy-selenium");
			easySeleniumBrowser.getAction().click(searchButton, MouseButton.Left);

			String execJs = easySeleniumBrowser.getAction().execJs("return document.getElementById('resultStats').innerHTML");
			Assert.assertNotNull(execJs);

			easySeleniumBrowser.getAction().execJs("alert('test')");
			easySeleniumBrowser.getAction().handleAlert(AlertAction.DISMISS, 5);
		}
		finally
		{
			easySeleniumBrowser.close();
		}
	}
}
