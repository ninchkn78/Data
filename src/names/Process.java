package names;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Process {
    //Constants

    //in case format of lines are different
    private final int NAME_INDEX = 0;
    private final int GENDER_INDEX = 1;
    private final int COUNT_INDEX = 2;

    //Variables

    //ask about declaring as TreeMap
    private TreeMap<Integer, List> profiles;

    public Process(int start, int end){
        profiles = new TreeMap<>();
        ReadFiles listGenerator = new ReadFiles();
        while (start <= end) {
            profiles.put(start, listGenerator.generateList(start));
            start += 1;
        }
    }
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
    public String getName(String gender, int targetRank) {
        StringBuilder ret = new StringBuilder();
        int year = profiles.firstKey();
        for (Map.Entry<Integer, List> data : profiles.entrySet()) {
            int currRank = 1;
            List<String[]> value = data.getValue();
            for (String[] profile : value) {
                if (!profile[GENDER_INDEX].equals(gender)) {
                    continue;
                }
                else if(currRank == targetRank) {
                    ret.append(year).append(":").append(profile[NAME_INDEX]).append("\n");
                    break;
                }
                else {
                    currRank += 1;
                    ret.append("Fewer than ").append(targetRank).append(" babies in ").append(year).append("\n");
                }
            }
            year += 1;

        }
        return ret.toString();
    }
    //these two methods almost do the same thing
    public String getRank(String name, String gender){
        StringBuilder ret = new StringBuilder();

        int year = profiles.firstKey();
        for (Map.Entry<Integer, List> data : profiles.entrySet()) {
            int currRank = 1;
            boolean nameFound = false;
            List<String[]> value = data.getValue();
            for (String[] profile : value) {
                if (profile[GENDER_INDEX].equals(gender) && profile[NAME_INDEX].equals(name)) {
                    ret.append(year).append(":").append(currRank).append("\n");
                    nameFound = true;
                    break;
                }
                currRank += 1;
            }
            if (!nameFound) ret.append("Name not found in ").append(year).append("\n");
            year += 1;

        }
        return ret.toString();

    }

}
