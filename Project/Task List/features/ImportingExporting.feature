Feature: importingexporting

    User Imports a previous save file
	User Saves
	User Exports the save file to another location

    Scenario Outline: import/export
        Given the input "<input>"
        When the TaskList program is run
        Then the output should be "<output>"



Examples:

|input     |output              |
|test2.txt | result2.txt|
