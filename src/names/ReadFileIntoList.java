package names;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadFileIntoList {
    //TO-DO: generalize this to be able to handle URL or different file locations/naming conventions
    private String generateDataSource(int year){
        String FileName = "yob" + year + ".txt";
        String PathName = "C:/Users/alexc/CS307/data_team01/data/ssa_complete/";
        return PathName + FileName;
    }
    //change data structure later
    public List<String[]> generateList(int year) {
        // pass the path to the file as a parameter
        File file =
                new File(generateDataSource(year));
        List<String[]> profiles = new ArrayList<>();
        String[] profile;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine()) != null) {
                profile = st.split(",");
                profiles.add(profile);
            }
            return profiles;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
    }
        return profiles;
    }
   /* public static void main(String[] args) throws Exception
    {
        ReadFileIntoList names = new ReadFileIntoList();
        System.out.println(names.generateList(1900).get(0)[1]);
    }

    */
}



