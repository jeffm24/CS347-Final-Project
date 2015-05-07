Feature: addinggroupstasks

    Users adds groups and tasks to GUI.
	Saves the application.
	Exists.

    Scenario Outline: adding Groups and Tasks
        Given the input "<input>"
        When the TaskList program is run
        Then the output should be "<output>"



Examples:

|input     |output              |
|test1.txt | result1.txt|
