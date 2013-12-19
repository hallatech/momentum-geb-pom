package com.spindrift.momentum.modules

import com.spindrift.momentum.web.MomentumModule

class LoginModule extends MomentumModule {

	//module parameters
	def formClass
	def buttonName

	//content DSL
	static content = {
		form  { $('div',class:formClass).find("form") }
		button { form.find('input', type:'submit', value:buttonName) }
	}


}
