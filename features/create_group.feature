Feature: Create Group
	Used to create the groups which tasks will be stored in
	groups is an array that stores all of the current groups
	
	Scenario: Add non-existing group (no groups of the same name)
		Given group does not exist
			When user selects create group
				And user selects OK
			Then group name exists in the groups array
	
	Scenario: Add existing group
		Given group does exist
			When user selects create group
				And user selects OK
			Then group name does not exist in the groups array