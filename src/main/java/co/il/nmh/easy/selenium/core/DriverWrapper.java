package co.il.nmh.easy.selenium.core;

import org.openqa.selenium.WebDriver;

/**
 * @author Maor Hamami
 */

public abstract class DriverWrapper
{
	protected WebDriver driver;

	public DriverWrapper(WebDriver driver)
	{
		this.driver = driver;
	}
}
