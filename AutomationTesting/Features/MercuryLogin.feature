#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template

Feature: Mercury Tours Login
  I wish to log in to Mercury Tours using proper credentials

	# If you wish to utilize multiple records for input, you must use 'Scenario Outline' as opposed to 
	# just 'Scenario'
  Scenario: Logging in to Mercury Tours
		Given I am at the login screen for Mercury Tours
		When I input my username and my password and click sign in
						| username	| password	|
						| bobbert		| bobbert		|
						|	tropicana	|	tropicana	|
						|	goodbye		| hello			|
						| jakeFromStateFarm | khakis    |
		Then I shall be redirected to the find flights page
  	#utilize 'examples:' with a table as written above to allow cucumber to run with multiple sets of 
  	
#Deprecated notes:
#When I input my username, "bobbert" and my password, "bobbert" and click submit   
## By using quotes, you allow for dynamic input on the spot