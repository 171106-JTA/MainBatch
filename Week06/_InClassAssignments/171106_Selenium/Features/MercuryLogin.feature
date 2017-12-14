Feature: Mercury Tours Login
 	I wish to login to Mercury using proper credentials.

  Scenario: Logging in to Mercury Tours
		Given I am at the login screen for Mercury Tours
		When I input my username and my password and click submit
						| username	| password	|
						| bobbert		| bobbert		|
						|	tropicana	|	tropicana	|
						|	goodbye		| hello			|
						| bobbert		| bobbert		|
						|	tropicana	|	tropicana	|
						|	goodbye		| hello			|
		Then I shall be redirected to the find flights page


#Remember, examples require 'Scenario Outline:'
#	Examples: 
#		| username	| password	|
#		| bobbert		| bobbert		|
#		|	tropicana	|	tropicana	|
#		|	goodbye		| hello			|
#		| bobbert		| bobbert		|
#		|	tropicana	|	tropicana	|
#		|	goodbye		| hello			|
		
