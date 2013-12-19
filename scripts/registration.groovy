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
import com.spindrift.web.pages.*

def r = (new Random()).nextInt(10000)
def user=[
	'login':"superman${r}",
	'firstName':'Clark',
	'lastName':'Kent',
	'email':"superman${r}@krypton.com",
	'password':'Password1',
]
def welcomeMessage = "Welcome back!"
def userExistsMessage = "A user already exists with the login ${user.login}"


Browser.drive {
	to RegisterPage
	assert at(RegisterPage)
	
	// Fill in registration form and submit
	$('input', name:'login').value(user.login)
	$('input', name:'firstName').value(user.firstName)
	$('input', name:'lastName').value(user.lastName)
	$('input', name:'email').value(user.email)
	$('input', name:'password').value(user.password)
	$('input', name:'confirmPassword').value(user.password)
	$('input', name:'autoLogin').value(true)
	$('input', type:'submit', value:'Register').click()
	
	//Confirm registration
	assert at(HomePage)
	
	// As the text is spread over a few lines just check it contains the name and the welcome string
	def messageToTest = $('p', class:'primary-login-register').text()
	assert  messageToTest.contains('Clark') && messageToTest.contains(welcomeMessage)
}

