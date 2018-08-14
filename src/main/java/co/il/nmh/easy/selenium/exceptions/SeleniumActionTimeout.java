package co.il.nmh.easy.selenium.exceptions;

import org.openqa.selenium.TimeoutException;

/**
 * @author Maor Hamami
 */

public class SeleniumActionTimeout extends Exception
{
	private static final long serialVersionUID = 767561845449567290L;

	public SeleniumActionTimeout(TimeoutException e)
	{
		super(e);
	}
}
