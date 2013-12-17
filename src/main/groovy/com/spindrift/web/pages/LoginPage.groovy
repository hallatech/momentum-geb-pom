package com.spindrift.web.pages

import geb.Page
import groovy.lang.MetaClass;

class LoginPage extends MomentumPage {

	// Defines this page's location relative to the configured base url in GebConfig.groovy
	static url = '/login'

	// at checkers allow verifying that the browser is at the expected page
	static at = { title == momentum.loginPageTitle }

}
