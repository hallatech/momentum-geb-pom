momentum-geb-pom
================

Momentum User tests with Geb using inline scripting only via gradle, extending the Page Object Model.

The tests assume you have Momentum Storefront running on localhost on port 8080, either local on your host or in a VM with port forwarding.

Includes the gradle wrapper if you don't have gradle installed.

The default gradle task is 'test' so you don't need to include an actual task.

The following command will launch the tests with firefox browser only:

    ./gradlew test
    ./gradlew

or if you have gradle already installed:

    gradle test
    gradle
    
The defaults will leave the browser windows open where the test completes. To close the browser after each test add the -Pclose option

	./gradlew -Pclose
	gradle -Pclose
