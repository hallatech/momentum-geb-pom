package com.spindrift.momentum.modules

import com.spindrift.momentum.web.MomentumModule

class AlreadyLoggedInModule extends MomentumModule {

	static content = {
		alreadyLoggedInMessage { $('div',class:'content').find('div',class:'span8') }
	}
}
