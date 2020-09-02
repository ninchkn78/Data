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

  //in case format of lines are different
  private final static int NAME_INDEX = 0;
  private final static int GENDER_INDEX = 1;
  private final static int COUNT_INDEX = 2;
  private final static String NAME_ERROR = "NAME NOT FOUND";
  private final static String YEAR_ERROR = "YEAR NOT IN DATABASE";

  //Variables

  //need to declare this as TreeMap so can use firstKey and lastKey
  private final TreeMap<Integer, List<String[]>> dataSet;

  public DataProcessor(String folderName) {
    dataSet = (TreeMap<Integer, List<String[]>>) FileReader.generateMap(folderName);
  }

  public int getDataFirstYear() {
    return dataSet.firstKey();
  }

  public int getDataLastYear() {
    return dataSet.lastKey();
  }

  public int countNamesByYear(int year, String startsWith, String gender) {
    int count = 0;
    List<String[]> yearData = getYearData(year);
    //checks if year is in dataset
    if (yearData == null) {
      return -1;
    }
    for (String[] profile : yearData) {

      String name = profile[NAME_INDEX];
      if (profile[GENDER_INDEX].equals(gender)) {
        if (name.length() >= startsWith.length()) {
          if (name.startsWith(startsWith)) {

            count += 1;
          }
        }
      }

    }
    return count;
  }

  //these two methods perform the same loop and checks, so need to think of a way to generalize
  //loops through entire dataset, returns number of babies with given gender and starting letter
  public int countBabiesByYear(int year, String startsWith, String gender) {
    int sum = 0;
    List<String[]> yearData = getYearData(year);
    //checks if year is in dataset
    if (yearData == null) {
      System.out.println(YEAR_ERROR);
      return -1;
    }
    for (String[] profile : yearData) {
      String name = profile[NAME_INDEX];
      if (profile[GENDER_INDEX].equals(gender)) {
        if (name.length() >= startsWith.length()) {
          if (name.startsWith(startsWith)) {
            sum += Integer.parseInt(profile[COUNT_INDEX]);
          }
        }
      }
    }
    return sum;
  }

  //these three methods all have the same loops ??
  //returns alphabetized list of all names starting with
  public List<String> getNamesStartingWith(String startsWith, String gender, int start, int end) {
    List<String> names = new ArrayList<>();
    while (start <= end) {
      List<String[]> yearData = getYearData(start);
      for (String[] profile : yearData) {
        String name = profile[NAME_INDEX];
        if (profile[GENDER_INDEX].equals(gender)) {
          if (name.length() >= startsWith.length()) {
            if (name.startsWith(startsWith)) {
              names.add(name);
            }
          }
        }
      }
      start++;
    }
    Collections.sort(names);
    Set<String> hashSet = new LinkedHashSet<>(names);
    names = new ArrayList<>(hashSet);
    return names;
  }

  private List<String[]> getYearData(int start) {
    return dataSet.get(start);
  }

  //can't use firstKey if not declared as TreeMap
  //gets names from specified years
  public List<String> getNamesFromRank(int start, int end, String gender, int targetRank) {
    List<String> names = new ArrayList<>();
    while (start <= end) {
      names.add(getNameFromRank(start, gender, targetRank));
      start++;
    }
    return names;
  }


  private Map<Integer, String> rankNameMap(List<String[]> yearData, String gender) {
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

  //gets name of specified rank and year
  public String getNameFromRank(int year, String gender, int targetRank) {
    //this repeats in getRank because want a way to return YEAR_ERROR
    List<String[]> yearData = getYearData(year);
    //checks if year is in dataset
    if (yearData == null) {
      return YEAR_ERROR;
    }
    Map<Integer, String> rankNameMap = rankNameMap(yearData, gender);
    String name = rankNameMap.get(targetRank);
    if (name == null) {
      return NAME_ERROR;
    }
    return name;
  }
  //getRanks and getNames
  public List<Integer> getRanks(int start, int end, String name, String gender) {
    List<Integer> ranks = new ArrayList<>();
    while (start <= end) {
      ranks.add(getRank(start, gender, name));
      start++;
    }
    return ranks;
  }

  public int getRank(int year, String gender, String name) {
    //this repeats in getRank because want a way to return YEAR_ERROR
    List<String[]> yearData = getYearData(year);
    //checks if year is in dataset
    if (yearData == null) {
      return 0;
    }
    Map<Integer, String> rankNameMap = rankNameMap(yearData, gender);
    for (int key : rankNameMap.keySet()) {
      if (rankNameMap.get(key).equals(name)) {
        return key;
      }
    }
    return 0;
  }

  //how should this handle ties?
  //returns most frequently occurring name in non unique list of strings
  public String mostFrequentNames(List<String> names) {
    int currCount;
    Map<String, Integer> nameCountMap = new TreeMap<>();
    for (String name : names) {
      if (name.equals(NAME_ERROR)) {
        continue;
      }
      currCount = nameCountMap.getOrDefault(name, 0);
      nameCountMap.put(name, currCount + 1);
    }
    if (nameCountMap.isEmpty()) {
      return NAME_ERROR;
    }
    return listToString(maxOccurrences(nameCountMap));
  }

  //this one increments by total babies not by 1
  //can it be a dictionary instead?
  public List<String> mostPopularLetters(int start, int end, String gender) {
    Map<String, Integer> letterCountMap = countBabiesByLetter(start, end, gender);
    List<String> letters = maxOccurrences(letterCountMap);
    letters.remove(letters.size() - 1);
    return letters;
  }

  //returns key value pairs where key is a letter and value is number of baby names
  //in the range starting with that letter of specified gender
  private Map<String, Integer> countBabiesByLetter(int start, int end, String gender) {
    int year;
    int currCount;
    String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String[] alphaArray = alphabet.split("");
    Map<String, Integer> letterCountMap = new TreeMap<>();
    for (String letter : alphaArray) {
      year = start;
      while (year <= end) {
        currCount = letterCountMap.getOrDefault(letter, 0);
        letterCountMap.put(letter, currCount + countBabiesByYear(year, letter, gender));
        year++;
      }
    }
    return letterCountMap;
  }

  //given a map of String Integer key value pairs
  // returns keys with max value in values
  private List<String> maxOccurrences(Map<String, Integer> stringIntMap) {
    List<String> mostFrequentStrings = new ArrayList<>();
    int max = Collections.max(stringIntMap.values());
    if (max == 0) {
      mostFrequentStrings.add("EMPTY");
      return mostFrequentStrings;
    }
    for (String key : stringIntMap.keySet()) {
      if (stringIntMap.get(key) == max) {
        mostFrequentStrings.add(key);
      }
    }
    mostFrequentStrings.add(Integer.toString(max));
    return mostFrequentStrings;
  }

  private String listToString(List<String> list) {
    StringBuilder sb = new StringBuilder();
    for (String s : list) {
      sb.append(s);
      sb.append(" ");
    }
    return sb.toString().strip();
  }

}
