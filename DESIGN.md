data
====

### Name: Alex Chao

### Design goals

I wanted to create a base of reusable simple but general methods that could interact with each other
or be called with specific parameters to answer more interesting or complicated questions. This 
will ideally make new features easy to implement since they can just call the other methods that
have already exist. Also, I made constants for anything that was accessing the dataset so that a 
differently formatted file could be easily accounted for, such as one that has more information than
just name, gender, and count. Since the DataReader class is static and operates independently, it 
will also be easy to handle different file formats or read in additional meanings files by adding
a method to that class. 

### High level design 

The DataReader class reads in the information from the source and outputs a data structure for the
other two classes to work with. The DataProcesser class contains helper methods that retrieve specific
information and are then used by Outputter to answer all of the provided questions. 

### Assumptions

I assumed that all years in the data set would have a standard format and that years would not be
missing between the start and end year. I also assumed that each name only had one rank. 

### New features

Any new type of data being read in could be added by adding a case statement in the DataReader 
generateBabyNamesDataSet method and then writing the corresponding method to handle it. New genders
can be handled by creating another meanings map for that gender and adding the gender to the GENDERS
constant. To answer more questions, anything that could be repeated could be added as a method in 
the DataProcessor class, and then the question that uses these methods would be added in the 
Outputter class. 