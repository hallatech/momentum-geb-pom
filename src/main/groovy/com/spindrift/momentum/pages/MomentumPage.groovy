package com.spindrift.momentum.pages

import geb.Page
import groovy.lang.MetaClass;

class MomentumPage extends Page {
				
				/**
				 * Gets the extended configuration for Momentum from the standard GebConfig.groovy
				 * Set configuration as follows:
				 * momentum {
				 *   siteTitle = "Spindrift Momentum"
				 * }
				 * In your Page extending this class get at the siteTitle from configuration
				 *   title == momentum.homePageTitle
				 * @param configProperty
				 * @return
				 */
				
				def getMomentum() {
								browser.config.rawConfig.momentum
				}
				
				
}