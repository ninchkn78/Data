package names;

import java.util.List;

public class Process {
    //Constants

    //in case format of lines are different
    private final int NAME_INDEX = 0;
    private final int GENDER_INDEX = 1;
    private final int COUNT_INDEX = 2;

    //Variables
    private final List<String[]> profiles;

    public Process(int year){
        ReadFileIntoList listGenerator = new ReadFileIntoList();
        profiles = listGenerator.generateList(year);
    }
    public int namesStartWith (char letter, String gender) {
        int count = 0;
        for (String[] profile : profiles)
            if (profile[GENDER_INDEX].equals(gender) && profile[NAME_INDEX].charAt(0) == letter) count += 1;
        return count;
    }
    //these two methods perform the same loop and checks, so need to think of a way to generalize
    public int totalCount(char letter, String gender) {
        int sum = 0;
        for (String[] profile: profiles) {
            if (profile[GENDER_INDEX].equals(gender) && profile[NAME_INDEX].charAt(0) == letter)
                sum += Integer.parseInt(profile[COUNT_INDEX]);
        }
        return sum;
    }
    //gets name from gender and rank
    public String getName(String gender, int targetRank){
        int currRank = 1;
        for (String[] profile: profiles) {
            if(profile[GENDER_INDEX].equals(gender) && currRank == targetRank) {
                return profile[NAME_INDEX];
            }
            currRank += 1;
            if(currRank >= targetRank) return "There are not that many names of this gender";

        }
        return "No babies of this gender";
    }
    //these two methods almost do the same thing
    public String getRank(String name, String gender){
        int currRank = 1;
        for (String[] profile: profiles) {
            if(profile[GENDER_INDEX].equals(gender) && profile[NAME_INDEX].equals(name)) {
                return Integer.toString(currRank);
            }
            currRank +=1;
        }
        return "Name not found";
    }

}
