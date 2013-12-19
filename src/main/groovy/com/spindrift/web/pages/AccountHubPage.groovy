package com.spindrift.web.pages

import geb.*
import groovy.lang.MetaClass
import com.spindrift.momentum.pages.MomentumAccountHubPage

class AccountHubPage extends MomentumAccountHubPage {

  //Add customisations to override generated class here

	static at = { title == momentum.accountHubPageTitle }
}