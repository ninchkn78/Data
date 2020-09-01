package names;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;


public class Process {
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

  public Process(String folderName) {
    dataSet = (TreeMap<Integer, List<String[]>>) FileReader.generateMap(folderName);
  }
  public int getStartYear() {
    return dataSet.firstKey();
  }

  public int getEndYear() {
    return dataSet.lastKey();
  }

  public int countNamesByYear(int year, char letter, String gender) {
    int count = 0;
    List<String[]> yearData = dataSet.get(year);
    //checks if year is in dataset
    if (yearData == null) {
      return -1;
    }
    for (String[] profile : yearData) {
      if (profile[GENDER_INDEX].equals(gender) && profile[NAME_INDEX].charAt(0) == letter) {
        count += 1;
      }
    }
    return count;
  }

  //these two methods perform the same loop and checks, so need to think of a way to generalize
  //loops through entire dataset, returns number of babies with given gender and starting letter
  public int countBabiesByYear(int year, char letter, String gender) {
    int sum = 0;
    List<String[]> yearData = dataSet.get(year);
    //checks if year is in dataset
    if (yearData == null) {
      System.out.println(YEAR_ERROR);
      return -1;
    }
    for (String[] profile : yearData) {
      if (profile[GENDER_INDEX].equals(gender) && profile[NAME_INDEX].charAt(0) == letter) {
        sum += Integer.parseInt(profile[COUNT_INDEX]);
      }
    }
    return sum;
  }

  //these three methods all have the same loops ??
  //returns alphabeteized list of all names starting with
  public List<String> namesStartWith(char letter, String gender, int start, int end) {
    List<String> names = new ArrayList<>();
    while (start <= end) {
      List<String[]> value = dataSet.get(start);
      for (String[] profile : value) {
        if (profile[GENDER_INDEX].equals(gender) && profile[NAME_INDEX].charAt(0) == letter) {
          names.add(profile[NAME_INDEX]);
        }
      }
      start++;
    }
    Collections.sort(names);
    Set<String> hashSet = new LinkedHashSet<>(names);
    names = new ArrayList<>(hashSet);
    return names;
  }

  //can't use firstKey if not declared as TreeMap
  //gets names from specified years
  public List<String> getNames(int start, int end, String gender, int targetRank) {
    List<String> names = new ArrayList<>();
    while (start <= end) {
      names.add(getName(start, gender, targetRank));
      start++;
    }
    return names;
  }

  //gets name from a year, returns error if not in the dataset
  //the above comment is incorrect
  public String getName(int year, String gender, int targetRank) {
    int currRank = 1;
    List<String[]> yearData = dataSet.get(year);
    //checks if year is in dataset
      if (yearData == null) {
          return YEAR_ERROR;
      }
    for (String[] profile : yearData) {
      if (profile[GENDER_INDEX].equals(gender)) {
        if (currRank == targetRank) {
          return profile[NAME_INDEX];
        } else {
          currRank += 1;
          if (currRank > targetRank) {
            return NAME_ERROR;
          }
        }
      }
    }
    return NAME_ERROR;
  }

  //these two methods almost do the same thing
  public List<String> getRanks(int start, int end, String name, String gender) {
    List<String> ranks = new ArrayList<>();
    while (start <= end) {
      ranks.add(getRank(start, gender, name));
      start++;
    }
    return ranks;
  }

  public String getRank(int year, String gender, String name) {
    int currRank = 1;
    List<String[]> yearData = dataSet.get(year);
    //checks if year is in dataset
      if (yearData == null) {
          return NAME_ERROR;
      }
    for (String[] profile : yearData) {
      if (profile[GENDER_INDEX].equals(gender)) {
        if (profile[NAME_INDEX].equals(name)) {
          return Integer.toString(currRank);
        } else {
          currRank += 1;
        }
      }
    }
    return NAME_ERROR;
  }

  //how should this handle ties?
  public String mostFrequent(List<String> list) {
    List<String> items = new ArrayList<>();
    List<Integer> counts = new ArrayList<>();
    for (String name : list) {
      if (name.equals(NAME_ERROR)) {
        continue;
      }
      if (items.contains(name)) {
        int indx = items.indexOf(name);
        int currCount = counts.get(indx);
        counts.set(indx, currCount + 1);
      } else {
        items.add(name);
        counts.add(1);
      }
    }
      if (items.size() == 0) {
          return NAME_ERROR;
      }
    return listToString(maxOccurences(items, counts)).strip();
  }

  //this one increments by total babies not by 1
  //can it be a dictionary instead?
  public List<String> mostPopularLetters(int start, int end, String gender) {
    int tempStart;
    String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String[] alphaArray = alphabet.split("");
    List<String> alphabetList = Arrays.asList(alphaArray);
    List<Integer> counts = new ArrayList<>(Collections.nCopies(26, 0));
    int i = 0;
    while (i < 26) {
      tempStart = start;
      while (tempStart <= end) {
        int currCount = counts.get(i);
        counts.set(i, currCount + countBabiesByYear(tempStart, alphabet.charAt(i), gender));
        tempStart++;
      }
      i++;
    }
    List<String> letters = maxOccurences(alphabetList, counts);
    letters.remove(letters.size() - 1);
    return letters;
  }

  private List<String> maxOccurences(List<String> items, List<Integer> counts) {
    List<String> ret = new ArrayList<>();
    int max = Collections.max(counts);
    if (max == 0) {
      ret.add("0");
      return ret;
    }
    for (int i = 0; i < counts.size(); i++) {
      if (counts.get(i) == max) {
        ret.add(items.get(i));
      }
    }
    ret.add(Integer.toString(max));
    return ret;
  }

  private String listToString(List<String> list) {
    StringBuilder sb = new StringBuilder();
    for (String s : list) {
      sb.append(s);
      sb.append(" ");
    }
    return sb.toString();
  }

}
