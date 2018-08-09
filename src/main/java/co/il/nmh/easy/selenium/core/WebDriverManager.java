package co.il.nmh.easy.selenium.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import co.il.nmh.easy.selenium.enums.BrowserType;

/**
 * @author Maor Hamami
 */

public class WebDriverManager
{
	public static WebDriverManager INSTANCE = new WebDriverManager();

	private List<WebDriver> drivers;

	private WebDriverManager()
	{
		drivers = new ArrayList<WebDriver>();
	}

	public WebDriver buildDriver(BrowserType browser)
	{
		WebDriver driver = null;

		Proxy proxy = new Proxy();
		proxy.setAutodetect(true);

		switch (browser)
		{
			case Chrome:
				driver = buildChrome(proxy);
				break;
			case FireFox:
				driver = buildFirefox();
				break;
			case IExplorer:
				driver = buildIExpolorer(proxy);
				break;
		}

		drivers.add(driver);

		return driver;
	}

	private WebDriver buildChrome(Proxy proxy)
	{
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("credentials_enable_service", false);
		prefs.put("profile.password_manager_enabled", false);

		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", prefs);
		options.addArguments("--no-sandbox");
		options.addArguments("--start-maximized");
		options.addArguments("--enable-automation");
		options.addArguments("--disable-infobars");
		// options.addArguments("--disable-extensions");
		// options.addArguments("--disable-extensions-file-access-check");
		// options.addArguments("--disable-extensions-http-throttling");
		options.addArguments("--disable-web-security");
		// options.addArguments("--no-proxy-server");

		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		capabilities.setCapability(CapabilityType.PROXY, proxy);
		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

		System.setProperty("webdriver.chrome.driver", "./chromedriver.exe");

		return new ChromeDriver(capabilities);
	}

	private WebDriver buildFirefox()
	{
		// Use FirefoxProfile Constructor
		FirefoxProfile myProfile = new FirefoxProfile();
		myProfile.setAcceptUntrustedCertificates(true);
		myProfile.setAssumeUntrustedCertificateIssuer(false);
		myProfile.setPreference("network.proxy.type", 4);

		DesiredCapabilities dc = DesiredCapabilities.firefox();
		dc.setCapability(FirefoxDriver.PROFILE, myProfile);
		dc.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

		System.setProperty("webdriver.gecko.driver", "./geckodriver.exe");

		return new FirefoxDriver(myProfile);
	}

	private WebDriver buildIExpolorer(Proxy proxy)
	{
		DesiredCapabilities dc = DesiredCapabilities.internetExplorer();
		dc.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		dc.setCapability(CapabilityType.PROXY, proxy);
		dc.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		System.setProperty("webdriver.iexplorer.driver", "./IEDriverServer.exe");
		System.setProperty("webdriver.ie.driver", "./IEDriverServer.exe");

		return new InternetExplorerDriver(dc);
	}

	public void close()
	{
		for (WebDriver webDriver : drivers)
		{
			try
			{
				webDriver.close();
				webDriver.quit();
			}

			catch (Throwable e)
			{
			}
		}
	}
}
