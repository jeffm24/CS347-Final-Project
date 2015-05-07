Feature: sorting

    Users multiple tasks to group
	Users sorts the tasks in several different ways
	Exists.

    Scenario Outline: Sorting by various variables
        Given the input "<input>"
        When the TaskList program is run
        Then the output should be "<output>"



Examples:

|input     |output              |
|test3.txt | result3.txt|
