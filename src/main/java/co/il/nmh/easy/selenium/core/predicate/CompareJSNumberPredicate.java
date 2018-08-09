package co.il.nmh.easy.selenium.core.predicate;

import co.il.nmh.easy.selenium.core.wrappers.ActionWrapper;
import co.il.nmh.easy.selenium.enums.CompareNumber;

/**
 * @author Maor Hamami
 */

public class CompareJSNumberPredicate extends CompareNumberPredicate
{
	protected ActionWrapper actionWrapper;
	protected String script;

	public CompareJSNumberPredicate(String script, CompareNumber compareNumber, float expectedValue)
	{
		super(null, null, compareNumber, expectedValue);

		this.script = script;
	}

	@Override
	public String currValue()
	{
		return actionWrapper.execJs(script);
	}
}
