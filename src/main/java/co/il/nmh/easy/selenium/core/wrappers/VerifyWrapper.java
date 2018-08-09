package co.il.nmh.easy.selenium.core.wrappers;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import co.il.nmh.easy.selenium.core.DriverWrapper;
import co.il.nmh.easy.selenium.core.predicate.AbstractComparePredicate;
import co.il.nmh.easy.selenium.core.predicate.CompareJSNumberPredicate;
import co.il.nmh.easy.selenium.core.predicate.CompareJSStringPredicate;
import co.il.nmh.easy.selenium.core.predicate.CompareNumberAdvancePredicate;
import co.il.nmh.easy.selenium.core.predicate.CompareNumberPredicate;
import co.il.nmh.easy.selenium.core.predicate.CompareStringAdvancePredicate;
import co.il.nmh.easy.selenium.core.predicate.CompareStringPredicate;
import co.il.nmh.easy.selenium.data.ImageVerificationResponse;
import co.il.nmh.easy.selenium.data.VerificationResponse;
import co.il.nmh.easy.selenium.enums.AttributeCompare;
import co.il.nmh.easy.selenium.enums.AttributeCompareAdvance;
import co.il.nmh.easy.selenium.enums.CompareNumber;
import co.il.nmh.easy.selenium.enums.CompareString;
import co.il.nmh.easy.selenium.utils.InputValidationUtils;

/**
 * @author Maor Hamami
 */

public class VerifyWrapper extends DriverWrapper
{
	protected ActionWrapper actionWrapper;
	protected NavigateWrapper navigateWrapper;
	protected WindowWrapper windowWrapper;

	public VerifyWrapper(WebDriver driver, ActionWrapper actionWrapper, NavigateWrapper navigateWrapper, WindowWrapper windowWrapper)
	{
		super(driver);

		this.actionWrapper = actionWrapper;
		this.navigateWrapper = navigateWrapper;
		this.windowWrapper = windowWrapper;
	}

	public VerificationResponse verifyNumber(WebElement element, AttributeCompare attributeCompare, CompareNumber comperator, float expectedValue, int timeoutForVerification)
	{
		CompareNumberPredicate predicate = new CompareNumberPredicate(element, attributeCompare, comperator, expectedValue);
		return verify(predicate, timeoutForVerification);
	}

	public VerificationResponse verifyNumber(WebElement element, AttributeCompareAdvance attributeCompare, CompareNumber comperator, String identifier, float expectedValue, int timeoutForVerification)
	{
		CompareNumberAdvancePredicate predicate = new CompareNumberAdvancePredicate(element, attributeCompare, comperator, identifier, expectedValue);
		return verify(predicate, timeoutForVerification);
	}

	public VerificationResponse verifyText(WebElement element, AttributeCompare attributeCompare, CompareString comperator, String expectedValue, boolean ignoreCase, int timeoutForVerification)
	{
		CompareStringPredicate predicate = new CompareStringPredicate(element, attributeCompare, comperator, expectedValue, ignoreCase);
		return verify(predicate, timeoutForVerification);
	}

	public VerificationResponse verifyText(WebElement element, AttributeCompareAdvance attributeCompare, CompareString comperator, String expectedValue, String identifier, boolean ignoreCase, int timeoutForVerification)
	{
		CompareStringAdvancePredicate predicate = new CompareStringAdvancePredicate(element, attributeCompare, comperator, identifier, expectedValue, ignoreCase);
		return verify(predicate, timeoutForVerification);
	}

	public VerificationResponse verifyJS(String script, CompareNumber comperator, float expectedValue, int timeoutForVerification, boolean ignoreCase)
	{
		CompareJSNumberPredicate predicate = new CompareJSNumberPredicate(script, comperator, expectedValue);
		return verify(predicate, timeoutForVerification);
	}

	public VerificationResponse verifyJS(String script, CompareString comperator, String expectedValue, boolean ignoreCase, int timeoutForVerification)
	{
		CompareJSStringPredicate predicate = new CompareJSStringPredicate(script, comperator, expectedValue, ignoreCase);
		return verify(predicate, timeoutForVerification);
	}

	public VerificationResponse verifyTitle(CompareString comperator, String expectedValue, boolean ignoreCase, int timeoutForVerification)
	{
		CompareStringPredicate predicate = new CompareStringPredicate(null, null, comperator, expectedValue, ignoreCase)
		{
			@Override
			public String currValue()
			{
				return navigateWrapper.getTitle();
			}
		};

		return verify(predicate, timeoutForVerification);
	}

	public VerificationResponse verifyURL(CompareString comperator, String expectedValue, boolean ignoreCase, int timeoutForVerification)
	{
		CompareStringPredicate predicate = new CompareStringPredicate(null, null, comperator, expectedValue, ignoreCase)
		{
			@Override
			public String currValue()
			{
				return navigateWrapper.getUrl();
			}
		};

		return verify(predicate, timeoutForVerification);
	}

	public ImageVerificationResponse verifyImage(BufferedImage expectedImage, Double matchPercent) throws IOException
	{
		InputValidationUtils.INSTANCE.validateMinimumValue(0, matchPercent, "matchPercent");

		BufferedImage screenshot = windowWrapper.screenshot();

		ImageVerificationResponse imageVerificationResponse = new ImageVerificationResponse();

		if (expectedImage.getHeight() != screenshot.getHeight() || expectedImage.getWidth() != screenshot.getWidth())
		{
			imageVerificationResponse.setInfo("failed to compare - images must be on the same size");
			return imageVerificationResponse;
		}

		ColorModel cm = expectedImage.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = expectedImage.copyData(null);

		expectedImage = new BufferedImage(cm, raster, isAlphaPremultiplied, null);

		int changes = 0;

		for (int x = 0; x < expectedImage.getWidth(); x++)
		{
			for (int y = 0; y < expectedImage.getHeight(); y++)
			{
				int expectedImageRGB = expectedImage.getRGB(x, y);
				int realImageRGB = screenshot.getRGB(x, y);

				if (expectedImageRGB != realImageRGB)
				{
					expectedImage.setRGB(x, y, (int) (((double) (expectedImageRGB + realImageRGB)) / 2));
					changes++;
				}
			}
		}

		imageVerificationResponse.setDelta(expectedImage);

		if (changes > 0)
		{
			int totalPixels = expectedImage.getHeight() * expectedImage.getWidth();
			double changePercentage = ((double) changes / totalPixels) * 100;
			changePercentage = new BigDecimal(changePercentage).setScale(2, RoundingMode.HALF_UP).doubleValue();

			imageVerificationResponse.setInfo("found " + changePercentage + "% diff");
			imageVerificationResponse.setSuccess(matchPercent <= changePercentage);
		}

		else
		{
			imageVerificationResponse.setInfo("matching images was ended with success");
			imageVerificationResponse.setSuccess(true);
		}

		return imageVerificationResponse;
	}

	private VerificationResponse verify(AbstractComparePredicate predicate, int timeoutForVerification)
	{
		InputValidationUtils.INSTANCE.validateMinimumValue(0, timeoutForVerification, "timeoutForVerification");

		WebDriverWait wait = new WebDriverWait(driver, timeoutForVerification);

		VerificationResponse verificationResponse = new VerificationResponse();

		try
		{
			wait.until(predicate);
			verificationResponse.setSuccess(true);
		}

		catch (TimeoutException ex)
		{
		}

		verificationResponse.setInfo(predicate.message(verificationResponse.isSuccess()));

		return verificationResponse;
	}
}
