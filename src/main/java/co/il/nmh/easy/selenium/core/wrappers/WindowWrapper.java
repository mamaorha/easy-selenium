package co.il.nmh.easy.selenium.core.wrappers;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import co.il.nmh.easy.selenium.core.DriverWrapper;
import co.il.nmh.easy.selenium.utils.InputValidationUtils;

/**
 * @author Maor Hamami
 */

public class WindowWrapper extends DriverWrapper
{
	public WindowWrapper(WebDriver driver)
	{
		super(driver);
	}

	public void setSize(int width, int height)
	{
		InputValidationUtils.INSTANCE.validateMinimumValue(1, width, "width");
		InputValidationUtils.INSTANCE.validateMinimumValue(1, height, "height");

		driver.manage().window().setSize(new Dimension(width, height));
	}

	public void fullScreen()
	{
		driver.manage().window().fullscreen();
	}

	public void maximize()
	{
		driver.manage().window().maximize();
	}

	public BufferedImage screenshot() throws IOException
	{
		byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

		InputStream in = new ByteArrayInputStream(screenshotBytes);
		BufferedImage bufferedImage = ImageIO.read(in);

		return bufferedImage;
	}
}
