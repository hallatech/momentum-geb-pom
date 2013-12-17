package com.spindrift.web.spock

import geb.spock.GebSpec
import groovy.lang.MetaClass;

import com.spindrift.web.pages.*

class HomePageSpec extends GebSpec {

	def "Home Page should be valid for the environment"() {
		given:
		to HomePage
		
		expect:
		at HomePage
	}
}
