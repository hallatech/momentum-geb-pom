// Script to test Momentum homepage access

// Add imported classes to classpath via buildscript {}
buildscript {
	repositories {
		mavenCentral()
	}
	dependencies {
		classpath "org.gebish:geb-core:0.9.1"
		classpath "org.seleniumhq.selenium:selenium-firefox-driver:2.38.0"
		// Add the Momentum Page Object classes
		classpath files("${buildDir}/classes/main")
		// Add the GebConfig.groovy configuration
		classpath files("${buildDir}/resources/test")
	}
}

// Groovy inline script to test homepage access
import geb.Browser
import com.spindrift.web.pages.RegistrationPage

Browser.drive {
	to RegistrationPage
	assert at(RegistrationPage)
}

