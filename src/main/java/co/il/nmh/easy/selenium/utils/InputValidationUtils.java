package co.il.nmh.easy.selenium.utils;

/**
 * @author Maor Hamami
 */

public class InputValidationUtils
{
	public static final InputValidationUtils INSTANCE = new InputValidationUtils();

	private InputValidationUtils()
	{
	}

	public void validateMinimumValue(int minValue, int value, String field)
	{
		if (value < minValue)
		{
			throw new IllegalArgumentException(field + " can't be lower than " + minValue);
		}
	}

	public void validateMinimumValue(int minValue, double value, String field)
	{
		if (value < minValue)
		{
			throw new IllegalArgumentException(field + " can't be lower than " + minValue);
		}
	}
}
