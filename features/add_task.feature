Feature: Add Task
	Used to create tasks within a specific group
	Each group object has an array of tasks called tasks
	currentGroup is the group that the user is currently in
	
	Scenario: Add non-existing task to currentGroup
		Given task does not exist in currentGroup.tasks array
			When user selects create task
				And user selects OK
			Then task name exists in the currentGroup.tasks array
	
	Scenario: Add task that already exists in currentGroup
		Given task does exist in currentGroup.tasks array
			When user selects create task
				And user selects OK
			Then task name does not exist in the currentGroup.tasks array