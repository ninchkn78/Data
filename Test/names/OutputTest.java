package names;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

//ask about having databases be made in the call

class OutputTest {
    Output Test1900 = new Output(1900);
    Output Test1900_1950 = new Output(1900, 1950);
    Output Empty_File = new Output(0);
    Output Custom_Test = new Output(1,5);

    @Test
    void Basic1YearInSingleYearDataSet() {
        assertEquals("John\nMary", Test1900.topNames(1900));
    }
    @Test
    void Basic1YearNotInSingleYearDataSet() {
        assertEquals("YEAR NOT IN DATABASE", Test1900.topNames(1901));
    }
    @Test
    void Basic1YearInMultiYearDataSet() {
        assertEquals("Robert\nMary", Test1900_1950.topNames(1925));
    }

    @Test
    void Basic2YearInSingleYearDataSet() {
        assertEquals("Names: 3\nBabies: 111", Test1900.countNamesAndBabies(1900,'Q',"F"));
        assertEquals("Names: 2\nBabies: 21", Test1900.countNamesAndBabies(1900,'Q',"M"));
    }
    @Test
    void Basic2YearNotInSingleYearDataSet() {
        assertEquals("YEAR NOT IN DATABASE", Test1900.countNamesAndBabies(1901, 'A', "M"));
    }
    @Test
    void Basic2YearMultiYearDataSet() {
        assertEquals("Names: 7\nBabies: 344", Test1900_1950.countNamesAndBabies(1925, 'Q', "F"));
    }
    @Test
    void Basic2NameNotFound() {
        assertEquals("Names: 0\nBabies: 0", Empty_File.countNamesAndBabies(0, 'Y', "M"));
    }

    @Test
    void Test1FemaleRanks() {
        List<String> expectedOutput = Arrays.asList("1", "2", "3", "4", "5");
        assertEquals(expectedOutput,Custom_Test.getRanks(1,5,"Miryam","F"));
    }
    @Test
    void Test1MaleRanks() {
        List<String> expectedOutput = Arrays.asList("1", "2", "3", "4", "5");
        assertEquals(expectedOutput,Custom_Test.getRanks(1,5,"Alex","M"));
    }
    @Test
    void Test1EmptyFile() {
        List<String> expectedOutput = Collections.singletonList("NAME NOT FOUND");
        assertEquals(expectedOutput,Empty_File.getRanks(0,0,"Alex","M"));
    }
    @Test
    void Test1NameNotFound() {
        List<String> expectedOutput = Arrays.asList("NAME NOT FOUND","NAME NOT FOUND","NAME NOT FOUND","NAME NOT FOUND","NAME NOT FOUND");
        assertEquals(expectedOutput,Custom_Test.getRanks(1,5,"J","M"));
    }

    @org.junit.jupiter.api.Test
    void getTodayName() {
    }

    @org.junit.jupiter.api.Test
    void mostPopularName() {
    }

    @org.junit.jupiter.api.Test
    void mostPopularLetter() {
    }
}