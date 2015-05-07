Given /^the input "(.*)"$/ do |arg|
    @input=arg
	#puts @input
end

When /^the TaskList program is run$/ do
    @output= `java -cp "bin" TaskListMain test #{@input}`
	#puts "\n"
    #puts @output
	
end
    
Then /^the output should be "(.*)"$/ do |result|
    	file = File.open(result, "r")
	contents = file.read
	file.close
	#puts contents
	raise ('wrong answer!!!!') unless @output.eql? contents
end