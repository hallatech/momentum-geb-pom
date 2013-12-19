/**
 * Base configuration for GEB Web testing
 * Most of this has been taken from the GEB manual itself and either set the same as the defaults or commented out.
 * Adjust as necessary
 */
import org.openqa.selenium.firefox.FirefoxDriver

import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver


/**
 * Options for web drivers
 * Short Name	Driver
 * htmlunit		org.openqa.selenium.htmlunit.HtmlUnitDriver
 * firefox		org.openqa.selenium.firefox.FirefoxDriver
 * ie					org.openqa.selenium.ie.InternetExplorerDriver
 * chrome			org.openqa.selenium.chrome.ChromeDriver
 */
// default is to use firefox
driver = { new FirefoxDriver() }

/**
 * Ability to override default per environment, using geb.env
 */
environments {
	 // when system property 'geb.env' is set to 'win-ie' use a remote IE driver
	 'win-ie' {
			 driver = {
					 new RemoteWebDriver(new URL("http://windows.ci-server.local"), DesiredCapabilities.internetExplorer())
			 }
	 }
}

/**
 * Sets the base URL for the tests, which can be overridden at environment level.
 * Default to development and also set if geb.env=dev
 * This is the default Momentum URL for dev
 */
baseUrl = "http://localhost:8080"
environments {
	'dev' {
		baseUrl = "http://localhost:8080"
	}
	'cloud' {
		baseUrl = "http://live.uat.momentum.com"
	}
}

/**
 * Presets for waiting, e.g. AJAX requests
 * Defaults are:
 * timeout = 5 secs
 * retryInterval = 0.1
 * 
 */
//waiting {
//	timeout = 5
//	retryInterval = 0.1
//}

/**
 * At checkers can be configured to be implictly wrapped with waitFor calls. 
 * The possible values for the atCheckWaiting option are consistent with the ones for content definition and can be one of the following:
 * true - wait for the content using the default wait configuration
 * a string - wait for the content using the wait preset with this name from the configuration
 * a number - wait for the content for this many seconds, using the default retry interval from the configuration
 * a 2 element list of numbers - wait for the content using element 0 as the timeout seconds value, and element 1 as the retry interval seconds value
 */
//atCheckWaiting = true

/**
 * Unexpected pages
 * e.g. PageNotFoundPage, InternalServerErrorPage
 * These pages must be defined
 */
unexpectedPages = [PageNotFoundPage]

/**
 * By default Geb will take a report at the end of each test method, regardless of whether it ended successfully or not. 
 * The reportOnTestFailureOnly setting allows you to specify that a report should be taken only if a failure occurs.
 * This might be useful as a way to speed up large test suites.
 * Currently only supported by testNG - so check if valid for other test frameworks if you want to enable
 */
//reportOnTestFailureOnly = true

/**
 * Certain integrations will automatically clear the driver’s cookies, which is usually necessary when using an implicit driver.
 * This configuration flag, which is true by default, can be disabled by setting the autoClearCookies value in the config to false.
 */
//autoClearCookies = false

/**
 * Runtime overrides
 * The Configuration object also has setters for all of the config properties it exposes, allowing you to override config properties 
 * at runtime in particular circumstances if you need to.
 * def setup() {
 * 	browser.config.autoClearCookies = false << or which ever config you need to override
 * }
 */

/**
 * Site specific configuration
 */
momentum {
	siteTitlePrefix = 'Spindrift Momentum ‚Äî '
	homePageTitleSuffix = 'Welcome to the home of online shopping.'
	homePageTitle = "${siteTitlePrefix}${homePageTitleSuffix}"
	registrationPageTitle = "${siteTitlePrefix}Register"
	loginPageTitle = "${siteTitlePrefix}Login"
	accountHubPageTitle = "${siteTitlePrefix}My Account"
	
	homePageUrl = '/'
}

