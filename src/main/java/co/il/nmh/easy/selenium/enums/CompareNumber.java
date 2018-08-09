package co.il.nmh.easy.selenium.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Maor Hamami
 */

public enum CompareNumber
{
	SMALLER("<"), BIGGER(">"), EQUAL("="), NOT_EQUAL("!="), SMALLER_EQUAL("<="), BIGGER_EQUAL(">=");

	private String value;
	private static Map<String, CompareNumber> namesMap = new HashMap<String, CompareNumber>();

	static
	{
		final CompareNumber[] values = CompareNumber.values();

		for (CompareNumber value : values)
		{
			namesMap.put(value.toString().toLowerCase(), value);
		}
	}

	private CompareNumber(String value)
	{
		this.value = value;
	}

	@Override
	public String toString()
	{
		return value;
	}

	public static CompareNumber forValue(String value)
	{
		CompareNumber temp = null;

		if (null != value)
		{
			temp = namesMap.get(value.toLowerCase());
		}

		if (temp == null)
		{
			throw new RuntimeException(value + " doesn't exist for enum CompareNumber");
		}

		return temp;
	}
}
