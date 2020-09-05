package names;

import static java.lang.StrictMath.abs;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

//talk to TA about this class
//ask about comments

/**
 * Feel free to completely change this code or delete it entirely.
 */
public class Outputter {

  /**
   * Start of the program.
   */
  private final int dataStartYear;
  private final int dataEndYear;
  private final DataProcessor process;
  private final static String YEAR_ERROR = "YEAR NOT IN DATABASE";
  private final static String RANGE_ERROR = "INVALID RANGE";
  private final static String NAME_ERROR = "NAME NOT FOUND";
  private final static String GENDER_ERROR = "INVALID GENDER";
  private final static List<String> GENDERS = Arrays.asList("M", "F");

  public Outputter(String dataSource, String dataType) {
    process = new DataProcessor(dataSource, dataType);
    dataStartYear = process.getDataFirstYear();
    dataEndYear = process.getDataLastYear();
  }

  public String topNames(int year) {
    String maleName = process.getNameFromRank(year, "M", 1);
    if (maleName.equals(NAME_ERROR)) {
      return NAME_ERROR;
    } else if (maleName.equals(YEAR_ERROR)) {
      return YEAR_ERROR;
    }
    String femaleName = process.getNameFromRank(year, "F", 1);
    return maleName + "\n" + femaleName;
  }

  public String countNamesAndBabies(int year, String startsWith, String gender) {
    validateGender(gender);
    int countNames = process.countNamesStartingWith(year, startsWith, gender);
    if (countNames == -1) {
      return YEAR_ERROR;
    }
    int totalBabies = process.countBabiesByYear(year, startsWith, gender);
    return "Names: " + countNames + "\nBabies: " + totalBabies;
  }

  public List<Integer> getRanks(String name, String gender) {
    name = validateName(name);
    validateGender(gender);
    return process.getRanks(dataStartYear, dataEndYear, name, gender);
  }

  public String getTodayName(int year, String name, String gender) {
    name = validateName(name);
    validateGender(gender);
    int rank = process.getRank(year, gender, name);
    //if getRank couldn't find a name
    if (rank == 0) {
      return NAME_ERROR;
    }
    String todayName = process.getNameFromRank(dataEndYear, gender, rank);
    return todayName + " " + gender;
  }

  //how should this handle ties?
  public String mostPopularName(int start, int end, String gender) {
    validateGender(gender);
    validateRange(start, end);
    List<String> names = process.getNamesFromRank(start, end, gender, 1);
    return process.mostFrequentNames(names);
  }

  public List<String> mostPopularLetter(int start, int end) {
    validateRange(start, end);
    List<String> letters = process.mostPopularLetters(start, end, "F");
    if (letters.size() == 0) {
      return letters;
    }
    String letter = letters.remove(0).substring(0, 1);
    return process.getNamesStartingWith(letter, "F", start, end);
  }

  public List<Integer> getRanksFromRange(int start, int end, String name, String gender) {
    validateRange(start, end);
    validateGender(gender);
    name = validateName(name);
    return process.getRanks(start, end, name, gender);
  }

  //if name isn't in both years, returns 0
  public int rankChange(int start, int end, String name, String gender) {
    validateRange(start, end);
    validateGender(gender);
    name = validateName(name);
    int nameFirstRank = process.getRank(start, gender, name);
    int nameLastRank = process.getRank(end, gender, name);
    if (nameFirstRank == 0 || nameLastRank == 0) {
      return 0;
    } else {
      return nameFirstRank - nameLastRank;
    }
  }

  //for ties, returns all names
  public List<String> biggestRankChange(int start, int end, String gender) {
    validateRange(start, end);
    validateGender(gender);
    Map<String, Integer> namesToRankChangeMap = new TreeMap<>();
    List<String> namesFromFirstYear = process.getNamesStartingWith("", gender, start, start);
    if (namesFromFirstYear.isEmpty()) {
      return namesFromFirstYear;
    }
    for (String name : namesFromFirstYear) {
      namesToRankChangeMap.put(name, abs(rankChange(start, end, name, gender)));
    }
    List<String> names = process.maxOccurrences(namesToRankChangeMap);
    names.remove(names.size() - 1);
    return names;
  }

  public double getAverageRankRange(int start, int end, String name, String gender) {
    validateRange(start, end);
    validateGender(gender);
    name = validateName(name);
    float sum = 0;
    float count = 0;
    while (start <= end) {
      int rank = process.getRank(start, gender, name);
      if (rank != 0) {
        sum += rank;
        count++;
      }
      start++;
    }
    return Math.round(sum / count * 100.0) / 100.0;
  }

  //ask about this, duplication and rounding
  public List<String> highestAverageRank(int start, int end, String gender) {
    validateRange(start, end);
    validateGender(gender);
    Map<String, Integer> namesToAverageRankMap = new TreeMap<>();
    List<String> allNames = process.getNamesStartingWith("", gender, start, end);
    if (allNames.isEmpty()) {
      return allNames;
    }
    for (String name : allNames) {
      namesToAverageRankMap.put(name, -1 * (int) getAverageRankRange(start, end, name, gender));
    }
    List<String> names = process.maxOccurrences(namesToAverageRankMap);
    names.remove(names.size() - 1);
    return names;
  }

  public double getAverageRankRecent(int numYears, String name, String gender) {
    int start = dataEndYear - numYears + 1;
    //don't need to validate since already checked in getAverageRankRange
    return getAverageRankRange(start, dataEndYear, name, gender);
  }

  public List<String> namesOfRank(int start, int end, String gender, int targetRank) {
    validateGender(gender);
    validateRange(start, end);
    List<String> names = new ArrayList<>();
    while (start <= end) {
      names.add(process.getNameFromRank(start, gender, targetRank));
      start++;
    }
    return names;
  }

  public String mostFrequentRank(int start, int end, String gender, int targetRank) {
    //don't need to validate
    List<String> names = namesOfRank(start, end, gender, targetRank);
    return process.mostFrequentNames(names);
  }

  public List<String> mostCommonPrefix(int start, int end, String gender) {
    validateRange(start, end);
    validateGender(gender);
    Map<String, Integer> prefixCountMap = new TreeMap<>();
    List<String> allNames = process.getNamesStartingWith("", gender, start, end);
    if (allNames.isEmpty()) {
      return allNames;
    }
    for (String name : allNames) {
      prefixCountMap.put(name, process.countNamesStartingWithRange(start, end, name, gender));
    }
    List<String> names = process.maxOccurrences(prefixCountMap);
    names.remove(names.size() - 1);
    return names;
  }


  //validate data range input
  private void validateRange(int start, int end) {
    if ((start > end) || (start < dataStartYear) || (end > dataEndYear)) {
      throw new InvalidParameterException(RANGE_ERROR);
    }
  }

  private void validateGender(String gender) {
    if (!GENDERS.contains(gender)) {
      throw new InvalidParameterException(GENDER_ERROR);
    }
  }

  //fixes format of name to be same as dataset
  //assumed to be capital first letter lowercase rest of letters
  private String validateName(String name) {
    return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
  }

  public static void main(String[] args) {
    Outputter Test = new Outputter("Test", "FOLDER");
    System.out.println(Test.biggestRankChange(1, 1, "M"));
//    System.out.println(Test.validateName("bob"));
//    System.out.println(Test.validateName("niCoHlAS"));
//    System.out.println(Test.validateName("Jake"));
//    System.out.println(Test.topNames(1990));
//    System.out.println(Test.countNamesAndBabies(1900, "R", "M"));
//    System.out.println(Test.countNamesAndBabies(1900, "Q", "F"));
//    System.out.println(Test.getRanks("Alex", "M"));
//    System.out.println((Test.getTodayName(2001, "Janet", "F")));
//    System.out.println(Test.mostPopularName(2001, 2001, "F"));
//    System.out.println(Test.mostPopularLetter(1900, 1910));
//    System.out.println(Test.mostPopularLetter(1900, 1925));
  }
}
