Feature: Customization
	Used to change the appearance of the application
	currentTheme is the theme that the user is currently using
	
	Scenario: change theme
		Given theme selected is not currentTheme
			When user selects new theme
				And user selects OK
			Then currentTheme is updated to the theme selected
	
	Scenario: Reorder group
		Given group does exist 
			When user selects reorder group
				And user selects OK
			Then user will be able to reorder group