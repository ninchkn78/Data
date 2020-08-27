package names;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//ask about having databases be made in the call

class OutputTest {

    @Test
    void topNames() {
        Output Test = new Output();
        assertEquals("John\nMary", Test.topNames(1900));
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