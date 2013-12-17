package com.spindrift.web.spock

import geb.spock.GebSpec
import groovy.lang.MetaClass;

import com.spindrift.web.pages.*

class RegisteredUserLoginSpec extends GebSpec {

	def "Navigate to login page and login successfully"() {
		given:
		to LoginPage
		
		expect:
		at LoginPage
	}
}
