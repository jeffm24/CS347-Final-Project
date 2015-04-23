Feature: Change View
	Used to switch between calendar, list, and grid view
	currentView is the variable that determines the view
	0: list view, 1: grid view, 2: calendar view
	
	Scenario: Change to calendar view while in list view
		Given currentView == 0
			When user selects change view
				And user selects calendar view
			Then currentView == 2
	
	Scenario: Change to list view while in list view
		Given currentView == 0
			When user selects change view
				And user selects list view
			Then currentView == 0
	