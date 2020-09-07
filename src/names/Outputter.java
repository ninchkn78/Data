package names;

import static java.lang.StrictMath.abs;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


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

  private final Map<String,String> maleNameMeanings;
  private final Map<String,String> femaleNameMeanings;

  public Outputter(String dataSource, String dataType) {
    process = new DataProcessor(dataSource, dataType);
    dataStartYear = process.getDataFirstYear();
    dataEndYear = process.getDataLastYear();
    maleNameMeanings = DataReader.generateNamesMeaningsMap("M","meanings.txt");
    femaleNameMeanings = DataReader.generateNamesMeaningsMap("F","meanings.txt");
  }

  //test 1
  public String topMaleAndFemaleName(int year, boolean wantMeaning) {
    String maleName = process.getNameFromRank(year, "M", 1);
    String femaleName = process.getNameFromRank(year, "F", 1);
    if (maleName.equals(NAME_ERROR)) {
      return NAME_ERROR;
    } else if (maleName.equals(YEAR_ERROR)) {
      return YEAR_ERROR;
    }
    maleName = addMeaningToName(wantMeaning,maleName,"M");
    femaleName = addMeaningToName(wantMeaning,femaleName,"F");
    return maleName + "\n" + femaleName;
  }

  //test 2
  public String countNamesAndBabies(int year, String startsWith, String gender) {
    validateGender(gender);
    int countNames = process.countNamesStartingWith(year, startsWith, gender);
    int totalBabies = process.countBabiesByYear(year, startsWith, gender);
    return "Names: " + countNames + "\nBabies: " + totalBabies;
  }

  //basic 1
  public List<Integer> ranksFromDataset(String name, String gender) {
    name = validateName(name);
    validateGender(gender);
    return process.getRanks(dataStartYear, dataEndYear, name, gender);
  }

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
    todayName = addMeaningToName(wantMeaning,todayName,gender);
    return todayName;
  }

  //basic 3
  public String mostPopularNames(int start, int end, String gender) {
    validateGenderAndRange(start, end, gender);
    List<String> names = process.getNamesFromRank(start, end, gender, 1);
    return process.mostFrequentNames(names);
  }

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

  //complete 1
  public List<Integer> ranksFromRange(int start, int end, String name, String gender) {
    validateGenderAndRange(start, end, gender);
    name = validateName(name);
    return process.getRanks(start, end, name, gender);
  }

  //complete 2
  //if name isn't in both years, returns 0
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

  //complete 3
  //for ties, returns all names
  public List<String> namesWithBiggestRankChange(int start, int end, String gender) {
    validateGenderAndRange(start, end, gender);
    Map<String, Integer> namesToRankChangeMap = new TreeMap<>();
    List<String> namesFromFirstYear = process.getNamesStartingWith("", gender, start, start);
    for (String name : namesFromFirstYear) {
      namesToRankChangeMap.put(name, abs(rankChange(start, end, name, gender)));
    }
    return process.maxOccurrences(namesToRankChangeMap);
  }

  //complete 4
  //average rank returned is an int
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

  //complete 6
  public int recentAverageRank(int numYears, String name, String gender) {
    int start = dataEndYear - numYears + 1;
    //don't need to validate since already checked in getAverageRankRange
    return averageRank(start, dataEndYear, name, gender);
  }

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

  //complete 8
  public String mostFrequentRankedNames(int start, int end, String gender, int targetRank) {
    //don't need to validate
    List<String> names = namesWithRank(start, end, gender, targetRank);
    return process.mostFrequentNames(names);
  }

  //complete 9
  public List<String> mostCommonPrefix(int start, int end, String gender) {
    validateGenderAndRange(start, end, gender);
    Map<String, Integer> prefixCountMap = new TreeMap<>();
    List<String> allNames = process.getNamesStartingWith("", gender, start, end);
    for (String name : allNames) {
      prefixCountMap.put(name, process.countNamesStartingWithRange(start, end, name, gender));
    }
    return process.maxOccurrences(prefixCountMap);
  }

  //complete 10
  //starts at the biggest prefix then gets smaller
  private String nameMeaning(Map<String,String> meanings, String name){
    name = name.toUpperCase();
    //check all prefixes, if a meaning is found return the meaning
    int endIndex = name.length() ;
    String meaning;
    while(endIndex > 0){
      meaning = meanings.get(name.substring(0,endIndex));
      if(meaning != null){
        return meaning;
      }
      endIndex -= 1;
    }
    return NO_MEANING;
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

  //demonstrates possible functionality for adding names when a list is returned
  public List<String> addMeaningToNameList(boolean wantMeaning, List<String> names, String gender){
    if(wantMeaning){
      int i = 0;
      while(i < names.size()){
        names.set(i,addMeaningToName(true,names.get(i),gender));
        i++;
      }
    }
    return names;
  }

  private String addMeaningToName(boolean wantMeaning, String name, String gender){
    if(wantMeaning){
      Map<String,String> meanings = new HashMap<>();
      if(gender.equals("F")){
        meanings = femaleNameMeanings;
      }
      else if(gender.equals("M")){
        meanings = maleNameMeanings;
      }
      return name + " " + nameMeaning(meanings,name);
    }
    return name;
  }

}
