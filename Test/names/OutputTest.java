package names;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//ask about having databases be made in the call

class OutputTest {
    Output Test1900 = new Output(1900);
    Output Test1900_1950 = new Output(1900, 1950);
    Output Empty_File = new Output(2019);
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
        assertEquals("Names: 0\nBabies: 0", Empty_File.countNamesAndBabies(2019, 'Y', "M"));
    }

    @org.junit.jupiter.api.Test
    void getRanks() {
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