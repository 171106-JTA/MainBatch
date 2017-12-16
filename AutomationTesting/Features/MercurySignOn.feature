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
Feature: Mercury Tours Sign On
  I wish to sign on to Mercury Tours website

  Scenario: Signing on to Mercury Tours
    Given I am at the sign-on screen for Mercury Tours
    When I input my username and my password and click submit
    	| username	| password	|
						| bobbert		| bobbert		|
						|	tropicana	|	tropicana	|
						|	goodbye		| hello			|
						| jakeFromStateFarm | khakis    |
		Then I shall be redirected to the flight finder page

