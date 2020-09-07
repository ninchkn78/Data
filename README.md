data
====

This project uses data about [baby names from the US Social Security Administration] 
(https://www.ssa.gov/oact/babynames/limits.html) to answer specific questions. 

### Name: Alex Chao

### Timeline

Start Date: 08/22/20

Finish Date: 09/07/20

Hours Spent: 38 hours 15 minutes

### Resources Used
Reading individual files in a zip 
https://stackoverflow.com/questions/15667125/read-content-from-files-which-are-inside-zip-file
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
getting help from office hours from Professor Duvall, Ben, Cayla, Ryan, and Christina 
### Running the Program

Main class: Main 
 
Resource root: data 
contains 
Folders with individual CSV text files containing data on babies' names
meanings.txt is a space separated text file of meanings of names

How to change data source: 
This program handles four types for the data source, a local folder, a URL, a local zip, and a URL 
zip. 
The Outputter object should be constructed with the name of the data source as the first argument 
and the type as the second (e.g. "LOCAL", "URL", "LOCAL_ZIP", "URL_ZIP").

Examples for each of these are included as commented out lines in the Main class. 

Extra credit: completed all optional parts in complete

### Handling Errors

If an invalid data source is passed in, the DataReader class will handle this  and create an 
empty data structure, then DataProcessor will throw an illegal argument exception. 

A data set that contains non sequential years (e.g. 1999, 2000, 2002) will cause an illegal argument 
exception. 

Names that are passed in without the correct casing will be properly formatted. Genders that are not
in the dataset and invalid ranges (the start is greater than the end or the full range is not 
included in the dataset) as arguments will cause an invalid parameter exception to be thrown. 

For methods that return names, "NAME NOT FOUND" will be returned if the specified parameters could not 
find a name in the data set. "YEAR NOT IN RANGE" will be returned if the year is not in the data set.

For methods that return ranks of names, 0 will be returned if the name could not be found. 

For methods that return counts, 0 will be returned for any names that could not be found or for a year
that is not in the dataset.  

### Notes/Assumptions
Assumptions about data source (with specific exclusive assumptions for different types):
All: 
-lines are in the format name,gender("M"/"F"),counts
*order,genders, and additional information can be changed by changing the INDEX and GENDERS constants 
-names are sorted in decreasing order of counts, with the first name starting at rank 1 and incrementing
by 1 per name within a gender.
-years are the only numbers in the name of any baby name data text file. 
-years are sequential within the dataSource 

Local Folder: 
no additional assumptions 

URL: 
-names of text file start with a prefix that can be set by changing the FILE_PREFIX constant

ZIP:
-any text file with numbers in its name is a relevant text file (the zip files contains a 
NationalReadMe)

Assumptions for meanings.txt
-line are formatted as: NAME GENDER MEANING 
-genders are "m", "f", "M", or "F"

Notes for prefix question: 
-the meaning returned is the longest prefix of the name with an entry in meanings.txt.
-meanings are separated by gender
-if no meaning is found, "(no meaning found)" is returned, which can be customized in the NO_MEANING
constant 

Notes for adding meanings to name: 
-The program only includes adding the meaning to methods that will return only a single name. For 
methods that returns names in a list, I didn't think this made a lot of sense to implement since 
it would quickly become very long and confusing especially with commas both separating names and 
included in the meanings (which sometimes include other names). However, to show that this would not
be difficult to implement, I added a method that is tested that runs through a list of names and 
modifies the names to add the meaning. A wantMeaning boolean could then be added as a parameter 
similarly to how my methods that return single names are structured. 

### Impressions




 