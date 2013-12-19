package com.spindrift.web.spock

import geb.spock.GebSpec

import com.spindrift.web.pages.*

class RegisteredUserAuthenticationSpec extends GebSpec {

	def user=[
		'login':"superman",
		'firstName':'Clark',
		'lastName':'Kent',
		'email':"superman@krypton.com",
		'password':'Password1',
	]
	def welcomeMessage = "Hello ${user.firstName}. Welcome back!"
	def loggedInMessage = "You are already logged in as ${user.firstName} ${user.lastName}.  Logout?"

	def "Navigate to login page and login successfully"() {
		given:
		to LoginPage

		expect:
		at LoginPage

		when:
		loginForm.login = user.login
		loginForm.password = user.password
		loginButton.click()

		then:
		at AccountHubPage

		and:
		$().text().contains("${welcomeMessage}")
	}

	def "Log in when already logged in"() {
		given:
		to LoginPage

		expect:
		at LoginPage

		when:
		loginForm.login = user.login
		loginForm.password = user.password
		loginButton.click()

		then:
		at AccountHubPage

		and:
		$().text().contains("${welcomeMessage}")

		then:
		to LoginPage

		expect:
		at LoginPage

		and:
		alreadyLoggedInMessage.text().contains("${loggedInMessage}")
	}

	def "Log out when logged in"() {
	}
}
