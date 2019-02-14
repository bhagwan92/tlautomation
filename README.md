Automation Framework/Script Documentation

This is a PageFactory / Page Object  Framework. Which support re-usability of automation script and can handle element locators very easily for big project. 

Pre Requisite Software:
•	Eclipse 2018-12 (4.10.0)
•	Java 1.8
•	maven
•	TestNG 6.14.2
•	Appium java Client 6.0.0 BETA
•	Selenium 3.9.1
•	Appium 1.8.0
•	Apache POI (To read and update xls sheet data)



Architecture : It is maven project written in java language.

App Class : Contains Test Cases running with the help of TestNG.
Utility class : Contains basic functions which we can call from main and pageFactory classes with parameters.
config.properties : Contains configuration related parameters We can also use xls file for data handling using apache POI.
Log4j.properties : contains logging path. (Given log path is D:/automation/logs/temp.log modify accordingly) to maintain log history of all executions


Note : Please update the eBay login credential in config.properties file before start execution
eBayUser=  
eBayPassword=
