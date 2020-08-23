package names;

import java.util.ArrayList;

public class Process {
    //Constants

    //in case format of lines are different
    int NAME_INDEX = 0;
    int GENDER_INDEX = 1;
    int COUNT_INDEX = 2;

    //Variables
    private ArrayList<String[]> profiles;

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
    public int TotalCount(char letter, String gender) {
        int sum = 0;
        for (String[] profile: profiles) {
            if (profile[GENDER_INDEX].equals(gender) && profile[NAME_INDEX].charAt(0) == letter)
                sum += Integer.parseInt(profile[COUNT_INDEX]);
        }
        return sum;
    }

    public String TopName(String gender){
        for (String[] profile: profiles) {
            if(profile[GENDER_INDEX].equals(gender)) {
                return profile[NAME_INDEX];
            }
        }
        return "No babies of this gender";
    }

}
