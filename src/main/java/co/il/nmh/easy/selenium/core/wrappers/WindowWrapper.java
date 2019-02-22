package co.il.nmh.easy.selenium.core.wrappers;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver.Window;

import co.il.nmh.easy.selenium.EasySeleniumBrowser;
import co.il.nmh.easy.selenium.core.DriverWrapper;
import co.il.nmh.easy.selenium.utils.InputValidationUtils;

/**
 * @author Maor Hamami
 */

public class WindowWrapper extends DriverWrapper
{
	public WindowWrapper(EasySeleniumBrowser easySeleniumBrowser)
	{
		super(easySeleniumBrowser);
	}

	/**
	 * Use this method when you wish to set the size of the browser window. This will change the outer window dimension, not just the view port, synonymous to window.resizeTo() in JS.
	 * 
	 * @param width
	 *            - window new width
	 * @param height
	 *            - window new height
	 */
	public WindowWrapper setSize(int width, int height)
	{
		InputValidationUtils.INSTANCE.validateMinimumValue(0, width, "width");
		InputValidationUtils.INSTANCE.validateMinimumValue(0, height, "height");

		getDriverWindow().setSize(new Dimension(width, height));
		return this;
	}

	/**
	 * Use this method when you wish to make the browser window on fullscreen if it is not already fullscreen
	 */
	public WindowWrapper fullScreen()
	{
		getDriverWindow().fullscreen();
		return this;
	}

	/**
	 * Use this method when you wish to maximize the browser window if it is not already maximized
	 */
	public WindowWrapper maximize()
	{
		getDriverWindow().maximize();
		return this;
	}

	/**
	 * Use this method to set the position of the browser window. This is relative to the upper left corner of the screen, synonymous to window.moveTo() in JS.
	 * 
	 * @param x
	 * @param y
	 */
	public WindowWrapper setPosition(int x, int y)
	{
		getDriverWindow().setPosition(new Point(x, y));
		return this;
	}

	/**
	 * @return the interface for managing the current window, given by Selenium webDriver.
	 */
	public Window getDriverWindow()
	{
		return easySeleniumBrowser.driver().manage().window();
	}
}
