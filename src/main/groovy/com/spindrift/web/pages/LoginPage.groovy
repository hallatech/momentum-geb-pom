package com.spindrift.web.pages

import geb.*

import com.spindrift.momentum.modules.AlreadyLoggedInModule
import com.spindrift.momentum.pages.MomentumLoginPage
class LoginPage extends MomentumLoginPage {

	static content = {
		loginForm { $('div',class:'account-login').find("form") }
		loginButton { $('div', class: 'account-login').find('input', type:'submit', value:'Login') }
		alreadyLoggedInMessage { module AlreadyLoggedInModule }
	}
}