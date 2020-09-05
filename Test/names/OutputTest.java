package names;

import java.util.ArrayList;
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
  Outputter Test2 = new Outputter("Test2");

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
    List<Integer> expectedOutput = Arrays.asList(0,1,2,3,4,5);
    assertEquals(expectedOutput, Test.getRanks("Miryam", "F"));
  }

  @Test
  void Basic1MaleRanks() {
    List<Integer> expectedOutput = Arrays.asList(0,1,2,3,4,5);
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
        .asList(0,0,0,0,0,0);
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
    assertEquals("Lucy Michelle Miryam 1", Test.mostPopularName(1, 3, "F"));
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
    assertEquals(expectedOutput, Test.mostPopularLetter(2, 5));
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


  @Test
  void Complete1FemaleRanks() {
    List<Integer> expectedOutput = Arrays.asList(1, 2, 3, 4, 5);
    assertEquals(expectedOutput, Test.getRanksFromRange(1, 5, "Miryam", "F"));
  }

  @Test
  void Complete1MaleRanks() {
    List<Integer> expectedOutput = Arrays.asList(1, 2, 3, 4, 5);
    assertEquals(expectedOutput,Test.getRanksFromRange(1, 5, "Alex", "M"));
  }

  @Test
  void Complete1EmptyFile() {
    List<Integer> expectedOutput = Collections.singletonList(0);
    assertEquals(expectedOutput, Test.getRanksFromRange(0, 0, "Alex", "M"));
  }

  @Test
  void Complete1NameNotFound() {
    List<Integer> expectedOutput = Arrays.asList(0, 0, 0, 0, 0);
    assertEquals(expectedOutput, Test.getRanksFromRange(1, 5, "J", "M"));
  }

  @Test
  void Complete2NegativeChange() {
    assertEquals(-4, Test.rankChange(1, 5, "Miryam", "F"));
    assertEquals(-4, Test.rankChange(1,5,"Alex","M"));
  }

  @Test
  void Complete2PositiveChange() {
    assertEquals(6, Test.rankChange(1, 5, "Sophie", "F"));
  }

  @Test
  void Complete2NoChange() {
    assertEquals(0, Test.rankChange(1, 4, "Jerry", "M"));
  }

  @Test
  void Complete2NameNotInBothYears() {
    assertEquals(0, Test.rankChange(1, 3, "Sophie", "F"));
  }

  @Test
  void Complete3SingleName() {
    List<String> expectedOutput = Collections.singletonList("Sophie");
    assertEquals(expectedOutput, Test.biggestRankChange(1, 5, "F"));
  }

  @Test
  void Complete3MultipleNames() {
    List<String> expectedOutput = Arrays.asList("Albert","Alex");
    assertEquals(expectedOutput, Test.biggestRankChange(2, 3, "M"));
  }

  //note: names that only appear in one year count as no rank change
  @Test
  void Complete3NoRankChanges() {
    List<String> expectedOutput = new ArrayList<>();
    assertEquals(expectedOutput, Test2.biggestRankChange(1999, 2000, "M"));
  }
  @Test
  void Complete3EmptyFile() {
    List<String> expectedOutput = new ArrayList<>();
    assertEquals(expectedOutput, Test.biggestRankChange(0, 0, "M"));
  }

  @Test
  void Complete4NameInSomeYears() {
    assertEquals(3.0, Test.getAverageRankRange(1, 5, "Sophie", "F"));
  }

  @Test
  void Complete4NameInAllYears() {
    assertEquals(3, Test.getAverageRankRange(1, 5, "Miryam", "F"));
    assertEquals(3, Test.getAverageRankRange(1, 5, "Alex", "M"));
  }

  @Test
  void Complete4NameNotInRange() {
    assertEquals(0, Test.getAverageRankRange(1, 5, "Sophie", "M"));
  }

  @Test
  void Complete5MultipleNames() {
    List<String> expectedOutput = Arrays.asList("Lucy", "Michelle", "Sophie");
    assertEquals(expectedOutput, Test.highestAverageRank(2, 5, "F"));
  }

  @Test
  void Complete5SingeName() {
    List<String> expectedOutput = Arrays.asList("Alex");
    assertEquals(expectedOutput, Test2.highestAverageRank(1999, 2000, "M"));
  }


  @Test
  void Complete5EmptyDataFile() {
    List<String> expectedOutput = new ArrayList<>();
    assertEquals(expectedOutput, Test.highestAverageRank(0,0, "M"));
  }

  @Test
  void Complete6NameInSomeYears() {
    assertEquals(1.0, Test.getAverageRankRecent(4, "Sophie", "F"));
  }

  @Test
  void Complete6NameInAllYears() {
    assertEquals(3, Test.getAverageRankRecent(5, "Miryam", "F"));
    assertEquals(3, Test.getAverageRankRecent(5, "Alex", "M"));
  }

  @Test
  void Complete6NameNotInRange() {
    assertEquals(0, Test.getAverageRankRecent(5, "Sophie", "M"));
  }

  @Test
  void Complete7SameNameRank1() {
    List<String> expectedOutput = Arrays.asList("Miryam","Miryam");
    assertEquals(expectedOutput, Test2.namesOfRank(1999, 2000, "F", 1));
  }

  @Test
  void Complete7MultipleNamesRank3() {
    List<String> expectedOutput = Arrays.asList("Prateek","Albert","Alex","Jeffrey");
    assertEquals(expectedOutput, Test.namesOfRank(1, 4, "M", 3));
  }

  @Test
  void Complete7EmptyFile() {
    List<String> expectedOutput = Arrays.asList("NAME NOT FOUND");
    assertEquals(expectedOutput, Test.namesOfRank(0, 0, "F", 1));
  }
  @Test
  void Complete8SingleNameRank2() {
    assertEquals("Jerry 2", Test.mostFrequentRank(1, 5, "M", 2));
  }

  @Test
  void Complete8MultipleNamesRank1() {
    assertEquals("Lucy Michelle Sophie 1", Test.mostFrequentRank(2,4, "F", 1));
  }

  @Test
  void Complete8NameNotInDataSet() {
    assertEquals("NAME NOT FOUND", Test.mostFrequentRank(0, 0, "F", 1));
  }



}