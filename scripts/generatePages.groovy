	buildscript {
	repositories {
	  mavenCentral()
	}
	dependencies {
	  classpath "org.gebish:geb-core:0.9.2"
	  classpath "org.seleniumhq.selenium:selenium-firefox-driver:2.38.0"
	}
}

/**
 * This script generates Page (Object Modle) classes by extracting data from the Momentum URL Manager in the BCC and then finding the equivalent
 * page on the live site to get the page titles.
 */

//Define a temporary page class

class SitePage {
	String url
	String name	
	String title
	String toString() {
		"URL=${url},name=${name},title=${title}"
	}
}

import geb.Browser
import org.openqa.selenium.firefox.FirefoxDriver



def browser = new Browser(driver: new FirefoxDriver())

// Set base URL
def bccUrl = "http://localhost:8180/atg/bcc"
def storefrontUrl = "http://localhost:8080"
def user=[
	'name':"admin",
	'password':'password1',
]
def bccPageTitle='Oracle ATG Web Commerce Business Control Center'
def rootTitle = "Spindrift Momentum — "
def homePageTitle = "${rootTitle}Welcome to the home of online shopping."

ArrayList<SitePage> momPages = new ArrayList<SitePage>()

//First go to the BCC and extract URL and page names
browser.baseUrl = bccUrl
try {
	// Go to homepage - base Url
	browser.go()
	assert browser.page.title == bccPageTitle
	browser.$('input', id:'loginName').value(user.name)
	browser.$('input', id:'loginPassword').value(user.password)
	browser.$('input', type:'submit', value:'Log In').click()
	
	//Page has same title as login so assert something else as well
	assert browser.page.title == bccPageTitle
	assert browser.$('a', class:'opTitle', text:'Site Builder URL Management')
	browser.$('td',id:'childSiteBuilderActivitySource').find('td',align:'right').find('a',title:'Browse').click()
	// IN browse mode on the URL pages
	assert browser.page.title == 'SSB Site Administration'
	browser.$('a', class:'sitemanager-site-sitemap-link').click()
	//As its an ajax request we must wait for the response
	browser.waitFor { browser.$('div',id:'sitemanager-sitemap-urls').find('tr').size() > 0 }
	//Process all the rows creating page objects
	def tableRows = browser.$('div',id:'sitemanager-sitemap-urls').find('tr')
	if (tableRows.size() > 0) {
		for (def row: tableRows) {
			def newPage = new SitePage()
			newPage.url = row.find('td',0).text()
			newPage.name = row.find('td',2).text()
			if (newPage.url != null) {
				momPages.add(newPage)
			}
		}
	}
}
finally {
	if (hasProperty('close')) {
		browser.close()
	}
}

//Add a home page in case one wasn't generated
def homePage = new SitePage()
homePage.url = '/'
homePage.name = 'homePage'
momPages.add(homePage)

println "BCC URL Pages found: ${momPages}"

// Now we attempt to get the page titles for the pages
browser = new Browser(driver: new FirefoxDriver())
browser.baseUrl = storefrontUrl
try {
	// Go to homepage - base Url
	browser.go()
	assert browser.page.title == homePageTitle
	// Now process the pages and populate page titles
	for (SitePage sitePage : momPages ) {
		browser.go sitePage.url
		//Do some kind of asserting - lets assume for now no page title is the same as the home page
		//TODO - There are some pages requiring login so we need to either cater for that or let them through for now with a manual fix later
		if (!homePageTitle.equals(browser.page.title)) {
			sitePage.title = browser.page.title
		}
		else {
			println "There was a potential problem accessing the url: ${sitePage.url}, title was not updated"
		}
	}
}
finally {
	if (hasProperty('close')) {
		browser.close()
	}
}

def numSuccessPages = momPages.findAll { p->p.title != null}.collect().size()
println "Total pages found: ${momPages.size()}"
println "Number of successful pages processed: ${numSuccessPages}"
println "Storefront URL Pages found: ${momPages}"

/**
 * Sanitize the pagename to a standardized class name
 * @param pageName
 * @return
 */
def sanitizePageClass(String pageName) {
	//This will need customisation depending on the standard used for creating page names
	def className = pageName.replaceAll("\\s","").replaceAll( "[\\d]", "" ) -'Page' -'page:' +'Page'
	className.capitalize()
}

//Finally we can generate page objects - first as a generated tier and secondly as a custom tier
File sourceDir= new File("${projectDir}/src/main/groovy")
def momPackageDirName = 'com/spindrift/momentum/pages'
def momPackageName = momPackageDirName.replace('/', '.')
File momPackageDir= new File(sourceDir, momPackageDirName)
if (!momPackageDir.exists()) momPackageDir.mkdirs()

def customPackageDirName = 'com/spindrift/web/pages'
def customPackageName = customPackageDirName.replace('/', '.')
File customPackageDir= new File(sourceDir, customPackageDirName)
if (!customPackageDir.exists()) customPackageDir.mkdirs()

for (SitePage sitePage : momPages ) {
	def pageName = sanitizePageClass(sitePage.name)
	new File("${momPackageDir}/Momentum${pageName}.groovy").withWriter { w->
		w << "package ${momPackageName}"
		w << "\n\nimport geb.*"
		w << "\nimport groovy.lang.MetaClass"
		w << "\nimport com.spindrift.momentum.web.MomentumPage"
		w << "\n\nclass Momentum${pageName} extends MomentumPage {"
		w << "\n\n  //Generated url derived from the BCC Site Builder URL Management"
		w << "\n  static url = '${sitePage.url}'"
		w << "\n\n  //At checker title derived from live site"
		w << "\n  static at = { title == '${sitePage.title}' }"
		w << "\n\n}"
	}
	//Only re-generate custom classes when requested otherwise any customisations would be lost
	//This will only override Page classes that do not exist. To override even customised classes you must also force it by specifying reInitialiseCustomisedClasses
	if (hasProperty('overrideCustomClasses')) {
		def customClassFile = new File("${customPackageDir}/${pageName}.groovy")
		if (!(customClassFile).exists() || (hasProperty('reInitialiseCustomisedClasses'))) {
			customClassFile.withWriter { w->
				w << "package ${customPackageName}"
				w << "\n\nimport geb.*"
				w << "\nimport groovy.lang.MetaClass"
				w << "\nimport ${momPackageName}.Momentum${pageName}"
				w << "\n\nclass ${pageName} extends Momentum${pageName} {"
				w << "\n\n  //Add customisations to override generated class here"
				w << "\n\n}"
			}
		}
	}
}


