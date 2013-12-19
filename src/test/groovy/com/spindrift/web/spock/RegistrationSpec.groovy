package com.spindrift.web.spock

import geb.spock.GebSpec
import groovy.lang.MetaClass;

import com.spindrift.web.pages.*

class RegistrationSpec extends GebSpec {
	
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

	def "Navigate to registration page and register successfully"() {
		given:
		to RegisterPage
		
		expect:
		at RegisterPage
		
		when:
		$('input', name:'login').value(user.login)
		$('input', name:'firstName').value(user.firstName)
		$('input', name:'lastName').value(user.lastName)
		$('input', name:'email').value(user.email)
		$('input', name:'password').value(user.password)
		$('input', name:'confirmPassword').value(user.password)
		$('input', name:'autoLogin').value(true)
		$('input', type:'submit', value:'Register').click()
		
		then:
		at HomePage
		
		and:
		def messageToTest = $('p', class:'primary-login-register').text()
		assert  messageToTest.contains('Clark') && messageToTest.contains(welcomeMessage)
		
	}
}
