Feature: add

    

    Scenario Outline: correct difficulty that should be used
        Given the input "<input>"
        When the TaskList program is run
        Then the output should be "<output>"



Examples:

|input     |output              |
|input.txt | expectedoutput1.txt|