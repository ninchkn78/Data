package names;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

//ask about having databases be made in the call

class OutputTest {

  //don't have a beforeEach because none of my methods being tested
  //change the state of any of the instance variables
  Outputter TestFolder = new Outputter("Test", "FOLDER");
  Outputter TestFolder2 = new Outputter("Test2", "FOLDER");
  //Outputter URL = new Outputter("https://www2.cs.duke.edu/courses/fall20/compsci307d/assign/01_data/data/ssa_complete/", "URL");



//  @Test
//  void CheckURLDataSet() {
//    assertEquals("Mary 76", URL.mostPopularName(1880,2018,"F"));
//    assertEquals(Collections.singletonList("Loyd"),URL.biggestRankChange(1880,2018,"M"));
//  }


  @Test
  void HandleErrorNonExistentDataSource() {
  assertThrows(InvalidParameterException.class, () ->
        new Outputter("Test3","FOLDER"));
  assertThrows(InvalidParameterException.class, () ->
        new Outputter("www.com", "URL"));
    assertThrows(InvalidParameterException.class, () ->
        new Outputter("www.com", "URL_ZIP"));
  }

  @Test
  void HandleErrorEmptyFolder() {
    assertThrows(InvalidParameterException.class, () ->
        new Outputter("EmptyFile","FOLDER"));
  }
  @Test
  void HandleErrorName() {
    assertEquals("Sophie F", TestFolder.todayName(1, "miryam", "F"));
    assertEquals("Sophie F", TestFolder.todayName(1, "miRyAm", "F"));
  }

  @Test
  void HandleErrorGender() {
    Exception exception = assertThrows(InvalidParameterException.class, () ->
        TestFolder.todayName(1, "Miryam", "f"));

    String expectedMessage = "INVALID GENDER";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  void HandleErrorYear() {
    String expectedMessage = "INVALID RANGE";
    Exception exception1 = assertThrows(InvalidParameterException.class,
        () -> TestFolder.mostPopularFemaleStartingLetter(1, 6));
    String actualMessage1 = exception1.getMessage();
    assertTrue(actualMessage1.contains(expectedMessage));
    Exception exception2 = assertThrows(InvalidParameterException.class,
        () -> TestFolder.mostPopularFemaleStartingLetter(-1, 5));
    String actualMessage2 = exception2.getMessage();
    assertTrue(actualMessage2.contains(expectedMessage));
    Exception exception3 = assertThrows(InvalidParameterException.class,
        () -> TestFolder.mostPopularFemaleStartingLetter(5, 2));
    String actualMessage3 = exception3.getMessage();
    assertTrue(actualMessage3.contains(expectedMessage));
  }
  @Test
  void Test1EmptyDataSet() {
    assertEquals("NAME NOT FOUND", TestFolder.topMaleAndFemaleName(0));
  }

  @Test
  void Test1YearNotInDataSet() {
    assertEquals("YEAR NOT IN DATABASE", TestFolder.topMaleAndFemaleName(8));
  }

  @Test
  void Test1YearInDataSet() {
    assertEquals("Alex\nMiryam", TestFolder.topMaleAndFemaleName(1));
  }


  @Test
  void Test2YearNotInDataSet() {
    assertEquals("YEAR NOT IN DATABASE", TestFolder.countNamesAndBabies(1899, "A", "M"));
  }

  @Test
  void Test2YearInDataSet() {
    assertEquals("Names: 2\nBabies: 4800", TestFolder.countNamesAndBabies(3, "A", "M"));
  }

  @Test
  void Test2NameNotFound() {
    assertEquals("Names: 0\nBabies: 0", TestFolder.countNamesAndBabies(0, "Y", "M"));
  }

  @Test
  void Basic1FemaleRanks() {
    List<Integer> expectedOutput = Arrays.asList(0, 1, 2, 3, 4, 5);
    assertEquals(expectedOutput, TestFolder.ranksFromDataset("Miryam", "F"));
  }

  @Test
  void Basic1MaleRanks() {
    List<Integer> expectedOutput = Arrays.asList(0, 1, 2, 3, 4, 5);
    assertEquals(expectedOutput, TestFolder.ranksFromDataset("Alex", "M"));
  }

  @Test
  void Basic1NameNotFound() {
    List<Integer> expectedOutput = Arrays
        .asList(0, 0, 0, 0, 0, 0);
    assertEquals(expectedOutput, TestFolder.ranksFromDataset("J", "M"));
  }

  @Test
  void Basic2FemaleNameInDataSet() {
    assertEquals("Sophie F", TestFolder.todayName(1, "Miryam", "F"));
  }

  @Test
  void Basic2MaleNameInDataSet() {
    assertEquals("Matt M", TestFolder.todayName(3, "Alex", "M"));
  }

  @Test
  void Basic2NameNotInDataSet() {
    assertEquals("NAME NOT FOUND", TestFolder.todayName(3, "Jackie", "M"));
    assertEquals("NAME NOT FOUND", TestFolder.todayName(3, "Jackie", "F"));
  }

  @Test
  void Basic2YearNotInDataSet() {
    assertEquals("NAME NOT FOUND", TestFolder.todayName(7, "Miryam", "F"));
  }

  @Test
  void Basic3SingleName() {
    assertEquals("Jared 2", TestFolder.mostPopularName(1, 5, "M"));
  }

  @Test
  void Basic3MultipleNames() {
    assertEquals("Lucy Michelle Miryam 1", TestFolder.mostPopularName(1, 3, "F"));
  }

  @Test
  void Basic3NameNotInDataSet() {
    assertEquals("NAME NOT FOUND", TestFolder.mostPopularName(0, 0, "F"));
  }


  @Test
  void Basic4TiesAmongYears() {
    List<String> expectedOutput = Collections.singletonList("Giselle");
    assertEquals(expectedOutput, TestFolder.mostPopularFemaleStartingLetter(3, 4));
  }

  @Test
  void Basic4MultipleNamesAmongYears() {
    List<String> expectedOutput = Arrays.asList("Megan", "Michelle", "Miryam");
    assertEquals(expectedOutput, TestFolder.mostPopularFemaleStartingLetter(1, 4));
  }

  @Test
  void Basic4SingleNameAmongYears() {
    List<String> expectedOutput = Collections.singletonList("Sophie");
    assertEquals(expectedOutput, TestFolder.mostPopularFemaleStartingLetter(2, 5));
  }

  @Test
  void Basic4NoNames() {
    List<String> expectedOutput = Collections.emptyList();
    assertEquals(expectedOutput, TestFolder.mostPopularFemaleStartingLetter(0, 0));
  }

  @Test
  void Complete1FemaleRanks() {
    List<Integer> expectedOutput = Arrays.asList(1, 2, 3, 4, 5);
    assertEquals(expectedOutput, TestFolder.ranksFromRange(1, 5, "Miryam", "F"));
  }

  @Test
  void Complete1MaleRanks() {
    List<Integer> expectedOutput = Arrays.asList(1, 2, 3, 4, 5);
    assertEquals(expectedOutput, TestFolder.ranksFromRange(1, 5, "Alex", "M"));
  }

  @Test
  void Complete1EmptyFile() {
    List<Integer> expectedOutput = Collections.singletonList(0);
    assertEquals(expectedOutput, TestFolder.ranksFromRange(0, 0, "Alex", "M"));
  }

  @Test
  void Complete1NameNotFound() {
    List<Integer> expectedOutput = Arrays.asList(0, 0, 0, 0, 0);
    assertEquals(expectedOutput, TestFolder.ranksFromRange(1, 5, "J", "M"));
  }

  @Test
  void Complete2NegativeChange() {
    assertEquals(-4, TestFolder.rankChange(1, 5, "Miryam", "F"));
    assertEquals(-4, TestFolder.rankChange(1, 5, "Alex", "M"));
  }

  @Test
  void Complete2PositiveChange() {
    assertEquals(6, TestFolder.rankChange(1, 5, "Sophie", "F"));
  }

  @Test
  void Complete2NoChange() {
    assertEquals(0, TestFolder.rankChange(1, 4, "Jerry", "M"));
  }

  @Test
  void Complete2NameNotInBothYears() {
    assertEquals(0, TestFolder.rankChange(1, 3, "Sophie", "F"));
  }

  @Test
  void Complete3SingleName() {
    List<String> expectedOutput = Collections.singletonList("Sophie");
    assertEquals(expectedOutput, TestFolder.namesWithBiggestRankChange(1, 5, "F"));
  }

  @Test
  void Complete3MultipleNames() {
    List<String> expectedOutput = Arrays.asList("Albert", "Alex");
    assertEquals(expectedOutput, TestFolder.namesWithBiggestRankChange(2, 3, "M"));
  }

  //note: names that only appear in one year count as no rank change
  @Test
  void Complete3NoRankChanges() {
    List<String> expectedOutput = new ArrayList<>();
    assertEquals(expectedOutput, TestFolder2.namesWithBiggestRankChange(1999, 2000, "M"));
  }

  @Test
  void Complete3EmptyFile() {
    List<String> expectedOutput = new ArrayList<>();
    assertEquals(expectedOutput, TestFolder.namesWithBiggestRankChange(0, 0, "M"));
  }

  @Test
  void Complete4NameInSomeYears() {
    assertEquals(3.0, TestFolder.averageRank(1, 5, "Sophie", "F"));
  }

  @Test
  void Complete4NameInAllYears() {
    assertEquals(3, TestFolder.averageRank(1, 5, "Miryam", "F"));
    assertEquals(3, TestFolder.averageRank(1, 5, "Alex", "M"));
  }

  @Test
  void Complete4NameNotInRange() {
    assertEquals(0, TestFolder.averageRank(1, 5, "Sophie", "M"));
  }

  @Test
  void Complete5MultipleNames() {
    List<String> expectedOutput = Arrays.asList("Lucy", "Michelle", "Sophie");
    assertEquals(expectedOutput, TestFolder.namesWithHighestAverageRank(2, 5, "F"));
  }

  @Test
  void Complete5SingeName() {
    List<String> expectedOutput = Collections.singletonList("Alex");
    assertEquals(expectedOutput, TestFolder2.namesWithHighestAverageRank(1999, 2000, "M"));
  }


  @Test
  void Complete5EmptyDataFile() {
    List<String> expectedOutput = new ArrayList<>();
    assertEquals(expectedOutput, TestFolder.namesWithHighestAverageRank(0, 0, "M"));
  }

  @Test
  void Complete6NameInSomeYears() {
    assertEquals(1.0, TestFolder.recentAverageRank(4, "Sophie", "F"));
  }

  @Test
  void Complete6NameInAllYears() {
    assertEquals(3, TestFolder.recentAverageRank(5, "Miryam", "F"));
    assertEquals(3, TestFolder.recentAverageRank(5, "Alex", "M"));
  }

  @Test
  void Complete6NameNotInRange() {
    assertEquals(0, TestFolder.recentAverageRank(5, "Sophie", "M"));
  }

  @Test
  void Complete7SameNameRank1() {
    List<String> expectedOutput = Arrays.asList("Miryam", "Miryam");
    assertEquals(expectedOutput, TestFolder2.namesWithRank(1999, 2000, "F", 1));
  }

  @Test
  void Complete7MultipleNamesRank3() {
    List<String> expectedOutput = Arrays.asList("Prateek", "Albert", "Alex", "Jeffrey");
    assertEquals(expectedOutput, TestFolder.namesWithRank(1, 4, "M", 3));
  }

  @Test
  void Complete7EmptyFile() {
    List<String> expectedOutput = Collections.singletonList("NAME NOT FOUND");
    assertEquals(expectedOutput, TestFolder.namesWithRank(0, 0, "F", 1));
  }

  @Test
  void Complete8SingleNameRank2() {
    assertEquals("Jerry 2", TestFolder.mostFrequentRankedNames(1, 5, "M", 2));
  }

  @Test
  void Complete8MultipleNamesRank1() {
    assertEquals("Lucy Michelle Sophie 1", TestFolder.mostFrequentRankedNames(2, 4, "F", 1));
  }

  @Test
  void Complete8NameNotInDataSet() {
    assertEquals("NAME NOT FOUND", TestFolder.mostFrequentRankedNames(0, 0, "F", 1));
  }

  @Test
  void Complete9EmptyFile() {
    List<String> expectedOutput = new ArrayList<>();
    assertEquals(expectedOutput, TestFolder.mostCommonPrefix(0, 0, "F"));
  }

  @Test
  void Complete9MultiplePrefixes() {
    List<String> expectedOutput = Arrays.asList("Alex", "Symon");
    assertEquals(expectedOutput, TestFolder2.mostCommonPrefix(1999, 2000, "M"));
  }

  @Test
  void Complete9SinglePrefix() {
    List<String> expectedOutput = Collections.singletonList("Lucy");
    assertEquals(expectedOutput, TestFolder2.mostCommonPrefix(1999, 2000, "F"));
  }


}