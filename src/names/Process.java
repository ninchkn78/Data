package names;

import java.util.List;

public class Process {
    //Constants

    //in case format of lines are different
    private final int NAME_INDEX = 0;
    private final int GENDER_INDEX = 1;
    private final int COUNT_INDEX = 2;

    //Variables
    private List<String[]> profiles;

    public Process(int year){
        ReadFileIntoList listGenerator = new ReadFileIntoList();
        profiles = listGenerator.generateList(year);
    }
    public int NamesStartWith (char letter, String gender) {
        int count = 0;
        for (String[] profile : profiles)
            if (profile[GENDER_INDEX].equals(gender) && profile[NAME_INDEX].charAt(0) == letter) count += 1;
        return count;
    }
    //these two methods perform the same loop and checks, so need to think of a way to generalize
    public int TotalCount(char letter, String gender) {
        int sum = 0;
        for (String[] profile: profiles) {
            if (profile[GENDER_INDEX].equals(gender) && profile[NAME_INDEX].charAt(0) == letter)
                sum += Integer.parseInt(profile[COUNT_INDEX]);
        }
        return sum;
    }

    //only accounts for gender
    public String TopName(String gender){
        for (String[] profile: profiles) {
            if(profile[GENDER_INDEX].equals(gender)) {
                return profile[NAME_INDEX];
            }
        }
        return "No babies of this gender";
    }

}
