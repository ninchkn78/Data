package names;
/**
 * @author Alex Chao
 */

import static java.lang.StrictMath.abs;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Answers interesting questions about a given data set of baby names
 * throws an exception if the source of the data is invalid
 * Dependencies: DataReader.java and DataProcessor.java
 */
public class Outputter {

  private final int dataStartYear;
  private final int dataEndYear;
  private final DataProcessor process;
  private final static String YEAR_ERROR = "YEAR NOT IN DATABASE";
  private final static String RANGE_ERROR = "INVALID RANGE";
  private final static String NAME_ERROR = "NAME NOT FOUND";
  private final static String GENDER_ERROR = "INVALID GENDER";
  private final static List<String> GENDERS = Arrays.asList("M", "F");
  private final static String NO_MEANING = "(no meaning found)";

  private final Map<String, String> maleNameMeanings;
  private final Map<String, String> femaleNameMeanings;

  /**
   * Creates an Outputter object that can answer questions when given a dataset of baby names
   * @param dataSource the name of the source of baby names text files
   * @param dataType the type of data, currently supports local folder and local zip file in
   * resources root, URL, and online zip file
   */
  public Outputter(String dataSource, String dataType) {
    process = new DataProcessor(dataSource, dataType);
    dataStartYear = process.getDataFirstYear();
    dataEndYear = process.getDataLastYear();
    maleNameMeanings = DataReader.generateNamesMeaningsMap("M", "meanings.txt");
    femaleNameMeanings = DataReader.generateNamesMeaningsMap("F", "meanings.txt");
  }

  /**
   * Gets top female and male names from a given year
   * if the name does not exist, returns NAME ERROR. if the year
   * is not in the data set returns YEAR_ERROR.
   * @param year year to look through
   * @param wantMeaning if true, returns meanings of names along with names
   * @return the top male and female name.
   */
  //test 1
  public String topMaleAndFemaleName(int year, boolean wantMeaning) {
    String maleName = process.getNameFromRank(year, "M", 1);
    String femaleName = process.getNameFromRank(year, "F", 1);
    if (maleName.equals(NAME_ERROR)) {
      return NAME_ERROR;
    } else if (maleName.equals(YEAR_ERROR)) {
      return YEAR_ERROR;
    }
    maleName = addMeaningToName(wantMeaning, maleName, "M");
    femaleName = addMeaningToName(wantMeaning, femaleName, "F");
    return maleName + "\n" + femaleName;
  }

  /**
   *Counts the number of names and babies with a given year, gender, and starting String
   * throws an exception if an invalid gender is passed in.
   * if the year does not exist or if the starting string is invalid returns 0 for both values.
   * @param year year to look through
   * @param startsWith what each name should start with to be counted
   * @param gender gender to match
   * @return the count of the number of names and number of total babies.
   */
  //test 2
  public String countNamesAndBabies(int year, String startsWith, String gender) {
    validateGender(gender);
    int countNames = process.countNamesStartingWith(year, startsWith, gender);
    int totalBabies = process.countBabiesInYear(year, startsWith, gender);
    return "Names: " + countNames + "\nBabies: " + totalBabies;
  }

  /**
   * Finds the rank of the name/gender pair in every year in the dataset
   * throws an exception if the gender is invalid
   * @param name name to find the rank of
   * @param gender gender to match
   * @return a list of all the ranks that the name/gender pair held
   */
  //basic 1
  public List<Integer> ranksFromDataset(String name, String gender) {
    name = validateName(name);
    validateGender(gender);
    return process.getRanks(dataStartYear, dataEndYear, name, gender);
  }

  /**
   * Gets the name of the equivalent rank and gender in the most recent year in the dataset given a
   * year, name, and gender.
   * returns NAME_ERROR if the passed in name/gender pair was not found
   * @param year year to look through
   * @param name name to look for
   * @param gender gender to match
   * @param wantMeaning if true, returns the meaning of the name with the name
   * @return name matching the gender and rank in most recent year in dataset
   */
  //basic 2
  public String todayName(int year, String name, String gender, boolean wantMeaning) {
    name = validateName(name);
    validateGender(gender);
    int rank = process.getRank(year, gender, name);
    //if getRank couldn't find a name
    if (rank == 0) {
      return NAME_ERROR;
    }
    String todayName = process.getNameFromRank(dataEndYear, gender, rank);
    todayName = addMeaningToName(wantMeaning, todayName, gender);
    return todayName;
  }

  /**
   * Finds the most popular names given a range of years and a gender
   * throws an exception if the range or gender is invalid
   * @param start start year of range 
   * @param end end year of range the end year of the range to look through
   * @param gender gender to match
   * @return all of the top ranked names in the range of years
   */
  //basic 3
  public String mostPopularNames(int start, int end, String gender) {
    validateGenderAndRange(start, end, gender);
    List<String> names = process.getNamesFromRank(start, end, gender, 1);
    return process.mostFrequentNames(names);
  }

  /**
   * Finds all female names starting with the most popular starting letter in a range
   * throws an exception if the range is invalid
   * @param start start year of range 
   * @param end end year of range end year of the range
   * @return all of the names starting with the most popular letter. in the case of a tie, uses the
   * alphabetically first letter.
   */
  //basic 4
  public List<String> mostPopularFemaleStartingLetter(int start, int end) {
    validateRange(start, end);
    List<String> letters = process.mostPopularLetters(start, end, "F");
    if (letters.size() == 0) {
      return letters;
    }
    String letter = letters.remove(0).substring(0, 1);
    return process.getNamesStartingWith(letter, "F", start, end);
  }

  /**
   * Outputs the rank of the given name/gender pair for all years in a range
   * throws exception if range or gender is invalid
   * @param start start year of range 
   * @param end end year of range end range of year
   * @param name name to look for 
   * @param gender gender to match
   * @return a list of ranks for the name
   */
  //complete 1
  public List<Integer> ranksFromRange(int start, int end, String name, String gender) {
    validateGenderAndRange(start, end, gender);
    name = validateName(name);
    return process.getRanks(start, end, name, gender);
  }

  /**
   * Outputs a number representing how much a name's rank changed from the beginning and end of a
   * range. Positive means the rank went up and negative means the rank went down
   * @param start start year of range
   * @param end end year of range
   * @param name name to look for
   * @param gender gender to match
   * @return the rank difference of the name. returns 0 if the name is not in both years.
   */
  //complete 2
  public int rankChange(int start, int end, String name, String gender) {
    validateGenderAndRange(start, end, gender);
    name = validateName(name);
    int nameFirstRank = process.getRank(start, gender, name);
    int nameLastRank = process.getRank(end, gender, name);
    if (nameFirstRank == 0 || nameLastRank == 0) {
      return 0;
    } else {
      return nameFirstRank - nameLastRank;
    }
  }

  /**
   * Outputs the names that had the biggest rank change (either up or down) between two given years. ignores
   * names that are not represented in both years.
   * throws an exception if gender or range is invalid
   * @param start start year of range
   * @param end end year of range
   * @param gender gender to match
   * @return name that had the biggest rank change, for ties returns all names.
   */
  //complete 3
  public List<String> namesWithBiggestRankChange(int start, int end, String gender) {
    validateGenderAndRange(start, end, gender);
    Map<String, Integer> namesToRankChangeMap = new TreeMap<>();
    List<String> namesFromFirstYear = process.getNamesStartingWith("", gender, start, start);
    for (String name : namesFromFirstYear) {
      namesToRankChangeMap.put(name, abs(rankChange(start, end, name, gender)));
    }
    return process.maxOccurrences(namesToRankChangeMap);
  }

  /**
   * Outputs the average rank across a range of years for a given name/gender. ignores years where a
   * name is not present.
   * throws an exception if range of gender is invalid
   * @param start start year of range
   * @param end end year of range
   * @param name name to look for
   * @param gender gender to match
   * @return the average rank as an integer or 0 if the name was not present in any year
   */
  //complete 4
  public int averageRank(int start, int end, String name, String gender) {
    validateGenderAndRange(start, end, gender);
    name = validateName(name);
    int sum = 0, count = 0;
    while (start <= end) {
      int rank = process.getRank(start, gender, name);
      if (rank != 0) {
        sum += rank;
        count++;
      }
      start++;
    }
    if (count == 0) {
      return sum;
    }
    return sum / count;
  }

  /**
   * Outputs the names with the highest average rank with given gender in given range
   * throws an exception for invalid gender or range
   * @param start start year of range
   * @param end end year of range
   * @param gender gender to match
   * @return name with the highest rank, for ties, returns all names
   */
  //complete 5
  public List<String> namesWithHighestAverageRank(int start, int end, String gender) {
    validateGenderAndRange(start, end, gender);
    Map<String, Integer> namesToAverageRankMap = new TreeMap<>();
    List<String> allNames = process.getNamesStartingWith("", gender, start, end);
    for (String name : allNames) {
      //since a higher average rank is a smaller number, multiplying by -1 makes it so the highest
      //rank is actually the biggest number
      namesToAverageRankMap.put(name, -1 * averageRank(start, end, name, gender));
    }
    return process.maxOccurrences(namesToAverageRankMap);
  }

  /**
   * Outputs the average rank for the most recent number of years in the dataset.
   * throws an exception if the gender is invalid
   * @param numYears the number of recent years to look through
   * @param name name to look for
   * @param gender gender to match
   * @return the rank of the name across the years, 0 if the name was not found
   */
  //complete 6
  public int recentAverageRank(int numYears, String name, String gender) {
    int start = dataEndYear - numYears + 1;
    //don't need to validate since already checked in getAverageRankRange
    return averageRank(start, dataEndYear, name, gender);
  }

  /**
   * Outputs all names in range with the given rank and gender
   * throws an exception if the gender or range is invalid
   * @param start start year of range
   * @param end end year of range
   * @param gender gender to match
   * @param targetRank rank to look for
   * @return all names with target rank
   */
  //complete 7
  public List<String> namesWithRank(int start, int end, String gender, int targetRank) {
    validateGenderAndRange(start, end, gender);
    List<String> names = new ArrayList<>();
    while (start <= end) {
      names.add(process.getNameFromRank(start, gender, targetRank));
      start++;
    }
    return names;
  }

  /**
   * Outputs the names that most frequently held the target rank in a given range and gender
   * throws an exception for invalid range or gender
   * @param start start year of range
   * @param end end year of range
   * @param gender gender to match
   * @param targetRank rank to match
   * @return all names with the target rank
   */
  //complete 8
  public String mostFrequentRankedNames(int start, int end, String gender, int targetRank) {
    //don't need to validate
    List<String> names = namesWithRank(start, end, gender, targetRank);
    return process.mostFrequentNames(names);
  }

  /**
   * Outputs the names that start with the most common prefix in a range of years with a specific gender
   * if there are ties for prefix count, chooses the alphabetically first prefix
   * throws an exception for invalid gender or range
   * @param start start year of range
   * @param end end year of range
   * @param gender gender to match
   * @return all names starting with the most common prefix
   */
  //complete 9
  //if tie in prefixes, returns list using alphabetically first prefix
  public List<String> mostCommonPrefixNames(int start, int end, String gender) {
    validateGenderAndRange(start, end, gender);
    Map<String, Integer> prefixCountMap = new TreeMap<>();
    List<String> allNames = process.getNamesStartingWith("", gender, start, end);
    for (String name : allNames) {
      prefixCountMap.put(name, process.countNamesStartingWithRange(start, end, name, gender));
    }
    String mostCommonPrefix = process.maxOccurrences(prefixCountMap).get(0);
    return process.getNamesStartingWith(mostCommonPrefix, gender, start, end);
  }

  //complete 10
  //starts at the biggest prefix then gets smaller
  private String nameMeaning(Map<String, String> meanings, String name) {
    name = name.toUpperCase();
    //check all prefixes, if a meaning is found return the meaning
    int endIndex = name.length();
    String meaning;
    while (endIndex > 0) {
      meaning = meanings.get(name.substring(0, endIndex));
      if (meaning != null) {
        return meaning;
      }
      endIndex -= 1;
    }
    return NO_MEANING;
  }

  /**
   * Given a list of names with the same gender, outputs all names with their meanings attached
   * if a meaning could not be found, adds (no meaning found)
   * @param wantMeaning if True, adds meanings to all names
   * @param names list of names to add meanings to
   * @param gender gender to match
   * @return a list of names
   */
  //demonstrates possible functionality for adding names when a list is returned
  public List<String> addMeaningToNameList(boolean wantMeaning, List<String> names, String gender) {
    if (wantMeaning) {
      int i = 0;
      while (i < names.size()) {
        names.set(i, addMeaningToName(true, names.get(i), gender));
        i++;
      }
    }
    return names;
  }

  private void validateGenderAndRange(int start, int end, String gender) {
    validateGender(gender);
    validateRange(start, end);
  }

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

  private String addMeaningToName(boolean wantMeaning, String name, String gender) {
    if (wantMeaning) {
      Map<String, String> meanings = new HashMap<>();
      if (gender.equals("F")) {
        meanings = femaleNameMeanings;
      } else if (gender.equals("M")) {
        meanings = maleNameMeanings;
      }
      return name + " " + nameMeaning(meanings, name);
    }
    return name;
  }
 
}
