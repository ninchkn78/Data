data
====

This project uses data about [baby names from the US Social Security Administration] 
(https://www.ssa.gov/oact/babynames/limits.html) to answer specific questions. 

### TODO 
-optional 10
-zip file
-handle empty files
-how to throw exceptions
-refactor the 3 repeated methods
-complete readme
-main method


Name: Alex Chao

### Timeline

Start Date: 08/22/20

Finish Date: 

Hours Spent: 31 hours 0 minutes

### Resources Used
handling URL sources
https://stackoverflow.com/questions/6932369/inputstream-from-a-url
https://docs.oracle.com/javase/tutorial/networking/urls/readingURL.html
http://theoryapp.com/read-from-url-in-java/
https://www.baeldung.com/convert-file-to-input-stream
how to handle exception assertion 
https://www.baeldung.com/junit-assert-exception
Make first letter capital 
https://stackoverflow.com/questions/3904579/how-to-capitalize-the-first-letter-of-a-string-in-java
Getting Integers from a string
https://www.geeksforgeeks.org/extract-all-integers-from-the-given-string-in-java/
Reading Files from a Folder
https://www.netjstech.com/2017/04/reading-all-files-in-folder-java-program.html#:~:text=Java%20Example%20Code%20to%20read,recent%20addition%20in%20Java%208.
Removing Duplicates
https://devqa.io/java-remove-duplicates-from-list/
Initializing ArrayList with Zeroes
https://stackoverflow.com/questions/5600668/how-can-i-initialize-an-arraylist-with-all-zeroes-in-java
### Running the Program

Main class:

Data files needed: 

Key/Mouse inputs:

Cheat keys:

Known Bugs:

Extra credit:


### Notes/Assumptions
Lines are in the format Name, Gender as M/F, Occurences 
Lines are sorted in decreasing order by occurences 
Name of text files only contain numbers that are the year 


### Impressions

Do i want start and end year to be in the methods or in the output 
should i add a function that loops over years 

having readfiles in its own class allows it to access different data

CHANGES: READFILES taking in year, then year range, then data folder 
change getName and getRank by taking in a dictionary instead 
generalized starting letter to also be able to take starting stirn g

even though there's more questions at the end, lots of thinking in the beginning to make
the end easier

check for when years are missing based off of the get 

prefix is counting names, could adapt get top names method? 

need to make constants public 