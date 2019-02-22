package co.il.nmh.easy.selenium.core.predicate;

import co.il.nmh.easy.selenium.core.wrappers.ActionWrapper;
import co.il.nmh.easy.selenium.enums.CompareString;

/**
 * @author Maor Hamami
 */

public class CompareJSStringPredicate extends CompareStringPredicate
{
	protected ActionWrapper actionWrapper;
	protected String script;

	public CompareJSStringPredicate(String script, CompareString compareString, String expectedValue, boolean ignoreCase)
	{
		super(null, null, compareString, expectedValue, ignoreCase);

		this.script = script;
	}

	@Override
	public String currValue()
	{
		return actionWrapper.execJs(script).map((res) -> res.toString()).orElse(null);
	}
}
