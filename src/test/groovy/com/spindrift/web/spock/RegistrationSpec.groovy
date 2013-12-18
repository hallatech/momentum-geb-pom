package com.spindrift.web.spock

import geb.spock.GebSpec
import groovy.lang.MetaClass;

import com.spindrift.web.pages.*

class RegistrationSpec extends GebSpec {

	def "Navigate to registration page and register successfully"() {
		given:
		to RegisterPage
		
		expect:
		at RegisterPage
	}
}
