# Data Plan
## Alex Chao

This is the link to the [assignment](http://www.cs.duke.edu/courses/compsci307/current/assign/01_data/):


### What is the answer to the two questions below for the data file yob1900.txt (pick a letter that makes it easy to answer)? 
For the year 1900, Mary was the most common Female name and John was the most common Male name. For the year 1900, 3 
female names start with the letter Q for a total of 111 babies. 2 male names start with the letter Q for a total of 21
babies.
### Describe the algorithm you would use to answer each one.
Question 1: 
-find file with given year
-check the gender of the first name in the list
-store the name 
-read through the lines until reaching the other gender
-store the name
-output both names with their corresponding gender 

Question 2: 
-find file with given year 
-read through all the names, checking the first letter of the name and the gender
-for each gender, if the first letter of the name is the target letter, 
increment some counter and then add the count to a sum 
-output the counter and total sum for both genders 
### Likely you may not even need a data structure to answer the questions below, but what about some of the questions in Part 2?

### What are some ways you could test your code to make sure it is giving the correct answer (think beyond just choosing "lucky" parameter values)?

### What kinds of things make the second question harder to test?
The first question only requires returning one name from each gender based off of 
information we are given in the data (the number of occurences), so the algorithm is simpler. This also means that
it is easy to check if the returned value is correct because the data sets are sorted already. The second question 
requires us to look through all the names and keep track of two different sums. This means that it is more difficult to 
manually verify outputs with large datasets, so we would have to either take a subset or use letters that are not common 
as first letters of names. In both instances, since we are reducing the data, we have to be more careful about balancing 
out the comprehensiveness of the test with the feasibility of verifying than for the first question.

### What kind of errors might you expect to encounter based on the questions or in the dataset?
1. For the second question, no names in the dataset begin with the given letter. 
### How would you detect those errors and what would a reasonable "answer" be in such cases?
1. Can detect the error if the name counter for is 0. Could output something that said that no names were
found for that year. 
### How would your algorithm and testing need to change (if at all) to handle multiple files (i.e., a range of years)?

For the second question, the meat of the algorithm wouldn't change, but instead of outputting after reaching the end of file, 
I would loop through the range of years and then output after exiting the loop. 
