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

Edit: this was my original thinking for the algorithms, but after starting to code I split up the steps into multiple methods
instead of just one read through so that each method was simpler and might be better adaptable for future questions.
### Likely you may not even need a data structure to answer the questions below, but what about some of the questions in Part 2?
Since the first two questions are fairly simple and only come from one year, it would be possible to implement a solution
that scans through the file once, keeps track of sums/names in local variables, and returns the output. However, this is not 
scalable to more complex questions that involve multiple years.
### What are some ways you could test your code to make sure it is giving the correct answer (think beyond just choosing "lucky" parameter values)?
Try multiple years for question one. For question two, test with more and less common beginning letters using smaller 
datasets. 
### What kinds of things make the second question harder to test?
The first question only requires returning one name from each gender based off of 
information we are given in the data (the number of occurences), so the algorithm is simpler. This also means that
it is easy to check if the returned value is correct because the data sets are sorted already. The second question 
requires us to look through all the names and keep track of two different sums. This means that it is more difficult to 
manually verify outputs with large datasets, so we would have to either take a subset or use letters that are not common 
as first letters of names. In both instances, since we are reducing the data, we have to be more careful about balancing 
out the comprehensiveness of the test with the feasibility of verifying than for the first question.

### What kind of errors might you expect to encounter based on the questions or in the dataset?
1. For question 1, either or both genders is missing 
2. For the second question, no names in the dataset begin with the given letter. 
### How would you detect those errors and what would a reasonable "answer" be in such cases?
1. Can detect the error if reached the end of file and haven't found a name for both genders
2. Can detect the error if the name counter for is 0. Could output 0 
### How would your algorithm and testing need to change (if at all) to handle multiple files (i.e., a range of years)?
For question 1, if I was trying to loop through multiple years and only return one value, then the algorithm for the first 
question would have to change to store the frequency of top ranked names for each year. Then at the end it would choose
the name with the most occurences. I would also have to track ties since we are examining more than one name across years.

If I was looping through multiple years and returning a value for each year, the algorithm would simply loop over several 
years, returning a value for each year. 

For question 2, the algorithm would loop over the range of years and depending on if a cumulative sum/count was wanted, 
it would either return at the end or have multiple calls and return for each year. 
