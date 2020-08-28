package names;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class ReadFiles {
    private String fileName;

    private int getYear(String path) {

        return Integer.parseInt(path.substring(3, 7));
    }

    //    public Map<Integer, List> listAllFiles(String path){
//        System.out.println("In listAllfiles(String path) method");
//        try(Stream<Path> paths = Files.walk(Paths.get(path))) {
//            paths.forEach(filePath -> {
//                if (Files.isRegularFile(filePath)) {
//                    try {
//                        generateList(filePath);
//                    } catch (Exception e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//                }
//            });
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
    //TO-DO: generalize this to be able to handle URL or different file locations/naming conventions
    private void getFileName(int year) {
        fileName = "yob" + year + ".txt";
    }

    //change data structure later
//    public List<String[]> generateList(Path path) {
//        List<String[]> profiles = new ArrayList<>();
//        String[] profile;
//        try {
//            for (String line : Files.readAllLines(path)) {
//                profile = line.split(",");
//                profiles.add(profile);
//            }
//            return profiles;
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (NullPointerException e) {
//            System.out.println("That year is not in the database");
//            System.exit(0);
//        }
//        return profiles;
//    }

    public List<String[]> generateList(int year) {
        List<String[]> profiles = new ArrayList<>();
        String[] profile;

        getFileName(year);

        try {
            Path path = Paths.get(ReadFiles.class.getClassLoader().getResource(fileName).toURI());
            for (String line : Files.readAllLines(path)) {
                profile = line.split(",");
                profiles.add(profile);
            }
            return profiles;
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("That year is not in the database");
            System.exit(0);
        }
        return profiles;
    }
}
//    public static void main(String[] args) throws Exception{
//        String folderPath = "C:/Users/alexc/CS307/data_team01/data/ssa_2000s";
//        listAllFiles(folderPath);
//
//    }
//}
//
