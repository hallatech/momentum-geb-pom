package com.spindrift.web.spock

import geb.spock.GebSpec
import groovy.lang.MetaClass;

import com.spindrift.web.pages.*

class RegisteredUserLoginSpec extends GebSpec {
	
	def user=[
		'login':"superman",
		'firstName':'Clark',
		'lastName':'Kent',
		'email':"superman@krypton.com",
		'password':'Password1',
	]
	def welcomeMessage = "Welcome back!"

	def "Navigate to login page and login successfully"() {
		given:
		to LoginPage
		
		expect:
		at LoginPage
		
		when:
		$('input', id:'login-field').value(user.login)
		$('input', id:'password-field').value(user.password)
		$('input', type:'submit', value:'Login').click()
		
		then:
		at AccountHubPage
		
		and:
		def messageToTest = $('p', class:'primary-login-register').text()
		assert  messageToTest.contains('Clark') && messageToTest.contains(welcomeMessage)
		
	}
}
