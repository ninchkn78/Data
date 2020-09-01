package names;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

//ask about having databases be made in the call

class OutputTest {

  //don't have a beforeEach because none of my methods being tested
  //change the state of any of the instance variables
  Outputter Test = new Outputter("Test");

  @Test
  void Test1EmptyDataSet() {
    assertEquals("NAME NOT FOUND", Test.topNames(0));
  }

  @Test
  void Test1YearNotInDataSet() {
    assertEquals("YEAR NOT IN DATABASE", Test.topNames(8));
  }

  @Test
  void Test1YearInDataSet() {
    assertEquals("Alex\nMiryam", Test.topNames(1));
  }


  @Test
  void Test2YearNotInDataSet() {
    assertEquals("YEAR NOT IN DATABASE", Test.countNamesAndBabies(1899, "A", "M"));
  }

  @Test
  void Test2YearInDataSet() {
    assertEquals("Names: 2\nBabies: 4800", Test.countNamesAndBabies(3, "A", "M"));
  }

  @Test
  void Test2NameNotFound() {
    assertEquals("Names: 0\nBabies: 0", Test.countNamesAndBabies(0, "Y", "M"));
  }

  @Test
  void Basic1FemaleRanks() {
    List<Integer> expectedOutput = Arrays.asList(-1,1,2,3,4,5);
    assertEquals(expectedOutput, Test.getRanks("Miryam", "F"));
  }

  @Test
  void Basic1MaleRanks() {
    List<Integer> expectedOutput = Arrays.asList(-1,1,2,3,4,5);
    assertEquals(expectedOutput, Test.getRanks("Alex", "M"));
  }

//    @Test
//    void Test1EmptyFile() {
//        List<String> expectedOutput = Collections.singletonList("NAME NOT FOUND");
//        assertEquals(expectedOutput, Test.getRanks("Alex", "M"));
//    }

  @Test
  void Basic1NameNotFound() {
    List<Integer> expectedOutput = Arrays
        .asList(-1,-1,-1,-1,-1,-1);
    assertEquals(expectedOutput, Test.getRanks("J", "M"));
  }

  @Test
  void Basic2FemaleNameInDataSet() {
    assertEquals("Sophie F", Test.getTodayName(1, "Miryam", "F"));
  }

  @Test
  void Basic2MaleNameInDataSet() {
    assertEquals("Matt M", Test.getTodayName(3, "Alex", "M"));
  }

  @Test
  void Basic2NameNotInDataSet() {
    assertEquals("NAME NOT FOUND", Test.getTodayName(3, "Jackie", "M"));
    assertEquals("NAME NOT FOUND", Test.getTodayName(3, "Jackie", "F"));
  }

  @Test
  void Basic2YearNotInDataSet() {
    assertEquals("NAME NOT FOUND", Test.getTodayName(7, "Miryam", "F"));
  }

  @Test
  void Basic3SingleName() {
    assertEquals("Jared 2", Test.mostPopularName(1, 5, "M"));
  }

  @Test
  void Basic3MultipleNames() {
    assertEquals("Miryam Michelle Lucy 1", Test.mostPopularName(1, 3, "F"));
  }

  @Test
  void Basic3NameNotInDataSet() {
    assertEquals("NAME NOT FOUND", Test.mostPopularName(0, 0, "F"));
  }

  @Test
  void Basic3YearNotInDataSet() {
    assertEquals("INVALID RANGE", Test.mostPopularName(1, 6, "F"));
  }

  @Test
  void Basic4TiesAmongYears() {
    List<String> expectedOutput = Collections.singletonList("Giselle");
    assertEquals(expectedOutput, Test.mostPopularLetter(3, 4));
  }

  @Test
  void Basic4MultipleNamesAmongYears() {
    List<String> expectedOutput = Arrays.asList("Megan", "Michelle", "Miryam");
    assertEquals(expectedOutput, Test.mostPopularLetter(1, 4));
  }

  @Test
  void Basic4SingleNameAmongYears() {
    List<String> expectedOutput = Collections.singletonList("Sophie");
    assertEquals(expectedOutput, Test.mostPopularLetter(1, 5));
  }

  @Test
  void Basic4NoNames() {
    List<String> expectedOutput = Collections.emptyList();
    assertEquals(expectedOutput, Test.mostPopularLetter(0, 0));
  }

  @Test
  void Basic4YearNotInDataset() {
    assertEquals(Collections.singletonList("INVALID RANGE"), Test.mostPopularLetter(1, 6));
  }

 //have one test that checks all the errors
  //make a note about how the funciton doesn't matter since it's
  //the first thing that happens


//    @Test
//    void Test1FemaleRanks() {
//        List<String> expectedOutput = Arrays.asList("1", "2", "3", "4", "5");
//        assertEquals(expectedOutput, Test.getRanks(1, 5, "Miryam", "F"));
//    }
//
//    @Test
//    void Test1MaleRanks() {
//        List<String> expectedOutput = Arrays.asList("1", "2", "3", "4", "5");
//        assertEquals(expectedOutput,Test.getRanks(1, 5, "Alex", "M"));
//    }
//
//    @Test
//    void Test1EmptyFile() {
//        List<String> expectedOutput = Collections.singletonList("NAME NOT FOUND");
//        assertEquals(expectedOutput, Test.getRanks(0, 0, "Alex", "M"));
//    }
//
//    @Test
//    void Test1NameNotFound() {
//        List<String> expectedOutput = Arrays.asList("NAME NOT FOUND", "NAME NOT FOUND", "NAME NOT FOUND", "NAME NOT FOUND", "NAME NOT FOUND");
//        assertEquals(expectedOutput, Test.getRanks(1, 5, "J", "M"));
//}
}