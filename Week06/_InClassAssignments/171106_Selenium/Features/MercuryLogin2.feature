Feature: Log into mercury

	Background:
		Given I am at the landing page of Mercury
		#Background designates a scenario that runs before every
		#Other scenario.

	@smoke
  Scenario: Can we even access then login
    When I login with "bobbert" and password "bobbert"
    Then I should be at the find flights page

	@regression @stuff
  Scenario Outline: Logging in with different accounts
    When I login with "<username>" and "<password>"
    Then I arrive at the findflights page

    Examples: 
			| username 	| password  |
			| bobbert 	| bobbert 	|
			| tropicana | tropicana |
			| goodbye		| hello			|
