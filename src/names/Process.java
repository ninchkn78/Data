package names;

import java.util.*;

public class Process {
    //Constants

    //in case format of lines are different
    private final int NAME_INDEX = 0;
    private final int GENDER_INDEX = 1;
    private final int COUNT_INDEX = 2;
    private final String GET_NAME_ERROR = "NAME NOT FOUND";

    //Variables

    //ask about declaring as TreeMap
    private final TreeMap<Integer, List> profiles;

    public Process(int start, int end){
        profiles = new TreeMap<>();
        ReadFiles listGenerator = new ReadFiles();
        while (start <= end) {
            profiles.put(start, listGenerator.generateList(start));
            start += 1;
        }
    }
    //wrapper
    public Process(int year){
        this(year, year);
    }

    //rename
//    private int sum(char letter, String gender, int iter) {
//        int count = 0;
//        for (Map.Entry<Integer, List> data : profiles.entrySet()) {
//            List<String[]> value = data.getValue();
//            for (String[] profile : value){
//                if (profile[GENDER_INDEX].equals(gender) && profile[NAME_INDEX].charAt(0) == letter) {
//                    count += iter;
//                }
//            }
//        }
//        return count;
//    }
//    public int namesStartWith (char letter, String gender) {
//        return sum(letter, gender, 1);
//    }
//
//    public int totalCount(char letter, String gender) {
//       return sum(letter, gender, Integer.parseInt(profile[COUNT_INDEX]);
//    }
    public int namesStartWith (char letter, String gender) {
        int count = 0;
        for (Map.Entry<Integer, List> data : profiles.entrySet()) {
            List<String[]> value = data.getValue();
            for (String[] profile : value){
                if (profile[GENDER_INDEX].equals(gender) && profile[NAME_INDEX].charAt(0) == letter) {
                    count += 1;
                    }
        }}
        return count;
    }
    //these two methods perform the same loop and checks, so need to think of a way to generalize
    public int totalCount(char letter, String gender) {
        int sum = 0;
        for (Map.Entry<Integer, List> data : profiles.entrySet()) {
            List<String[]> value = data.getValue();
            for (String[] profile : value) {
                if (profile[GENDER_INDEX].equals(gender) && profile[NAME_INDEX].charAt(0) == letter)
                    sum += Integer.parseInt(profile[COUNT_INDEX]);
            }
        }
        return sum;
    }
    //can't use firstKey if not declared as TreeMap
    public List<String> getNames(String gender, int targetRank) {
        List<String> names = new ArrayList<>();
        int year = profiles.firstKey();
        for (Map.Entry<Integer, List> data : profiles.entrySet()) {
            int currRank = 1;
            List<String[]> value = data.getValue();
            for (String[] profile : value) {
                if (profile[GENDER_INDEX].equals(gender)) {
                    if (currRank == targetRank) {
                        names.add(profile[NAME_INDEX]);
                        break;
                    } else {
                        currRank += 1;
                        if (currRank > targetRank) {
                            names.add(GET_NAME_ERROR);
                            break;}
                    }
                }
            }
            year += 1;

        }
        return names;
    }

    //these two methods almost do the same thing
    public List<String> getRanks(String name, String gender){
        List<String> ranks = new ArrayList<>();
        int year = profiles.firstKey();
        for (Map.Entry<Integer, List> data : profiles.entrySet()) {
            int currRank = 1;
            boolean nameFound = false;
            List<String[]> value = data.getValue();
            for (String[] profile : value) {
                if (profile[GENDER_INDEX].equals(gender)){
                        if(profile[NAME_INDEX].equals(name)) {
                    //ret.append(year).append(":").append(currRank).append("\n");
                        ranks.add(Integer.toString(currRank));
                        nameFound = true;
                        break;
                }
                        else {
                            currRank += 1;
                        }
            }}
            if (!nameFound) ranks.add("Not found");
            year += 1;
        }
        return ranks;

    }
    //how should this handle ties?
    public String mostFrequent(List<String> list){
        List<String> items = new ArrayList<>();
        List<Integer> counts = new ArrayList<>();
        for (String name : list){
            if(name.equals(GET_NAME_ERROR)) {
                continue;
            }
            if (items.contains(name)){
                int indx = items.indexOf(name);
                int currCount = counts.get(indx);
                counts.set(indx, currCount + 1);
            }
            else{
                items.add(name);
                counts.add(1);
            }
        }
        if(items.size() == 0) return GET_NAME_ERROR;
        return listToString(maxOccurences(items,counts));


    }

    public List<String> mostPopularLetters(String gender){
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String[] alphaArray = alphabet.split("");
        List<String> alphabetList = Arrays.asList(alphaArray);
        List<Integer> counts = new ArrayList<>();
        int i = 0;
        while (i < 26) {
            counts.add(namesStartWith(alphabet.charAt(i), gender));
            i++;
        }
        List<String> letters = maxOccurences(alphabetList, counts);
        letters.remove(letters.size() - 1);
        return letters;
        }

    private List<String> maxOccurences(List<String> items, List<Integer> counts){
        List<String> ret = new ArrayList<>();
        int max = Collections.max(counts);
        for(int i = 0; i < counts.size(); i++){
            if(counts.get(i) == max){
                ret.add(items.get(i));
            }
        }
        ret.add(Integer.toString(max));
        return ret;
    }
    private String listToString(List<String>List<String> strings){
        StringBuilder sb = new StringBuilder();
        for (String s : ret) {
            sb.append(s);
            sb.append(" ");
        }
        String str = sb.toString();
        return str;
    }
}
