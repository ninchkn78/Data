package names;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//ask about having databases be made in the call

class OutputTest {
    Output Test1900 = new Output(1900);
    Output Test1900_1950 = new Output(1900, 1950);
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

    @org.junit.jupiter.api.Test
    void countBabies() {
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