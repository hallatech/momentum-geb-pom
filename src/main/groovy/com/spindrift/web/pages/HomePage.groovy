package com.spindrift.web.pages

import geb.*
import groovy.lang.MetaClass;

class HomePage extends MomentumPage {

	// Defines this page's location relative to the configured base url in GebConfig.groovy
	static url = '/'

	// at checkers allow verifying that the browser is at the expected page
	static at = { title == momentum.homePageTitle }

}
