Feature: removinggroupstasks

    Users removes groups and tasks to GUI.
	Saves the application.
	Exists.

    Scenario Outline: removing Groups and Tasks
        Given the input "<input>"
        When the TaskList program is run
        Then the output should be "<output>"



Examples:

|input     |output              |
|test4.txt | result4.txt|
