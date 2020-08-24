package names;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ReadFileIntoList {
    private String FileName;

    //TO-DO: generalize this to be able to handle URL or different file locations/naming conventions
    private void getFileName(int year){
        FileName = "yob" + year + ".txt";
    }
    //change data structure later
    public List<String[]> generateList(int year) {
        List<String[]> profiles = new ArrayList<>();
        String[] profile;

        getFileName(year);

        try {
            Path path = Paths.get(ReadFileIntoList.class.getClassLoader().getResource(FileName).toURI());
            for (String line : Files.readAllLines(path)) {
                profile = line.split(",");
                profiles.add(profile);
            }
            return profiles;
        } catch (IOException e) {
            e.printStackTrace();
    }
        catch (URISyntaxException e){
            e.printStackTrace();
        }
        return profiles;
    }
    /*
   public static void main(String[] args) throws Exception
    {
        ReadFileIntoList names = new ReadFileIntoList();
        System.out.println(names.generateList(1900).get(0)[1]);
    }

     */


}



