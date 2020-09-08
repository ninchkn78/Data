package names;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class DataProcessor {
  //Constants

  private final static String NAME_ERROR = "NAME NOT FOUND";
  private final static String YEAR_ERROR = "YEAR NOT IN DATABASE";
  //in case format of lines are different
  private final static int NAME_INDEX = 0;
  private final static int GENDER_INDEX = 1;
  private final static int COUNT_INDEX = 2;

  //Variables

  //need to declare this as TreeMap so can use firstKey and lastKey
  private final TreeMap<Integer, List<String[]>> dataSet;


  public DataProcessor(String dataSource, String dataType) {
    dataSet = (TreeMap<Integer, List<String[]>>) DataReader
        .generateBabyNamesDataSet(dataSource, dataType);
    validateDataSet(dataSet);
  }

  private void validateDataSet(TreeMap<Integer, List<String[]>> dataSet) {
    if (dataSet.isEmpty()) {
      throw new IllegalArgumentException(DataReader.DATA_SOURCE_ERROR);
    }
    //check to make sure there are no missing years
    //i.e. all years are sequential and increasing by 1
    int previous = dataSet.firstKey() - 1;
    for (int year : dataSet.keySet()) {
      if (year != previous + 1) {
        System.out.println("YEARS NOT VALID");
        throw new IllegalArgumentException(DataReader.DATA_SOURCE_ERROR);
      }
      previous++;
    }
  }

  public int getDataFirstYear() {
    return dataSet.firstKey();
  }

  public int getDataLastYear() {
    return dataSet.lastKey();
  }

  //counts number of names w/year, starting string, gender
  public int countNamesStartingWith(int year, String startsWith, String gender) {
    if (getYearData(year) == null) {
      return 0;
    }
    List<String[]> yearData = getProfilesStartingWith(startsWith, gender, year);
    return yearData.size();
  }

  //counts number of names in given range w/gender,starting string
  public int countNamesStartingWithRange(int start, int end, String startsWith, String gender) {
    return getNamesStartingWith(startsWith, gender, start, end).size();
  }

  //counts number of babies w/year,starting string, gender
  public int countBabiesInYear(int year, String startsWith, String gender) {
    int sum = 0;
    List<String[]> yearData = getProfilesStartingWith(startsWith, gender, year);
    //checks if year is in dataset
    if (yearData.isEmpty()) {
      return 0;
    }
    for (String[] profile : yearData) {
      sum += Integer.parseInt(profile[COUNT_INDEX]);
    }
    return sum;
  }

  //returns alphabetized list of all names in range w/starting string, gender
  public List<String> getNamesStartingWith(String startsWith, String gender, int start, int end) {
    List<String> names = new ArrayList<>();
    while (start <= end) {
      List<String[]> yearData = getProfilesStartingWith(startsWith, gender, start);
      for (String[] profile : yearData) {
        names.add(profile[NAME_INDEX]);
      }
      start++;
    }
    names = sortRemoveDuplicates(names);
    return names;
  }

  //returns all names of rank in range w/gender
  public List<String> getNamesFromRank(int start, int end, String gender, int targetRank) {
    List<String> names = new ArrayList<>();
    while (start <= end) {
      names.add(getNameFromRank(start, gender, targetRank));
      start++;
    }
    return names;
  }

  //gets name of rank w/gender, year
  public String getNameFromRank(int year, String gender, int targetRank) {
    List<String[]> yearData = getYearData(year);
    if (yearData == null) {
      return YEAR_ERROR;
    }
    Map<Integer, String> rankNameMap = makeRankNameMap(yearData, gender);
    String name = rankNameMap.get(targetRank);
    if (name == null) {
      return NAME_ERROR;
    }
    return name;
  }

  //gets list of ranks per year in range w/name, gender
  public List<Integer> getRanks(int start, int end, String name, String gender) {
    List<Integer> ranks = new ArrayList<>();
    while (start <= end) {
      ranks.add(getRank(start, gender, name));
      start++;
    }
    return ranks;
  }

  //gets rank of name/gender pair in given year
  public int getRank(int year, String gender, String name) {
    List<String[]> yearData = getYearData(year);
    if (yearData == null) {
      return 0;
    }
    Map<Integer, String> rankNameMap = makeRankNameMap(yearData, gender);
    for (int key : rankNameMap.keySet()) {
      if (rankNameMap.get(key).equals(name)) {
        return key;
      }
    }
    return 0;
  }

  //returns most frequently occurring name in non unique list of name with how many times the name
  //appeared appended at the end
  public String mostFrequentNames(List<String> names) {
    int currentCount;
    Map<String, Integer> nameCountMap = new TreeMap<>();
    for (String name : names) {
      if (name.equals(NAME_ERROR)) {
        continue;
      }
      currentCount = nameCountMap.getOrDefault(name, 0);
      nameCountMap.put(name, currentCount + 1);
    }
    if (nameCountMap.isEmpty()) {
      return NAME_ERROR;
    }
    return listToString(maxOccurrences(nameCountMap)) + " " + maxValue(nameCountMap);
  }

  //returns the most popular starting letters of babies' names by baby count in given range, gender
  public List<String> mostPopularLetters(int start, int end, String gender) {
    Map<String, Integer> letterCountMap = countBabiesByLetter(start, end, gender);
    return maxOccurrences(letterCountMap);
  }

  //gets data from a given year
  private List<String[]> getYearData(int start) {
    return dataSet.get(start);
  }

  private List<String> sortRemoveDuplicates(List<String> names) {
    Collections.sort(names);
    Set<String> hashSet = new LinkedHashSet<>(names);
    names = new ArrayList<>(hashSet);
    return names;
  }

  //converts a list to a string
  private String listToString(List<String> list) {
    StringBuilder sb = new StringBuilder();
    for (String s : list) {
      sb.append(s);
      sb.append(" ");
    }
    return sb.toString().strip();
  }

  //given a list of ranked names, returns key value pairs
  //where key is rank and value is name associated with that rank
  private Map<Integer, String> makeRankNameMap(List<String[]> yearData, String gender) {
    Map<Integer, String> rankMap = new TreeMap<>();
    int rank = 1;
    for (String[] profile : yearData) {
      if (profile[GENDER_INDEX].equals(gender)) {
        rankMap.put(rank, profile[NAME_INDEX]);
        rank++;
      }
    }
    return rankMap;
  }

  //returns key value pairs where key is a letter and value is number of baby names
  //in the range starting with that letter
  private Map<String, Integer> countBabiesByLetter(int start, int end, String gender) {
    int year, currentCount;
    String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; //constant maybe
    String[] alphaArray = alphabet.split("");
    Map<String, Integer> letterCountMap = new TreeMap<>();
    for (String letter : alphaArray) {
      year = start;
      while (year <= end) {
        currentCount = letterCountMap.getOrDefault(letter, 0);
        letterCountMap.put(letter, currentCount + countBabiesInYear(year, letter, gender));
        year++;
      }
    }
    return letterCountMap;
  }

  // returns keys with max value in values, if empty map, returns empty string
  public List<String> maxOccurrences(Map<String, Integer> stringIntMap) {
    List<String> mostFrequentStrings = new ArrayList<>();
    if (stringIntMap.isEmpty()) {
      mostFrequentStrings.add("");
      return mostFrequentStrings;
    }
    int max = Collections.max(stringIntMap.values());
    if (max == 0) {
      return mostFrequentStrings;
    }
    for (String key : stringIntMap.keySet()) {
      if (stringIntMap.get(key) == max) {
        mostFrequentStrings.add(key);
      }
    }
    return mostFrequentStrings;
  }

  //get the maximum Integer in a String, Integer map
  private int maxValue(Map<String, Integer> stringIntegerMap) {
    return Collections.max(stringIntegerMap.values());
  }

  //gets yearData from a year and filters for names with starting string and gender
  private List<String[]> getProfilesStartingWith(String startsWith, String gender, int start) {
    List<String[]> yearData = getYearData(start);
    List<String[]> profiles = new ArrayList<>();
    if (yearData == null) {
      return profiles;
    }
    for (String[] profile : yearData) {
      String name = profile[NAME_INDEX];
      if (profile[GENDER_INDEX].equals(gender) &&
          name.length() >= startsWith.length() &&
          name.startsWith(startsWith)) {
        profiles.add(profile);
      }
    }
    return profiles;
  }

}

